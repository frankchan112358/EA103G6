package com.democrat.model;

import java.util.List;

public interface DemocratDAO_interface {
	public void insert(DemocratVO democratVO);

	public void update(DemocratVO democratVO);

	public void delete(String democratNo);

	public DemocratVO getByPrimaryKey(String democratNo);

	public List<DemocratVO> getAll();
}
