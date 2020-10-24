package com.video.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.video.model.VideoService;
import com.video.model.VideoVO;

@WebServlet("/VideoServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求，查詢單個影片觀看紀錄的資料

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("videoNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入影片編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/video/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				VideoService videoSvc = new VideoService();
				VideoVO videoVO = videoSvc.getOneVideo(str);
				if (videoVO == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/video/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("videoVO", videoVO);
				String url = "/back-end/video/listOneVideo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/video/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllVideo.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String videoNo = req.getParameter("videoNo");

				VideoService videoSvc = new VideoService();
				VideoVO videoVO = videoSvc.getOneVideo(videoNo);

				req.setAttribute("videoVO", videoVO);
				String url = "/back-end/video/update_video_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/video/listAllVideo.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String videoNo = req.getParameter("videoNo");
				String timetableNo = req.getParameter("timetableNo");
				String videoName = req.getParameter("videoName");

				Part update_video = req.getPart("upfile2");
				byte[] videos = null;
				// 如果有新增影片，先驗證格是是否正確 > 重新取得影片、設定檔名
				if (update_video.getSize() != 0) {

					if (!"video/mp4".equals(update_video.getContentType().toLowerCase())) {
						errorMsgs.add("請上傳mp4格式影片");
					}

					// 就算有error，一樣把輸入值保留(所以要先存入再forward)
					if (!errorMsgs.isEmpty()) {
						VideoVO videoVO = new VideoVO();
						videoVO.setTimetableNo(timetableNo);
						videoVO.setVideoName(videoName);
						req.setAttribute("videoVO", videoVO);
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/video/update_video_input.jsp");
						failureView.forward(req, res);
						return;
					}

					Part DBvideo = req.getPart("upfile2");

					// video儲存到資料夾(取路徑 > 建立資料夾 > getName > 存入file)
					String Destination = "/videos";
					String realPath = getServletContext().getRealPath(Destination);
					File fDestination = new File(realPath);
					if (!fDestination.exists())
						fDestination.mkdirs();

					File f = new File(fDestination, videoName + ".mp4");
					update_video.write(f.toString());
				} else { // 如果影片名稱有改，則重取檔案 > 重新命名 > 重新存入

					String Destination = "/videos";
					String realPath = getServletContext().getRealPath(Destination);

					/// 存入file(找檔案 > 設定好output檔案 > 建立水管 > read)
					InputStream fin = new FileInputStream(new File(realPath + "\\" + videoNo + ".mp4"));
					videos = new byte[fin.available()];
					OutputStream fos = new FileOutputStream(new File(realPath + "\\" + videoNo + ".mp4"));
					File fDestination = new File(realPath);
					if (!fDestination.exists())
						fDestination.mkdirs();
					fin.read(videos);
					fos.write(videos);
					fos.close();
					fin.close();

				}

				VideoVO videoVO = new VideoVO();
				videoVO.setVideoNo(videoNo);
				videoVO.setTimetableNo(timetableNo);
				videoVO.setVideoName(videoName);
				videoVO.setVideo(videos);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("videoVO", videoVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/video/update_video_input.jsp");
					failureView.forward(req, res);
					return;
				}
				VideoService videoSvc = new VideoService();
				videoVO = videoSvc.updateVideo(videoNo, timetableNo, videoName, videos);

				req.setAttribute("videoVO", videoVO);
				String url = "/back-end/video/listOneVideo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				e.getStackTrace();
				errorMsgs.add("資料修改失敗: " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/video/update_video_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addVideojsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String timetableNo = req.getParameter("timetableNo");
				Part video = req.getPart("upfile2");
				if (!"video/mp4".equals(video.getContentType().toLowerCase())) {
					errorMsgs.add("請上傳mp4格式影片");
				}

				// 就算有error，一樣把輸入值保留(所以要先存入再forward)
				if (!errorMsgs.isEmpty()) {
					VideoVO videoVO = new VideoVO();
					videoVO.setTimetableNo(timetableNo);
					req.setAttribute("videoVO", videoVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/video/addVideo.jsp");
					failureView.forward(req, res);
					return;
				}

				// 要存直到videoVO了
				VideoVO videoVO = new VideoVO();
				videoVO.setTimetableNo(timetableNo);
				String Destination = "/videos";
				String filePath = getServletContext().getRealPath(Destination);

				VideoService videoSvc = new VideoService();
				videoVO = videoSvc.addVideo(videoVO.getTimetableNo(), " ", filePath, video);

				String url = "/back-end/video/listAllVideo2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/video/addVideo.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action))

		{

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String videoNo = req.getParameter("videoNo");

				VideoService videoSvc = new VideoService();
				videoSvc.deleteVideo(videoNo);

				String url = "/back-end/video/listAllVideo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/video/listAllVideo.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
