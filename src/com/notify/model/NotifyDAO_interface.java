package com.notify.model;

import java.util.List;

public interface NotifyDAO_interface {

	public void insert(NotifyVO notifyVO);
	public List<NotifyVO> getAllByThemself(String userNo);
}
