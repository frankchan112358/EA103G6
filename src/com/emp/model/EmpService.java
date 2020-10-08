package com.emp.model;

import java.util.List;

public class EmpService {
	private EmpDAO_interface dao;

	public EmpService() {
		dao=new EmpDAO();
	}
	
	public EmpVO addEmp(String userNo,String empName) {
		
		EmpVO empVO =new EmpVO();
		empVO.setUserNo(userNo);
		empVO.setEmpName(empName);
		dao.insert(empVO);
		
		return empVO;
	}
	
	public EmpVO updateEmp(String empNo,String userNo,Integer empStatus,String empName) {
		
		EmpVO empVO =new EmpVO();
		empVO.setEmpNo(empNo);
		empVO.setUserNo(userNo);
		empVO.setEmpStatus(empStatus);
		empVO.setEmpName(empName);
		dao.update(empVO);
		
		return empVO;
	}
	
	public EmpVO getOneTeacher(String empNo) {
		return dao.findByPrimaryKey(empNo);
	}
	
	public List<EmpVO> getAll(){
		return dao.getAll();
	}
}
