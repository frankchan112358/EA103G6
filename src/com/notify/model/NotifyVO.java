package com.notify.model;

import java.sql.Timestamp;

public class NotifyVO implements java.io.Serializable{
	
	private String userNo;
	private String notifyContent;
	private Timestamp notifyDate;
	
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getNotifyContent() {
		return notifyContent;
	}
	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}
	public Timestamp getNotifyDate() {
		return notifyDate;
	}
	public void setNotifyDate(Timestamp notifyDate) {
		this.notifyDate = notifyDate;
	}

}
