package com.login.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user.model.UserService;
import com.user.model.UserVO;

public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String id = req.getParameter("id").trim();
				String password = req.getParameter("password").trim();
				String checkPassword = req.getParameter("checkPassword").trim();
				String passwordReg = "\\w{6,12}";

				UserService userSvc = new UserService();
				UserVO userVO = userSvc.UserForget(id);
				req.setAttribute("userVO", userVO);

				if (password == null && checkPassword == null) {
					RequestDispatcher failureView = req.getRequestDispatcher("/login/changePassword.jsp");
					failureView.forward(req, res);
					return;
				}

				if (!password.trim().matches(passwordReg)) {
					errorMsgs.add("密碼僅能輸入英文字母及數字，且長度為6-12");
					RequestDispatcher failureView = req.getRequestDispatcher("/login/changePassword.jsp");
					failureView.forward(req, res);
					return;
				}

				if (userVO == null) {
					errorMsgs.add("請確認您所輸入的身分證字號");
					RequestDispatcher failureView = req.getRequestDispatcher("/login/changePassword.jsp");
					failureView.forward(req, res);
					return;
				}

				if (!password.equals(checkPassword)) {
					errorMsgs.add("請確認新密碼與密碼驗證欄位輸入為一致");
					RequestDispatcher failureView = req.getRequestDispatcher("/login/changePassword.jsp");
					failureView.forward(req, res);
					return;
				}
				userVO.setPassword(password);
				userVO.setId(id);
				userVO = userSvc.update_Password(id, password);

				RequestDispatcher successView = req.getRequestDispatcher("/login/changePasswordSuccess.jsp");
				successView.forward(req, res);

			} catch (Exception e) {

				RequestDispatcher failureView = req.getRequestDispatcher("/login/changePassword.jsp");
				failureView.forward(req, res);

			}
		}
	
}
