package com.websocketchat.model;

public class ChatUser implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userNo;
	private String userName;
	
	public ChatUser(String userNo,String userName) {
		this.userNo=userNo;
		this.userName=userName;
	}
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
