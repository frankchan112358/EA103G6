package com.finalscore.controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.reflect.Type;
import java.util.List;

import com.course.model.CourseService;
import com.finalscore.model.FinalScoreService;
import com.finalscore.model.FinalScoreVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.student.model.StudentService;
import com.student.model.StudentVO;
import com.websocketnotify.controller.NotifyServlet;

public class FinalScoreAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("ok");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("update".equals(action)) {
			res.setContentType("text/html;");
			String jsonStr = req.getParameter("scoreList");
			PrintWriter out = res.getWriter();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			System.out.println(jsonStr);
			Type collectionType = new TypeToken<List<FinalScoreVO>>() {
			}.getType();
			List<FinalScoreVO> finalScoreList = gson.fromJson(jsonStr, collectionType);
			for (FinalScoreVO finalScoreVO : finalScoreList) {
				String courseNo = finalScoreVO.getCourseNo();
				String studentNo = finalScoreVO.getStudentNo();
				Integer score = finalScoreVO.getScore();
				FinalScoreService finalScoreSvc = new FinalScoreService();
				if (finalScoreSvc.getScore(courseNo, studentNo) == null) {
					finalScoreSvc.addFinalScore(courseNo, studentNo, score);
					CourseService courseSvc = new CourseService();
					String banjiNo = courseSvc.getOneCourse(courseNo).getBanjiNo();
					StudentService studentService = new StudentService();
					List<StudentVO> stulList = studentService.getAllWithBanji(banjiNo);
					for (StudentVO stuVO : stulList) {
						NotifyServlet notifyServlet = new NotifyServlet();
						notifyServlet.broadcast(stuVO.getUserNo(), "影片公告", "導師上傳了一堂課程影片!!");
					}
				}else {
					finalScoreSvc.updateScore(courseNo, studentNo, score);
				}
				//Future 其實這整段應該要做成交易，只要有一筆失敗，就要全部roolback，但是我們先當作所有都成功的理想狀況
			}
			out.print("ok");
			return;
		}
	}

}
