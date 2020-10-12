package com.finalscore.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.finalscore.model.*;

public class FinalScoreServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("finalScoreNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("⚠請輸入期末成績編號⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failuresView = req.getRequestDispatcher("/select_page.jsp");
					failuresView.forward(req, res);
					return;
				}
				String finalScoreNo = null;
				try {
					finalScoreNo = new String(str);
				} catch (Exception e) {
					errorMsgs.add("⚠期末成績編號格式不正確⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				FinalScoreService finalScoreSvc = new FinalScoreService();
				FinalScoreVO finalScoreVO = finalScoreSvc.getOneFinalScore(finalScoreNo);
				if (finalScoreVO == null) {
					errorMsgs.add("⚠查無資料⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("finalScoreVO", finalScoreVO);
				String url = "/back-end/finalscore/listOneFinalScore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("⚠無法取得資料⚠:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String finalScoreNo = new String(req.getParameter("finalScoreNo").trim());

				FinalScoreService finalScoreSvc = new FinalScoreService();
				FinalScoreVO finalScoreVO = finalScoreSvc.getOneFinalScore(finalScoreNo);

				req.setAttribute("finalScoreVO", finalScoreVO);
				String url = "/back-end/finalscore/update_finalScore_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠無法取得要修改的資料⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/finalscore/listAllFinalScore.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String finalScoreNo = new String(req.getParameter("finalScoreNo").trim());

				String courseNo = new String(req.getParameter("courseNo").trim());
				if (courseNo == null || courseNo.trim().length() == 0) {
					errorMsgs.put("courseNo", "⚠課程編號請勿空白⚠");
				}

				String studentNo = new String(req.getParameter("studentNo").trim());
				if (studentNo == null || studentNo.trim().length() == 0) {
					errorMsgs.put("studentNo", "⚠學員編號請勿空白⚠");
				}

				Integer score = null;
				try {
					score = new Integer(req.getParameter("score").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("score", "⚠成績請填數字⚠");
				}

				FinalScoreVO finalScoreVO = new FinalScoreVO();
				finalScoreVO.setFinalScoreNo(finalScoreNo);
				finalScoreVO.setCourseNo(courseNo);
				finalScoreVO.setStudentNo(studentNo);
				finalScoreVO.setScore(score);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("finalScoreVO", finalScoreVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/finalscore/update_finalScore_input.jsp");
					failureView.forward(req, res);
					return;
				}

				FinalScoreService finalScoreSvc = new FinalScoreService();
				finalScoreVO = finalScoreSvc.updateFinalScore(finalScoreNo, courseNo, studentNo, score);

				req.setAttribute("finalScoreVO", finalScoreVO);
				String url = "/back-end/finalscore/listOneFinalScore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠修改資料失敗⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/finalscore/update_finalScore_input.jsp");
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

				String studentNo = new String(req.getParameter("studentNo").trim());
				if (studentNo == null || studentNo.trim().length() == 0) {
					errorMsgs.put("studentNo", "⚠學員編號請勿空白⚠");
				}

				Integer score = null;
				try {
					score = new Integer(req.getParameter("score").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("score", "⚠成績請填數字⚠");
				}

				FinalScoreVO finalScoreVO = new FinalScoreVO();
				finalScoreVO.setCourseNo(courseNo);
				finalScoreVO.setStudentNo(studentNo);
				finalScoreVO.setScore(score);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("finalScoreVO", finalScoreVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/finalscore/addFinalScore.jsp");
					failureView.forward(req, res);
					return;
				}

				FinalScoreService finalScoreSvc = new FinalScoreService();
				finalScoreVO = finalScoreSvc.addFinalScore(courseNo, studentNo, score);

				String url = "/back-end/finalscore/listAllFinalScore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠新增資料失敗⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/finalscore/addFinalScore.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String finalScoreNo = new String(req.getParameter("finalScoreNo"));

				FinalScoreService finalScoreSvc = new FinalScoreService();
				finalScoreSvc.deleteFinalScore(finalScoreNo);

				String url = "/back-end/finalscore/listAllFinalScore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("⚠刪除資料失敗⚠:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/finalscore/listAllFinalScore.jsp");
				failureView.forward(req, res);
			}
		}
	}
}