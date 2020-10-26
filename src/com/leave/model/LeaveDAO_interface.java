package com.leave.model;

import java.util.List;

public interface LeaveDAO_interface {
	public void insert(LeaveVO leaveVO);

	public void update(LeaveVO leaveVO);

	public void delete(String leaveNo);

	public LeaveVO findByPribaryKey(String leaveNo);

	public List<LeaveVO> getAll();
	
	public void updateStatusWhenTimetableEdit(String timetableNo,Integer status);
	
	public String insert2(LeaveVO leaveVO);
}
