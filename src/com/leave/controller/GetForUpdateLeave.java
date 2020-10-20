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
import com.timetable.model.TimetableVO;

public class GetForUpdateLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String leaveNo = req.getParameter("leaveNo");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		LeaveVO leaveVO = new LeaveService().getOneLeave(leaveNo);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("leaveNo", leaveVO.getLeaveNo());
		jsonObject.addProperty("timetableNo", leaveVO.getTimetableNo());
		jsonObject.addProperty("type", leaveVO.getType());
		jsonObject.addProperty("description", leaveVO.getDescription());
		jsonObject.add("timetableEvent", timetableEvent(leaveVO.getTimetableVO()));
		String jsonStr = gson.toJson(jsonObject);
		out.print(jsonStr);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	private JsonObject timetableEvent(TimetableVO timetableVO) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", timetableVO.getTimetableNo());
		jsonObject.addProperty("title",
				String.format("%s,%s", timetableVO.getPeriodText(), timetableVO.getCourseVO().getCourseName()));
		jsonObject.addProperty("start",
				String.format("%sT%s", timetableVO.getTimetableDate(), timetableVO.getPeriodEnum().getStart()));
		jsonObject.addProperty("borderColor", "red");
		JsonObject extendedProps = new JsonObject();
		extendedProps.addProperty("timetableInfo", String.format("%s,%s,%s", timetableVO.getTimetableDate(),
				timetableVO.getPeriodEnum().getText(), timetableVO.getCourseVO().getCourseName()));
		jsonObject.add("extendedProps", extendedProps);
		return jsonObject;
	}
}
