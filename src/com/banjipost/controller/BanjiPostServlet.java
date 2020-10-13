package com.banjipost.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.banjipost.model.*;

public class BanjiPostServlet extends HttpServlet {
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
				String str = req.getParameter("banjiPostNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入班級公告編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiPost/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				String banjiPostNo = null;
				try {
					banjiPostNo = str;
				} catch (Exception e) {
					errorMsgs.add("班級公告編號格式不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiPost/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				BanjiPostService banjiPostSvc = new BanjiPostService();
				BanjiPostVO banjiPostVO = banjiPostSvc.getOneBanjiPost(banjiPostNo);

				if (banjiPostVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiPost/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("banjiPostVO", banjiPostVO);
				String url = "/back-end/banjiPost/listOneBanjiPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiPost/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiPostNo = req.getParameter("banjiPostNo");

				BanjiPostService banjiPostSvc = new BanjiPostService();
				BanjiPostVO banjiPostVO = banjiPostSvc.getOneBanjiPost(banjiPostNo);

				req.setAttribute("banjiPostVO", banjiPostVO);
				String url = "/back-end/banjiPost/update_banjiPost_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiPost/listAllBanjiPost.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			System.out.println("97");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("100");

			try {
				String banjiPostNo = req.getParameter("banjiPostNo");
				System.out.println("104");

				String banjiNo = req.getParameter("banjiNo");
				System.out.println("107");

				String title = req.getParameter("title");
				System.out.println("109" + title);
				
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("公告標題請勿空白");
				}
				System.out.println("115");
				String banjiPostContent = req.getParameter("banjiPostContent");
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("公告內容請勿空白");
				}

				System.out.println("121");
				java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());
//				try {
//					updateTime = java.sql.Timestamp.valueOf(req.getParameter("updateTime").trim());
//				} catch (IllegalArgumentException e) {
//					updateTime = new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("時間錯誤");
//				}

				Integer status = null;
				try {
					status = new Integer(req.getParameter("status").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("狀態不對");
				}

				BanjiPostVO banjiPostVO = new BanjiPostVO();
				banjiPostVO.setBanjiPostNo(banjiPostNo);
				System.out.println("139");
				banjiPostVO.setBanjiNo(banjiNo);
				banjiPostVO.setTitle(title);
				banjiPostVO.setBanjiPostContent(banjiPostContent);
				banjiPostVO.setUpdateTime(updateTime);
				banjiPostVO.setStatus(status);
				System.out.println("145");

				if (!errorMsgs.isEmpty()) {
					
					List<String> errorMsgss = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgss);
					
					
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/banjiPost/update_banjiPost_input.jsp");
//					failureView.forward(req, res);
//					return;
				}

				System.out.println("154");
				BanjiPostService banjiPostSvc = new BanjiPostService();
				banjiPostVO = banjiPostSvc.updateBanjiPost(banjiPostNo, banjiNo, title, banjiPostContent, updateTime,
						status);

				System.out.println("159");
				req.setAttribute("banjiPostVO", banjiPostVO);
				String url = "/back-end/banjiPost/listOneBanjiPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("更新資料失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/banjiPost/update_banjiPost_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiNo = req.getParameter("banjiNo");
				String title = req.getParameter("title");
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("公告標題請勿空白");
				}

				String banjiPostContent = req.getParameter("banjiPostContent");
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("公告內容請勿空白");
				}

				java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());
//				try {
//					updateTime = java.sql.Timestamp.valueOf(req.getParameter("updateTime").trim());
//				} catch (IllegalArgumentException e) {
//					updateTime = new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("時間錯誤");
//				}

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
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiPost/addBanjiPost.jsp");
					failureView.forward(req, res);
					return;
				}

				BanjiPostService banjiPostSvc = new BanjiPostService();
				banjiPostVO = banjiPostSvc.addBanjiPost(banjiNo, title, banjiPostContent, updateTime, status);

				String url = "/back-end/banjiPost/listAllBanjiPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiPost/addBanjiPost.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiPostNo = req.getParameter("banjiPostNo");

				BanjiPostService banjiPostSvc = new BanjiPostService();
				banjiPostSvc.deleteBanjiPost(banjiPostNo);

				String url = "/back-end/banjiPost/listAllBanjiPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("資料刪除失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiPost/listAllBanjiPost.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
