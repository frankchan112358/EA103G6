package com.teachingfile.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.course.model.CourseVO;
import com.teachingfile.model.TeachingFileService;
import com.teachingfile.model.TeachingFileVO;

@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		System.out.println("downloadFile : line 30");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			System.out.println("downloadFile : line 35");
			try {
				
				System.out.println("downloadFile : line 38");
				//傳檔案名稱進來
//				String courseNo = req.getParameter("courseNo");
				String queryString = req.getQueryString();
				System.out.println("queryString :" + queryString);
				
				System.out.println("downloadFile : line 44");
				TeachingFileService teachingFileSvc = new TeachingFileService();
				 TeachingFileVO teachingFileVO= teachingFileSvc.getOneTeachingFile(queryString);
				
				 System.out.println("downloadFile : line 48");
				//建立資料夾
				String path = req.getSession().getServletContext().getRealPath(queryString + ".pdf");
				File file = new File(path);
				String filename = file.getName();
				if (!file.exists())
					file.mkdirs();
				
				System.out.println("downloadFile : line 56");				
				//用VO.File取得檔案(byte[])接outputStream
				System.out.println(teachingFileVO.getTeachingFile());
				System.out.println(teachingFileVO.getTeachingFileNo());
				InputStream is = new BufferedInputStream(new ByteArrayInputStream(teachingFileVO.getTeachingFile()));
				FileOutputStream fos = new FileOutputStream(queryString + ".pdf");
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				fos.write(buffer);
				fos.close();
				is.close();
				
				System.out.println("downloadFile : line 66");
				//回到原本畫面(嗎? 
				String url = "/back-end/teachingFile/listAllTeachingFile2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
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
