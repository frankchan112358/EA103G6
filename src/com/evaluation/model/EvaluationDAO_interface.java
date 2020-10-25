package com.evaluation.model;

import java.util.List;

public interface EvaluationDAO_interface {
	public void insert(EvaluationVO evaluationVO);

	public void update(EvaluationVO evaluationVO);

	public void delete(String evaluationNo);

	public EvaluationVO findByPrimaryKey(String evaluationNo);

	public List<EvaluationVO> getAll();

	public List<EvaluationVO> findByCourseNo(String courseNo);
	
	public List<EvaluationVO> getEvaluationWithCourseStudent(String courseNo, String studentNo);
	
	public void deleteWithCourseStudent(String courseNo, String studentNo);
	
	public List<String> getStudentAddedCourseEvaluation(String studentNo);
}
