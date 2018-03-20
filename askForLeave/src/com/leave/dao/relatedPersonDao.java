package com.leave.dao;

import java.util.List;
import java.util.Map;

import com.leave.db.JDBC;

public class relatedPersonDao {

	//相关人员页面数据展示
	public List<Map<String, Object>> listRelated(int leave_user_id, int pageNum) {
		
		String sql = "select * from leave_related a inner join leave_user b on a.related_leader_id=b.id where leave_user_id=?";
		
		return JDBC.executeQuery(sql, leave_user_id);
	}

	public boolean deleteRelated(int user_id, int user_related_id) {
		//其实应该通过传来的该条记录的id值直接删除就好了，不用这样稍稍麻烦
		String sql = "delete from leave_related where leave_user_id=? and related_leader_id=?";
		return JDBC.executeUpdate(sql, user_id,user_related_id);
	}

}
