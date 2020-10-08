package com.leave.model;

import java.util.List;

public class LeaveService {
	private LeaveDAO_interface dao;

	public LeaveService() {
		dao = new LeaveJNDIDAO();
	}

	public LeaveVO addLeave(String studentNo, String timetableNo, Integer type, String description, Integer status) {
		LeaveVO leaveVO = new LeaveVO();
		leaveVO.setStudentNo(studentNo);
		leaveVO.setTimetableNo(timetableNo);
		leaveVO.setType(type);
		leaveVO.setDescription(description);
		leaveVO.setStatus(status);
		dao.insert(leaveVO);
		return leaveVO;
	}

	public LeaveVO updateLeaveVO(String leaveNo, String studentNo, String timetableNo, Integer type, String description,
			Integer status) {
		LeaveVO leaveVO = new LeaveVO();
		leaveVO.setLeaveNo(leaveNo);
		leaveVO.setStudentNo(studentNo);
		leaveVO.setTimetableNo(timetableNo);
		leaveVO.setType(type);
		leaveVO.setDescription(description);
		leaveVO.setStatus(status);
		dao.update(leaveVO);
		return leaveVO;
	}

	public void deleteLeaveVO(String leaveNo) {
		dao.delete(leaveNo);
	}

	public LeaveVO getOneLeave(String leaveNo) {
		return dao.findByPribaryKey(leaveNo);
	}

	public List<LeaveVO> getAll() {
		return dao.getAll();
	}

	public LeaveType[] getLeaveTypeAll() {
		return LeaveType.values();
	}

	public LeaveStatus[] getLeaveStatusAll() {
		return LeaveStatus.values();
	}
	
	public String getLeaveTypeText(Integer num) {
		return LeaveType.findByNum(num).getText();
	}
	
	public String getLeaveStatusText(Integer num) {
		return LeaveStatus.findByNum(num).getText();
	}
}
