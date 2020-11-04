package com.websocketnotify.model;
import java.util.List;
import com.websocket.jedispool.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class WebsocketNotifyDAO {

	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	public static List<String> getHistoryNotify(String userNO){
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.auth("123456");
			List<String> historyData = jedis.lrange(userNO, 0, -1);
			return historyData;
		}finally {
			jedis.close();

		}
	}
												//傳進的notify設計為json
	public static void saveNotify(String userNo,String notify) {
		Jedis jedis =null;
		try {
			jedis = pool.getResource();
			jedis.auth("123456");
			jedis.rpush(userNo, notify); //rpush新進來的資料就放在右邊(表示最新)
			
		}finally{
			jedis.close();
		}
		
	}
	
	public static Long getIndex(String userNo) {
		Jedis jedis =null;
		try {
			jedis = pool.getResource();
			jedis.auth("123456");
			Long index=jedis.llen(userNo); 
			return index;
			
		}finally{
			jedis.close();
		}
	}
	
	public static void changeValue(String userNo,long index,String jsonMessage) {
		Jedis jedis =null;
		try {
			jedis = pool.getResource();
			jedis.auth("123456");
			jedis.lset(userNo, index, jsonMessage); 
			
		}finally{
			jedis.close();
		}
	}
	
}
