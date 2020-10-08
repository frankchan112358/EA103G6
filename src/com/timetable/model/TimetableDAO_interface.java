package com.timetable.model;

import java.util.List;

public interface TimetableDAO_interface {
	public void insert(TimetableVO timetableVO);
	public void update(TimetableVO timetableVO);
	public void delete(String timetableNo);
	public TimetableVO findByPrimaryKey(String timetableNo);
	public List<TimetableVO> getAll();
	}
