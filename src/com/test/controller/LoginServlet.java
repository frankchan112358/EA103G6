package com.test.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.student.model.StudentService;
import com.student.model.StudentVO;
import com.teacher.model.TeacherService;
import com.teacher.model.TeacherVO;
import com.user.model.UserService;
import com.user.model.UserVO;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String type = req.getParameter("type");
		if ("emp".equals(type)) {
			String empNo = req.getParameter("empNo");
			EmpVO empVO = (new EmpService()).getOneEmp(empNo);
			if(empVO!=null) {
				UserVO userVO = (new UserService()).getOneUser(empVO.getUserNo());				
				HttpSession session = req.getSession();
				session.setAttribute("userVO", userVO);
				res.sendRedirect(req.getContextPath() + "/back-end/index/index.jsp");
				return;
			}
		}
		if ("teacher".equals(type)) {
			String teacherNo = req.getParameter("teacherNo");
			TeacherVO teacherVO = (new TeacherService()).getOneTeacher(teacherNo);
			if(teacherVO!=null) {
				UserVO userVO = (new UserService()).getOneUser(teacherVO.getUserNo());				
				HttpSession session = req.getSession();
				session.setAttribute("userVO", userVO);
				res.sendRedirect(req.getContextPath() + "/back-end/index/index.jsp");
				return;
			}
		}
		if ("student".equals(type)) {
			String studentNo = req.getParameter("studentNo");
			StudentVO studentVO = (new StudentService()).getOneStudent(studentNo);
			if(studentVO!=null) {
				UserVO userVO = (new UserService()).getOneUser(studentVO.getUserno());				
				HttpSession session = req.getSession();
				session.setAttribute("userVO", userVO);
				session.setAttribute("studentVO", studentVO);
				res.sendRedirect(req.getContextPath() + "/front-end/index/index.jsp");
				return;
			}
		}
		res.sendRedirect(req.getContextPath() + "/back-end/test/login.jsp");
	}

}
