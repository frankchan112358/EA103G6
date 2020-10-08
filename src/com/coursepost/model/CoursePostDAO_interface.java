package com.coursepost.model;

import java.util.*;

public interface CoursePostDAO_interface {

	public void insert(CoursePostVO coursePostVO);

	public void update(CoursePostVO coursePostVO);

	public void delete(String coursePostNo);

	public CoursePostVO findByPrimaryKey(String coursePostNo);

	public List<CoursePostVO> getAll();

}
