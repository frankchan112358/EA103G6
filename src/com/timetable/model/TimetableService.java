package com.timetable.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.course.model.CourseService;
import com.course.model.CourseVO;

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
}
