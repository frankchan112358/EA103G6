package login;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
			String type = req.getParameter("type");
			String account = req.getParameter("account").trim();
			String password = req.getParameter("password").trim();

			
			UserService userSvc = new UserService();
			UserVO userVO1 = userSvc.Login_stu(account, password);
			UserVO userVO2 = userSvc.Login_emp(account, password);
			UserVO userVO3 = userSvc.Login_tea(account, password);

			if ("emp".equals(type)) {
				
				
				if (userVO2 == null) {
					errorMsgs.add("帳號或密碼輸入錯誤");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/page_login.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("userVO", userVO2);
				HttpSession session = req.getSession();
				session.setAttribute("account", account);

				RequestDispatcher successView = req.getRequestDispatcher("/back-end/index/index.jsp");
				successView.forward(req, res);

			}

			if ("teacher".equals(type)) {

				if (userVO3 == null) {
					errorMsgs.add("帳號或密碼輸入錯誤");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/page_login.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("userVO", userVO3);
				HttpSession session = req.getSession();
				session.setAttribute("account", account);


				RequestDispatcher successView = req.getRequestDispatcher("/back-end/index/index.jsp");
				successView.forward(req, res);

			}

			if ("student".equals(type)) {

				if (userVO1 == null) {
					errorMsgs.add("帳號或密碼輸入錯誤");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/page_login.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("userVO", userVO1);
				HttpSession session = req.getSession();
				session.setAttribute("account", account);

				RequestDispatcher successView = req.getRequestDispatcher("/front-end/index/index.jsp");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/

			}
		} catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/page_login.jsp");
			failureView.forward(req, res);

		}

	}
}

//package login;
//
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.user.model.UserService;
//import com.user.model.UserVO;
//
//public class LoginHandler extends HttpServlet {
//
//	private static final long serialVersionUID = 1L;
//
//	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//
//		List<String> errorMsgs = new LinkedList<String>();
//		req.setAttribute("errorMsgs", errorMsgs);
//
//		try {
//			String account = req.getParameter("account").trim();
//			String password = req.getParameter("password").trim();
//
//			UserService userSvc = new UserService();
//			UserVO userVO = userSvc.UserLogin(account, password);
//
//			if (userVO == null) {
//				errorMsgs.add("帳號或密碼輸入錯誤");
//			}
//
//			if (!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/page_login.jsp");
//				failureView.forward(req, res);
//				return;
//			}
//
//			/*************************** 查詢完成,準備轉交(Send the Success view) *************/
//			req.setAttribute("userVO", userVO);
//			HttpSession session = req.getSession();
//			session.setAttribute("account", account);
//
//			RequestDispatcher successView = req.getRequestDispatcher("/front-end/index/index.jsp");
//			successView.forward(req, res);
//
//			/*************************** 其他可能的錯誤處理 *************************************/
//
//		} catch (Exception e) {
//			errorMsgs.add("無法取得資料:" + e.getMessage());
//			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/page_login.jsp");
//			failureView.forward(req, res);
//
//		}
//
//	}
//
//}
