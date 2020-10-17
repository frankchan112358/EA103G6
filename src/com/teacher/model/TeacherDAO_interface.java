package com.teacher.model;

import java.util.List;

public interface TeacherDAO_interface {
	public void insert(TeacherVO teacherVO);
    public void update(TeacherVO teacherVO);
    public void delete(String teacherNo);
    public TeacherVO findByPrimaryKey(String teacherNo);
    public TeacherVO findByUserNo(String userNo);
    public List<TeacherVO> getAll();
  

}
