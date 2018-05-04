package com.leave.dao;

import java.util.List;
import java.util.Map;

import com.leave.db.JDBC;

public class updateRuleDao {
	
	public boolean addRule(List listName, List listValue) {
		StringBuffer sql = new StringBuffer("insert into leave_rule(");
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
		return JDBC.executeUpdate(sql.toString());
	}
	
	public List<Map<String, Object>> listRule() {
		String sql="select *from leave_rule";
		return JDBC.executeQuery(sql);
	}

	public boolean deleteRule(int id) {
		String sql = "delete from leave_rule where id=?";
		return JDBC.executeUpdate(sql, id);
	}

	public List<Map<String, Object>> updateRuleList(int id) {
		
		String sql = "select * from leave_rule where id=?";
		List<Map<String, Object>> list = JDBC.executeQuery(sql, id);
		deleteRule(id);//删除原来的规则
		return list;
	}
	public boolean updateRule(List listName, List listValue) {
		return addRule(listName, listValue);//简单点，相当于删除原来的规则重新创建一个，见方法updateRuleList
	}
}
