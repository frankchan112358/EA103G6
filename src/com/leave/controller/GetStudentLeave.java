package com.leave.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.leave.model.LeaveService;
import com.leave.model.LeaveVO;

public class GetStudentLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String studentNo = req.getParameter("studentNo");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (LeaveVO leaveVO : new LeaveService().getLeaveWithStudent(studentNo)) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("timetableDate", leaveVO.getTimetableVO().getTimetableDate().toString());
			jsonObject.addProperty("periodText", leaveVO.getTimetableVO().getPeriodText());
			jsonObject.addProperty("courseName", leaveVO.getTimetableVO().getCourseVO().getCourseName());
			jsonObject.addProperty("typeText", leaveVO.getTypeText());
			jsonObject.addProperty("statusText", leaveVO.getStatusText());
			jsonObject.add("action", getActionObj(leaveVO));
			jsonArray.add(jsonObject);
		}
		JsonObject data = new JsonObject();
		data.add("data", jsonArray);
		String jsonStr = gson.toJson(data);
		out.print(jsonStr);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private JsonObject getActionObj(LeaveVO leaveVO) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", leaveVO.getLeaveNo());
		jsonObject.addProperty("cando", getActionNum(leaveVO));
		return jsonObject;
	}

	private Integer getActionNum(LeaveVO leaveVO) {
		java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
		boolean flag = now.before(leaveVO.getTimetableVO().getTimetableDate());
		if (0 == leaveVO.getStatus() && flag)
			return 1;
		if (1 == leaveVO.getStatus() && flag)
			return 2;
		return 0;
	}
}
