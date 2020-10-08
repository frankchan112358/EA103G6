package com.report.model;

import java.util.List;


public class ReportService {

	private ReportDAO_interface dao;

	public ReportService() {
		dao = new ReportJDBCDAO();
	}

	public ReportVO addReport(String forumPostNo, String forumCommentNo, String studentNo, Integer type,
			String description, java.sql.Timestamp reportTime) {

		ReportVO reportVO = new ReportVO();

		reportVO.setForumPostNo(forumPostNo);
		reportVO.setForumCommentNo(forumCommentNo);
		reportVO.setStudentNo(studentNo);
		reportVO.setType(type);
		reportVO.setDescription(description);
		reportVO.setReportTime(reportTime);
		dao.insert(reportVO);

		return reportVO;
	}

	public ReportVO updateReport(String reportNo, String forumPostNo, String forumCommentNo, String studentNo, Integer type,
			String description, java.sql.Timestamp reportTime) {

		ReportVO reportVO = new ReportVO();

		reportVO.setReportNo(reportNo);
		reportVO.setForumPostNo(forumPostNo);
		reportVO.setForumCommentNo(forumCommentNo);
		reportVO.setStudentNo(studentNo);
		reportVO.setType(type);
		reportVO.setDescription(description);
		reportVO.setReportTime(reportTime);
		dao.update(reportVO);

		return reportVO;
	}

	public void deleteReport(String reportNo) {
		dao.delete(reportNo);
	}

	public ReportVO getOneReport(String reportNo) {
		return dao.findByPrimaryKey(reportNo);
	}

	public List<ReportVO> getAll() {
		return dao.getAll();
	}
}
