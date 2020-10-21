package com.websocketnotify.model;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class NotifyDAO {

	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	public static List<String> getHistoryNotify(String userNO){
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(userNO, 0, -1);
		jedis.close();
		return historyData;
	}
}
