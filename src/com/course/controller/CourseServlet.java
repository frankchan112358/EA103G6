package com.course.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.course.model.*;

@MultipartConfig


public class CourseServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getCourseImg".equals(action)) {

			String courseNo = req.getParameter("courseNo");

			CourseService courseSvc = new CourseService();
			InputStream in = courseSvc.getCourseImg(courseNo);

			ServletOutputStream out = res.getOutputStream();
			res.setContentType("image/gif");

			byte[] buf = new byte[4 * 1024];
			int len = 0;

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
			in.close();
		}

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("courseNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("⚠請輸入課程編號⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failuresView = req.getRequestDispatcher("/back-end/course/listAllCourse.jsp");
					failuresView.forward(req, res);
					return;
				}
				String courseNo = null;
				try {
					courseNo = new String(str);
				} catch (Exception e) {
					errorMsgs.add("⚠課程編號格式不正確⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course/listAllCourse.jsp");
					failureView.forward(req, res);
					return;
				}

				CourseService courseSvc = new CourseService();
				CourseVO courseVO = courseSvc.getOneCourse(courseNo);
				if (courseVO == null) {
					errorMsgs.add("⚠查無資料⚠");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course/listAllCourse.jsp");
					failureView.forward(req, res);
					return;
				}
				HttpSession session = req.getSession();
				session.setAttribute("courseVO", courseVO);
				String url = "/back-end/course/listOneCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("⚠無法取得資料⚠:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course/listAllCourse.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String courseNo = new String(req.getParameter("courseNo").trim());

				CourseService courseSvc = new CourseService();
				CourseVO courseVO = courseSvc.getOneCourse(courseNo);

				req.setAttribute("courseVO", courseVO);
				String url = "/back-end/course/update_course_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠無法取得要修改的資料⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course/listAllCourse.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String courseNo = new String(req.getParameter("courseNo").trim());

				String banjiNo = new String(req.getParameter("banjiNo").trim());
				if (banjiNo == null || banjiNo.trim().length() == 0) {
					errorMsgs.put("banjiNo", "⚠班級請勿空白⚠");
				}

				String teacherNo = new String(req.getParameter("teacherNo").trim());
				if (teacherNo == null || teacherNo.trim().length() == 0) {
					errorMsgs.put("teacherNo", "⚠講師請勿空白⚠");
				}

				String classroomNo = new String(req.getParameter("classroomNo").trim());
				if (classroomNo == null || classroomNo.trim().length() == 0) {
					errorMsgs.put("classroomNo", "⚠教室請勿空白⚠");
				}

				String basicCourseNo = new String(req.getParameter("basicCourseNo").trim());
				if (basicCourseNo == null || basicCourseNo.trim().length() == 0) {
					errorMsgs.put("basicCourseNo", "⚠基本課程名稱請勿空白⚠");
				}

				String courseName = req.getParameter("courseName");
				if (courseName == null || courseName.trim().length() == 0) {
					errorMsgs.put("courseName", "⚠課程名稱請勿空白⚠");
				}

				String courseOutline = new String(req.getParameter("courseOutline").trim());

				Integer lesson = null;
				try {
					lesson = new Integer(req.getParameter("lesson").trim());
				} catch (NumberFormatException e) {
					lesson = 0;
					errorMsgs.put("lesson", "⚠堂數請勿空白⚠");
				}

				java.sql.Date startDate = null;
				try {
					startDate = java.sql.Date.valueOf(req.getParameter("startDate").trim());
				} catch (IllegalArgumentException e) {
					startDate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("startDate", "⚠請輸入開始日期⚠");
				}

				java.sql.Date endDate = null;
				try {
					endDate = java.sql.Date.valueOf(req.getParameter("endDate").trim());
				} catch (IllegalArgumentException e) {
					endDate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("endDate", "⚠請輸入結束日期⚠");
				}

				Part part = req.getPart("courseImg");
				InputStream courseImg = null;
				String form = part.getContentType().toLowerCase();

				CourseService courseSvc1 = new CourseService();
				courseImg = courseSvc1.getCourseImg(courseNo);
				

				if (form.contains("image") && errorMsgs.isEmpty()) {
					courseImg = part.getInputStream();
				}

				Integer status = new Integer(req.getParameter("status").trim());

				CourseVO courseVO = new CourseVO();
				courseVO.setCourseNo(courseNo);
				courseVO.setBanjiNo(banjiNo);
				courseVO.setTeacherNo(teacherNo);
				courseVO.setClassroomNo(classroomNo);
				courseVO.setBasicCourseNo(basicCourseNo);
				courseVO.setCourseName(courseName);
				courseVO.setCourseOutline(courseOutline);
				courseVO.setLesson(lesson);
				courseVO.setStartDate(startDate);
				courseVO.setEndDate(endDate);
				courseVO.setCourseImg(courseImg);
				courseVO.setStatus(status);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("courseVO", courseVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/course/update_course_input.jsp");
					failureView.forward(req, res);
					return;
				}

				CourseService courseSvc = new CourseService();
				courseVO = courseSvc.updateCourse(courseNo, banjiNo, teacherNo, classroomNo, basicCourseNo, courseName,
						courseOutline, lesson, startDate, endDate, courseImg, status);

				HttpSession session = req.getSession();
				session.setAttribute("courseVO", courseVO);
				String url = "/back-end/course/listOneCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠修改資料失敗⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course/update_course_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String banjiNo = new String(req.getParameter("banjiNo").trim());
				if (banjiNo == null || banjiNo.trim().length() == 0) {
					errorMsgs.put("banjiNo", "⚠班級請勿空白⚠");
				}

				String teacherNo = new String(req.getParameter("teacherNo").trim());
				if (teacherNo == null || teacherNo.trim().length() == 0) {
					errorMsgs.put("teacherNo", "⚠講師請勿空白⚠");
				}

				String classroomNo = new String(req.getParameter("classroomNo").trim());
				if (classroomNo == null || classroomNo.trim().length() == 0) {
					errorMsgs.put("classroomNo", "⚠教室請勿空白⚠");
				}

				String basicCourseNo = new String(req.getParameter("basicCourseNo").trim());
				if (basicCourseNo == null || basicCourseNo.trim().length() == 0) {
					errorMsgs.put("basicCourseNo", "⚠基本課程編號請勿空白⚠");
				}

				String courseName = req.getParameter("courseName");
				if (courseName == null || courseName.trim().length() == 0) {
					errorMsgs.put("courseName", "⚠課程名稱請勿空白⚠");
				}

				String courseOutline = new String(req.getParameter("courseOutline").trim());

				Integer lesson = null;
				try {
					lesson = new Integer(req.getParameter("lesson").trim());
				} catch (NumberFormatException e) {
					lesson = 0;
					errorMsgs.put("lesson", "⚠堂數請勿空白⚠");
				}

				java.sql.Date startDate = null;
				try {
					startDate = java.sql.Date.valueOf(req.getParameter("startDate").trim());
				} catch (IllegalArgumentException e) {
					startDate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("startDate", "⚠請輸入開始日期⚠");
				}

				java.sql.Date endDate = null;
				try {
					endDate = java.sql.Date.valueOf(req.getParameter("endDate").trim());
				} catch (IllegalArgumentException e) {
					endDate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("endDate", "⚠請輸入結束日期⚠");
				}
				
				Part part = req.getPart("courseImg");
				InputStream courseImg = null;
				String form = part.getContentType().toLowerCase();

				if (form.contains("image") && errorMsgs.isEmpty()) {
					courseImg = part.getInputStream();
				}
								
				Integer status = new Integer(req.getParameter("status").trim());

				CourseVO courseVO = new CourseVO();
				courseVO.setBanjiNo(banjiNo);
				courseVO.setTeacherNo(teacherNo);
				courseVO.setClassroomNo(classroomNo);
				courseVO.setBasicCourseNo(basicCourseNo);
				courseVO.setCourseName(courseName);
				courseVO.setCourseOutline(courseOutline);
				courseVO.setLesson(lesson);
				courseVO.setStartDate(startDate);
				courseVO.setEndDate(endDate);
				courseVO.setCourseImg(courseImg);
				courseVO.setStatus(status);
				

				if (!errorMsgs.isEmpty()) {
			        req.setAttribute("courseVO", courseVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course/addCourse.jsp");
					failureView.forward(req, res);
					return;
				}

				CourseService courseSvc = new CourseService();
				courseVO = courseSvc.addCourse(banjiNo, teacherNo, classroomNo, basicCourseNo, courseName,
						courseOutline, lesson, startDate, endDate, courseImg, status);
				
				String url = "/back-end/course/listAllCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("⚠新增資料失敗⚠", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course/addCourse.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String courseNo = new String(req.getParameter("courseNo"));

				CourseService courseSvc = new CourseService();
				courseSvc.deleteCourse(courseNo);

				String url = "/back-end/course/listAllCourse.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("⚠刪除資料失敗⚠:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/course/listAllCourse.jsp");
				failureView.forward(req, res);
			}
		}
	}
}