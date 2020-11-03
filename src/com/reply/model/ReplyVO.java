package com.reply.model;

import java.sql.*;

public class ReplyVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String replyNo;
	private String courseAskNo;
	private String teacherNo;
	private String studentNo;
	private String replyContent;
	private Timestamp updateTime;
	private String userNo;
	
	public ReplyVO() {
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(String replyNo) {
		this.replyNo = replyNo;
	}

	public String getCourseAskNo() {
		return courseAskNo;
	}

	public void setCourseAskNo(String courseAskNo) {
		this.courseAskNo = courseAskNo;
	}

	public String getTeacherNo() {
		return teacherNo;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
