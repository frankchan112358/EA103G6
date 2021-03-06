package com.user.model;

import java.io.InputStream;
import java.util.List;

public interface UserDAO_interface {
    public void insert(UserVO userVO);
    public void update(UserVO userVO);
    public void userEnable(UserVO userVO);
    public void delete(String userNo);
    public UserVO findByPrimaryKey(String userNo);
    public UserVO findById(String id);
    public List<UserVO> getAll();
    public List<String> checkAccount(Integer type);
    public List<String> checkId(String userNo);
    public InputStream getPic(String userNo);
    public UserVO Login_stu(String account,String password);
    public UserVO Login_emp(String account,String password);
    public UserVO Login_tea(String account,String password);
	public UserVO UserLogin(String account, String password, Integer type);
	public UserVO UserForget(String id) ;
	public void update_Password(UserVO userVO);
	public void update_Password_backEnd(UserVO userVO);
	public void studentEnable(String userNo);
	public void studentNoEnable(String userNo);
}