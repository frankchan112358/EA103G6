package com.teachingfile.controller;

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
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingFile/listAllTeachingFile.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String teachingFileNo = req.getParameter("teachingFileNo");

				String timetableNo = req.getParameter("timetableNo");
				String timetableReg = "[T]{2}[0-9]{6}";
				if (timetableNo == null || timetableNo.trim().length() == 0) {
					errorMsgs.add("課表編號: 請勿空白");
				} else if (!timetableNo.trim().matches(timetableReg)) {
					errorMsgs.add("課表編號格式錯誤");
				}

				String oriTeachingFileName = req.getParameter("oriTeachingFileName");
				String teachingFileName = req.getParameter("teachingFileName");
				if (teachingFileName == null || teachingFileName.trim().length() == 0) {
					errorMsgs.add("影片名稱: 請勿空白");
				}

				Part update_teachingFile = req.getPart("upfile2");
				byte[] teachingFiles = null;
				// 如果有新增教材，先驗證格是是否正確 > 重新取得影片、設定檔名
				if (update_teachingFile.getSize()!= 0) {

					if (!"application/pdf".equals(update_teachingFile.getContentType().toLowerCase())) {
						errorMsgs.add("請上傳pdf格式教材");
					}

					// 就算有error，一樣把輸入值保留(所以要先存入再forward)
					if (!errorMsgs.isEmpty()) {
						TeachingFileVO teachingFileVO = new TeachingFileVO();
						teachingFileVO.setTeachingFileNo(teachingFileNo);
						teachingFileVO.setTimetableNo(timetableNo);
						teachingFileVO.setTeachingFileName(teachingFileName);
						req.setAttribute("teachingFileVO", teachingFileVO);
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingFile/update_teachingFile_input.jsp");
						failureView.forward(req, res);
						return;
					}

					// 利用inputStream、outputStream把TeachingFile存入DB(給儲存資料byte > 取影片 > 利用資料流讀取資料 )
					Part DBteachingFile = req.getPart("upfile2");
//					byte[] teachingFiles = null;
//					byte[] buffer = new byte[8192];
//					InputStream in = DBteachingFile.getInputStream();
//					ByteArrayOutputStream out = new ByteArrayOutputStream();
//					int i;
//					while ((i = in.read(buffer)) != -1) {
//						out.write(buffer, 0, i);
//						out.flush();
//					}
//					teachingFiles = out.toByteArray();
//					out.close();
//					in.close();

					InputStream in = DBteachingFile.getInputStream();
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					teachingFiles = new byte[in.available()];
					in.read(teachingFiles);
					out.write(teachingFiles);
					out.close();
					in.close();
					
					// teachingFile儲存到資料夾(取路徑 > 建立資料夾 > getName > 存入file)
					String Destination = "/teachingFiles";
					String realPath = getServletContext().getRealPath(Destination);
					File fDestination = new File(realPath);
					if (!fDestination.exists())
						fDestination.mkdirs();

					File f = new File(fDestination, teachingFileName + ".pdf");
					update_teachingFile.write(f.toString());
				} else {   // 如果影片名稱有改，則重取檔案 > 重新命名 > 重新存入

					//從file讀出 > 存入DB
//					byte[] buffer = new byte[8192];
//					String Destination = "/teachingFiles";
//					String realPath = getServletContext().getRealPath(Destination);
//					
////					InputStream in = new FileInputStream(new File(realPath + "\\" + oriTeachingFileName + "."));
//					ByteArrayOutputStream out = new ByteArrayOutputStream();
//					int i;
//					while ((i = in.read(buffer)) != -1) {
//						out.write(buffer, 0, i);
//						out.flush();
//					}
//					teachingFiles = out.toByteArray();
//					out.close();
//					in.close();
					
					String Destination = "/teachingFiles";
					String realPath = getServletContext().getRealPath(Destination);
					
					InputStream in = new FileInputStream(new File(realPath + "\\" + oriTeachingFileName + ".pdf"));
					teachingFiles = new byte[in.available()];
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					in.read(teachingFiles);
					out.write(teachingFiles);
					out.close();
					in.close();
					
					///存入file(找檔案 > 設定好output檔案 > 建立水管 > read)

//					InputStream fin = new FileInputStream(new File(realPath + "\\" + oriTeachingFileName + "."));
//					OutputStream fos = new FileOutputStream(new File (realPath + "\\" + teachingFilesName + "."));
//					File fDestination = new File(realPath);
//					if (!fDestination.exists())
//						fDestination.mkdirs();
//					int c;
//					while ((c = fin.read()) != -1) {
//						fos.write(c);
//					}
//					fos.close();
//					fin.close();
					
					InputStream fin = new FileInputStream(new File(realPath + "\\" + oriTeachingFileName + ".pdf"));
					teachingFiles = new byte[fin.available()];
					OutputStream fos = new FileOutputStream(new File (realPath + "\\" + teachingFileName + ".pdf"));
					File fDestination = new File(realPath);
					if (!fDestination.exists())
						fDestination.mkdirs();
					fin.read(teachingFiles);
					fos.write(teachingFiles);
					fos.close();
					fin.close();
					
				}

				TeachingFileVO teachingFileVO = new TeachingFileVO();
				teachingFileVO.setTimetableNo(timetableNo);
				teachingFileVO.setTeachingFileName(teachingFileName);
				teachingFileVO.setTeachingFile(teachingFiles);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("teachingFileVO", teachingFileVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingFile/update_teachingFile_input.jsp");
					failureView.forward(req, res);
					return;
				}
				TeachingFileService teachingFileSvc = new TeachingFileService();
				teachingFileVO = teachingFileSvc.updateTeachingFile(teachingFileNo, timetableNo, teachingFileName, teachingFiles);

				req.setAttribute("teachingFileVO", teachingFileVO);
				String url = "/back-end/teachingFile/listAllTeachingFile.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				e.getStackTrace();
				errorMsgs.add("資料修改失敗: " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingFile/update_teachingFile_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addTeachingFile.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String timetableNo = req.getParameter("timetableNo");
				if (timetableNo == null || timetableNo.trim().length() == 0) {
					errorMsgs.add("課表編號: 請勿空白");
				} else if (!timetableNo.trim().matches("[T]{2}[0-9]{6}")) {
					errorMsgs.add("課表編號格式錯誤");
				}
				String teachingFileName = req.getParameter("teachingFileName");
				if (teachingFileName == null || teachingFileName.trim().length() == 0) {
					errorMsgs.add("課表名稱: 請勿空白");
				}
				
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
					TeachingFileVO teachingFileVO = new TeachingFileVO();
					teachingFileVO.setTimetableNo(timetableNo);
					teachingFileVO.setTeachingFileName(teachingFileName);
					req.setAttribute("teachingFileVO", teachingFileVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingFile/addTeachingFile.jsp");
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

				// teachingFile儲存到資料夾(取路徑 > 建立資料夾 > getName > 存入file)
				String Destination = "/teachingFiles";
				String realPath = getServletContext().getRealPath(Destination);
				File fDestination = new File(realPath);
				if (!fDestination.exists())
					fDestination.mkdirs();

				File f = new File(fDestination, teachingFileName + ".pdf");
				teachingFile.write(f.toString());

				TeachingFileVO teachingFileVO = new TeachingFileVO();
				teachingFileVO.setTimetableNo(timetableNo);
				teachingFileVO.setTeachingFileName(teachingFileName);
				teachingFileVO.setTeachingFile(teachingFiles);
				TeachingFileService teachingFileSvc = new TeachingFileService();
				teachingFileVO = teachingFileSvc.addTeachingFile(teachingFileVO.getTimetableNo(), teachingFileVO.getTeachingFileName(), teachingFileVO.getTeachingFile());
				String url = "/back-end/teachingFile/listAllTeachingFile.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingFile/addTeachingFile.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action))	{

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String teachingFileNo = req.getParameter("teachingFileNo");

				TeachingFileService teachingFileSvc = new TeachingFileService();
				teachingFileSvc.deleteTeachingFile(teachingFileNo);
				
				String url = "/back-end/teachingFile/listAllTeachingFile.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teachingFile/listAllTeachingFile.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
