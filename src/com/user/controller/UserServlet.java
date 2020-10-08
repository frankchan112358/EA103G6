package com.user.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.permission.model.PermissionService;
import com.permission.model.PermissionVO;
import com.teacher.model.TeacherService;
import com.teacher.model.TeacherVO;
import com.user.model.UserService;
import com.user.model.UserVO;
import com.userpermission.model.UserPermissionService;
import com.userpermission.model.UserPermissionVO;

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
			InputStream in = userSvc.getPic(userNo);

			ServletOutputStream out = res.getOutputStream();
			res.setContentType("image/gif");

			byte[] buf = new byte[4 * 1024];
			int len = 0;

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
			in.close();
		}

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("userNo");

				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入使用者編號");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				String userNo = null;
				try {
					userNo = str;
				} catch (Exception e) {
					errorMsgs.add("使用者編號格式不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				UserService userSvc = new UserService();
				UserVO userVO = userSvc.getOneUser(userNo);

				if (userVO == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("userVO", userVO);
				String url = "/back-end/user/listOneUser.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer type = null;

				try {
					type = java.lang.Integer.valueOf(req.getParameter("type"));
				} catch (NumberFormatException se) {
					errorMsgs.add("請填寫使用者身分欄位");
				}

				String account = req.getParameter("account").trim();
				String accountReg = "\\w{6,12}";

				UserService userSVAccount = new UserService();
				List<String> accountVS = userSVAccount.checkAccount(type);

				if (account == null || account.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				} else if (!account.trim().matches(accountReg)) {
					errorMsgs.add("帳號：僅能輸入英文字母及數字，且長度為6-12");
				} else {
					for (String checkAccount : accountVS) {
						if (account.equals(checkAccount)) {
							errorMsgs.add("帳號：已重複註冊請更換");
							break;
						}
					}
				}

				String password = req.getParameter("password");

				String passwordReg = req.getParameter("id");
				if (password == null || password.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				} else if (!password.trim().matches(passwordReg)) {
					errorMsgs.add("密碼：請與學員身分證字號相同");
				}

				String name = req.getParameter("name");
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (name == null || name.trim().length() == 0) {
					errorMsgs.add("姓名: 請勿空白");
				} else if (!name.trim().matches(nameReg)) {
					errorMsgs.add("姓名: 只能是中、英文字母 , 且長度必需在2到10之間");
				}

				String mail = req.getParameter("mail");
				String mailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				if (mail == null || mail.trim().length() == 0) {
					errorMsgs.add("信箱: 請勿空白");
				} else if (!mail.trim().matches(mailReg)) {
					errorMsgs.add("信箱: 格式錯誤，請再次檢查");
				}

				String phone = req.getParameter("phone");
				String phoneReg = "[0-9]{10}";
				if (phone == null || phone.trim().length() == 0) {
				} else if (!phone.trim().matches(phoneReg)) {
					errorMsgs.add("手機號碼: 僅接受台灣連絡電話且僅能輸入10碼");
				}

				String address = req.getParameter("address").trim();

				String id = "";
				try {
					id = req.getParameter("id").trim();
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
						errorMsgs.add("請仔細填寫身分證號碼，第一碼請大寫");
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
								errorMsgs.add("身分證號碼輸入錯誤");

							}
						} else {
							if ((10 - total) != Integer.valueOf(Character.toString(id.charAt(9)))) {
								errorMsgs.add("身分證號碼輸入錯誤");

							}
						}
					}
				} catch (StringIndexOutOfBoundsException se) {
					errorMsgs.add("請仔細填寫身分證號碼");
				}

				UserService userSvcId = new UserService();
				List<String> idVS = userSvcId.checkId("null"); // 請與update方法檢查身分證做比較

				for (String checkId : idVS) {
					if (id.equals(checkId)) {
						errorMsgs.add("身分證字號：已重複註冊請更換");
						break;
					}
				}

				UserVO userVO = new UserVO();
				userVO.setAccount(account);
				userVO.setPassword(password);
				userVO.setType(type);
				userVO.setName(name);
				userVO.setMail(mail);
				userVO.setPhone(phone);
				userVO.setAddress(address);
				userVO.setId(id);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("userVO", userVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/addUser.jsp");
					failureView.forward(req, res);
					return;

				}
				/*************************** 2.開始新增資料 ***************************************/
				UserService userSvc = new UserService();
				userVO = userSvc.addUser(account, password, type, name, mail, phone, address, id);
				/*************************** 2-1.開始新增其身分table *******************************/

				if (type == 0) {
					// 暫無學員table
				} else if (type == 1) {
					// 新增講師table
					String skill = null;
					String description = null;

					UserVO userVO1 = userSvc.getOneUserById(id);
					String userNo = userVO1.getUserNo();

					TeacherService teacherSvc = new TeacherService();
					teacherSvc.addTeacher(userNo, name, skill, description);

				} else {
					// 新增導師table
					UserVO userVO1 = userSvc.getOneUserById(id);
					String userNo = userVO1.getUserNo();

					EmpService empSvc = new EmpService();
					empSvc.addEmp(userNo, name);

					// 新增導師權限table
					PermissionService PermissionSvc = new PermissionService();
					UserPermissionService userPermissionService = new UserPermissionService();
					List<PermissionVO> list = PermissionSvc.getAll();
					for (PermissionVO permissionVO : list) {
						Integer readable = 1;
						Integer editable = 1;

						if (req.getParameter("readable" + permissionVO.getPermissionNo()) == null) {
							readable = 0;
						}
						if (req.getParameter("editabld" + permissionVO.getPermissionNo()) == null) {
							editable = 0;
						}

						userPermissionService.addUserPermission(userNo, permissionVO.getPermissionNo(), readable,
								editable);
					}
				}

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

				RequestDispatcher successView = req.getRequestDispatcher("/back-end/user/listAllUser.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/addUser.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.接收請求參數 ****************************************/
				String userNo = req.getParameter("userNo");

				/*************************** 2.開始查詢資料 ****************************************/

				UserService userSvc = new UserService();
				UserVO userVO = userSvc.getOneUser(userNo);
				req.setAttribute("userVO", userVO);

				/*************************** 2.查詢資料,並準備轉交(Send the Success view) ************/
				req.setAttribute("userVO", userVO);

				if (userVO.getType().equals(0)) {

					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/student/update_student_input.jsp");
					failureView.forward(req, res);
					return;

				} else if (userVO.getType().equals(1)) {

					TeacherService teacherSvc = new TeacherService();
					TeacherVO teacherVO = teacherSvc.getOneTeacherByUserNo(userNo);
					if (teacherVO == null) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/user/update_user_input.jsp");
						failureView.forward(req, res);
						return;
					}

					req.setAttribute("teacherVO", teacherVO);
					RequestDispatcher successView = req
							.getRequestDispatcher("/back-end/teacher/update_teacher_input.jsp");
					successView.forward(req, res);
				} else {

					EmpService empSvc = new EmpService();
					EmpVO empVO = empSvc.getOneEmpByUserNo(userNo);
					if (empVO == null) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/user/update_user_input.jsp");
						failureView.forward(req, res);
						return;
					}

					req.setAttribute("empVO", empVO);
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/emp/update_emp_input.jsp");
					successView.forward(req, res);

				}

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/listAllUser.jsp");
				failureView.forward(req, res);
			}

		}

		if ("getOne_For_Update_themselves".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.接收請求參數 ****************************************/
				String userNo = req.getParameter("userNo");

				/*************************** 2.開始查詢資料 ****************************************/

				UserService userSvc = new UserService();
				UserVO userVO = userSvc.getOneUser(userNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/

				req.setAttribute("userVO", userVO);
				RequestDispatcher successView = req
						.getRequestDispatcher("/back-end/user/update_user_input_by_themselves.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/select_page.jsp");
				failureView.forward(req, res);
			}

		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String userNo = req.getParameter("userNo");

				/*************************** 2.開始刪除資料 ***************************************/
				UserService UserSvc = new UserService();
				UserVO userVO = UserSvc.getOneUser(userNo);

				if (userVO.getType().equals(0)) {
					// 暫無學員table
				} else if (userVO.getType().equals(1)) {

					TeacherService teacherSvc = new TeacherService();
					String teacherNo = teacherSvc.getOneTeacherByUserNo(userNo).getTeacherNo();
					teacherSvc.deleteTeacher(teacherNo);
				} else {
					EmpService empSvc = new EmpService();
					String empNo = empSvc.getOneEmpByUserNo(userNo).getEmpNo();
					empSvc.deleteEmp(empNo);

				}

				UserSvc.deleteUser(userNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/user/listAllUser.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/listAllUser.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String userNo = req.getParameter("userNo");

				Integer type = null;

				try {
					type = java.lang.Integer.valueOf(req.getParameter("type"));
				} catch (NumberFormatException se) {
					errorMsgs.add("請填寫使用者身分欄位");
				}

				String account = req.getParameter("account");

				String password = req.getParameter("password");
				String passwordReg = "\\w{6,12}";

				if (password == null || password.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				} else if (!password.trim().matches(passwordReg)) {
					errorMsgs.add("密碼：僅能輸入英文字母及數字，且長度為6-12");
				}

				String name = req.getParameter("name");
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (name == null || name.trim().length() == 0) {
					errorMsgs.add("姓名: 請勿空白");
				} else if (!name.trim().matches(nameReg)) {
					errorMsgs.add("姓名: 只能是中、英文字母 , 且長度必需在2到10之間");
				}

				String mail = req.getParameter("mail");
				String mailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				if (mail == null || mail.trim().length() == 0) {
					errorMsgs.add("信箱: 請勿空白");
				} else if (!mail.trim().matches(mailReg)) {
					errorMsgs.add("信箱: 格式錯誤，請再次檢查");
				}

				String phone = req.getParameter("phone");
				String phoneReg = "[0-9]{10}";
				if (phone == null || phone.trim().length() == 0) {
				} else if (!phone.trim().matches(phoneReg)) {
					errorMsgs.add("手機號碼: 僅接受台灣連絡電話且僅能輸入10碼");
				}

				String address = req.getParameter("address").trim();

				String id = "";
				try {
					id = req.getParameter("id").trim();
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
						errorMsgs.add("請仔細填寫身分證號碼，第一碼請大寫");
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
								errorMsgs.add("身分證號碼輸入錯誤");
							}
						} else {
							if ((10 - total) != Integer.valueOf(Character.toString(id.charAt(9)))) {
								errorMsgs.add("身分證號碼輸入錯誤");

							}
						}
					}
				} catch (StringIndexOutOfBoundsException se) {
					errorMsgs.add("請仔細填寫身分證號碼");
				}

				UserService userSvcId = new UserService();
				List<String> idVS = userSvcId.checkId(userNo);

				for (String checkId : idVS) {
					if (id.equals(checkId)) {
						errorMsgs.add("身分證字號：已重複註冊請更換");
						break;
					}
				}

				Integer enable = null;

				try {
					enable = java.lang.Integer.valueOf(req.getParameter("enable"));
				} catch (NumberFormatException se) {
					errorMsgs.add("請填寫啟用狀態");
				}

				Part part = req.getPart("photo");
				InputStream photo = null;
				String form = part.getContentType().toLowerCase();

				UserService userSercice1 = new UserService();
				photo = userSercice1.getPic(userNo);

				if (form.contains("image") && errorMsgs.isEmpty()) {
					photo = part.getInputStream();
				}

				/*********************** 1.1其他table接收請求參數 - 輸入格式的錯誤處理 *************************/

				UserVO userVO = new UserVO();
				userVO.setUserNo(userNo);
				userVO.setAccount(account);
				userVO.setPassword(password);
				userVO.setType(type);
				userVO.setName(name);
				userVO.setMail(mail);
				userVO.setPhone(phone);
				userVO.setAddress(address);
				userVO.setId(id);
				userVO.setPhoto(photo);
				userVO.setEnable(enable);

				if (type.equals(0)) {

					if (!errorMsgs.isEmpty()) {
						req.setAttribute("userVO", userVO);
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/student/update_student_input.jsp");
						failureView.forward(req, res);
						return;
					}

				} else if (type.equals(1)) {
					String teacherNo = req.getParameter("teacherNo");
					String skill = req.getParameter("skill").trim();
					String description = req.getParameter("description").trim();
					Integer teacherStatus = java.lang.Integer.valueOf(req.getParameter("teacherStatus"));

					TeacherVO teacherVO = new TeacherVO();
					teacherVO.setTeacherNo(teacherNo);
					teacherVO.setSkill(skill);
					teacherVO.setDescription(description);
					teacherVO.setTeacherStatus(teacherStatus);
					req.setAttribute("teacherVO", teacherVO);

					if (!errorMsgs.isEmpty()) {
						req.setAttribute("userVO", userVO);
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/teacher/update_teacher_input.jsp");
						failureView.forward(req, res);
						return;
					}

				} else {

					String empNo = req.getParameter("empNo");
					Integer empStatus = java.lang.Integer.valueOf(req.getParameter("empStatus"));

					EmpVO empVO = new EmpVO();
					empVO.setEmpNo(empNo);
					empVO.setEmpStatus(empStatus);
					req.setAttribute("empVO", empVO);

					if (!errorMsgs.isEmpty()) {
						req.setAttribute("userVO", userVO);
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/update_emp_input.jsp");
						failureView.forward(req, res);
						return;
					}
				}

				/**************************
				 * 2.UserTable開始修改資料
				 **********************************/

				UserService userSvc = new UserService();
				userVO = userSvc.updateUser(userNo, account, password, type, name, mail, phone, address, id, photo,
						enable);

				/*************************** 2.1開始修改其他table **********************************/

				TeacherVO teacherVO = null;
				EmpVO empVO = null;

				if (type.equals(0)) {
					// 暫時無學員table

				} else if (type.equals(1)) {
					// 修改講師資料

					String teacherNo = req.getParameter("teacherNo");
					String skill = req.getParameter("skill").trim();
					String description = req.getParameter("description").trim();
					Integer teacherStatus = java.lang.Integer.valueOf(req.getParameter("teacherStatus"));

					TeacherService teacherSvc = new TeacherService();
					teacherVO = teacherSvc.updateTeacher(teacherNo, userNo, name, skill, description, teacherStatus);

				} else {
					// 修改導師資料
					String empNo = req.getParameter("empNo");
					Integer empStatus = java.lang.Integer.valueOf(req.getParameter("empStatus"));

					EmpService empSvc = new EmpService();
					empVO = empSvc.updateEmp(empNo, userNo, empStatus, name);

				}

				/*************************** 3.修改完成,準備轉交(Send the Success view) ***********/
				UserService userSercice = new UserService();
				userVO = userSercice.getOneUser(userVO.getUserNo());
				req.setAttribute("userVO", userVO);

				if (type.equals(0)) {
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/student/listOneStudent.jsp");
					successView.forward(req, res);

				} else if (type.equals(1)) {
					req.setAttribute("teacherVO", teacherVO);
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/teacher/listOneTeacher.jsp");
					successView.forward(req, res);

				} else {
					req.setAttribute("empVO", empVO);
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/emp/listOneEmp.jsp");
					successView.forward(req, res);
				}

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/user/select_page.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
