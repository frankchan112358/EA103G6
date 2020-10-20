package com.leave.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leave.model.LeaveService;
import com.leave.model.LeaveStatus;

public class UpdateStudentLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			String studentNo = req.getParameter("studentNo");
			String leaveNo = req.getParameter("leaveNo");
			String timetableNo = req.getParameter("timetableNo");
			Integer type = new Integer(req.getParameter("type"));
			String description = req.getParameter("description");
			new LeaveService().updateLeaveVO(leaveNo, studentNo, timetableNo, type, description, LeaveStatus.Review.getNum());
			out.print("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
