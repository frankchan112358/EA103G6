package com.timetable.controller;

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

import com.timetable.model.TimetableService;

@WebServlet("/TeachingNoteSummernote")
@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class TeachingNoteSummernote extends HttpServlet {
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
		if ("read".equals(action)) {
			res.setContentType("text/html;");
			PrintWriter out = res.getWriter();
			String timetableNo = req.getParameter("timetableNo");
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement("SELECT teachingLog FROM timetable where timetableNo=?");
				pstmt.setString(1, timetableNo);
				rs = pstmt.executeQuery();
				if (rs.next() && rs.getString("teachingLog") != null) {
					Reader reader = rs.getCharacterStream("teachingLog");
					BufferedReader br = new BufferedReader(reader);
					String str;
					while ((str = br.readLine()) != null)
						out.print(str);
					br.close();
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			} catch (SQLException se) {
				se.printStackTrace();
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
		if ("insert".equals(action) || "update".equals(action)) {
			res.setContentType("text/html;");
			PrintWriter out = res.getWriter();
			Part part = req.getPart("teachingNote");
			String timetableNo = req.getParameter("timetableNo");
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement("UPDATE timetable set teachingLog=? where timetableNo=?");
				part.getInputStream();
				InputStream in = part.getInputStream();
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				byte[] notes = new byte[in.available()];
				in.read(notes);
				bao.write(notes);
				Reader reader = new InputStreamReader(new ByteArrayInputStream(notes), "UTF-8");
				pstmt.setCharacterStream(1, reader);
				pstmt.setString(2, timetableNo);
				bao.close();
				in.close();
				pstmt.executeUpdate();
				out.print("ok");
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
		}	
		if ("delete_teachingLog".equals(action)) {
			res.setContentType("text/html;");
			PrintWriter out = res.getWriter();
			try {
				String timetableNo = req.getParameter("timetableNo");
				TimetableService timetableSvc = new TimetableService();
				timetableSvc.deleteTimetableLog(timetableNo);
				out.print("ok");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
