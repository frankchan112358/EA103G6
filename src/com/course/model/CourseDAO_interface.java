package com.course.model;

import java.io.InputStream;
import java.util.*;

public interface CourseDAO_interface {

	public void insert(CourseVO courseVO);

	public void update(CourseVO courseVO);

	public void delete(String courseNo);

	public CourseVO findByPrimaryKey(String courseNo);

	public List<CourseVO> getAll();

	public InputStream getCourseImg(String courseNo);


}
