package com.video.model;

import java.util.List;

import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.timetable.model.TimetableService;
import com.timetable.model.TimetableVO;

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

	public String getDatatableJson(String courseNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		TimetableService timetableService = new TimetableService();
		JsonArray jsonArray = new JsonArray();
		for (TimetableVO timetableVO : new TimetableService().getAllWithCourseNo(courseNo)) {  //取到timetableVO list
			JsonObject jsonObject = gson.fromJson(timetableService.getTimetableJsonObject(timetableVO), JsonObject.class); //換成json物件
			jsonObject.addProperty("periodText", timetableVO.getPeriodText()); //列舉值的部分
			VideoVO videoVO = getOneVideoWithTimetableNo(timetableVO.getTimetableNo()); //取得單個videoVO 物件
			jsonObject.addProperty("videoNo", videoVO == null ? "" : videoVO.getVideoNo()); //取得videoNo
			jsonArray.add(jsonObject); //加到jsonArray
		}
		JsonObject data = new JsonObject();
		data.add("data", jsonArray);
		return gson.toJson(data);
	}
}