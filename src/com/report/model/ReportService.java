package com.report.model;

import java.sql.Timestamp;
import java.util.List;




public class ReportService {

	private ReportDAO_interface dao;

	public ReportService() {
		dao = new ReportJDBCDAO();
	}

	public ReportVO addReport(String forumPostNo, String forumCommentNo, String studentNo, Integer type,
			String description, Integer status) {

		ReportVO reportVO = new ReportVO();

		reportVO.setForumPostNo(forumPostNo);
		reportVO.setForumCommentNo(forumCommentNo);
		reportVO.setStudentNo(studentNo);
		reportVO.setType(type);
		reportVO.setDescription(description);
		reportVO.setReportTime(new Timestamp(System.currentTimeMillis()));
		reportVO.setStatus(status);

		dao.insert(reportVO);

		return reportVO;
	}

	public ReportVO updateReportVO(String reportNo, String forumPostNo, String forumCommentNo, String studentNo, Integer type,
			String description, Timestamp reportTime, Integer status) {

		ReportVO reportVO = new ReportVO();

		reportVO.setReportNo(reportNo);
		reportVO.setForumPostNo(forumPostNo);
		reportVO.setForumCommentNo(forumCommentNo);
		reportVO.setStudentNo(studentNo);
		reportVO.setType(type);
		reportVO.setDescription(description);
		reportVO.setReportTime(new Timestamp(System.currentTimeMillis()));
		reportVO.setStatus(status);

		dao.update(reportVO);

		return reportVO;
	}

	public void deleteReportVO(String reportNo) {
		dao.delete(reportNo);
	}

	public ReportVO getOneReport(String reportNo) {
		return dao.findByPrimaryKey(reportNo);
	}

	public List<ReportVO> getAll() {
		return dao.getAll();
	}
	
	public ReportType[] getReportTypeAll() {
		return ReportType.values();
	}

	public ReportStatus[] getReportStatusAll() {
		return ReportStatus.values();
	}
	
	public String getReportTypeText(Integer num) {
		return ReportType.findByNum(num).getText();
	}
	
	public String getReportStatusText(Integer num) {
		return ReportStatus.findByNum(num).getText();
	}
}
