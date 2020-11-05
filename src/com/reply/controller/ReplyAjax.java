package com.reply.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.courseask.model.CourseAskService;
import com.courseask.model.CourseAskVO;
import com.reply.model.ReplyService;
import com.reply.model.ReplyVO;
import com.student.model.StudentService;
import com.user.model.UserVO;
import com.websocketnotify.controller.NotifyServlet;

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
			UserVO userVO=(UserVO) session.getAttribute("userVO");
			String userNo = ((UserVO) session.getAttribute("userVO")).getUserNo();
			String replyContent = req.getParameter("replyContent");
			java.sql.Timestamp updateTime = new java.sql.Timestamp((new java.util.Date()).getTime());
			new ReplyService().addReply(courseAskNo, replyContent, updateTime, userNo);
			
			if(userVO.getType().equals(2)) {
				CourseAskService courseAskService =new CourseAskService();
				CourseAskVO courseAskVO=courseAskService.getOneCourseAsk(courseAskNo);
				
				StudentService studentService =new StudentService();
				String userNoForNotify=studentService.getOneStudent(courseAskVO.getStudentNo()).getUserNo();
				NotifyServlet notifyServlet =new NotifyServlet();
				notifyServlet.broadcast(userNoForNotify, "回覆通知", "你有一則新的回覆");
				
			}
			
			
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
