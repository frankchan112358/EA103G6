package com.chatroommember.model;


public class ChatroommemberService {
	private ChatroommemberDAO_interface dao;
	public ChatroommemberService() {
		dao = new ChatroommemberDAO();
	}
	
	public ChatroommemberVO addChatroommember(String chatroommemberno,String userno) {

		ChatroommemberVO chatroomVO = new ChatroommemberVO();

		
		chatroomVO.setChatroommemberno(chatroommemberno);
		chatroomVO.setUserno(userno);
		
		
		dao.insert(chatroomVO);

		return chatroomVO;
	}
	
	public ChatroommemberVO getOneChatroommember(String chatroommemberno) {
		return dao.findByPrimaryKey(chatroommemberno);
		
	}
}
