package com.healthlog.model;

import java.sql.Date;

public class HealthLogVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String healthLogNo;
	private String studentNo;
	private Integer status;
	private Date healthLogDate;

	public String getHealthLogNo() {
		return healthLogNo;
	}

	public void setHealthLogNo(String healthLogNo) {
		this.healthLogNo = healthLogNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getHealthLogDate() {
		return healthLogDate;
	}

	public void setHealthLogDate(Date healthLogDate) {
		this.healthLogDate = healthLogDate;
	}
}
