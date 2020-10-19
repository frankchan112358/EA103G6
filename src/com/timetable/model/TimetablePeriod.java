package com.timetable.model;

public enum TimetablePeriod {
	Morning(0,"早上","09:00:00","12:00:00"),Afternoon(1,"下午","13:30:00","16:30:00"),Evening(2,"晚上","18:00:00","21:00:00");
	
	private Integer num;	
	private String text;
	private String start;
	private String end;
	
	TimetablePeriod(Integer num, String text, String start,String end) {
		this.num = num;
		this.text = text;
		this.start = start;
		this.end = end;
	}
	
	public Integer getNum() {
		return this.num;
	}

	public String getText() {
		return this.text;
	}
	
	public String getStart() {
		return this.start;
	}
	
	public String getEnd() {
		return this.end;
	}
	
	public static TimetablePeriod findByNum(Integer num) {
		for (TimetablePeriod period : values()) {
			if (period.getNum().equals(num))
				return period;
		}
		return null;
	}
}
