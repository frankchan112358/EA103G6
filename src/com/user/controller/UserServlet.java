package com.user.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.google.gson.Gson;
import com.mail.service.MailService;
import com.permission.model.PermissionService;
import com.permission.model.PermissionVO;
import com.student.model.StudentService;
import com.student.model.StudentVO;
import com.teacher.model.TeacherService;
import com.teacher.model.TeacherVO;
import com.user.image.ImageUtil;
import com.user.model.UserRedisDAO;
import com.user.model.UserRedisMisVO;
import com.user.model.UserRedisVO;
import com.user.model.UserService;
import com.user.model.UserVO;
import com.userpermission.model.UserPermissionService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class UserServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getPhoto".equals(action)) {

			String userNo = req.getParameter("userNo");

			UserService userSvc = new UserService();
			

			ServletOutputStream out = res.getOutputStream();
			res.setContentType("image/gif");
			
			/***************此為原本直接輸出code********************/
			byte[] buf = new byte[4 * 1024];
			int len = 0;

			InputStream in = userSvc.getPic(userNo);
			if (in == null) {
				in = getServletContext().getResourceAsStream("/images/noPicture.png");
			}
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
			in.close();		
			/***************上述為原本直接輸出code********************/
			
//			BufferedInputStream bis = new BufferedInputStream(in);
//			byte[] buf = new byte[4 * 1024];
//			int len = 0;
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	        while((len = bis.read(buf))!=-1){
//	            bos.write(buf, 0, len);
//	        }
//	        bis.close();
//	        bos.close();
//	        
//	        byte[] picByte=bos.toByteArray();
//	        
//	        picByte=ImageUtil.shrink(picByte, 100);
//	        out.write(picByte);
//	        out.flush();
//	        out.close();
	        
		}

		if ("getOne_For_Display".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("userNo");

				// 限制任何人更改最高管理者
				if (str.equals("U000001")) {
					req.setAttribute("permission", "forbid"); //丟到首頁顯示無權限
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/index/index.jsp");
					successView.forward(req, res);
					return;
				}

				//設計沒有可以讓其手動查詢的位置，此用來擋網址的不正當輸入
				String userNo = null;
				try {
					userNo = str;
				} catch (Exception e) {
					errorMsgs.put("userNoCheck", "使用者編號格式不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				UserService userSvc = new UserService();
				UserVO userVO = userSvc.getOneUser(userNo);

				
				//設計沒有可以讓其手動查詢的位置，此用來擋網址的不正當輸入
				if (userVO == null) {
					errorMsgs.put("userNoCheck", "查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("userVOForShow", userVO);

				if (userVO.getType().equals(0)) {
					StudentService studentSvc = new StudentService();
					StudentVO studentVO = studentSvc.findByPrimaryKeyByuserNo(userNo);
					req.setAttribute("studentVOForShow", studentVO);

					RequestDispatcher successView = req.getRequestDispatcher("/back-end/student/listOneStudent.jsp");
					successView.forward(req, res);

				} else if (userVO.getType().equals(1)) {
					TeacherService teacherSvc = new TeacherService();
					TeacherVO teacherVO = teacherSvc.getOneTeacherByUserNo(userNo);
					req.setAttribute("teacherVOForShow", teacherVO);

					RequestDispatcher successView = req.getRequestDispatcher("/back-end/teacher/listOneTeacher.jsp");
					successView.forward(req, res);

				} else {
					EmpService empSvc = new EmpService();
					EmpVO empVO = empSvc.getOneEmpByUserNo(userNo);
					req.setAttribute("empVOForShow", empVO);

					RequestDispatcher successView = req.getRequestDispatcher("/back-end/emp/listOneEmp.jsp");
					successView.forward(req, res);
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.put("mistake", "無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer type = java.lang.Integer.valueOf(req.getParameter("type"));

				String name = req.getParameter("name");
				if (name != null)
					name.trim();
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (name == null || name.length() == 0) {
					errorMsgs.put("name", "姓名請勿空白");
				} else if (!name.trim().matches(nameReg)) {
					errorMsgs.put("name", "只能是中、英文字母 , 且長度必需在2到10之間");
				}

				String mail = req.getParameter("mail");
				if (mail != null)
					mail.trim();
				String mailReg = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
				if (mail == null || mail.length() == 0) {
					errorMsgs.put("mail", "信箱請勿空白");
				} else if (!mail.matches(mailReg)) {
					errorMsgs.put("mail", "格式錯誤，請再次檢查");
				} else {
					// 進資料庫對比信箱(帳號)是否重複註冊
					UserService userSVAccount = new UserService();
					List<String> accountVS = userSVAccount.checkAccount(type);

					for (String checkAccount : accountVS) {
						if (mail.toLowerCase().equals(checkAccount)) {
							errorMsgs.put("mail", "信箱已重複註冊請更換");
							break;
						}
					}
				}

				String id = "";
				try {
					id = req.getParameter("id");
					if (id != null)
						id.trim();

					char firstChar = 0;
					int lastNum;
					int firstNum = 0;
					int total = 0;

					char[] firstEng = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
							'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
					int[] multiply = { 0, 8, 7, 6, 5, 4, 3, 2, 1 }; // 扣除英文數字對應的數字權數補上0後的權數
					int[] checkNum = { 10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 25, 26, 27,
							28, 29, 32, 30, 31, 33 };

					String reg = "^[A-Z][12]\\d{8}$";

					if (!id.matches(reg)) {
						errorMsgs.put("id", "請仔細填寫身分證號碼，第一碼請大寫");
					} else {
						firstChar = Character.toUpperCase(id.charAt(0));
						lastNum = Integer.valueOf(Character.toString(id.charAt(9)));

						// 找出字母對應的數字，並計算其值
						for (int i = 0; i < firstEng.length; i++) {
							if (firstChar == firstEng[i]) {
								firstNum = checkNum[i];
								firstNum = (int) (Math.floor(firstNum / 10)) + (firstNum % 10 * 9);
							}
						}

						// 計算1-8的數值
						for (int j = 1; j <= 8; j++) {
							total = total + Integer.valueOf(Character.toString(id.charAt(j))) * multiply[j];
						}

						// 求出檢查碼
						total = total + firstNum;
						total %= 10;

						if (total == 0) {
							if (total != Integer.valueOf(Character.toString(id.charAt(9)))) {
								errorMsgs.put("id", "身分證號碼輸入錯誤");

							}
						} else {
							if ((10 - total) != Integer.valueOf(Character.toString(id.charAt(9)))) {
								errorMsgs.put("id", "身分證號碼輸入錯誤");

							}
						}
					}
				} catch (StringIndexOutOfBoundsException se) {
					errorMsgs.put("id", "請仔細填寫身分證號碼");
				}

				UserService userSvcId = new UserService();
				List<String> idVS = userSvcId.checkId("null"); // 請與update方法檢查身分證做比較

				for (String checkId : idVS) {
					if (id.equals(checkId)) {
						errorMsgs.put("id", "身分證字號已重複註冊請更換");
						break;
					}
				}

				UserVO userVO = new UserVO();
				userVO.setType(type);
				userVO.setName(name);
				userVO.setMail(mail);
				userVO.setId(id);

				/************************ 發生錯誤轉交錯誤 **********************/
				if (!errorMsgs.isEmpty()) {
					// 錯誤導前端
					if (type.equals(0)) {
						req.setAttribute("userVOForInsert", userVO);
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/student/studentList.jsp");
						failureView.forward(req, res);
						return;


					// 錯誤導講師頁面
					} else if (type.equals(1)) {

						req.setAttribute("userVOForInsert", userVO);
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teacher/teacherList.jsp");
						failureView.forward(req, res);
						return;
					// 錯誤導導師頁面
					} else {
						req.setAttribute("userVOForInsert", userVO);
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/empList.jsp");
						failureView.forward(req, res);
						return;
					}
				}

				/************************ 2.確認無誤後開始新增使用者Table資料 **********************/

				UserService userSvc = new UserService();
				userVO = userSvc.addUser(type, mail, name, mail, id);
				/*************************** 3.開始新增其身分table *******************************/

				// 透過身分證抓取剛新增的使用者編號
				String userNo = userSvc.getOneUserById(id).getUserNo();

				if (type.equals(0)) {
					// req.getParameter("banjiNo").trim();

					StudentService studentSvc = new StudentService();
					String banjiNo = req.getParameter("banjiNo");
					studentSvc.addStudent(userNo, banjiNo, name);

				} else if (type.equals(1)) {
					// 新增講師table

					TeacherService teacherSvc = new TeacherService();
					teacherSvc.addTeacher(userNo, name);

				} else {
					// 新增導師table

					EmpService empSvc = new EmpService();
					empSvc.addEmp(userNo, name);

					// 新增導師權限table(預設全部為0)
					PermissionService PermissionSvc = new PermissionService();
					UserPermissionService userPermissionService = new UserPermissionService();
					List<PermissionVO> list = PermissionSvc.getAll();
					for (PermissionVO permissionVO : list) {
						Integer readable = 0;
						Integer editable = 0;

						userPermissionService.addUserPermission(userNo, permissionVO.getPermissionNo(), readable,
								editable);
					}
				}

				/********************** 4.新增完成,準備寄email ******************/

				/***************** 隨機產生亂數開始 **************/

				int arrayA[] = new int[62];

				// 數字、英文最後用型別轉換成字元
				for (int i = 0; i < arrayA.length; i++) {
					if (i < 10)
						arrayA[i] = 48 + i; // 數字10個(0-9)

					else if (i < 36)
						arrayA[i] = 55 + i; // 大寫英文字26個參照 ASCII

					else
						arrayA[i] = 61 + i; // 小寫英文字26個參照 ASCII
				}

				int arrayB[] = new int[8];
				Random r = new Random();
				String ranSen = "";
				for (int i = 0; i < arrayB.length; i++) {
					arrayB[i] = arrayA[r.nextInt(62)];
					char ranChar = (char) arrayB[i];
					String charToString = String.valueOf(ranChar);
					ranSen = ranSen + charToString;
				}

				/***************** 隨機產生亂數結束 **************/

				String encoding = "guest" + userNo.substring(1);
				String messageText = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
						+ req.getContextPath() + "/user.do?action=enable&guest=" + encoding + "&userNo=" + ranSen;

				String picLink=req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
				+ req.getContextPath() +"/login/login.jsp";
				
				MailService sendMail = new MailService();
//				sendMail.sendMail(mail, "Work Join Learn 會員啟用", messageText);
				sendMail.sendHTMLMail(mail, "Work Join Learn 會員啟用", messageText,picLink);

				/*************************** 5.新增完成,準備轉交畫面 ***********/
				// 轉交前端
				if (type.equals(0)) {

					RequestDispatcher successView = req.getRequestDispatcher("/back-end/student/studentList.jsp");
					successView.forward(req, res);
					// 轉交講師頁面
				} else if (type.equals(1)) {
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/teacher/teacherList.jsp");
					successView.forward(req, res);
					// 轉交導師頁面
				} else {
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/emp/empList.jsp");
					successView.forward(req, res);
				}

			} catch (Exception e) {
				errorMsgs.put("mistake", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.接收請求參數 ****************************************/
				String userNo = req.getParameter("userNo");

				/*************************** 2.開始查詢資料 ****************************************/

				UserService userSvc = new UserService();
				UserVO userVO = userSvc.getOneUser(userNo);
				req.setAttribute("userVOForUpdate", userVO);

				/*************************** 2.查詢資料,並準備轉交(Send the Success view) ************/

				if (userVO.getType().equals(0)) {
					StudentService studentSvc = new StudentService();
					StudentVO studentVO = studentSvc.findByPrimaryKeyByuserNo(userNo);
//					if (studentVO == null) {// 若離職頁面轉至此跳刪除建議，但目前新版面離職的人會抓不到
//						RequestDispatcher failureView = req
//								.getRequestDispatcher("/back-end/user/update_user_input.jsp");
//						failureView.forward(req, res);
//						return;
//					}

					req.setAttribute("studentVOForUpdate", studentVO);
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/student/updateStudent.jsp");
					successView.forward(req, res);

				} else if (userVO.getType().equals(1)) {

					TeacherService teacherSvc = new TeacherService();
					TeacherVO teacherVO = teacherSvc.getOneTeacherByUserNo(userNo);

					req.setAttribute("teacherVOForUpdate", teacherVO);
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/teacher/updateTeacher.jsp");
					successView.forward(req, res);
				} else {

					EmpService empSvc = new EmpService();
					EmpVO empVO = empSvc.getOneEmpByUserNo(userNo);

					req.setAttribute("empVOForUpdate", empVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/updateEmp.jsp");
					failureView.forward(req, res);
				}

			} catch (Exception e) {
				errorMsgs.put("mistake", "無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
			}

		}	

		if ("delete".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String userNo = req.getParameter("userNo");

				
				/******************* 2.開始刪除資料，並轉交*********************/
				UserService UserSvc = new UserService();
				UserVO userVO = UserSvc.getOneUser(userNo);
				UserSvc.deleteUser(userNo);

				if (userVO.getType().equals(0)) {

					StudentService studentSvc = new StudentService();
					String studentNo = studentSvc.findByPrimaryKeyByuserNo(userNo).getStudentNo();
					studentSvc.deleteStudent(studentNo);

					RequestDispatcher successView = req.getRequestDispatcher("/back-end/student/studentList.jsp");
					successView.forward(req, res);
				} else if (userVO.getType().equals(1)) {

					TeacherService teacherSvc = new TeacherService();
					String teacherNo = teacherSvc.getOneTeacherByUserNo(userNo).getTeacherNo();
					teacherSvc.deleteTeacher(teacherNo);

					RequestDispatcher successView = req.getRequestDispatcher("/back-end/teacher/teacherList.jsp");
					successView.forward(req, res);
				} else {
					EmpService empSvc = new EmpService();
					String empNo = empSvc.getOneEmpByUserNo(userNo).getEmpNo();
					empSvc.deleteEmp(empNo);

					RequestDispatcher successView = req.getRequestDispatcher("/back-end/emp/empList.jsp");
					successView.forward(req, res);
				}

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/

			} catch (Exception e) {
				errorMsgs.put("mistake", "刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
			}
		}

		// 此為專們設計給管理者的修改
		if ("update".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String userNo = req.getParameter("userNo");

				Integer type = null;

				try {
					type = java.lang.Integer.valueOf(req.getParameter("type"));
				} catch (NumberFormatException se) {
					errorMsgs.put("type", "請填寫使用者身分欄位");
				}

				String name = req.getParameter("name");
				if (name != null)
					name.trim();
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (name == null || name.length() == 0) {
					errorMsgs.put("name", "姓名請勿空白");
				} else if (!name.matches(nameReg)) {
					errorMsgs.put("name", "姓名只能是中、英文字母 , 且長度必需在2到10之間");
				}

				String mail = req.getParameter("mail");
				if (mail != null)
					mail.trim();
				String mailReg = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
				if (mail == null || mail.length() == 0) {
					errorMsgs.put("mail", "信箱請勿空白");
				} else if (!mail.matches(mailReg)) {
					errorMsgs.put("mail", "信箱格式錯誤，請再次檢查");
				}

				String phone = req.getParameter("phone");
				if (phone != null)
					phone.trim();
				String phoneReg = "[0-9]{10}";
				if (phone == null || phone.length() == 0) {
				} else if (!phone.matches(phoneReg)) {
					errorMsgs.put("phone", "手機號碼僅接受台灣連絡電話且僅能輸入10碼");
				}

				String address = req.getParameter("address");
				if (address != null)
					address.trim();

				String id = "";
				try {
					id = req.getParameter("id");
					if (id != null)
						id.trim();
					char firstChar = 0;
					int lastNum;
					int firstNum = 0;
					int total = 0;

					char[] firstEng = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
							'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
					int[] multiply = { 0, 8, 7, 6, 5, 4, 3, 2, 1 }; // 扣除英文數字對應的數字權數補上0後的權數
					int[] checkNum = { 10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 25, 26, 27,
							28, 29, 32, 30, 31, 33 };

					String reg = "^[A-Z][12]\\d{8}$";

					if (!id.matches(reg)) {
						errorMsgs.put("id", "請仔細填寫身分證號碼，第一碼請大寫");
					} else {
						firstChar = Character.toUpperCase(id.charAt(0));
						lastNum = Integer.valueOf(Character.toString(id.charAt(9)));

						// 找出字母對應的數字，並計算其值
						for (int i = 0; i < firstEng.length; i++) {
							if (firstChar == firstEng[i]) {
								firstNum = checkNum[i];
								firstNum = (int) (Math.floor(firstNum / 10)) + (firstNum % 10 * 9);
							}
						}

						// 計算1-8的數值
						for (int j = 1; j <= 8; j++) {
							total = total + Integer.valueOf(Character.toString(id.charAt(j))) * multiply[j];
						}

						// 求出檢查碼
						total = total + firstNum;
						total %= 10;

						if (total == 0) {
							if (total != Integer.valueOf(Character.toString(id.charAt(9)))) {
								errorMsgs.put("id", "身分證號碼輸入錯誤");
							}
						} else {
							if ((10 - total) != Integer.valueOf(Character.toString(id.charAt(9)))) {
								errorMsgs.put("id", "身分證號碼輸入錯誤");

							}
						}
					}
				} catch (StringIndexOutOfBoundsException se) {
					errorMsgs.put("id", "請仔細填寫身分證號碼");
				}

				UserService userSvcId = new UserService();
				List<String> idVS = userSvcId.checkId(userNo);

				for (String checkId : idVS) {
					if (id.equals(checkId)) {
						errorMsgs.put("id", "身分證字號：已重複註冊請更換");
						break;
					}
				}

				Integer enable = null;

				try {
					enable = java.lang.Integer.valueOf(req.getParameter("enable"));
				} catch (NumberFormatException se) {
					errorMsgs.put("enable", "請填寫啟用狀態");
				}

				Part part = req.getPart("photo");
				InputStream photo = null;
				String form = part.getContentType().toLowerCase();

				UserService userSercice1 = new UserService();
				photo = userSercice1.getPic(userNo);

				if (form.contains("image") && errorMsgs.isEmpty()) {
					photo = part.getInputStream();
				}

				/*********************** 2.其他table接收請求參數 *************************/

				UserVO userVO = new UserVO();
				userVO.setUserNo(userNo);
				userVO.setType(type);
				userVO.setName(name);
				userVO.setMail(mail);
				userVO.setPhone(phone);
				userVO.setAddress(address);
				userVO.setId(id);
				userVO.setPhoto(photo);
				userVO.setEnable(enable);

				/*********************** 取得學生資料 *************************/
				String studentNo = req.getParameter("studentNo");
				String banjiNo = req.getParameter("banjiNo");

				Integer studentStatus = null;
				if (req.getParameter("studentStatus") != null) {
					studentStatus = java.lang.Integer.valueOf(req.getParameter("studentStatus"));

				}

				/*********************** 取得講師資料 *************************/

				String teacherNo = req.getParameter("teacherNo");

				String skill = req.getParameter("skill");
				if (skill != null)
					skill.trim();

				String description = req.getParameter("description");
				if (description != null)
					description.trim();

				Integer teacherStatus = null;
				if (req.getParameter("teacherStatus") != null) {
					teacherStatus = java.lang.Integer.valueOf(req.getParameter("teacherStatus"));

				}

				/*********************** 取得導師資料 *************************/
				String empNo = req.getParameter("empNo");

				Integer empStatus = null;
				if (req.getParameter("empStatus") != null) {
					empStatus = java.lang.Integer.valueOf(req.getParameter("empStatus"));
				}

				/*********************** 3.資料包裝並開始進行錯誤處理 *************************/

				if (type.equals(0)) {
					StudentVO studentVO = new StudentVO();
					studentVO.setStudentNo(studentNo);
					studentVO.setBanjiNo(banjiNo);
					studentVO.setStudentDescription(description);
					studentVO.setStudentStatus(studentStatus);

					req.setAttribute("studentVOForUpdate", studentVO);

					if (!errorMsgs.isEmpty()) {
						req.setAttribute("userVOForUpdate", userVO);
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/student/update_student_input.jsp");
						failureView.forward(req, res);
						return;
					}

				} else if (type.equals(1)) {

					TeacherVO teacherVO = new TeacherVO();
					teacherVO.setTeacherNo(teacherNo);
					teacherVO.setSkill(skill);
					teacherVO.setDescription(description);
					teacherVO.setTeacherStatus(teacherStatus);
					req.setAttribute("teacherVOForUpdate", teacherVO);

					if (!errorMsgs.isEmpty()) {
						req.setAttribute("userVOForUpdate", userVO);
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/teacher/updateTeacher.jsp");
						failureView.forward(req, res);
						return;
					}

				} else if (type.equals(2)) {

					EmpVO empVO = new EmpVO();
					empVO.setEmpNo(empNo);
					empVO.setEmpStatus(empStatus);

					req.setAttribute("empVOForUpdate", empVO);

					if (!errorMsgs.isEmpty()) {
						req.setAttribute("userVOForUpdate", userVO);
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/updateEmp.jsp");
						failureView.forward(req, res);
						return;
					}
				}

				/*********************** 4.UserTable開始修改資料 *************************/

				UserService userSvc = new UserService();
				userVO = userSvc.updateUser(userNo, type, name, mail, phone, address, id, photo, enable);

				/*************************** 5.開始修改其他table **********************************/

				TeacherVO teacherVO = null;
				EmpVO empVO = null;
				StudentVO studentVO = null;

				if (type.equals(0)) {
					StudentService studentSvc = new StudentService();
					studentVO = studentSvc.updateStudent(studentNo, userNo, name, banjiNo, description, studentStatus);

				} else if (type.equals(1)) {
					// 修改講師資料

					TeacherService teacherSvc = new TeacherService();
					teacherVO = teacherSvc.updateTeacher(teacherNo, userNo, name, skill, description, teacherStatus);

					// 當導師改成離職時自動刪除使用者身分
					if (teacherStatus.equals(0)) {
						UserService userSvcDelete = new UserService();
						userSvcDelete.deleteUser(userNo);
					}
				} else if (type.equals(2)) {
					// 修改導師資料

					EmpService empSvc = new EmpService();
					empVO = empSvc.updateEmp(empNo, userNo, empStatus, name);

					/*********************** 修改導師權限資料 *************************/
					PermissionService permissionSvc = new PermissionService();
					UserPermissionService userPermissionService = new UserPermissionService();
					List<PermissionVO> list = permissionSvc.getAll();
					for (PermissionVO permissionVO : list) {
						Integer readable = 0;
						Integer editable = 1;

						if (req.getParameter("editable" + permissionVO.getPermissionNo()) == null) {
							editable = 0;
						}

						userPermissionService.updateUserPermission(userNo, permissionVO.getPermissionNo(), readable,
								editable);
					}

					// 當導師改成離職時自動刪除使用者身分
					if (empStatus.equals(0)) {
						UserService userSvcDelete = new UserService();
						userSvcDelete.deleteUser(userNo);
					}
				}

				/*************************** 6.修改完成,準備轉交(Send the Success view) ***********/
				UserService userSercice = new UserService();
				userVO = userSercice.getOneUser(userVO.getUserNo());

				// 當userVO被刪除時直接轉交畫面請記得return
				if (userVO == null) {

					if (type.equals(0)) {
						RequestDispatcher successView = req.getRequestDispatcher("/back-end/student/studentList.jsp");
						successView.forward(req, res);
						return;
					} else if (type.equals(1)) {
						RequestDispatcher successView = req.getRequestDispatcher("/back-end/teacher/teacherList.jsp");
						successView.forward(req, res);
						return;
					} else {
						RequestDispatcher successView = req.getRequestDispatcher("/back-end/emp/empList.jsp");
						successView.forward(req, res);
						return;
					}

				}

				req.setAttribute("userVOForShow", userVO);

				if (type.equals(0)) {
					req.setAttribute("studentVOForShow", studentVO);
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/student/listOneStudent.jsp");
					successView.forward(req, res);

				} else if (type.equals(1)) {
					req.setAttribute("teacherVOForShow", teacherVO);
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/teacher/listOneTeacher.jsp");
					successView.forward(req, res);

				} else {
					req.setAttribute("empVOForShow", empVO);
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/emp/listOneEmp.jsp");
					successView.forward(req, res);
				}

			} catch (Exception e) {
				errorMsgs.put("mistake", "修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
			}
		}

		if ("enableUpdate".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				Integer type = null;

				try {
					type = java.lang.Integer.valueOf(req.getParameter("type"));
				} catch (NumberFormatException se) {
					errorMsgs.put("type", "請填寫使用者身分欄位");
				}

				String id = req.getParameter("id");
				UserService userSvcId = new UserService();
				String userNo = userSvcId.getOneUserById(id).getUserNo();

				String account = req.getParameter("account");

				String password = req.getParameter("password");
				if (password != null)
					password.trim();
				String passwordReg = "\\w{6,12}";

				if (password == null || password.length() == 0) {
					errorMsgs.put("password", "密碼請勿空白");
				} else if (!password.matches(passwordReg)) {
					errorMsgs.put("password", "密碼僅能輸入英文字母及數字，且長度為6-12");
				}

				String passwordAgain = req.getParameter("passwordAgain");
				if (!passwordAgain.equals(password)) {
					errorMsgs.put("passwordAgain", "與您所輸入之密碼不相同");
				}

				Integer enable = 1;

				/*********************** 接收請求參數 - 輸入格式的錯誤處理 **********************/

				UserVO userVO = new UserVO();
				userVO.setUserNo(userNo);
				userVO.setAccount(account);
				userVO.setPassword(password);
				userVO.setType(type);
				userVO.setId(id);
				userVO.setEnable(enable);

				// 錯誤處理轉向，請區分前後台
				if (type.equals(0)) {

					if (!errorMsgs.isEmpty()) {
						req.setAttribute("userVOForEnable", userVO);
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/userEnable.jsp");
						failureView.forward(req, res);
						return;
					}

				} else {

					if (!errorMsgs.isEmpty()) {
						req.setAttribute("userVOForEnable", userVO);
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/userEnable.jsp");
						failureView.forward(req, res);
						return;
					}
				}

				/********************** 2.UserTable開始修改資料 ***************************/

				UserService userSvc = new UserService();
				userVO = userSvc.enableUser(userNo, password, enable);

				/********************* 3.修改完成,準備轉交(Send the Success view) ***********/

				UserService userSercice = new UserService();
				userVO = userSercice.getOneUser(userVO.getUserNo());
				HttpSession session = req.getSession();
				session.setAttribute("userVO", userVO);

				if (type.equals(0)) {
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/index/index.jsp");
					successView.forward(req, res);

				} else {
					req.setAttribute("enableUpdate", "firstTime");
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/accountmanager/updateThemselves.jsp");
					successView.forward(req, res);
				}

				// 發生錯誤導到登入畫面
			} catch (Exception e) {
				errorMsgs.put("mistake", "修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/login/login.jsp");
				failureView.forward(req, res);
			}
		}

		if ("enable".equals(action)) {
			String guest = req.getParameter("guest");

			String userNo = guest.substring(5);
			userNo = "U" + userNo;

			UserService userSvc = new UserService();
			UserVO userVO = userSvc.getOneUser(userNo);
			req.setAttribute("userVOForEnable", userVO);

			if (userVO.getEnable().equals(0)) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/userEnable.jsp");
				failureView.forward(req, res);
			} else {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
			}

		}

		if ("updatePassword".equals(action)) {
			res.setCharacterEncoding("UTF-8");
			HttpSession session = req.getSession();
			UserVO userVO = (UserVO) session.getAttribute("userVO");
			String CodeAction=req.getParameter("CodeAction");
			
			Gson gson = new Gson();
			/***************** 隨機產生亂數開始 **************/
			if("sendCode".equals(CodeAction)) {
			res.setContentType("text/html;");
			int arrayA[] = new int[62];
			// 數字、英文最後用型別轉換成字元
			for (int i = 0; i < arrayA.length; i++) {
				if (i < 10)
					arrayA[i] = 48 + i; // 數字10個(0-9)

				else if (i < 36)
					arrayA[i] = 55 + i; // 大寫英文字26個參照 ASCII

				else
					arrayA[i] = 61 + i; // 小寫英文字26個參照 ASCII
			}
			int arrayB[] = new int[8];
			Random r = new Random();
			String ranSen = "";
			for (int i = 0; i < arrayB.length; i++) {
				arrayB[i] = arrayA[r.nextInt(62)];
				char ranChar = (char) arrayB[i];
				String charToString = String.valueOf(ranChar);
				ranSen = ranSen + charToString;
			}

			/***************** 隨機產生亂數結束 **************/
			
			UserRedisVO userRedisVO=new UserRedisVO();
			userRedisVO.setCode(ranSen);
			userRedisVO.setTime(new Date().getTime());
			String jsonCode=gson.toJson(userRedisVO);
			UserRedisDAO.keepRandCode(userVO.getUserNo(), jsonCode); //將物件轉成json存進資料庫
			
			String picLink=req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
			+ req.getContextPath() +"/login/login.jsp";
			
			MailService sendMail = new MailService(); //寄出驗證信
			sendMail.sendUpdatePSWMail(userVO.getMail(),"Work Join Learn 更改密碼驗證", ranSen, picLink);
			PrintWriter out = res.getWriter();
			out.print("ok");
			return;
			}
			//輸入驗證碼之驗證
			if("checkCode".equals(CodeAction)) {
				res.setContentType("application/json;");
				String inputCode=req.getParameter("inputCode");
				if(inputCode!=null)inputCode.trim();
				
				String checkJson=UserRedisDAO.getRandCode(userVO.getUserNo());
				UserRedisVO userRedisVO=gson.fromJson(checkJson, UserRedisVO.class);
				
				boolean checkCodeVar=userRedisVO.getCode().equals(inputCode);
				boolean checkTimeVar=(new Date().getTime()-userRedisVO.getTime())<1000*60;
				
				if(checkCodeVar&&checkTimeVar) {
					//驗證成功開始回傳給前台讓其轉換modal
					UserRedisVO userRedisVOForSuc=new UserRedisVO();
					userRedisVOForSuc.setStatus("success");
					PrintWriter out = res.getWriter();
					out.print(gson.toJson(userRedisVOForSuc));
					return;
					
				}else {
					//驗證失敗傳送失敗訊息
					UserRedisMisVO userRedisMisVO =new UserRedisMisVO();
					userRedisMisVO.setStatus("mistake");;
					if(!checkTimeVar) { //只會送出其中一個錯誤碼，先檢查時間
						userRedisMisVO.setMistake("驗證碼已過期請重新寄送");
					}else {
						userRedisMisVO.setMistake("驗證碼輸入錯誤");
					}
					PrintWriter out = res.getWriter();
					out.print(gson.toJson(userRedisMisVO));
					return;
				}
			}
			
			if("checkPSW".equals(CodeAction)) {
				res.setContentType("application/json;");
				String oldPSW=req.getParameter("oldPSW");
				String newPSW=req.getParameter("newPSW");
				String newPSWCheck=req.getParameter("newPSWCheck");
				
				if(oldPSW !=null)oldPSW.trim();
				if(newPSW !=null)newPSW.trim();
				if(newPSWCheck !=null)newPSWCheck.trim();
				
				UserRedisMisVO userRedisMisVO=new UserRedisMisVO();
				
				/*******************錯誤驗證開始*********************/
				UserService userSerVice =new UserService();
				UserVO userVOForChange=userSerVice.getOneUser(userVO.getUserNo());
				
				if(!userVOForChange.getPassword().equals(oldPSW)) {
					userRedisMisVO.setStatus("mistake");
					userRedisMisVO.setMisOldPSW("舊密碼輸入錯誤");;
					
				}
				String passwordReg = "\\w{6,12}";
				if (!newPSW.matches(passwordReg)) {
					userRedisMisVO.setStatus("mistake");
					userRedisMisVO.setMisNewPSW("密碼僅能輸入英文字母及數字，且長度為6-12");
					
				}
				
				if(!newPSWCheck.equals(newPSW)) {
					userRedisMisVO.setStatus("mistake");
					userRedisMisVO.setMisNewPSWCheck("新密碼輸入不一致");
				}
				
				if("mistake".equals(userRedisMisVO.getStatus())) {
					PrintWriter out = res.getWriter();
					out.print(gson.toJson(userRedisMisVO));
					return;
				}
				/*******************錯誤驗證結束修改密碼*********************/
				UserService userService =new UserService();
				userService.update_Password_backEnd(userVO.getUserNo(), newPSW);
				userRedisMisVO.setStatus("success");
				PrintWriter out = res.getWriter();
				out.print(gson.toJson(userRedisMisVO));
				return;
				
			}

		}
		
		if ("update_themselves".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				HttpSession session = req.getSession();
				UserVO userVO = (UserVO) session.getAttribute("userVO");
				String userNo = userVO.getUserNo(); //先取userNo給檢查id使用


				String name = req.getParameter("name");
				if (name != null)
					name.trim();
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (name == null || name.length() == 0) {
					errorMsgs.put("name", "姓名請勿空白");
				} else if (!name.matches(nameReg)) {
					errorMsgs.put("name", "姓名只能是中、英文字母 , 且長度必需在2到10之間");
				}

				String mail = req.getParameter("mail");
				if (mail != null)
					mail.trim();
				String mailReg = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
				if (mail == null || mail.length() == 0) {
					errorMsgs.put("mail", "信箱請勿空白");
				} else if (!mail.matches(mailReg)) {
					errorMsgs.put("mail", "信箱格式錯誤，請再次檢查");
				}

				String phone = req.getParameter("phone");
				if (phone != null)
					phone.trim();
				String phoneReg = "[0-9]{10}";
				if (phone == null || phone.length() == 0) {
				} else if (!phone.matches(phoneReg)) {
					errorMsgs.put("phone", "手機號碼僅接受台灣連絡電話且僅能輸入10碼");
				}

				String address = req.getParameter("address");
				if (address != null)
					address.trim();

				String id = "";
				try {
					id = req.getParameter("id");
					if (id != null)
						id.trim();
					char firstChar = 0;
					int lastNum;
					int firstNum = 0;
					int total = 0;

					char[] firstEng = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
							'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
					int[] multiply = { 0, 8, 7, 6, 5, 4, 3, 2, 1 }; // 扣除英文數字對應的數字權數補上0後的權數
					int[] checkNum = { 10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 25, 26, 27,
							28, 29, 32, 30, 31, 33 };

					String reg = "^[A-Z][12]\\d{8}$";

					if (!id.matches(reg)) {
						errorMsgs.put("id", "請仔細填寫身分證號碼，第一碼請大寫");
					} else {
						firstChar = Character.toUpperCase(id.charAt(0));
						lastNum = Integer.valueOf(Character.toString(id.charAt(9)));

						// 找出字母對應的數字，並計算其值
						for (int i = 0; i < firstEng.length; i++) {
							if (firstChar == firstEng[i]) {
								firstNum = checkNum[i];
								firstNum = (int) (Math.floor(firstNum / 10)) + (firstNum % 10 * 9);
							}
						}

						// 計算1-8的數值
						for (int j = 1; j <= 8; j++) {
							total = total + Integer.valueOf(Character.toString(id.charAt(j))) * multiply[j];
						}

						// 求出檢查碼
						total = total + firstNum;
						total %= 10;

						if (total == 0) {
							if (total != Integer.valueOf(Character.toString(id.charAt(9)))) {
								errorMsgs.put("id", "身分證號碼輸入錯誤");
							}
						} else {
							if ((10 - total) != Integer.valueOf(Character.toString(id.charAt(9)))) {
								errorMsgs.put("id", "身分證號碼輸入錯誤");

							}
						}
					}
				} catch (StringIndexOutOfBoundsException se) {
					errorMsgs.put("id", "請仔細填寫身分證號碼");
				}

				UserService userSvcId = new UserService();
				List<String> idVS = userSvcId.checkId(userNo);

				for (String checkId : idVS) {
					if (id.equals(checkId)) {
						errorMsgs.put("id", "身分證字號：已重複註冊請更換");
						break;
					}
				}

				Part part = req.getPart("photo");
				InputStream photo = null;
				String form = part.getContentType().toLowerCase();

				UserService userSercice1 = new UserService();
				photo = userSercice1.getPic(userNo);

				if (form.contains("image") && errorMsgs.isEmpty()) {
					photo = part.getInputStream();
				}

				/********************* 2.錯誤裝箱導回修改畫面 *********************/
				//直接從session取不改變的資料再覆蓋進資料庫，就無需再創建新的方法(DAO)
				Integer type =userVO.getType();
				Integer enable =userVO.getEnable();
				
				UserVO userVOForUpdateAccount = new UserVO();
				userVOForUpdateAccount.setUserNo(userNo);
				userVOForUpdateAccount.setType(type);
				userVOForUpdateAccount.setName(name);
				userVOForUpdateAccount.setMail(mail);
				userVOForUpdateAccount.setPhone(phone);
				userVOForUpdateAccount.setAddress(address);
				userVOForUpdateAccount.setId(id);
				userVOForUpdateAccount.setPhoto(photo);
				userVOForUpdateAccount.setEnable(enable);
				
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("userVOForUpdateAccount", userVOForUpdateAccount);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/accountmanager/updateThemselves.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/********************* 3.資料無誤開始修改*********************/
				UserService userSvc = new UserService();
				UserVO  tempUserVO= userSvc.updateUser(userNo, type, name, mail, phone, address, id, photo, enable);
				userVO =userSvc.getOneUser(tempUserVO.getUserNo());
				
				/****************** 4.開始修改其他Table共有資料******************/
				
				if(type.equals(0)) {
					
				}else if(type.equals(1)) {
					TeacherService teacherSvc=new TeacherService();
					TeacherVO teacherVO=teacherSvc.getOneTeacherByUserNo(userNo);
					
					teacherVO=teacherSvc.updateTeacher(teacherVO.getTeacherNo(), userNo, name, teacherVO.getSkill(), teacherVO.getDescription(), teacherVO.getTeacherStatus());
					
					session.setAttribute("userVO", userVO);
					session.setAttribute("teacherVO", teacherVO);
					
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/accountmanager/listThemselves.jsp");
					successView.forward(req, res);
					return;
				}else {
					EmpService empSvc=new EmpService();
					EmpVO empVO=empSvc.getOneEmpByUserNo(userNo);
					
					empVO=empSvc.updateEmp(empVO.getEmpNo(), userNo, empVO.getEmpStatus(), name); //其實只改名字
					
					session.setAttribute("userVO", userVO);
					session.setAttribute("empVO", empVO);
					
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/accountmanager/listThemselves.jsp");
					successView.forward(req, res);
					return;
				}
				
				
			} catch (Exception e) {
				errorMsgs.put("mistake", "修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/index/index.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update_stuselves".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.接收請求參數 ****************************************/
				String userNo = req.getParameter("userNo");

				/*************************** 2.開始查詢資料 ****************************************/

				UserService userSvc = new UserService();
				UserVO userVO = userSvc.getOneUser(userNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/

				req.setAttribute("userVO", userVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/accountmanager/updateStu.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("mistake","無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/select_page.jsp");
				failureView.forward(req, res);
			}

		}
		
		

		if ("update_stuself".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String userNo = req.getParameter("userNo");

				Integer type = 0;

				String name = req.getParameter("name");
				if (name != null)
					name.trim();
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (name == null || name.trim().length() == 0) {
					errorMsgs.put("name", "姓名請勿空白");
				} else if (!name.trim().matches(nameReg)) {
					errorMsgs.put("name", "姓名只能是中、英文字母 , 且長度必需在2到10之間");
				}

				String mail = req.getParameter("mail");
				if (mail != null)
					mail.trim();
				String mailReg = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";
				if (mail == null || mail.trim().length() == 0) {
					errorMsgs.put("mail", "信箱請勿空白");
				} else if (!mail.trim().matches(mailReg)) {
					errorMsgs.put("mail", "信箱格式錯誤，請再次檢查");
				}

				String phone = req.getParameter("phone");
				if (phone != null)
					phone.trim();
				String phoneReg = "[0-9]{10}";
				if (phone == null || phone.trim().length() == 0) {
				} else if (!phone.trim().matches(phoneReg)) {
					errorMsgs.put("phone", "手機號碼僅接受台灣連絡電話且僅能輸入10碼");
				}

				String address = req.getParameter("address");
				if (address != null)
					address.trim();

				String id = "";
				try {
					id = req.getParameter("id");
					if (id != null)
						id.trim();
					char firstChar = 0;
					int lastNum;
					int firstNum = 0;
					int total = 0;

					char[] firstEng = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
							'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
					int[] multiply = { 0, 8, 7, 6, 5, 4, 3, 2, 1 }; // 扣除英文數字對應的數字權數補上0後的權數
					int[] checkNum = { 10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 25, 26, 27,
							28, 29, 32, 30, 31, 33 };

					String reg = "^[A-Z][12]\\d{8}$";

					if (!id.matches(reg)) {
						errorMsgs.put("id", "請仔細填寫身分證號碼，第一碼請大寫");
					} else {
						firstChar = Character.toUpperCase(id.charAt(0));
						lastNum = Integer.valueOf(Character.toString(id.charAt(9)));

						// 找出字母對應的數字，並計算其值
						for (int i = 0; i < firstEng.length; i++) {
							if (firstChar == firstEng[i]) {
								firstNum = checkNum[i];
								firstNum = (int) (Math.floor(firstNum / 10)) + (firstNum % 10 * 9);
							}
						}

						// 計算1-8的數值
						for (int j = 1; j <= 8; j++) {
							total = total + Integer.valueOf(Character.toString(id.charAt(j))) * multiply[j];
						}

						// 求出檢查碼
						total = total + firstNum;
						total %= 10;

						if (total == 0) {
							if (total != Integer.valueOf(Character.toString(id.charAt(9)))) {
								errorMsgs.put("id", "身分證號碼輸入錯誤");
							}
						} else {
							if ((10 - total) != Integer.valueOf(Character.toString(id.charAt(9)))) {
								errorMsgs.put("id", "身分證號碼輸入錯誤");

							}
						}
					}
				} catch (StringIndexOutOfBoundsException se) {
					errorMsgs.put("id", "請仔細填寫身分證號碼");
				}

				UserService userSvcId = new UserService();
				List<String> idVS = userSvcId.checkId(userNo);

				for (String checkId : idVS) {
					if (id.equals(checkId)) {
						errorMsgs.put("id", "身分證字號：已重複註冊請更換");
						break;
					}
				}

				Integer enable = 1;

				try {
					enable = java.lang.Integer.valueOf(req.getParameter("enable"));
				} catch (NumberFormatException se) {
					errorMsgs.put("enable", "請填寫啟用狀態");
				}

				Part part = req.getPart("photo");
				InputStream photo = null;
				String form = part.getContentType().toLowerCase();

				UserService userSercice1 = new UserService();
				photo = userSercice1.getPic(userNo);

				if (form.contains("image") && errorMsgs.isEmpty()) {
					photo = part.getInputStream();
				}

				/*********************** 2.其他table接收請求參數 *************************/

				UserVO userVO = new UserVO();
				userVO.setUserNo(userNo);
				userVO.setType(type);
				userVO.setName(name);
				userVO.setMail(mail);
				userVO.setPhone(phone);
				userVO.setAddress(address);
				userVO.setId(id);
				userVO.setPhoto(photo);
				userVO.setEnable(enable);

				/*********************** 取得學生資料 *************************/
				String studentNo = req.getParameter("studentNo");
				String banjiNo = req.getParameter("banjiNo");

				Integer studentStatus = 1;

				String description = req.getParameter("description");
				if (description != null)
					description.trim();

				StudentVO studentVO = new StudentVO();
				studentVO.setStudentNo(studentNo);
				studentVO.setBanjiNo(banjiNo);
				studentVO.setStudentDescription(description);
				studentVO.setStudentStatus(studentStatus);

				req.setAttribute("studentVO", studentVO);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("userVO", userVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/accountmanager/updateStu.jsp");
					failureView.forward(req, res);
					return;
				}

				
				/*********************** 4.UserTable開始修改資料 *************************/

				UserService userSvc = new UserService();
				userVO = userSvc.updateUser(userNo, type, name, mail, phone, address, id, photo, enable);

				/*************************** 5.開始修改其他table **********************************/

				StudentService studentSvc = new StudentService();
				studentVO = studentSvc.updateStudent(studentNo, userNo, name, banjiNo, description, studentStatus);

				/*************************** 6.修改完成,準備轉交(Send the Success view) ***********/
				UserService userSercice = new UserService();
				userVO = userSercice.getOneUser(userVO.getUserNo());

				req.setAttribute("userVO", userVO);

				req.setAttribute("studentVO", studentVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/accountmanager/listOneStu.jsp");
				successView.forward(req, res);
				HttpSession session = req.getSession();
				session.setAttribute("userVO", userVO);
				session.setAttribute("studentVO", studentVO);

			} catch (Exception e) {
				errorMsgs.put("mistake", "修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/index/index.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update_passwordstu".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String mail=req.getParameter("mail");
				String encoding = req.getParameter("id");
				String messageText = "http://localhost:8081" + req.getContextPath() + "/ChangePassword/ChangePassword.do?id="
						+ encoding;

				MailService sendMail = new MailService();
				sendMail.sendMail(mail, "Work Join 變更密碼通知", messageText);
				
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/accountmanager/mailSuccess.jsp");
				successView.forward(req, res);
				


			} catch (Exception e) {
				errorMsgs.put("mistake", "修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/index/index.jsp");
				failureView.forward(req, res);
			}
		}
		

	}
}
