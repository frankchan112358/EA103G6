package com.emp.model;

import java.util.List;

import com.teacher.model.TeacherVO;

public interface EmpDAO_interface {
	public void insert(EmpVO empVO);
    public void update(EmpVO empVO);
    public void delete(String empNo);
    public EmpVO findByPrimaryKey(String empNo);
    public EmpVO findByUserNo(String userNo);
    public List<EmpVO> getAll();
}
