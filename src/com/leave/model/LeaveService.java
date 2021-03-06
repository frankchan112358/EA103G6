package com.leave.model;

import java.util.ArrayList;
import java.util.List;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.student.model.StudentService;
import com.student.model.StudentVO;
import com.timetable.model.TimetableService;
import com.timetable.model.TimetableVO;
import com.websocketnotify.controller.NotifyServlet;

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
		String next_leaveNo = dao.insert2(leaveVO);

		leaveVO.setLeaveNo(next_leaveNo);
		String userNo = leaveVO.getStudentVO().getBanjiVO().getEmpVO().getUserNo();
		TimetableVO timetableVO = leaveVO.getTimetableVO();
		String string = String.format("%s同學申請%s在%s%s%s", leaveVO.getStudentVO().getStudentName(), leaveVO.getTypeText(),
				timetableVO.getTimetableDate(), timetableVO.getPeriodText(), timetableVO.getCourseVO().getCourseName());
		new NotifyServlet().broadcast(userNo, "請假申請", string);

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

	public List<TimetableVO> getTimetableEventsIncludeSelf(String studentNo, String timetableNo) {
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
		list.add(new TimetableService().getOneTimetable(timetableNo));
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
		List<LeaveVO> list = new ArrayList<LeaveVO>();
		BanjiVO banjiVO = new BanjiService().getOneBanji(banjiNo);
		for (StudentVO studentVO : banjiVO.getStudentList()) {
			list.addAll(getLeaveWithStudent(studentVO.getStudentNo()));
		}
		return list;
	}

	public void updateStatusWhenTimetableEdit(String timetableNo, Integer status) {
		dao.updateStatusWhenTimetableEdit(timetableNo, status);
	}

	public String getCalenderEventsJson(String studentNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (TimetableVO timetableVO : new LeaveService().getTimetableEvents(studentNo)) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", timetableVO.getTimetableNo());
			jsonObject.addProperty("title",
					String.format("%s,%s", timetableVO.getPeriodText(), timetableVO.getCourseVO().getCourseName()));
			jsonObject.addProperty("start",
					String.format("%sT%s", timetableVO.getTimetableDate(), timetableVO.getPeriodEnum().getStart()));
			jsonObject.addProperty("backgroundColor", "#2198F3");
			jsonObject.addProperty("borderColor", "#2198F3");
			jsonObject.addProperty("editable", false);
			JsonObject extendedProps = new JsonObject();
			extendedProps.addProperty("timetableInfo", String.format("%s,%s,%s", timetableVO.getTimetableDate(),
					timetableVO.getPeriodEnum().getText(), timetableVO.getCourseVO().getCourseName()));
			jsonObject.add("extendedProps", extendedProps);
			jsonArray.add(jsonObject);
		}
		return gson.toJson(jsonArray);
	}

	public String getCalenderEventsJson(String studentNo, LeaveVO leaveVO) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (TimetableVO timetableVO : new LeaveService().getTimetableEventsIncludeSelf(studentNo,
				leaveVO.getTimetableNo())) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", timetableVO.getTimetableNo());
			jsonObject.addProperty("title",
					String.format("%s,%s", timetableVO.getPeriodText(), timetableVO.getCourseVO().getCourseName()));
			jsonObject.addProperty("start",
					String.format("%sT%s", timetableVO.getTimetableDate(), timetableVO.getPeriodEnum().getStart()));
			jsonObject.addProperty("backgroundColor", "#2198F3");
			jsonObject.addProperty("borderColor", "#2198F3");
			if (timetableVO.getTimetableNo().equals(leaveVO.getTimetableNo()))
				jsonObject.addProperty("borderColor", "red");
			jsonObject.addProperty("editable", false);
			JsonObject extendedProps = new JsonObject();
			extendedProps.addProperty("timetableInfo", String.format("%s,%s,%s", timetableVO.getTimetableDate(),
					timetableVO.getPeriodEnum().getText(), timetableVO.getCourseVO().getCourseName()));
			jsonObject.add("extendedProps", extendedProps);
			jsonArray.add(jsonObject);
		}
		return gson.toJson(jsonArray);
	}

	public String getDatatableJson(String banjiNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (LeaveVO leaveVO : new LeaveService().getAllWithBanji(banjiNo)) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("studentNo", leaveVO.getStudentNo());
			jsonObject.addProperty("studentName", leaveVO.getStudentVO().getStudentName());
			jsonObject.addProperty("timetableDate", leaveVO.getTimetableVO().getTimetableDate().toString());
			jsonObject.addProperty("periodText", leaveVO.getTimetableVO().getPeriodText());
			jsonObject.addProperty("courseName", leaveVO.getTimetableVO().getCourseVO().getCourseName());
			jsonObject.addProperty("typeText", leaveVO.getTypeText());
			jsonObject.addProperty("statusText", leaveVO.getStatusText());
			jsonObject.add("action", getActionObj(leaveVO));
			jsonArray.add(jsonObject);
		}
		JsonObject data = new JsonObject();
		data.add("data", jsonArray);
		return gson.toJson(data);
	}

	private JsonObject getActionObj(LeaveVO leaveVO) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", leaveVO.getLeaveNo());
		jsonObject.addProperty("status", leaveVO.getStatus());
		return jsonObject;
	}

	public String getReadLeaveJson(String leaveNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("description", leaveVO.getDescription());
		jsonObject.addProperty("studentName", leaveVO.getStudentVO().getStudentName());
		jsonObject.addProperty("banjiName", leaveVO.getStudentVO().getBanjiVO().getBanjiName());
		jsonObject.addProperty("studentNo", leaveVO.getStudentNo());
		jsonObject.addProperty("timetableDate", leaveVO.getTimetableVO().getTimetableDate().toString());
		jsonObject.addProperty("periodText", leaveVO.getTimetableVO().getPeriodText());
		jsonObject.addProperty("courseName", leaveVO.getTimetableVO().getCourseVO().getCourseName());
		jsonObject.addProperty("typeText", leaveVO.getTypeText());
		jsonObject.addProperty("statusText", leaveVO.getStatusText());
		jsonObject.addProperty("leaveNo", leaveVO.getLeaveNo());
		jsonObject.addProperty("userNo", leaveVO.getStudentVO().getUserNo());
		jsonObject.addProperty("hasHeadImg", leaveVO.getStudentVO().getUserVO().getPhoto() == null ? false : true);
		return gson.toJson(jsonObject);
	}
	
	public String getReviewDatatableJsonWithEmpVO(String empNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (LeaveVO leaveVO : getAllWithEmp(empNo) ) {
			if (LeaveStatus.Review.getNum().equals(leaveVO.getStatus())) {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("banjiName", leaveVO.getStudentVO().getBanjiVO().getBanjiName());
				jsonObject.addProperty("studentNo", leaveVO.getStudentNo());
				jsonObject.addProperty("studentName", leaveVO.getStudentVO().getStudentName());
				jsonObject.addProperty("timetableDate", leaveVO.getTimetableVO().getTimetableDate().toString());
				jsonObject.addProperty("periodText", leaveVO.getTimetableVO().getPeriodText());
				jsonObject.addProperty("courseName", leaveVO.getTimetableVO().getCourseVO().getCourseName());
				jsonObject.addProperty("typeText", leaveVO.getTypeText());
				jsonObject.addProperty("statusText", leaveVO.getStatusText());
				jsonObject.add("action", getActionObj(leaveVO));
				jsonArray.add(jsonObject);
			}
		}
		JsonObject data = new JsonObject();
		data.add("data", jsonArray);
		return gson.toJson(data);
	}
}
