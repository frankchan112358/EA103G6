package com.teachingplan.model;

import java.util.*;

public interface TeachingPlanDAO_interface {

	public void insert(TeachingPlanVO teachingPlanVO);

	public void update(TeachingPlanVO teachingPlanVO);

	public void delete(String teachingPlanNo);

	public TeachingPlanVO findByPrimaryKey(String teachingPlanNo);

	public List<TeachingPlanVO> getAll();
	
	List<TeachingPlanVO> getTeachingPlanByCourseNo(String courseNo);


}
