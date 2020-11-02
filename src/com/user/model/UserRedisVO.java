package com.user.model;

public class UserRedisVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String status;
	private String code;
	private Long time;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}
