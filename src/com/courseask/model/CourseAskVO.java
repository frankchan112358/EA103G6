package com.courseask.model;

import java.sql.*;

public class CourseAskVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String courseAskNo;
	private String courseNo;
	private String studentNo;
	private String title;
	private String question;
	private Timestamp updateTime;
	private Integer status;

	public CourseAskVO() {
	}

	public String getCourseAskNo() {
		return courseAskNo;
	}

	public void setCourseAskNo(String courseAskNo) {
		this.courseAskNo = courseAskNo;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
