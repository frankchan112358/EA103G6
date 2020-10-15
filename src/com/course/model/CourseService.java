package com.course.model;

import java.sql.Date;
import java.util.List;

public class CourseService {

	private CourseDAO_interface dao;

	public CourseService() {
		dao = new CourseDAO();
	}

	public CourseVO addCourse(String banjiNo, String teacherNo, String classroomNo, String basicCourseNo,
			String courseName, String courseOutline, Integer lesson, Date startDate, Date endDate, Integer status) {

		CourseVO courseVO = new CourseVO();

		courseVO.setBanjiNo(banjiNo);
		courseVO.setTeacherNo(teacherNo);
		courseVO.setClassroomNo(classroomNo);
		courseVO.setBasicCourseNo(basicCourseNo);
		courseVO.setCourseName(courseName);
		courseVO.setCourseOutline(courseOutline);
		courseVO.setLesson(lesson);
		courseVO.setStartDate(startDate);
		courseVO.setEndDate(endDate);
		courseVO.setStatus(status);
		dao.insert(courseVO);

		return courseVO;
	}

	public CourseVO updateCourse(String courseNo, String banjiNo, String teacherNo, String classroomNo,
			String basicCourseNo, String courseName, String courseOutline, Integer lesson, Date startDate, Date endDate,
			Integer status) {

		CourseVO courseVO = new CourseVO();

		courseVO.setCourseNo(courseNo);
		courseVO.setBanjiNo(banjiNo);
		courseVO.setTeacherNo(teacherNo);
		courseVO.setClassroomNo(classroomNo);
		courseVO.setBasicCourseNo(basicCourseNo);
		courseVO.setCourseName(courseName);
		courseVO.setCourseOutline(courseOutline);
		courseVO.setLesson(lesson);
		courseVO.setStartDate(startDate);
		courseVO.setEndDate(endDate);
		courseVO.setStatus(status);
		dao.update(courseVO);

		return courseVO;
	}

	public void deleteCourse(String courseNo) {
		dao.delete(courseNo);
	}

	public CourseVO getOneCourse(String courseNo) {
		return dao.findByPrimaryKey(courseNo);
	}

	public List<CourseVO> getAll() {
		return dao.getAll();
	}

	public CourseStatus[] getCourseStatusAll() {
		return CourseStatus.values();
	}

	public String getCourseStatusText(Integer num) {
		return CourseStatus.findByNum(num).getText();
	}
}