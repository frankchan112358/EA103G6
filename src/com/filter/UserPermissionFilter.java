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
		/*********************擋沒登入者直接入侵*********************/
		if (userVO == null) {
			RequestDispatcher failureView = req.getRequestDispatcher("/error-page/page_error-forPermission.jsp");
			failureView.forward(req, res);
			return;
		}
		
		
		
		String gotoPlace = req.getParameter("goto");
		String getForFile=null;
		
		if(gotoPlace==null) {
			String getForFullFile=req.getRequestURI();
			getForFile=getForFullFile.substring(getForFullFile.lastIndexOf('/')+1);
			System.out.println(getForFile);
			
		}
		
		/*********************導師管理的權限篩選*********************/
		if ("empList".equals(gotoPlace)||"listOneEmp".equals(gotoPlace)||"empList.jsp".equals(getForFile)||"listOneEmp.jsp".equals(getForFile)) {
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
				if (checkPermission.getOneUserPermission(userVO.getUserNo(), "4").getPermissionEdit().equals(1)) {
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
		if ("teacherList".equals(gotoPlace)||"listOneTeacher".equals(gotoPlace)||"teacherList.jsp".equals(getForFile)||"listOneTeacher.jsp".equals(getForFile)) {
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
		if ("studentList".equals(gotoPlace)||"listOneStudent".equals(gotoPlace)||"studentList.jsp".equals(getForFile)||"listOneStudent.jsp".equals(getForFile)) {
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
		
		//只要filter註冊沒有跑到上面就全擋
		RequestDispatcher failureView = req.getRequestDispatcher("/error-page/page_error-forPermission.jsp");
		failureView.forward(req, res);
		return;
	}
}
