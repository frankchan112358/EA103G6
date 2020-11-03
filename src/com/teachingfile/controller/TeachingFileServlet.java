package com.teachingfile.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.course.model.CourseVO;
import com.teachingfile.model.TeachingFileService;
import com.teachingfile.model.TeachingFileVO;

@WebServlet("/TeachingFileServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class TeachingFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Update".equals(action)) { // 來自listAllTeachingFile.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String teachingFileNo = req.getParameter("teachingFileNo");

				TeachingFileService teachingFileSvc = new TeachingFileService();
				TeachingFileVO teachingFileVO = teachingFileSvc.getOneTeachingFile(teachingFileNo);

				req.setAttribute("teachingFileVO", teachingFileVO);
				String url = "/back-end/teachingFile/update_teachingFile_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/teachingFile/listAllTeachingFile.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String teachingFileNo = req.getParameter("teachingFileNo");

				String courseNo = req.getParameter("courseNo");
				String courseReg = "[C]{1}[0-9]{3}";
				if (courseNo == null || courseNo.trim().length() == 0) {
					errorMsgs.add("課表編號: 請勿空白");
				} else if (!courseNo.trim().matches(courseReg)) {
					errorMsgs.add("課表編號格式錯誤");
				}

				String teachingFileName = req.getParameter("teachingFileName");
				if (teachingFileName == null || teachingFileName.trim().length() == 0) {
					errorMsgs.add("影片名稱: 請勿空白");
				}

				Part update_teachingFile = req.getPart("upfile2");
				byte[] teachingFiles = null;
				// 如果有新增教材，先驗證格是是否正確 > 重新取得檔案、設定檔名
				if (update_teachingFile.getSize() != 0) {
					System.out.println("upload file");
					if (!"application/pdf".equals(update_teachingFile.getContentType().toLowerCase())) {
						errorMsgs.add("請上傳pdf格式教材");
					}
					System.out.println(errorMsgs);
					// 就算有error，一樣把輸入值保留(所以要先存入再forward)
					if (!errorMsgs.isEmpty()) {
						System.out.println("here is the errorMsgs in errorMsgs1");
						TeachingFileVO teachingFileVO = new TeachingFileVO();
						teachingFileVO.setTeachingFileNo(teachingFileNo);
						teachingFileVO.setCourseNo(courseNo);
						teachingFileVO.setTeachingFileName(teachingFileName);
						req.setAttribute("teachingFileVO", teachingFileVO);
						System.out.println("here is the errorMsgs in errorMsgs2");
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/teachingFile/update_teachingFile_input.jsp");
						failureView.forward(req, res);
						return;
					}

					// 利用inputStream、outputStream把TeachingFile存入DB(給儲存資料byte > 取影片 > 利用資料流讀取資料 )
					Part DBteachingFile = req.getPart("upfile2");

					InputStream in = DBteachingFile.getInputStream();
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					teachingFiles = new byte[in.available()];
					in.read(teachingFiles);
					out.write(teachingFiles);
					out.close();
					in.close();

					TeachingFileVO teachingFileVO = new TeachingFileVO();
					teachingFileVO.setTeachingFileNo(teachingFileNo);
					teachingFileVO.setCourseNo(courseNo);
					teachingFileVO.setTeachingFileName(teachingFileName);
					teachingFileVO.setTeachingFile(teachingFiles);

					if (!errorMsgs.isEmpty()) {
						req.setAttribute("teachingFileVO", teachingFileVO);
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/teachingFile/update_teachingFile_input.jsp");
						failureView.forward(req, res);
						return;
					}

					TeachingFileService teachingFileSvc = new TeachingFileService();
					teachingFileVO = teachingFileSvc.updateTeachingFile(teachingFileNo, courseNo, teachingFileName,
							teachingFiles);
					req.setAttribute("teachingFileVO", teachingFileVO);
					String url = "/back-end/teachingFile/listAllTeachingFile.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);

				} else { // 如果影片名稱有改，則重取檔案 > 重新命名 > 重新存入
					TeachingFileVO teachingFileVO = new TeachingFileVO();
					teachingFileVO.setTeachingFileNo(teachingFileNo);
					teachingFileVO.setCourseNo(courseNo);
					teachingFileVO.setTeachingFileName(teachingFileName);

					if (!errorMsgs.isEmpty()) {
						req.setAttribute("teachingFileVO", teachingFileVO);
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/teachingFile/update_teachingFile_input.jsp");
						failureView.forward(req, res);
						return;
					}
					TeachingFileService teachingFileSvc = new TeachingFileService();
					teachingFileVO = teachingFileSvc.updateTeachingFileNOFILE(teachingFileNo, courseNo,
							teachingFileName);

					req.setAttribute("teachingFileVO", teachingFileVO);
					String url = "/back-end/teachingFile/listAllTeachingFile.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);

				}

			} catch (Exception e) {
				e.getStackTrace();
				errorMsgs.add("資料修改失敗: " + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/teachingFile/update_teachingFile_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addTeachingFile.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String courseNo = req.getParameter("courseNo");
				String teachingFileName = req.getParameter("teachingFileName");

				byte[] teachingFiles = null;
				Part teachingFile = req.getPart("upfile2"); // Servlet3.0新增了Part介面，讓我們方便的進行檔案上傳處理
				if (!"application/pdf".equals(teachingFile.getContentType().toLowerCase())) {
					errorMsgs.add("請上傳pdf格式教材");
				}
//				if (!"application/pdf".equals(teachingFile.getContentType().toLowerCase())
//						|| !"image/jpeg".equals(teachingFile.getContentType().toLowerCase())) {
//					errorMsgs.add("請上傳pdf或gif格式教材");
//				}

				// 就算有error，一樣把輸入值保留(所以要先存入再forward)
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("courseNo", courseNo);
					TeachingFileVO teachingFileVO = new TeachingFileVO();
					teachingFileVO.setCourseNo(courseNo);
					teachingFileVO.setTeachingFileName(teachingFileName);
					req.setAttribute("teachingFileVO", teachingFileVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/teachingFile/addTeachingFile.jsp");
					failureView.forward(req, res);
					return;
				}

				// 利用inputStream、outputStream把teachingFile存入DB(給儲存資料byte > 取影片 > 利用資料流讀取資料 )
				Part DBteachingFile = req.getPart("upfile2");

				InputStream in = DBteachingFile.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				teachingFiles = new byte[in.available()];
				in.read(teachingFiles);
				out.write(teachingFiles);
				out.close();
				in.close();
				
				CourseVO courseVO = new CourseVO();
				courseVO.setCourseNo(courseNo);
				req.setAttribute("courseVO", courseVO);

				TeachingFileVO teachingFileVO = new TeachingFileVO();
				teachingFileVO.setCourseNo(courseNo);
				teachingFileVO.setTeachingFileName(teachingFileName);
				teachingFileVO.setTeachingFile(teachingFiles);
				
				TeachingFileService teachingFileSvc = new TeachingFileService();
				teachingFileVO = teachingFileSvc.addTeachingFile(teachingFileVO.getCourseNo(),
						teachingFileVO.getTeachingFileName(), teachingFileVO.getTeachingFile());
				String url = "/back-end/teachingFile/listAllTeachingFile2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingFile/addTeachingFile.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String teachingFileNo = req.getParameter("teachingFileNo");
				String courseNo = req.getParameter("courseNo");
				
				TeachingFileService teachingFileSvc = new TeachingFileService();
				teachingFileSvc.deleteTeachingFile(teachingFileNo);
				
				CourseVO courseVO = new CourseVO();
				courseVO.setCourseNo(courseNo);
				req.setAttribute("courseVO", courseVO);

				String url = "/back-end/teachingFile/listAllTeachingFile2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/teachingFile/listAllTeachingFile2.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
