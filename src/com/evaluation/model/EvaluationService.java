package com.evaluation.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public Map<String, Integer> getEvaluationWithCourseStudent(String courseNo, String studentNo) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<EvaluationVO> list = dao.getEvaluationWithCourseStudent(courseNo, studentNo);
		for (EvaluationVO evaluationVO : list) {
			map.put("Q" + evaluationVO.getQuestion(), evaluationVO.getAnswer());
		}
		return map;
	}

	public void deleteEvaluationWithCourseStudent(String courseNo, String studentNo) {
		dao.deleteWithCourseStudent(courseNo, studentNo);
	}

	public List<String> getStudentAddedCourseEvaluation(String studentNo) {
		return dao.getStudentAddedCourseEvaluation(studentNo);
	}
}
