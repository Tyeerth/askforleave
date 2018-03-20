package com.leave.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.leave.db.JDBC;

public class printInfoDao {

	public List<Map<String, Object>> print(String s_id) {
		int id = Integer.parseInt(s_id);
		String sql = "select * from ask_for_leave a inner join leave_user b on a.user_id=b.id where a.id=?";
		return JDBC.executeQuery(sql, id);
	}
	//统计请假次数等数据
	//已请事假__a__次，累计__b__天；已请病假__c__次，累计_d___天；
	//已请轮休（分时段休假）__e__次，累计__f__天。
	//要改成查找的事每年的数据才行
	public List<List> LeaveInfo(int user_id) {//user的id
		//查找user_id
		int a=0,b=0,c=0,d=0,e=0,f=0,A=0,B=0,C=0,D=0,E=0,F=0;
		
		//获得本年年份
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String leave_start_day="";
		
		//事假数据统计
		String sql = "select count(*) as a from ask_for_leave where leave_kind='事假' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";//leave_passed=1表示审核通过的历史记录
		List<List> list = JDBC.list(sql,user_id);
		if(list == null) {
			a = 0;//请事假0次
			//则b=0
			b=0;
		}
		else {
			a = Integer.parseInt(String.valueOf(list.get(1).get(0)));
			sql = "select leave_day,leave_start_day from ask_for_leave where leave_kind='事假' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
			list = JDBC.list(sql, user_id);
			String b2="0";
			//事假累计天数
			for(int i = 1; i < list.size();i++) {//i从1开始
				//leave_start_day = (String)list.get(i).get(1);
				b2=(String)list.get(i).get(0);
				b += Integer.parseInt(b2);
			}
		}
		//病假数据统计
		sql = "select count(*) as a from ask_for_leave where leave_kind='病假' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
		list = JDBC.list(sql,user_id);
		if(list == null) {
			c = 0;//请病假0次
			//则b=0
			d=0;
		}
		else {
			c = Integer.parseInt(String.valueOf(list.get(1).get(0)));
			sql = "select leave_day,leave_start_day from ask_for_leave where leave_kind='病假' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
			list = JDBC.list(sql, user_id);
			String d2="0";
			//事假累计天数
			for(int i = 1; i < list.size();i++) {//i从1开始
				//leave_start_day = (String)list.get(i).get(1);
					d2=(String)list.get(i).get(0);
					d += Integer.parseInt(d2);	
			}
		}
		//轮休数据统计
		sql = "select count(*) as a from ask_for_leave where leave_kind='轮休' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
		list = JDBC.list(sql,user_id);
		if(list == null) {
			e = 0;//请病假0次
			//则b=0
			f=0;
		}
		else {
			e = Integer.parseInt(String.valueOf(list.get(1).get(0)));
			sql = "select leave_day,leave_start_day from ask_for_leave where leave_kind='轮休' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
			list = JDBC.list(sql, user_id);
			String f2="0";
			//事假累计天数
			for(int i = 1; i < list.size();i++) {//i从1开始
					f2=(String)list.get(i).get(0);
					f += Integer.parseInt(f2);	
			}
		}
		//休假数据统计
		sql = "select count(*) as a from ask_for_leave where leave_kind='休假' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
		list = JDBC.list(sql,user_id);
		if(list == null) {
			A = 0;//请病假0次
			//则b=0
			B=0;
		}
		else {
			A = Integer.parseInt(String.valueOf(list.get(1).get(0)));
			sql = "select leave_day,leave_start_day from ask_for_leave where leave_kind='休假' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
			list = JDBC.list(sql, user_id);
			String B2="0";
			//事假累计天数
			for(int i = 1; i < list.size();i++) {//i从1开始
				leave_start_day = (String)list.get(i).get(1);
				if(leave_start_day.contains(year)) {
					B2=(String)list.get(i).get(0);
					B += Integer.parseInt(B2);	
				}
			}
		}
		//产假数据统计
		sql = "select count(*) as a from ask_for_leave where leave_kind='产假' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
		list = JDBC.list(sql,user_id);
		if(list == null) {
			C = 0;//请病假0次
			//则b=0
			D=0;
		}
		else {
			C = Integer.parseInt(String.valueOf(list.get(1).get(0)));
			sql = "select leave_day,leave_start_day from ask_for_leave where leave_kind='产假' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
			list = JDBC.list(sql, user_id);
			String D2="0";
			//事假累计天数
			for(int i = 1; i < list.size();i++) {//i从1开始
					D2=(String)list.get(i).get(0);
					D += Integer.parseInt(D2);	
			}
		}
		//丧假数据统计
		sql = "select count(*) as a from ask_for_leave where leave_kind='丧假' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
		list = JDBC.list(sql,user_id);
		if(list == null) {
			E = 0;//请病假0次
			//则b=0
			F=0;
		}
		else {
			E = Integer.parseInt(String.valueOf(list.get(1).get(0)));
			sql = "select leave_day,leave_start_day from ask_for_leave where leave_kind='丧假' and user_id=? and leave_passed=1 and leave_start_day like '%"+year+"%'";
			list = JDBC.list(sql, user_id);
			String F2="0";
			//事假累计天数
			for(int i = 1; i < list.size();i++) {//i从1开始
					F2=(String)list.get(i).get(0);
					F += Integer.parseInt(F2);	
			}
		}
		//休假数据统计
		List reList = new ArrayList<>();
		reList.add(a);reList.add(b);reList.add(c);reList.add(d);reList.add(e);reList.add(f);
		reList.add(A);reList.add(B);reList.add(C);reList.add(D);reList.add(E);reList.add(F);
		return reList;
	}
	
	//根据请假人员 的信息（打印页面）id查找统计数据
	public List<List> selectInfo(String id) {
		//查找user_id
		String sql = "select * from ask_for_leave where id=?";
		List<Map<String, Object>> listMap = JDBC.executeQuery(sql, id);
		if(listMap==null) return null;
		int user_id = (Integer)listMap.get(0).get("user_id");
		
		return LeaveInfo(user_id);
	}

}
