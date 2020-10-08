package com.videolog.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.videoLog1.model.VideoLogService;
import com.videoLog1.model.VideoLogVO;

@WebServlet("/VideoLogServlet")
public class VideoLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求，查詢單個影片觀看紀錄的資料
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String str = req.getParameter("videoLogNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入觀看紀錄編號");
				} 
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/videoLog/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				VideoLogService videoLogSvc = new VideoLogService();
				VideoLogVO videoLogVO = videoLogSvc.getOneVideoLog(str);
				if (videoLogVO == null) {
					errorMsgs.add("查無資料");
				}
				
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/videoLog/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				req.setAttribute("videoLogVO", videoLogVO);
				String url = "/videoLog/listOneVideoLog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/videoLog/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) {  //來自listAllVideoLog.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String videoLogNo = req.getParameter("videoLogNo");
				
				VideoLogService videoLogSvc = new VideoLogService();
				VideoLogVO videoLogVO = videoLogSvc.getOneVideoLog(videoLogNo);
				
				req.setAttribute("videoLogVO", videoLogVO);
				String url = "/videoLog/update_videoLog_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}  catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/videoLog/listAllVideoLog.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String videoLogNo = req.getParameter("videoLogNo");
				
				String videoNo = req.getParameter("videoNo");
				String videoNoReg = "[0-9]{1,}";
				if (videoNo == null || videoNo.trim().length() == 0) {
					errorMsgs.add("影片編號: 請勿空白");
				} else if (!videoNo.trim().matches(videoNoReg)) {
					errorMsgs.add("影片編號僅能是數字");
				}
				
				Integer watchTime = Integer.parseInt(req.getParameter("watchTime"));
				String watchTimeReg = "[0-9]{1,}";
				if (!watchTime.toString().trim().matches(watchTimeReg)) {
					errorMsgs.add("觀看時間輸入格式錯誤");
				}
				
				Integer status = Integer.parseInt(req.getParameter("status"));
				String statusReg = "[0-1]{1}";
				if (!status.toString().trim().matches(statusReg)) {
					errorMsgs.add("觀看狀態輸入格式錯誤");
				}
				
				
				VideoLogVO videoLogVO = new VideoLogVO();
				videoLogVO.setVideoNo(videoNo);
				videoLogVO.setWatchTime(watchTime);
				videoLogVO.setStatus(status);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("videoLogVO", videoLogVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/videoLog/update_videoLog_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				VideoLogService videoLogSvc = new VideoLogService();
				videoLogVO = videoLogSvc.updateVideoLog(videoLogNo, videoNoReg, watchTime, status);
				
				req.setAttribute("videoLogVO", videoLogVO);
				String url = "/videoLog/listOneVideoLog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			
			} catch (Exception e) {
				errorMsgs.add("資料修改失敗: " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/videoLog/update_videoLog_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("insert".equals(action)) { //來自addVideoLog.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String videoNo = req.getParameter("videoNo");
				String videoNoReg = "[0-9]{1,}";
				if (videoNo == null || videoNo.trim().length() == 0) {
					errorMsgs.add("影片編號: 請勿空白");
				} else if (!videoNo.trim().matches(videoNoReg)) {
					errorMsgs.add("影片編號僅能是數字");
				}
				
				Integer watchTime = Integer.parseInt(req.getParameter("watchTime"));
				String watchTimeReg = "[0-9]{1,}";
				if (!watchTime.toString().trim().matches(watchTimeReg)) {
					errorMsgs.add("觀看時間輸入格式錯誤");
				}
				
				Integer status = Integer.parseInt(req.getParameter("status"));
				String statusReg = "[0-1]{1}";
				if (!status.toString().trim().matches(statusReg)) {
					errorMsgs.add("觀看狀態輸入格式錯誤");
				}
				
				VideoLogVO videoLogVO = new VideoLogVO();
				videoLogVO.setVideoNo(videoNo);
				videoLogVO.setWatchTime(watchTime);
				videoLogVO.setStatus(status);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("videoLogVO", videoLogVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/videoLog/addVideoLogt.jsp");
					failureView.forward(req, res);
					return;
				}
				
				VideoLogService videoLogSvc = new VideoLogService();
				videoLogVO = videoLogSvc.addVideoLog(videoNoReg, watchTime, status);
				
				String url = "/videoLog/listAllVideoLog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/videoLog/addVideoLog.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String videoLogNo = req.getParameter("videoLogNo");
				
				VideoLogService videoLogSvc = new VideoLogService();
				videoLogSvc.deleteVideoLog(videoLogNo);
				
				String url = "/videoLog/listAllVideoLog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/videoLog/listAllVideoLog.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
