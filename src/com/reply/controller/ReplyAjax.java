package com.reply.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reply.model.ReplyService;
import com.reply.model.ReplyVO;
import com.user.model.UserVO;

public class ReplyAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String action = req.getParameter("action");
		if ("getAllWithCourseAskNo".equals(action)) {
			res.setContentType("application/json;");
			String courseAskNo = req.getParameter("courseAskNo");
			PrintWriter out = res.getWriter();
			out.print(new ReplyService().getAllJsonWithCourseAskNo(courseAskNo));
			return;
		}
		if ("getOne".equals(action)) {
			res.setContentType("application/json;");
			String replyNo = req.getParameter("replyNo");
			PrintWriter out = res.getWriter();
			out.print(new ReplyService().getOneReplyJson(replyNo));
			return;
		}
		if ("insertWithStudent".equals(action)) {
			res.setContentType("application/json;");
			String courseAskNo = req.getParameter("courseAskNo");
			String userNo = ((UserVO) session.getAttribute("userVO")).getUserNo();
			String replyContent = req.getParameter("replyContent");
			java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());
			new ReplyService().addReply(courseAskNo, replyContent, updateTime, userNo);
			PrintWriter out = res.getWriter();
			out.print(new ReplyService().getAllJsonWithCourseAskNo(courseAskNo));
			return;
		}
		if ("updateWithStudent".equals(action)) {
			res.setContentType("application/json;");
			String replyNo = req.getParameter("replyNo");
			String replyContent = req.getParameter("replyContent");
			java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());
			ReplyVO replyVO = new ReplyService().getOneReply(replyNo);
			new ReplyService().updateReply(replyNo, replyVO.getCourseAskNo(), replyContent, updateTime,
					replyVO.getUserNo());
			PrintWriter out = res.getWriter();
			out.print(new ReplyService().getAllJsonWithCourseAskNo(replyVO.getCourseAskNo()));
			return;
		}
		if ("deleteWithStudent".equals(action)) {
			res.setContentType("application/json;");
			String replyNo = req.getParameter("replyNo");
			ReplyVO replyVO = new ReplyService().getOneReply(replyNo);
			new ReplyService().deleteReply(replyNo);
			PrintWriter out = res.getWriter();
			out.print(new ReplyService().getAllJsonWithCourseAskNo(replyVO.getCourseAskNo()));
			return;
		}
	}
}
