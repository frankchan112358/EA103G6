package com.course.model;

import java.io.InputStream;
import java.sql.Date;

public class CourseVO implements java.io.Serializable {
	private String courseNo;
	private String banjiNo;
	private String teacherNo;
	private String classroomNo;
	private String basicCourseNo;
	private String courseName;
	private String courseOutline;
	private Integer lesson;
	private Date startDate;
	private Date endDate;
	private InputStream courseImg;
	private Integer status;

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getBanjiNo() {
		return banjiNo;
	}

	public void setBanjiNo(String banjiNo) {
		this.banjiNo = banjiNo;
	}

	public String getTeacherNo() {
		return teacherNo;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public String getClassroomNo() {
		return classroomNo;
	}

	public void setClassroomNo(String classroomNo) {
		this.classroomNo = classroomNo;
	}

	public String getBasicCourseNo() {
		return basicCourseNo;
	}

	public void setBasicCourseNo(String basicCourseNo) {
		this.basicCourseNo = basicCourseNo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseOutline() {
		return courseOutline;
	}

	public void setCourseOutline(String courseOutline) {
		this.courseOutline = courseOutline;
	}

	public Integer getLesson() {
		return lesson;
	}

	public void setLesson(Integer lesson) {
		this.lesson = lesson;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public InputStream getCourseImg() {
		return courseImg;
	}

	public void setCourseImg(InputStream courseImg) {
		this.courseImg = courseImg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
