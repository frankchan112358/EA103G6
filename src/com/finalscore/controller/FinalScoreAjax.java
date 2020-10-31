package com.finalscore.controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.lang.reflect.Type;
import java.util.List;

import com.finalscore.model.FinalScoreVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FinalScoreAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("ok");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
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
				System.out.println(finalScoreVO.getFinalScoreNo());
				System.out.println(finalScoreVO.getCourseNo());
				System.out.println(finalScoreVO.getStudentNo());
				System.out.println(finalScoreVO.getScore());
				System.out.println("=================================");
				
				//TO DO 更新成績
				
				//1.檢查該筆finalScoreVO是不是有在資料庫，提示用courseNo和studentNo去資料庫撈資料
				
				//2.如果撈回來的  _finalScoreVO ，是null，那就insert
				
				//3.如果撈回來的  _finalScoreVO ，不是null，那就一定有finalScoreNo，那就setFinalScoreNo()，進行update
				
				//Future 其實這整段應該要做成交易，只要有一筆失敗，就要全部roolback，但是我們先當作所有都成功的理想狀況
			}

			out.print("ok");
			return;
		}
	}

}
