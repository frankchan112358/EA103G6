package com.reply.model;

import java.util.List;

public class ReplyService {
	private ReplyDAO_interface dao;

	public ReplyService() {
		dao = new ReplyJNDIDAO();
	}

	public ReplyVO addReply(String courseAskNo, String teacherNo, String studentNo, String replyContent,
			java.sql.Timestamp updateTime) {
		ReplyVO replyVO = new ReplyVO();

		replyVO.setCourseAskNo(courseAskNo);
		replyVO.setTeacherNo(teacherNo);
		replyVO.setStudentNo(studentNo);
		replyVO.setReplyContent(replyContent);
		replyVO.setUpdateTime(updateTime);
		dao.insert(replyVO);

		return replyVO;
	}

	public ReplyVO updateReply(String replyNo, String courseAskNo, String teacherNo, String studentNo, String replyContent,
			java.sql.Timestamp updateTime) {
		ReplyVO replyVO = new ReplyVO();

		replyVO.setReplyNo(replyNo);
		replyVO.setCourseAskNo(courseAskNo);
		replyVO.setTeacherNo(teacherNo);
		replyVO.setStudentNo(studentNo);
		replyVO.setReplyContent(replyContent);
		replyVO.setUpdateTime(updateTime);
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
}
