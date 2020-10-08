package com.chatroommember.model;

import java.io.Serializable;

public class ChatroommemberVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String chatroommemberno;
	private String chatroomno;
	private String userno;
	
	public ChatroommemberVO(){	
	}
	public String getChatroommemberno() {
		return chatroommemberno;
	}
	public void setChatroommemberno(String chatroommemberno) {
		this.chatroommemberno = chatroommemberno;
	}
	public String getChatroomno() {
		return chatroomno;
	}
	public void setChatroomno(String chatroomno) {
		this.chatroomno = chatroomno;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}

}
