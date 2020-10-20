package com.emp.model;

import java.util.List;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;

public class EmpVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String empNo;
	private String userNo;
	private Integer empStatus;
	private String empName;

	public EmpVO() {

	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Integer getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(Integer empStatus) {
		this.empStatus = empStatus;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public List<BanjiVO> getBanjiList(){
		return new BanjiService().getAllWithEmp(this.empNo);
	}
	
}
