package com.forumtopic.model;

import java.util.List;

import com.forumpost.model.ForumPostService;
import com.forumpost.model.ForumPostVO;

public class ForumTopicVO implements java.io.Serializable {
	private String forumTopicNo;
	private String banjiNo;
	private String forumTopicName;
	private String content;
	private String rule;
	private String postTemplete;

	public String getForumTopicNo() {
		return forumTopicNo;
	}

	public void setForumTopicNo(String forumTopicNo) {
		this.forumTopicNo = forumTopicNo;
	}

	public String getBanjiNo() {
		return banjiNo;
	}

	public void setBanjiNo(String banjiNo) {
		this.banjiNo = banjiNo;
	}

	public String getForumTopicName() {
		return forumTopicName;
	}

	public void setForumTopicName(String forumTopicName) {
		this.forumTopicName = forumTopicName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getPostTemplete() {
		return postTemplete;
	}

	public void setPostTemplete(String postTemplete) {
		this.postTemplete = postTemplete;
	}

	public List<ForumPostVO> getForumPostList() {
		return new ForumPostService().getByTopicNo(this.forumTopicNo);
	}

	public Integer getForumCommentTotalSize() {
		Integer size = 0;
		for (ForumPostVO forumPostVO : getForumPostList())
			size += forumPostVO.getForumCommentList().size();
		return size;
	}
}
