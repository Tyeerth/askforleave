package com.leave.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.leave.db.JDBC;

/**
 * 请假相关动作
 * @author lu
 *
 */
public class getUserInfoDao {
	//name查询user
	public List<Map<String, Object>> getUserInfo(String user_name) {
		String sql = "select * from leave_user where user_name=?";
		List<Map<String, Object>> list = JDBC.executeQuery(sql, user_name);
		//if(list == null) return null;
		return list;
	}
	//id查询user
	public List<Map<String, Object>> getUserInfo(int id) {
		String sql = "select * from leave_user where id=?";
		List<Map<String, Object>> list = JDBC.executeQuery(sql, id);
		//if(list == null) return null;
		return list;
	}
	//请假动作信息录入
	//字符型数字，可以直接插入mysql数据库int类型中
	public int insertLeave(List listName, List listValue) {
		
		StringBuffer sql = new StringBuffer("insert into ask_for_leave(");
		for(int i = 0; i < listName.size(); i++) {
			sql.append(listName.get(i)+",");
		}
		sql = new StringBuffer(sql.substring(0, sql.length()-1));
		sql.append(") value('");
		for(int i = 0; i < listValue.size(); i++) {
			sql.append(listValue.get(i)+"','");
		}
		sql = new StringBuffer(sql.substring(0, sql.length()-2));
		sql.append(")");
		//System.out.println(sql);
		//mysql中如果有一个默认值，且!!不为空not null,java中执行不包含该项的insert语句会不成功，但是不报错误信息！！
		//但是该语句放到mysql中直接执行却是正确的！！！(~本就是正确的，不知java中调包执行为啥会报错)
		//取消not null才行
		JDBC.executeUpdate(sql.toString());
		String sql2 = "select max(id) from ask_for_leave";
		List<List> list = JDBC.list(sql2);
		list.remove(0);//删除表字段名
		int id = (Integer)list.get(0).get(0);
		return id;
	}
	public String getPersonKind(int id) {
		String sql = "select person_kind from ask_for_leave where id=?";
		List<List> list = JDBC.list(sql,id);
		//System.out.println(list);
		list.remove(0);//删除表字段名
		String person_kind = (String)list.get(0).get(0);
		return person_kind;
	}
	public boolean addRelated(int leave_user_id, int related_leader_id) {
		String sql = "insert into leave_related(leave_user_id,related_leader_id) value(?,?)";
		return JDBC.executeUpdate(sql, leave_user_id,related_leader_id);
	}
	public int findLeaveNum(int user_id) {
		String sql = "select count(*) as day from ask_for_leave where user_id=?  and leave_kind='轮休'";
		List<List> list = JDBC.list(sql, user_id);
		if(list!=null) return Integer.parseInt(String.valueOf(list.get(1).get(0)));
		return 0;
	}
	//统计本年用户请假的所有天数，事假
	public int findLeaveDay(int user_id) {//跨年份请假可能会有问题
		//获得本年年份
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String leave_end_day="";
		String sql = "select leave_day,leave_start_day from ask_for_leave where user_id=? and leave_passed=1 and leave_kind='事假'";
		List<List> list = JDBC.list(sql, user_id);
		if(list!=null) {
			list.remove(0);//删除字段名
			int day=0;
			for(List lDay : list) {
				leave_end_day = (String)lDay.get(1);
				leave_end_day = leave_end_day.substring(0, 4);
				if(leave_end_day.equals(year)) {
					day+=Integer.parseInt(String.valueOf(lDay.get(0)));
				}
			}
			return day;
		}
		return 0;
	}
	//统计本年用户请假的所有天数，病假
		public int findLeaveDay2(int user_id) {//跨年份请假可能会有问题
			//获得本年年份
			Calendar cal = Calendar.getInstance();
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String leave_end_day="";
			String sql = "select leave_day,leave_start_day from ask_for_leave where user_id=? and leave_passed=1 and leave_kind='病假'";
			List<List> list = JDBC.list(sql, user_id);
			if(list!=null) {
				list.remove(0);//删除字段名
				int day=0;
				for(List lDay : list) {
					leave_end_day = (String)lDay.get(1);
					leave_end_day = leave_end_day.substring(0, 4);
					if(leave_end_day.equals(year)) {
						day+=Integer.parseInt(String.valueOf(lDay.get(0)));
					}
				}
				return day;
			}
			return 0;
		}
		//判断前一年又没有请过产假
		public boolean findLeaveDay3(int user_id) {//跨年份请假可能会有问题
			//获得本年年份
			Calendar cal = Calendar.getInstance();
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String leave_end_day="";
			String sql = "select leave_start_day from ask_for_leave where user_id=? and leave_passed=1 and leave_kind='产假'";
			List<List> list = JDBC.list(sql, user_id);
			if(list!=null) {
				return true;
			}
			return false;
		}
		//统计本年用户请假的所有天数，丧假
		public int findLeaveDay4(int user_id) {//跨年份请假可能会有问题
			//获得本年年份
			Calendar cal = Calendar.getInstance();
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String leave_end_day="";
			String sql = "select leave_day,leave_start_day from ask_for_leave where user_id=? and leave_passed=1 and leave_kind='丧假'";
			List<List> list = JDBC.list(sql, user_id);
			if(list!=null) {
				list.remove(0);//删除字段名
				int day=0;
				for(List lDay : list) {
					leave_end_day = (String)lDay.get(1);
					leave_end_day = leave_end_day.substring(0, 4);
					if(leave_end_day.equals(year)) {
						day+=Integer.parseInt(String.valueOf(lDay.get(0)));
					}
				}
				return day;
			}
			return 0;
		}
		//休假
		public int findLeaveDay5(int user_id) {//跨年份请假可能会有问题
			//获得本年年份
			Calendar cal = Calendar.getInstance();
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String leave_end_day="";
			String sql = "select leave_day,leave_start_day from ask_for_leave where user_id=? and leave_passed=1 and leave_kind='休假'";
			List<List> list = JDBC.list(sql, user_id);
			if(list!=null) {
				list.remove(0);//删除字段名
				int day=0;
				for(List lDay : list) {
					leave_end_day = (String)lDay.get(1);
					leave_end_day = leave_end_day.substring(0, 4);
					if(leave_end_day.equals(year)) {
						day+=Integer.parseInt(String.valueOf(lDay.get(0)));
					}
				}
				return day;
			}
			return 0;
		}
	//本年度小于3天的不计算的请事假天数
	public int findLeaveDayLess3Day(int user_id) {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String leave_end_day="";
		String sql = "select leave_day,leave_start_day from ask_for_leave where user_id=? and leave_passed=1 and leave_kind='事假'";
		List<List> list = JDBC.list(sql, user_id);
		if(list!=null) {
			list.remove(0);//删除字段名
			int day=0;
			int day2=0;
			for(List lDay : list) {
				leave_end_day = (String)lDay.get(1);
				leave_end_day = leave_end_day.substring(0, 4);
				if(leave_end_day.equals(year)) {
					day2=Integer.parseInt(String.valueOf(lDay.get(0)));
					if(day2>3) day+=day2;
				}
			}
			return day;
		}
		return 0;
	}
	//获取所有规则
	public List<Map<String, Object>> getRuleInfo() {
		String sql = "select * from leave_rule";
		return JDBC.executeQuery(sql);
	}

}
