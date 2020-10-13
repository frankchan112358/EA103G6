package com.forumcomment.model;

import java.sql.Timestamp;
import java.util.List;



public class ForumCommentService {
	
	private ForumCommentDAO_interface dao;

	public ForumCommentService() {
		dao = new ForumCommentJDBCDAO();
	}

	public ForumCommentVO addForumComment(String forumPostNo, String studentNo, String content
			) {

		ForumCommentVO forumCommentVO = new ForumCommentVO();

		forumCommentVO.setForumPostNo(forumPostNo);
		forumCommentVO.setStudentNo(studentNo);
		forumCommentVO.setContent(content);
		forumCommentVO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		forumCommentVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
		dao.insert(forumCommentVO);

		return forumCommentVO;
	}

	public ForumCommentVO updateForumComment(String forumCommentNo,String forumPostNo, String studentNo, String content
			) {

		ForumCommentVO forumCommentVO = new ForumCommentVO();

		forumCommentVO.setForumCommentNo(forumCommentNo);
		forumCommentVO.setForumPostNo(forumPostNo);
		forumCommentVO.setStudentNo(studentNo);
		forumCommentVO.setContent(content);
		forumCommentVO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//		forumCommentVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
		dao.update(forumCommentVO);

		return forumCommentVO;
	}

	public void deleteForumComment(String forumCommentNO) {
		dao.delete(forumCommentNO);
	}

	public ForumCommentVO getOneForumComment(String forumCommentNO) {
		return dao.findByPrimaryKey(forumCommentNO);
	}

	public List<ForumCommentVO> getAll() {
		return dao.getAll();
	}

	
}



