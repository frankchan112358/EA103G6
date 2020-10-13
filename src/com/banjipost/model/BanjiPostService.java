package com.banjipost.model;

import java.util.List;

public class BanjiPostService {
	private BanjiPostDAO_interface dao;

	public BanjiPostService() {
		dao = new BanjiPostJNDIDAO();
	}

	public BanjiPostVO addBanjiPost(String banjiNo, String title, String banjiPostContent,
			java.sql.Timestamp updateTime, Integer status) {

		BanjiPostVO banjiPostVO = new BanjiPostVO();

		banjiPostVO.setBanjiNo(banjiNo);
		banjiPostVO.setTitle(title);
		banjiPostVO.setBanjiPostContent(banjiPostContent);
		banjiPostVO.setUpdateTime(updateTime);
		banjiPostVO.setStatus(status);
		dao.insert(banjiPostVO);

		return banjiPostVO;
	}

	public BanjiPostVO updateBanjiPost(String banjiPostNo, String banjiNo, String title, String banjiPostContent,
			java.sql.Timestamp updateTime, Integer status) {

		BanjiPostVO banjiPostVO = new BanjiPostVO();

		banjiPostVO.setBanjiPostNo(banjiPostNo);
		banjiPostVO.setBanjiNo(banjiNo);
		banjiPostVO.setTitle(title);
		banjiPostVO.setBanjiPostContent(banjiPostContent);
		banjiPostVO.setUpdateTime(updateTime);
		banjiPostVO.setStatus(status);
		dao.update(banjiPostVO);

		return banjiPostVO;
	}

	public void deleteBanjiPost(String banjiPostNo) {
		dao.delete(banjiPostNo);
	}

	public BanjiPostVO getOneBanjiPost(String banjiPostNo) {
		return dao.findByPrimaryKey(banjiPostNo);
	}

	public List<BanjiPostVO> getAll() {
		return dao.getAll();
	}
}