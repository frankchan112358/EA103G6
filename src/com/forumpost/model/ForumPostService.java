package com.forumpost.model;

import java.sql.Timestamp;
import java.util.List;

public class ForumPostService {

	private ForumPostDAO_interface dao;

	public ForumPostService() {
		dao = new ForumPostJDBCDAO();
	}

	public ForumPostVO addForumPost(String forumTopicNo, String studentNo, String title, String content) {

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
//		forumPostVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
		dao.update(forumPostVO);

		return forumPostVO;
	}

	// 增加瀏覽數
	public ForumPostVO addForumPostViews(String forumPostNo, Integer forumPostViews) {
		ForumPostVO forumPostVO = new ForumPostVO();
		forumPostVO.setForumPostNo(forumPostNo);
		forumPostVO.setForumPostViews(forumPostViews);
		dao.addForumPostViews(forumPostVO);
		return forumPostVO;
	}

	// 刪除貼文
	public void deleteForumPost(String forumPostNo) {
		dao.delete(forumPostNo);
	}

	// 取得一個學員的所有文章
	public List<ForumPostVO> getOneSTUDENT(String studentNo) {
		return dao.findOneSTUDENT(studentNo);
	}

	// 取的一篇貼文
	public ForumPostVO getOneForumPost(String forumPostNo) {
		return dao.findOneFpByFpNo(forumPostNo);
	}

	// 取得所有貼文
	public List<ForumPostVO> getAll() {
		return dao.getAll();
	}

	// 熱門文章
	public List<ForumPostVO> getAllHot() {
		return dao.getAllHot();
	}

	// 模糊搜尋
	public List<ForumPostVO> search(String title) {
		return dao.search(title);
	}

	public List<ForumPostVO> getByTopicNo(String forumTopicNo) {
		return dao.getByTopicNo(forumTopicNo);
	}
	
	// 增加瀏覽數2
	public ForumPostVO addForumPostViews2(String forumPostNo) {
		ForumPostVO forumPostVO = getOneForumPost(forumPostNo);
		forumPostVO.setForumPostViews(forumPostVO.getForumPostViews() + 1);
		dao.addForumPostViews(forumPostVO);
		return forumPostVO;
	}
}
