package com.leave.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leave.model.LeaveService;

public class CancelLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			String leaveNo = req.getParameter("leaveNo");
			new LeaveService().cancelLeave(leaveNo);
			out.print("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
