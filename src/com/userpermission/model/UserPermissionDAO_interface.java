package com.userpermission.model;

import java.util.List;


public interface UserPermissionDAO_interface {

	public void insert(UserPermissionVO userPermissionVO);
	public void update(UserPermissionVO userPermissionVO);
	public UserPermissionVO findByFK(String userNo,String permissionNo);
	public List<UserPermissionVO> getAll();
}
