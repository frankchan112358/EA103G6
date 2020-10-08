package com.finalscore.model;

import java.util.List;

public class FinalScoreService {

	private FinalScoreDAO_interface dao;

	public FinalScoreService() {
		dao = new FinalScoreDAO();
	}

	public FinalScoreVO addFinalScore(String courseNo, String studentNo, Integer score) {

		FinalScoreVO finalScoreVO = new FinalScoreVO();

		finalScoreVO.setCourseNo(courseNo);
		finalScoreVO.setStudentNo(studentNo);
		finalScoreVO.setScore(score);
		dao.insert(finalScoreVO);

		return finalScoreVO;
	}

	public FinalScoreVO updateFinalScore(String finalScoreNo, String courseNo, String studentNo, Integer score) {

		FinalScoreVO finalScoreVO = new FinalScoreVO();

		finalScoreVO.setFinalScoreNo(finalScoreNo);
		finalScoreVO.setCourseNo(courseNo);
		finalScoreVO.setStudentNo(studentNo);
		finalScoreVO.setScore(score);
		dao.update(finalScoreVO);

		return finalScoreVO;
	}

	public void deleteFinalScore(String finalScoreNo) {
		dao.delete(finalScoreNo);
	}

	public FinalScoreVO getOneFinalScore(String finalScoreNo) {
		return dao.findByPrimaryKey(finalScoreNo);
	}

	public List<FinalScoreVO> getAll() {
		return dao.getAll();
	}
}
