package com.user.model;

import com.websocket.jedispool.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class UserRedisDAO {

	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	public static void keepRandCode(String userNo, String jsonCode) {
		Jedis jedis=null;
		
		try {
			jedis = pool.getResource();
			jedis.auth("123456");
			String key = new StringBuilder(userNo).append(":").append("changePSW").toString();
			jedis.set(key, jsonCode);
		
		}finally {
			jedis.close();
		}
	}
	
	public static String getRandCode(String userNo) { //回傳json
		Jedis jedis=null;
		
		try {
			jedis = pool.getResource();
			jedis.auth("123456");
			String key = new StringBuilder(userNo).append(":").append("changePSW").toString();
			String code= jedis.get(key);
			return code;
		}finally {
			jedis.close();
		}
	}

}
