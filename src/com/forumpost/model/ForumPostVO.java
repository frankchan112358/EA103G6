package com.forumpost.model;

import java.sql.Date;
import java.sql.Timestamp;

public class ForumPostVO implements java.io.Serializable {
	private String forumPostNo;
	private String forumTopicNo;
	private String studentNo;
	private String title;
	private String content;
    private Timestamp updateTime;
	private Timestamp createTime;
	

	public String getForumPostNo() {
		return forumPostNo;
	}

	public void setForumPostNo(String forumPostNo) {
		this.forumPostNo = forumPostNo;
	}

	public String getForumTopicNo() {
		return forumTopicNo;
	}

	public void setForumTopicNo(String forumTopicNo) {
		this.forumTopicNo = forumTopicNo;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	

}
