package com.banji.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.leave.model.LeaveService;
import com.leave.model.LeaveStatus;
import com.leave.model.LeaveVO;
import com.user.model.UserVO;

public class BanjiLeave extends HttpServlet {
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
		String banjiNo = (String)session.getAttribute("banjiNo");
		if (banjiNo == null) {
			res.sendRedirect(req.getContextPath() + "/banji/banji.manage");
			return;
		}
		BanjiVO banjiVO = new BanjiService().getOneBanji(banjiNo);		
		String action = req.getParameter("action");
		if (action == null) {
			res.setContentType("text/html;");
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/leave/leave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("datatable".equals(action)) {
			res.setContentType("application/json;");
			PrintWriter out = res.getWriter();
			out.print(new LeaveService().getDatatableJson(banjiNo));
			return;
		}
		if ("read".equals(action)) {
			res.setContentType("application/json;");
			String leaveNo = req.getParameter("leaveNo");
			PrintWriter out = res.getWriter();
			out.print(new LeaveService().getReadLeaveJson(leaveNo));
			return;
		}
		if ("decide".equals(action)) {
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
