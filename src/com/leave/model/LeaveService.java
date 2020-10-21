package com.leave.model;

import java.util.ArrayList;
import java.util.List;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.student.model.StudentService;
import com.student.model.StudentVO;
import com.timetable.model.TimetableService;
import com.timetable.model.TimetableVO;

public class LeaveService {
	private LeaveDAO_interface dao;

	public LeaveService() {
		dao = new LeaveJNDIDAO();
	}

	public LeaveVO addLeave(String studentNo, String timetableNo, Integer type, String description) {
		LeaveVO leaveVO = new LeaveVO();
		leaveVO.setStudentNo(studentNo);
		leaveVO.setTimetableNo(timetableNo);
		leaveVO.setType(type);
		leaveVO.setDescription(description);
		leaveVO.setStatus(LeaveStatus.Review.getNum());
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

	public List<LeaveVO> getLeaveWithStudent(String studentNo) {
		List<LeaveVO> list = new ArrayList<LeaveVO>();
		List<LeaveVO> all = dao.getAll();
		for (LeaveVO leaveVO : all) {
			if (studentNo.equals(leaveVO.getStudentNo()))
				list.add(leaveVO);
		}
		return list;
	}

	public List<TimetableVO> getTimetableWithStudent(String studentNo) {
		List<TimetableVO> list = new ArrayList<TimetableVO>();
		StudentVO studentVO = new StudentService().getOneStudent(studentNo);
		if (studentVO != null) {
			CourseService courseSvc = new CourseService();
			List<TimetableVO> all = new TimetableService().getAll();
			for (TimetableVO timetableVO : all) {
				CourseVO courseVO = courseSvc.getOneCourse(timetableVO.getCourseNo());
				if (studentVO.getBanjiNo().equals(courseVO.getBanjiNo()))
					list.add(timetableVO);
			}
		}
		return list;
	}

	public List<TimetableVO> getTimetableEvents(String studentNo) {
		List<TimetableVO> list = new ArrayList<TimetableVO>();
		List<LeaveVO> studentLeaveList = getLeaveWithStudent(studentNo);
		List<TimetableVO> studentTimetableList = getTimetableWithStudent(studentNo);
		java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
		for (TimetableVO timetableVO : studentTimetableList) {
			if (checkTimetableStatus(timetableVO.getTimetableNo(), studentLeaveList)
					&& now.before(timetableVO.getTimetableDate())) {
				list.add(timetableVO);
			}
		}
		return list;
	}

	public Boolean checkTimetableStatus(String timetableNo, List<LeaveVO> studentLeaveList) {
		for (LeaveVO leaveVO : studentLeaveList) {
			LeaveStatus status = leaveVO.getStatusEnum();
			if (leaveVO.getTimetableNo().equals(timetableNo)
					&& (LeaveStatus.Review.equals(status) || LeaveStatus.Pass.equals(status))) {
				return false;
			}
		}
		return true;
	}

	public void cancelLeave(String leaveNo) {
		LeaveVO leaveVO = getOneLeave(leaveNo);
		leaveVO.setStatus(LeaveStatus.Cancel.getNum());
		dao.update(leaveVO);
	}

	public List<LeaveVO> getAllWithEmp(String empNo) {
		List<LeaveVO> list = new ArrayList<LeaveVO>();
		EmpVO empVO = new EmpService().getOneEmp(empNo);
		for (BanjiVO banjiVO : empVO.getBanjiList()) {
			for (StudentVO studentVO : banjiVO.getStudentList()) {
				list.addAll(getLeaveWithStudent(studentVO.getStudentNo()));
			}
		}
		return list;
	}

	public List<LeaveVO> getAllWithBanji(String banjiNo) {
		List<LeaveVO>list = new ArrayList<LeaveVO>();
		BanjiVO banjiVO = new BanjiService().getOneBanji(banjiNo);
		for (StudentVO studentVO : banjiVO.getStudentList()) {
			list.addAll(getLeaveWithStudent(studentVO.getStudentNo()));
		}
		return list;
	}
}
