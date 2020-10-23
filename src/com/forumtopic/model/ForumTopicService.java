package com.forumtopic.model;

import java.util.ArrayList;
import java.util.List;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;


public class ForumTopicService {

	private ForumTopicDAO_interface dao;

	public ForumTopicService() {
		dao = new ForumTopicJNDIDAO();
	}

	public ForumTopicVO addForumTopic(String banjiNo, String forumTopicName) {

		ForumTopicVO forumTopicVO = new ForumTopicVO();

		forumTopicVO.setBanjiNo(banjiNo);
		forumTopicVO.setForumTopicName(forumTopicName);
		dao.insert(forumTopicVO);

		return forumTopicVO;
	}

	public ForumTopicVO updateForumTopic(String forumTopicNo, String banjiNo, String forumTopicName, String content,
			String rule, String postTemplete) {

		ForumTopicVO forumTopicVO = new ForumTopicVO();

		forumTopicVO.setForumTopicNo(forumTopicNo);
		forumTopicVO.setBanjiNo(banjiNo);
		forumTopicVO.setForumTopicName(forumTopicName);
		forumTopicVO.setContent(content);
		forumTopicVO.setRule(rule);
		forumTopicVO.setPostTemplete(postTemplete);

		dao.update(forumTopicVO);

		return forumTopicVO;
	}

	public void deleteForumTopic(String forumTopicNo) {
		dao.delete(forumTopicNo);
	}

	public ForumTopicVO getOneForumTopic(String forumTopicNo) {
		return dao.findByPrimaryKey(forumTopicNo);
	}

	public List<ForumTopicVO> getAll() {
		return dao.getAll();
	}
	
	public List<ForumTopicVO> getByBanJiNo(String banJiNo) {
		return dao.getByBanJiNo(banJiNo);
	}
	public List<ForumTopicVO> getAllWithBanji(String banjiNo) {
		List<ForumTopicVO>list = new ArrayList<ForumTopicVO>();
		BanjiVO banjiVO = new BanjiService().getOneBanji(banjiNo);
		
		return list;
	}
}
