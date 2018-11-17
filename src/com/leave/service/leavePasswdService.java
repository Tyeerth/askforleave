package com.leave.service;

import java.util.List;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.leave.config.Config;
import com.leave.dao.leavePasswdDao;

public class leavePasswdService {

	leavePasswdDao lDao = new leavePasswdDao();
	public void pass(int id) throws ClientException {
		//这里给用户自己发短信
		List<Map<String, Object>> list = lDao.pass(id);
		String name = "";//员工姓名
		String user_work_address = "";//单位
		String user_position = "";//职务
		String kind = "";//请假种类
		String day = "";//请假天数
		String time1 = "";//员工请假时间
		String time2 = "";//员工请假时间
		int user_id = 0;//获得员工id
		if(list != null) {
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
			//System.out.println(accessKeyId);
			//初始化ascClient,暂时不支持多region
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
			accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			 //组装请求对象
			 SendSmsRequest request = new SendSmsRequest();
			 //使用post提交
			 request.setMethod(MethodType.POST);
			 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			 String user_phone = String.valueOf(list.get(0).get("user_phone"));//员工电话号码
			 request.setPhoneNumbers(user_phone);
			 //必填:短信签名-可在短信控制台中找到
			 request.setSignName(Config.get("db.signName"));
			 System.out.println(Config.get("db.signName"));
			 //必填:短信模板-可在短信控制台中找到
			 request.setTemplateCode(Config.get("db.SMS_1"));
			 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			 name = (String)list.get(0).get("user_name");
			 user_work_address = (String)list.get(0).get("user_work_address");
			 user_position = (String)list.get(0).get("user_position");
			 kind = (String)list.get(0).get("leave_kind");
			 kind = kind.substring(0, kind.length()-1);
			 day = (String)list.get(0).get("leave_day");
			 time1 = (String)list.get(0).get("leave_start_day");
			 time2 = (String)list.get(0).get("leave_end_day");
			 
			 //单个参数总长度<=20个字符??
			 request.setTemplateParam("{\"name\":\""+name+"\",\"kind\":\""+kind+"\",\"day\":\""+day+"\",\"time1\":\""+time1+"\",\"time2\":\""+time2+"\"}");
			 //request.setTemplateParam("{\"name\":\""+name+"\", \"kind\":\""+kind+"\",\"time\":\""+time+"\"}");
			 //request.setTemplateParam("{\"name\":\""+name+"\",\"time\":\""+time+"\"}");
			 //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
			 //request.setSmsUpExtendCode("90997");
			 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			 request.setOutId("yourOutId");
			//请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			System.out.println("错误码(阿里帮助文档查看)："+sendSmsResponse.getCode());
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			//请求成功
				System.out.println("个人短信发送成功");
			}
			//员工id
			user_id = (Integer)list.get(0).get("user_id");
		}
		//这里给个人的相关领导发短信
		List<Map<String, Object>> listRelated = lDao.getLeaderInfo(user_id);
		if(listRelated != null) {
			String user_phone = "";//相关领导电话
			String leader_name = "";//相关领导姓名
			for(Map map : listRelated){
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
					DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
					IAcsClient acsClient = new DefaultAcsClient(profile);
					 //组装请求对象
					 SendSmsRequest request = new SendSmsRequest();
					 //使用post提交
					 request.setMethod(MethodType.POST);
					 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
					 user_phone = String.valueOf(map.get("user_phone"));//领导电话号码
					 leader_name = String.valueOf(map.get("user_name"));
					 request.setPhoneNumbers(user_phone);
					 //必填:短信签名-可在短信控制台中找到
					 request.setSignName(Config.get("db.signName"));
					 //必填:短信模板-可在短信控制台中找到
					 request.setTemplateCode(Config.get("db.SMS_2"));//发送给领导的模板
					 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
					 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
					 request.setTemplateParam("{\"name\":\""+leader_name+"\",\"user_work_address\":\""+user_work_address+"\",\"name2\":\""+user_position+name+"\",\"kind\":\""+kind+"\",\"day\":\""+day+"\",\"time1\":\""+time1+"\",,\"time2\":\""+time2+"\"}");
					 //request.setTemplateParam("{\"name\":\""+name+"\",\"time\":\""+time+"\"}");
					 //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
					 //request.setSmsUpExtendCode("90997");
					 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
					 request.setOutId("yourOutId");
					//请求失败这里会抛ClientException异常
					SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
					if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
					//请求成功
						System.out.println("相关领导短信发送成功");
					}
			}
		}
	}

	public void unpass(int id) {
		lDao.unpass(id);
	}

}
