package com.notify.model;

import java.sql.Timestamp;
import java.util.List;

public class NotifyService {
	private NotifyDAO_interface dao;
	
	public NotifyService() {
		dao=new NotifyDAO();
	}
	
	public void addNotify(String userNo,String notifyContent,Timestamp notifyDate) {
		NotifyVO notifyVO=new NotifyVO();
		notifyVO.setUserNo(userNo);
		notifyVO.setNotifyContent(notifyContent);
		notifyVO.setNotifyDate(notifyDate);
		dao.insert(notifyVO);
	}
	
	public List<NotifyVO> getAllByThemself(String userNo){
		return dao.getAllByThemself(userNo);
		
	}
	
}
