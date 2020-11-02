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
	
	public FinalScoreVO updateScore(String courseNo, String studentNo, Integer score) {

		FinalScoreVO finalScoreVO = new FinalScoreVO();

		finalScoreVO.setCourseNo(courseNo);
		finalScoreVO.setStudentNo(studentNo);
		finalScoreVO.setScore(score);
		FinalScoreDAO.updateScore(finalScoreVO);

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
	
	public Integer getScore(String courseNo, String studentNo) {
		Integer score = null;
		for (FinalScoreVO finalScoreVO : getAll()) {
			if(courseNo.equals(finalScoreVO.getCourseNo()) && studentNo.equals(finalScoreVO.getStudentNo())) {
				score = finalScoreVO.getScore();
				break;
			}
		}
		return score;
	}
	
}
