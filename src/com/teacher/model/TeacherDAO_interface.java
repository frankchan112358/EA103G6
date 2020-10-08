package com.teacher.model;

import java.util.List;

public interface TeacherDAO_interface {
	public void insert(TeacherVO teacherVO);
    public void update(TeacherVO teacherVO);
    public TeacherVO findByPrimaryKey(String teacherNo);
    public List<TeacherVO> getAll();
  

}
