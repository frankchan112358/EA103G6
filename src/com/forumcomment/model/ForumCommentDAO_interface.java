package com.forumcomment.model;

import java.util.List;

import com.forumpost.model.ForumPostVO;

public interface ForumCommentDAO_interface {
	public void insert(ForumCommentVO forumCommentVO);
    public void update(ForumCommentVO forumCommentVO);
    public void delete(String forumCommentNo);
    public ForumCommentVO findByPrimaryKey(String forumCommentNo);
    public List<ForumCommentVO> getAll();

}
