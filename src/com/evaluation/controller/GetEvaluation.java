package com.evaluation.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.evaluation.model.EvaluationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetEvaluation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("application/json; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String courseNo = req.getParameter("courseNo");
		String studentNo = req.getParameter("studentNo");
		EvaluationService evaluationSvc = new EvaluationService();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String jsonStr = gson.toJson(evaluationSvc.getEvaluationWithCourseStudent(courseNo, studentNo));
		out.print(jsonStr);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
