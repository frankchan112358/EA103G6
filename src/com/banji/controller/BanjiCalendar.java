package com.banji.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.timetable.model.TimetableService;
import com.user.model.UserVO;

public class BanjiCalendar extends HttpServlet {
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
		String banjiNo = req.getParameter("banjiNo");
		if (banjiNo == null) {
			res.sendRedirect(req.getContextPath() + "/banji/banji.manage");
			return;
		}
		EmpVO empVO = (EmpVO) session.getAttribute("empVO");
		if (empVO == null)
			empVO = new EmpService().getOneEmpByUserNo(userVO.getUserNo());
		String action = req.getParameter("action");
		if ("events".equals(action)) {
			res.setContentType("application/json;");
			PrintWriter out = res.getWriter();
			out.print(new TimetableService().getAllJsonWithBanjiNo(banjiNo));
			return;
		}
	}

}
