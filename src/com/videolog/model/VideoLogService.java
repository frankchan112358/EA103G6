package com.videolog.model;

import java.util.List;

public class VideoLogService {
	
	private VideoLogDAO_interface dao;
	
	public VideoLogService() {
		dao = new VideoLogDAO();
	} 
	
	public VideoLogVO addVideoLog(String videoNo, Integer watchTime, Integer status) {
		VideoLogVO videoLogVO = new VideoLogVO();
		
		videoLogVO.setVideoNo(videoNo);
		videoLogVO.setWatchTime(watchTime);
		videoLogVO.setStatus(status);
		dao.insert(videoLogVO);
		
		return videoLogVO;
	}

	public VideoLogVO updateVideoLog(String videoLogNo, String videoNo, Integer watchTime, Integer status) {
		VideoLogVO videoLogVO = new VideoLogVO();
		videoLogVO.setVideoLogNo(videoLogNo);
		videoLogVO.setVideoNo(videoNo);
		videoLogVO.setWatchTime(watchTime);
		videoLogVO.setStatus(status);
		dao.update(videoLogVO);
		
		return videoLogVO;
	}
	
	public void deleteVideoLog(String videoLogNo) {
		dao.delete(videoLogNo);
	}
	
	public VideoLogVO getOneVideoLog(String videoLogNo) {
		return dao.findByPrimaryKey(videoLogNo);
	}

	public List<VideoLogVO> getAll() {
		return dao.getAll();
	}
}
