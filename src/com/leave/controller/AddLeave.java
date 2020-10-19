package com.leave.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leave.model.LeaveService;

public class AddLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			String studentNo = req.getParameter("studentNo");
			String timetableNo = req.getParameter("timetableNo");
			Integer type = new Integer(req.getParameter("type").trim());
			String description = req.getParameter("description");
			new LeaveService().addLeave(studentNo, timetableNo, type, description);
			out.print("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
