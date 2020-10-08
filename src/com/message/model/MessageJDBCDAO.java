package com.message.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;





public class MessageJDBCDAO implements MessageDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO Message (messageNo,chatRoomNo,userNo,MESSAGETIME,messageCount) VALUES (messageNo_seq.NEXTVAL, ?,?, ?, ?)";
	private static final String GET_ONE_STMT = 
		"SELECT messageNo,chatRoomNo,userNo,messageContent, messageTime ,messageCount FROM Message where messageNo = ?";

	
	

	@Override
	public void insert(MessageVO messageVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, messageVO.getChatRoomNo());
			pstmt.setString(2, messageVO.getUserNo());
			pstmt.setTimestamp(3, messageVO.getMessageTime());
			pstmt.setString(4, messageVO.getMessageContent());

			

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


	@Override
	public MessageVO findByPrimaryKey(String messageno) {
		MessageVO messageVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		return messageVO;
	}
	
	public static void main(String[] args) {

	MessageJDBCDAO dao = new MessageJDBCDAO();
		
		MessageVO empVO1 = new MessageVO();
		empVO1.setChatRoomNo("6");
		empVO1.setUserNo("1");
		empVO1.setMessageContent("112454");
		empVO1.setMessageTime(Timestamp.valueOf("2020-10-07 08:50:00"));
		
		
		

		dao.insert(empVO1);
		
//		MessageVO studentVO3 = dao.findByPrimaryKey("1");
//		System.out.print(studentVO3.getChatRoomNo() + ",");
//		System.out.print(studentVO3.getUserNo() + ",");
//		System.out.print(studentVO3.getMessageContent() + ",");
//		System.out.print(studentVO3.getMessageTime() + ",");
//		System.out.print(studentVO3.getMessageCount() + ",");
		

		System.out.println("---------------------");
	
	}

}
