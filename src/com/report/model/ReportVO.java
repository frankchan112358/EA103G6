package com.report.model;

import java.sql.Timestamp;

public class ReportVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String reportNo;
	private String forumPostNo;
	private String forumCommentNo;
	private String studentNo;
	private Integer type;
	private String description;
	private Timestamp reportTime;

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getForumPostNo() {
		return forumPostNo;
	}

	public void setForumPostNo(String forumPostNo) {
		this.forumPostNo = forumPostNo;
	}

	public String getForumCommentNo() {
		return forumCommentNo;
	}

	public void setForumCommentNo(String forumCommentNo) {
		this.forumCommentNo = forumCommentNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getReportTime() {
		return reportTime;
	}

	public void setReportTime(Timestamp reportTime) {
		this.reportTime = reportTime;
	}

}
