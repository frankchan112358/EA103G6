package com.teachingfile.controller;

import java.io.ByteArrayInputStream;
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
import javax.servlet.http.HttpSession;

import com.teachingfile.model.TeachingFileService;
import com.teachingfile.model.TeachingFileVO;

@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");

		// 傳檔案名稱進來
		HttpSession session = req.getSession();

		String queryString = req.getQueryString();

		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);

		try {

			TeachingFileService teachingFileSvc = new TeachingFileService();
			TeachingFileVO teachingFileVO = teachingFileSvc.getOneTeachingFile(queryString);

			// 用VO.File取得檔案(byte[])接outputStream
			ByteArrayInputStream bis = new ByteArrayInputStream(teachingFileVO.getTeachingFile());
			String str = java.net.URLEncoder.encode(teachingFileVO.getTeachingFileName(), "UTF-8");
			str = str.replace("+", "%20");
			res.setHeader("content-disposition", "attachment;filename=" + str+".pdf");
			ServletOutputStream sos = res.getOutputStream();
			byte[] loadFile = new byte[bis.available()];
			bis.read(loadFile);
			sos.write(loadFile);
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
			errorMsgs.add("下載資料失敗:" + e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingFile/listAllTeachingFile3.jsp");
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
				String teachingFileNo = req.getParameter("teachingFileNo");

				TeachingFileService teachingFileSvc = new TeachingFileService();
				TeachingFileVO teachingFileVO = teachingFileSvc.getOneTeachingFile(teachingFileNo);

				ByteArrayInputStream bis = new ByteArrayInputStream(teachingFileVO.getTeachingFile());
				ServletOutputStream sos = res.getOutputStream();
				byte[] file = new byte[bis.available()];
				bis.read(file);
				sos.write(file);
				sos.close();
				bis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}