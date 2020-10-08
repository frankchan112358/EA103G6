package com.chatroommember.model;

import com.chatroommember.model.ChatroommemberVO;

public interface ChatroommemberDAO_interface {
	public ChatroommemberVO findByPrimaryKey(String chatroommemberno);
	public void insert(ChatroommemberVO chatroommemberVO);
}
