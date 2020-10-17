package com.banjitypecourse.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.banjitypecourse.model.*;

public class BanjiTypeCourseServlet extends HttpServlet {

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
				String str = req.getParameter("banjiTypeCourseNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入班種課程編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failuresView = req
							.getRequestDispatcher("/back-end/banjiTypCourse/select_page.jsp");
					failuresView.forward(req, res);
					return;
				}
				String banjiTypeCourseNo = null;
				try {
					banjiTypeCourseNo = new String(str);
				} catch (Exception e) {
					errorMsgs.add("班種課程編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/banjiTypeCourse/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				BanjiTypeCourseService banjiTypeCourseSvc = new BanjiTypeCourseService();
				BanjiTypeCourseVO banjiTypeCourseVO = banjiTypeCourseSvc.getOneBanjiTypeCourse(banjiTypeCourseNo);
				if (banjiTypeCourseVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/banjiTypeCourse/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("banjiTypeCourseVO", banjiTypeCourseVO);
				String url = "/back-end/banjiTypeCourse/listOneBanjiTypeCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiTypeCourse/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiTypeCourseNo = req.getParameter("banjiTypeCourseNo");

				BanjiTypeCourseService banjiTypeCourseSvc = new BanjiTypeCourseService();
				BanjiTypeCourseVO banjiTypeCourseVO = banjiTypeCourseSvc.getOneBanjiTypeCourse(banjiTypeCourseNo);

				req.setAttribute("banjiTypeCourseVO", banjiTypeCourseVO);
				String url = "/back-end/banjiTypeCourse/update_banjiTypeCourse_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得修改資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/banjiType/listAllBanjiTypeCourse.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String banjiTypeCourseNo = req.getParameter("banjiTypeCourseNo");

				String banjiTypeNo = req.getParameter("banjiTypeNo");

				String basicCourseNo = req.getParameter("basicCourseNo");

				BanjiTypeCourseVO banjiTypeCourseVO = new BanjiTypeCourseVO();
				banjiTypeCourseVO.setBanjiTypeCourseNo(banjiTypeCourseNo);
				banjiTypeCourseVO.setBanjiTypeNo(banjiTypeNo);
				banjiTypeCourseVO.setBasicCourseNo(basicCourseNo);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/banjiTypeCourse/update_banjiTypeCourse_input.jsp");
					failureView.forward(req, res);
					return;
				}
				BanjiTypeCourseService banjiTypeCourseSvc = new BanjiTypeCourseService();
				banjiTypeCourseVO = banjiTypeCourseSvc.updateBanjiTypeCourse(banjiTypeCourseNo, banjiTypeNo,
						basicCourseNo);

				req.setAttribute("banjiTypeCourseNo", banjiTypeCourseNo);
				String url = "/back-end/banjiTypeCourse/listOneBanjiTypeCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/banjiTypeCourse/update_banjiTypeCourse_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiTypeNo = req.getParameter("banjiTypeNo");

				String basicCourseNo = req.getParameter("basicCourseNo");

				BanjiTypeCourseVO banjiTypeCourseVO = new BanjiTypeCourseVO();
				banjiTypeCourseVO.setBanjiTypeNo(banjiTypeNo);
				banjiTypeCourseVO.setBasicCourseNo(basicCourseNo);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/banjiTypeCourse/addBanjiTypeCourse.jsp");
					failureView.forward(req, res);
					return;
				}
				BanjiTypeCourseService banjiTypeCourseSvc = new BanjiTypeCourseService();
				banjiTypeCourseVO = banjiTypeCourseSvc.addBanjiTypeCourse(banjiTypeNo, basicCourseNo);

				String url = "/back-end/banjiTypeCourse/listAllBanjiTypeCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/banjiTypeCourse/addBanjiTypeCourse.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiTypeCourseNo = req.getParameter("banjiTypeCourseNo");

				BanjiTypeCourseService banjiTypeCourseSvc = new BanjiTypeCourseService();
				banjiTypeCourseSvc.deleteBanjiTypeCourse(banjiTypeCourseNo);

				String url = "/back-end/banjiTypeCourse/listAllBanjiTypeCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("資料刪除失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/banjiTypeCourse/listAllBanjiTypeCourse.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
