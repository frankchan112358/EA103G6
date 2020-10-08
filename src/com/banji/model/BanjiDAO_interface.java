package com.banji.model;

import java.util.*;

public interface BanjiDAO_interface {

	public void insert(BanjiVO banjiVO);

	public void update(BanjiVO banjiVO);

	public void delete(String banjiNo);

	public BanjiVO findByPrimaryKey(String banjiNo);
	
	public List<BanjiVO> getAll();

}
