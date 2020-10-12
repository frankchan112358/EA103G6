package com.evaluation.model;

import java.util.List;

public class EvaluationService {

	private EvaluationDAO_interface dao;

	public EvaluationService() {
		dao = new EvaluationJNDIDAO();
	}

	public EvaluationVO addEvaluation(String courseNo, String studentNo, Integer question, Integer answer) {
		EvaluationVO evaluationVO = new EvaluationVO();
		evaluationVO.setCourseNo(courseNo);
		evaluationVO.setStudentNo(studentNo);
		evaluationVO.setQuestion(question);
		evaluationVO.setAnswer(answer);
		dao.insert(evaluationVO);
		return evaluationVO;

	}

	public EvaluationVO updatEvaluation(String evaluationNo, String courseNo, String studentNo, Integer question,
			Integer answer) {
		EvaluationVO evaluationVO = new EvaluationVO();
		evaluationVO.setEvaluationNo(evaluationNo);
		evaluationVO.setCourseNo(courseNo);
		evaluationVO.setStudentNo(studentNo);
		evaluationVO.setQuestion(question);
		evaluationVO.setAnswer(answer);
		dao.update(evaluationVO);
		return evaluationVO;
	}

	public void deleteEvaluation(String evaluationNo) {
		dao.delete(evaluationNo);
	}

	public EvaluationVO getOneEvaluation(String evaluationNo) {
		return dao.findByPrimaryKey(evaluationNo);
	}

	public List<EvaluationVO> getAll() {
		return dao.getAll();
	}

	public EvaluationQuestion[] getEvaluationQuestionAll() {
		return EvaluationQuestion.values();
	}

	public String getEvaluationQuestionText(Integer num) {
		return EvaluationQuestion.findByNum(num).getText();
	}
}
