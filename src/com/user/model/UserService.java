package com.user.model;

import java.io.InputStream;
import java.util.List;


public class UserService {

	private UserDAO_interface dao;
	
	public UserService() {
		dao = new UserDAO();
	}
	
	public UserVO addUser(String account,String password,Integer type,String name,
			String mail,String phone, String address,String id) {
		
		UserVO userVO = new UserVO();
		
		userVO.setAccount(account);
		userVO.setPassword(password);
		userVO.setType(type);
		userVO.setName(name);
		userVO.setMail(mail);
		userVO.setPhone(phone);
		userVO.setAddress(address);
		userVO.setId(id);
		dao.insert(userVO);
		
		return userVO;				
	}
	
	public UserVO updateUser(String userNo,String account,String password,Integer type,String name,
			String mail,String phone, String address,String id,InputStream photo,Integer enable) {
		
		UserVO userVO = new UserVO();
		userVO.setUserNo(userNo);
		userVO.setAccount(account);
		userVO.setPassword(password);
		userVO.setType(type);
		userVO.setName(name);
		userVO.setMail(mail);
		userVO.setPhone(phone);
		userVO.setAddress(address);
		userVO.setId(id);
		userVO.setPhoto(photo);
		userVO.setEnable(enable);
		dao.update(userVO);
		return userVO;
		
	}
	
	public List<String> checkAccount(Integer type){
		return dao.checkAccount(type);
		
	}
	public List<String> checkId(String userNo){
		return dao.checkId(userNo);
		
	}
	
	public List<UserVO> getAll() {
		return dao.getAll();
	}
	
	public UserVO getOneUser(String userNo) {
		return dao.findByPrimaryKey(userNo);
		
	}
	
	public UserVO getOneUserById(String id) {
		return dao.findById(id);
		
	}
	
	public InputStream getPic(String userNo) {
		return dao.getPic(userNo);
	}
	
	public void deleteUser(String userNo) {
		dao.delete(userNo);
	}
	public UserVO Login_stu(String account,String password) {
		return dao.Login_stu(account, password);
		
	}
	public UserVO Login_emp(String account,String password) {
		return dao.Login_emp(account, password);
		
	}
	public UserVO Login_tea(String account,String password) {
		return dao.Login_tea(account, password);
		
	}
	
}
