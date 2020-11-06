package com.forum.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.forumcomment.model.ForumCommentService;
import com.forumpost.model.ForumPostService;
import com.forumpost.model.ForumPostVO;
import com.forumtopic.model.ForumTopicService;
import com.student.model.StudentVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if (action == null) {
			res.setContentType("text/html;");
			HttpSession session = req.getSession();
			StudentVO studentVO = (StudentVO) session.getAttribute("studentVO");
			req.setAttribute("forumTopicList", new ForumTopicService().getAllWithBanji(studentVO.getBanjiNo()));
			String url = "/front-end/forum/forumHome.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumTopicHomePage".equals(action)) {
			res.setContentType("text/html;");
			String forumTopicNo = req.getParameter("forumTopicNo");
			req.setAttribute("forumTopicVO", new ForumTopicService().getOneForumTopic(forumTopicNo));
			req.setAttribute("forumPostList", new ForumPostService().getByTopicNo(forumTopicNo));
			String url = "/front-end/forum/forumTopic.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumPostHomePage".equals(action)) {
			res.setContentType("text/html;");
			String forumPostNo = req.getParameter("forumPostNo");
			new ForumPostService().addForumPostViews2(forumPostNo);
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			req.setAttribute("forumCommentList", new ForumCommentService().getOneFpFc(forumPostNo));
			String url = "/front-end/forum/forumPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumStudentHomePage".equals(action)) {
			res.setContentType("text/html;");
			HttpSession session = req.getSession();
			StudentVO studentVO = (StudentVO) session.getAttribute("studentVO");		
			req.setAttribute("forumPostList", new ForumPostService().getOneSTUDENT(studentVO.getStudentNo()));
			String url = "/front-end/forum/forumStudent.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumPostStudentHomePage".equals(action)) {
			res.setContentType("text/html;");
			String forumPostNo = req.getParameter("forumPostNo");
			req.setAttribute("mode", "student");
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			req.setAttribute("forumCommentList", new ForumCommentService().getOneFpFc(forumPostNo));
			String url = "/front-end/forum/forumPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumPostNewPage".equals(action)) {
			res.setContentType("text/html;");
			String forumTopicNo = req.getParameter("forumTopicNo");
			req.setAttribute("forumTopicVO", new ForumTopicService().getOneForumTopic(forumTopicNo));
			String url = "/front-end/forum/forumPostEditor.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumPostUpdatePage".equals(action)) {
			res.setContentType("text/html;");
			String forumPostNo = req.getParameter("forumPostNo");
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			String url = "/front-end/forum/forumPostUpdate.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumPostInsert".equals(action)) {
			res.setContentType("text/html;");
			Part part = req.getPart("content");
			String title = req.getParameter("title");
			String forumTopicNo = req.getParameter("forumTopicNo");
			String studentNo = req.getParameter("studentNo");
			String forumPostNo = "";
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				String cols[] = { "forumpostno" };
				pstmt = con.prepareStatement(
						"INSERT INTO forumpost (forumpostno,forumtopicno,studentno,title, content , updatetime,createtime) VALUES (forumpost_seq.NEXTVAL,?, ?,?, ?, ?, ?)",
						cols);
				part.getInputStream();
				InputStream in = part.getInputStream();
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				byte[] notes = new byte[in.available()];
				in.read(notes);
				bao.write(notes);
				Reader reader = new InputStreamReader(new ByteArrayInputStream(notes), "UTF-8");
				pstmt.setString(1, forumTopicNo);
				pstmt.setString(2, studentNo);
				pstmt.setString(3, title);
				pstmt.setCharacterStream(4, reader);
				pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				bao.close();
				in.close();
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					forumPostNo = rs.getString(1);
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			req.setAttribute("forumCommentList", new ForumCommentService().getOneFpFc(forumPostNo));
			String url = "/front-end/forum/forumPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumPostUpdate".equals(action)) {
			res.setContentType("text/html;");
			Part part = req.getPart("content");
			String title = req.getParameter("title");
			String forumPostNo = req.getParameter("forumPostNo");
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement("UPDATE forumpost set title=?, content=?, updatetime=? where forumpostno = ?");
				part.getInputStream();
				InputStream in = part.getInputStream();
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				byte[] notes = new byte[in.available()];
				in.read(notes);
				bao.write(notes);
				Reader reader = new InputStreamReader(new ByteArrayInputStream(notes), "UTF-8");
				pstmt.setString(1, title);
				pstmt.setCharacterStream(2, reader);
				pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(4, forumPostNo);
				bao.close();
				in.close();
				pstmt.executeUpdate();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			} catch (SQLException se) {
				se.printStackTrace();
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			req.setAttribute("mode", "student");
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			req.setAttribute("forumCommentList", new ForumCommentService().getOneFpFc(forumPostNo));
			String url = "/front-end/forum/forumPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumPostDelete".equals(action)) {
			res.setContentType("text/html;");
			String forumPostNo = req.getParameter("forumPostNo");
			new ForumPostService().deleteForumPost(forumPostNo);
			HttpSession session = req.getSession();
			StudentVO studentVO = (StudentVO) session.getAttribute("studentVO");		
			req.setAttribute("forumPostList", new ForumPostService().getOneSTUDENT(studentVO.getStudentNo()));
			String url = "/front-end/forum/forumStudent.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumCommentNewPage".equals(action)) {
			res.setContentType("text/html;");
			String forumPostNo = req.getParameter("forumPostNo");
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			String url = "/front-end/forum/ForumCommentEditor.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumCommentUpdatePage".equals(action)) {
			res.setContentType("text/html;");
			String forumPostNo = req.getParameter("forumPostNo");
			String forumCommentNo = req.getParameter("forumCommentNo");
			req.setAttribute("mode", "update");
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			req.setAttribute("forumCommentVO", new ForumCommentService().getOneForumComment(forumCommentNo));
			String url = "/front-end/forum/ForumCommentEditor.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumCommentInsert".equals(action)) {
			res.setContentType("text/html;");
			Part part = req.getPart("content");
			String forumPostNo = req.getParameter("forumPostNo");
			String studentNo = req.getParameter("studentNo");
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement("INSERT INTO forumcomment (forumcommentno,forumpostno,studentno, content ,updatetime,createtime) VALUES (forumcomment_seq.NEXTVAL,?, ?,?, ?, ?)");
				part.getInputStream();
				InputStream in = part.getInputStream();
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				byte[] notes = new byte[in.available()];
				in.read(notes);
				bao.write(notes);
				Reader reader = new InputStreamReader(new ByteArrayInputStream(notes), "UTF-8");
				pstmt.setString(1, forumPostNo);
				pstmt.setString(2, studentNo);
				pstmt.setCharacterStream(3, reader);
				pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				bao.close();
				in.close();
				pstmt.executeUpdate();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			req.setAttribute("forumCommentList", new ForumCommentService().getOneFpFc(forumPostNo));
			String url = "/front-end/forum/forumPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumCommentUpdate".equals(action)) {
			res.setContentType("text/html;");
			Part part = req.getPart("content");
			String forumPostNo = req.getParameter("forumPostNo");
			String forumCommentNo = req.getParameter("forumCommentNo");
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement("UPDATE forumcomment set content=?,updatetime=? where forumcommentno = ?");
				part.getInputStream();
				InputStream in = part.getInputStream();
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				byte[] notes = new byte[in.available()];
				in.read(notes);
				bao.write(notes);
				Reader reader = new InputStreamReader(new ByteArrayInputStream(notes), "UTF-8");
				pstmt.setCharacterStream(1, reader);
				pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(3, forumCommentNo);
				bao.close();
				in.close();
				pstmt.executeUpdate();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			req.setAttribute("forumCommentList", new ForumCommentService().getOneFpFc(forumPostNo));
			String url = "/front-end/forum/forumPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if ("forumCommentDelete".equals(action)) {
			res.setContentType("text/html;");
			String forumPostNo = req.getParameter("forumPostNo");
			String forumCommentNo = req.getParameter("forumCommentNo");
			new ForumCommentService().deleteForumComment(forumCommentNo);
			req.setAttribute("forumPostVO", new ForumPostService().getOneForumPost(forumPostNo));
			req.setAttribute("forumCommentList", new ForumCommentService().getOneFpFc(forumPostNo));
			String url = "/front-end/forum/forumPost.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
		if("forumPostContent".equals(action)) {
			res.setContentType("text/html;");
			String forumPostNo = req.getParameter("forumPostNo");
			PrintWriter out = res.getWriter();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement("SELECT content FROM forumpost WHERE forumPostNo=?");
				pstmt.setString(1, forumPostNo);
				rs = pstmt.executeQuery();
				if (rs.next() && rs.getString("content") != null) {
					Reader reader = rs.getCharacterStream("content");
					BufferedReader br = new BufferedReader(reader);
					String str;
					while ((str = br.readLine()) != null)
						out.print(str);
					br.close();
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
		}
		if("forumCommentContent".equals(action)) {
			res.setContentType("text/html;");
			String forumCommentNo = req.getParameter("forumCommentNo");
			PrintWriter out = res.getWriter();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement("SELECT content FROM forumComment WHERE forumCommentNo=?");
				pstmt.setString(1, forumCommentNo);
				rs = pstmt.executeQuery();
				if (rs.next() && rs.getString("content") != null) {
					Reader reader = rs.getCharacterStream("content");
					BufferedReader br = new BufferedReader(reader);
					String str;
					while ((str = br.readLine()) != null)
						out.print(str);
					br.close();
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
		}
	}

}
