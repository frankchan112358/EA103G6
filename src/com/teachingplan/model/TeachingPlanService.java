package com.teachingplan.model;

import java.util.List;

public class TeachingPlanService {

	private TeachingPlanDAO_interface dao;

	public TeachingPlanService() {
		dao = new TeachingPlanDAO();
	}

	public TeachingPlanVO addTeachingPlan(String teachingPlanNo, String courseNo, Integer week, Integer lesson,
			String planContent) {

		TeachingPlanVO teachingPlanVO = new TeachingPlanVO();

		teachingPlanVO.setCourseNo(courseNo);
		teachingPlanVO.setWeek(week);
		teachingPlanVO.setLesson(lesson);
		teachingPlanVO.setPlanContent(planContent);
		dao.insert(teachingPlanVO);

		return teachingPlanVO;
	}

	public TeachingPlanVO updateTeachingPlan(String teachingPlanNo, String courseNo, Integer week, Integer lesson,
			String planContent) {

		TeachingPlanVO teachingPlanVO = new TeachingPlanVO();

		teachingPlanVO.setTeachingPlanNo(teachingPlanNo);
		teachingPlanVO.setCourseNo(courseNo);
		teachingPlanVO.setWeek(week);
		teachingPlanVO.setLesson(lesson);
		teachingPlanVO.setPlanContent(planContent);
		dao.update(teachingPlanVO);

		return null;
	}

	public void deleteTeachingPlan(String teachingPlanNo) {
		dao.delete(teachingPlanNo);
	}

	public TeachingPlanVO getOneTeachingPlan(String teachingPlanNo) {
		return dao.findByPrimaryKey(teachingPlanNo);
	}

	public List<TeachingPlanVO> getAll() {
		return dao.getAll();
	}

}
