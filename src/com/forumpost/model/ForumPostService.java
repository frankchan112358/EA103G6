package com.forumpost.model;

import java.sql.Timestamp;
import java.util.List;

public class ForumPostService {

	private ForumPostDAO_interface dao;

	public ForumPostService() {
		dao = new ForumPostJDBCDAO();
	}

	public ForumPostVO addForumPost(String forumTopicNo, String studentNo, String title, String content
			) {

		ForumPostVO forumPostVO = new ForumPostVO();

		forumPostVO.setForumTopicNo(forumTopicNo);
		forumPostVO.setStudentNo(studentNo);
		forumPostVO.setTitle(title);
		forumPostVO.setContent(content);
		forumPostVO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		forumPostVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
		dao.insert(forumPostVO);

		return forumPostVO;
	}

	public ForumPostVO updateForumPost(String forumPostNo, String forumTopicNo, String studentNo, String title,
			String content) {

		ForumPostVO forumPostVO = new ForumPostVO();

		forumPostVO.setForumPostNo(forumPostNo);
		forumPostVO.setForumTopicNo(forumTopicNo);
		forumPostVO.setStudentNo(studentNo);
		forumPostVO.setTitle(title);
		forumPostVO.setContent(content);
		forumPostVO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		forumPostVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
		dao.update(forumPostVO);

		return forumPostVO;
	}

	public void deleteForumPost(String forumPostNo) {
		dao.delete(forumPostNo);
	}

	public ForumPostVO getOneForumPost(String forumPostNo) {
		return dao.findByPrimaryKey(forumPostNo);
	}

	public List<ForumPostVO> getAll() {
		return dao.getAll();
	}
}
