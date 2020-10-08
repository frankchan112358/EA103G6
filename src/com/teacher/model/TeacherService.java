package com.teacher.model;

import java.util.List;

public class TeacherService {
	private TeacherDAO_interface dao;

	public TeacherService() {
		dao = new TeacherDAO();
	}

	public TeacherVO addTeacher(String userNo, String teacherName, String skill, String description) {

		TeacherVO teacherVO = new TeacherVO();

		teacherVO.setUserNo(userNo);
		teacherVO.setTeacherName(teacherName);
		teacherVO.setSkill(skill);
		teacherVO.setDescription(description);
		dao.insert(teacherVO);

		return teacherVO;
	}

	public TeacherVO updateTeacher(String teacherNo, String userNo, String teacherName, String skill,
			String description, Integer teacherStatus) {

		TeacherVO teacherVO = new TeacherVO();
		
		teacherVO.setTeacherNo(teacherNo);
		teacherVO.setUserNo(userNo);
		teacherVO.setTeacherName(teacherName);
		teacherVO.setSkill(skill);
		teacherVO.setDescription(description);
		teacherVO.setTeacherStatus(teacherStatus);
		dao.update(teacherVO);

		return teacherVO;
	}

	
	public TeacherVO getOneTeacher(String teacherNo) {
		return dao.findByPrimaryKey(teacherNo);
	}
	
	public List<TeacherVO> getAll(){
		return dao.getAll();
	}
}
