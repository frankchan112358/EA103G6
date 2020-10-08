package com.forumcomment.model;

import java.util.List;

public interface ForumCommentDAO_interface {
	public void insert(ForumCommentVO forumCommentVO);
    public void update(ForumCommentVO forumCommentVO);
    public void delete(String forumCommentNo);
    public ForumCommentVO findByPrimaryKey(String forumCommentNo);
    public List<ForumCommentVO> getAll();

}
