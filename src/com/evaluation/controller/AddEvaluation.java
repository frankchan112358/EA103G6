package com.evaluation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.evaluation.model.EvaluationQuestion;
import com.evaluation.model.EvaluationService;

public class AddEvaluation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		try {
			String studentNo = req.getParameter("studentNo");
			String courseNo = req.getParameter("courseNo");
			EvaluationService evaluationSvc = new EvaluationService();
			for (int i = 1; i <= EvaluationQuestion.values().length; i++) {
				Integer question = i;
				Integer answer = Integer.parseInt(req.getParameter("Q" + i));
				evaluationSvc.addEvaluation(courseNo, studentNo, question, answer);
			}
			out.print("ok");
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

}
