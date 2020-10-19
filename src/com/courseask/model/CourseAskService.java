package com.courseask.model;

import java.util.List;

public class CourseAskService {

	private CourseAskDAO_interface dao;

	public CourseAskService() {
		dao = new CourseAskJNDIDAO();
	}

	public CourseAskVO addCourseAsk(String courseNo, String studentNo, String title, String question,
			java.sql.Timestamp updateTime) {

		CourseAskVO courseAskVO = new CourseAskVO();

		courseAskVO.setCourseNo(courseNo);
		courseAskVO.setStudentNo(studentNo);
		courseAskVO.setTitle(title);
		courseAskVO.setQuestion(question);
		courseAskVO.setUpdateTime(updateTime);

		dao.insert(courseAskVO);

		return courseAskVO;
	}

	public CourseAskVO updateCourseAsk(String courseAskNo,String courseNo, String studentNo, String title, String question,
			java.sql.Timestamp updateTime, Integer status) {

		CourseAskVO courseAskVO = new CourseAskVO();
		
		courseAskVO.setCourseAskNo(courseAskNo);
		courseAskVO.setCourseNo(courseNo);
		courseAskVO.setStudentNo(studentNo);
		courseAskVO.setTitle(title);
		courseAskVO.setQuestion(question);
		courseAskVO.setUpdateTime(updateTime);
		courseAskVO.setStatus(status);
		dao.update(courseAskVO);

		return courseAskVO;
	}

	public void deleteCourseAsk(String courseAskNo) {
		dao.delete(courseAskNo);
	}

	public CourseAskVO getOneCourseAsk(String courseAskNo) {
		return dao.findByPrimaryKey(courseAskNo);
	}

	public List<CourseAskVO> getAll() {
		return dao.getAll();
	}
}
