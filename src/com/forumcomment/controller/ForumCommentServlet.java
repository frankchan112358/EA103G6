package com.forumcomment.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.forumcomment.model.*;

public class ForumCommentServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("forumCommentNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入留言編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumcomment/forum_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String forumCommentNo = null;
				try {
					forumCommentNo = new String(str);
				} catch (Exception e) {
					errorMsgs.add("留言編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumcomment/forum_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ForumCommentService forumcommentSvc = new ForumCommentService();
				ForumCommentVO forumCommentVO = forumcommentSvc.getOneForumComment(forumCommentNo);
				if (forumCommentVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumcomment/forum_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("forumCommentVO", forumCommentVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/forumcomment/listOneForumComment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumcomment/forum_select_page.jsp");
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
				String forumCommentNo = new String(req.getParameter("forumCommentNo"));

				/*************************** 2.開始查詢資料 ****************************************/
				ForumCommentService forumcommentSvc = new ForumCommentService();
				ForumCommentVO forumCommentVO = forumcommentSvc.getOneForumComment(forumCommentNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("forumCommentVO", forumCommentVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/forumcomment/update_forumComment_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumcomment/listAllForumComment.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);// 位址的值

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String forumCommentNo = new String(req.getParameter("forumCommentNo").trim());

				String forumPostNo = req.getParameter("forumPostNo");
				if (forumPostNo == null || forumPostNo.trim().length() == 0) {
					errorMsgs.add("貼文編號: 請勿空白");
				}

				String studentNo = req.getParameter("studentNo").trim();
				if (studentNo == null || studentNo.trim().length() == 0) {
					errorMsgs.add("學員編號請勿空白");
				}
				
				String	content = req.getParameter("content").trim();
				if (content == null || content.trim().length() == 0) {
					errorMsgs.add("貼文內容請勿空白");
				}
				
				ForumCommentVO forumCommentVO = new ForumCommentVO();
				forumCommentVO.setForumPostNo(forumPostNo);
				forumCommentVO.setStudentNo(studentNo);
				forumCommentVO.setContent(content);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("forumCommentVO", forumCommentVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumcomment/update_forumcomment_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				ForumCommentService forumcommentSvc = new ForumCommentService();
				forumCommentVO = forumcommentSvc.updateForumComment(forumCommentNo,forumPostNo,studentNo, content);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("forumCommentVO", forumCommentVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/forumcomment/listOneForumComment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumcomment/update_forumComment_input.jsp");
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
				String forumPostNo = req.getParameter("forumPostNo");
				if (forumPostNo == null || forumPostNo.trim().length() == 0) {
					errorMsgs.add("貼文編號: 請勿空白");
				
				}

				String studentNo = req.getParameter("studentNo").trim();
				if (studentNo == null || studentNo.trim().length() == 0) {
					errorMsgs.add("學員編號請勿空白");
				}
								
				String	content = req.getParameter("content").trim();
				if (content == null || content.trim().length() == 0) {
					errorMsgs.add("留言內容請勿空白");
				}
				
				ForumCommentVO forumCommentVO = new ForumCommentVO();
				forumCommentVO.setForumPostNo(forumPostNo);
				forumCommentVO.setStudentNo(studentNo);
				forumCommentVO.setContent(content);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("forumCommentVO", forumCommentVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumcomment/addForumComment.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				ForumCommentService forumcommentSvc = new ForumCommentService();
				forumCommentVO = forumcommentSvc.addForumComment(forumPostNo,studentNo,content);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/forumcomment/listAllForumComment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("insert error"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumcomment/addForumComment.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String forumCommentNo = new String(req.getParameter("forumCommentNo"));

				/*************************** 2.開始刪除資料 ***************************************/
				ForumCommentService forumcommentSvc = new ForumCommentService();
				forumcommentSvc.deleteForumComment(forumCommentNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/forumcomment/listAllForumComment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumcomment/listAllForumComment.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
