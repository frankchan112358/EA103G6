package com.student.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.course.model.CourseService;
import com.course.model.CourseVO;

public class StudentVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String studentNo;
	private String userNo;
	private String banjiNo;
	private String studentName;
	private String faceId;		
	private InputStream face;
	private String studentDescription;
	private Integer studentStatus;
	
	public StudentVO() {
		
	}
	
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getBanjiNo() {
		return banjiNo;
	}
	public void setBanjiNo(String banjiNo) {
		this.banjiNo = banjiNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getFaceId() {
		return faceId;
	}
	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}
	public InputStream getFace() {
		return face;
	}
	public void setFace(InputStream face) {
		this.face = face;
	}
	public String getStudentDescription() {
		return studentDescription;
	}
	public void setStudentDescription(String studentDescription) {
		this.studentDescription = studentDescription;
	}
	public Integer getStudentStatus() {
		return studentStatus;
	}
	public void setStudentStatus(Integer studentStatus) {
		this.studentStatus = studentStatus;
	}
	
	public List<CourseVO>getCourseList(){
		List<CourseVO> list = new ArrayList<CourseVO>();
		for (CourseVO courseVO : new CourseService().getAll()) {
			if (courseVO.getBanjiNo().equals(this.getBanjiNo())) {
				list.add(courseVO);
			}
		}
		return list;
	}
	
}





