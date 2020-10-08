package com.permission.model;

import java.util.List;

public class PermissionService {
	private PermissionDAO_interface dao;
	
	public PermissionService() {
		dao=new PermissionDAO();
	}

	
	public List<PermissionVO> getAll(){
		return dao.getAll();
	}
}
