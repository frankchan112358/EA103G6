package com.banjitype.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.banji.model.*;
import com.banjitype.model.*;

public class BanjiTypeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if ("listBanji_ByBanjiTypeNo_A".equals(action) || "listBanji_ByBanjiTypeNo_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String banjiTypeNo = req.getParameter("banjiTypeNo");

				BanjiTypeService banjiTypeSvc = new BanjiTypeService();

				Set<BanjiVO> set = banjiTypeSvc.getBanjiTypeByBanjiTypeNo(banjiTypeNo);

				req.setAttribute("listBanji_ByBanjiTypeNo", set);

				String url = null;

				if ("listBanji_ByBanjiTypeNo_A".equals(action))
					url = "/back-end/banjiType/listBanji_ByBanjiTypeNo.jsp";
				else if ("listBanji_ByBanjiTypeNo_B".equals(action))
					url = "/back-end/banjiType/listAllBanjiType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {

				throw new ServletException();
			}
		}

		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			
			try {
				String str = req.getParameter("banjiTypeNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入班種編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiType/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				String banjiTypeNo = null;
				try {
					banjiTypeNo = str;
				} catch (Exception e) {
					errorMsgs.add("班種編號格式不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				BanjiTypeService banjiTypeSvc = new BanjiTypeService();
				BanjiTypeVO banjiTypeVO = banjiTypeSvc.getOneBanjiType(banjiTypeNo);
				if (banjiTypeVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiType/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("banjiTypeVO", banjiTypeVO);

				String url = "/back-end/banjiType/listOneBanjiType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiType/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiTypeNo = req.getParameter("banjiTypeNo");
				BanjiTypeService banjiTypeSvc = new BanjiTypeService();
				BanjiTypeVO banjiTypeVO = banjiTypeSvc.getOneBanjiType(banjiTypeNo);
				req.setAttribute("banjiTypeVO", banjiTypeVO);
				String url = "/back-end/banjiType/update_BanjiType_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得修改資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiType/listAllBanjiType.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiTypeNo = req.getParameter("banjiTypeNo");
				String banjiTypeName = req.getParameter("banjiTypeName");
				if (banjiTypeName == null || banjiTypeName.trim().length() == 0) {
					errorMsgs.add("班種名稱:請勿空白");
				}
				Integer classHours = null;
				try {
					classHours = new Integer(req.getParameter("classHours").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請填寫上課時數");
				}

				String banjiTypeContent = req.getParameter("banjiTypeContent");
				if (banjiTypeContent == null || banjiTypeContent.trim().length() == 0) {
					errorMsgs.add("班級內容請勿空白");
				}

				Integer banjiTypeEnable = null;
				try {
					banjiTypeEnable = new Integer(req.getParameter("banjiTypeEnable").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("班種未啟用");
				}

				BanjiTypeVO banjiTypeVO = new BanjiTypeVO();
				banjiTypeVO.setBanjiTypeNo(banjiTypeNo);
				banjiTypeVO.setBanjiTypeName(banjiTypeName);
				banjiTypeVO.setClassHours(classHours);
				banjiTypeVO.setBanjiTypeContent(banjiTypeContent);
				banjiTypeVO.setBanjiTypeEnable(banjiTypeEnable);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("banjiTypeVO", banjiTypeVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/banjiType/update_BanjiType_input.jsp");
					failureView.forward(req, res);
					return;
				}

				BanjiTypeService banjiTypeSvc = new BanjiTypeService();
				banjiTypeVO = banjiTypeSvc.updateBanjiType(banjiTypeNo, banjiTypeName, classHours, banjiTypeContent,
						banjiTypeEnable);

				req.setAttribute("banjiTypeVO", banjiTypeVO);
				String url = "/back-end/banjiType/homeBanjiType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/banjiType/update_BanjiType_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String banjiTypeName = req.getParameter("banjiTypeName");
				String banjiTypeNameReg = "^[(\u4e00-\u9fd5)(a-zA-Z0-9_)]{2,10}$";
				if (banjiTypeName == null || banjiTypeName.trim().length() == 0) {
					errorMsgs.put("banjiTypeName","班種名稱:請勿空白");
				} else if (!banjiTypeName.trim().matches(banjiTypeNameReg)) {
					errorMsgs.put("banjiTypeName","班種只能是中英數字");
				}

				Integer classHours = null;
				try {
					classHours = new Integer(req.getParameter("classHours").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("classHours","請填寫上課時數");
				}

				String banjiTypeContent = req.getParameter("banjiTypeContent");
				if (banjiTypeContent == null || banjiTypeContent.trim().length() == 0) {
					errorMsgs.put("banjiTypeContent","班級內容請勿空白");
				}

				Integer banjiTypeEnable = null;
				try {
					banjiTypeEnable = new Integer(req.getParameter("banjiTypeEnable").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("banjiTypeEnable","班種未啟用");
				}

				BanjiTypeVO banjiTypeVO = new BanjiTypeVO();
				banjiTypeVO.setBanjiTypeName(banjiTypeName);
				banjiTypeVO.setClassHours(classHours);
				banjiTypeVO.setBanjiTypeContent(banjiTypeContent);
				banjiTypeVO.setBanjiTypeEnable(banjiTypeEnable);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("banjiTypeVO", banjiTypeVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiType/addBanjiType.jsp");
					failureView.forward(req, res);
					return;
				}
				BanjiTypeService banjiTypeSvc = new BanjiTypeService();
				banjiTypeVO = banjiTypeSvc.addBanjiType(banjiTypeName, classHours, banjiTypeContent, banjiTypeEnable);

				String url = "/back-end/banjiType/homeBanjiType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.put("新增失敗",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiType/addBanjiType.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiTypeNo = req.getParameter("banjiTypeNo");

				BanjiTypeService banjiTypeSvc = new BanjiTypeService();
				banjiTypeSvc.deleteBanjiType(banjiTypeNo);

				String url = "/back-end/banjiType/homeBanjiType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("資料刪除失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/banjiType/listAllBanjiType.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
