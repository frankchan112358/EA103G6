package com.finalscore.model;

public class FinalScoreVO implements java.io.Serializable {

	private String finalScoreNo;
	private String courseNo;
	private String studentNo;
	private Integer score;

	public String getFinalScoreNo() {
		return finalScoreNo;
	}

	public void setFinalScoreNo(String finalScoreNo) {
		this.finalScoreNo = finalScoreNo;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
