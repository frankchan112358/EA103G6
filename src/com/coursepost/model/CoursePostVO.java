package com.coursepost.model;

import java.sql.Timestamp;

public class CoursePostVO implements java.io.Serializable {

	private String coursePostNo;
	private String courseNo;
	private String title;
	private String postContent;
	private Timestamp updateTime;

	public String getCoursePostNo() {
		return coursePostNo;
	}

	public void setCoursePostNo(String coursePostNo) {
		this.coursePostNo = coursePostNo;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
