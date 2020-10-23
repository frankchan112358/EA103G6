package com.course.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.timetable.model.TimetableService;
import com.timetable.model.TimetableVO;
import com.video.model.VideoService;
import com.video.model.VideoVO;

@WebServlet("/CourseTTSvc")
public class CourseTTServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CourseTTServlet() {
		super();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("getTTDisplayList".equals(action)) {
			String courseNo = req.getParameter("courseNo");
			CourseService courseSvc = new CourseService();
			CourseVO courseVO = courseSvc.getOneCourse(courseNo);
			req.setAttribute("courseVO", courseVO);
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/video/listAllVideo2.jsp");
			successView.forward(req, res);
		}

	}

}
