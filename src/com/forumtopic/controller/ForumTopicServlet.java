package com.forumtopic.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.forumtopic.model.ForumTopicService;
import com.forumtopic.model.ForumTopicVO;

@WebServlet("/ForumTopicServlet")
public class ForumTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ForumTopicServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String forumTopicNo = req.getParameter("forumTopicNo");
				if (forumTopicNo == null || (forumTopicNo.trim()).length() == 0) {
					errorMsgs.add("請輸入主題編號");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumtopic/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				ForumTopicService forumtopicSvc = new ForumTopicService();
				ForumTopicVO forumTopicVO = forumtopicSvc.getOneForumTopic(forumTopicNo);
				if (forumTopicVO == null) {
					errorMsgs.add("查無資料");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumtopic/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("forumTopicVO", forumTopicVO);
				String url = "/back-end/forumtopic/listOneForumTopic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumtopic/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String forumTopicNo = req.getParameter("forumTopicNo");
				ForumTopicService forumtopicSvc = new ForumTopicService();
				ForumTopicVO forumTopicVO = forumtopicSvc.getOneForumTopic(forumTopicNo);
				req.setAttribute("forumTopicVO", forumTopicVO);
				String url = "/back-end/forumtopic/update_forumTopic_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumtopic/listAllForumTopic.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String forumTopicNo = req.getParameter("forumTopicNo");
				String banjiNo = req.getParameter("banjiNo");
				String forumTopicName = req.getParameter("forumTopicName");
				String content = req.getParameter("content");
				String rule = req.getParameter("rule");
				String postTemplete = req.getParameter("postTemplete");
				
				ForumTopicVO forumTopicVO = new ForumTopicVO();
				forumTopicVO.setForumTopicNo(forumTopicNo);
				forumTopicVO.setBanjiNo(banjiNo);
				forumTopicVO.setForumTopicName(forumTopicName);
				forumTopicVO.setContent(content);
				forumTopicVO.setRule(rule);
				forumTopicVO.setPostTemplete(postTemplete);
				
				
				ForumTopicService forumtopicSvc = new ForumTopicService();
				forumTopicVO = forumtopicSvc.updateForumTopic(forumTopicNo, banjiNo, forumTopicName, content, rule, postTemplete);
				req.setAttribute("forumTopicVO", forumTopicVO);
				String url = "/back-end/forumtopic/listOneForumTopic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumtopic/update_forumTopic_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String banjiNo = req.getParameter("banjiNo");
				String forumTopicName = req.getParameter("forumTopicName");
				String content = req.getParameter("content");
				String rule = req.getParameter("rule");
				String postTemplete = req.getParameter("postTemplete");
				
				ForumTopicVO forumTopicVO = new ForumTopicVO();
				
				forumTopicVO.setBanjiNo(banjiNo);
				forumTopicVO.setForumTopicName(forumTopicName);
				forumTopicVO.setContent(content);
				forumTopicVO.setRule(rule);
				forumTopicVO.setPostTemplete(postTemplete);
				ForumTopicService forumtopicSvc = new ForumTopicService();
				forumTopicVO = forumtopicSvc.addForumTopic(banjiNo, forumTopicName, content, rule, postTemplete);
				String url = "/back-end/forumtopic/listAllForumTopic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumtopic/addForumTopic.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String forumTopicNo = req.getParameter("forumTopicNo");
				ForumTopicService forumTopicService = new ForumTopicService();
				forumTopicService.deleteForumTopic(forumTopicNo);
				String url = "/back-end/forumtopic/listAllForumTopic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumtopic/listAllForumTopic.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
