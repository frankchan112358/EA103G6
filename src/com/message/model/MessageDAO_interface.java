package com.message.model;


public interface MessageDAO_interface {
	public void insert(MessageVO messageVO);
	public MessageVO findByPrimaryKey(String messageno);

   
}
