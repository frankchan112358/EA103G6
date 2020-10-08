package com.basiccourse.model;

import java.util.*;

public interface BasicCourseDAO_interface {
	
	public void insert(BasicCourseVO basicCourseVO);

	public void update(BasicCourseVO basicCourseVO);

	public void delete(String basicCourseNo);

	public BasicCourseVO findByPrimaryKey(String basicCourseNo);

	public List<BasicCourseVO> getAll();
}
