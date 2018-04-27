package com.leave.dao;

import java.util.List;
import java.util.Map;

import com.leave.db.JDBC;

public class relatedPersonDao {

	//相关人员页面数据展示(个人)
	public List<Map<String, Object>> listRelated(int leave_user_id, int pageNum) {
		
		String sql = "select * from leave_related a inner join leave_user b on a.related_leader_id=b.id where leave_user_id=?";
		
		return JDBC.executeQuery(sql, leave_user_id);
	}
	//相关人员页面数据展示(批量,操作次数不会太多分离出来)
	public List<Map<String, Object>> listRelated(String[] ids) {
		
		String sql = "select related_leader_id from leave_related where leave_user_id=?";
		List<List> list = null;
		//初始id集合，取交集
		List<List> listID = JDBC.list(sql, ids[0]);
		if(listID != null) listID.remove(0);//删除字段名
		for(int i=1; i < ids.length; i++){//ids从1开始，0作为比较的标准
			list = JDBC.list(sql, ids[i]);
			listID.retainAll(list);//取公共的id（相关人员/领导）
		}
		sql = "select *from leave_user where id in (";
		if(listID != null){
			for(int i = 0; i < listID.size(); i++){
				sql += listID.get(i).get(0)+",";
			}
			sql = sql.substring(0,sql.length()-1);
		}
		sql += ")";
		System.out.println("共同领导信息查询"+sql);
		return JDBC.executeQuery(sql);
	}
	public boolean deleteRelated(int user_id, int user_related_id) {
		//其实应该通过传来的该条记录的id值直接删除就好了，不用这样稍稍麻烦
		String sql = "delete from leave_related where leave_user_id=? and related_leader_id=?";
		return JDBC.executeUpdate(sql, user_id,user_related_id);
	}

}
