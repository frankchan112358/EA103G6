package com.student.model;

import java.util.List;
import java.io.InputStream;



import com.student.model.StudentVO;




public class StudentService {
	private StudentDAO_interface dao;
	public StudentService() {
		dao = new StudentDAO();
	}
	
	public StudentVO addStudent(String userNo, String banjiNo,
			String studentName, String faceId, String studentDescription,Integer studentStatus) {

		StudentVO studentVO = new StudentVO();

		
		studentVO.setUserNo(userNo);
		studentVO.setBanjiNo(banjiNo);
		studentVO.setStudentName(studentName);
		studentVO.setFaceId(faceId);
		studentVO.setStudentDescription(studentDescription);
		studentVO.setStudentStatus(studentStatus);
		dao.insert(studentVO);

		return studentVO;
	}
	public StudentVO updateStudent(String studentNo,String userNo,String studentName,String banjiNo,String faceId,
			String studentDescription,Integer studentStatus) {
		
		StudentVO studentVO = new StudentVO();
		studentVO.setStudentNo(studentNo);
		studentVO.setUserNo(userNo);
	studentVO.setBanjiNo(banjiNo);
		studentVO.setStudentName(studentName);
		studentVO.setFaceId(faceId);
//		studentVO.setFace(face);
		studentVO.setStudentDescription(studentDescription);
		studentVO.setStudentStatus(studentStatus);
		dao.update(studentVO);
		return studentVO;
		
	}
	
	public void deleteStudent(String studentNo) {
		dao.delete(studentNo);
	}
	
	public StudentVO getOneStudent(String studentNo) {
		return dao.findByPrimaryKey(studentNo);
		
	}
	public List<StudentVO> getAll() {
		return dao.getAll();
	}
	
	public InputStream getPic(String studentNo) {
		return dao.getPic(studentNo);
	}
	
	
	
	
	
	
	
	
	
//	public StudentVO updateStudent(String banjiName,String studentName,String email,String address,
//			Long phone,Integer status,String studentNo) {
//		
//		StudentVO studentVO = new StudentVO();
//		
//		studentVO.setBanjiName(banjiName);
//		studentVO.setStudentName(studentName);
//		studentVO.setEmail(email);
//		studentVO.setAddress(address);
//		studentVO.setPhone(phone);
//		studentVO.setStatus(status);
//		studentVO.setStudentNo(studentNo);
//		
//		dao.update(studentVO);
//		
//		return studentVO;
//		
//	}
	
	
}
