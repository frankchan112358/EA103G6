package com.message.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MessageDAO implements MessageDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
			"INSERT INTO Message (messageNo,chatRoomNo,userNo,MESSAGETIME,messageCount) VALUES (messageNo_seq.NEXTVAL, ?,?, ?, ?)";
	private static final String GET_ONE_STMT = "SELECT messageNo,chatRoomNo,userNo,messageContent,messageTime,messageCount FROM Message where messageNo = ?";

	@Override
	public void insert(MessageVO messageVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);


			pstmt.setString(1, messageVO.getChatRoomNo());
			pstmt.setString(2, messageVO.getUserNo());
			pstmt.setTimestamp(3, messageVO.getMessageTime());
			pstmt.setString(4, messageVO.getMessageContent());
			

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

	@Override
	public MessageVO findByPrimaryKey(String messageno) {
		MessageVO messageVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, messageno);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				messageVO = new MessageVO();
				messageVO.setMessageContent(rs.getString("messageContent"));
				messageVO.setMessageTime(rs.getTimestamp("messageTime"));
				messageVO.setMessageCount(rs.getInt("messageCount"));

			}
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
		return messageVO;
	}
}
