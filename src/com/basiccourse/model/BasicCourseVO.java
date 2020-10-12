package com.basiccourse.model;

public class BasicCourseVO implements java.io.Serializable {
	private String basicCourseNo;
	private String basicCourseName;
	private String basicCourseInfo;
	private Integer lesson;

	public String getBasicCourseNo() {
		return basicCourseNo;
	}

	public void setBasicCourseNo(String basicCourseNo) {
		this.basicCourseNo = basicCourseNo;
	}

	public String getBasicCourseName() {
		return basicCourseName;
	}

	public void setBasicCourseName(String basicCourseName) {
		this.basicCourseName = basicCourseName;
	}

	public String getBasicCourseInfo() {
		return basicCourseInfo;
	}

	public void setBasicCourseInfo(String basicCourseInfo) {
		this.basicCourseInfo = basicCourseInfo;
	}

	public Integer getLesson() {
		return lesson;
	}

	public void setLesson(Integer lesson) {
		this.lesson = lesson;
	}

}
