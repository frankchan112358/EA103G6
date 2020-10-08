package com.evaluation.model;

public class EvaluationVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String evaluationNo;
	private String courseNo;
	private String studentNo;
	private Integer question;
	private Integer answer;

	public String getEvaluationNo() {
		return evaluationNo;
	}

	public void setEvaluationNo(String evaluationNo) {
		this.evaluationNo = evaluationNo;
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

	public Integer getQuestion() {
		return question;
	}

	public void setQuestion(Integer question) {
		this.question = question;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

}
