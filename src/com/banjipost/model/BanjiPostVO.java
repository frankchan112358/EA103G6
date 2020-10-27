package com.banjipost.model;

import java.sql.*;


public class BanjiPostVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String banjiPostNo;
	private String banjiNo;
	private String title;
	private String banjiPostContent;
	private Timestamp updateTime;
	private Integer status;

	public BanjiPostVO() {
	}

	public String getBanjiPostNo() {
		return banjiPostNo;
	}

	public void setBanjiPostNo(String banjiPostNo) {
		this.banjiPostNo = banjiPostNo;
	}

	public String getBanjiNo() {
		return banjiNo;
	}

	public void setBanjiNo(String banjiNo) {
		this.banjiNo = banjiNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBanjiPostContent() {
		return banjiPostContent;
	}

	public void setBanjiPostContent(String banjiPostContent) {
		this.banjiPostContent = banjiPostContent;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
