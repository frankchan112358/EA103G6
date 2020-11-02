package com.timetable.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.attendance.model.AttendanceService;
import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.leave.model.LeaveService;
import com.leave.model.LeaveStatus;
import com.teacher.model.TeacherVO;
import com.teachingfile.model.TeachingFileService;
import com.teachingfile.model.TeachingFileVO;
import com.video.model.VideoService;
import com.video.model.VideoVO;

public class TimetableService {

	private TimetableDAO_interface dao;

	public TimetableService() {
		dao = new TimetableDAO();
	}

	public TimetableVO addTimetable(String courseNo, String classroomNo, Integer timetablePeriod, Date timetableDate,
			String teachingLog) {

		TimetableVO timetableVO = new TimetableVO();

		timetableVO.setCourseNo(courseNo);
		timetableVO.setClassroomNo(classroomNo);
		timetableVO.setTimetablePeriod(timetablePeriod);
		timetableVO.setTimetableDate(timetableDate);
		timetableVO.setTeachingLog(teachingLog);
		dao.insert(timetableVO);

		return timetableVO;
	}

	public TimetableVO updateTimetable(String timetableNo, String courseNo, String classroomNo, Integer timetablePeriod,
			Date timetableDate, String teachingLog) {

		TimetableVO timetableVO = new TimetableVO();

		timetableVO.setTimetableNo(timetableNo);
		timetableVO.setCourseNo(courseNo);
		timetableVO.setClassroomNo(classroomNo);
		timetableVO.setTimetablePeriod(timetablePeriod);
		timetableVO.setTimetableDate(timetableDate);
		timetableVO.setTeachingLog(teachingLog);
		new LeaveService().updateStatusWhenTimetableEdit(timetableNo, LeaveStatus.Reschedule.getNum());
		dao.update(timetableVO);

		return timetableVO;
	}
	
	public TimetableVO updateTimetableLog(String timetableNo, String teachingLog) {

		TimetableVO timetableVO = new TimetableVO();

		timetableVO.setTimetableNo(timetableNo);
		timetableVO.setTeachingLog(teachingLog);
		TimetableDAO.update_teachingLog(timetableVO);

		return timetableVO;
	}

	public void deleteTimetable(String timetableNo) {
		VideoService videoService = new VideoService();
		VideoVO videoVO = videoService.getOneVideoWithTimetableNo(timetableNo);
		if (videoVO != null)
			videoService.deleteVideo(videoVO.getVideoNo());

		TeachingFileService teachingFileService = new TeachingFileService();
		for (TeachingFileVO teachingFileVO : teachingFileService.getAll()) {
			if (timetableNo.equals(teachingFileVO.getTimetableNo()))
				teachingFileService.deleteTeachingFile(teachingFileVO.getTeachingFileNo());
		}

		new LeaveService().updateStatusWhenTimetableEdit(timetableNo, LeaveStatus.Reschedule.getNum());

		new AttendanceService().deleteAttendanceWithTimetableNo(timetableNo);

		dao.delete(timetableNo);
	}
	
	public void deleteTimetableLog(String timetableNo) {

		TimetableDAO timetableDAO = new TimetableDAO();
		timetableDAO.delete_teachingLog(timetableNo);
	}

	public TimetableVO getOneTimetable(String timetableNo) {
		return dao.findByPrimaryKey(timetableNo);
	}

	public List<TimetableVO> getAll() {
		return dao.getAll();
	}

	public TimetablePeriod[] getTimetablePeriodAll() {
		return TimetablePeriod.values();
	}

	public String getTimetablePeriondText(Integer num) {
		return TimetablePeriod.findByNum(num).getText();
	}

	public List<TimetableVO> getAllWithCourseNo(String courseNo) {
		List<TimetableVO> list = new ArrayList<TimetableVO>();
		for (TimetableVO timetableVO : getAll()) {
			if (courseNo.equals(timetableVO.getCourseNo()))
				list.add(timetableVO);
		}
		return list;
	}

	public List<TimetableVO> getAllWithBanjiNo(String banjiNo) {
		List<TimetableVO> list = new ArrayList<TimetableVO>();
		for (CourseVO courseVO : new CourseService().getAllWithBanjiNo(banjiNo))
			list.addAll(courseVO.getTimetableList());
		return list;
	}

	public String getAllJsonWithBanjiNo(String banjiNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (TimetableVO timetableVO : getAllWithBanjiNo(banjiNo)) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", timetableVO.getTimetableNo());
			jsonObject.addProperty("title",
					String.format("%s %s %s", timetableVO.getPeriodText(), timetableVO.getCourseVO().getCourseName(),
							timetableVO.getCourseVO().getTeacherVO().getTeacherName()));
			jsonObject.addProperty("start",
					String.format("%sT%s", timetableVO.getTimetableDate(), timetableVO.getPeriodEnum().getStart()));
			jsonObject.addProperty("end",
					String.format("%sT%s", timetableVO.getTimetableDate(), timetableVO.getPeriodEnum().getEnd()));
			jsonObject.addProperty("borderColor", "");
			jsonObject.addProperty("editable", false);
			JsonObject extendedProps = new JsonObject();
			extendedProps.addProperty("timetableInfo", String.format("%s,%s,%s", timetableVO.getTimetableDate(),
					timetableVO.getPeriodEnum().getText(), timetableVO.getCourseVO().getCourseName()));
			jsonObject.add("extendedProps", extendedProps);
			jsonArray.add(jsonObject);
		}
		String jsonStr = gson.toJson(jsonArray);
		return jsonStr;
	}

	public String getAllByJsonStrWithBanjiNoCourseNo(String banjiNo, String courseNo, String mode) {
		java.sql.Timestamp now = getNow();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonObject jsonObject = new JsonObject();
		TeacherVO teacherVO = new CourseService().getOneCourse(courseNo).getTeacherVO();
		String teacherNo = teacherVO.getTeacherNo();
		JsonArray jsonArray = new JsonArray();
		for (TimetableVO ttVO : getAll()) {
			String ttCourseNo = ttVO.getCourseNo();
			String ttTeacherNo = ttVO.getCourseVO().getTeacherVO().getTeacherNo();
			String ttBanjiNo = ttVO.getCourseVO().getBanjiNo();
			JsonObject jsonObj = new JsonObject();
			if (ttBanjiNo.equals(banjiNo) && ttCourseNo.equals(courseNo)) {
				jsonObj.addProperty("title", String.format("%s %s %s", ttVO.getPeriodText(),
						ttVO.getCourseVO().getCourseName(), ttVO.getCourseVO().getTeacherVO().getTeacherName()));
				jsonObj.addProperty("backgroundColor", "#fd3995");
				jsonObj.addProperty("borderColor", "#fd3995");
				jsonObj.addProperty("editable", true);
				if (!"test".equals(mode) && now.after(getStartTime(ttVO)))
					jsonObj.addProperty("editable", false);
			} else if (ttBanjiNo.equals(banjiNo)) {
				jsonObj.addProperty("title", String.format("%s %s %s", ttVO.getPeriodText(),
						ttVO.getCourseVO().getCourseName(), ttVO.getCourseVO().getTeacherVO().getTeacherName()));
				jsonObj.addProperty("backgroundColor", "#868e96");
				jsonObj.addProperty("borderColor", "#868e96");
				jsonObj.addProperty("editable", false);
			} else if (ttTeacherNo.equals(teacherNo)) {
				jsonObj.addProperty("title",
						String.format("%s %s %s %s", ttVO.getPeriodText(),
								ttVO.getCourseVO().getBanjiVO().getBanjiName(), ttVO.getCourseVO().getCourseName(),
								ttVO.getCourseVO().getTeacherVO().getTeacherName()));
				jsonObj.addProperty("backgroundColor", "#1dc9b7");
				jsonObj.addProperty("borderColor", "#1dc9b7");
				jsonObj.addProperty("editable", false);
			} else {
				continue;
			}
			jsonObj.addProperty("id", ttVO.getTimetableNo());
			jsonObj.addProperty("start",
					String.format("%sT%s", ttVO.getTimetableDate(), ttVO.getPeriodEnum().getStart()));
			jsonObj.addProperty("end", String.format("%sT%s", ttVO.getTimetableDate(), ttVO.getPeriodEnum().getEnd()));
			JsonObject extendedProps = gson.fromJson(gson.toJson(ttVO), JsonObject.class);
			extendedProps.addProperty("courseName", ttVO.getCourseVO().getCourseName());
			extendedProps.addProperty("classroomName", ttVO.getClassroomVO().getClassroomName());
			extendedProps.addProperty("periodText", ttVO.getPeriodText());
			extendedProps.addProperty("teacherName", ttVO.getCourseVO().getTeacherVO().getTeacherName());
			jsonObj.add("extendedProps", extendedProps);
			jsonArray.add(jsonObj);
		}
		jsonObject.add("events", jsonArray);
		CourseVO courseVO = new CourseService().getOneCourse(courseNo);
		courseVO.setCourseImg(null);
		JsonObject _courseVO = gson.fromJson(gson.toJson(courseVO), JsonObject.class);
		_courseVO.addProperty("teacherName", courseVO.getTeacherVO().getTeacherName());
		_courseVO.addProperty("statusText", courseVO.getStatusText());
		_courseVO.addProperty("timetableSize", getAllWithCourseNo(courseNo).size());
		jsonObject.addProperty("_courseVO", gson.toJson(_courseVO));
		String jsonStr = gson.toJson(jsonObject);
		return jsonStr;
	}

	public String getAllByJsonStrWithBanjiNo(String banjiNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (TimetableVO ttVO : getAll()) {
			String ttBanjiNo = ttVO.getCourseVO().getBanjiNo();
			JsonObject jsonObj = new JsonObject();
			if (ttBanjiNo.equals(banjiNo)) {
				jsonObj.addProperty("title", String.format("%s %s %s", ttVO.getPeriodText(),
						ttVO.getCourseVO().getCourseName(), ttVO.getCourseVO().getTeacherVO().getTeacherName()));
				jsonObj.addProperty("backgroundColor", "#2198F3");
				jsonObj.addProperty("borderColor", "#2198F3");
				jsonObj.addProperty("editable", false);
				jsonObj.addProperty("id", ttVO.getTimetableNo());
				jsonObj.addProperty("start",
						String.format("%sT%s", ttVO.getTimetableDate(), ttVO.getPeriodEnum().getStart()));
				jsonObj.addProperty("end",
						String.format("%sT%s", ttVO.getTimetableDate(), ttVO.getPeriodEnum().getEnd()));
				JsonObject extendedProps = gson.fromJson(gson.toJson(ttVO), JsonObject.class);
				extendedProps.addProperty("courseName", ttVO.getCourseVO().getCourseName());
				extendedProps.addProperty("classroomName", ttVO.getClassroomVO().getClassroomName());
				extendedProps.addProperty("periodText", ttVO.getPeriodText());
				extendedProps.addProperty("teacherName", ttVO.getCourseVO().getTeacherVO().getTeacherName());
				jsonObj.add("extendedProps", extendedProps);
				jsonArray.add(jsonObj);
			}
		}
		BanjiVO banjiVO = new BanjiService().getOneBanji(banjiNo);
		JsonObject _banjiVO = gson.fromJson(gson.toJson(banjiVO), JsonObject.class);
		_banjiVO.addProperty("empName", banjiVO.getEmpVO().getEmpName());
		_banjiVO.addProperty("statusText", banjiVO.getStatusText());
		_banjiVO.addProperty("timetableSize", getAllWithBanjiNo(banjiNo).size());
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("events", jsonArray);
		jsonObject.add("_banjiVO", _banjiVO);
		String jsonStr = gson.toJson(jsonObject);
		return jsonStr;
	}

	public java.sql.Date jsonStrTimeConvertToSqlDate(String jsonStrTime, String formatStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			java.util.Date uDate = format.parse(jsonStrTime);
			return new java.sql.Date(uDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public java.sql.Timestamp getNow() {
		java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
		return now;
	}

	public java.sql.Timestamp getStartTime(TimetableVO timetableVO) {
		String startTimeStr = String.format("%s %s", timetableVO.getTimetableDate(),
				timetableVO.getPeriodEnum().getStart());
		java.sql.Timestamp start = java.sql.Timestamp.valueOf(startTimeStr);
		return start;
	}

	public java.sql.Timestamp getStartTime(Date timetableDate, Integer timetablePeriod) {
		String startTimeStr = String.format("%s %s", timetableDate,
				TimetablePeriod.findByNum(timetablePeriod).getStart());
		java.sql.Timestamp start = java.sql.Timestamp.valueOf(startTimeStr);
		return start;
	}

	public String checkInsert(String courseNo, Date timetableDate, Integer timetablePeriod) {
		CourseVO courseVO = new CourseService().getOneCourse(courseNo);
		if (getStartTime(timetableDate, timetablePeriod).before(getNow()))
			return "請勿排在過去的時間";
		if (timetableDate.before(courseVO.getStartDate()))
			return "請勿排在課程開始時間之前";
		if (timetableDate.after(courseVO.getEndDate()))
			return "請勿排在課程結束時間之後";
		return "pass";
	}

	public String checkUpdatePeriod(String courseNo, String timetableNo, Date timetableDate, Integer timetablePeriod) {
		CourseVO courseVO = new CourseService().getOneCourse(courseNo);
		TimetableVO timetableVO = new TimetableService().getOneTimetable(timetableNo);
		if (timetableDate.before(timetableVO.getTimetableDate()) || timetableDate.after(timetableVO.getTimetableDate()))
			return "該課堂已經事先被異動，請重新調整";
		if (getStartTime(timetableDate, timetablePeriod).before(getNow()))
			return "請勿排在過去的時間";
		if (timetableDate.before(courseVO.getStartDate()))
			return "請勿排在課程開始時間之前";
		if (timetableDate.after(courseVO.getEndDate()))
			return "請勿排在課程結束時間之後";
		return "pass";
	}

	public String checkUpdateDate(String courseNo, String timetableNo, Date timetableDate, Integer timetablePeriod) {
		CourseVO courseVO = new CourseService().getOneCourse(courseNo);
		if (getStartTime(timetableDate, timetablePeriod).before(getNow()))
			return "請勿排在過去的時間";
		if (timetableDate.before(courseVO.getStartDate()))
			return "請勿排在課程開始時間之前";
		if (timetableDate.after(courseVO.getEndDate()))
			return "請勿排在課程結束時間之後";
		return "pass";
	}
}
