package com.leave.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.emp.model.EmpVO;
import com.leave.model.LeaveService;
import com.leave.model.LeaveStatus;
import com.user.model.UserVO;
import com.leave.model.LeaveVO;

public class LeaveManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		if (userVO == null || userVO.getType() != 2) {
			res.sendRedirect(req.getContextPath() + "/login/login.jsp");
			return;
		}
		EmpVO empVO = (EmpVO) session.getAttribute("empVO");
		String action = req.getParameter("action");
		if (action == null) {
//			String banjiNo = req.getParameter("banjiNo");
//			Integer status = Integer.parseInt(req.getParameter("status"));
			List<LeaveVO> list = new LeaveService().getAllWithEmp(empVO.getEmpNo());
			req.setAttribute("list", list);
			String url = "/back-end/leave/leave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		if ("read".equals(action)) {
			String leaveNo = req.getParameter("leaveNo");
			LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
			req.setAttribute("leaveVO", leaveVO);
			String url = "/back-end/leave/readLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		if ("review".equals(action)) {
			String leaveNo = req.getParameter("leaveNo");
			LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
			req.setAttribute("leaveVO", leaveVO);
			String url = "/back-end/leave/readLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		if ("decide".equals(action)) {
			String leaveNo = req.getParameter("leaveNo");
			LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
			String todo = req.getParameter("todo");
			if ("reject".equals(todo))
				leaveVO.setStatus(LeaveStatus.Reject.getNum());
			else if ("pass".equals(todo))
				leaveVO.setStatus(LeaveStatus.Pass.getNum());
			new LeaveService().updateLeaveVO(leaveVO.getLeaveNo(), leaveVO.getStudentNo(), leaveVO.getTimetableNo(),
					leaveVO.getType(), leaveVO.getDescription(), leaveVO.getStatus());
			List<LeaveVO> list = new LeaveService().getAllWithEmp(empVO.getEmpNo());
			req.setAttribute("list", list);
			String url = "/back-end/leave/leave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

	}
}
