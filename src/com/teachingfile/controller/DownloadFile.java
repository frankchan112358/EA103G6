package com.teachingfile.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teachingfile.model.TeachingFileService;
import com.teachingfile.model.TeachingFileVO;

@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				//傳檔案名稱進來
//				String courseNo = req.getParameter("courseNo");
				String queryString = req.getQueryString();
				System.out.println(queryString);

				TeachingFileService teachingFileSvc = new TeachingFileService();
				TeachingFileVO teachingFileVO= teachingFileSvc.getOneTeachingFile(queryString);
				
				System.out.println("40行"); 
//				//建立資料夾
//				String path = req.getSession().getServletContext().getRealPath(queryString + ".pdf");
				File file = new File(queryString);
				if (!file.exists())
					file.mkdirs();
				
				System.out.println("47行");
				//用VO.File取得檔案(byte[])接outputStream
				ByteArrayInputStream bis = new ByteArrayInputStream(teachingFileVO.getTeachingFile());
				ServletOutputStream sos = res.getOutputStream();
				byte[] loadFile = new byte[bis.available()];
				bis.read(loadFile);
				sos.write(loadFile);
				sos.close();
				
				System.out.println("56行");


			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("下載資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/teachingFile/listAllTeachingFile2.jsp");
				failureView.forward(req, res);
			
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("preRead".equals(action)) { // 來自addTeachingFile.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String courseNo = req.getParameter("courseNo");
				String timetableNo = req.getParameter("timetableNo");
				String teachingFileName = req.getParameter("teachingFileName");
				String teachingFileNo = req.getParameter("teachingFileNo");
				
				TeachingFileService teachingFileSvc = new TeachingFileService();
				TeachingFileVO teachingFileVO = teachingFileSvc.getOneTeachingFile(teachingFileNo);

				ByteArrayInputStream bis = new ByteArrayInputStream(teachingFileVO.getTeachingFile());
				ServletOutputStream sos = res.getOutputStream();
				byte[] file = new byte[bis.available()];
				bis.read(file);
				sos.write(file);
				sos.close();
				
				String url = "/back-end/teachingFile/listAllTeachingFile2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingFile/addTeachingFile.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
