package com.forumpost.model;


import java.util.*;



public interface ForumPostDAO_interface {
	public void insert(ForumPostVO forumPostVO);
    public void update(ForumPostVO forumPostVO);
    public void delete(String forumPostNo);
	public void addForumPostViews(ForumPostVO forumPostVO);
    public ForumPostVO findOneFpByFpNo(String forumPostNo);
    public List<ForumPostVO> findOneSTUDENT(String studentNo);
	public List<ForumPostVO> search(String title);
    public List<ForumPostVO> getAll();
	public List<ForumPostVO> getAllHot();
	public List<ForumPostVO> getByTopicNo(String forumTopicNo);

    

}
