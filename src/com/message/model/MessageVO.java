package com.message.model;
import java.io.Serializable;
import java.sql.Timestamp;

public class MessageVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String messageNo;
	private String chatRoomNo;
	private String userNo;
	private String messageContent;
	private Timestamp messageTime;		
	private Integer messageCount;
	
	public MessageVO() {
		
	}
	public String getMessageNo() {
		return messageNo;
	}
	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}
	public String getChatRoomNo() {
		return chatRoomNo;
	}
	public void setChatRoomNo(String chatRoomNo) {
		this.chatRoomNo = chatRoomNo;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public Timestamp getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(Timestamp messageTime) {
		this.messageTime = messageTime;
	}
	public Integer getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}
	

}
