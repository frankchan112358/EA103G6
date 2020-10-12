package com.basiccourse.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.basiccourse.model.*;

public class BasicCourseServlet extends HttpServlet {

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
				String str = req.getParameter("basicCourseNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("⚠請輸入基本課程編號⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failuresView = req.getRequestDispatcher("/back-end/basiccourse/select_page.jsp");
					failuresView.forward(req, res);
					return;
				}
				String basicCourseNo = null;
				try {
					basicCourseNo = new String(str);
				} catch (Exception e) {
					errorMsgs.add("⚠基本課程編號格式不正確⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/basiccourse/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				BasicCourseService basicCourseSvc = new BasicCourseService();
				BasicCourseVO basicCourseVO = basicCourseSvc.getOneBasicCourse(basicCourseNo);
				if (basicCourseVO == null) {
					errorMsgs.add("⚠查無資料⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/basiccourse/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("basicCourseVO", basicCourseVO);
				String url = "/back-end/basiccourse/listOneBasicCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("⚠無法取得資料⚠:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/basiccourse/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String basicCourseNo = new String(req.getParameter("basicCourseNo").trim());

				BasicCourseService basicCourseSvc = new BasicCourseService();
				BasicCourseVO basicCourseVO = basicCourseSvc.getOneBasicCourse(basicCourseNo);

				req.setAttribute("basicCourseVO", basicCourseVO);
				String url = "/back-end/basiccourse/update_basicCourse_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠無法取得要修改的資料⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/basiccourse/listAllBasicCourse.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String basicCourseNo = new String(req.getParameter("basicCourseNo").trim());

				String basicCourseName = new String(req.getParameter("basicCourseName").trim());
				if (basicCourseName == null || basicCourseName.trim().length() == 0) {
					errorMsgs.put("basicCourseName", "⚠基本課程名稱請勿空白⚠");
				}

				String basicCourseInfo = new String(req.getParameter("basicCourseInfo").trim());

				Integer lesson = null;
				try {
					lesson = new Integer(req.getParameter("lesson").trim());
				} catch (NumberFormatException e) {
					lesson = 0;
					errorMsgs.put("lesson", "⚠堂數請填數字⚠");
				}

				BasicCourseVO basicCourseVO = new BasicCourseVO();
				basicCourseVO.setBasicCourseNo(basicCourseNo);
				basicCourseVO.setBasicCourseName(basicCourseName);
				basicCourseVO.setBasicCourseInfo(basicCourseInfo);
				basicCourseVO.setLesson(lesson);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("basicCourseVO", basicCourseVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/basiccourse/update_basicCourse_input.jsp");
					failureView.forward(req, res);
					return;
				}

				BasicCourseService basicCourseSvc = new BasicCourseService();
				basicCourseVO = basicCourseSvc.updateBasicCourse(basicCourseNo, basicCourseName, basicCourseInfo,
						lesson);

				req.setAttribute("basicCourseVO", basicCourseVO);
				String url = "/back-end/basiccourse/listOneBasicCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠修改資料失敗⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/basiccourse/update_basicCourse_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String basicCourseName = new String(req.getParameter("basicCourseName").trim());
				if (basicCourseName == null || basicCourseName.trim().length() == 0) {
					errorMsgs.put("basicCourseName", "⚠基本課程名稱請勿空白⚠");
				}

				String basicCourseInfo = new String(req.getParameter("basicCourseInfo").trim());

				Integer lesson = null;
				try {
					lesson = new Integer(req.getParameter("lesson").trim());
				} catch (NumberFormatException e) {
					lesson = 0;
					errorMsgs.put("lesson", "⚠堂數請填數字⚠");
				}

				BasicCourseVO basicCourseVO = new BasicCourseVO();
				basicCourseVO.setBasicCourseName(basicCourseName);
				basicCourseVO.setBasicCourseInfo(basicCourseInfo);
				basicCourseVO.setLesson(lesson);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("basicCourseVO", basicCourseVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/basiccourse/addBasicCourse.jsp");
					failureView.forward(req, res);
					return;
				}

				BasicCourseService basicCourseSvc = new BasicCourseService();
				basicCourseVO = basicCourseSvc.addBasicCourse(basicCourseName, basicCourseInfo, lesson);

				String url = "/back-end/basiccourse/listAllBasicCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠新增資料失敗⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/basiccourse/addBasicCourse.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String basicCourseNo = new String(req.getParameter("basicCourseNo"));

				BasicCourseService basicCourseSvc = new BasicCourseService();
				basicCourseSvc.deleteBasicCourse(basicCourseNo);

				String url = "/back-end/basiccourse/listAllBasicCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("⚠刪除資料失敗⚠:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/basiccourse/listAllBasicCourse.jsp");
				failureView.forward(req, res);
			}
		}
	}
}