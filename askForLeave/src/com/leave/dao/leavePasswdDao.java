package com.leave.dao;

import java.util.List;
import java.util.Map;

import com.leave.db.JDBC;

public class leavePasswdDao {

	/**
	 * 审核策略：修改请假表ask_for_leave的审核字段leave_passed
	 * 0：未审核
	 * 1：审核通过
	 * 2：审核未通过
	 */
	//审核通过,返回发短信用的电话号码
	public List<Map<String, Object>> pass(int id) {
		//审核通过
		String sql = "update ask_for_leave set leave_passed=1 where id=?";
		JDBC.executeUpdate(sql, id);
		//获取发短信用的号码
		sql = "select * from ask_for_leave b inner join leave_user a on a.id=b.user_id where b.id=?";
		List<Map<String, Object>> list = JDBC.executeQuery(sql, id);
		return list;
	}
	//审核未通过
	public void unpass(int id) {
		String sql = "update ask_for_leave set leave_passed=2 where id=?";
		JDBC.executeUpdate(sql, id);
	}
	//查询个人相关领导的信息（手机号码）
	public List<Map<String, Object>> getLeaderInfo(int id) {
		String sql = "select * from leave_related a inner join leave_user b on a.related_leader_id=b.id where leave_user_id=?";
		return JDBC.executeQuery(sql, id);
	}

}
