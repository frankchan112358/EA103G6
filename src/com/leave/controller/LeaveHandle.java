package com.leave.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leave.model.LeaveService;
import com.leave.model.LeaveVO;
import com.student.model.StudentService;
import com.student.model.StudentVO;
import com.timetable.model.TimetableVO;
import com.user.model.UserVO;

public class LeaveHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		if (userVO == null || userVO.getType() != 0) {
			res.sendRedirect(req.getContextPath() + "/login/login.jsp");
			return;
		}
		StudentVO studentVO = (StudentVO) session.getAttribute("studentVO");
		if (studentVO == null)
			studentVO = new StudentService().getOneStudentByUserNo(userVO.getUserNo());
		String action = req.getParameter("action");
		if (action == null) {
			res.setContentType("text/html;");
			req.setAttribute("list", studentVO.getLeaveList());
			String url = "/front-end/leave/listLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("new".equals(action)) {
			res.setContentType("text/html;");
			req.setAttribute("mode", "new");
			req.setAttribute("calenderEvents", new LeaveService().getCalenderEventsJson(studentVO.getStudentNo()));
			String url = "/front-end/leave/editorLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("insert".equals(action)) {
			res.setContentType("text/html;");
			String studentNo = req.getParameter("studentNo");
			String timetableNo = req.getParameter("timetableNo");
			Integer type = new Integer(req.getParameter("type"));
			String description = req.getParameter("description");
			new LeaveService().addLeave(studentNo, timetableNo, type, description);
			req.setAttribute("list", studentVO.getLeaveList());
			String url = "/front-end/leave/listLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("update".equals(action)) {
			res.setContentType("text/html;");
			String leaveNo = req.getParameter("leaveNo");
			String studentNo = req.getParameter("studentNo");
			String timetableNo = req.getParameter("timetableNo");
			Integer type = new Integer(req.getParameter("type"));
			String description = req.getParameter("description");
			LeaveVO _leaveVO = new LeaveService().getOneLeave(leaveNo);
			new LeaveService().updateLeaveVO(leaveNo, studentNo, timetableNo, type, description, _leaveVO.getStatus());
			req.setAttribute("list", studentVO.getLeaveList());
			String url = "/front-end/leave/listLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("display_for_update".equals(action)) {
			res.setContentType("text/html;");
			String leaveNo = req.getParameter("leaveNo");
			LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
			TimetableVO timetableVO = leaveVO.getTimetableVO();
			req.setAttribute("mode", "update");
			req.setAttribute("leaveVO", leaveVO);
			req.setAttribute("calenderEvents",
					new LeaveService().getCalenderEventsJson(studentVO.getStudentNo(), leaveVO));
			req.setAttribute("timetableInfo", String.format("%s,%s,%s", timetableVO.getTimetableDate(),
					timetableVO.getPeriodEnum().getText(), timetableVO.getCourseVO().getCourseName()));
			String url = "/front-end/leave/editorLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("cancel".equals(action)) {
			res.setContentType("text/html;");
			String leaveNo = req.getParameter("leaveNo");
			new LeaveService().cancelLeave(leaveNo);
			req.setAttribute("list", studentVO.getLeaveList());
			String url = "/front-end/leave/listLeave.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
	}

}
