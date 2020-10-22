package com.forumcomment.model;

import java.sql.Timestamp;
import java.util.List;


public class ForumCommentService {

	private ForumCommentDAO_interface dao;

	public ForumCommentService() {
		dao = new ForumCommentJDBCDAO();
	}

	// 新增留言
	public ForumCommentVO addForumComment(String forumPostNo, String studentNo, String content ,Timestamp updateTime,Timestamp createTime) {

		ForumCommentVO forumCommentVO = new ForumCommentVO();

		forumCommentVO.setForumPostNo(forumPostNo);
		forumCommentVO.setStudentNo(studentNo);
		forumCommentVO.setContent(content);
		forumCommentVO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		forumCommentVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
		dao.insert(forumCommentVO);

		return forumCommentVO;
	}

	// 修改留言
	public ForumCommentVO updateForumComment(String forumCommentNo, 
			String content) {

		ForumCommentVO forumCommentVO = new ForumCommentVO();

		forumCommentVO.setForumCommentNo(forumCommentNo);
//		forumCommentVO.setForumPostNo(forumPostNo);
//		forumCommentVO.setStudentNo(studentNo);
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
		return dao.getOneFcByFcNo(forumCommentNO);
	}

	public List<ForumCommentVO> getAll() {
		return dao.getAll();
	}

	// 取得文章下的所有留言
	public List<ForumCommentVO> getOneFpFc(String forumPostNo) {
		return dao.getOneFpFc(forumPostNo);
	}

	// 取得一篇文章來進行留言
	public ForumCommentVO addFcByGetFp(String forumPostNo) {
		return dao.addFcByGetFp(forumPostNo);
	}

	// 取得一個文章的回應數
	public Integer getFcResponsesByFpNo(String forumPostNo) {
		return dao.getFcResponsesByFpNo(forumPostNo);
	}

	// 取得一個學員的所有留言
	public List<ForumCommentVO> getOneStudentFc(String studentNo) {
		return dao.getOneStudentFc(studentNo);
	}

}
