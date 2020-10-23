package com.banji.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banji.model.BanjiService;
import com.banji.model.BanjiVO;
import com.course.model.CourseService;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teacher.model.TeacherVO;
import com.timetable.model.TimetableService;
import com.timetable.model.TimetableVO;
import com.user.model.UserVO;

public class BanjiTimetable extends HttpServlet {
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
		if (action == null) {
			String events = new TimetableService().getAllJsonWithBanjiNo(banjiNo);
			req.setAttribute("banjiVO", banjiVO);
			req.setAttribute("events", events);
			String url = "/back-end/banji/timetable/timetable.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
//		if ("banji_and_teacher_timetable".equals(action)) {
//			res.setContentType("application/json;");
//			String courseNo= req.getParameter("courseNo");
//			PrintWriter out = res.getWriter();
//			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//			JsonObject jsonObject = new JsonObject();
//			jsonObject.add("events", new TimetableService().getAllJsonArrayWithBanjiNoCourseNo(banjiNo, courseNo));		
//			jsonObject.addProperty("_courseVO",gson.toJson(new CourseService().getOneCourse(courseNo)));
//			String jsonStr = gson.toJson(jsonObject);
//			out.print(jsonStr);
//			return;
//		}
		if ("banji_and_teacher_timetable".equals(action)) {
			res.setContentType("application/json;");
			String courseNo= req.getParameter("courseNo");
			PrintWriter out = res.getWriter();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			JsonObject jsonObject = new JsonObject();
			
			TeacherVO teacherVO = new CourseService().getOneCourse(courseNo).getTeacherVO();
			String teacherNo = teacherVO.getTeacherNo();		
			JsonArray jsonArray = new JsonArray();
			for (TimetableVO ttVO :new TimetableService().getAll()) {
				String ttCourseNo = ttVO.getCourseNo();
				String ttTeacherNo = ttVO.getCourseVO().getTeacherVO().getTeacherNo();
				String ttBanjiNo = ttVO.getCourseVO().getBanjiNo();
				JsonObject jsonObj = new JsonObject();
				if (ttBanjiNo.equals(banjiNo)&&ttCourseNo.equals(courseNo)) {
					jsonObj.addProperty("title", String.format("%s %s %s",ttVO.getPeriodText(),ttVO.getCourseVO().getCourseName(),ttVO.getCourseVO().getTeacherVO().getTeacherName()));				
					jsonObj.addProperty("backgroundColor", "red");
					jsonObj.addProperty("editable", true);
				}else if(ttBanjiNo.equals(banjiNo)){
					jsonObj.addProperty("title", String.format("%s %s %s",ttVO.getPeriodText(),ttVO.getCourseVO().getCourseName(),ttVO.getCourseVO().getTeacherVO().getTeacherName()));				
					jsonObj.addProperty("backgroundColor", "gray");
					jsonObj.addProperty("editable", false);
				}else if (ttTeacherNo.equals(teacherNo)) {
					jsonObj.addProperty("title", String.format("%s %s %s %s",ttVO.getPeriodText(),ttVO.getCourseVO().getBanjiVO().getBanjiName(),ttVO.getCourseVO().getCourseName(),ttVO.getCourseVO().getTeacherVO().getTeacherName()));				
					jsonObj.addProperty("backgroundColor", "green");
					jsonObj.addProperty("editable", false);
				}else {
					continue;
				}
				jsonObj.addProperty("id", ttVO.getTimetableNo());
				jsonObj.addProperty("start", String.format("%sT%s", ttVO.getTimetableDate(),ttVO.getPeriodEnum().getStart()));
				jsonObj.addProperty("borderColor", "");
				jsonObj.add("extendedProps",gson.fromJson(gson.toJson(ttVO), JsonObject.class) );
				jsonArray.add(jsonObj);
			}			
			
			jsonObject.add("events", jsonArray);		
			jsonObject.addProperty("_courseVO",gson.toJson(new CourseService().getOneCourse(courseNo)));
			String jsonStr = gson.toJson(jsonObject);
			out.print(jsonStr);
			return;
		}
	}

}
