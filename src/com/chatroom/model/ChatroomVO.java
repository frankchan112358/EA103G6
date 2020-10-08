package com.chatroom.model;


public class ChatroomVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String chatroomno;
	private String chatroomname;
	private Integer chatroomtype;
	
	public ChatroomVO(){	
	}
	public String getChatroomno() {
		return chatroomno;
	}
	public void setChatroomno(String chatroomno) {
		this.chatroomno = chatroomno;
	}
	public String getChatroomname() {
		return chatroomname;
	}
	public void setChatroomname(String chatroomname) {
		this.chatroomname = chatroomname;
	}
	public Integer getChatroomtype() {
		return chatroomtype;
	}
	public void setChatroomtype(Integer chatroomtype) {
		this.chatroomtype = chatroomtype;
	}
}
