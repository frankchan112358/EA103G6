package com.chatroommember.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.chatroommember.model.*;

public class ChatroommemberDAO implements ChatroommemberDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO chatroommember (chatroommemberno,userNo,chatRoomNo) VALUES (chatroommember_seq.NEXTVAL, ?, ?)";
	private static final String GET_ONE_STMT = "SELECT userNo,chatRoomNo FROM chatroommember where chatroommemberNo = ?";

	@Override
	public ChatroommemberVO findByPrimaryKey(String chatroommemberno) {
		ChatroommemberVO chatroommemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, chatroommemberno);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				chatroommemberVO = new ChatroommemberVO();
				chatroommemberVO.setChatroomno(rs.getString("chatRoomNo"));
				chatroommemberVO.setUserno(rs.getString("userNo"));
			}
			// Handle any driver errors
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
		return chatroommemberVO;

	}

	@Override
	public void insert(ChatroommemberVO chatroommemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, chatroommemberVO.getChatroomno());
			pstmt.setString(2, chatroommemberVO.getUserno());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	}

}
