package com.attendance.model;

import java.util.List;

public interface AttendanceDAO_interface {
	public void insert(AttendanceVO attendanceVO);

	public void update(AttendanceVO attendanceVO);

	public void delete(String attendanceNo);

	public AttendanceVO findByPrimaryKey(String attendanceNo);

	public List<AttendanceVO> getAll();
}
