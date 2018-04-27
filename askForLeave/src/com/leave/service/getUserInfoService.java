package com.leave.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.leave.dao.getUserInfoDao;
import com.leave.dao.printInfoDao;
import com.leave.util.DateL;

public class getUserInfoService {
	getUserInfoDao gDao = new getUserInfoDao();
	public String getUserInfo(String user_name) {
		List<Map<String, Object>> list = gDao.getUserInfo(user_name);
		StringBuffer  pageContent = new StringBuffer("");
		if(list != null) {
			//重名着数目
			if(list.size() >= 2) {
				String option = "<option value=\"\">"+list.get(0).get("user_name")+"(重名)</option>";
				for(Map map : list) {
					option +="<option value=\""+map.get("id")+"\">"+map.get("user_name")+"</option>";
				}
				pageContent.append("<form id=\"askForLeave\" method=\"post\" onsubmit=\"get_userInfo()\" class=\"form-horizontal\">\r\n" + 
						"\r\n" + 
						"                        <div class=\"form-body\" id=\"form-body\">\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">姓名</label>\r\n" + 
						"                                <div class=\"col-md-4\">\r\n" + 
						"                                    <input value=\""+list.get(0).get("user_name")+"\" onkeydown=\"name_event()\" id=\"user_name\" name=\"user_name\" type=\"text\" class=\"form-control\">\r\n" + 
						"                                </div>\r\n" + 
						"                                <div class=\"col-md-4\">\r\n" + 
						"                                    <select onchange=\"optionUser()\" id=\"selectUserId\" name=\"\" class=\"form-control\">\r\n" + 
						"                                        "+option+"" + 
						"                                    </select>\r\n" + 
						"                                </div>\r\n" + 
						"\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"\r\n" + 
						"                                <label class=\"col-md-3 control-label\">联系电话</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"user_phone\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">单位</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"user_work_address\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">职务</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"user_position\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">本人籍贯</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"user_origin\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">配偶籍贯</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"user_origin\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">是否两地分居</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <select name=\"user_separated\" class=\"form-control\" disabled>\r\n" + 
						"                                        <option value=\"\">是</option>\r\n" + 
						"                                        <option value=\"\">否</option>\r\n" + 
						"                                    </select>\r\n" + 
						"\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">不在岗期间主持工作领导</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">请假种类</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <select name=\"\" class=\"form-control\">\r\n" + 
						"                                    </select>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <input type=\"hidden\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">事由</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">开始日期</label>\r\n" + 
						"                                <div class=\"col-md-3\">\r\n" + 
						"                                    <input type=\"date\" class=\"form-control\" id=\"to_party_name\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                                <label class=\"col-md-2 control-label\">请假天数</label>\r\n" + 
						"                                <div class=\"col-md-3\">\r\n" + 
						"                                    <input type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"\r\n" + 
						"							<div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">备注</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input type=\"text\" name=\"leave_remark\" class=\"form-control\" >\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" +
						"                            <div class=\"form-group\">\r\n" + 
						"                                <div class=\"col-md-11\">\r\n" + 
						"                                    <div style=\"float:right;\">\r\n" + 
						"                                        <a onclick=\"\" id=\"get_userInfo\" class=\"btn blue\" role=\"button\">提交</a>\r\n" + 
						"                                        <a role=\"button\" class=\"btn default new_words\">取消</a>\r\n" + 
						"                                    </div>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 

						"                        </div>\r\n" + 
						"                    </form>\r\n" + 
						"");
			}
			else {
				//直接根据id调用就ok~，这里唯一name对应唯一id
				int id = (Integer)list.get(0).get("id");
				return new getUserInfoService().getUserInfo(id);
			}
		}
		
		return pageContent.toString();
	}
	
	public String getUserInfo(int id) {
		//唯一id
		List<Map<String, Object>> list = gDao.getUserInfo(id);
		//根据id找name用作<select option>
		List<Map<String, Object>> listName = gDao.getUserInfo((String)list.get(0).get("user_name"));
		String option = "<option value=\"\"></option>";
		for(Map map : listName) {
			option +="<option value=\""+map.get("id")+"\"><strong>"+map.get("user_name")+"</strong></option>";
		}		
		
		//唯一user
		StringBuffer  pageContent = new StringBuffer("");
		if(list != null) {
			
				Map map = list.get(0);
				
				pageContent.append("<form id=\"askForLeave\" method=\"post\" onsubmit=\"get_userInfo()\" class=\"form-horizontal\">\r\n" + 
						"\r\n" + 
						"                        <div class=\"form-body\" id=\"form-body\">\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">姓名</label>\r\n" + 
						"                                <div class=\"col-md-4\">\r\n" + 
						"                                    <input value=\""+map.get("id")+"\" name=\"user_id\" type=\"hidden\">\r\n" + 
						"                                    <input value=\""+map.get("user_name")+"\" onkeydown=\"name_event()\" id=\"user_name\" name=\"user_name\" type=\"text\" class=\"form-control\">\r\n" + 
						"                                </div>\r\n" + 
						"                                <div class=\"col-md-4\">\r\n" + 
						"                                    <select onchange=\"optionUser()\" id=\"selectUserId\" name=\"\" class=\"form-control\">\r\n" + 
						"                                        "+option+"" + 
						"                                    </select>\r\n" + 
						"                                </div>\r\n" + 
						"\r\n" + 
						"                            </div>\r\n<div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">打印表格类型</label>\r\n" + 
						"<div class=\"col-md-8\">"+					
						"                                    <select name=\"person_kind\" class=\"person_kind form-control\">\r\n" + 
						"                                        <option value=\"县直正科\">县直正科</option>\r\n" + 
						"                                        <option value=\"县直副科\">县直副科</option>\r\n" + 
						"                                        <option value=\"科办员\">科办员</option>\r\n" + 
						"                                        <option value=\"专技工勤人员\">专技工勤人员</option>\r\n" + 
						"                                        <option value=\"乡镇正科书记\">乡镇正科书记</option>\r\n" + 
						"                                        <option value=\"乡镇正科\">乡镇正科</option>\r\n" + 
						"                                        <option value=\"乡镇副科\">乡镇副科</option>\r\n" + 
						"                                        <option value=\"驻寺负责人\">驻寺负责人</option>\r\n" + 
						"                                        <option value=\"驻寺一般\">驻寺一般</option>\r\n" + 
						"                                        <option value=\"驻寺干警\">驻寺干警</option>\r\n" + 
						"                                        <option value=\"驻寺工勤\">驻寺工勤</option>\r\n" + 
						"                                        <option value=\"乡镇派出所正科\">乡镇派出所正科</option>\r\n" + 
						"                                        <option value=\"乡镇派出所副科\">乡镇派出所副科</option>\r\n" + 
						"                                    </select>\r\n" + 
						"                           </div> </div>" + 
						"                            <div class=\"form-group\">\r\n" + 
						"\r\n" + 
						"                                <label class=\"col-md-3 control-label\">联系电话</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input value=\""+map.get("user_phone")+"\" name=\"user_phone\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">单位</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input value=\""+map.get("user_work_address")+"\" name=\"user_work_address\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">职务</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input value=\""+map.get("user_position")+"\" name=\"user_position\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">本人籍贯</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input value=\""+map.get("user_origin")+"\" name=\"user_origin\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">配偶籍贯</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input value=\""+map.get("user_spouse_origin")+"\" name=\"user_origin\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">是否两地分居</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    	<input value=\""+map.get("user_separated")+"\" name=\"user_separated\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">不在岗期间主持工作领导</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"leave_leader\" type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">请假种类</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <select name=\"leave_kind\" class=\"form-control\">\r\n" + 
						"                                        <option value=\"事假\">事假</option>\r\n" + 
						"                                        <option value=\"病假\">病假</option>\r\n" + 
						"                                        <option value=\"休假\">休假</option>\r\n" + 
						"                                        <option value=\"轮休\">轮休</option>\r\n" + 
						"                                        <option value=\"产假\">产假</option>\r\n" + 
						"                                        <option value=\"丧假\">丧假</option>\r\n" + 
						"                                        <option value=\"护理假\">护理假</option>\r\n" + 
						"                                        <option value=\"出差\">出差</option>\r\n" + 
						"                                        <option value=\"培训\">培训</option>\r\n" + 
						"									  </select>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                                    <input name=\"leave_day\" value=\"0\" type=\"hidden\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">事由</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"leave_reason\" type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">开始日期</label>\r\n" + 
						"                                <div class=\"col-md-3\">\r\n" + 
						"                                    <input name=\"leave_start_day\" type=\"date\" class=\"form-control\" id=\"to_party_name\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                                <label class=\"col-md-2 control-label\">请假天数</label>\r\n" + 
						"                                <div class=\"col-md-3\">\r\n" + 
						"                                    <input name=\"leave_end_day\" type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"\r\n" + 
						"								<div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">备注</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input type=\"text\" name=\"leave_remark\" class=\"form-control\" >\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <div class=\"col-md-11\">\r\n" + 
						"                                    <div style=\"float:right;\">\r\n" + 
						"                                        <a onclick=\"get_userInfo('askForLeave')\" id=\"get_userInfo\" class=\"btn blue\" role=\"button\">提交</a>\r\n" + 
						"                                        <a role=\"button\" class=\"btn default new_words\">取消</a>\r\n" + 
						"                                    </div>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"\r\n" + 
						"                        </div>\r\n" + 
						"                    </form>\r\n" + 
						"");
			
		}
		
		return pageContent.toString();
	}
	public String getUserInfo2(int leave_user_id, String user_name) {
		List<Map<String, Object>> list = gDao.getUserInfo(user_name);
		StringBuffer  pageContent = new StringBuffer("");
		if(list != null) {
			//重名着数目
			if(list.size() >= 2) {
				String option = "<option value=\"\">"+list.get(0).get("user_name")+"</option>";
				for(Map map : list) {
					option +="<option value=\""+map.get("id")+"\">"+map.get("user_name")+"</option>";
				}
				pageContent.append("<form id=\"askForLeave\" method=\"post\" onsubmit=\"get_userInfo()\" class=\"form-horizontal\">\r\n" + 
						"\r\n" + 
						"                        <div class=\"form-body\" id=\"form-body\">\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">姓名</label>\r\n" + 
						"                                <div class=\"col-md-4\">\r\n" + 
						"                                    <input value=\""+list.get(0).get("user_name")+"\" onkeydown=\"name_event2()\" id=\"user_name\" name=\"user_name\" type=\"text\" class=\"form-control\">\r\n" + 
						"                                </div>\r\n" + 
						"                                <div class=\"col-md-4\">\r\n" + 
						"                                    <select onchange=\"optionUser2()\" id=\"selectUserId\" name=\"\" class=\"form-control\">\r\n" + 
						"                                        "+option+"" + 
						"                                    </select>\r\n" + 
						"                                </div>\r\n" + 
						"\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"\r\n" + 
						"                                <label class=\"col-md-3 control-label\">联系电话</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"user_phone\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">单位</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"user_work_address\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">职务</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"user_position\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">籍贯</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input name=\"user_origin\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">是否两地分居</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <select name=\"user_separated\" class=\"form-control\" disabled>\r\n" + 
						"                                        <option value=\"\">是</option>\r\n" + 
						"                                        <option value=\"\">否</option>\r\n" + 
						"                                    </select>\r\n" + 
						"\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            </div>\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <div class=\"col-md-11\">\r\n" + 
						"                                    <div style=\"float:right;\">\r\n" + 
						"                                        <a onclick=\"\" id=\"get_userInfo\" class=\"btn blue\" role=\"button\">提交</a>\r\n" + 
						"                                        <a role=\"button\" class=\"btn default new_words\">取消</a>\r\n" + 
						"                                    </div>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"\r\n" + 
						"                        </div>\r\n" + 
						"                    </form>\r\n" + 
						"");
			}
			else {
				//直接根据id调用就ok~，这里唯一name对应唯一id
				int id = (Integer)list.get(0).get("id");
				return new getUserInfoService().getUserInfo2(leave_user_id,id);
			}
		}
		
		return pageContent.toString();
	}
	
	public String getUserInfo2(int leave_user_id, int id) {
		//唯一id
		List<Map<String, Object>> list = gDao.getUserInfo(id);
		//根据id找name用作<select option>
		List<Map<String, Object>> listName = gDao.getUserInfo((String)list.get(0).get("user_name"));
		String option = "<option value=\"\">"+list.get(0).get("user_name")+"</option>";
		for(Map map : listName) {
			option +="<option value=\""+map.get("id")+"\"><strong>"+map.get("user_name")+"</strong></option>";
		}		
		
		//唯一user
		StringBuffer  pageContent = new StringBuffer("");
		if(list != null) {
			
				Map map = list.get(0);
				
				pageContent.append("<form id=\"askForLeave\" method=\"post\" onsubmit=\"get_userInfo()\" class=\"form-horizontal\">\r\n" + 
						"\r\n" + 
						"                        <div class=\"form-body\" id=\"form-body\">\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">姓名</label>\r\n" + 
						"                                <div class=\"col-md-4\">\r\n" + 
						"                                    <input value=\""+leave_user_id+"\" name=\"leave_user_id\" type=\"hidden\">\r\n" + 
						"                                    <input value=\""+map.get("id")+"\" name=\"related_leader_id\" type=\"hidden\">\r\n" + 
						"                                    <input value=\""+map.get("user_name")+"\" onkeydown=\"name_event2()\" id=\"user_name\" name=\"user_name\" type=\"text\" class=\"form-control\">\r\n" + 
						"                                </div>\r\n" + 
						"                                <div class=\"col-md-4\">\r\n" + 
						"                                    <select onchange=\"optionUser2()\" id=\"selectUserId\" name=\"\" class=\"form-control\">\r\n" + 
						"                                        "+option+"" + 
						"                                    </select>\r\n" + 
						"                                </div></div>\r\n" + 
						"\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"\r\n" + 
						"                                <label class=\"col-md-3 control-label\">联系电话</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input value=\""+map.get("user_phone")+"\" name=\"user_phone\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">单位</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input value=\""+map.get("user_work_address")+"\" name=\"user_work_address\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">职务</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input value=\""+map.get("user_position")+"\" name=\"user_position\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">籍贯</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input value=\""+map.get("user_origin")+"\" name=\"user_origin\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">是否两地分居</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    	<input value=\""+map.get("user_separated")+"\" name=\"user_separated\" type=\"text\" class=\"form-control\" alt=\"string\" disabled>\r\n" + 
						"\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <div class=\"col-md-11\">\r\n" + 
						"                                    <div style=\"float:right;\">\r\n" + 
						"                                        <a onclick=\"get_userInfo2('addRelated')\" id=\"get_userInfo\" class=\"btn blue\" role=\"button\">提交</a>\r\n" + 
						"                                        <a role=\"button\" class=\"btn default new_words\">取消</a>\r\n" + 
						"                                    </div>\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"\r\n" + 
						"                        </div>\r\n" + 
						"                    </form>\r\n" + 
						"");
			
		}
		
		return pageContent.toString();
	}
	//插入请假数据后返回id
	public int insertLeave(List listName, List listValue) {
		//计算请假天数
		String leave_day = "";
		/*for(int i = 0 ;i < listName.size();i++) {
			if(listName.get(i).equals("leave_day")) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String start_day = (String) listValue.get(i+2);
				String end_day = (String) listValue.get(i+3);
				try {
					Date d1=sdf.parse(start_day);
					Date d2=sdf.parse(end_day); 
					int day = DateL.daysBetween(d1,d2)+1;
					leave_day = String.valueOf(day);
				} catch (ParseException e) {
					System.out.println("日期差计算错误");
				}  
				listValue.remove(i);
				listValue.add(i, leave_day);;
			}
		}*/
		for(int i = 0 ;i < listName.size();i++) {
			if(listName.get(i).equals("leave_day")) {
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String start_day = (String) listValue.get(i+2);
				String end_day = (String) listValue.get(i+3);
				
				leave_day = end_day;//项目修改，原先位置的结束日期改为请假天数
				int num_leave = Integer.parseInt(leave_day)-1;
				//通过请假天数计算结束日期				
				try {
					Date d1=sdf.parse(start_day);//开始时间				
					end_day = sdf.format(new Date(d1.getTime() + (long)num_leave * 24 * 60 * 60 * 1000));				
				} catch (ParseException e) {
					System.out.println("请假结束日期计算错误");
				}
				listValue.remove(i+3);//移除原先位置的结束日期
				listValue.add(i+3,end_day);
				listValue.remove(i);
				listValue.add(i, leave_day);;
			}
		}
		//System.out.println(listValue);
		int id = gDao.insertLeave(listName,listValue);
		return id;
	}
		/**
		 * 规则相关判断作为提示
		 * @return
		 */
		//插入数据后返回id,先进行相关规则的判断
		public int insertLeave2(List listName, List listValue) {
			//计算请假天数
			String leave_day = "";
			for(int i = 0 ;i < listName.size();i++) {
				if(listName.get(i).equals("leave_day")) {
					
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String start_day = (String) listValue.get(i+2);
					String end_day = (String) listValue.get(i+3);
					
					leave_day = end_day;//项目修改，原先位置的结束日期改为请假天数
					int num_leave = Integer.parseInt(leave_day)-1;
					//通过请假天数计算结束日期
					try {
						Date d1=sdf.parse(start_day);//开始时间				
						end_day = sdf.format(new Date(d1.getTime() + (long)num_leave * 24 * 60 * 60 * 1000));				
					} catch (ParseException e) {
						System.out.println("日期差计算错误");
					}
					listValue.remove(i+3);//移除原先位置的结束日期
					listValue.add(i+3,end_day);
					listValue.remove(i);
					listValue.add(i, leave_day);;
				}
			}
			
			//请假前进行一些逻辑判断，是否允许请假等
			int user_id = Integer.parseInt((String)listValue.get(0));//用户id
			for(int i = 0 ;i < listName.size();i++) {
				if(listName.get(i).equals("leave_kind")) {
					//==============================
					//轮休
					//====================
					if(listValue.get(i).equals("轮休")) {
						int leave_num = gDao.findLeaveNum(user_id);//查找用户已经请轮休次数
						int leave_dayed = gDao.findLeaveDay(user_id);//查找用户已请轮休天数
						//System.out.println(leave_num);
						if(leave_num > 3) return 0;
						List<Map<String, Object>> list = gDao.getUserInfo(user_id);//用户信息
						if(leave_dayed+Integer.parseInt(leave_day) > (Integer)list.get(0).get("user_max_day")) return 0;//每个人允许请假的天数上限
					}
					//==============================
					//休假
					//====================
					if(listValue.get(i).equals("休假")) {
						int leave_dayed = gDao.findLeaveDay5(user_id);//查找用户已请轮休天数
						List<Map<String, Object>> list = gDao.getUserInfo(user_id);//用户信息
						System.out.println(leave_dayed+"$$"+list.get(0).get("user_max_day"));
						if(leave_dayed+Integer.parseInt(leave_day) > (Integer)list.get(0).get("user_max_day")) return 0;//每个人允许请假的天数上限
					}
					//=============
					//事假
					//==============
					else if(listValue.get(i).equals("事假")) {
						if(Integer.parseInt(leave_day) <=3) break;//2.	事假小于3天（含3天）的直接记录
						int leave_dayed = gDao.findLeaveDayLess3Day(user_id);//小于3天的不计算的请事假天数，用来判断本次请假是否超过请假次数
						
						//根据自定义的规则开始匹配
						List<Map<String, Object>> list = gDao.getUserInfo(user_id);//查询用户籍贯、职级、所在类区、是否两地分居
						Map mapUser = list.get(0);//唯一id，表示该用户
						String user_class_area = (String) mapUser.get("user_class_area");//类区
						String user_origin = (String) mapUser.get("user_origin");//籍贯
						String user_position_rank = (String) mapUser.get("user_position_rank");//职级
						String user_separated = (String) mapUser.get("user_separated");//是否两地分居
						List<Map<String, Object>> rule = gDao.getRuleInfo();//获取所有规则，进行一一比较
						if(rule != null) {//if中的规则舍弃，暂时保留不删除
							for(Map mapRule : rule) {
								if(user_origin.substring(0, 2).equals("西藏")) {
									if(user_origin.equals("西藏日喀则市谢通门县")) {//西藏日喀则市谢通门县的不能超过10天
										if(leave_dayed+Integer.parseInt(leave_day)>10) return 0;
									}
									else {//西藏区内除去谢通门县的不能超过15天
										if(leave_dayed+Integer.parseInt(leave_day)>15) return 0;
									}
									break;
									//匹配到正确的西藏规则
									/*if(user_class_area.equals(mapRule.get("user_class_area")) 
											&&user_position_rank.equals(mapRule.get("user_position_rank")) 
											&&user_separated.equals(mapRule.get("user_separated")) 
											&&mapRule.get("user_origin").toString().contains("非西藏")){//匹配到正确的非西藏规则
										if(leave_dayed > Integer.parseInt((String)mapRule.get("user_max_day"))) {
											return 0;
										}
										break;//退出
									}*/
								}
								else {
									if(leave_dayed+Integer.parseInt(leave_day)>20) return 0;//籍贯在西藏区外的不能超过20天
									//匹配到正确的非西藏规则
									if(user_class_area.equals(mapRule.get("user_class_area")) 
											&&user_position_rank.equals(mapRule.get("user_position_rank")) 
											&&user_separated.equals(mapRule.get("user_separated")) 
											&&mapRule.get("user_origin").toString().contains("非西藏")){
										if(leave_dayed+Integer.parseInt(leave_day) > Integer.parseInt((String)mapRule.get("user_max_day"))) {
											return 0;
										}
										break;//退出
									}
								}
							}
						}
						//System.out.println(leave_dayed+"$$"+mapUser.get("user_max_day"));
						if(leave_dayed+Integer.parseInt(leave_day) > (Integer)mapUser.get("user_max_day")) return 0;//每个人允许请假的天数上限
					}
					//======================
					//	病假
					//======================
					else if(listValue.get(i).equals("病假")) {
						int leave_dayed = gDao.findLeaveDay2(user_id);
						if(leave_dayed+Integer.parseInt(leave_day) > 40) {
							return 0;
						}
					}
					//======================
					//	产假
					//======================
					else if(listValue.get(i).equals("产假")) {
						//今年和明年只能一次休假,判断前一年有没有请过产假
						boolean b = gDao.findLeaveDay3(user_id);
						if(b) {
							return 0;
						}
					}
					//======================
					//	丧假
					//======================
					else if(listValue.get(i).equals("丧假")) {
						int leave_dayed = gDao.findLeaveDay4(user_id);//小于3天的不计算的请事假天数，用来判断本次请假是否超过请假次数
						
						//根据自定义的规则开始匹配
						List<Map<String, Object>> list = gDao.getUserInfo(user_id);//查询用户籍贯、职级、所在类区、是否两地分居
						Map mapUser = list.get(0);//唯一id，表示该用户
						String user_class_area = (String) mapUser.get("user_class_area");//类区
						String user_origin = (String) mapUser.get("user_origin");//籍贯
						String user_position_rank = (String) mapUser.get("user_position_rank");//职级
						String user_separated = (String) mapUser.get("user_separated");//是否两地分居
						List<Map<String, Object>> rule = gDao.getRuleInfo();//获取所有规则，进行一一比较
						if(rule != null) {
							for(Map mapRule : rule) {
								if(user_origin.substring(0, 2).equals("西藏")) {
									if(user_origin.contains("西藏阿里地区")
											||user_origin.contains("西藏昌都市")
											||user_origin.contains("西藏那曲市")) {//籍贯为西藏阿里地区、西藏昌都市、西藏那曲市的丧假天数上限为36天
										if(leave_dayed+Integer.parseInt(leave_day)>36) return 0;
									}
									else if(user_origin.contains("西藏拉萨市")
											||user_origin.contains("西藏林芝市")
											||user_origin.contains("西藏山南市")) {//籍贯为西藏拉萨市、西藏林芝市、西藏山南市的丧假天数上限为34天
										if(leave_dayed+Integer.parseInt(leave_day)>34) return 0;
									}
									else if(user_origin.contains("西藏日喀则市桑珠孜区")) {//西藏日喀则市桑珠孜区的丧假天数上限为30天
										if(leave_dayed+Integer.parseInt(leave_day)>30) return 0;
									}
									else if(user_origin.contains("西藏日喀则市谢通门县")) {//西藏日喀则市谢通门县的丧假天数上限为28天
										if(leave_dayed+Integer.parseInt(leave_day)>28) return 0;
									}
									else {//西藏日喀则市内除桑珠孜区、谢通门县的其他县区的丧假天数上限为32天
										if(leave_dayed+Integer.parseInt(leave_day)>32) return 0;
									}
									break;
								}
								else {
									if(leave_dayed+Integer.parseInt(leave_day)>38) return 0;//西藏地区外的丧假天数上限为38天
									//匹配到正确的非西藏规则
									if(user_class_area.equals(mapRule.get("user_class_area")) 
											&&user_position_rank.equals(mapRule.get("user_position_rank")) 
											&&user_separated.equals(mapRule.get("user_separated")) 
											&&mapRule.get("user_origin").toString().contains("非西藏")){
										if(leave_dayed+Integer.parseInt(leave_day) > Integer.parseInt((String)mapRule.get("user_max_day"))) {
											return 0;
										}
										break;//退出
									}
								}
							}
						}
					}
				}
			}		
			//System.out.println(listValue);
			int id = gDao.insertLeave(listName,listValue);
			return id;
		}

	public String getPersonKind(int id) {
		return gDao.getPersonKind(id);
	}

	public boolean addRelated(int leave_user_id, int related_leader_id) {
		return gDao.addRelated(leave_user_id, related_leader_id);
	}

	
	printInfoDao pDao = new printInfoDao();
	//请假数据统计，根据id
	public String idLeavedInfo(int id) {
		List<List> list = pDao.LeaveInfo(id);
		StringBuffer content = new StringBuffer("");
		if(list !=null) {
			content.append("					<div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-4 control-label\">已请事假：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(0)+"次</p>\r\n" + 
					"                                </div>\r\n" + 
					"                                <label class=\"col-md-3 control-label\">累积：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(1)+"天</p>\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-4 control-label\">已请病假：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(2)+"次</p>\r\n" + 
					"                                </div>\r\n" + 
					"                                <label class=\"col-md-3 control-label\">累积：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(3)+"天</p>\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-4 control-label\">已请轮休：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(4)+"次</p>\r\n" + 
					"                                </div>\r\n" + 
					"                                <label class=\"col-md-3 control-label\">累积：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(5)+"天</p>\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-4 control-label\">已请休假：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(6)+"次</p>\r\n" + 
					"                                </div>\r\n" + 
					"                                <label class=\"col-md-3 control-label\">累积：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(7)+"天</p>\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-4 control-label\">已请产假：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(8)+"次</p>\r\n" + 
					"                                </div>\r\n" + 
					"                                <label class=\"col-md-3 control-label\">累积：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(9)+"天</p>\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-4 control-label\">已请丧假：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(10)+"次</p>\r\n" + 
					"                                </div>\r\n" + 
					"                                <label class=\"col-md-3 control-label\">累积：</label>\r\n" + 
					"                                <div class=\"col-md-2\">\r\n" + 
					"                                    <p class=\"form-control-static\">"+list.get(11)+"天</p>\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>");
		}
		return content.toString();
	}

	public String nameLeavedInfo(String user_name) {
		List<Map<String, Object>> list = gDao.getUserInfo(user_name);
		System.out.println(list);
		StringBuffer  pageContent = new StringBuffer("");
		if(list != null) {
			//重名着数目
			if(list.size() >= 2) {
			}
			else {
				//直接根据id调用就ok~，这里唯一name对应唯一id
				int id = (Integer)list.get(0).get("id");
				return new getUserInfoService().idLeavedInfo(id);
			}
		}
		
		return pageContent.toString();
	}

}
