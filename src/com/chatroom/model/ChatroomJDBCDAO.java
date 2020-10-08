package com.chatroom.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class ChatroomJDBCDAO implements ChatroomDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO Chatroom (ChatroomNO,Chatroomname,chatRoomType) VALUES (CHATROOMNO_SEQ.nextval, ?, ?) ";
	private static final String DELETE = "UPDATE Chatroom SET chatRoomType=2 where ChatroomNO = ?";
	private static final String GET_ONE_STMT = "SELECT ChatroomName FROM  Chatroom where Chatroomno = ?  ";
	private static final String UPDATE = "UPDATE Chatroom set ChatroomName=? where ChatroomNO = ?";
	
	@Override
	public void insert(ChatroomVO chatroomnoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, chatroomnoVO.getChatroomname());
			pstmt.setInt(2, chatroomnoVO.getChatroomtype());


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
	public void delete(String chatroomno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, chatroomno);

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
	public void update(ChatroomVO chatroomVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, chatroomVO.getChatroomname());

			pstmt.executeUpdate();
			

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public ChatroomVO findByPrimaryKey(String chatroomnono) {
		ChatroomVO chatroomVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, chatroomnono);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				chatroomVO = new ChatroomVO();
				chatroomVO.setChatroomname(rs.getString("chatroomname"));		
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
		return chatroomVO;
	}

	
	
	public static void main(String[] args) {

		ChatroomJDBCDAO dao = new ChatroomJDBCDAO();
		
//		ChatroomVO chatroomVO1 = new ChatroomVO();
//		chatroomVO1.setChatroomname("陳浩偉");
//		chatroomVO1.setChatroomtype(0);
//		dao.insert(chatroomVO1);
//		System.out.println("新增成功");
//		
//		dao.delete("4");
//		System.out.println("刪除成功");
		
//		ChatroomVO chatroomVO3 = dao.findByPrimaryKey("1");
//		System.out.print(chatroomVO3.getChatroomname() );
		
		ChatroomVO chatroomVO4 = new ChatroomVO();
		chatroomVO4.setChatroomno("6");
		chatroomVO4.setChatroomname("陳浩偉");
		dao.update(chatroomVO4);
		
	}
}
