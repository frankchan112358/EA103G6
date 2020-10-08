package com.notify.model;

import java.sql.Timestamp;

public class NotifyVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String userNo;
	private String notifyContent;
	private Timestamp notifyDate;

	public NotifyVO() {

	}

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
