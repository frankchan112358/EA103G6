package com.teachingfile.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.teachingfile.model.TeachingFileService;
import com.teachingfile.model.TeachingFileVO;
import com.video.model.VideoService;

@WebServlet("/TeachingFileAjax")
public class TeachingFileAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

		if ("insert".equals(action)) {
			res.setContentType("text/html");
			try {
				// 接參數
				String courseNo = (String) session.getAttribute("courseNo");
				String teachingFileName = req.getParameter("teachingFileName");
				// 接檔案 >判斷有檔案 > 判斷格式(對>上傳 ; 不對 > print errorMsgs) > 上傳
				byte[] teachingFiles = null;
				System.out.println("TFAjax44");
				Part teachingFile = req.getPart("upfile2");
				System.out.println("TFAjax46");
				
				if (!"application/pdf".equals(teachingFile.getContentType().toLowerCase())) {
					res.getWriter().append("請上傳pdf格式教材");
				}
				InputStream in = teachingFile.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				teachingFiles = new byte[in.available()];
				in.read(teachingFiles);
				out.write(teachingFiles);
				out.close();
				in.close();
				new TeachingFileService().addTeachingFile(courseNo, teachingFileName, teachingFiles);
				res.getWriter().append("ok");
				return;
			} catch (Exception e) {
				e.printStackTrace();
				res.getWriter().append(e.getMessage());
			}
		}

		if ("delete".equals(action)) {
			res.setContentType("text/html");
			try {
				String teachingFileNo = req.getParameter("teachingFileNo");
				new TeachingFileService().deleteTeachingFile(teachingFileNo);
				res.getWriter().append("ok");
				return;
			} catch (Exception e) {
				e.printStackTrace();
				res.getWriter().append(e.getMessage());
			}
		}
		if ("datatable".equals(action)) {
			res.setContentType("application/json;");
			String courseNo = req.getParameter("courseNo");
			PrintWriter out = res.getWriter();
			out.print(new TeachingFileService().getDatatableJson(courseNo));
			return;
		}
	}
}
