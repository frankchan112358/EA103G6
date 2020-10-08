package com.student.model;

import java.util.List;
import java.io.InputStream;



import com.student.model.StudentVO;




public class StudentService {
	private StudentDAO_interface dao;
	public StudentService() {
		dao = new StudentDAO();
	}
	
	public StudentVO addStudent(String userno, String banjino,
			String studentname, String faceid, String studentdescription,Integer studentstatus) {

		StudentVO studentVO = new StudentVO();

		
		studentVO.setUserno(userno);
		studentVO.setBanjino(banjino);
		studentVO.setStudentname(studentname);
		studentVO.setFaceid(faceid);
		studentVO.setStudentdescription(studentdescription);
		studentVO.setStudentstatus(studentstatus);
		dao.insert(studentVO);

		return studentVO;
	}
	//String userno,String banjino,InputStream face,InputStream face,
	public StudentVO updateStudent(String studentno,String userno,String studentname,String banjino,String faceid,
			String studentdescription,Integer studentstatus) {
		
		StudentVO studentVO = new StudentVO();
		studentVO.setStudentno(studentno);
		studentVO.setUserno(userno);
	studentVO.setBanjino(banjino);
		studentVO.setStudentname(studentname);
		studentVO.setFaceid(faceid);
//		studentVO.setFace(face);
		studentVO.setStudentdescription(studentdescription);
		studentVO.setStudentstatus(studentstatus);
		dao.update(studentVO);
		return studentVO;
		
	}
	
	public void deleteStudent(String studentno) {
		dao.delete(studentno);
	}
	
	public StudentVO getOneStudent(String studentno) {
		return dao.findByPrimaryKey(studentno);
		
	}
	public List<StudentVO> getAll() {
		return dao.getAll();
	}
	
	public InputStream getPic(String studentno) {
		return dao.getPic(studentno);
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
