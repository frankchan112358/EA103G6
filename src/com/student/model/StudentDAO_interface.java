package com.student.model;

import java.io.InputStream;
import java.util.*;

import com.student.model.StudentVO;


public interface StudentDAO_interface {
	public void insert(StudentVO studentVO);
    public void update(StudentVO studentVO);
    public void delete(String studentNo);
	public StudentVO findByPrimaryKey(String studentNo);
	public StudentVO findByPrimaryKeyByuserNo(String userNo);
    public List<StudentVO> getAll();
    public InputStream getPic(String studentNo);
}
