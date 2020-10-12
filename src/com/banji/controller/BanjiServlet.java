package com.banji.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.banji.model.*;

public class BanjiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

				String banjiNo = req.getParameter("banjiNo");
				if (banjiNo == null || (banjiNo.trim()).length() == 0) {
					errorMsgs.add("請輸入班級編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				BanjiService banjiSvc = new BanjiService();
				BanjiVO banjiVO = banjiSvc.getOneBanji(banjiNo);
				if (banjiVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("banjiVO", banjiVO);
				String url = "/back-end/banji/listOneBanji.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/select_page.jsp");

				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiNo = req.getParameter("banjiNo");
				if (banjiNo == null || (banjiNo.trim()).length() == 0) {
					errorMsgs.add("請輸入班級編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				BanjiService banjiSvc = new BanjiService();
				BanjiVO banjiVO = banjiSvc.getOneBanji(banjiNo);

				req.setAttribute("banjiVO", banjiVO);

				String url = "/back-end/banji/update_banji_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/listAllBanji.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiNo = req.getParameter("banjiNo");

				String empNo = req.getParameter("empNo");

				String banjiTypeNo = req.getParameter("banjiTypeNo");

				java.sql.Date startDay = null;

				try {
					startDay = java.sql.Date.valueOf(req.getParameter("startDay").trim());
				} catch (IllegalArgumentException e) {
					startDay = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				java.sql.Date endDay = null;

				try {
					endDay = java.sql.Date.valueOf(req.getParameter("endDay").trim());
				} catch (IllegalArgumentException e) {
					endDay = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				int compare = endDay.compareTo(startDay);
				if (compare < 0) {
					errorMsgs.add("結訓日不可比開訓日早");
				} else if (new Integer(compare).equals(0)) {
					errorMsgs.add("結訓日不可與開訓日同日");
				}

				String banjiName = req.getParameter("banjiName");
				String banjiNameReg = "^[(\u4e00-\u9fd5)(a-zA-Z0-9_)]{2,10}$";
				if (banjiName == null || banjiName.trim().length() == 0) {
					errorMsgs.add("班級名稱請勿空白");
				} else if (!banjiName.trim().matches(banjiNameReg)) {
					errorMsgs.add("班級名稱只能是中、英文字母、數字，其他不行。");
				}

				Integer classHours = null;
				try {
					classHours = new Integer(req.getParameter("classHours").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("上課時數請填數字");
				}

				Integer numberOfStudent = null;
				try {
					numberOfStudent = new Integer(req.getParameter("numberOfStudent").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請填寫上課人數");
				}

				String classroomNo = req.getParameter("classroomNo");
//				String classroomNoReg = "^[(\u4e00-\u9fd5)(a-zA-Z0-9_)]{2,10}$";
				if (classroomNo == null || classroomNo.trim().length() == 0) {
					errorMsgs.add("教室編號請勿空白");
				}

				Integer status = null;
				try {
					status = java.lang.Integer.valueOf(req.getParameter("status").trim());
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請輸入正確值");
				}

				String banjiContent = req.getParameter("banjiContent");
//				String banjiContentReg = "^[(\u4e00-\u9fd5)(a-zA-Z0-9_)]{2,10}$";
				if (banjiContent == null || banjiContent.trim().length() == 0) {
					errorMsgs.add("班級內容請勿空白");
				}

				BanjiVO banjiVO = new BanjiVO();
				banjiVO.setBanjiNo(banjiNo);
				banjiVO.setEmpNo(empNo);
				banjiVO.setBanjiTypeNo(banjiTypeNo);
				banjiVO.setStartDay(startDay);
				banjiVO.setEndDay(endDay);
				banjiVO.setBanjiName(banjiName);
				banjiVO.setClassHours(classHours);
				banjiVO.setNumberOfStudent(numberOfStudent);
				banjiVO.setClassroomNo(classroomNo);
				banjiVO.setStatus(status);
				banjiVO.setBanjiContent(banjiContent);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("banjiVO", banjiVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/update_banji_input.jsp");
					failureView.forward(req, res);
					return;
				}
				BanjiService banjiSvc = new BanjiService();
				banjiVO = banjiSvc.updateBanji(banjiNo, empNo, banjiTypeNo, startDay, endDay, banjiName, classHours,
						numberOfStudent, classroomNo, status, banjiContent);

				req.setAttribute("banjiVO", banjiVO);
				String url = "/back-end/banji/listOneBanji.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);

				successView.forward(req, res);

			} catch (Exception e) {

				errorMsgs.add("資料錯誤" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/update_banji_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiNo = req.getParameter("banjiNo");

				String empNo = req.getParameter("empNo");

				String banjiTypeNo = req.getParameter("banjiTypeNo");

				java.sql.Date startDay = null;
				try {
					startDay = java.sql.Date.valueOf(req.getParameter("startDay").trim());
				} catch (IllegalArgumentException e) {
					startDay = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				java.sql.Date endDay = null;
				try {
					endDay = java.sql.Date.valueOf(req.getParameter("endDay").trim());
				} catch (IllegalArgumentException e) {
					endDay = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				int compare = endDay.compareTo(startDay);
				if (compare < 0) {
					errorMsgs.add("結訓日不可比開訓日早");
				} else if (new Integer(compare).equals(0)) {
					errorMsgs.add("結訓日不可與開訓日同日");
				}

				String banjiName = req.getParameter("banjiName");
				String banjiNameReg = "^[(\u4e00-\u9fd5)(a-zA-Z0-9_)]{2,10}$";
				if (banjiName == null || banjiName.trim().length() == 0) {
					errorMsgs.add("班級名稱請勿空白");
				} else if (!banjiName.trim().matches(banjiNameReg)) {
					errorMsgs.add("班級名稱只能是中、英文字母、數字，其他不行。");
				}

				Integer classHours = null;
				try {
					classHours = new Integer(req.getParameter("classHours").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("上課時數請填數字");
				}

				Integer numberOfStudent = null;
				try {
					numberOfStudent = new Integer(req.getParameter("numberOfStudent").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請填寫上課人數");
				}

				String classroomNo = req.getParameter("classroomNo");
//				String classroomNoReg = "^[(\u4e00-\u9fd5)(a-zA-Z0-9_)]{2,10}$";
				if (classroomNo == null || classroomNo.trim().length() == 0) {
					errorMsgs.add("教室編號請勿空白");
				}
				Integer status = null;
				try {
					status = java.lang.Integer.valueOf(req.getParameter("status").trim());
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請輸入正確值");
				}

				String banjiContent = req.getParameter("banjiContent");
//				aString banjiContentReg = "^[(\u4e00-\u9fd5)(a-zA-Z0-9_)]{2,10}$";
				if (banjiContent == null || banjiContent.trim().length() == 0) {
					errorMsgs.add("班級內容請勿空白");
				}

				BanjiVO banjiVO = new BanjiVO();
				banjiVO.setBanjiNo(banjiNo);
				banjiVO.setEmpNo(empNo);
				banjiVO.setBanjiTypeNo(banjiTypeNo);
				banjiVO.setStartDay(startDay);
				banjiVO.setEndDay(endDay);
				banjiVO.setBanjiName(banjiName);
				banjiVO.setClassHours(classHours);
				banjiVO.setNumberOfStudent(numberOfStudent);
				banjiVO.setClassroomNo(classroomNo);
				banjiVO.setStatus(status);
				banjiVO.setBanjiContent(banjiContent);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("banjiVO", banjiVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/addbanji.jsp");
					failureView.forward(req, res);
					return;
				}
				BanjiService banjiSvc = new BanjiService();
				banjiVO = banjiSvc.addBanji(empNo, banjiTypeNo, startDay, endDay, banjiName, classHours,
						numberOfStudent, classroomNo, status, banjiContent);

				req.setAttribute("banjiVO", banjiVO);

				String url = "/back-end/banji/listOneBanji.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url);

				successView.forward(req, res);
			} catch (Exception e) {

				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/addbanji.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiNo = new String(req.getParameter("banjiNo"));
				BanjiService banjiSvc = new BanjiService();
				banjiSvc.deleteBanji(banjiNo);

				String url = "/back-end/banji/listAllBanji.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/listAllBanji");
				failureView.forward(req, res);
			}
		}
	}
}
