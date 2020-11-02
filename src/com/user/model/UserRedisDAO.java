package com.user.model;

import com.websocket.jedispool.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class UserRedisDAO {

	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	
	public static void keepRandCode(String userNo, String jsonCode) {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		String key = new StringBuilder(userNo).append(":").append("changePSW").toString();
		jedis.set(key, jsonCode);
		
		jedis.close();
	}
	
	public static String getRandCode(String userNo) { //回傳json
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		String key = new StringBuilder(userNo).append(":").append("changePSW").toString();
		String code= jedis.get(key);
		return code;
	}

}
