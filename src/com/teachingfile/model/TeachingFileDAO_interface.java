package com.teachingfile.model;

import java.util.List;

public interface TeachingFileDAO_interface {
	public void insert(TeachingFileVO teachingFileVO);
	public void update(TeachingFileVO teachingFileVO);
	public void delete(String teachingFileNo);
	public TeachingFileVO findByPrimaryKey(String teachingFileNo);
	public List<TeachingFileVO> getAll();
}
