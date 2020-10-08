package com.userpermission.model;

public class UserPermissionVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String userNo;
	private String permissionNo;
	private Integer readable;
	private Integer permissionEdit;

	public UserPermissionVO() {

	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getPermissionNo() {
		return permissionNo;
	}

	public void setPermissionNo(String permissionNo) {
		this.permissionNo = permissionNo;
	}

	public Integer getReadable() {
		return readable;
	}

	public void setReadable(Integer readable) {
		this.readable = readable;
	}

	public Integer getPermissionEdit() {
		return permissionEdit;
	}

	public void setPermissionEdit(Integer permissionEdit) {
		this.permissionEdit = permissionEdit;
	}

}
