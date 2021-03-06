package com.forumcomment.model;

import java.sql.Date;
import java.sql.Timestamp;

import com.forumpost.model.ForumPostService;
import com.forumpost.model.ForumPostVO;
import com.student.model.StudentService;
import com.student.model.StudentVO;

public class ForumCommentVO implements java.io.Serializable{
	private String forumCommentNo;
	private String forumPostNo;
	private String studentNo;
	private String content;
    private Timestamp updateTime;
	private Timestamp createTime;
	
	
	public String getForumCommentNo() {
		return forumCommentNo;
	}
	public void setForumCommentNo(String forumCommentNo) {
		this.forumCommentNo = forumCommentNo;
	}
	public String getForumPostNo() {
		return forumPostNo;
	}
	public void setForumPostNo(String forumPostNo) {
		this.forumPostNo = forumPostNo;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
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

	public ForumPostVO getForumPostVO() {
		return new ForumPostService().getOneForumPost(this.forumPostNo);
	}
	
	public StudentVO getStudentVO() {
		return new StudentService().getOneStudent(this.studentNo);
	}
}
