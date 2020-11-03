package com.reply.model;

import java.util.List;

public class ReplyService {
	private ReplyDAO_interface dao;

	public ReplyService() {
		dao = new ReplyJNDIDAO();
	}

	public ReplyVO addReply(String courseAskNo,  String replyContent,
			java.sql.Timestamp updateTime,String userNo) {
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

	public ReplyVO updateReply(String replyNo, String courseAskNo, String replyContent,
			java.sql.Timestamp updateTime,String userNo) {
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
}
