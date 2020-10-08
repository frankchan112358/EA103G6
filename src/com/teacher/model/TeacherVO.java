package com.teacher.model;

public class TeacherVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String teacherNo;
	private String userNo;
	private String teacherName;
	private String skill;
	private String description;
	private Integer teacherStatus;
	
	
	public String getTeacherNo() {
		return teacherNo;
	}
	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getTeacherStatus() {
		return teacherStatus;
	}
	public void setTeacherStatus(Integer teacherStatus) {
		this.teacherStatus = teacherStatus;
	}
}
