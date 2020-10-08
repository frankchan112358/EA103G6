package com.healthlog.model;

import java.util.List;

public interface HealthLogDAO_interface {
	public void insert(HealthLogVO healthLogVO);

	public void update(HealthLogVO healthLogVO);

	public void delete(String healthLogNo);

	public HealthLogVO findByPrimaryKey(String healthLogNo);

	public List<HealthLogVO> getAll();
}
