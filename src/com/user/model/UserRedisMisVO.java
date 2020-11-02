package com.user.model;

public class UserRedisMisVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String status;
	private String mistake;
	private String misOldPSW;
	private String misNewPSW;
	private String misNewPSWCheck;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMistake() {
		return mistake;
	}
	public void setMistake(String mistake) {
		this.mistake = mistake;
	}
	public String getMisOldPSW() {
		return misOldPSW;
	}
	public void setMisOldPSW(String misOldPSW) {
		this.misOldPSW = misOldPSW;
	}
	public String getMisNewPSW() {
		return misNewPSW;
	}
	public void setMisNewPSW(String misNewPSW) {
		this.misNewPSW = misNewPSW;
	}
	public String getMisNewPSWCheck() {
		return misNewPSWCheck;
	}
	public void setMisNewPSWCheck(String misNewPSWCheck) {
		this.misNewPSWCheck = misNewPSWCheck;
	}
	
	
	
	



}
