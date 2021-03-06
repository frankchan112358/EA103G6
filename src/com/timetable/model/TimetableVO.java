package com.timetable.model;

import java.sql.Date;

import com.classroom.model.ClassroomService;
import com.classroom.model.ClassroomVO;
import com.course.model.CourseService;
import com.course.model.CourseVO;

public class TimetableVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String timetableNo;
	private String courseNo;
	private String classroomNo;
	private Integer timetablePeriod;
	private Date timetableDate;
	private String teachingLog;
	
	public TimetableVO() {}
	
	public String getTimetableNo() {
		return timetableNo;
	}
	public void setTimetableNo(String timetableNo) {
		this.timetableNo = timetableNo;
	}
	public String getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}
	public String getClassroomNo() {
		return classroomNo;
	}
	public void setClassroomNo(String classroomNo) {
		this.classroomNo = classroomNo;
	}
	
	public Integer getTimetablePeriod() {
		return timetablePeriod;
	}
	public void setTimetablePeriod(Integer timetablePeriod) {
		this.timetablePeriod = timetablePeriod;
	}
	public Date getTimetableDate() {
		return timetableDate;
	}
	public void setTimetableDate(Date timetableDate) {
		this.timetableDate = timetableDate;
	}
	public String getTeachingLog() {
		return teachingLog;
	}
	public void setTeachingLog(String teachingLog) {
		this.teachingLog = teachingLog;
	}
	@Override
	public String toString() {
		return "TimetableVO [timetableNo=" + timetableNo + ", courseNo=" + courseNo + ", classroomNo=" + classroomNo
				+ ", timetablePeriod=" + timetablePeriod + ", timetableDate=" + timetableDate + ", teachingLog="
				+ teachingLog + "]";
	}
	
	public String getPeriodText() {
		return TimetablePeriod.findByNum(this.timetablePeriod).getText();
	}
	
	public CourseVO getCourseVO() {
		return new CourseService().getOneCourse(this.courseNo);
	}
	
	public TimetablePeriod getPeriodEnum() {
		return TimetablePeriod.findByNum(this.timetablePeriod);
	}
	
	public ClassroomVO getClassroomVO() {
		return new ClassroomService().getOneClassroom(this.classroomNo);
	}
}
