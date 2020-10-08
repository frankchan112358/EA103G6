package com.chatroom.model;

import com.chatroom.model.ChatroomVO;

public class ChatroomService {
	private ChatroomDAO_interface dao;
	public ChatroomService() {
		dao = new ChatroomDAO();
	}
	
	public ChatroomVO addChatroom(String chatroomname,Integer chatroomtype) {

		ChatroomVO chatroomVO = new ChatroomVO();

		
		chatroomVO.setChatroomname(chatroomname);
		chatroomVO.setChatroomtype(chatroomtype);
		
		
		dao.insert(chatroomVO);

		return chatroomVO;
	}
	
	public ChatroomVO updateStudent(String chatroomname) {
		
		ChatroomVO chatroomVO = new ChatroomVO();
		chatroomVO.setChatroomname(chatroomname);

		
		dao.update(chatroomVO);
		return chatroomVO;
		
	}
	
	public void deletetChatroom(String chatroomno) {
		dao.delete(chatroomno);
	}
	
	public ChatroomVO getOneChatroom(String chatroomno) {
		return dao.findByPrimaryKey(chatroomno);
		
	}
	
	

}
