package com.notify.model;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Test {
	public static void main(String[] args) {
//		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
//		Timestamp time= new Timestamp(System.currentTimeMillis());
		Timestamp time= new Timestamp(new Date().getTime());//獲取系統當前時間 
		System.out.println("time:"+time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 df.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		String timeStr = df.format(time); 
		System.out.println(timeStr);
		time = Timestamp.valueOf(timeStr); 
		System.out.println(time);
	}
}
