package com.leave.dao;

import java.util.List;

import com.leave.db.JDBC;

public class updateUserDao {

	public boolean insertUser(List list) {
		String sql = "insert into leave_user(user_name,user_sex,user_born_time,user_work_time,user_origin,user_spouse_origin,user_work_address,user_position,user_position_rank,user_class_area,user_separated,user_phone,user_max_day) value(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object user_origin = (String)list.get(4)+(String)list.get(5)+(String)list.get(6);
		Object user_spouse_origin = (String)list.get(7)+(String)list.get(8)+(String)list.get(9);
		return JDBC.executeUpdate(sql,list.get(0),list.get(1),list.get(2),list.get(3),user_origin,user_spouse_origin,list.get(10),list.get(11),list.get(12),list.get(13),list.get(14),list.get(15),list.get(16));
		//System.out.println(b);
	}

	public void updateUser(List list) {
		//System.out.println(list);
		String sql = "update leave_user set user_name=?,user_sex=?,user_born_time=?,user_work_time=?,user_origin=?,user_spouse_origin=?,user_work_address=?,user_position=?,user_position_rank=?,user_class_area=?,user_separated=?,user_phone=?,user_max_day=? where id=?";
		Object user_origin = (String)list.get(5)+(String)list.get(6)+(String)list.get(7);
		Object user_spouse_origin = (String)list.get(8)+(String)list.get(9)+(String)list.get(10);
		JDBC.executeUpdate(sql,list.get(1),list.get(2),list.get(3),list.get(4),user_origin,user_spouse_origin,list.get(11),list.get(12),list.get(13),list.get(14),list.get(15),list.get(16),list.get(17),list.get(0));
	}

	public void deleteUser(int id) {
		//删除该用户同时删除该用户的其他相关数据，如请假数据(和历史数据)
		String sql = "delete from ask_for_leave where user_id=?";
		JDBC.executeUpdate(sql, id);
		//sql = "delete from leave_history where id=?";
		//JDBC.executeUpdate(sql, id);
		//删除该用户
		sql = "delete from leave_user where id=?";
		JDBC.executeUpdate(sql, id);
	}
	//批量插入
	public boolean insertUserMore(List<List> listMore) {
		boolean b=false;
		//System.out.println(listMore);
		if(listMore!=null) {
			for(List list :listMore) {//一行一行尝试导入到数据库
				String sql = "insert into leave_user(user_name,user_sex,user_born_time,user_work_time,user_origin,user_spouse_origin,user_work_address,user_position,user_position_rank,user_class_area,user_separated,user_phone,user_max_day) value(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				b = JDBC.executeUpdate(sql,list.get(0),list.get(1),list.get(2),list.get(3),list.get(4),list.get(5),list.get(6),list.get(7),list.get(8),list.get(9),list.get(10),list.get(11),list.get(12));
			}
		}
		return b;
	}
}
