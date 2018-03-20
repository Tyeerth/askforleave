package com.leave.dao;

import java.util.ArrayList;
import java.util.List;

import com.leave.db.JDBC;
import com.leave.domain.Leave_user;

public class Leave_userDao {
	public ArrayList<Leave_user> selectExcel(){
		ArrayList<Leave_user> arrayList = new ArrayList<Leave_user>();
		Leave_user user = new Leave_user();
		String sql = "select *from leave_user";
		List<List> list = JDBC.list(sql);
		if(list == null) return null;//删除表title
		
		list.remove(0);
		for(List li : list) {
			user.setId((Integer)li.get(0));
            user.setUser_name((String)li.get(1));
            user.setUser_sex((String)li.get(2));
            user.setUser_born_time((String)li.get(3));
            user.setUser_work_time((String)li.get(4));
            user.setUser_origin((String)li.get(5));
            user.setUser_spouse_origin((String)li.get(6));
            user.setUser_work_address((String)li.get(7));
            user.setUser_position((String)li.get(8));
            user.setUser_position_rank((String)li.get(9));
            user.setUser_class_area((String)li.get(10));
            user.setUser_separated((String)li.get(11));
            user.setUser_phone((Integer)li.get(12));
            arrayList.add(user);
		}
		
		return arrayList;
	}
}
