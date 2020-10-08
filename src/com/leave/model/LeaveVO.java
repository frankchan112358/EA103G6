package com.leave.model;

public class LeaveVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String leaveNo;
	private String studentNo;
	private String timetableNo;
	private Integer type;
	private String description;
	private Integer status;

	public String getLeaveNo() {
		return leaveNo;
	}

	public void setLeaveNo(String leaveNo) {
		this.leaveNo = leaveNo;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
