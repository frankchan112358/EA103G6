package com.banji.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.forumtopic.model.ForumTopicService;
import com.forumtopic.model.ForumTopicVO;
import com.leave.model.LeaveService;
import com.leave.model.LeaveVO;
import com.user.model.UserVO;

public class BanjiForumTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		if (userVO == null || userVO.getType() != 2) {
			res.sendRedirect(req.getContextPath() + "/login/login.jsp");
			return;
		}
		EmpVO empVO = (EmpVO) session.getAttribute("empVO");
		if (empVO == null)
			empVO = new EmpService().getOneEmpByUserNo(userVO.getUserNo());
		String banjiNo = (String) session.getAttribute("banjiNo");
		if (banjiNo == null) {
			res.sendRedirect(req.getContextPath() + "/banji/banji.manage");
			return;
		}
		BanjiVO banjiVO = new BanjiService().getOneBanji(banjiNo);
		
		String action = req.getParameter("action");
		if (action == null) {
			List<ForumTopicVO> list = new ForumTopicService().getByBanJiNo(banjiNo);
			req.setAttribute("list", list);
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/forumtopic/forumTopic.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}

		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String forumTopicNo = req.getParameter("forumTopicNo");
				if (forumTopicNo == null || (forumTopicNo.trim()).length() == 0) {
					errorMsgs.add("請輸入主題編號");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/forumtopic/forumTopic.jsp");
					failureView.forward(req, res);
					return;
				}
				ForumTopicService forumtopicSvc = new ForumTopicService();
				ForumTopicVO forumTopicVO = forumtopicSvc.getOneForumTopic(forumTopicNo);
				if (forumTopicVO == null) {
					errorMsgs.add("查無資料");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/forumtopic/forumTopic.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("forumTopicVO", forumTopicVO);
				String url = "/back-end/banji/forumtopic/forumTopic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/forumtopic/forumTopic.jsp");
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
				String url = "/back-end/banji/forumtopic/updateForumTopic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/forumtopic/forumTopic.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String forumTopicNo = req.getParameter("forumTopicNo");
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
				List<ForumTopicVO> list = new ForumTopicService().getByBanJiNo(banjiNo);
				req.setAttribute("list", list);
				String url = "/back-end/banji/forumtopic/forumTopic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/forumtopic/forumTopic.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
//			try {
				String forumTopicName = req.getParameter("forumTopicName");
				String banjiNo2 = req.getParameter("banjiNo");
				
				ForumTopicVO forumTopicVO = new ForumTopicVO();
				
				forumTopicVO.setBanjiNo(banjiNo);
				forumTopicVO.setForumTopicName(forumTopicName);
				System.out.println(banjiNo2);
				System.out.println(forumTopicName);
				ForumTopicService forumtopicSvc = new ForumTopicService();
				forumTopicVO = forumtopicSvc.addForumTopic(banjiNo2, forumTopicName);
				
				BanjiVO banjiVO2 = new BanjiService().getOneBanji(banjiNo);
				req.setAttribute("banjiVO", banjiVO2);
				
				String url = "/back-end/banji/forumtopic/forumTopic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
//			} catch (Exception e) {
//				errorMsgs.add("新增資料失敗" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/forumtopic/forumTopic.jsp");
//				failureView.forward(req, res);
//			}
		}
		if ("delete".equals(action)) {
			System.out.println("test");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String forumTopicNo = req.getParameter("forumTopicNo");
				ForumTopicService forumTopicService = new ForumTopicService();
				forumTopicService.deleteForumTopic(forumTopicNo);
				
				
				String url = "/back-end/banji/forumtopic/forumTopic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/forumtopic/forumTopic.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
