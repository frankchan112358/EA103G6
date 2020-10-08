package com.emp.model;

import java.util.List;

public interface EmpDAO_interface {
	public void insert(EmpVO empVO);
    public void update(EmpVO empVO);
    public EmpVO findByPrimaryKey(String empNo);
    public List<EmpVO> getAll();
}
