package com.chatroom.model;

import java.util.List;


public interface ChatroomDAO_interface {
	 public void insert(ChatroomVO chatroomnoVO);
     public void update(ChatroomVO chatroomnoVO);
     public void delete(String chatroomnono);
     public ChatroomVO findByPrimaryKey(String chatroomnono);
}
