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

		UserPermissionService checkPermission = new UserPermissionService();
		/*********************檔沒登入者直接入侵*********************/
		if (userVO == null) {
			RequestDispatcher failureView = req.getRequestDispatcher("/error-page/page_error-forPermission.jsp");
			failureView.forward(req, res);
			return;
		}
		
		/*********************start檔沒資格修改的人將前端按鈕解除*********************/
		String blockGoto=(String) req.getAttribute("goto");
		if("update".equals(blockGoto)) {
			if(checkPermission.getOneUserPermission(userVO.getUserNo(), "4").getPermissionEdit().equals(1)) {
				chain.doFilter(request, response);
				return;
			}else {  //若沒資格直接丟去首頁，因為非法入侵
				req.setAttribute("permission", "forbid");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		
		
		
		/*********************end檔沒資格修改的人將前端按鈕解除*********************/
		
		String gotoPlace = req.getParameter("goto");

		
		/*********************導師管理的權限篩選*********************/
		if ("empList".equals(gotoPlace)||"listOneEmp".equals(gotoPlace)) {
			if(userVO.getType().equals(0)) {
				req.setAttribute("permission", "forbid");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/index/index.jsp");
				failureView.forward(req, res);
				return;
			}else if (userVO.getType().equals(1)) {
				
				req.setAttribute("permission", "forbid");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
				return;

			} else if (userVO.getType().equals(2)) {

				// 測導師的權限若可讀就導向
				if (checkPermission.getOneUserPermission(userVO.getUserNo(), "4").getReadable().equals(1)) {
					chain.doFilter(request, response);
					return;
				} else {
					req.setAttribute("permission", "forbid");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
					failureView.forward(req, res);
					return;
				}

			}
		}
		
		
		/*********************講師管理的權限篩選*********************/
		if ("teacherList".equals(gotoPlace)||"listOneTeacher".equals(gotoPlace)) {
			if(userVO.getType().equals(0)) {
				req.setAttribute("permission", "forbid");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/index/index.jsp");
				failureView.forward(req, res);
				return;
				
			}else if (userVO.getType().equals(1)) {
				req.setAttribute("permission", "forbid");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
				return;
			} else {
				chain.doFilter(request, response);
				return;
			}
		}
		
		/*********************學生管理的權限篩選*********************/
		if ("studentList".equals(gotoPlace)||"listOneStudent".equals(gotoPlace)) {
			if(userVO.getType().equals(0)) {
				req.setAttribute("permission", "forbid");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/index/index.jsp");
				failureView.forward(req, res);
				return;
				
			}else if (userVO.getType().equals(1)) {
				req.setAttribute("permission", "forbid");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
				return;
			} else {
				chain.doFilter(request, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}
}
