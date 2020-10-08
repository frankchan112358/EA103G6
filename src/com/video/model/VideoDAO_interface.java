package com.video.model;

import java.util.List;

public interface VideoDAO_interface {
	public void insert(VideoVO videoVO);
	public void update(VideoVO videoVO);
	public void delete(String videoNo);
	public VideoVO findByPrimaryKey(String videoNo);
	public List<VideoVO> getAll();
}
