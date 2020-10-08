package com.forumtopic.model;

public class ForumTopicVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
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
	
	

}
