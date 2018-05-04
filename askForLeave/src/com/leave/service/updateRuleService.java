package com.leave.service;

import java.util.List;
import java.util.Map;

import com.leave.dao.updateRuleDao;

public class updateRuleService {
	
	updateRuleDao uDao = new updateRuleDao();
	public boolean addRule(List listName, List listValue) {
		originOpt(listName,listValue);
		return uDao.addRule(listName,listValue);
	}
	
	public boolean updateRule(List listName, List listValue) {
		originOpt(listName,listValue);
		return uDao.updateRule(listName,listValue);
	}
	//籍贯处理转换方法
	private void originOpt(List listName, List listValue) {
		String cmbProvince="";
		String cmbCity="";
		String cmbArea="";
		for(int i=0; i < listName.size(); i++) {
			if(listName.get(i).equals("cmbProvince")) {
				cmbProvince = (String) listValue.get(i);
				listName.remove(i);
				listValue.remove(i);
				i--;
			}
			else if(listName.get(i).equals("cmbCity")) {
				cmbCity = (String) listValue.get(i);
				listName.remove(i);
				listValue.remove(i);
				i--;
			}
			else if(listName.get(i).equals("cmbArea")) {
				cmbArea = (String) listValue.get(i);
				listName.remove(i);
				listValue.remove(i);
				i--;
			}
		}
		listName.add(1, "user_origin");
		listValue.add(1, cmbProvince+cmbCity+cmbArea);
	}
	//规则展示
	public String listRule() {
		StringBuffer pageContent = new StringBuffer("");
		List<Map<String, Object>> list = uDao.listRule();
		if(list!=null) {
			pageContent.append("<table class=\"table table-bordered\">\r\n" + 
					"                                        <thead>\r\n" + 
					"                                        <tr>\r\n" + 
					"                                            <th> 类区</th>\r\n" + 
					"                                            <th> 籍贯</th>\r\n" + 
					"                                            <th> 职级</th>\r\n" + 
					"                                            <th> 是否两地分居</th>\r\n" + 
					"                                            <th> 请假天数</th>\r\n" + 
					"                                            <th> 修改</th>\r\n" + 
					"                                            <th> 删除</th>\r\n" + 
					"                                        </tr>\r\n" + 
					"                                        </thead>\r\n" + 
					"                                        <tbody>");
			for(Map map : list) {
				pageContent.append("<tr><td>"+map.get("user_class_area")+"</td>\r\n" + 
						"                                            <td>"+map.get("user_origin")+"</td>\r\n" + 
						"                                            <td>"+map.get("user_position_rank")+"</td>\r\n" + 
						"                                            <td>"+map.get("user_separated")+"</td>\r\n" + 
						"                                            <td>"+map.get("user_max_day")+"</td>\r\n" + 
						"                                            <td><a href=\"rule_up.html?id="+map.get("id")+"\">修改</a></td>\r\n" + 
						"                                            <td><a onclick=\"deleteRule('"+map.get("id")+"')\">删除</a></td>\r\n" + 
						"                                        </tr>");
			}
			pageContent.append("</tbody></table>");
		}
		return pageContent.toString();
	}

	public boolean deleteRule(int id) {
		return uDao.deleteRule(id);
	}
	//根据id展示修改页面数据
	public String updateRuleList(int id) {
		StringBuffer pageContent = new StringBuffer("");
		List<Map<String, Object>> list = uDao.updateRuleList(id);
		if(list!=null) {
			Map map = list.get(0);//唯一id
			pageContent.append(" <form method=\"post\" onsubmit=\"return update_rule('update')\" id=\"updateRule\" class=\"form-horizontal form-bordered form-label-stripped\">\r\n" + 
					"\r\n" + 
					"                        <div class=\"form-body\">\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-3 control-label\">所在类区</label>\r\n" + 
					"                                <div class=\"col-md-6\">\r\n" + 
					"                                    <select name=\"user_class_area\" class=\"form-control\"\r\n" + 
					"                                            alt=\"string\">\r\n" + 
					"                                        <option value=\""+map.get("user_class_area")+"\">"+map.get("user_class_area")+"(已选)</option>\r\n" + 
					"                                        <option value=\"二类区\">二类区</option>\r\n" + 
					"                                        <option value=\"三类区\">三类区</option>\r\n" + 
					"                                        <option value=\"四类区\">四类区</option>\r\n" + 
					"                                    </select>\r\n" + 
					"\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-3 control-label\">本人籍贯</label>\r\n" + 
					"                                <div class=\"col-md-6\">\r\n" + 
					"                                    <!-- <input name=\"user_origin\" type=\"text\" class=\"form-control\" alt=\"string\">\r\n" + 
					" -->\r\n" + 
					"                                    <select class=\"col-md-4\" id=\"cmbProvince\" name=\"cmbProvince\"></select>\r\n" + 
					"                                    <select class=\"col-md-4\" id=\"cmbCity\" name=\"cmbCity\"></select>\r\n" + 
					"                                    <select class=\"col-md-4\" id=\"cmbArea\" name=\"cmbArea\"></select>\r\n" + 
					"\r\n" + 
					"                                </div><script type=\"text/javascript\">\r\n" + 
					"    addressInit('cmbProvince', 'cmbCity', 'cmbArea');\r\n" + 
					"    addressInit('cmbProvince2', 'cmbCity2', 'cmbArea2');\r\n" + 
					"</script>\r\n" + 
					"                            </div>\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-3 control-label\">职级</label>\r\n" + 
					"                                <div class=\"col-md-6\" id=\"position_rank\">\r\n" + 
					"                                    <select name=\"user_position_rank\" class=\"form-control\">\r\n" + 
					"                                        <option value=\"\"></option>\r\n" + 
					"                                    </select>\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-3 control-label\">是否两地分居</label>\r\n" + 
					"                                <div class=\"col-md-6\">\r\n" + 
					"                                    <select name=\"user_separated\" class=\"form-control\" alt=\"string\">\r\n" + 
					"                                        <option value=\"是\">是</option>\r\n" + 
					"                                        <option value=\"否\">否</option>\r\n" + 
					"                                    </select>\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <label class=\"col-md-3 control-label\">请假天数</label>\r\n" + 
					"                                <div class=\"col-md-6\">\r\n" + 
					"                                    <input name=\"user_separated\" class=\"form-control\" value=\""+map.get("user_max_day")+"\">\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>\r\n" + 
					"\r\n" + 
					"                            <div class=\"form-group\">\r\n" + 
					"                                <div class=\"text-right col-md-9\">\r\n" + 
					"                                    <a class=\"btn blue\" role=\"button\" onclick=\"update_rule('update')\">提交</a>\r\n" + 
					"                                    <button type=\"reset\" class=\"btn default\">取消</button>\r\n" + 
					"                                </div>\r\n" + 
					"                            </div>\r\n" + 
					"                        </div>\r\n" + 
					"\r\n" + 
					"                    </form>");
		}
		return pageContent.toString();
	}
}
