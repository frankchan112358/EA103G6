package com.test.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.user.model.UserService;
import com.user.model.UserVO;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String type = req.getParameter("type");
		if ("emp".equals(type)) {
			String empNo = req.getParameter("empNo");
			EmpVO empVO = (new EmpService()).getOneEmp(empNo);
			UserVO userVO = (new UserService()).getOneUser(empVO.getUserNo());
			HttpSession session = req.getSession();
			session.setAttribute("userVO", userVO);
			res.sendRedirect(req.getContextPath()+"/back-end/index/index.jsp");
		}
		if ("teacher".equals(type)) {
			String teacherNo = req.getParameter("teacherNo");
		}
		if ("student".equals(type)) {
			String studentNo = req.getParameter("studentNo");
		}
		
	}

}
