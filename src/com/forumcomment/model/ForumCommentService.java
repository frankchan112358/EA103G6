package com.forumcomment.model;

import java.util.List;



public class ForumCommentService {
	
	private ForumCommentDAO_interface dao;

	public ForumCommentService() {
		dao = new ForumCommentJDBCDAO();
	}

	public ForumCommentVO addForumComment(String forumPostNo, String studentNo, String content,
			java.sql.Timestamp updateTime, java.sql.Timestamp createTime) {

		ForumCommentVO forumCommentVO = new ForumCommentVO();

		forumCommentVO.setForumPostNo(forumPostNo);
		forumCommentVO.setStudentNo(studentNo);
		forumCommentVO.setContent(content);
		forumCommentVO.setUpdateTime(updateTime);
		forumCommentVO.setCreateTime(createTime);
		dao.insert(forumCommentVO);

		return forumCommentVO;
	}

	public ForumCommentVO updateComment(String forumCommentNo,String forumPostNo, String studentNo, String content,
			java.sql.Timestamp updateTime, java.sql.Timestamp createTime) {

		ForumCommentVO forumCommentVO = new ForumCommentVO();

		forumCommentVO.setForumCommentNo(forumCommentNo);
		forumCommentVO.setForumPostNo(forumPostNo);
		forumCommentVO.setStudentNo(studentNo);
		forumCommentVO.setContent(content);
		forumCommentVO.setUpdateTime(updateTime);
		forumCommentVO.setCreateTime(createTime);
		dao.update(forumCommentVO);

		return forumCommentVO;
	}

	public void deleteForumPost(String forumCommentNO) {
		dao.delete(forumCommentNO);
	}

	public ForumCommentVO getOneForumPost(String forumCommentNO) {
		return dao.findByPrimaryKey(forumCommentNO);
	}

	public List<ForumCommentVO> getAll() {
		return dao.getAll();
	}
}



