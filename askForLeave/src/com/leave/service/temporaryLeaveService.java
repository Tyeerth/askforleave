package com.leave.service;

import java.util.List;
import java.util.Map;

import com.leave.dao.getUserInfoDao;
import com.leave.dao.temporaryLeaveDao;

public class temporaryLeaveService {

	getUserInfoDao gDao = new getUserInfoDao();
	temporaryLeaveDao tDao = new temporaryLeaveDao();
	public String getUserInfo(String user_name) {
		List<Map<String, Object>> list = gDao.getUserInfo(user_name);
		System.out.println(list);
		StringBuffer  pageContent = new StringBuffer("");
		if(list != null) {
			//重名着数目
			if(list.size() >= 2) {
				String option = "<option value=\"\">"+list.get(0).get("user_name")+"(重名)</option>";
				for(Map map : list) {
					option +="<option value=\""+map.get("id")+"\">"+map.get("user_name")+"</option>";
				}
				pageContent.append("<form id=\"askForLeave\" method=\"post\" class=\"form-horizontal\">\r\n" + 
						"\r\n" + 
						"                        <div class=\"form-body\" id=\"form-body\">\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">姓名</label>\r\n" + 
						"                                <div class=\"col-md-4\">\r\n" + 
						"                                    <input value=\""+list.get(0).get("user_name")+"\" onkeydown=\"name_event2()\" id=\"user_name\" name=\"user_name\" type=\"text\" class=\"form-control\">\r\n" + 
						"                                </div>\r\n" + 
						"                                <div class=\"col-md-4\">\r\n" + 
						"                                    <select onchange=\"optionUser3()\" id=\"selectUserId\" name=\"\" class=\"form-control\">\r\n" + 
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
						"<div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">事由</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                            </div>\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">请假开始日期</label>\r\n" + 
						"                                <div class=\"col-md-3\">\r\n" + 
						"                                    <input type=\"date\" class=\"form-control\" id=\"to_party_name\" alt=\"string\">\r\n" + 
						"                                </div>\r\n" + 
						"                               \r\n" + 
						"                            </div>\r\n" + 
						"                               <input type=\"hidden\" class=\"form-control\" alt=\"string\">\r\n" + 
						"                            <div class=\"form-group\">\r\n" + 
						"                                <label class=\"col-md-3 control-label\">时长(小时)</label>\r\n" + 
						"                                <div class=\"col-md-8\">\r\n" + 
						"                                    <input type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
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
						"\r\n" + 
						"                        </div>\r\n" + 
						"                    </form>\r\n" + 
						"");
			}
			else {
				//直接根据id调用就ok~，这里唯一name对应唯一id
				int id = (Integer)list.get(0).get("id");
				return new temporaryLeaveService().getUserInfo(id);
			}
		}
		
		return pageContent.toString();
	}

	public String getUserInfo(int id) {//关键
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
						
						pageContent.append("<form id=\"temporaryLeavedd\" method=\"post\" onsubmit=\"temporaryLeaveOpt()\" class=\"form-horizontal\">\r\n" + 
								"\r\n" + 
								"                        <div class=\"form-body\" id=\"form-body\">\r\n" + 
								"                            <div class=\"form-group\">\r\n" + 
								"                                <label class=\"col-md-3 control-label\">姓名</label>\r\n" + 
								"                                <div class=\"col-md-4\">\r\n" + 
								"                                    <input value=\""+map.get("id")+"\" name=\"user_id\" type=\"hidden\">\r\n" + 
								"                                    <input value=\""+map.get("user_name")+"\" onkeydown=\"name_event3()\" id=\"user_name\" name=\"user_name\" type=\"text\" class=\"form-control\">\r\n" + 
								"                                </div>\r\n" + 
								"                                <div class=\"col-md-4\">\r\n" + 
								"                                    <select onchange=\"optionUser3()\" id=\"selectUserId\" name=\"\" class=\"form-control\">\r\n" + 
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
								"                            </div>\r\n<div class=\"form-group\">\r\n" + 
								"                                <label class=\"col-md-3 control-label\">事由</label>\r\n" + 
								"                                <div class=\"col-md-8\">\r\n" + 
								"                                    <input name=\"leave_reason\" type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
								"                                </div>\r\n" + 
								"                            </div>\r\n" + 
								"                            <div class=\"form-group\">\r\n" + 
								"                                <label class=\"col-md-3 control-label\">请假开始日期</label>\r\n" + 
								"                                <div class=\"col-md-3\">\r\n" + 
								"                                    <input name=\"leave_date\" type=\"date\" class=\"form-control\" id=\"to_party_name\" alt=\"string\">\r\n" + 
								"                                </div>\r\n" + 
								"                               \r\n" + 
								"                            </div>\r\n" + 
								"                               <input type=\"hidden\" class=\"form-control\" alt=\"string\">\r\n" + 
								"                            <div class=\"form-group\">\r\n" + 
								"                                <label class=\"col-md-3 control-label\">时长(小时)</label>\r\n" + 
								"                                <div class=\"col-md-8\">\r\n" + 
								"                                    <input name=\"leave_hours\" type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
								"                                </div>\r\n" + 
								"                            </div>" + 
								"                            <div class=\"form-group\">\r\n" + 
								"                                <div class=\"col-md-11\">\r\n" + 
								"                                    <div style=\"float:right;\">\r\n" + 
								"                                        <a onclick=\"temporaryLeaveOpt('add')\" class=\"btn blue\" role=\"button\">提交</a>\r\n" + 
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
	//添加临时记录
	public boolean addTemporaryInfo(List listName, List listValue) {
		return tDao.addTemporaryInfo(listName,listValue);
	}

}
