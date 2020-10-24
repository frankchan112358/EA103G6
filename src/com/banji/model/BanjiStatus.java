package com.banji.model;

public enum BanjiStatus {
	Start(0, "開課"), End(1, "結訓"), Delay(2, "延期"), Cancel(3,"未開課");
	
	private Integer num;
	private String text;

	BanjiStatus(Integer num, String text) {
		this.num = num;
		this.text = text;
	}

	public Integer getNum() {
		return this.num;
	}

	public String getText() {
		return this.text;
	}

	public static BanjiStatus findByNum(Integer num) {
		for (BanjiStatus status : values()) {
			if (status.getNum().equals(num))
				return status;
		}
		return null;
	}
}
