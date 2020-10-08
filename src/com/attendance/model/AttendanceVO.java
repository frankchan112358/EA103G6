package com.attendance.model;

import java.sql.Timestamp;

public class AttendanceVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String attendanceNo;
	private String studentNo;
	private String timetableNo;
	private Timestamp time;
	private Integer status;
	private String note;

	public String getAttendanceNo() {
		return attendanceNo;
	}

	public void setAttendanceNo(String attendanceNo) {
		this.attendanceNo = attendanceNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getTimetableNo() {
		return timetableNo;
	}

	public void setTimetableNo(String timetableNo) {
		this.timetableNo = timetableNo;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
