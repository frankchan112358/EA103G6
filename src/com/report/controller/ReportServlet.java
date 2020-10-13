package com.report.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.report.model.ReportService;
import com.report.model.ReportVO;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReportServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String reportNo = req.getParameter("reportNo");
				if (reportNo == null || (reportNo.trim()).length() == 0) {
					errorMsgs.add("請輸入檢舉編號");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report/forum_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				ReportService reportSvc = new ReportService();
				ReportVO reportVO = reportSvc.getOneReport(reportNo);
				if (reportVO == null) {
					errorMsgs.add("查無資料");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report/forum_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("reportVO", reportVO);
				String url = "/back-end/report/listOneReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report/forum_select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String reportNo = req.getParameter("reportNo");
				ReportService reportSvc = new ReportService();
				ReportVO reportVO = reportSvc.getOneReport(reportNo);
				req.setAttribute("reportVO", reportVO);
				String url = "/back-end/report/update_report_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report/listAllReport.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String reportNo = req.getParameter("reportNo");
				String forumPostNo = req.getParameter("forumPostNo");
				String forumCommentNo = req.getParameter("forumCommentNo");
				String studentNo = req.getParameter("studentNo");
				Integer type = new Integer(req.getParameter("type").trim());
				String description = req.getParameter("description");
				Timestamp reportTime=new java.sql.Timestamp((new java.util.Date()).getTime());
				Integer status = new Integer(req.getParameter("status").trim());
				ReportVO reportVO = new ReportVO();
				reportVO.setReportNo(reportNo);
				reportVO.setForumPostNo(forumPostNo);
				reportVO.setForumCommentNo(forumCommentNo);
				reportVO.setStudentNo(studentNo);
				reportVO.setType(type);
				reportVO.setDescription(description);
				reportVO.setReportTime(reportTime);
				reportVO.setStatus(status);
				
				ReportService reportSvc = new ReportService();
				reportVO = reportSvc.updateReportVO(reportNo, forumPostNo, forumCommentNo, studentNo, type, description, reportTime, status);
				req.setAttribute("reportVO", reportVO);
				String url = "/back-end/report/listOneReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report/update_report_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String forumPostNo = req.getParameter("forumPostNo");
				String forumCommentNo = req.getParameter("forumCommentNo");
				String studentNo = req.getParameter("studentNo");
				Integer type = new Integer(req.getParameter("type").trim());
				String description = req.getParameter("description");
				Integer status = new Integer(req.getParameter("status").trim());
				ReportVO reportVO = new ReportVO();
				
				reportVO.setForumPostNo(forumPostNo);
				reportVO.setForumCommentNo(forumCommentNo);
				reportVO.setStudentNo(studentNo);
				reportVO.setType(type);
				reportVO.setDescription(description);
				reportVO.setStatus(status);
				ReportService reportSvc = new ReportService();
				reportVO = reportSvc.addReport(forumPostNo, forumCommentNo,studentNo, type, description, status);
				String url = "/back-end/report/listAllReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report/addReport.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String reportNo = req.getParameter("reportNo");
				ReportService reportService = new ReportService();
				reportService.deleteReportVO(reportNo);
				String url = "/back-end/report/listAllReport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report/listAllReport.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
