package com.banjipost.model;

import java.util.*;

public interface BanjiPostDAO_interface {

	public void insert(BanjiPostVO banjiPostVO);

	public void update(BanjiPostVO banjiPostVO);

	public void delete(String banjiPostNo);

	public BanjiPostVO findByPrimaryKey(String banjiPostNo);

	public List<BanjiPostVO> getAll();
	
}
