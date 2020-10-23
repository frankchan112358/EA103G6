package com.forumpost.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.forumcomment.model.*;
import com.forumpost.model.*;
import com.student.model.StudentService;
import com.student.model.StudentVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class ForumPostServlet extends HttpServlet {
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

				String forumPostNo = req.getParameter("forumPostNo");

				/*************************** 2.開始查詢資料 *****************************************/
				ForumPostService forumpostSvc = new ForumPostService();
				ForumPostVO forumPostVO = forumpostSvc.getOneForumPost(forumPostNo);

				int forumPostViews = forumPostVO.getForumPostViews() + 1;
				forumpostSvc.addForumPostViews(forumPostNo, forumPostViews);

				ForumCommentService forumcommentmSvc = new ForumCommentService();
				List<ForumCommentVO> list = forumcommentmSvc.getOneFpFc(forumPostVO.getForumPostNo());

				StudentService studentSvc = new StudentService();
				StudentVO studentVO = studentSvc.getOneStudent(forumPostVO.getStudentNo());

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/forumpost/forumPost_index.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("forumPostVO", forumPostVO);
				req.setAttribute("list", list);
				req.setAttribute("studentVO", studentVO);

				String url = "/front-end/forumpost/forumPostPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forumpost/forumPost_index.jsp");
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
				String forumPostNo = new String(req.getParameter("forumPostNo"));

				/*************************** 2.開始查詢資料 ****************************************/
				ForumPostService forumpostSvc = new ForumPostService();
				ForumPostVO forumPostVO = forumpostSvc.getOneForumPost(forumPostNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("forumPostVO", forumPostVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/forumpost/update_forumPost_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/forumpost/listAllForumPost.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);// 位址的值

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String forumPostNo = new String(req.getParameter("forumPostNo").trim());

				String forumTopicNo = req.getParameter("forumTopicNo");
				if (forumTopicNo == null || forumTopicNo.trim().length() == 0) {
					errorMsgs.add("主題編號: 請勿空白");
				}

				String studentNo = req.getParameter("studentNo").trim();
				if (studentNo == null || studentNo.trim().length() == 0) {
					errorMsgs.add("學員編號請勿空白");
				}

				String title = req.getParameter("title").trim();
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("貼文標題請勿空白");
				}

				String content = req.getParameter("content").trim();
				if (content == null || content.trim().length() == 0) {
					errorMsgs.add("貼文內容請勿空白");
				}

				ForumPostVO forumPostVO = new ForumPostVO();
				forumPostVO.setForumPostNo(forumPostNo);
				forumPostVO.setForumTopicNo(forumTopicNo);
				forumPostVO.setStudentNo(studentNo);
				forumPostVO.setTitle(title);
				forumPostVO.setContent(content);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("forumPostVO", forumPostVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/forumpost/updateForumPost.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				ForumPostService forumpostSvc = new ForumPostService();
				forumPostVO = forumpostSvc.updateForumPost(forumPostNo, forumTopicNo, studentNo, title, content);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("forumPostVO", forumPostVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改貼文失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forumpost/updateForumPost.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String forumTopicNo = req.getParameter("forumTopicNo");
				System.out.println(forumTopicNo);
				/*
				 * if (forumTopicNo == null || forumTopicNo.trim().length() == 0) {
				 * errorMsgs.add("主題編號: 請勿空白");
				 * 
				 * }
				 */

				String studentNo = req.getParameter("studentNo").trim();
				System.out.println(studentNo);
				/*
				 * if (studentNo == null || studentNo.trim().length() == 0) {
				 * errorMsgs.add("學員編號請勿空白"); }
				 */

				String title = req.getParameter("title").trim();
				System.out.println(title);
				/*
				 * if (title == null || title.trim().length() == 0) { errorMsgs.add("貼文標題請勿空白");
				 * }
				 */

				String content = req.getParameter("content");
				System.out.println(content);
				/*
				 * if (content == null || content.trim().length() == 0) {
				 * errorMsgs.add("貼文內容請勿空白"); }
				 */

				ForumPostVO forumPostVO = new ForumPostVO();
				forumPostVO.setForumTopicNo(forumTopicNo);
				forumPostVO.setStudentNo(studentNo);
				forumPostVO.setTitle(title);
				forumPostVO.setContent(content);

				if (!errorMsgs.isEmpty()) {

					req.setAttribute("forumPostVO", forumPostVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forumpost/addForumPost.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				ForumPostService forumpostSvc = new ForumPostService();
				forumPostVO = forumpostSvc.addForumPost(forumTopicNo, studentNo, title, content);

				req.setAttribute("forumPostVO", forumPostVO);
				String url = "/front-end/forumpost/forumPost_index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("無法新增貼文" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forumpost/addForumPost.jsp");
				failureView.forward(req, res);
			}
		}

		// 取得文章去修改
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String forumPostNo = new String(req.getParameter("forumPostNo"));

				/*************************** 2.開始查詢資料 ****************************************/
				ForumPostService forumpostSvc = new ForumPostService();
				ForumPostVO forumPostVO = forumpostSvc.getOneForumPost(forumPostNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("forumPostVO", forumPostVO);
				String url = "/front-end/forumpost/updateForumPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forumpost/user_forumPost.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String forumPostNo = new String(req.getParameter("forumPostNo"));

				/*************************** 2.開始刪除資料 ***************************************/
				ForumPostService forumpostSvc = new ForumPostService();
				forumpostSvc.deleteForumPost(forumPostNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/forumpost/user_forumPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forumpost/user_forumPost.jsp");
				failureView.forward(req, res);
			}
		}
		// 搜尋
		if ("search".equals(action)) {
			List<ForumPostVO> list = new ArrayList<ForumPostVO>();
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String title = new String(req.getParameter("title"));
				if (title == null || title.trim().length() == 0) {
					errorMsgs.add("請輸入搜尋內容!");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/forumpost/forumPost_index.jsp");
					failureView.forward(req, res);
					return;
				}

				ForumPostService forumpostSvc = new ForumPostService();
				HttpSession session = req.getSession();
				list = forumpostSvc.search(title);
				session.setAttribute("list", list);

				String url = "/front-end/forumpost/forumPostIndex_search.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要搜尋的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forumpost/forumPost_index.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("getByTopicNo".equals(action)) {
			String topicNo = req.getParameter("forumTopicNo");
			
			
			HttpSession session = req.getSession();
			
            if (req.getParameter("whichPage") == null){
            	session.setAttribute("topicNo", topicNo);
            } else {
            	String topicNo2 = (String)session.getAttribute("topicNo");
            	ForumPostService forumpostSvc = new ForumPostService();
    			List<ForumPostVO> list = forumpostSvc.getByTopicNo(topicNo2);
    			req.setAttribute("list", list);
    			String url = "/front-end/forumtopic/selectByTopic.jsp";
    			RequestDispatcher successView = req.getRequestDispatcher(url);
    			successView.forward(req, res);
    			return;
            }
			ForumPostService forumpostSvc = new ForumPostService();
			List<ForumPostVO> list = forumpostSvc.getByTopicNo(topicNo);
			
			req.setAttribute("list", list);
			String url = "/front-end/forumtopic/selectByTopic.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
	}

}
