package com.leave.model;

public enum LeaveStatus {
	Review(0, "審核"), Pass(1, "通過"), Reject(2, "拒絕"), Cancel(3, "取消"), Reschedule(4, "調課取消");

	private Integer num;
	private String text;

	LeaveStatus(Integer num, String text) {
		this.num = num;
		this.text = text;
	}

	public Integer getNum() {
		return this.num;
	}

	public String getText() {
		return this.text;
	}

	public static LeaveStatus findByNum(Integer num) {
		for (LeaveStatus status : values()) {
			if (status.getNum().equals(num))
				return status;
		}
		return null;
	}
}
