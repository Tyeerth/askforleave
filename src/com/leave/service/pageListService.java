package com.leave.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import com.leave.dao.pageListDao;


public class pageListService {
	
	pageListDao pLD = new pageListDao();
	//人员信息页面加载
	public String listPage(int pageNum) {
		
		List<List> list = pLD.listPage(pageNum);
		
		//返回的数据样式
		StringBuffer  pageContent = new StringBuffer("");
		pageContent.append("<table class=\"table table-bordered\" id=\"user_info\">\r\n" + 
				"                                        <thead>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <th> <input class=\"checkBox\" type=\"checkbox\" onclick=\"swapCheck()\" /></th>\r\n" + 
				"                                            <th> 姓名</th>\r\n" + 
				"                                            <th> 性别</th>\r\n" + 
				"                                            <th> 出生年月</th>\r\n" + 
				"                                            <th> 参工时间</th>\r\n" + 
				"                                            <th> 本人籍贯</th>\r\n" + 
				"                                            <th> 配偶籍贯</th>\r\n" + 
				"                                            <th> 工作单位</th>\r\n" + 
				"                                            <th> 现任职务</th>\r\n" + 
				"                                            <th> 职级</th>\r\n" + 
				"                                            <th> 所在类区</th>\r\n" + 
				"                                            <th> 是否两地分居</th>\r\n" + 
				"                                            <th> 联系电话</th>\r\n" + 
				"                                            <th> 允许休假天数</th>\r\n" + 
				"                                            <th> 相关领导</th>\r\n" + 
				"                                            <th> 修改</th>\r\n" + 
				"                                            <th> 删除</th>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        </thead>\r\n");
		if(list != null) {
			for(int i = 0; i < list.size(); i++) {
				List pageList = list.get(i);
				if((Integer)pageList.get(0) == 1) continue;//隐藏根管理员
				pageContent.append("<tbody>\r\n" + 
						"                                        <td><input name=\"IDS\" value=\""+pageList.get(0)+"\" class=\"checkBox\" type=\"checkbox\" /></td>\r\n" + 
						"                                        <td>"+pageList.get(1)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(2)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(3)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(4)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(5)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(6)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(7)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(8)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(9)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(10)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(11)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(12)+"</td>\r\n" + 
						"                                        <td>"+pageList.get(13)+"</td>\r\n" + 
						"                                        <td><a href=\"user_related_person.html?id="+pageList.get(0)+"\">相关领导</a></td>\r\n" + 
						"                                        <td><a href=\"update_user.html?id="+pageList.get(0)+"\">修改</a></td>\r\n" + 
						"                                        <td><a href=\"updateUser?opt=delete&id="+pageList.get(0)+"\">删除</a></td>\r\n" + 
						"                                        </tbody>");
			}
		}
		pageContent.append("</table>");
		
		return pageContent.toString();
	}
	//修改人员信息页面数据
	public String updateListPage(int id) {
		pageListDao pLD = new pageListDao();
		List<List> list = pLD.updateListPage(id);
		
		//返回的数据样式
		StringBuffer  pageContent = new StringBuffer("");
		if(list != null) {
			for(List pageList : list) {
				//if(start.length() > 15) start = start.substring(0, 15);
				pageContent.append("<form method=\"post\" onsubmit=\"return update_user()\" id=\"uploadForm\" class=\"form-horizontal form-bordered form-label-stripped\">\r\n" + 
						"\r\n" + 
						"                        <div class=\"form-body\">\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">姓名</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"\r\n" + 
						"                                    <input name=\"id\" type=\"hidden\" value="+pageList.get(0)+">\r\n" + 
						"                                    <input name=\"user_name\" type=\"text\" class=\"form-control\" alt=\"string\" value="+pageList.get(1)+">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">性别</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"                                    <select name=\"user_sex\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                        <option value=\""+pageList.get(2)+"\">"+pageList.get(2)+"</option>\r\n" + 
						"                                        <option value=\"男\">男</option>\r\n" + 
						"                                        <option value=\"女\">女</option>\r\n" + 
						"                                    </select>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">出生年月</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"\r\n" + 
						"                                    <input name=\"user_born_time\" type=\"date\" class=\"form-control\" alt=\"string\" value="+pageList.get(3)+">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">参工时间</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"\r\n" + 
						"                                    <input name=\"user_work_time\" type=\"date\" class=\"form-control\" alt=\"string\" value="+pageList.get(4)+">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">本人籍贯</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"                                    <input name=\"user_origin\" type=\"text\" value="+pageList.get(5)+" class=\"form-control\" alt=\"string\">\r\n" + 
						" \r\n" + 
						/*"                                    <select id=\"cmbProvince\" name=\"cmbProvince\" class=\"col-md-4\"></select>    \r\n" + 
						"									<select id=\"cmbCity\" name=\"cmbCity\" class=\"col-md-4\"></select>    \r\n" + 
						"									<select id=\"cmbArea\" name=\"cmbArea\" class=\"col-md-4\"></select>    \r\n" + */
						"						            \r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">配偶籍贯</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"                                    <input name=\"user_spouse_origin\" type=\"text\" value="+pageList.get(6)+" class=\"form-control\" alt=\"string\">\r\n" + 
						/*" -->                                    <select id=\"cmbProvince2\" name=\"cmbProvince2\" class=\"col-md-4\"></select>    \r\n" + 
						"									<select id=\"cmbCity2\" name=\"cmbCity2\" class=\"col-md-4\"></select>    \r\n" + 
						"									<select id=\"cmbArea2\" name=\"cmbArea2\" class=\"col-md-4\"></select>\r\n" + */
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						/*"                            <div id=\"setupField\">\r\n" + 
						"                            </div>"*/
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">工作单位</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"                                    <input name=\"user_work_address\" value=\""+pageList.get(7)+"\" type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">现任职务</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"                                    <input name=\"user_position\" value=\""+pageList.get(8)+"\" type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">职级</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"                                    <input name=\"user_position_rank\" value=\""+pageList.get(9)+"\" type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"							<div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">所在类区</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"                                    <select name=\"user_class_area\" class=\"form-control\"\r\n" + 
						"                                                alt=\"string\">\r\n" + 
						"                                        <option value=\""+pageList.get(10)+"\">"+pageList.get(10)+"</option>\r\n" + 
						"                                        <option value=\"二类区\">二类区</option>\r\n" + 
						"                                        <option value=\"三类区\">三类区</option>\r\n" + 
						"                                        <option value=\"四类区\">四类区</option>\r\n" + 
						"                                        </select>\r\n" + 
						"\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">是否两地分居</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"                                    <select name=\"user_separated\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                    	<option value=\""+pageList.get(11)+"\">"+pageList.get(11)+"</option>\r\n" + 
						"                                    	<option value=\"是\">是</option>\r\n" + 
						"                                       <option value=\"否\">否</option>\r\n" + 
						"                                    </select>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">联系电话</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"                                    <input id=\"user_phone\" name=\"user_phone\" type=\"text\" class=\"form-control\" alt=\"string\" value="+pageList.get(12)+">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" +
						"							<div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">允许休假天数</label>\r\n" + 
						"                                <div class=\"col-md-6\">\r\n" + 
						"                                    <input id=\"user_max_day\" name=\"user_max_day\" value=\""+pageList.get(13)+"\" type=\"text\" class=\"form-control\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <div class=\"text-right col-md-9\">\r\n" + 
						"                                    <a class=\"btn blue\" role=\"button\" onclick=\"update_user()\">修改</a>\r\n" + 
						"                                    <a class=\"btn blue\" role=\"button\" onclick=\"cancle_user()\">取消</a>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                        </div>\r\n" + 
						"\r\n" + 
						"                    </form><script type=\"text/javascript\">    \r\n" + 
						"addressInit('cmbProvince', 'cmbCity', 'cmbArea');\r\n" + 
						"addressInit('cmbProvince2', 'cmbCity2', 'cmbArea2'); \r\n" + 
						"</script>");
			}
		}
		return pageContent.toString();
	}
	//请假审批
	public String isLeave(int pageNum) {
		List<Map<String, Object>> list = pLD.isLeave(pageNum);
		StringBuffer  pageContent = new StringBuffer("");
		pageContent.append("<table class=\"table table-bordered\" id=\"sample_1\">	<thead>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <th> <input class=\"checkBox\" type=\"checkbox\" onclick=\"swapCheck()\" /></th>\r\n" + 
				"                                            <th> 姓名</th>\r\n" + 
				"                                            <th> 单位</th>\r\n" + 
				"                                            <th> 职务</th>\r\n" + 
				"                                            <th> 不在岗期间主持工作领导</th>\r\n" + 
				"                                            <th> 请假类型</th>\r\n" + 
				"                                            <th> 请假天数</th>\r\n" + 
				"                                            <th> 开始日期</th>\r\n" + 
				"                                            <th> 结束日期</th>\r\n" + 
				"                                            <th> 联系电话</th>\r\n" + 
				"                                            <th> 备注</th>\r\n" + 
				"                                            <th> 审批</th>\r\n" + 
				"                                            <th> 审批</th>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        </thead>");
		//添加数据
		if(list != null) {
			for(Map map : list) {
			pageContent.append("<tbody>\r\n" + 
					"                                        <td><input name=\"IDS\" value=\""+map.get("id")+"\" class=\"checkBox\" type=\"checkbox\" /></td>\r\n" + 
					"                                        <td>"+map.get("user_name")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_work_address")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_position")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_leader")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_kind")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_day")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_start_day")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_end_day")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_phone")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_remark")+"</td>\r\n" + 
					"                                        <td><a onclick=\"leavePasswd('"+map.get("id")+"','pass')\" href=\"#\">同意</a></td>\r\n" + 
					"                                        <td><a onclick=\"leavePasswd('"+map.get("id")+"','unpass')\" href=\"#\">不同意</a></td>\r\n" + 
					"                                        </tbody>");
			}
		}
		pageContent.append("</table>");
		return pageContent.toString();
	}
	//历史请假记录
	public String historyLeave(int pageNum) {
		List<Map<String, Object>> list = pLD.historyLeave(pageNum);
		StringBuffer  pageContent = new StringBuffer("");
		pageContent.append("<table class=\"table table-bordered\" id=\"sample_1\">	<thead>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <th> 姓名</th>\r\n" + 
				"                                            <th> 单位</th>\r\n" + 
				"                                            <th> 职务</th>\r\n" + 
				"                                            <th> 职级</th>\r\n" + 
				"                                            <th> 不在岗期间主持工作领导</th>\r\n" + 
				"                                            <th> 请假类型</th>\r\n" + 
				"                                            <th> 请假天数</th>\r\n" + 
				"                                            <th> 开始日期</th>\r\n" + 
				"                                            <th> 结束日期</th>\r\n" + 
				"                                            <th> 联系电话</th>\r\n" + 
				"                                            <th> 请假备注</th>\r\n" + 
				"                                            <th> 到岗备注</th>\r\n" + 
				"                                            <th> 审批状态</th>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        </thead>");
		
		//添加数据
		if(list != null) {
			String status = "";
			int stat = 0;
			for(Map map : list) {
				stat = (Integer)map.get("leave_passed");
				if(stat == 1) status = "通过";
				else if(stat == 2) status = "不通过";
			pageContent.append("<tbody>\r\n" + 
					"                                        <td>"+map.get("user_name")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_work_address")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_position")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_position_rank")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_leader")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_kind")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_day")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_start_day")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_end_day")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_phone")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_remark")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_cut_remark")+"</td>\r\n" + 
					"                                        <td>"+status+"</td>\r\n" + 
					"                                        </tbody>");
			}
		}
		pageContent.append("</table>");
		return pageContent.toString();
	}
	//销假(修改历史请假记录)
	public String cuthistoryLeave(int pageNum) {
		List<Map<String, Object>> list = pLD.cuthistoryLeave(pageNum);
		StringBuffer  pageContent = new StringBuffer("");
		pageContent.append("<table class=\"table table-bordered\" id=\"sample_1\">	<thead>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <th> 姓名</th>\r\n" + 
				"                                            <th> 单位</th>\r\n" + 
				"                                            <th> 职务</th>\r\n" + 
				"                                            <th> 职级</th>\r\n" + 
				"                                            <th> 不在岗期间主持工作领导</th>\r\n" + 
				"                                            <th> 请假类型</th>\r\n" + 
				"                                            <th> 请假天数</th>\r\n" + 
				"                                            <th> 开始日期</th>\r\n" + 
				"                                            <th> 结束日期</th>\r\n" + 
				"                                            <th> 联系电话</th>\r\n" + 
				"                                            <th> 备注</th>\r\n" + 
				"                                            <th> 审批状态</th>\r\n" + 
				"                                            <th> 到岗</th>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        </thead>");
		
		//添加数据
		if(list != null) {
			String status = "";
			int stat = 0;
			for(Map map : list) {
				stat = (Integer)map.get("leave_passed");
				if(stat == 1) status = "通过";
				else if(stat == 2) status = "不通过";
			pageContent.append("<tbody>\r\n" + 
					"                                        <td>"+map.get("user_name")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_work_address")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_position")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_position_rank")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_leader")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_kind")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_day")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_start_day")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_end_day")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_phone")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_remark")+"</td>\r\n" + 
					"                                        <td>"+status+"</td>\r\n" + 
					"                                        <td><a onclick=\"cutLeave('"+map.get("id")+"')\">到岗</a></td>\r\n" + 
					"                                        </tbody>");
			}
		}
		pageContent.append("</table>");
		return pageContent.toString();
	}
	public String updatehistoryLeave(int id, String leave_end_day, String leave_cut_remark) throws ClientException {
		//发送销假短信
		leavePasswdDao lDao = new leavePasswdDao();
		//结果没有影响，但是会让pass()里面的update更新语句多执行一次无用功
		List<Map<String, Object>> list = lDao.pass(id);
		String name = "";//员工姓名
		String user_work_address = "";//单位
		String user_position = "";//职务
		String time = "";//员工请假时间
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
			 //必填:短信模板-可在短信控制台中找到
			 request.setTemplateCode(Config.get("db.SMS_6"));
			 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			 name = (String)list.get(0).get("user_name");
			 time = (String)list.get(0).get("leave_end_day");
			 System.out.println("预计请假结束日期:"+time);
			 request.setTemplateParam("{\"name\":\""+name+"\",\"time\":\""+time+"\"}");
			 //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
			 //request.setSmsUpExtendCode("90997");
			 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			 request.setOutId("yourOutId");
			//请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			//请求成功
				System.out.println("销假个人短信发送成功");
			}
			//员工id
			user_id = (Integer)list.get(0).get("user_id");
		}
		//这里给个人的相关领导发短信
		List<Map<String, Object>> listRelated = lDao.getLeaderInfo(user_id);
		if(listRelated != null) {
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
					 String user_phone = String.valueOf(map.get("user_phone"));//领导电话号码
					 String leader_name = String.valueOf(map.get("user_name"));//领导姓名
					 request.setPhoneNumbers(user_phone);
					 //必填:短信签名-可在短信控制台中找到
					 request.setSignName(Config.get("db.signName"));
					 //必填:短信模板-可在短信控制台中找到
					 request.setTemplateCode(Config.get("db.SMS_5"));//发送给领导的模板
					 user_work_address = (String)list.get(0).get("user_work_address");
					 user_position = (String)list.get(0).get("user_position");
					 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
					 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
					 request.setTemplateParam("{\"name\":\""+leader_name+"\",\"user_work_address\":\""+user_work_address+"\",\"name2\":\""+user_position+name+"\"}");
					 //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
					 //request.setSmsUpExtendCode("90997");
					 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
					 request.setOutId("yourOutId");
					//请求失败这里会抛ClientException异常
					SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
					if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
					//请求成功
						System.out.println("销假短信相关领导短信发送成功");
					}
			}
		}
		//修改销假记录
		pLD.updatehistoryLeave(id,leave_end_day,leave_cut_remark);
		return "";
	}
	//全局查找时数据总数
	public String countAll() {
		String count = pLD.countAll();
		String content = "<a>共有<strong id=\"countAll\">"+count+"</stong>条数据</a>";
		return content;
	}
	public String countAllLeave() {
		String count = pLD.countAllLeave();
		String content = "<a>共有<strong id=\"countAll\">"+count+"</stong>条数据</a>";
		return content;
	}
	public String countAllHistory() {
		String count = pLD.countAllHistory();
		String content = "<a>共有<strong id=\"countAll\">"+count+"</stong>条数据</a>";
		return content;
	}
	public String countAllcutHistory() {
		String count = pLD.countAllcutHistory();
		String content = "<a>共有<strong>"+count+"</stong>条数据</a>";
		return content;
	}
	//模糊匹配查找时数据总数
	public String countAll2(HttpSession session) {
		String content = "<a>共有<strong id=\"countAll\">"+session.getAttribute("userInfoNum")+"</stong>条数据</a>";
		//System.out.println(session.getAttribute("userInfoNum"));
		return content;
	}
	public String countAllLeave2(HttpSession session) {
		String content = "<a>共有<strong id=\"countAll\">"+session.getAttribute("isLeaveNum")+"</stong>条数据</a>";
		//System.out.println(session.getAttribute("leaveHistoryNum"));
		return content;
	}
	public String countAllHistory2(HttpSession session) {
		String content = "<a>共有<strong id=\"countAll\">"+session.getAttribute("leaveHistoryNum")+"</stong>条数据</a>";
		return content;
	}
	public String countAllcutHistory2(HttpSession session) {
		String content = "<a>共有<strong id=\"countAll\">"+session.getAttribute("cutLeaveNum")+"</stong>条数据</a>";
		return content;
	}
	//临时请假记录
	public String temporaryLeave() {
		List<Map<String, Object>> list = pLD.temporaryLeave();
		StringBuffer  pageContent = new StringBuffer("");
		pageContent.append("<table class=\"table table-bordered\" id=\"sample_1\">	<thead>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <th> 姓名</th>\r\n" + 
				"                                            <th> 职级</th>\r\n" + 
				"                                            <th> 联系电话</th>\r\n" + 
				"                                            <th> 短信内容</th>\r\n" + 
				"                                            <th> 时间</th>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        </thead>");
		
		//添加数据
		if(list != null) {
			for(Map map : list) {
			pageContent.append("<tbody>\r\n" + 
					"                                        <td>"+map.get("user_name")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_position_rank")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_phone")+"</td>\r\n" + 
					"                                        <td>"+map.get("content")+"</td>\r\n" + 
					"                                        <td>"+map.get("message_date")+"</td>\r\n" + 
					"                                        </tbody>");
			}
		}
		pageContent.append("</table>");
		return pageContent.toString();
	}
	public String temporaryHistoryLeave(int pageNum) {
		List<Map<String, Object>> list = pLD.temporaryHistoryLeave(pageNum);
		StringBuffer  pageContent = new StringBuffer("");
		pageContent.append("<table class=\"table table-bordered\" id=\"sample_1\">	<thead>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <th> 姓名</th>\r\n" + 
				"                                            <th> 单位</th>\r\n" + 
				"                                            <th> 职务</th>\r\n" + 
				"                                            <th> 职级</th>\r\n" + 
				"                                            <th> 联系电话</th>\r\n" + 
				"                                            <th> 事由</th>\r\n" + 
				"                                            <th> 日期</th>\r\n" + 
				"                                            <th> 时长(小时)</th>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        </thead>");
		
		//添加数据
		if(list != null) {
			for(Map map : list) {
			pageContent.append("<tbody>\r\n" + 
					"                                        <td>"+map.get("user_name")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_work_address")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_position")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_position_rank")+"</td>\r\n" + 
					"                                        <td>"+map.get("user_phone")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_reason")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_date")+"</td>\r\n" + 
					"                                        <td>"+map.get("leave_hours")+"</td>\r\n" + 
					"                                        </tbody>");
			}
		}
		pageContent.append("</table>");
		return pageContent.toString();
	}
	public String countAllTemporaryHistory(HttpSession session) {
		String count = pLD.countAllTemporaryHistory();
		String content = "<a>共有<strong>"+count+"</stong>条数据</a>";
		return content;
	}
	public String countAllTemporaryHistory2(HttpSession session) {
		String content = "<a>共有<strong>"+session.getAttribute("temporaryHistoryNum")+"</stong>条数据</a>";
		return content;
	}
}
