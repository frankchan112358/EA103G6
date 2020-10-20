package com.leave.model;

import com.student.model.StudentService;
import com.student.model.StudentVO;
import com.timetable.model.TimetableService;
import com.timetable.model.TimetableVO;

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
	
	public String getStatusText() {
		return LeaveStatus.findByNum(this.status).getText();
	}
	
	public String getTypeText() {
		return LeaveType.findByNum(this.type).getText();
	}
	
	public TimetableVO getTimetableVO() {
		return new TimetableService().getOneTimetable(this.timetableNo);
	}
	
	public LeaveStatus getStatusEnum() {
		return LeaveStatus.findByNum(this.status);
	}
	
	public LeaveType getTypeEnum() {
		return LeaveType.findByNum(this.type);
	}
	
	public StudentVO getStudentVO() {
		return new StudentService().getOneStudent(studentNo);
	}
}
