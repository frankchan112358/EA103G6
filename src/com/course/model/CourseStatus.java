package com.course.model;

public enum CourseStatus {
	NotYet(0, "還沒開始"), Started(1, "授課中"), Finish(2, "課程結束");

	private Integer num;
	private String text;
	
	CourseStatus(Integer num, String text) {
		this.num = num;
		this.text = text;
	}
	
	public Integer getNum() {
		return this.num;
	}

	public String getText() {
		return this.text;
	}
	
	public static CourseStatus findByNum(Integer num) {
		for (CourseStatus status : values()) {
			if (status.getNum().equals(num)) {
				return status;
			}
		}
		return null;
	}
}
