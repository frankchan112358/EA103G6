package com.coursepost.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.coursepost.model.*;

public class CoursePostServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("listCoursePost_ByCourseNo".equals(action)||"studentSelectCourseAndForwardToCoursePost".equals(action)) {
			HttpSession session = req.getSession();
			String courseNo = (String)session.getAttribute("courseNo");
			List<CoursePostVO> coursePostList = new CoursePostService().getCoursePostByCourseNo(courseNo);
			req.setAttribute("coursePostList", coursePostList);
			session.setAttribute("courseWork", "coursePost");
			String url = "/front-end/course/coursePost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
				
		HttpSession session = req.getSession();
		String courseNo = (String)session.getAttribute("courseNo");
		CourseVO courseVO = new CourseService().getOneCourse(courseNo);
		if (courseVO == null) {
			res.sendRedirect(req.getContextPath() + "/course/course.do");
			return;
		}
				
		if (action == null) {			
			res.setContentType("text/html;");			
			session.setAttribute("courseWork", "coursePost");
			String url = "/back-end/coursepost/listAllCoursePost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		
		if("new".equals(action)) {
			res.setContentType("text/html;");
			req.setAttribute("courseNo", courseNo);
			String url = "/back-end/coursepost/addCoursePost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}		
		
		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("coursePostNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("⚠請輸入課程編號⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failuresView = req.getRequestDispatcher("/back-end/coursepost/listAllCoursePost.jsp");
					failuresView.forward(req, res);
					return;
				}
				String coursePostNo = null;
				try {
					coursePostNo = new String(str);
				} catch (Exception e) {
					errorMsgs.add("⚠公告編號格式不正確⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/coursepost/listAllCoursePost.jsp");
					failureView.forward(req, res);
					return;
				}

				CoursePostService coursePostSvc = new CoursePostService();
				CoursePostVO coursePostVO = coursePostSvc.getOneCoursePost(coursePostNo);
				if (coursePostVO == null) {
					errorMsgs.add("⚠查無資料⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/coursepost/listAllCoursePost.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("coursePostVO", coursePostVO);
				String url = "/back-end/coursepost/listOneCoursePost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("⚠無法取得資料⚠:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/coursepost/listAllCoursePost.jsp");
				failureView.forward(req, res);
			}
			return;
		}

		if ("getOne_For_Update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String coursePostNo = new String(req.getParameter("coursePostNo").trim());

				CoursePostService coursePostSvc = new CoursePostService();
				CoursePostVO coursePostVO = coursePostSvc.getOneCoursePost(coursePostNo);

				req.setAttribute("coursePostVO", coursePostVO);
				String url = "/back-end/coursepost/update_coursePost_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠無法取得要修改的資料⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/coursepost/listAllCoursePost.jsp");
				failureView.forward(req, res);
			}
			return;
		}

		if ("update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String coursePostNo = new String(req.getParameter("coursePostNo").trim());

				String title = new String(req.getParameter("title").trim());
				if (title == null || title.trim().length() == 0) {
					errorMsgs.put("title", "⚠公告標題請勿空白⚠");
				}

				String postContent = new String(req.getParameter("postContent").trim());


				CoursePostVO coursePostVO = new CoursePostVO();
				coursePostVO.setCoursePostNo(coursePostNo);
				coursePostVO.setCourseNo(courseNo);
				coursePostVO.setTitle(title);
				coursePostVO.setPostContent(postContent);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coursePostVO", coursePostVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/coursepost/update_coursePost_input.jsp");
					failureView.forward(req, res);
					return;
				}

				CoursePostService coursePostSvc = new CoursePostService();
				coursePostVO = coursePostSvc.updateCoursePost(coursePostNo, courseNo, title, postContent);
				req.setAttribute("coursePostVO", coursePostVO);
				String url = "/back-end/coursepost/listOneCoursePost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠修改資料失敗⚠", e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/coursepost/update_coursePost_input.jsp");
				failureView.forward(req, res);
			}
			return;
		}

		if ("insert".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String title = new String(req.getParameter("title").trim());
				if (title == null || title.trim().length() == 0) {
					errorMsgs.put("title", "⚠公告標題請勿空白⚠");
				}

				String postContent = new String(req.getParameter("postContent").trim());


				CoursePostVO coursePostVO = new CoursePostVO();
				coursePostVO.setCourseNo(courseNo);
				coursePostVO.setTitle(title);
				coursePostVO.setPostContent(postContent);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coursePostVO", coursePostVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/coursepost/addCoursePost.jsp");
					failureView.forward(req, res);
					return;
				}

				CoursePostService coursePostSvc = new CoursePostService();
				coursePostVO = coursePostSvc.addCoursePost(courseNo, title, postContent);
				String url = "/back-end/coursepost/listAllCoursePost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.put("⚠新增資料失敗⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/coursepost/addCoursePost.jsp");
				failureView.forward(req, res);
			}
			return;
		}

		if ("delete".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String coursePostNo = new String(req.getParameter("coursePostNo"));
				CoursePostService coursePostSvc = new CoursePostService();
				coursePostSvc.deleteCoursePost(coursePostNo);

				String url = "/back-end/coursepost/listAllCoursePost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				errorMsgs.add("⚠刪除資料失敗⚠:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/coursepost/listAllCoursePost.jsp");
				failureView.forward(req, res);
			}
			return;
		}

	}
}
