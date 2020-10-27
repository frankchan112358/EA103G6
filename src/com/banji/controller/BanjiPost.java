package com.banji.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.banjipost.model.BanjiPostService;
import com.banjipost.model.BanjiPostVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.user.model.UserVO;

public class BanjiPost extends HttpServlet {
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
			res.setContentType("text/html;");
			req.setAttribute("list", new BanjiPostService().getAllWhitBanjiNo(banjiNo));
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/banjiPost/listAllBanjiPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("getOne_For_Display".equals(action)) {
			res.setContentType("text/html;");
			String banjiPostNo = req.getParameter("banjiPostNo");
			BanjiPostVO banjiPostVO = new BanjiPostService().getOneBanjiPost(banjiPostNo);
			if (banjiPostVO == null) {
				req.setAttribute("list", new BanjiPostService().getAllWhitBanjiNo(banjiNo));
				req.setAttribute("banjiVO", banjiVO);
				String url = "/back-end/banji/banjiPost/listAllBanjiPost.jsp";
				req.getRequestDispatcher(url).forward(req, res);
				return;
			}
			req.setAttribute("banjiPostVO", banjiPostVO);
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/banjiPost/listOneBanjiPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("getOne_For_Update".equals(action)) {
			res.setContentType("text/html;");
			String banjiPostNo = req.getParameter("banjiPostNo");
			BanjiPostVO banjiPostVO = new BanjiPostService().getOneBanjiPost(banjiPostNo);
			if (banjiPostVO == null) {
				req.setAttribute("list", new BanjiPostService().getAllWhitBanjiNo(banjiNo));
				req.setAttribute("banjiVO", banjiVO);
				String url = "/back-end/banji/banjiPost/listAllBanjiPost.jsp";
				req.getRequestDispatcher(url).forward(req, res);
				return;
			}
			req.setAttribute("banjiPostVO", banjiPostVO);
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/banjiPost/update_banjiPost_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("new".equals(action)) {
			res.setContentType("text/html;");
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/banjiPost/addBanjiPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String banjiPostNo = req.getParameter("banjiPostNo");
				
				String title = req.getParameter("title");
				if (title == null || title.trim().length() == 0) 
					errorMsgs.add("公告標題請勿空白");
				
				String banjiPostContent = req.getParameter("banjiPostContent");
				if (banjiPostContent == null || banjiPostContent.trim().length() == 0) 
					errorMsgs.add("公告內容請勿空白");
				
				java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());

				Integer status = null;
				try {
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("狀態不對");
				}

				BanjiPostVO banjiPostVO = new BanjiPostVO();
				banjiPostVO.setBanjiPostNo(banjiPostNo);
				banjiPostVO.setBanjiNo(banjiNo);
				banjiPostVO.setTitle(title);
				banjiPostVO.setBanjiPostContent(banjiPostContent);
				banjiPostVO.setUpdateTime(updateTime);
				banjiPostVO.setStatus(status);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("banjiPostVO", banjiPostVO);
					req.setAttribute("banjiVO", banjiVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/banji/banjiPost/update_banjiPost_input.jsp");
					failureView.forward(req, res);
					return;
				}

				BanjiPostService banjiPostSvc = new BanjiPostService();
				banjiPostVO = banjiPostSvc.updateBanjiPost(banjiPostNo, banjiNo, title, banjiPostContent, updateTime,
						status);

				req.setAttribute("banjiPostVO", banjiPostVO);
				req.setAttribute("banjiVO", banjiVO);
				String url = "/back-end/banji/banjiPost/listOneBanjiPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("更新資料失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/banji/banjiPost/update_banjiPost_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String title = req.getParameter("title");
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("公告標題請勿空白");
				}

				String banjiPostContent = req.getParameter("banjiPostContent");
				if (banjiPostContent == null || banjiPostContent.trim().length() == 0) {
					errorMsgs.add("公告內容請勿空白");
				}

				java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());

				Integer status = null;
				try {
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("狀態不對");
				}

				BanjiPostVO banjiPostVO = new BanjiPostVO();
				banjiPostVO.setBanjiNo(banjiNo);
				banjiPostVO.setTitle(title);
				banjiPostVO.setBanjiPostContent(banjiPostContent);
				banjiPostVO.setUpdateTime(updateTime);
				banjiPostVO.setStatus(status);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("banjiPostVO", banjiPostVO);
					req.setAttribute("banjiVO", banjiVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/banjiPost/addBanjiPost.jsp");
					failureView.forward(req, res);
					return;
				}

				BanjiPostService banjiPostSvc = new BanjiPostService();
				banjiPostVO = banjiPostSvc.addBanjiPost(banjiNo, title, banjiPostContent, updateTime, status);
				
				req.setAttribute("list", new BanjiPostService().getAllWhitBanjiNo(banjiNo));
				req.setAttribute("banjiVO", banjiVO);
				String url = "/back-end/banji/banjiPost/listAllBanjiPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/banjiPost/addBanjiPost.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String banjiPostNo = req.getParameter("banjiPostNo");

				new BanjiPostService().deleteBanjiPost(banjiPostNo);

				req.setAttribute("list", new BanjiPostService().getAllWhitBanjiNo(banjiNo));
				req.setAttribute("banjiVO", banjiVO);
				String url = "/back-end/banji/banjiPost/listAllBanjiPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("資料刪除失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banji/banjiPost/listAllBanjiPost.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
