package com.timetable.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.timetable.model.TimetableService;
import com.timetable.model.TimetableVO;

@WebServlet("/TimetableServlet")
public class TimetableServlet extends HttpServlet {
       
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求，查詢單個課表的資料
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String str = req.getParameter("timetableNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入課表編號");
				}
				if (!errorMsgs.isEmpty() ) {
					RequestDispatcher failureView = 
							req.getRequestDispatcher("/back-end/timetable/select_page.jsp");
					failureView.forward(req, res);
					return;
				} 
				
				TimetableService timetableSvc = new TimetableService();
				TimetableVO timetableVO = timetableSvc.getOneTimetable(str);
				if (timetableVO == null) {
					errorMsgs.add("查無資料");
				}
				
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/timetable/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				req.setAttribute("timetableVO", timetableVO);
				String url = "/back-end/timetable/listOneTimetable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = 
						req.getRequestDispatcher("/back-end/timetable/select_page.jsp");
				failureView.forward(req, res);
			}
		} 
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllTimetable.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String timetableNo = req.getParameter("timetableNo");
				
				TimetableService timetableSvc = new TimetableService();
				TimetableVO timetableVO = timetableSvc.getOneTimetable(timetableNo);
				
				req.setAttribute("timetableVO", timetableVO);
				String url = "/back-end/timetable/update_timetable_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/timetable/listAllTimetable.jsp");
				failureView.forward(req, res);
			}
		} 
		
		 	                   
		if ("update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String timetableNo = req.getParameter("timetableNo");
				
				String classroomNo = req.getParameter("classroomNo");
				String classroomNoReg = "[0-9]{1}";
				if (classroomNo == null || classroomNo.trim().length() == 0) {
					errorMsgs.add("教室編號: 請勿空白");
				} else if(!classroomNo.trim().matches(classroomNoReg)) {
					errorMsgs.add("課程編號僅能是數字");
				}
				
				String courseNo = req.getParameter("courseNo");
				String courseNoReg = "[C][0-9]{3}";
				if (courseNo == null || courseNo.trim().length() == 0) {
					errorMsgs.add("課程編號: 請勿空白");
				} else if(!courseNo.trim().matches(courseNoReg)) {
					errorMsgs.add("課程編號僅能是C開頭加三個數字");
				}
				
				Integer timetablePeriod = Integer.parseInt(req.getParameter("timetablePeriod"));
				String timetablePeriodReg = "[0-2]{1}";
				if (timetablePeriod == null || timetablePeriod.toString().trim().length() == 0) {
					errorMsgs.add("上課時段錯誤: 請勿空白");
				} else if (!timetablePeriod.toString().trim().matches(timetablePeriodReg)) {
					errorMsgs.add("上課時段僅能是0-2之間的數");
				}
				
				Date timetableDate = Date.valueOf(req.getParameter("timetableDate"));
				String teachingLog = req.getParameter("teachingLog");
				
				
				TimetableVO timetableVO = new TimetableVO();
				timetableVO.setTimetableNo(timetableNo);
				timetableVO.setCourseNo(courseNo);
				timetableVO.setClassroomNo(classroomNo);
				timetableVO.setTimetablePeriod(timetablePeriod);
				timetableVO.setTimetableDate(timetableDate);
				timetableVO.setTeachingLog(teachingLog);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("timetableVO", timetableVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/timetable/update_timetable_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				TimetableService timetableSvc = new TimetableService();
				timetableVO = timetableSvc.updateTimetable(timetableNo, courseNo, classroomNo, timetablePeriod, timetableDate, teachingLog);

				req.setAttribute("timetableVO", timetableVO);
				String url = "/back-end/timetable/listAllTimetable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/timetable/update_timetable_input.jsp");
				failureView.forward(req,  res);
			}
		} 
		
		
		if ("insert".equals(action)) { // 來自addTimetable.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String courseNo = req.getParameter("courseNo");
				String courseNoReg = "[C][0-9]{3}";
				if (courseNo == null || courseNo.trim().length() == 0) {
					errorMsgs.add("課程編號: 請勿空白");
				} else if(!courseNo.trim().matches(courseNoReg)) {
					errorMsgs.add("課程編號僅能是C開頭加三個數字");
				}
				
				String classroomNo = req.getParameter("classroomNo");
				String classroomNoReg = "[0-9]{1}";
				if (classroomNo == null || classroomNo.trim().length() == 0) {
					errorMsgs.add("教室編號: 請勿空白");
				} else if(!classroomNo.trim().matches(classroomNoReg)) {
					errorMsgs.add("課程編號僅能是1個數字");
				}
				
				Integer timetablePeriod = Integer.parseInt(req.getParameter("timetablePeriod"));
				String timetablePeriodReg = "[0-2]{1}";
				if (timetablePeriod == null || timetablePeriod.toString().length() == 0) {
					errorMsgs.add("上課時段: 請勿空白");
				} else if (!timetablePeriod.toString().trim().matches(timetablePeriodReg)) {
					errorMsgs.add("上課時段格式錯誤");
				}
				
				java.sql.Date timetableDate = null;
				try {
					timetableDate = java.sql.Date.valueOf(req.getParameter("timetableDate").trim());
				} catch (IllegalArgumentException e) {
					timetableDate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String teachingLog = req.getParameter("teachingLog");
				if (teachingLog == null || teachingLog.trim().length() == 0) {
					errorMsgs.add("日誌請勿空白");
				}
				
				TimetableVO timetableVO = new TimetableVO();
				timetableVO.setCourseNo(courseNo);
				timetableVO.setClassroomNo(classroomNo);
				timetableVO.setTimetablePeriod(timetablePeriod);
				timetableVO.setTimetableDate(timetableDate);
				timetableVO.setTeachingLog(teachingLog);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("timetableVO", timetableVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/timetable/addTimetable.jsp");
					failureView.forward(req, res);
					return;
				}
				
				TimetableService timetableSvc = new TimetableService();
				timetableVO = timetableSvc.addTimetable(courseNo, classroomNo, timetablePeriod, timetableDate, teachingLog);

				
				String url = "/back-end/timetable/listAllTimetable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch(Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/timetable/addTimetable.jsp");
				failureView.forward(req, res); 
			}
		}
		
		
		if ("delete".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String timetableNo = req.getParameter("timetableNo");
				
				TimetableService timetableSvc = new TimetableService();
				timetableSvc.deleteTimetable(timetableNo);
				
				String url = "/back-end/timetable/listAllTimetable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/timetable/listAllTimetable.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
