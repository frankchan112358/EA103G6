package com.permission.model;

public class PermissionVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String permissionNo;
	private String permissionName;
	
	public String getPermissionNo() {
		return permissionNo;
	}
	public void setPermissionNo(String permissionNo) {
		this.permissionNo = permissionNo;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

}
