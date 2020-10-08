package com.classroom.model;

import java.util.List;

public class ClassroomService {

	private ClassroomDAO_interface dao;

	public ClassroomService() {
		dao = new ClassroomJNDIDAO();
	}

	public ClassroomVO addClassroom( String classroomName, Integer numberOfStudent) {
		ClassroomVO classroomVO = new ClassroomVO();

		classroomVO.setClassroomName(classroomName);
		classroomVO.setNumberOfStudent(numberOfStudent);
		dao.insert(classroomVO);

		return classroomVO;

	}

	public ClassroomVO updateClassroom(String classroomNo, String classroomName, Integer numberOfStudent) {
		ClassroomVO classroomVO = new ClassroomVO();

		classroomVO.setClassroomNo(classroomNo);
		classroomVO.setClassroomName(classroomName);
		classroomVO.setNumberOfStudent(numberOfStudent);
		dao.update(classroomVO);

		return classroomVO;

	}

	public void deleteClassroom(String classroomNo) {
		dao.delete(classroomNo);
	}

	public ClassroomVO getOneClassroom(String classroomNo) {
		return dao.findByPrimaryKey(classroomNo);
	}

	public List<ClassroomVO> getAll() {
		return dao.getAll();
	}
}
