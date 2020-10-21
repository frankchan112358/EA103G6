package com.banji.controller;

import java.io.IOException;
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

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		if (userVO == null || userVO.getType() != 2) {
			res.sendRedirect(req.getContextPath() + "/login/login.jsp");
			return;
		}
		String banjiNo = req.getParameter("banjiNo");
		if (banjiNo ==null) {
			res.sendRedirect(req.getContextPath() + "/banji/banji.manage");
			return;
		}
		BanjiVO banjiVO = new BanjiService().getOneBanji(banjiNo);
		EmpVO empVO = (EmpVO) session.getAttribute("empVO");
		if(empVO==null)
			empVO=new EmpService().getOneEmpByUserNo(userVO.getUserNo());
		String action = req.getParameter("action");
		if (action == null) {
			List<LeaveVO> list = new LeaveService().getAllWithBanji(banjiNo);
			req.setAttribute("list", list);
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/leave/leave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("read".equals(action)) {
			String leaveNo = req.getParameter("leaveNo");
			LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
			req.setAttribute("leaveVO", leaveVO);
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/leave/readLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("review".equals(action)) {
			String leaveNo = req.getParameter("leaveNo");
			LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
			req.setAttribute("leaveVO", leaveVO);
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/leave/readLeave.jsp";
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
			List<LeaveVO> list = new LeaveService().getAllWithBanji(banjiNo);
			req.setAttribute("list", list);
			req.setAttribute("banjiVO", banjiVO);
			String url = "/back-end/banji/leave/leave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
	}

}
