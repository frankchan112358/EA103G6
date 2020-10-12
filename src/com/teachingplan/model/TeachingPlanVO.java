package com.teachingplan.model;

public class TeachingPlanVO implements java.io.Serializable {

	private String teachingPlanNo;
	private String courseNo;
	private Integer week;
	private Integer lesson;
	private String planContent;

	public String getTeachingPlanNo() {
		return teachingPlanNo;
	}

	public void setTeachingPlanNo(String teachingPlanNo) {
		this.teachingPlanNo = teachingPlanNo;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getLesson() {
		return lesson;
	}

	public void setLesson(Integer lesson) {
		this.lesson = lesson;
	}

	public String getPlanContent() {
		return planContent;
	}

	public void setPlanContent(String planContent) {
		this.planContent = planContent;
	}

}
