package com.forumtopic.model;

import java.util.List;



public interface ForumTopicDAO_interface {
	public void insert(ForumTopicVO forumTopicVO);
    public void update(ForumTopicVO forumTopicVO);
    public void delete(String forumTopicNo);
    public ForumTopicVO findByPrimaryKey(String forumTopicNo);
    public List<ForumTopicVO> getAll();
    public List<ForumTopicVO> getByBanJiNo(String banjiNo);

}