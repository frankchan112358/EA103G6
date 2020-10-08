package com.message.model;

import java.sql.Timestamp;

public class MessageService {
	private MessageDAO_interface dao;
	public MessageService() {
		dao = new MessageDAO();
	}
	
	public MessageVO addMessage(String chatroommemberno,String userno,String messageContent,Timestamp messageTime) {

		MessageVO messageVO = new MessageVO();

		
		messageVO.setChatRoomNo(chatroommemberno);
		messageVO.setUserNo(userno);
		messageVO.setMessageContent(messageContent);
		messageVO.setMessageTime(messageTime);
		
		dao.insert(messageVO);

		return messageVO;
	}
	
	public MessageVO getOneChatroommember(String messageno) {
		return dao.findByPrimaryKey(messageno);
		
	}

}
