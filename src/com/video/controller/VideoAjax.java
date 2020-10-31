package com.video.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.leave.model.LeaveService;
import com.video.model.VideoService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class VideoAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("insert".equals(action)) {
			res.setContentType("text/html");
			try {
				String timetableNo = req.getParameter("timetableNo");
				Part video = req.getPart("video");
				if (!"video/mp4".equals(video.getContentType().toLowerCase())) {
					res.getWriter().append("請上傳mp4格式影片");
					return;
				}
				String Destination = "/videos";
				String filePath = getServletContext().getRealPath(Destination);
				new VideoService().addVideo(timetableNo, " ", filePath, video);
				res.getWriter().append("ok");
				return;
			} catch (Exception e) {
				e.printStackTrace();
				res.getWriter().append(e.getMessage());
			}
		}
		if ("update".equals(action)) {
			res.setContentType("text/html");
			try {
				String videoNo = req.getParameter("videoNo");
				Part video = req.getPart("video");
				if (!"video/mp4".equals(video.getContentType().toLowerCase())) {
					res.getWriter().append("請上傳mp4格式影片");
					return;
				}
				String Destination = "/videos";
				String filePath = getServletContext().getRealPath(Destination);
				File fDestination = new File(filePath);
				if (!fDestination.exists())
					fDestination.mkdirs();
				File f = new File(fDestination, videoNo + ".mp4");
				video.write(f.toString());
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
				String videoNo = req.getParameter("videoNo");
				new VideoService().deleteVideo(videoNo);
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
			out.print(new VideoService().getDatatableJson(courseNo));
			return;
		}
	}

}
