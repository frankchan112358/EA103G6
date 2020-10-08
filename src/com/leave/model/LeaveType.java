package com.leave.model;

public enum LeaveType implements java.io.Serializable {
	Other(0, "其他"), Personal(1, "事假"), Sick(2, "病假"), Official(3, "公假"), Maternity(4, "娩假"), Funeral(5, "喪假");

	private Integer num;
	private String text;

	LeaveType(Integer num, String text) {
		this.num = num;
		this.text = text;
	}

	public Integer getNum() {
		return this.num;
	}

	public String getText() {
		return this.text;
	}

	public static LeaveType findByNum(Integer num) {
		for (LeaveType type : values()) {
			if (type.getNum().equals(num))
				return type;
		}
		return null;
	}
}
