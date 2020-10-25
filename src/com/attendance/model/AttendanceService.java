package com.attendance.model;

import java.sql.Timestamp;
import java.util.List;

public class AttendanceService {

	private AttendanceDAO_interface dao;

	public AttendanceService() {
		dao = new AttendanceJNDIDAO();
	}

	public AttendanceVO addAttendance(String studentNo, String timetableNo, Timestamp time, Integer status,
			String note) {
		AttendanceVO attendanceVO = new AttendanceVO();
		attendanceVO.setStudentNo(studentNo);
		attendanceVO.setTimetableNo(timetableNo);
		attendanceVO.setTime(time);
		attendanceVO.setStatus(status);
		attendanceVO.setNote(note);
		dao.insert(attendanceVO);
		return attendanceVO;
	}

	public AttendanceVO updateAttendance(String attendanceNo, String studentNo, String timetableNo, Timestamp time,
			Integer status, String note) {
		AttendanceVO attendanceVO = new AttendanceVO();
		attendanceVO.setAttendanceNo(attendanceNo);
		attendanceVO.setStudentNo(studentNo);
		attendanceVO.setTimetableNo(timetableNo);
		attendanceVO.setTime(time);
		attendanceVO.setStatus(status);
		attendanceVO.setNote(note);
		dao.update(attendanceVO);
		return attendanceVO;
	}

	public void deleteAttendance(String attendanceNo) {
		dao.delete(attendanceNo);
	}

	public AttendanceVO getOneAttendance(String attendanceNo) {
		return dao.findByPrimaryKey(attendanceNo);
	}

	public List<AttendanceVO> getAll() {
		return dao.getAll();
	}
	
	public void deleteAttendanceWithTimetableNo(String timetableNo) {
		dao.delete(timetableNo);
	}
}
