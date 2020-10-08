package com.courseask.model;

import java.util.List;

public interface CourseAskDAO_interface {
	public void insert(CourseAskVO courseAskVO);

	public void update(CourseAskVO courseAskVO);

	public void delete(String courseAskNo);

	public CourseAskVO findByPrimaryKey(String courseAskNo);

	public List<CourseAskVO> getAll();
}
