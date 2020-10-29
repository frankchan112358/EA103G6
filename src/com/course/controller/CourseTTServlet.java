package com.course.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.course.model.CourseService;
import com.course.model.CourseVO;

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
		
		HttpSession session = req.getSession();
		String courseNo = (String)session.getAttribute("courseNo");
		CourseVO courseVO = new CourseService().getOneCourse(courseNo);
		if (courseVO == null) {
			req.getRequestDispatcher("/back-end/course/listAllCourse.jsp").forward(req, res);
			return;
		}
		
		if ("getTTDisplayList".equals(action)) {
			session.setAttribute("courseWork", "courseVideo");
			req.setAttribute("courseVO", courseVO);
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/video/listAllVideo2.jsp");
			successView.forward(req, res);
		}
		
		if ("getTFDisplayList".equals(action)) {
			session.setAttribute("courseWork", "teachingFile");
			req.setAttribute("courseVO", courseVO);
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/teachingFile/listAllTeachingFile2.jsp");
			successView.forward(req, res);
		}
		
		if ("getFSDisplayList".equals(action)) {
			session.setAttribute("courseWork", "finalScore");
			req.setAttribute("courseVO", courseVO);
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/finalscore/listFinalScore.jsp");
			successView.forward(req, res);
		}

	}

}
