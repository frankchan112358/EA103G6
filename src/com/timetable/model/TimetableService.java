package com.timetable.model;

import java.sql.Date;
import java.util.List;

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
}
