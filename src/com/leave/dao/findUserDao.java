package com.leave.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.leave.config.Config;
import com.leave.db.JDBC;

public class findUserDao {

	private int pageListNum = Integer.parseInt(Config.get("db.pageListNum"));
	
	//人员信息查询
	public List<List> findDao(List listName, List listValue, HttpSession session) {
		//人员信息查询
		String sql = "select *from leave_user where ";
		int pageNum = 1;//分页初始化
		for(int i = 0; i < listValue.size(); i++) {
			if(listName.get(i).equals("pageNum")) {
				pageNum = Integer.parseInt((String)listValue.get(i));//分页
				continue;
			}
			sql += listName.get(i)+" like '%"+listValue.get(i)+"%' and ";
		}
		sql = sql.substring(0, sql.length()-4);
		List<List> list = JDBC.list(sql);
		session.setAttribute("userInfoNum", list.size()-1);//integer,查询后的人员数（表字段-1）条件筛选不到管理员不用-1
		sql += " order by id desc limit "+(pageNum-1)*pageListNum+","+pageListNum;
		list = JDBC.list(sql);
		list.remove(0);//删除表字段名
		return list;
	}
	//历史请假记录查询,显示审核通过的
	public List<Map<String, Object>> findLeaveHistory(List listName, List listValue, HttpSession session) {
		
		String sql = "select *from leave_user a inner join ask_for_leave b on a.id=b.user_id where leave_passed=1 and "; //未通过的不算历史
		//表单数据有值
		String leave_start_day = "";
		String leave_end_day = "";
		int pageNum = 1;//分页初始化
		if(listValue != null) {
			for(int i = 0; i < listValue.size(); i++) {
				if(listName.get(i).equals("select_year")) {
					//过滤
					continue;
				}
				if(listName.get(i).equals("pageNum")) {
					pageNum = Integer.parseInt((String)listValue.get(i));//分页
					continue;
				}
				if(listName.get(i).equals("leave_start_day")) {
					sql += "timestampdiff(day,?,leave_start_day) >= 0 and timestampdiff(day,?,leave_end_day) >= 0 and ";
					leave_start_day = (String)listValue.get(i);
					continue;
				}
				if(listName.get(i).equals("leave_end_day")) {
					sql += "timestampdiff(day,?,leave_start_day) <= 0 and timestampdiff(day,?,leave_end_day) <= 0 and ";
					leave_end_day = (String)listValue.get(i);
					continue;
				}
				sql += listName.get(i)+" like '%"+listValue.get(i)+"%' and ";
			}
		}
		sql = sql.substring(0, sql.length()-4);
		List<Map<String , Object>> list = null;
		if(sql.contains("leave_start_day")) {
			list = JDBC.executeQuery(sql,leave_start_day,leave_start_day,leave_end_day,leave_end_day);
		}
		else list = JDBC.executeQuery(sql);
		int isLeaveNum = list.size();//查找的审核记录数目
		session.setAttribute("leaveHistoryNum", isLeaveNum);//integer,查询后的销假记录数
		/*if(pageNum == 1) {//每次的第一次查询时执行
		}*/
		sql += " order by b.id desc limit "+(pageNum-1)*pageListNum+","+pageListNum;
		if(sql.contains("leave_start_day")) {
			list = JDBC.executeQuery(sql,leave_start_day,leave_start_day,leave_end_day,leave_end_day);
		}
		else list = JDBC.executeQuery(sql);
		return list;
	}
	//销假信息查询
	public List<Map<String, Object>> find_cutLeave(List listName, List listValue, HttpSession session) {
		String sql = "select * from leave_user a inner join ask_for_leave b on a.id=b.user_id where leave_passed=1 and leave_cut=0 and ";

		String leave_end_day2 = "";
		String leave_end_day = "";
		int pageNum = 1;//分页初始化
		if(listValue != null) {
			for(int i = 0; i < listValue.size(); i++) {
				if(listName.get(i).equals("pageNum")) {
					pageNum = Integer.parseInt((String)listValue.get(i));//分页
					continue;
				}
				if(listName.get(i).equals("leave_end_day")) {
					sql += "timestampdiff(day,?,leave_end_day) >= 0 and ";
					leave_end_day = (String)listValue.get(i);
					continue;
				}
				if(listName.get(i).equals("leave_end_day2")) {
					sql += "timestampdiff(day,?,leave_end_day) <= 0 and ";
					leave_end_day2 = (String)listValue.get(i);
					continue;
				}
				sql += listName.get(i)+" like '%"+listValue.get(i)+"%' and ";
			}
		}
		sql = sql.substring(0, sql.length()-4);
		List<Map<String , Object>> list = null;
		if(sql.contains("leave_end_day")) {
			list = JDBC.executeQuery(sql,leave_end_day,leave_end_day2);
		}
		else list = JDBC.executeQuery(sql);
		int isLeaveNum = list.size();//查找的审核记录数目
		session.setAttribute("cutLeaveNum", isLeaveNum);//integer,查询后的销假记录数
		/*if(pageNum == 1) {//每次的第一次查询时执行
		}*/
		sql += " order by b.id desc limit "+(pageNum-1)*pageListNum+","+pageListNum;
		if(sql.contains("leave_end_day")) {
			list = JDBC.executeQuery(sql,leave_end_day,leave_end_day2);
		}
		else list = JDBC.executeQuery(sql);
		return list;
	}
	//请假审核信息查询
	public List<Map<String, Object>> find_isleave(List listName, List listValue, HttpSession session) {
		String sql = "select * from leave_user a inner join ask_for_leave b on a.id=b.user_id where leave_passed=0 and ";
		
		String leave_start_day = "";
		String leave_end_day = "";
		int pageNum = 1;//分页初始化
		if(listValue != null) {
			for(int i = 0; i < listValue.size(); i++) {
				if(listName.get(i).equals("pageNum")) {
					pageNum = Integer.parseInt((String)listValue.get(i));//分页
					continue;
				}
				if(listName.get(i).equals("leave_start_day")) {
					sql += "timestampdiff(day,?,leave_start_day) >= 0 and timestampdiff(day,?,leave_end_day) >= 0 and ";
					leave_start_day = (String)listValue.get(i);
					continue;
				}
				if(listName.get(i).equals("leave_end_day")) {
					sql += "timestampdiff(day,?,leave_start_day) <= 0 and timestampdiff(day,?,leave_end_day) <= 0 and ";
					leave_end_day = (String)listValue.get(i);
					continue;
				}
				sql += listName.get(i)+" like '%"+listValue.get(i)+"%' and ";
			}
		}
		sql = sql.substring(0, sql.length()-4);
		List<Map<String , Object>> list = null;
		if(sql.contains("leave_start_day")) {
			list = JDBC.executeQuery(sql,leave_start_day,leave_start_day,leave_end_day,leave_end_day);
		}
		else list = JDBC.executeQuery(sql);
		int isLeaveNum = list.size();//查找的审核记录数目
		session.setAttribute("isLeaveNum", isLeaveNum);//integer,查询后的销假记录数
		/*if(pageNum == 1) {//每次的第一次查询时执行
		}*/
		sql += " order by b.id desc limit "+(pageNum-1)*pageListNum+","+pageListNum;
		if(sql.contains("leave_start_day")) {
			list = JDBC.executeQuery(sql,leave_start_day,leave_start_day,leave_end_day,leave_end_day);
		}
		else list = JDBC.executeQuery(sql);
		return list;
	}
	//判断日期是否在范围之内，包含边界
	public static boolean belongCalendar(Date time, Date from, Date to) {
        Calendar date = Calendar.getInstance();
        date.setTime(time);

        Calendar after = Calendar.getInstance();
        after.setTime(from);

        Calendar before = Calendar.getInstance();
        before.setTime(to);

        if (date.before(after) || date.after(before)) {//包含边界，lu修订版
            return false;
        } else {
            return true;
        }
        /*if (date.after(after) && date.before(before)) {//不包含边界，1月1号无法比较
            return true;
        } else {
            return false;
        }*/
    }
	//临时请假历史记录
	public List<Map<String, Object>> find_TemporaryHistoryLeave(List listName, List listValue, HttpSession session) {
		String sql = "select * from temporary_leave_info a inner join leave_user b on a.user_id=b.id where 1=1 and ";
		
		String leave_start_day = "";
		String leave_end_day = "";
		int pageNum = 1;//分页初始化
		if(listValue != null) {
			for(int i = 0; i < listValue.size(); i++) {
				if(listName.get(i).equals("pageNum")) {
					pageNum = Integer.parseInt((String)listValue.get(i));//分页
					continue;
				}
				if(listName.get(i).equals("leave_start_day")) {
					leave_start_day = (String)listValue.get(i);
					continue;
				}
				if(listName.get(i).equals("leave_end_day")) {
					leave_end_day = (String)listValue.get(i);
					continue;
				}
				sql += listName.get(i)+" like '%"+listValue.get(i)+"%' and ";
			}
		}
		sql = sql.substring(0, sql.length()-4);
		//System.out.println(sql);
		List<Map<String , Object>> liNum = JDBC.executeQuery(sql);
		int isLeaveNum = liNum.size();//查找的审核记录数目
		//System.out.println(isLeaveNum);
		if(pageNum == 1) {//每次的第一次查询时执行,记录总数据数目
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	        boolean b = false; boolean b2 = false;
			if(liNum != null) {
				for(int i = 0; i < liNum.size(); i++) {
					Map map = liNum.get(i);
					try {
					Date time1  = sdf.parse((String)map.get("leave_date"));
					b = belongCalendar(time1,sdf.parse(leave_start_day),sdf.parse(leave_end_day));
					if(!b) {
						liNum.remove(map);
						i--;
						isLeaveNum--;//数量-1
						if(liNum.size()==0) break;//防止死循环
						continue;
					}
					}catch(Exception e) {
						System.out.println("没有日期条件或日期范围判断出错findUserDao.java");
					}
				}
			}
		}
		sql += " order by a.id desc limit "+(pageNum-1)*pageListNum+","+pageListNum;
		List<Map<String, Object>> list = JDBC.executeQuery(sql);
		//查询后进行日期筛选(在日期范围内)
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        boolean b = false; boolean b2 = false;
		if(list != null) {
			for(int i = 0; i < list.size(); i++) {
				Map map = list.get(i);
				try {
				Date time1  = sdf.parse((String)map.get("leave_date"));
				b = belongCalendar(time1,sdf.parse(leave_start_day),sdf.parse(leave_end_day));
				if(!b) {
					list.remove(map);
					i--;//删除后从当前位置开始不能加1(消除影响)
					continue;
				}
				}catch(Exception e) {
					System.out.println("没有日期条件或日期范围判断出错findUserDao.java");
				}
			}
		}
		session.setAttribute("temporaryHistoryNum", isLeaveNum);//integer,查询后的人员数
		return list;
	}   
	//根据id显示相应用户的历史请假信息
	public static List<Map<String, Object>> idleavedInfoHistory(int id){
		String sql = "select *from leave_user a inner join ask_for_leave b on a.id=b.user_id where "
				+ "leave_passed=1 and a.id=? order by b.id desc"; //未通过的不算历史
		List<Map<String , Object>> list = JDBC.executeQuery(sql,id);
		return list;
	}
	
	public static void main(String[] args) throws ParseException {
		 //打印测试
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date time1  = sdf.parse("2013-02-13");
        Date from = sdf.parse("2013-01-29");
        Date to= sdf.parse("2013-02-44");//这。。。都能测试。。
        System.out.println(belongCalendar(time1,from,to));
	}

}
