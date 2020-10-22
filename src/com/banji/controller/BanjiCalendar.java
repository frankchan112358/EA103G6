package com.banji.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.timetable.model.TimetableVO;
import com.user.model.UserVO;

public class BanjiCalendar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		if (userVO == null || userVO.getType() != 2) {
			res.sendRedirect(req.getContextPath() + "/login/login.jsp");
			return;
		}
		String banjiNo = req.getParameter("banjiNo");
		if (banjiNo ==null) {
			res.sendRedirect(req.getContextPath() + "/banji/banji.manage");
			return;
		}
		BanjiVO banjiVO = new BanjiService().getOneBanji(banjiNo);
		EmpVO empVO = (EmpVO) session.getAttribute("empVO");
		if(empVO==null)
			empVO=new EmpService().getOneEmpByUserNo(userVO.getUserNo());
		String action = req.getParameter("action");
		if ("events".equals(action)) {
			res.setContentType("application/json;");
			PrintWriter out = res.getWriter();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			JsonArray jsonArray = new JsonArray();
			for (TimetableVO timetableVO : banjiVO.getTimetableList()) {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("id", timetableVO.getTimetableNo());
				jsonObject.addProperty("title", String.format("%s %s %s",timetableVO.getPeriodText(),timetableVO.getCourseVO().getCourseName(),timetableVO.getCourseVO().getTeacherVO().getTeacherName()));
				jsonObject.addProperty("start", String.format("%sT%s", timetableVO.getTimetableDate(),timetableVO.getPeriodEnum().getStart()));
				jsonObject.addProperty("borderColor", "");
				JsonObject extendedProps = new JsonObject();
				extendedProps.addProperty("timetableInfo", String.format("%s,%s,%s",timetableVO.getTimetableDate(),timetableVO.getPeriodEnum().getText(),timetableVO.getCourseVO().getCourseName()));
				jsonObject.add("extendedProps", extendedProps);
				jsonArray.add(jsonObject);
			}
			String jsonStr = gson.toJson(jsonArray);
			out.print(jsonStr);
			return;
		}
	}

}
