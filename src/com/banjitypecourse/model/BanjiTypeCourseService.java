package com.banjitypecourse.model;

import java.util.List;

public class BanjiTypeCourseService {

	private BanjiTypeCourseDAO_interface dao;

	public BanjiTypeCourseService() {
		dao = new BanjiTypeCourseDAO();
	}

	public BanjiTypeCourseVO addBanjiTypeCourse(String banjiTypeNo, String basicCourseNo) {

		BanjiTypeCourseVO banjiTypeCourseVO = new BanjiTypeCourseVO();

		banjiTypeCourseVO.setBanjiTypeNo(banjiTypeNo);
		banjiTypeCourseVO.setBasicCourseNo(basicCourseNo);
		dao.insert(banjiTypeCourseVO);

		return banjiTypeCourseVO;
	}

	public BanjiTypeCourseVO updateBanjiTypeCourse(String banjiTypeCourseNo, String banjiTypeNo, String basicCourseNo) {

		BanjiTypeCourseVO banjiTypeCourseVO = new BanjiTypeCourseVO();

		banjiTypeCourseVO.setBanjiTypeCourseNo(banjiTypeCourseNo);
		banjiTypeCourseVO.setBanjiTypeNo(banjiTypeNo);
		banjiTypeCourseVO.setBasicCourseNo(basicCourseNo);
		dao.update(banjiTypeCourseVO);

		return banjiTypeCourseVO;
	}

	public void deleteBanjiTypeCourse(String banjiTypeCourseNo) {
		dao.delete(banjiTypeCourseNo);
	}

	public BanjiTypeCourseVO getOneBanjiTypeCourse(String banjiTypeCourseNo) {
		return dao.findByPrimaryKey(banjiTypeCourseNo);
	}

	public List<BanjiTypeCourseVO> gatAll() {
		return dao.getAll();
	}
}
