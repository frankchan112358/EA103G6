package com.login.controller;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mail.service.MailService;
import com.user.model.UserService;
import com.user.model.UserVO;

public class ForgetServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
				
		try {

			String mail = req.getParameter("mail").trim();
			String id = req.getParameter("id").trim();
				
			UserService userSvc = new UserService();
			UserVO userVO = userSvc.UserForget(id);
			System.out.print(id);
			
			String mailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
			if (mail == null || mail.trim().length() == 0) {
				errorMsgs.add("信箱請勿空白");
			} else if (!mail.trim().matches(mailReg)) {
				errorMsgs.add("信箱格式錯誤，請再次檢查");
			}
			
			
			if (userVO == null ) {
				errorMsgs.add("您所輸入的身分證字號或信箱有誤");
				RequestDispatcher failureView = req.getRequestDispatcher("/login/forgetPassword.jsp");
				failureView.forward(req, res);
				return;
			}
			
			if(!userVO.getMail().equals(mail)){
				errorMsgs.add("您所輸入的身分證字號或信箱有誤");
				RequestDispatcher failureView = req.getRequestDispatcher("/login/forgetPassword.jsp");
				failureView.forward(req, res);
				return;
				
			}
			if(userVO.getEnable()==2) {
				errorMsgs.add("您沒有權限，請聯繫系統管理員");
				RequestDispatcher failureView = req.getRequestDispatcher("/login/forgetPassword.jsp");
				failureView.forward(req, res);
				return;
			}
			if(userVO.getEnable()==0) {
				errorMsgs.add("您尚未開通帳號，請至信箱收取驗證信後開通帳號");
				RequestDispatcher failureView = req.getRequestDispatcher("/login/forgetPassword.jsp");
				failureView.forward(req, res);
				return;
			}
			
			
			
			String encoding = userVO.getId();
			String messageText = "http://localhost:8081" + req.getContextPath() + "/ChangePassword/ChangePassword.do?id="
					+ encoding;

			MailService sendMail = new MailService();
			sendMail.sendMail(mail,"Work Join Learn 會員忘記密碼", messageText);
			
			RequestDispatcher successView = req.getRequestDispatcher("/login/mailSuccess.jsp");
			successView.forward(req, res);
			
		} catch (Exception e) {
			e.printStackTrace();
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/login/forgetPassword.jsp");
			failureView.forward(req, res);

		}
	}

}
