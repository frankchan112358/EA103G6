package com.courseask.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.courseask.model.CourseAskService;
import com.courseask.model.CourseAskVO;


public class CourseAskServlet extends HttpServlet {
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
				String str = req.getParameter("courseAskNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入提問編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/courseAsk/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				String courseAskNo = null;
				try {
					courseAskNo = str;
				} catch (Exception e) {
					errorMsgs.add("回覆編號格式不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/courseAsk/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				CourseAskService courseAskSvc = new CourseAskService();
				CourseAskVO courseAskVO = courseAskSvc.getOneCourseAsk(courseAskNo);
				if (courseAskVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/courseAsk/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("courseAskVO", courseAskVO);
				String url = "/back-end/courseAsk/listOneCourseAsk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/courseAsk/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String courseAskNo = req.getParameter("courseAskNo");

				CourseAskService courseAskSvc = new CourseAskService();
				CourseAskVO courseAskVO = courseAskSvc.getOneCourseAsk(courseAskNo);

				req.setAttribute("courseAskVO", courseAskVO);
				String url = "/back-end/courseAsk/update_courseAsk_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得修改資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/courseAsk/listAllCourseAsk.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String courseAskNo = req.getParameter("courseAskNo");

				String courseNo = req.getParameter("courseNo");

				String studentNo = req.getParameter("studentNo");

				String title = req.getParameter("title");
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("提問標題請勿空白");
				}
				String question = req.getParameter("question");
				if (question == null || question.trim().length() == 0) {
					errorMsgs.add("問題內容請勿空白");
				}

				java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());

				Integer status = null;
				try {
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("狀態請填數字");
				}

				CourseAskVO courseAskVO = new CourseAskVO();
				courseAskVO.setCourseAskNo(courseAskNo);
				courseAskVO.setCourseNo(courseNo);
				courseAskVO.setStudentNo(studentNo);
				courseAskVO.setTitle(title);
				courseAskVO.setQuestion(question);
				courseAskVO.setUpdateTime(updateTime);
				courseAskVO.setStatus(status);
				
				System.out.println(courseAskVO.getCourseAskNo());
				System.out.println(courseAskVO.getStatus());

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("courseAskVO", courseAskVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/courseAsk/update_courseAsk_input.jsp");
					failureView.forward(req, res);
					return;
				}

				CourseAskService courseAskSvc = new CourseAskService();
				courseAskVO = courseAskSvc.updateCourseAsk(courseAskNo, courseNo, studentNo, title, question,
						updateTime, status);

				req.setAttribute("courseAskVO", courseAskVO);
				String url = "/back-end/courseAsk/listOneCourseAsk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("更新資料失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/courseAsk/update_courseAsk_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String courseNo = req.getParameter("courseNo");

				String studentNo = req.getParameter("studentNo");

				String title = req.getParameter("title");

				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("提問標題請勿空白");
				}
				String question = req.getParameter("question");

				if (question == null || question.trim().length() == 0) {
					errorMsgs.add("問題內容請勿空白");
				}

				java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());

				Integer status = null;
				
				try {
					status = new Integer(req.getParameter("status").trim());

				} catch (NumberFormatException e) {
					errorMsgs.add("狀態請填數字");
				}

				CourseAskVO courseAskVO = new CourseAskVO();
				courseAskVO.setCourseNo(courseNo);
			
				courseAskVO.setStudentNo(studentNo);
			
				courseAskVO.setTitle(title);
				
				courseAskVO.setQuestion(question);
			
				courseAskVO.setUpdateTime(updateTime);
		
				courseAskVO.setStatus(status);
			
				
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("courseAskVO", courseAskVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/courseAsk/addCourseAsk.jsp");
					failureView.forward(req, res);
					return;
				}

				CourseAskService courseAskSvc = new CourseAskService();
				courseAskVO = courseAskSvc.addCourseAsk(courseNo, studentNo, title, question, updateTime, status);

				String url = "/back-end/courseAsk/listAllCourseAsk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("資料新增失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/courseAsk/addCourseAsk.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String courseAskNo = req.getParameter("courseAskNo");

				CourseAskService courseAskSvc = new CourseAskService();
				courseAskSvc.deleteCourseAsk(courseAskNo);

				String url = "/back-end/courseAsk/listAllCourseAsk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("資料刪除失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/courseAsk/listAllCourseAsk.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
