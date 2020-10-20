package com.leave.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.leave.model.LeaveService;
import com.leave.model.LeaveVO;

public class GetOneLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String leaveNo = req.getParameter("leaveNo");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("timetableDate", leaveVO.getTimetableVO().getTimetableDate().toString());
		jsonObject.addProperty("periodText", leaveVO.getTimetableVO().getPeriodText());
		jsonObject.addProperty("courseName", leaveVO.getTimetableVO().getCourseVO().getCourseName());
		jsonObject.addProperty("typeText", leaveVO.getTypeText());
		jsonObject.addProperty("statusText", leaveVO.getStatusText());
		jsonObject.addProperty("description", leaveVO.getDescription());
		String jsonStr = gson.toJson(jsonObject);
		out.print(jsonStr);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}

}
