package com.login.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
				
		try {
			Integer type = typeNameConvertToTypeNum(req.getParameter("type"));
			String account = req.getParameter("account").trim().toLowerCase();
			String password = req.getParameter("password").trim();
				
			UserService userSvc = new UserService();
			UserVO userVO = userSvc.UserLogin(account, password, type);
			
			
			if (userVO == null) {
				errorMsgs.add("帳號或密碼或型別輸入錯誤");
				RequestDispatcher failureView = req.getRequestDispatcher("/login/login.jsp");
				failureView.forward(req, res);
				return;
			}
		
			
			if(userVO.getEnable()==2) {
				errorMsgs.add("您沒有權限登入，請聯繫系統管理員");
				RequestDispatcher failureView = req.getRequestDispatcher("/login/login.jsp");
				failureView.forward(req, res);
				return;
			}
			if(userVO.getEnable()==0) {
				errorMsgs.add("您尚未開通帳號，請至信箱收信後開通帳號");
				RequestDispatcher failureView = req.getRequestDispatcher("/login/login.jsp");
				failureView.forward(req, res);
				return;
			}
			
			if (type == 2) {
				/*************************** 查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("userVO", userVO);
				EmpVO empVO = (new EmpService()).getOneEmpByUserNo(userVO.getUserNo());
				HttpSession session = req.getSession();
				session.setAttribute("userVO", userVO);
				session.setAttribute("empVO", empVO);
				
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/index/index.jsp");
				successView.forward(req, res);
			}
			
			if (type == 1) {
				/*************************** 查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("userVO", userVO);
				TeacherVO teacherVO = (new TeacherService()).getOneTeacherByUserNo(userVO.getUserNo());
				HttpSession session = req.getSession();
				session.setAttribute("userVO", userVO);
				session.setAttribute("teacherVO", teacherVO);

				RequestDispatcher successView = req.getRequestDispatcher("/back-end/index/index.jsp");
				successView.forward(req, res);
			}
			
			if (type == 0) {
				/*************************** 查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("userVO", userVO);
				StudentVO studentVO = (new StudentService()).getOneStudentByUserNo(userVO.getUserNo());
				HttpSession session = req.getSession();
				session.setAttribute("userVO", userVO);
				session.setAttribute("studentVO", studentVO);
				
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/index/index.jsp");
				successView.forward(req, res);
			}
		} catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/login/login.jsp");
			failureView.forward(req, res);

		}
	}
	
	private Integer typeNameConvertToTypeNum (String type) {
		Integer typeNum = -1;
		if(type == null)
			return typeNum;
		switch (type) {
		case "student":
			typeNum = 0;		
			break;
		case "teacher":
			typeNum = 1;		
			break;
		case "emp":
			typeNum = 2;		
			break;			
		default:
			break;
		}
		return typeNum;
	}
}


