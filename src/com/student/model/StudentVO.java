package com.student.model;

import java.io.InputStream;

public class StudentVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String studentno;
	private String userno;
	private String banjino;
	private String studentname;
	private String faceid;		
	private InputStream face;
	private String studentdescription;
	private Integer studentstatus;
	
	public StudentVO(){	
	}
	
	public String getStudentno() {
		return studentno;
	}
	public void setStudentno(String studentno) {
		this.studentno = studentno;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getBanjino() {
		return banjino;
	}
	public void setBanjino(String banjino) {
		this.banjino = banjino;
	}
	public String getStudentname() {
		return studentname;
	}
	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}
	public String getFaceid() {
		return faceid;
	}
	public void setFaceid(String faceid) {
		this.faceid = faceid;
	}
	public InputStream getFace() {
		return face;
	}
	public void setFace(InputStream face) {
		this.face = face;
	}
	public String getStudentdescription() {
		return studentdescription;
	}
	public void setStudentdescription(String studentdescription) {
		this.studentdescription = studentdescription;
	}
	public Integer getStudentstatus() {
		return studentstatus;
	}
	public void setStudentstatus(Integer studentstatus) {
		this.studentstatus = studentstatus;
	}
	
	

}





