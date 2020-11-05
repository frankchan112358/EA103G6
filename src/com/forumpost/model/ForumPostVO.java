package com.forumpost.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.forumcomment.model.ForumCommentService;
import com.forumcomment.model.ForumCommentVO;
import com.forumtopic.model.ForumTopicService;
import com.forumtopic.model.ForumTopicVO;
import com.student.model.StudentService;
import com.student.model.StudentVO;

public class ForumPostVO implements java.io.Serializable {
	private String forumPostNo;
	private String forumTopicNo;
	private String studentNo;
	private String title;
	private String content;
	private Integer forumPostViews;
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

	public Integer getForumPostViews() {
		return forumPostViews;
	}

	public void setForumPostViews(Integer forumPostViews) {
		this.forumPostViews = forumPostViews;
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

	public StudentVO getStudentVO() {
		return new StudentService().getOneStudent(this.studentNo);
	}

	public ForumTopicVO getForumTopicVO() {
		return new ForumTopicService().getOneForumTopic(this.forumTopicNo);
	}
	
	public List<ForumCommentVO> getForumCommentList() {
		return new ForumCommentService().getOneFpFc(this.forumPostNo);
	}
}
