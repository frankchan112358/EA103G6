package com.forumpost.model;


import java.util.*;

public interface ForumPostDAO_interface {
	public void insert(ForumPostVO forumPostVO);
    public void update(ForumPostVO forumPostVO);
    public void delete(String forumPostNo);
    public ForumPostVO findByPrimaryKey(String forumPostNo);
    public List<ForumPostVO> getAll();

}
