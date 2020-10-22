package com.forumcomment.model;

import java.util.List;

public interface ForumCommentDAO_interface {
	public void insert(ForumCommentVO forumCommentVO);
    public void update(ForumCommentVO forumCommentVO);
    public void delete(String forumCommentNo);
    public List<ForumCommentVO> getAll();
	public List<ForumCommentVO> getOneFpFc(String forumCommentNo);
	public List<ForumCommentVO> getOneStudentFc(String studentNo);
	public ForumCommentVO addFcByGetFp(String forumPostNo);
	public ForumCommentVO getOneFcByFcNo(String forumCommentNo);
	public Integer getFcResponsesByFpNo(String forumPostNo);


}
