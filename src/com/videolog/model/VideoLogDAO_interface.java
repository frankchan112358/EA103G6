package com.videolog.model;

import java.util.List;

public interface VideoLogDAO_interface {
	public void insert(VideoLogVO videoLogVO);
	public void update(VideoLogVO videoLogVO);
	public void delete(String videoLogNo);
	public VideoLogVO findByPrimaryKey(String videoLogNo);
	public List<VideoLogVO> getAll();
}
