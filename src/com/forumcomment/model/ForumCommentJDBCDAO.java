package com.forumcomment.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ForumCommentJDBCDAO implements ForumCommentDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO forumcomment (forumcommentno,forumpostno,studentno, content ,updatetime,createtime) VALUES (forumcomment_seq.NEXTVAL,?, ?,?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT forumcommentno,forumpostno,studentno, content ,updatetime,createtime FROM forumcomment where isDelete = 0 order by to_number(forumcommentno)";
	private static final String GET_ONE_STMT = "SELECT forumcommentno,forumpostno,studentno, content ,updatetime,createtime FROM forumcomment where forumcommentno = ?";
	private static final String DELETE = "UPDATE forumcomment set isDelete=1 where forumcommentno = ?";
	private static final String UPDATE = "UPDATE forumcomment set forumpostno=?, studentno=?, content=?, updatetime=? where forumcommentno = ?";


	@Override
	public void insert(ForumCommentVO forumCommentVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, forumCommentVO.getForumPostNo());
			pstmt.setString(2, forumCommentVO.getStudentNo());
			pstmt.setString(3, forumCommentVO.getContent());
			pstmt.setTimestamp(4, forumCommentVO.getUpdateTime());
			pstmt.setTimestamp(5, forumCommentVO.getCreateTime());
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
	public void update(ForumCommentVO forumCommentVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, forumCommentVO.getForumPostNo());
			pstmt.setString(2, forumCommentVO.getStudentNo());
			pstmt.setString(3, forumCommentVO.getContent());
			pstmt.setTimestamp(4, forumCommentVO.getUpdateTime());
//			pstmt.setTimestamp(5, forumCommentVO.getCreateTime());
			pstmt.setString(5, forumCommentVO.getForumCommentNo());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String forumCommentNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, forumCommentNo);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public ForumCommentVO findByPrimaryKey(String forumCommentNo) {
		ForumCommentVO forumCommentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, forumCommentNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumCommentVO = new ForumCommentVO();
				forumCommentVO.setForumCommentNo(rs.getString("forumCommentNo"));
				forumCommentVO.setForumPostNo(rs.getString("forumPostNo"));
				forumCommentVO.setStudentNo(rs.getString("studentNo"));
				forumCommentVO.setContent(rs.getString("content"));
				forumCommentVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumCommentVO.setCreateTime(rs.getTimestamp("createTime"));
				

			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return forumCommentVO;

	}

	@Override
	public List<ForumCommentVO> getAll() {
		List<ForumCommentVO> list = new ArrayList<ForumCommentVO>();
		ForumCommentVO forumCommentVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumCommentVO = new ForumCommentVO();
				forumCommentVO.setForumCommentNo(rs.getString("forumCommentNo"));
				forumCommentVO.setForumPostNo(rs.getString("forumPostNo"));
				forumCommentVO.setStudentNo(rs.getString("studentNo"));
				forumCommentVO.setContent(rs.getString("content"));
				forumCommentVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumCommentVO.setCreateTime(rs.getTimestamp("createTime"));
				list.add(forumCommentVO);
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return list;

	}
	public static void main(String[] args) {
		ForumCommentJDBCDAO dao = new ForumCommentJDBCDAO();

		ForumCommentVO forumCommentVO1 = new ForumCommentVO();
		forumCommentVO1.setForumPostNo("1");
		forumCommentVO1.setStudentNo("S000001");
		forumCommentVO1.setContent("座位(2-3)水瓶忘了帶走");
		forumCommentVO1.setUpdateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		forumCommentVO1.setCreateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		dao.insert(forumCommentVO1);

		ForumCommentVO forumCommentVO2 = new ForumCommentVO();
		forumCommentVO2.setForumCommentNo("3");
		forumCommentVO2.setForumPostNo("3");
		forumCommentVO2.setStudentNo("S000003");
		forumCommentVO2.setContent("請到櫃檯拿講義");
		forumCommentVO2.setUpdateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		forumCommentVO2.setCreateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		dao.update(forumCommentVO2);

    	dao.delete("2");

		ForumCommentVO forumCommentVO3 = dao.findByPrimaryKey("2");
		System.out.print(forumCommentVO3.getForumCommentNo() + ",");
		System.out.print(forumCommentVO3.getForumPostNo() + ",");
		System.out.print(forumCommentVO3.getStudentNo() + ",");
		System.out.print(forumCommentVO3.getContent() + ",");
		System.out.print(forumCommentVO3.getUpdateTime() + ",");
		System.out.print(forumCommentVO3.getCreateTime() + ",");
		System.out.println("---------------------");

		List<ForumCommentVO> list = dao.getAll();
		for (ForumCommentVO aForumPost : list) {
			System.out.print(aForumPost.getForumCommentNo() + ",");
			System.out.print(aForumPost.getForumPostNo() + ",");
			System.out.print(aForumPost.getStudentNo() + ",");
			System.out.print(aForumPost.getContent() + ",");
			System.out.print(aForumPost.getUpdateTime() + ",");
			System.out.print(aForumPost.getCreateTime() + ",");
			System.out.println();
		}
	}

}



