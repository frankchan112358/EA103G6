package com.teachingfile.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.google.gson.JsonArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;



public class TeachingFileService {
	
	private TeachingFileDAO_interface dao;
	
	public TeachingFileService() {
		dao = new TeachingFileDAO();
	}
	
	public TeachingFileVO addTeachingFile( String courseNo, String teachingFileName, byte[] teachingFile) {

		TeachingFileVO teachingFileVO = new TeachingFileVO();

		teachingFileVO.setCourseNo(courseNo);
		teachingFileVO.setTeachingFileName(teachingFileName);
		teachingFileVO.setTeachingFile(teachingFile);
		dao.insert(teachingFileVO);

		return teachingFileVO;
	}

	public TeachingFileVO updateTeachingFile(String teachingFileNo, String courseNo, String teachingFileName,
			byte[] teachingFile) {

		TeachingFileVO teachingFileVO = new TeachingFileVO();

		teachingFileVO.setTeachingFileNo(teachingFileNo);
		teachingFileVO.setCourseNo(courseNo);
		teachingFileVO.setTeachingFileName(teachingFileName);
		teachingFileVO.setTeachingFile(teachingFile);
		dao.update(teachingFileVO);

		return teachingFileVO;
	}
	
	public TeachingFileVO updateTeachingFileNOFILE(String teachingFileNo, String courseNo, String teachingFileName) {

		TeachingFileVO teachingFileVO = new TeachingFileVO();

		teachingFileVO.setTeachingFileNo(teachingFileNo);
		teachingFileVO.setCourseNo(courseNo);
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
	
	public List<TeachingFileVO> getAllWithCourseNo(String courseNo) {
		List<TeachingFileVO> list = new ArrayList<TeachingFileVO>();
		for (TeachingFileVO teachingFileVO : getAll()) {
			if (courseNo.equals(teachingFileVO.getCourseNo()))
				list.add(teachingFileVO);
		}
		return list;
	}

	public String getDatatableJson(String courseNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		JsonArray jsonArray = new JsonArray();
		for (TeachingFileVO teachingFileVO : getAllWithCourseNo(courseNo)) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("teachingFileNo", teachingFileVO.getTeachingFileNo());
			jsonObject.addProperty("teachingFileName", teachingFileVO.getTeachingFileName());
			jsonObject.addProperty("courseNo", teachingFileVO.getCourseNo());
			jsonArray.add(jsonObject);
		}
		JsonObject data = new JsonObject();
		data.add("data", jsonArray);

		return gson.toJson(data);
	}
}
