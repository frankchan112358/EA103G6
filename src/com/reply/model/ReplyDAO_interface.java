package com.reply.model;

import java.util.List;

public interface ReplyDAO_interface {

	public void insert(ReplyVO replyVO);

	public void update(ReplyVO replyVO);

	public void delete(String replyNo);

	public ReplyVO findByPrimaryKey(String replyNo);

	public List<ReplyVO> getAll();
	public List<ReplyVO> getAllWithCouseAskNo(String courseAskNo);
}
