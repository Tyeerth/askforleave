package com.leave.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.leave.config.Config;
import com.leave.db.JDBC;
import com.leave.util.DateL;


public class pageListDao {

	private int pageListNum = Integer.parseInt(Config.get("db.pageListNum"));
	public List<List> listPage(int pageNum) {
		String sql = "select * from leave_user order by id desc limit "+(pageNum-1)*pageListNum+","+pageListNum;
		List<List> list = JDBC.list(sql);
		if(list == null) return null;
		list.remove(0);//删表的字段名
		//list.remove(list.size()-1);//不展示管理员
		
		return list;
	}

	public List<List> updateListPage(int id) {
		String sql = "select * from leave_user where id=?";
		List<List> list = JDBC.list(sql,id);
		if(list == null) return null;
		list.remove(0);//删表的字段名
		
		return list;
	}
	//请假记录，审核
	public List<Map<String, Object>> isLeave(int pageNum) {
		String sql = "select * from leave_user a inner join ask_for_leave b on a.id=b.user_id where leave_passed=0 order by b.id desc limit "+(pageNum-1)*pageListNum+","+pageListNum;
		List<Map<String, Object>> list = JDBC.executeQuery(sql);
		return list;
	}
	//历史请假记录
	public List<Map<String, Object>> historyLeave(int pageNum) {
		String sql = "select * from leave_user a inner join ask_for_leave b on a.id=b.user_id where leave_passed!=0 order by b.id desc limit "+(pageNum-1)*pageListNum+","+pageListNum;
		List<Map<String, Object>> list = JDBC.executeQuery(sql);
		return list;
	}
	//销假查询
	public List<Map<String, Object>> cuthistoryLeave(int pageNum) {
		String sql = "select * from leave_user a inner join ask_for_leave b on a.id=b.user_id where leave_passed=1 and leave_cut=0 order by b.id desc limit "+(pageNum-1)*pageListNum+","+pageListNum;
		List<Map<String, Object>> list = JDBC.executeQuery(sql);
		return list;
	}
	//销假(历史记录的更新)
	public boolean updatehistoryLeave(int id, String leave_end_day, String leave_cut_remark) {
			
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss");
		/*//今天日期		
		Date date = new Date();
		String today = format.format(date);*/
		
		//到岗日期
		String end_day = leave_end_day;
		
		//请假天数的统计，开始日期
		String leave_day="";
		String sql = "select leave_start_day from ask_for_leave where id=?";
		List<List> list = JDBC.list(sql, id);
		String start_day = (String) list.get(1).get(0);
		try {
			Date d1=format.parse(start_day);
			Date d2=format.parse(end_day); 
			int day = DateL.daysBetween(d1,d2)+1;
			leave_day = String.valueOf(day);
		} catch (ParseException e) {
			System.out.println("到岗日期差计算错误");
		}
		
		//将请假的最后日期改成今天
		
		sql = "update ask_for_leave set leave_day=?,leave_end_day=?,leave_cut_remark=?,leave_cut=1 where id=?";
		return JDBC.executeUpdate(sql, leave_day,end_day,leave_cut_remark,id);
	}
	//人员信息总数
	public String countAll() {
		String sql = "select count(*) as Num from leave_user";
		List<List> list = JDBC.list(sql);
		if(list == null) return null;
		list.remove(0);//title
		long num = (long)list.get(0).get(0)-1;
		return String.valueOf(num);
	}
	//未审批人数
	public String countAllLeave() {
		String sql = "select count(*) as Num from ask_for_leave where leave_passed=0";
		List<List> list = JDBC.list(sql);
		if(list == null) return null;
		list.remove(0);//title
		return String.valueOf(list.get(0).get(0));
	}
	//历史记录数
	public String countAllHistory() {
		String sql = "select count(*) as Num from ask_for_leave where leave_passed!=0";
		List<List> list = JDBC.list(sql);
		if(list == null) return null;
		list.remove(0);//title
		return String.valueOf(list.get(0).get(0));
	}
	//销假记录数(到岗)
	public String countAllcutHistory() {
		String sql = "select count(*) as Num from ask_for_leave where leave_passed=1 and leave_cut=0";
		List<List> list = JDBC.list(sql);
		if(list == null) return null;
		list.remove(0);//title
		return String.valueOf(list.get(0).get(0));
	}
	//查询前三天所有临时请假信息
	public List<Map<String, Object>> temporaryLeave() {
		String  sql = "select * from temporary_leave a inner join leave_user b on a.user_id=b.id where message_date=?";
		//获取三天内的日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss");
		Date date = new Date();
		Calendar now = Calendar.getInstance(); 
		//前天
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 2);
    	String day=format.format(now.getTime());
    	List<Map<String, Object>> list1 = JDBC.executeQuery(sql, day);
    	//昨天
    	now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
        day=format.format(now.getTime());
        List<Map<String, Object>> list2 = JDBC.executeQuery(sql, day);
		//今天
        day=format.format(date);
		List<Map<String, Object>> list3 = JDBC.executeQuery(sql, day);
		
		//合并
		list1.addAll(list2);list1.addAll(list3);//为啥list3.addAll(list2);list3.addAll(list1);不行
		
		return list1;
	}
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss");
		Date date = new Date();
		System.out.println(format.format(date));
		Calendar now = Calendar.getInstance();  
        now.setTime(date);  
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 2);  
		System.out.println(format.format(now.getTime()));
	}

	public List<Map<String, Object>> temporaryHistoryLeave(int pageNum) {
		String  sql = "select * from temporary_leave_info a inner join leave_user b on a.user_id=b.id order by a.id desc limit "+(pageNum-1)*pageListNum+","+pageListNum;
		return JDBC.executeQuery(sql);
	}

	public String countAllTemporaryHistory() {
		String sql = "select count(*) from temporary_leave_info a inner join leave_user b on a.user_id=b.id";
		List<List> list = JDBC.list(sql);
		if(list == null) return null;
		list.remove(0);//title
		return String.valueOf(list.get(0).get(0));
	}
}
