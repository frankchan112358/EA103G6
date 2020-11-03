package com.teachingfile.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.timetable.model.TimetableService;
import com.timetable.model.TimetableVO;
import com.video.model.VideoVO;

public class TeachingFileService {
	
	private TeachingFileDAO_interface dao;
	
	public TeachingFileService() {
		dao = new TeachingFileDAO();
	}
	
	public TeachingFileVO addTeachingFile( String timetableNo, String teachingFileName,	byte[] teachingFile) {

		TeachingFileVO teachingFileVO = new TeachingFileVO();

		teachingFileVO.setTimetableNo(timetableNo);
		teachingFileVO.setTeachingFileName(teachingFileName);
		teachingFileVO.setTeachingFile(teachingFile);
		dao.insert(teachingFileVO);

		return teachingFileVO;
	}

	public TeachingFileVO updateTeachingFile(String teachingFileNo, String timetableNo, String teachingFileName,
			byte[] teachingFile) {

		TeachingFileVO teachingFileVO = new TeachingFileVO();

		teachingFileVO.setTeachingFileNo(teachingFileNo);
		teachingFileVO.setTimetableNo(timetableNo);
		teachingFileVO.setTeachingFileName(teachingFileName);
		teachingFileVO.setTeachingFile(teachingFile);
		dao.update(teachingFileVO);

		return teachingFileVO;
	}
	
	public TeachingFileVO updateTeachingFileNOFILE(String teachingFileNo, String timetableNo, String teachingFileName) {

		TeachingFileVO teachingFileVO = new TeachingFileVO();

		teachingFileVO.setTeachingFileNo(teachingFileNo);
		teachingFileVO.setTimetableNo(timetableNo);
		teachingFileVO.setTeachingFileName(teachingFileName);
		dao.updateNoFile(teachingFileVO);

		return teachingFileVO;
	}

	public void deleteTeachingFile(String teachingFileNo) {
		dao.delete(teachingFileNo);
	}

	public TeachingFileVO getOneTeachingFile(String teachingFileNo) {
		return dao.findByPrimaryKey(teachingFileNo);
	}

	public List<TeachingFileVO> getAll() {
		return dao.getAll();
	}
	
	public TeachingFileVO getOneTeachingFileWithTimetableNo(String timetableNo) {
		TeachingFileVO teachingFileVO = null;
		for (TeachingFileVO item :getAll()) {
			if (item.getTimetableNo().equals(timetableNo))
				teachingFileVO = item;
		}
		return teachingFileVO;
	}

	public String getDatatableJson(String courseNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (TimetableVO timetableVO : new TimetableService().getAllWithCourseNo(courseNo)) {
			JsonObject jsonObject = gson.fromJson(gson.toJson(timetableVO), JsonObject.class);
			jsonObject.addProperty("periodText", timetableVO.getPeriodText());
			TeachingFileVO teachingFileVO = getOneTeachingFileWithTimetableNo(timetableVO.getTimetableNo());
			jsonObject.addProperty("videoNo", teachingFileVO == null ? "" : teachingFileVO.getTeachingFileNo());
			jsonArray.add(jsonObject);
		}
		JsonObject data = new JsonObject();
		data.add("data", jsonArray);
		return gson.toJson(data);
	}
}
