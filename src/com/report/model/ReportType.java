package com.report.model;

public enum ReportType implements java.io.Serializable {
	Other(0, "其他"), Error(1, "格式錯誤"), Ad(2, "濫發廣告訊息"), Violence(3, "內容含有色情暴力"), 
	Meaningless(4, "無意義的貼文或留言"), Sensitive(5, "帶有敏感性議題");

	private Integer num;
	private String text;

	ReportType(Integer num, String text) {
		this.num = num;
		this.text = text;
	}

	public Integer getNum() {
		return this.num;
	}

	public String getText() {
		return this.text;
	}

	public static ReportType findByNum(Integer num) {
		for (ReportType type : values()) {
			if (type.getNum().equals(num))
				return type;
		}
		return null;
	}
}
