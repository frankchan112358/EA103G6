package com.teachingplan.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.teachingplan.model.*;

public class TeachingPlanServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("teachingPlanNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("⚠請輸入教學計劃編號⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failuresView = req.getRequestDispatcher("/back-end/teachingplan/listAllTeachingPlan.jsp");
					failuresView.forward(req, res);
					return;
				}
				String teachingPlanNo = null;
				try {
					teachingPlanNo = new String(str);
				} catch (Exception e) {
					errorMsgs.add("⚠教學計劃編號格式不正確⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingplan/listAllTeachingPlan.jsp");
					failureView.forward(req, res);
					return;
				}

				TeachingPlanService teachingPlanSvc = new TeachingPlanService();
				TeachingPlanVO teachingPlanVO = teachingPlanSvc.getOneTeachingPlan(teachingPlanNo);
				if (teachingPlanVO == null) {
					errorMsgs.add("⚠查無資料⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingplan/listAllTeachingPlan.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("teachingPlanVO", teachingPlanVO);
				String url = "/back-end/teachingplan/listOneTeachingPlan.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("⚠無法取得資料⚠:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingplan/listAllTeachingPlan.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String teachingPlanNo = new String(req.getParameter("teachingPlanNO").trim());

				TeachingPlanService teachingPlanSvc = new TeachingPlanService();
				TeachingPlanVO teachingPlanVO = teachingPlanSvc.getOneTeachingPlan(teachingPlanNo);

				req.setAttribute("teachingPlanVO", teachingPlanVO);
				String url = "/back-end/teachingplan/update_teachingPlan_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠無法取得要修改的資料⚠", e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/teachingplan/listAllTeachingPlan.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String teachingPlanNo = new String(req.getParameter("teachingPlanNo").trim());

				String courseNo = new String(req.getParameter("courseNo").trim());
				if (courseNo == null || courseNo.trim().length() == 0) {
					errorMsgs.put("courseNo", "⚠課程編號請勿空白⚠");
				}

				Integer week = null;
				try {
					week = new Integer(req.getParameter("week").trim());
				} catch (NumberFormatException e) {
					week = 0;
					errorMsgs.put("week", "⚠周次請填數字⚠");
				}

				Integer lesson = null;
				try {
					lesson = new Integer(req.getParameter("lesson").trim());
				} catch (NumberFormatException e) {
					lesson = 0;
					errorMsgs.put("lesson", "⚠堂數請填數字⚠");
				}

				String planContent = new String(req.getParameter("planContent").trim());

				TeachingPlanVO teachingPlanVO = new TeachingPlanVO();
				teachingPlanVO.setTeachingPlanNo(teachingPlanNo);
				teachingPlanVO.setCourseNo(courseNo);
				teachingPlanVO.setWeek(week);
				teachingPlanVO.setLesson(lesson);
				teachingPlanVO.setPlanContent(planContent);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("teachingPlanVO", teachingPlanVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/teachingplan/update_teachingPlan_input.jsp");
					failureView.forward(req, res);
					return;
				}

				TeachingPlanService teachingPlanSvc = new TeachingPlanService();
				teachingPlanVO = teachingPlanSvc.updateTeachingPlan(teachingPlanNo, courseNo, week, lesson,
						planContent);

				req.setAttribute("teachingPlanVO", teachingPlanVO);
				String url = "/back-end/teachingplan/listOneTeachingPlan.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠修改資料失敗⚠", e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/teachingplan/update_teachingPlan_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String courseNo = new String(req.getParameter("courseNo").trim());
				if (courseNo == null || courseNo.trim().length() == 0) {
					errorMsgs.put("courseNo", "⚠課程編號請勿空白⚠");
				}

				Integer week = null;
				try {
					week = new Integer(req.getParameter("week").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("week", "⚠周次請填數字⚠");
				}

				Integer lesson = null;
				try {
					lesson = new Integer(req.getParameter("lesson").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("lesson", "⚠堂數請填數字⚠");
				}

				String planContent = new String(req.getParameter("planContent").trim());

				TeachingPlanVO teachingPlanVO = new TeachingPlanVO();
				teachingPlanVO.setCourseNo(courseNo);
				teachingPlanVO.setWeek(week);
				teachingPlanVO.setLesson(lesson);
				teachingPlanVO.setPlanContent(planContent);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("teachingPlanVO", teachingPlanVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/teachingplan/addTeachingPlan.jsp");
					failureView.forward(req, res);
					return;
				}

				TeachingPlanService teachingPlanSvc = new TeachingPlanService();
				teachingPlanVO = teachingPlanSvc.addTeachingPlan(courseNo, week, lesson, planContent);

				String url = "/back-end/teachingplan/listAllTeachingPlan.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠新增資料失敗⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingplan/addTeachingPlan.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String teachingPlanNo = new String(req.getParameter("teachingPlanNo"));

				TeachingPlanService teachingPlanSvc = new TeachingPlanService();
				teachingPlanSvc.deleteTeachingPlan(teachingPlanNo);

				String url = "/back-end/teachingplan/listAllTeachingPlan.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("⚠刪除資料失敗⚠:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/teachingplan/listAllTeachingPlan.jsp");
				failureView.forward(req, res);
			}
		}

		if ("listTeachingPlan_ByCourseNo".equals(action)) {

			String courseNo = (String)session.getAttribute("courseNo");
//			System.out.println("courseNo = " + courseNo);

			TeachingPlanService teachingPlanSvc = new TeachingPlanService();
			List<TeachingPlanVO> teachingPlanVO = teachingPlanSvc.getTeachingPlanByCourseNo(courseNo);

			session.setAttribute("teachingPlanVO", teachingPlanVO);

			String url = "/front-end/teachingplan/teachingPlan.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
	}
}