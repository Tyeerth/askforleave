package com.leave.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.leave.dao.findUserDao;

public class findUserService {
	findUserDao  fDao = new findUserDao();
	public String findUser(List listName, List listValue, HttpSession session) {
		List<List> list = fDao.findDao(listName,listValue,session);
		
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
	//历史记录查询
	public String findLeaveHistory(List listName, List listValue, HttpSession session) {
		List<Map<String, Object>> list = fDao.findLeaveHistory(listName,listValue,session);
		
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
					"                                        </tbody>");
			}
		}
		pageContent.append("</table>");
		return pageContent.toString();
	}
	//销假记录查询
	public String find_cutLeave(List listName, List listValue, HttpSession session) {
		List<Map<String, Object>> list = fDao.find_cutLeave(listName,listValue,session);
		
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
					"                                        <td><a onclick=\"cutLeave('"+map.get("id")+"')\" href=\"#\">到岗</a></td>\r\n" + 
					"                                        </tbody>");
			}
		}
		pageContent.append("</table>");
		return pageContent.toString();
	}
	//审批条件查询
	public String find_isleave(List listName, List listValue, HttpSession session) {
		List<Map<String, Object>> list = fDao.find_isleave(listName,listValue,session);
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
	//临时请假记录查询
	public String find_TemporaryHistoryLeave(List listName, List listValue, HttpSession session) {
		List<Map<String, Object>> list = fDao.find_TemporaryHistoryLeave(listName, listValue, session);
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

}
