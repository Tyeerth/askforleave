package com.leave.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.leave.dao.relatedPersonDao;

public class relatedPersonService {
	relatedPersonDao rd = new relatedPersonDao();
	//个人相关人员信息
	public String listRelated(int id, int pageNum) {
		List<Map<String, Object>> list = rd.listRelated(id,pageNum);
		StringBuffer pageContent = new StringBuffer("");
		int leave_user_id = 0;
		pageContent.append("<table class=\"table table-bordered\" id=\"user_info\">\r\n" + 
				"                                        <thead>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <th> 姓名</th>\r\n" + 
				"                                            <th> 性别</th>\r\n" + 
				"                                            <th> 本人籍贯</th>\r\n" + 
				"                                            <th> 工作单位</th>\r\n" + 
				"                                            <th> 现任职务</th>\r\n" + 
				"                                            <th> 职级</th>\r\n" + 
				"                                            <th> 所在类区</th>\r\n" + 
				"                                            <th> 联系电话</th>\r\n" + 
				"                                            <th> 删除</th>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        </thead>\r\n");
		if(list != null) {
			for(Map map : list) {
				//if(start.length() > 15) start = start.substring(0, 15);
				pageContent.append("<tbody>\r\n" + 
						"                                        <td>"+map.get("user_name")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_sex")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_origin")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_work_address")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_position")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_position_rank")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_class_area")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_phone")+"</td>\r\n" + 
						"                                        <td><a href=\"#\" onclick=deleteRelated('"+id+"','"+map.get("related_leader_id")+"')>删除</a></td>\r\n" + 
						"                                        </tbody>");
			}
			//leave_user_id = (Integer)list.get(0).get("leave_user_id");
		}
		pageContent.append("</table><a class=\"btn blue\" href=\"add_related_person.html?id="+id+"\" style=\"float:right;\">新增相关领导</a>");
		return pageContent.toString();
	}
	//批量添加相关人员信息
	public String listRelated(String[] ids) {
		List<Map<String, Object>> list = rd.listRelated(ids);
		StringBuffer pageContent = new StringBuffer("");
		int leave_user_id = 0;
		
		//重新拼接ids
		String ids2 = "";
		for (int i = 0; i < ids.length; i++) {
			ids2 += ids[i] + "$";
		}
		//System.out.println(ids2);
		ids2 = ids2.substring(0,ids2.length()-1);
		pageContent.append("<table class=\"table table-bordered\" id=\"user_info\">\r\n" + 
				"                                        <thead>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <th> 姓名</th>\r\n" + 
				"                                            <th> 性别</th>\r\n" + 
				"                                            <th> 本人籍贯</th>\r\n" + 
				"                                            <th> 工作单位</th>\r\n" + 
				"                                            <th> 现任职务</th>\r\n" + 
				"                                            <th> 职级</th>\r\n" + 
				"                                            <th> 所在类区</th>\r\n" + 
				"                                            <th> 联系电话</th>\r\n" + 
				"                                            <th> 删除</th>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        </thead>\r\n");
		if(list != null) {
			for(Map map : list) {
				//if(start.length() > 15) start = start.substring(0, 15);
				pageContent.append("<tbody>\r\n" + 
						"                                        <td>"+map.get("user_name")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_sex")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_origin")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_work_address")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_position")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_position_rank")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_class_area")+"</td>\r\n" + 
						"                                        <td>"+map.get("user_phone")+"</td>\r\n" + 
						"                                        <td><a href=\"#\" onclick=deleteRelated('"+ids2+"','"+map.get("id")+"')>删除</a></td>\r\n" + 
						"                                        </tbody>");
			}
		}
		
		pageContent.append("</table><a class=\"btn blue\" href=\"add_related_person.html?id=-1&ids="+ids2+"\" style=\"float:right;\">新增相关领导</a>");
		return pageContent.toString();
	}
	
	//删除相关人员
	public boolean deleteRelated(int user_id, int user_related_id) {
		
		return rd.deleteRelated(user_id,user_related_id);
	}

}
