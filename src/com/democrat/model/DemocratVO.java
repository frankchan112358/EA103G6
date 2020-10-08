package com.democrat.model;

public class DemocratVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String democratNo;
	private String democratName;

	public String getDemocratNo() {
		return democratNo;
	}

	public void setDemocratNo(String democratNo) {
		this.democratNo = democratNo;
	}

	public String getDemocratName() {
		return democratName;
	}

	public void setDemocratName(String democratName) {
		this.democratName = democratName;
	}
}
