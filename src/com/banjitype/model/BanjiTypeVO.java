package com.banjitype.model;

public class BanjiTypeVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String banjiTypeNo;
	private String banjiTypeName;
	private Integer classHours;
	private String banjiTypeContent;
	private Integer banjiTypeEnable;

	public BanjiTypeVO() {
	}

	public String getBanjiTypeNo() {
		return banjiTypeNo;
	}

	public void setBanjiTypeNo(String banjiTypeNo) {
		this.banjiTypeNo = banjiTypeNo;
	}

	public String getBanjiTypeName() {
		return banjiTypeName;
	}

	public void setBanjiTypeName(String banjiTypeName) {
		this.banjiTypeName = banjiTypeName;
	}

	public Integer getClassHours() {
		return classHours;
	}

	public void setClassHours(Integer classHours) {
		this.classHours = classHours;
	}

	public String getBanjiTypeContent() {
		return banjiTypeContent;
	}

	public void setBanjiTypeContent(String banjiTypeContent) {
		this.banjiTypeContent = banjiTypeContent;
	}

	public Integer getBanjiTypeEnable() {
		return banjiTypeEnable;
	}

	public void setBanjiTypeEnable(Integer banjiTypeEnable) {
		this.banjiTypeEnable = banjiTypeEnable;
	}

}
