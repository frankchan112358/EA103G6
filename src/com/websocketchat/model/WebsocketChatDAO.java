package com.websocketchat.model;

import java.util.List;

import com.websocket.jedispool.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class WebsocketChatDAO {
	
	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	public static List<String> getHistoryMsg(String sender, String receiver) {
		Jedis jedis = null;
		try {
			String key = new StringBuilder(sender).append(":").append(receiver).toString();
			jedis = pool.getResource();
			jedis.auth("123456");
			List<String> historyData = jedis.lrange(key, 0, -1);
			return historyData;
		
		}finally {
			jedis.close();
		}
	}

	public static void saveChatMessage(String sender, String receiver, String message) {
		// 對雙方來說，都要各存著歷史聊天記錄，一個訊息存進兩個key
		Jedis jedis =null;
		try {
			String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
			String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
			jedis = pool.getResource();
			jedis.auth("123456");
			jedis.rpush(senderKey, message);
			jedis.rpush(receiverKey, message);
			//rpush新進來的資料就放在右邊(表示最新)
		}finally {
			jedis.close();
		}
	}

}
