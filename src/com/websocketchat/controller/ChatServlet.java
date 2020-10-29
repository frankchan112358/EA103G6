package com.websocketchat.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.user.model.UserService;
import com.websocketchat.model.ChatUser;
import com.websocketchat.model.State;
import com.websocketchat.model.WebsocketChatDAO;
import com.websocketchat.model.WebsocketChatVO;




@ServerEndpoint("/ChatServlet/{userNo}")
public class ChatServlet {

	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>(); 
	Gson gson = new Gson();
	
	
	@OnOpen
	public void onOpen(@PathParam("userNo") String userNo, Session userSession) throws IOException {
		sessionsMap.put(userNo, userSession);
		System.out.println(123);
		Set<String> userNos = sessionsMap.keySet(); 
		
		/*********************新增名字********************/
		UserService userService =new UserService();
		ChatUser chatUser=null;
		Set<ChatUser> chatUserList=new HashSet<ChatUser>();
		
		
		for(String splitUserNo: userNos) {
			chatUser=new ChatUser(splitUserNo,userService.getOneUser(splitUserNo).getName());
			chatUserList.add(chatUser);
		}
		
		
		
		
		/*********************新增名字********************/
		State stateMessage = new State("open", userNo, chatUserList);
		String stateMessageJson = gson.toJson(stateMessage);
		
		Collection<Session> sessions = sessionsMap.values();
		for (Session session : sessions) {
			if (session.isOpen()) {
				session.getAsyncRemote().sendText(stateMessageJson);
				System.out.println(stateMessageJson);
			}
		}

		String text = String.format("Session ID = %s, connected; userNo = %s%nusers: %s", userSession.getId(),
				userNo, userNos);
		System.out.println(text);
	}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		WebsocketChatVO chatMessage = gson.fromJson(message, WebsocketChatVO.class);
		String sender = chatMessage.getSender();
		String receiver = chatMessage.getReceiver();
		
		if ("history".equals(chatMessage.getType())) {
			List<String> historyData = WebsocketChatDAO.getHistoryMsg(sender, receiver);
			String historyMsg = gson.toJson(historyData);
			WebsocketChatVO cmHistory = new WebsocketChatVO("history", sender, receiver, historyMsg);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				System.out.println("history = " + gson.toJson(cmHistory));
				return;
			}
		}
		
		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
			userSession.getAsyncRemote().sendText(message);
			WebsocketChatDAO.saveChatMessage(sender, receiver, message);
		}
		
		System.out.println("Message received: " + message);
	}
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNoClose = null;
		Set<String> userNos = sessionsMap.keySet();
		for (String userNo : userNos) {
			if (sessionsMap.get(userNo).equals(userSession)) {
				userNoClose = userNo;
				sessionsMap.remove(userNo);
				break;
			}
		}

		/*********************新增名字********************/
		UserService userService =new UserService();
		ChatUser chatUser=null;
		Set<ChatUser> chatUserList=new HashSet<ChatUser>();
		
		
		for(String splitUserNo: userNos) {
			chatUser=new ChatUser(splitUserNo,userService.getOneUser(splitUserNo).getName());
			chatUserList.add(chatUser);
		}
		
		
		
		
		/*********************新增名字********************/
		
		if (userNoClose != null) {
			State stateMessage = new State("close", userNoClose, chatUserList);
			String stateMessageJson = gson.toJson(stateMessage);
			Collection<Session> sessions = sessionsMap.values();
			for (Session session : sessions) {
				session.getAsyncRemote().sendText(stateMessageJson);
				System.out.println(stateMessageJson);
			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNos);
		System.out.println(text);
	}

}
