package com.userpermission.model;

import java.util.List;

public class UserPermissionService {
	private UserPermissionDAO_interface dao;
	
	public UserPermissionService() {
		dao=new UserPermissionDAO();
	}
	
	public UserPermissionVO addUserPermission(String userNo,String permissionNo,Integer readable,Integer permissionEdit) {
		UserPermissionVO userPermissionVO =new UserPermissionVO();
		
		userPermissionVO.setUserNo(userNo);
		userPermissionVO.setPermissionNo(permissionNo);
		userPermissionVO.setReadable(readable);
		userPermissionVO.setPermissionEdit(permissionEdit);
		
		dao.insert(userPermissionVO);
		
		return userPermissionVO;
	}
	
	public UserPermissionVO updateUserPermission(String userNo,String permissionNo,Integer readable,Integer permissionEdit) {
		UserPermissionVO userPermissionVO =new UserPermissionVO();
		
		userPermissionVO.setUserNo(userNo);
		userPermissionVO.setPermissionNo(permissionNo);
		userPermissionVO.setReadable(readable);
		userPermissionVO.setPermissionEdit(permissionEdit);
		dao.update(userPermissionVO);
		
		return userPermissionVO;
	}
	
	public UserPermissionVO getOneUserPermission(String userNo, String permissionNo) {
		return dao.findByFK(userNo,permissionNo);
	}
	
	public List<UserPermissionVO> getAll(){
		return dao.getAll();
	}
	
	public List<UserPermissionVO> getAllByThemselves(String userNo){
		return dao.getAllByThemselves(userNo);
	}

}
