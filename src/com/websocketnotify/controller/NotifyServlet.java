package com.websocketnotify.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

@ServerEndpoint("/NotifyServlet/{userNo}")
public class NotifyServlet {

	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userNo") String userNo, Session userSession) throws IOException {
		sessionsMap.put(userNo,userSession);
	}
}


