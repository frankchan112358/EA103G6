package com.websocketnotify.model;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class WebsocketNotifyDAO {

	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	public static List<String> getHistoryNotify(String userNO){
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(userNO, 0, -1);
		jedis.close();
		return historyData;
	}
												//傳進的notify設計為json
	public static void saveNotify(String userNo,String notify) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		
		jedis.rpush(userNo, notify); //rpush新進來的資料就放在右邊(表示最新)
		
		jedis.close();
	}
}
