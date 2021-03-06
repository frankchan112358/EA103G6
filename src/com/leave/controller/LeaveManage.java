package com.leave.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
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
		res.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		if (userVO == null || userVO.getType() != 2) {
			res.sendRedirect(req.getContextPath() + "/login/login.jsp");
			return;
		}
		EmpVO empVO = (EmpVO) session.getAttribute("empVO");
		if(empVO==null)
			empVO=new EmpService().getOneEmpByUserNo(userVO.getUserNo());
		String action = req.getParameter("action");
		if (action == null) {
//			String banjiNo = req.getParameter("banjiNo");
//			Integer status = Integer.parseInt(req.getParameter("status"));
			List<LeaveVO> list = new LeaveService().getAllWithEmp(empVO.getEmpNo());
			req.setAttribute("list", list);
			String url = "/back-end/leave/leave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("read".equals(action)) {
			String leaveNo = req.getParameter("leaveNo");
			LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
			req.setAttribute("leaveVO", leaveVO);
			String url = "/back-end/leave/readLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("review".equals(action)) {
			String leaveNo = req.getParameter("leaveNo");
			LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
			req.setAttribute("leaveVO", leaveVO);
			String url = "/back-end/leave/readLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
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
			return;
		}
		if ("datatable_review".equals(action)) {
			res.setContentType("application/json;");
			PrintWriter out = res.getWriter();
			out.print(new LeaveService().getReviewDatatableJsonWithEmpVO(empVO.getEmpNo()));
			return;
		}
		if ("decide_index".equals(action)) {
			res.setContentType("application/json;");
			String leaveNo = req.getParameter("leaveNo");
			PrintWriter out = res.getWriter();
			out.print(new LeaveService().getReadLeaveJson(leaveNo));
			return;
		}
		if ("pass".equals(action)) {
			res.setContentType("text/html;");
			String leaveNo = req.getParameter("leaveNo");
			LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
			leaveVO.setStatus(LeaveStatus.Pass.getNum());
			new LeaveService().updateLeaveVO(leaveVO.getLeaveNo(), leaveVO.getStudentNo(), leaveVO.getTimetableNo(),
					leaveVO.getType(), leaveVO.getDescription(), leaveVO.getStatus());
			PrintWriter out = res.getWriter();
			out.print("ok");
			return;
		}
		if ("reject".equals(action)) {
			res.setContentType("text/html;");
			String leaveNo = req.getParameter("leaveNo");
			LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
			leaveVO.setStatus(LeaveStatus.Reject.getNum());
			new LeaveService().updateLeaveVO(leaveVO.getLeaveNo(), leaveVO.getStudentNo(), leaveVO.getTimetableNo(),
					leaveVO.getType(), leaveVO.getDescription(), leaveVO.getStatus());
			PrintWriter out = res.getWriter();
			out.print("ok");
		}
	}
}
