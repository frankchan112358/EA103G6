package com.leave.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.leave.model.LeaveService;
import com.leave.model.LeaveVO;

@WebServlet("/LeaveServlet")
public class LeaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LeaveServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String leaveNo = req.getParameter("leaveNo");
				if (leaveNo == null || (leaveNo.trim()).length() == 0) {
					errorMsgs.add("請輸入請假編號");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/leave/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				LeaveService leaveSvc = new LeaveService();
				LeaveVO leaveVO = leaveSvc.getOneLeave(leaveNo);
				if (leaveVO == null) {
					errorMsgs.add("查無資料");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/leave/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("leaveVO", leaveVO);
				String url = "/back-end/leave/listOneLeave.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/leave/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String leaveNo = req.getParameter("leaveNo");
				LeaveService leaveSvc = new LeaveService();
				LeaveVO leaveVO = leaveSvc.getOneLeave(leaveNo);
				req.setAttribute("leaveVO", leaveVO);
				String url = "/back-end/leave/update_leave_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/leave/listAllLeave.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
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
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/leave/update_leave_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
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
				leaveVO = leaveSvc.addLeave(studentNo, timetableNo, type, description, status);
				String url = "/back-end/leave/listAllLeave.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/leave/addLeave.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String leaveNo = req.getParameter("leaveNo");
				LeaveService leaveService = new LeaveService();
				leaveService.deleteLeaveVO(leaveNo);
				String url = "/back-end/leave/listAllLeave.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/leave/listAllLeave.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
