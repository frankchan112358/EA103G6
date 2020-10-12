package com.basiccourse.model;

import java.util.List;

public class BasicCourseService {

	private BasicCourseDAO_interface dao;

	public BasicCourseService() {
		dao = new BasicCourseDAO();
	}

	public BasicCourseVO addBasicCourse(String basicCourseName, String basicCourseInfo, Integer lesson) {

		BasicCourseVO basicCourseVO = new BasicCourseVO();

		basicCourseVO.setBasicCourseName(basicCourseName);
		basicCourseVO.setBasicCourseInfo(basicCourseInfo);
		basicCourseVO.setLesson(lesson);
		dao.insert(basicCourseVO);

		return basicCourseVO;

	}

	public BasicCourseVO updateBasicCourse(String basicCourseNo, String basicCourseName, String basicCourseInfo,
			Integer lesson) {

		BasicCourseVO basicCourseVO = new BasicCourseVO();

		basicCourseVO.setBasicCourseNo(basicCourseNo);
		basicCourseVO.setBasicCourseName(basicCourseName);
		basicCourseVO.setBasicCourseInfo(basicCourseInfo);
		basicCourseVO.setLesson(lesson);
		dao.update(basicCourseVO);

		return basicCourseVO;

	}

	public void deleteBasicCourse(String basicCourseNo) {
		dao.delete(basicCourseNo);
	}

	public BasicCourseVO getOneBasicCourse(String basicCourseNo) {
		return dao.findByPrimaryKey(basicCourseNo);
	}

	public List<BasicCourseVO> getAll() {
		return dao.getAll();
	}
}
