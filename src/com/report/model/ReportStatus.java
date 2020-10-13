package com.report.model;

public enum ReportStatus {
	Review(0, "審核"), Pass(1, "檢舉成功"), Reject(2, "檢舉失敗");

	private Integer num;
	private String text;

	ReportStatus(Integer num, String text) {
		this.num = num;
		this.text = text;
	}

	public Integer getNum() {
		return this.num;
	}

	public String getText() {
		return this.text;
	}

	public static ReportStatus findByNum(Integer num) {
		for (ReportStatus status : values()) {
			if (status.getNum().equals(num))
				return status;
		}
		return null;
	}
}
