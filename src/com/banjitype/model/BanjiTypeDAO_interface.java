package com.banjitype.model;

import java.util.*;

import com.banji.model.BanjiVO;

public interface BanjiTypeDAO_interface {
	
	public void insert(BanjiTypeVO banjiTypeVO);

	public void update(BanjiTypeVO banjiTypeVO);

	public void delete(String banjiTypeNo);

	public BanjiTypeVO findByPrimaryKey(String banjiTypeNo);
	
	public List<BanjiTypeVO> getAll();
	
	public Set<BanjiVO>getBanTypeByBanjiTypeNo(String banjiTypeNo);
}
