package com.chatroommember.model;

import java.sql.*;
import java.util.*;




	public class ChatroommemberJDBCDAO implements ChatroommemberDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO chatroommember (chatroommemberno,userNo,chatRoomNo) VALUES (ChatRoomMemberNO_SEQ.NEXTVAL, ?, ?)";
	private static final String GET_ONE_STMT = 
			"SELECT userNo,chatRoomNo FROM chatroommember where chatroommemberNo = ?";
	@Override
	public ChatroommemberVO findByPrimaryKey(String chatroommemberno) {
		ChatroommemberVO chatroommemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, chatroommemberno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				chatroommemberVO = new ChatroommemberVO();
				chatroommemberVO.setChatroomno(rs.getString("chatRoomNo"));
				chatroommemberVO.setUserno(rs.getString("userNo"));
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return chatroommemberVO;
		
		
		
		
	}
	@Override
	public void insert(ChatroommemberVO chatroommemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, chatroommemberVO.getChatroomno());
			pstmt.setString(2, chatroommemberVO.getUserno());


			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public static void main(String[] args) {

		ChatroommemberJDBCDAO dao = new ChatroommemberJDBCDAO();
//		
//		ChatroommemberVO empVO1 = new ChatroommemberVO();
//		empVO1.setChatroommemberno("1");
//		empVO1.setUserno("1");
//		empVO1.setChatroomno("1");
//
//		dao.insert(empVO1);
		
		ChatroommemberVO studentVO3 = dao.findByPrimaryKey("1");
		System.out.print(studentVO3.getUserno() + ",");
		System.out.print(studentVO3.getChatroomno() + ",");

		System.out.println("---------------------");
		
		
		}
	}
	
	
