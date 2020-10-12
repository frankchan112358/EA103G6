package com.banjitypecourse.model;

public class BanjiTypeCourseVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String banjiTypeCourseNo;
	private String banjiTypeNo;
	private String basicCourseNo;

	public BanjiTypeCourseVO() {
	}

	public String getBanjiTypeCourseNo() {
		return banjiTypeCourseNo;
	}

	public void setBanjiTypeCourseNo(String banjiTypeCourseNo) {
		this.banjiTypeCourseNo = banjiTypeCourseNo;
	}

	public String getBanjiTypeNo() {
		return banjiTypeNo;
	}

	public void setBanjiTypeNo(String banjiTypeNo) {
		this.banjiTypeNo = banjiTypeNo;
	}

	public String getBasicCourseNo() {
		return basicCourseNo;
	}

	public void setBasicCourseNo(String basicCourseNo) {
		this.basicCourseNo = basicCourseNo;
	}

}
