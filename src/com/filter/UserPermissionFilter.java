package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.model.UserVO;
import com.userpermission.model.UserPermissionService;

public class UserPermissionFilter implements Filter {
	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");

		if (userVO == null) {

			RequestDispatcher failureView = req.getRequestDispatcher("/error-page/page_error-forPermission.jsp");
			failureView.forward(req, res);
			return;
		}

		String gotoPlace = req.getParameter("goto");

		if ("empList".equals(gotoPlace)) {
			if (userVO.getType().equals(1)) {

				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);

			} else if (userVO.getType().equals(2)) {

				// 測導師的權限若可讀就導向
				UserPermissionService checkPermission = new UserPermissionService();
				if (checkPermission.getOneUserPermission(userVO.getUserNo(), "4").getReadable().equals(1)) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/empList.jsp");
					failureView.forward(req, res);
					return;
				} else {

					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
					failureView.forward(req, res);
					return;
				}

			}
		}

		if ("teacherList".equals(gotoPlace)) {
			if (userVO.getType().equals(1)) {

				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
				return;
			} else {

				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teacher/teacherList.jsp");
				failureView.forward(req, res);
			}
		}
		if ("studentList".equals(gotoPlace)) {
			if (userVO.getType().equals(1)) {

				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
				return;
			} else {

				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/studentList.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
