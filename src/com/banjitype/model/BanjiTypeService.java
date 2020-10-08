package com.banjitype.model;

import java.util.List;
import java.util.Set;

import com.banji.model.BanjiVO;

public class BanjiTypeService {
	private BanjiTypeDAO_interface dao;

	public BanjiTypeService() {
		dao = new BanjiTypeJNDIDAO();
	}

	public BanjiTypeVO addBanjiType(String banjiTypeName, Integer classHours, String banjiTypeContent) {

		BanjiTypeVO banjiTypeVO = new BanjiTypeVO();

		banjiTypeVO.setBanjiTypeName(banjiTypeName);
		banjiTypeVO.setClassHours(classHours);
		banjiTypeVO.setBanjiTypeContent(banjiTypeContent);

		dao.insert(banjiTypeVO);

		return banjiTypeVO;
	}

	public BanjiTypeVO updateBanjiType(String banjiTypeNo, String banjiTypeName, Integer classHours,
			String banjiTypeContent) {

		BanjiTypeVO banjiTypeVO = new BanjiTypeVO();

		banjiTypeVO.setBanjiTypeNo(banjiTypeNo);
		banjiTypeVO.setBanjiTypeName(banjiTypeName);
		banjiTypeVO.setClassHours(classHours);
		banjiTypeVO.setBanjiTypeContent(banjiTypeContent);

		dao.update(banjiTypeVO);

		return banjiTypeVO;
	}

	public void deleteBanjiType(String banjiTypeNo) {
		dao.delete(banjiTypeNo);
	}

	public BanjiTypeVO getOneBanjiType(String banjiTypeNo) {
		return dao.findByPrimaryKey(banjiTypeNo);
	}

	public List<BanjiTypeVO> getAll() {
		return dao.getAll();
	}

	public Set<BanjiVO> getBanTypeByBanjiTypeNo(String banjiTypeNo) {
		return dao.getBanTypeByBanjiTypeNo(banjiTypeNo);
	}
}
