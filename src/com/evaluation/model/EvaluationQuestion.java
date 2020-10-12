package com.evaluation.model;

public enum EvaluationQuestion implements java.io.Serializable {
	Q1(1, "01.您認為本課程目標之明確性"), Q2(2, "02.您認為本課程授課內容"), Q3(3, "03.您認為講師的教學方式"), Q4(4, "04.您認為講師在此課程領域之專業知識"),
	Q5(5, "05.您認為教材之內容能符合課程講授之需要"), Q6(6, "06.您認為本課程授課進度之安排"), Q7(7, "07.您認為您在此課程的收穫"), Q8(8, "08.您對此次課程的綜合評價"),
	Q9(9, "09.您認為此次課程值得推薦的程度"),;

	private Integer num;
	private String text;

	EvaluationQuestion(Integer num, String text) {
		this.num = num;
		this.text = text;
	}

	public Integer getNum() {
		return this.num;
	}

	public String getText() {
		return this.text;
	}

	public static EvaluationQuestion findByNum(Integer num) {
		for (EvaluationQuestion question : values()) {
			if (question.getNum().equals(num)) {
				return question;
			}
		}
		return null;
	}
}
