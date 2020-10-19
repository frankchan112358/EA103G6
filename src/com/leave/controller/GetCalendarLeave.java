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
import com.timetable.model.TimetableVO;

public class GetCalendarLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String studentNo = req.getParameter("studentNo");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray jsonArray = new JsonArray();
		for (TimetableVO timetableVO : new LeaveService().getTimetableEvents(studentNo)) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", timetableVO.getTimetableNo());
			jsonObject.addProperty("title", String.format("%s,%s",timetableVO.getPeriodText(),timetableVO.getCourseVO().getCourseName()));
			jsonObject.addProperty("start", String.format("%sT%s", timetableVO.getTimetableDate(),timetableVO.getPeriodEnum().getStart()));
			jsonObject.addProperty("borderColor", "");
			JsonObject extendedProps = new JsonObject();
			extendedProps.addProperty("timetableInfo", String.format("%s,%s,%s",timetableVO.getTimetableDate(),timetableVO.getPeriodEnum().getText(),timetableVO.getCourseVO().getCourseName()));
			jsonObject.add("extendedProps", extendedProps);
			jsonArray.add(jsonObject);
		}
		String jsonStr = gson.toJson(jsonArray);
		out.print(jsonStr);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
