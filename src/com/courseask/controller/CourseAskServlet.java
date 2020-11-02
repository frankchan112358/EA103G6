package com.courseask.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.courseask.model.CourseAskService;
import com.courseask.model.CourseAskVO;
import com.coursepost.model.CoursePostService;
import com.coursepost.model.CoursePostVO;
import com.reply.model.ReplyService;
import com.reply.model.ReplyVO;


public class CourseAskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("listCourseAskWithCourseNo".equals(action)) {
			try {
				HttpSession session = req.getSession();
				String courseNo = (String)session.getAttribute("courseNo");
				List<CourseAskVO> courseAskList = new CourseAskService().getAllWithCourseNo(courseNo);
				req.setAttribute("courseAskList", courseAskList);
				session.setAttribute("courseWork", "courseAsk");
				String url = "/front-end/courseAsk/listCourseAskWithCourseNo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String courseAskNo = req.getParameter("courseAskNo");
				System.out.println(courseAskNo);
				if (courseAskNo == null || (courseAskNo.trim()).length() == 0) {
					errorMsgs.add("請輸入提問編號");
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
				String url = "/front-end/courseAsk/listOneCourseAsk.jsp";
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

		
		if ("insert1".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String courseAskNo = req.getParameter("courseAskNo");
				String teacherNo = req.getParameter("teacherNo");

				String studentNo = req.getParameter("studentNo");

				String replyContent = req.getParameter("replyContent");
	
				java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());

				ReplyVO replyVO = new ReplyVO();
				replyVO.setCourseAskNo(courseAskNo);
				replyVO.setTeacherNo(teacherNo);
				replyVO.setStudentNo(studentNo);
				replyVO.setReplyContent(replyContent);
				replyVO.setUpdateTime(updateTime);

				
				req.setAttribute("replyVO", replyVO);
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/addReply.jsp");
					failureView.forward(req, res);
					return;
				}

				String url = "/front-end/courseAsk/addreply.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/addReply.jsp");
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



				CourseAskVO courseAskVO = new CourseAskVO();
				courseAskVO.setCourseNo(courseNo);
			
				courseAskVO.setStudentNo(studentNo);
			
				courseAskVO.setTitle(title);
				
				courseAskVO.setQuestion(question);
			
				courseAskVO.setUpdateTime(updateTime);
		
				
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("courseAskVO", courseAskVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/courseAsk/addCourseAsk.jsp");
					failureView.forward(req, res);
					return;
				}

				CourseAskService courseAskSvc = new CourseAskService();
				courseAskVO = courseAskSvc.addCourseAsk(courseNo, studentNo, title, question, updateTime);

				String url = "/front-end/courseAsk/NewFile.jsp";
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
