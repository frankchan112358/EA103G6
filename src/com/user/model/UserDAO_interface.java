package com.user.model;

import java.io.InputStream;
import java.util.List;

public interface UserDAO_interface {
    public void insert(UserVO userVO);
    public void empUpdate(UserVO userVO);
    public void update(UserVO userVO);
    public void delete(String userNo);
    public UserVO findByPrimaryKey(String userNo);
    public UserVO findById(String id);
    public List<UserVO> getAll();
    public List<String> checkAccount(Integer type);
    public List<String> checkId(String userNo);
    public InputStream getPic(String userNo);
}
