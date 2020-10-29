package com.reply.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.reply.model.*;

public class ReplyServlet extends HttpServlet {
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
				String str = req.getParameter("replyNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入回覆編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				String replyNo = null;
				try {
					replyNo = str;
				} catch (Exception e) {
					errorMsgs.add("回覆編號格式不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				ReplyService replySvc = new ReplyService();
				ReplyVO replyVO = replySvc.getOneReply(replyNo);
				if (replyVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("replyVO", replyVO);
				String url = "/back-end/reply/listOneReply.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String replyNo = req.getParameter("replyNo");

				ReplyService replySvc = new ReplyService();
				ReplyVO replyVO = replySvc.getOneReply(replyNo);

				req.setAttribute("replyVO", replyVO);
				String url = "/back-end/reply/update_reply_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得修改資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/listAllReply.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String replyNo = req.getParameter("replyNo");
				String courseAskNo = req.getParameter("courseAskNo");
				String teacherNo = req.getParameter("teacherNo");
				String studentNo = req.getParameter("studentNo");
				String replyContent = req.getParameter("replyContent");
				if (replyContent == null || replyContent.trim().length() == 0) {
					errorMsgs.add("回覆內容請勿空白");
				}

				java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());

				ReplyVO replyVO = new ReplyVO();
				replyVO.setReplyNo(replyNo);
				replyVO.setCourseAskNo(courseAskNo);
				replyVO.setTeacherNo(teacherNo);
				replyVO.setStudentNo(studentNo);
				replyVO.setReplyContent(replyContent);
				replyVO.setUpdateTime(updateTime);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("replyVO", replyVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/update_reply_input.jsp");
					failureView.forward(req, res);
					return;
				}

				ReplyService replySvc = new ReplyService();
				replyVO = replySvc.updateReply(replyNo, courseAskNo, teacherNo, studentNo, replyContent, updateTime);

				req.setAttribute("replyVO", replyVO);
				String url = "/back-end/reply/listOneReply.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("更新資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/update_reply_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert2".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String courseAskNo = req.getParameter("courseAskNo");
				System.out.println(courseAskNo);
				String teacherNo = req.getParameter("teacherNo");
				System.out.println(teacherNo);
				String studentNo = req.getParameter("studentNo");
				System.out.println(studentNo);
				String replyContent = req.getParameter("replyContent");
				System.out.println(replyContent);
				java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());

				ReplyVO replyVO = new ReplyVO();
				replyVO.setCourseAskNo(courseAskNo);
				replyVO.setTeacherNo(teacherNo);
				replyVO.setStudentNo(studentNo);
				replyVO.setReplyContent(replyContent);
				replyVO.setUpdateTime(updateTime);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/addReply.jsp");
					failureView.forward(req, res);
					return;
				}
				ReplyService replySvc = new ReplyService();
				replyVO = replySvc.addReply(courseAskNo, teacherNo, studentNo, replyContent, updateTime);

				String url = "/back-end/courseAsk/courseAsk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/addReply.jsp");
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
			HttpSession session = req.getSession();
			if (session.getAttribute("reply") != null) {
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

					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/addReply.jsp");
						failureView.forward(req, res);
						return;
					}
					ReplyService replySvc = new ReplyService();
					replyVO = replySvc.addReply(courseAskNo, teacherNo, studentNo, replyContent, updateTime);

					String url = "/front-end/courseAsk/courseAsk.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
				} catch (Exception e) {
					errorMsgs.add("資料失敗" + e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/addReply.jsp");
					failureView.forward(req, res);
				}
				session.removeAttribute("reply");
			} else {
				String url = "/front-end/courseAsk/courseAsk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String replyNo = req.getParameter("replyNo");

				ReplyService replySvc = new ReplyService();
				replySvc.deleteReply(replyNo);

				String url = "/back-end/reply/listAllReply.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("資料刪除失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/reply/listAllReply.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
