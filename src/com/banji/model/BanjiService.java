package com.banji.model;

import java.util.List;

public class BanjiService {
	private BanjiDAO_interface dao;

	public BanjiService() {
		dao = new BanjiJNDIDAO();
	}

	public BanjiVO addBanji(String empNo, String banjiTypeNo, java.sql.Date startDay, java.sql.Date endDay,
			String banjiName, Integer classHours, Integer numberOfStudent, String classroomNo, Integer status,
			String banjiContent) {

		BanjiVO banjiVO = new BanjiVO();

		banjiVO.setEmpNo(empNo);
		banjiVO.setBanjiTypeNo(banjiTypeNo);
		banjiVO.setStartDay(startDay);
		banjiVO.setEndDay(endDay);
		banjiVO.setBanjiName(banjiName);
		banjiVO.setClassHours(classHours);
		banjiVO.setNumberOfStudent(numberOfStudent);
		banjiVO.setClassroomNo(classroomNo);
		banjiVO.setStatus(status);
		banjiVO.setBanjiContent(banjiContent);
		dao.insert(banjiVO);

		return banjiVO;

	}

	public BanjiVO updateBanji(String banjiNo,String empNo, String banjiTypeNo, java.sql.Date startDay, java.sql.Date endDay,
			String banjiName, Integer classHours, Integer numberOfStudent, String classroomNo, Integer status,
			String banjiContent) {

		BanjiVO banjiVO = new BanjiVO();

		banjiVO.setBanjiNo(banjiNo);
		banjiVO.setEmpNo(empNo);
		banjiVO.setBanjiTypeNo(banjiTypeNo);
		banjiVO.setStartDay(startDay);
		banjiVO.setEndDay(endDay);
		banjiVO.setBanjiName(banjiName);
		banjiVO.setClassHours(classHours);
		banjiVO.setNumberOfStudent(numberOfStudent);
		banjiVO.setClassroomNo(classroomNo);
		banjiVO.setStatus(status);
		banjiVO.setBanjiContent(banjiContent);
		dao.update(banjiVO);

		return banjiVO;
	}

	public void deleteBanji(String banjiNo) {
		dao.delete(banjiNo);
	}

	public BanjiVO getOneBanji(String banjiNo) {
		return dao.findByPrimaryKey(banjiNo);
	}

	public List<BanjiVO> getAll() {
		return dao.getAll();
	}
}
