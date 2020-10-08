package com.classroom.model;

import java.util.List;

public interface ClassroomDAO_interface {

	public void insert(ClassroomVO classroomVO);

	public void update(ClassroomVO classroomVO);

	public void delete(String classroomNo);

	public ClassroomVO findByPrimaryKey(String classroomNo);

	public List<ClassroomVO> getAll();

}
