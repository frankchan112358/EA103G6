package com.banjitypecourse.model;

import java.util.*;

public interface BanjiTypeCourseDAO_interface {

	public void insert(BanjiTypeCourseVO banjiTypeCourseVO);

	public void update(BanjiTypeCourseVO banjiTypeCourseVO);

	public void delete(String banjiTypeCourseNo);

	public BanjiTypeCourseVO findByPrimaryKey(String banjiTypeCourseNo);

	public List<BanjiTypeCourseVO> getAll();

}
