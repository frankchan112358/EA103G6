package com.classroom.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classroom.model.ClassroomService;
import com.classroom.model.ClassroomVO;

public class ClassroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String str = req.getParameter("classroomNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入教室編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/classroom/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				String classroomNo = null;
				try {
					classroomNo = str;
				} catch (Exception e) {
					errorMsgs.add("教室編號格式不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/classroom/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				ClassroomService classroomSvc = new ClassroomService();
				ClassroomVO classroomVO = classroomSvc.getOneClassroom(classroomNo);
				if (classroomVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/classroom/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("classroomVO", classroomVO);
				String url = "/back-end/classroom/listOneClassroom.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/classroom/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String classroomNo = req.getParameter("classroomNo");

				ClassroomService classroomSvc = new ClassroomService();
				ClassroomVO classroomVO = classroomSvc.getOneClassroom(classroomNo);

				req.setAttribute("classroomVO", classroomVO);
				String url = "/back-end/classroom/update_classroom_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/classroom/listAllClassroom.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String classroomNo = req.getParameter("classroomNo");

				String classroomName = req.getParameter("classroomName");
				String classroomNameReg = "^[(\u4e00-\u9fd5)(a-zA-Z0-9_)]{2,10}$";
				if (classroomName == null || classroomName.trim().length() == 0) {
					errorMsgs.add("教室名稱請勿空白");
				} else if (!classroomName.trim().matches(classroomNameReg)) {
					errorMsgs.add("教室名稱只能是中、英文字母、數字，其他不行。");
				}

				Integer numberOfStudent = null;
				try {
					numberOfStudent = new Integer(req.getParameter("numberOfStudent").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請填寫上課人數");
				}

				ClassroomVO classroomVO = new ClassroomVO();
				classroomVO.setClassroomNo(classroomNo);
				classroomVO.setClassroomName(classroomName);
				classroomVO.setNumberOfStudent(numberOfStudent);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("classroomVO", classroomVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/classroom/update_classroom_input.jsp");
					failureView.forward(req, res);
					return;
				}

				ClassroomService classroomSvc = new ClassroomService();
				classroomVO = classroomSvc.updateClassroom(classroomNo, classroomName, numberOfStudent);

				req.setAttribute("classroomVO", classroomVO);
				String url = "/back-end/classroom/listOneClassroom.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/classroom/update_classroom_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String classroomName = req.getParameter("classroomName");
				String classroomNameReg = "^[(\u4e00-\u9fd5)(a-zA-Z0-9_)]{2,10}$";
				if (classroomName == null || classroomName.trim().length() == 0) {
					errorMsgs.add("教室名稱請勿空白");
				} else if (!classroomName.trim().matches(classroomNameReg)) {
					errorMsgs.add("教室名稱只能是中、英文字母、數字，其他不行。");
				}

				Integer numberOfStudent = null;
				try {
					numberOfStudent = new Integer(req.getParameter("numberOfStudent").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請填寫人數");
				}

				ClassroomVO classroomVO = new ClassroomVO();
				classroomVO.setClassroomName(classroomName);
				classroomVO.setNumberOfStudent(numberOfStudent);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("classroomVO", classroomVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/classroom/addClassroom.jsp");
					failureView.forward(req, res);
					return;
				}

				ClassroomService classroomSvc = new ClassroomService();
				classroomVO = classroomSvc.addClassroom(classroomName, numberOfStudent);

				String url = "/back-end/classroom/listAllClassroom.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/classroom/addClassroom.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String classroomNo = req.getParameter("classroomNo");

				ClassroomService classroomSvc = new ClassroomService();
				classroomSvc.deleteClassroom(classroomNo);

				String url = "/back-end/classroom/listAllClassroom.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/classroom/listAllClassroom.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
