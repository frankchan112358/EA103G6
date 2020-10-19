package com.leave.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.leave.model.LeaveService;
import com.leave.model.LeaveVO;

@WebServlet("/LeaveServlet01")
public class LeaveServlet01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LeaveServlet01() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("getOne_For_Display".equals(action)) {
			try {
				String leaveNo = req.getParameter("leaveNo");
				LeaveService leaveSvc = new LeaveService();
				LeaveVO leaveVO = leaveSvc.getOneLeave(leaveNo);
				req.setAttribute("leaveVO", leaveVO);
				String url = "/back-end/leave/listOneLeave.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		if ("getOne_For_Update".equals(action)) {
			try {
				String leaveNo = req.getParameter("leaveNo");
				LeaveService leaveSvc = new LeaveService();
				LeaveVO leaveVO = leaveSvc.getOneLeave(leaveNo);
				req.setAttribute("leaveVO", leaveVO);
				String url = "/back-end/leave/update_leave_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		if ("update".equals(action)) {
			try {
				String leaveNo = req.getParameter("leaveNo");
				String studentNo = req.getParameter("studentNo");
				String timetableNo = req.getParameter("timetableNo");
				Integer type = new Integer(req.getParameter("type").trim());
				String description = req.getParameter("description");
				Integer status = new Integer(req.getParameter("status").trim());
				LeaveVO leaveVO = new LeaveVO();
				leaveVO.setLeaveNo(leaveNo);
				leaveVO.setStudentNo(studentNo);
				leaveVO.setTimetableNo(timetableNo);
				leaveVO.setType(type);
				leaveVO.setDescription(description);
				leaveVO.setStatus(status);
				LeaveService leaveSvc = new LeaveService();
				leaveVO = leaveSvc.updateLeaveVO(leaveNo, studentNo, timetableNo, type, description, status);
				req.setAttribute("leaveVO", leaveVO);
				String url = "/back-end/leave/listOneLeave.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		if ("insert".equals(action)) {
			try {
				String studentNo = req.getParameter("studentNo");
				String timetableNo = req.getParameter("timetableNo");
				Integer type = new Integer(req.getParameter("type").trim());
				String description = req.getParameter("description");
				Integer status = new Integer(req.getParameter("status").trim());
				LeaveVO leaveVO = new LeaveVO();
				leaveVO.setStudentNo(studentNo);
				leaveVO.setTimetableNo(timetableNo);
				leaveVO.setType(type);
				leaveVO.setDescription(description);
				leaveVO.setStatus(status);
				LeaveService leaveSvc = new LeaveService();
				leaveVO = leaveSvc.addLeave(studentNo, timetableNo, type, description);
				String url = "/back-end/leave/listAllLeave.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		if ("delete".equals(action)) {
			try {
				String leaveNo = req.getParameter("leaveNo");
				LeaveService leaveService = new LeaveService();
				leaveService.deleteLeaveVO(leaveNo);
				String url = "/back_end/leave/listAllLeave.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}
}
