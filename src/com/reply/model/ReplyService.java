package com.reply.model;

import java.util.List;
import com.banji.model.BanjiVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.student.model.StudentService;
import com.user.model.UserService;
import com.user.model.UserVO;

public class ReplyService {
	private ReplyDAO_interface dao;

	public ReplyService() {
		dao = new ReplyJNDIDAO();
	}

	public ReplyVO addReply(String courseAskNo, String replyContent, java.sql.Timestamp updateTime, String userNo) {
		ReplyVO replyVO = new ReplyVO();

		replyVO.setCourseAskNo(courseAskNo);
		replyVO.setTeacherNo(null);
		replyVO.setStudentNo(null);
		replyVO.setReplyContent(replyContent);
		replyVO.setUpdateTime(updateTime);
		replyVO.setUserNo(userNo);
		dao.insert(replyVO);

		return replyVO;
	}

	public ReplyVO updateReply(String replyNo, String courseAskNo, String replyContent, java.sql.Timestamp updateTime,
			String userNo) {
		ReplyVO replyVO = new ReplyVO();

		replyVO.setReplyNo(replyNo);
		replyVO.setCourseAskNo(courseAskNo);
		replyVO.setTeacherNo(null);
		replyVO.setStudentNo(null);
		replyVO.setReplyContent(replyContent);
		replyVO.setUpdateTime(updateTime);
		replyVO.setUserNo(userNo);
		dao.update(replyVO);

		return replyVO;
	}

	public void deleteReply(String replyNo) {
		dao.delete(replyNo);
	}

	public ReplyVO getOneReply(String replyNo) {
		return dao.findByPrimaryKey(replyNo);
	}

	public List<ReplyVO> getAll() {
		return dao.getAll();
	}

	public List<ReplyVO> getAllWithCouseAskNo(String courseAskNo) {
		return dao.getAllWithCouseAskNo(courseAskNo);
	}

	public String getAllJsonWithCourseAskNo(String courseAskNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (ReplyVO replyVO : getAllWithCouseAskNo(courseAskNo)) {
			JsonObject jsonObject = gson.fromJson(gson.toJson(replyVO), JsonObject.class);
			UserVO userVO = new UserService().getOneUser(replyVO.getUserNo());
			jsonObject.addProperty("userNo", userVO.getUserNo());
			jsonObject.addProperty("userName", userVO.getName());
			jsonObject.addProperty("userType", userVO.getType());
			jsonArray.add(jsonObject);
		}
		String jsonStr = gson.toJson(jsonArray);
		return jsonStr;
	}

	public String getOneReplyJson(String replyNo) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		ReplyVO replyVO = dao.findByPrimaryKey(replyNo);
		JsonObject jsonObject = gson.fromJson(gson.toJson(replyVO), JsonObject.class);
		UserVO userVO = new UserService().getOneUser(replyVO.getUserNo());
		jsonObject.addProperty("userNo", userVO.getUserNo());
		jsonObject.addProperty("userName", userVO.getName());
		jsonObject.addProperty("userType", userVO.getType());
		return gson.toJson(jsonObject);
	}
}
