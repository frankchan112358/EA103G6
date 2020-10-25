package com.coursepost.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.evaluation.model.EvaluationVO;

public class CoursePostService {

	private CoursePostDAO_interface dao;

	public CoursePostService() {
		dao = new CoursePostDAO();
	}

	public CoursePostVO addCoursePost(String courseNo, String title, String postContent, Timestamp updateTime) {

		CoursePostVO coursePostVO = new CoursePostVO();

		coursePostVO.setCourseNo(courseNo);
		coursePostVO.setTitle(title);
		coursePostVO.setPostContent(postContent);
		coursePostVO.setUpdateTime(updateTime);
		dao.insert(coursePostVO);

		return coursePostVO;
	}

	public CoursePostVO updateCoursePost(String coursePostNo, String courseNo, String title, String postContent,
			Timestamp updateTime) {

		CoursePostVO coursePostVO = new CoursePostVO();

		coursePostVO.setCoursePostNo(coursePostNo);
		coursePostVO.setCourseNo(courseNo);
		coursePostVO.setTitle(title);
		coursePostVO.setPostContent(postContent);
		coursePostVO.setUpdateTime(updateTime);
		dao.update(coursePostVO);

		return coursePostVO;
	}

	public void deleteCoursePost(String coursePostNo) {
		dao.delete(coursePostNo);
	}

	public CoursePostVO getOneCoursePost(String coursePostNo) {
		return dao.findByPrimaryKey(coursePostNo);	
	}

	public List<CoursePostVO> getAll(){
		return dao.getAll();	
	}
	
	public List<CoursePostVO> getCoursePostByCourseNo(String courseNo){
		return dao.getCoursePostByCourseNo(courseNo);
	}
}
