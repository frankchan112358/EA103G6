package com.teachingnote.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.course.model.CourseService;
import com.course.model.CourseVO;

@WebServlet("/TeachingNote")
public class TeachingNote extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		String courseNo = (String) session.getAttribute("courseNo");
		CourseVO courseVO = new CourseService().getOneCourse(courseNo);
		if (courseVO == null) {
			res.sendRedirect(req.getContextPath() + "/course/course.do");
			return;
		}
		
		if (action == null) {
			res.sendRedirect(req.getContextPath() + "/course/course.do");
			return;
		}

		if ("getTNDisplayList".equals(action)) { // 來自listAllTeachingFile.jsp的請求
			session.setAttribute("courseWork", "teachingNote");
			req.setAttribute("courseVO", courseVO);

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String url = "/back-end/teachingnote/TeachingNote.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/teachingnote/TeachingNote.jsp");
				failureView.forward(req, res);
			}
		}

	}

}
