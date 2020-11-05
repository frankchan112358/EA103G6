package com.forum.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.forumcomment.model.ForumCommentService;
import com.forumpost.model.ForumPostService;
import com.forumtopic.model.ForumTopicService;
import com.student.model.StudentVO;

public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if (action == null) {
			res.setContentType("text/html;");
			HttpSession session = req.getSession();
			StudentVO studentVO = (StudentVO) session.getAttribute("studentVO");
			req.setAttribute("forumTopicList", new ForumTopicService().getAllWithBanji(studentVO.getBanjiNo()));
			String url = "/front-end/forum/forumHome.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumTopicHomePage".equals(action)) {
			res.setContentType("text/html;");
			String forumTopicNo = req.getParameter("forumTopicNo");
			req.setAttribute("forumTopicVO", new ForumTopicService().getOneForumTopic(forumTopicNo));
			req.setAttribute("forumPostList", new ForumPostService().getByTopicNo(forumTopicNo));
			String url = "/front-end/forum/forumTopic.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumPostHomePage".equals(action)) {
			res.setContentType("text/html;");
			String forumPostNo = req.getParameter("forumPostNo");
			new ForumPostService().addForumPostViews2(forumPostNo);
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			req.setAttribute("forumCommentList", new ForumCommentService().getOneFpFc(forumPostNo));
			String url = "/front-end/forum/forumPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
	}

}
