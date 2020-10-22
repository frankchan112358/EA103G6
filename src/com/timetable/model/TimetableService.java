package com.timetable.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teacher.model.TeacherService;
import com.teacher.model.TeacherVO;

public class TimetableService {

	private TimetableDAO_interface dao;
	
	public TimetableService() {
		dao = new TimetableDAO();
	}
	
	public TimetableVO addTimetable (String courseNo, String classroomNo, Integer timetablePeriod, Date timetableDate, String teachingLog) {
		
		TimetableVO timetableVO = new TimetableVO();
		
		timetableVO.setCourseNo(courseNo);
		timetableVO.setClassroomNo(classroomNo);
		timetableVO.setTimetablePeriod(timetablePeriod);
		timetableVO.setTimetableDate(timetableDate);
		timetableVO.setTeachingLog(teachingLog);
		dao.insert(timetableVO);
		
		return timetableVO;
	}
	
	public TimetableVO updateTimetable(String timetableNo, String courseNo, String classroomNo, Integer timetablePeriod, Date timetableDate, String teachingLog) {

		TimetableVO timetableVO = new TimetableVO();
		
		timetableVO.setTimetableNo(timetableNo);
		timetableVO.setCourseNo(courseNo);
		timetableVO.setClassroomNo(classroomNo);
		timetableVO.setTimetablePeriod(timetablePeriod);
		timetableVO.setTimetableDate(timetableDate);
		timetableVO.setTeachingLog(teachingLog);
		dao.update(timetableVO);
		
		return timetableVO;
	}
	
	public void deleteTimetable(String timetableNo) {
		dao.delete(timetableNo);
	}
	
	public TimetableVO getOneTimetable(String timetableNo) {
		return dao.findByPrimaryKey(timetableNo);
	}
	
	public List<TimetableVO> getAll(){
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
			jsonObject.addProperty("title", String.format("%s %s %s",timetableVO.getPeriodText(),timetableVO.getCourseVO().getCourseName(),timetableVO.getCourseVO().getTeacherVO().getTeacherName()));
			jsonObject.addProperty("start", String.format("%sT%s", timetableVO.getTimetableDate(),timetableVO.getPeriodEnum().getStart()));
			jsonObject.addProperty("borderColor", "");
			jsonObject.addProperty("editable", false);
			JsonObject extendedProps = new JsonObject();
			extendedProps.addProperty("timetableInfo", String.format("%s,%s,%s",timetableVO.getTimetableDate(),timetableVO.getPeriodEnum().getText(),timetableVO.getCourseVO().getCourseName()));
			jsonObject.add("extendedProps", extendedProps);
			jsonArray.add(jsonObject);
		}
		String jsonStr = gson.toJson(jsonArray);
		return jsonStr;
	}
	
	public String getAllWithBanjiNoCourseNo (String banjiNo,String courseNo) {
		TeacherVO teacherVO = new CourseService().getOneCourse(courseNo).getTeacherVO();
		String teacherNo = teacherVO.getTeacherNo();		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (TimetableVO ttVO : getAll()) {
			String ttCourseNo = ttVO.getCourseNo();
			String ttTeacherNo = ttVO.getCourseVO().getTeacherVO().getTeacherNo();
			String ttBanjiNo = ttVO.getCourseVO().getBanjiNo();
			JsonObject jsonObject = new JsonObject();
			if (ttBanjiNo.equals(banjiNo)&&ttCourseNo.equals(courseNo)) {
				jsonObject.addProperty("title", String.format("%s %s %s",ttVO.getPeriodText(),ttVO.getCourseVO().getCourseName(),ttVO.getCourseVO().getTeacherVO().getTeacherName()));				
				jsonObject.addProperty("backgroundColor", "red");
				jsonObject.addProperty("editable", true);
			}else if(ttBanjiNo.equals(banjiNo)){
				jsonObject.addProperty("title", String.format("%s %s %s",ttVO.getPeriodText(),ttVO.getCourseVO().getCourseName(),ttVO.getCourseVO().getTeacherVO().getTeacherName()));				
				jsonObject.addProperty("backgroundColor", "gray");
				jsonObject.addProperty("editable", false);
			}else if (ttTeacherNo.equals(teacherNo)) {
				jsonObject.addProperty("title", String.format("%s %s %s %s",ttVO.getPeriodText(),ttVO.getCourseVO().getBanjiVO().getBanjiName(),ttVO.getCourseVO().getCourseName(),ttVO.getCourseVO().getTeacherVO().getTeacherName()));				
				jsonObject.addProperty("backgroundColor", "green");
				jsonObject.addProperty("editable", false);
			}else {
				continue;
			}
			jsonObject.addProperty("id", ttVO.getTimetableNo());
			jsonObject.addProperty("start", String.format("%sT%s", ttVO.getTimetableDate(),ttVO.getPeriodEnum().getStart()));
			jsonObject.addProperty("borderColor", "");
			jsonArray.add(jsonObject);
		}
		String jsonStr = gson.toJson(jsonArray);
		return jsonStr;
	}
}
