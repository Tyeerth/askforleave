package com.leave.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.leave.config.Config;
import com.leave.db.JDBC;
/**
 * 静态定时任务
 * @author lu
 *
 */
public class TimerMessage {
	private static int last_day=1;//默认提前几天发送短信通知请假时间即将到期
	static TimerTask task = new TimerTask() {   
		public void run() {   
			//每次需要执行的代码放到这里面。   
			//System.out.println("测试");
			//leave_message_advance字段：0未提前发送过短信通知，1已发送
			String sql = "select a.id as id,leave_start_day,leave_end_day,user_name,user_phone from ask_for_leave a inner join leave_user b on a.user_id=b.id where leave_passed=1 and leave_message_advance=0";
			List<Map<String, Object>> list = JDBC.executeQuery(sql);
			
			//查询last_day天数配置
			sql = "select field from leave_setup where id=1";
			List<List> list2 = JDBC.list(sql);
			last_day = Integer.parseInt((String)list2.get(1).get(0));//设置界面中数据
			//System.out.println("什么鬼"+last_day);
			String name = "";//员工姓名
			String time = "";//员工请假时间
			if(list != null) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date d1 = null;Date d2=null;int day=0;
				for(Map map : list) {
					//System.out.println("什么鬼"+map);
					String end_day = (String) map.get("leave_end_day");
					try {
						d1=sdf.parse(sdf.format(new Date())); 
						d2=sdf.parse(end_day); 
						day = DateL.daysBetween(d1,d2);
						if(d1.before(d2) && day <= last_day) {//发短信,<=2天,发送过的不同发送了，防止遗漏
							System.out.println("提前"+day+"天发短信提醒返回岗位");
							//审核通过后发短信给该用户
							//设置超时时间-可自行调整
							System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
							System.setProperty("sun.net.client.defaultReadTimeout", "10000");
							//初始化ascClient需要的几个参数
							final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
							final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
							//替换成你的AK
							final String accessKeyId = Config.get("db.accessKeyId");//你的accessKeyId,参考本文档步骤2
							final String accessKeySecret = Config.get("db.accessKeySecret");//你的accessKeySecret，参考本文档步骤2
							//初始化ascClient,暂时不支持多region
							IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
							accessKeySecret);
							try {
								DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
							} catch (ClientException e) {
								e.printStackTrace();
							}
							IAcsClient acsClient = new DefaultAcsClient(profile);
							 //组装请求对象
							 SendSmsRequest request = new SendSmsRequest();
							 //使用post提交
							 request.setMethod(MethodType.POST);
							 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
							 String user_phone = String.valueOf(map.get("user_phone"));//员工电话号码
							 request.setPhoneNumbers(user_phone);
							 //必填:短信签名-可在短信控制台中找到
							 request.setSignName(Config.get("db.signName"));
							 //必填:短信模板-可在短信控制台中找到
							 request.setTemplateCode("SMS_117515413");
							 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
							 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
							 name = (String)map.get("user_name");
							 time = map.get("leave_start_day")+"到"+map.get("leave_end_day");
							 request.setTemplateParam("{\"name\":\""+name+"\",\"time\":\""+time+"\"}");
							 //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
							 //request.setSmsUpExtendCode("90997");
							 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
							 request.setOutId("yourOutId");
							//请求失败这里会抛ClientException异常
							SendSmsResponse sendSmsResponse = null;
							try {
								sendSmsResponse = acsClient.getAcsResponse(request);
							} catch (ClientException e) {
								e.printStackTrace();
							}
							if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
								//请求成功
								System.out.println("请假时间到期个人短信发送成功");
								sql = "update ask_for_leave set leave_message_advance=1 where id=?";
								JDBC.executeUpdate(sql, map.get("id"));
							}
						}
					} catch (ParseException e) {
						System.out.println("日期差计算错误");
					}
				}
			}
		}   
	}; 
	static Timer timer=null;
	public static void start() {//定时任务开启
		Calendar calendar = Calendar.getInstance();
		//增加(正数)或减少天数(负数),0不变
		//此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		
		/*** 定制每日17:00执行方法 ***/
		calendar.set(Calendar.HOUR_OF_DAY, 17);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date date=calendar.getTime();//第一次执行定时任务的时间
		//System.out.println(date);
		timer = new Timer();
		//timer.cancel();//关闭
		timer.schedule(task,date,86400000);//每天下午5点执行任务
	}
	public static void stop() {
		timer.cancel();
	}
}
