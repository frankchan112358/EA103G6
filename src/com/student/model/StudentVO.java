package com.student.model;

import java.io.InputStream;
import java.util.List;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.course.model.CourseVO;
import com.leave.model.LeaveService;
import com.leave.model.LeaveVO;
import com.user.model.UserService;
import com.user.model.UserVO;

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
		return getBanjiVO().getCourseList();
	}
	
	public List<LeaveVO> getLeaveList(){
		return new LeaveService().getLeaveWithStudent(this.studentNo);
	}
	
	public BanjiVO getBanjiVO() {
		return new BanjiService().getOneBanji(this.banjiNo);
	}
	
	public UserVO getUserVO() {
		return new UserService().getOneUser(this.userNo);
	}
}





