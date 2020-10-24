package com.video.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.Part;

public class VideoService {
	private VideoDAO_interface dao;

	public VideoService() {
		dao = new VideoDAO();
	}

	public VideoVO addVideo(String timetableNo, String videoName, String filePath, Part video) {

		VideoVO videoVO = new VideoVO();

		videoVO.setTimetableNo(timetableNo);
		videoVO.setVideoName(videoName);

		VideoDAO.insert2(videoVO, video, filePath);

		return videoVO;
	}

	public VideoVO updateVideo(String videoNo, String timetableNo, String videoName, byte[] video) {

		VideoVO videoVO = new VideoVO();

		videoVO.setVideoNo(videoNo);
		videoVO.setTimetableNo(timetableNo);
		videoVO.setVideoName(videoName);
		videoVO.setVideo(video);
		dao.update(videoVO);

		return videoVO;
	}

	public void deleteVideo(String videoNo) {
		dao.delete(videoNo);
	}

	public VideoVO getOneVideo(String videoNo) {
		return dao.findByPrimaryKey(videoNo);
	}

	public List<VideoVO> getAll() {
		return dao.getAll();
	}

	public VideoVO getOneVideoWithTimetableNo(String timetableNo) {
		VideoVO videoVO = null;
		for (VideoVO item : getAll()) {
			if (item.getTimetableNo().equals(timetableNo))
				videoVO = item;
		}
		return videoVO;
	}
}