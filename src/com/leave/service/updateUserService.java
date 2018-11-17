package com.leave.service;

import java.util.List;

import com.leave.dao.updateUserDao;

public class updateUserService {
	updateUserDao upDao = new updateUserDao();
	public boolean insertUser(List list) {		
		return upDao.insertUser(list);
	}

	public void updatetUser(List list) {
		upDao.updateUser(list);
	}

	public void deletetUser(int id) {
		upDao.deleteUser(id);
		
	}

	public boolean insertUserMore(List<List> list) {
		return upDao.insertUserMore(list);
	}

}
