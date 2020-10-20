package com.student.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.student.model.*;
import com.user.*;


public class StudentServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		

		if ("getFace".equals(action)) {

			String studentNo = req.getParameter("studentNo");

			StudentService userSvc = new StudentService();
			InputStream in = userSvc.getPic(studentNo);

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

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("studentNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入學員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/selectpage.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String studentNo = null;
				try {
					studentNo = str;
				} catch (Exception e) {
					errorMsgs.add("使用者編號格式不正確"); // 錯誤訊息暫時保留
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/selectpage.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				StudentService studentSvc = new StudentService();
				StudentVO studentVO = studentSvc.getOneStudent(studentNo);
				if (studentVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/selectpage.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("studentVO", studentVO); // 資料庫取出的empVO物件,存入req
				String url = "/student/listOneStudent1.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/selectpage.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String userNo = req.getParameter("userNo");

				if (userNo == null || userNo.trim().length() == 0) {
					errorMsgs.add("學員編號請勿空白");
				}

				String banjiNo = req.getParameter("banjiNo").trim();
				if (banjiNo == null || banjiNo.trim().length() == 0) {
					errorMsgs.add("班級編號請勿空白");
				}

				String studentName = req.getParameter("studentName");
				String studentNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (studentName == null || studentName.trim().length() == 0) {
					errorMsgs.add("學員姓名: 請勿空白");
				} else if (!studentName.trim().matches(studentNameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("學員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String faceId = req.getParameter("faceId");
				String studentDescription = req.getParameter("studentDescription");

//				Part part=req.getPart("face");
//				InputStream face=null;
//				String form = part.getContentType().toLowerCase();
//				if (form.contains("application/octet-stream")) {
//					
//				}else if(!form.contains("image")) {
//					errorMsgs.add("僅接受圖檔格式上傳");
//				}
//				else {
//					face = part.getInputStream();
//				}

				Integer studentStatus = new Integer(req.getParameter("studentStatus").trim());

				StudentVO studentVO = new StudentVO();

				studentVO.setUserNo(userNo);
				studentVO.setBanjiNo(banjiNo);
				studentVO.setStudentName(studentName);
				studentVO.setFaceId(faceId);
				studentVO.setStudentDescription(studentDescription);
				studentVO.setStudentStatus(studentStatus);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("studentVO", studentVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/addStudent.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				StudentService studentSvc = new StudentService();
//				studentVO = studentSvc.addStudent(userNo, banjiNo, studentName, faceId, studentDescription,
//						studentStatus);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/student/listAllStudent.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/addStudent.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String studentNo = req.getParameter("studentNo");

				/*************************** 2.開始刪除資料 ***************************************/
				StudentService StudentSvc = new StudentService();
				StudentSvc.deleteStudent(studentNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/student/listAllStudent.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/listAllStudent.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String studentNo = new String(req.getParameter("studentNo"));

				/*************************** 2.開始查詢資料 ****************************************/
				StudentService studentSvc = new StudentService();
				StudentVO studentVO = studentSvc.getOneStudent(studentNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("studentVO", studentVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/student/update_student_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/listAllStudent.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String studentNo = new String(req.getParameter("studentNo").trim());
				
				String userNo = req.getParameter("userNo");

				if (userNo == null || userNo.trim().length() == 0) {
					errorMsgs.add("學員編號請勿空白");
				}

				String banjiNo = req.getParameter("banjiNo").trim();
				if (banjiNo == null || banjiNo.trim().length() == 0) {
					errorMsgs.add("班級編號請勿空白");
				}

				String studentName = req.getParameter("studentName");
				String studentNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (studentName == null || studentName.trim().length() == 0) {
					errorMsgs.add("學員姓名: 請勿空白");
				} else if (!studentName.trim().matches(studentNameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("學員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String faceId = req.getParameter("faceId");
				String studentDescription = req.getParameter("studentDescription");

//				Part part = req.getPart("face");
//				InputStream face = null;
//				String form = part.getContentType().toLowerCase();
//				if (form.contains("application/octet-stream")) {
//
//				} else if (!form.contains("image")) {
//					errorMsgs.add("僅接受圖檔格式上傳");
//				} else {
//					face = part.getInputStream();
//				}

				Integer studentStatus = new Integer(req.getParameter("studentStatus").trim());

				StudentVO studentVO = new StudentVO();
				studentVO.setStudentNo(studentNo);
				studentVO.setUserNo(userNo);
				studentVO.setBanjiNo(banjiNo);
				studentVO.setStudentName(studentName);

				studentVO.setFaceId(faceId);
				studentVO.setStudentDescription(studentDescription);
				studentVO.setStudentStatus(studentStatus);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("studentVO", studentVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/update_student_input.jsp");
					failureView.forward(req, res);
					return;
				}
				//, face
				/*************************** 2.開始新增資料 ***************************************/
				StudentService studentSvc = new StudentService();
				studentVO = studentSvc.updateStudent(studentNo,userNo, banjiNo, studentName, faceId, studentDescription,
						studentStatus);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("studentVO", studentVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/student/listOneStudent.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/update_student_input.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
