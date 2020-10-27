package com.banji.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.user.model.UserVO;

public class BanjiManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		if (userVO == null || userVO.getType() != 2) {
			res.sendRedirect(req.getContextPath() + "/login/login.jsp");
			return;
		}
		EmpVO empVO = (EmpVO) session.getAttribute("empVO");
		if(empVO==null)
			empVO=new EmpService().getOneEmpByUserNo(userVO.getUserNo());
		String action = req.getParameter("action");
		if (action == null) {
			res.setContentType("text/html;");
			req.setAttribute("list", empVO.getBanjiList());
			session.setAttribute("banjiNo", null);
			String url = "/back-end/banji/homeBanji.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if("manage".equals(action)) {
			res.setContentType("text/html;");
			String banjiNo = req.getParameter("banjiNo");
			session.setAttribute("banjiNo", banjiNo);
			BanjiVO banjiVO = new BanjiService().getOneBanji(banjiNo);
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/banji.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("read".equals(action)) {
			res.setContentType("text/html;");
			String banjiNo = (String) session.getAttribute("banjiNo");
			BanjiVO banjiVO = new BanjiService().getOneBanji(banjiNo);
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/banji.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
	}

}
