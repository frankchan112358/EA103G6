package com.forumpost.model;

import java.util.*;

import com.forumpost.model.ForumPostJDBCDAO;
import com.forumpost.model.ForumPostVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ForumPostJDBCDAO implements ForumPostDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO forumpost (forumpostno,forumtopicno,studentno,title, content ,updatetime,createtime) VALUES (forumpost_seq.NEXTVAL,?, ?,?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT forumpostno,forumtopicno,studentno,title, content,updatetime,createtime FROM forumpost where isDelete = 0 order by to_number(forumpostno)";
	private static final String GET_ONE_STMT = "SELECT forumpostno,forumtopicno,studentno,title, content,updatetime,createtime FROM forumpost where forumpostno = ?";
	private static final String DELETE = "UPDATE forumpost set isDelete=1 where forumpostno = ?";
	private static final String UPDATE = "UPDATE forumpost set forumtopicno=?, studentno=?, title=?, content=?, updatetime=? where forumpostno = ?";

	@Override
	public void insert(ForumPostVO forumPostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, forumPostVO.getForumTopicNo());
			pstmt.setString(2, forumPostVO.getStudentNo());
			pstmt.setString(3, forumPostVO.getTitle());
			pstmt.setString(4, forumPostVO.getContent());
			pstmt.setTimestamp(5, forumPostVO.getUpdateTime());
			pstmt.setTimestamp(6, forumPostVO.getCreateTime());
			

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
	public void update(ForumPostVO forumPostVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, forumPostVO.getForumTopicNo());
			pstmt.setString(2, forumPostVO.getStudentNo());
			pstmt.setString(3, forumPostVO.getTitle());
			pstmt.setString(4, forumPostVO.getContent());
			pstmt.setTimestamp(5, forumPostVO.getUpdateTime());
//			pstmt.setTimestamp(6, forumPostVO.getCreateTime());
			pstmt.setString(6, forumPostVO.getForumPostNo());

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
	public void delete(String forumPostNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, forumPostNo);

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
	public ForumPostVO findByPrimaryKey(String forumPostNo) {

		ForumPostVO forumPostVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, forumPostNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumPostVO = new ForumPostVO();
				forumPostVO.setForumPostNo(rs.getString("forumPostNo"));
				forumPostVO.setForumTopicNo(rs.getString("forumTopicNo"));
				forumPostVO.setStudentNo(rs.getString("studentNo"));
				forumPostVO.setTitle(rs.getString("title"));
				forumPostVO.setContent(rs.getString("content"));
				forumPostVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumPostVO.setCreateTime(rs.getTimestamp("createTime"));
				

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
		return forumPostVO;

	}

	@Override
	public List<ForumPostVO> getAll() {
		List<ForumPostVO> list = new ArrayList<ForumPostVO>();
		ForumPostVO forumPostVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumPostVO = new ForumPostVO();
				forumPostVO.setForumPostNo(rs.getString("forumPostNo"));
				forumPostVO.setForumTopicNo(rs.getString("forumTopicNo"));
				forumPostVO.setStudentNo(rs.getString("studentNo"));
				forumPostVO.setTitle(rs.getString("title"));
				forumPostVO.setContent(rs.getString("content"));
				forumPostVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumPostVO.setCreateTime(rs.getTimestamp("createTime"));
				list.add(forumPostVO);
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
		ForumPostJDBCDAO dao = new ForumPostJDBCDAO();

		ForumPostVO forumPostVO1 = new ForumPostVO();
		forumPostVO1.setForumTopicNo("1");
		forumPostVO1.setStudentNo("S000001");
		forumPostVO1.setTitle("失物招領");
		forumPostVO1.setContent("座位(2-3)水瓶忘了帶走");
		forumPostVO1.setUpdateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		forumPostVO1.setCreateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		dao.insert(forumPostVO1);

		ForumPostVO forumPostVO2 = new ForumPostVO();
		forumPostVO2.setForumPostNo("3");
		forumPostVO2.setForumTopicNo("3");
		forumPostVO2.setStudentNo("S000003");
		forumPostVO2.setTitle("資料分享");
		forumPostVO2.setContent("請下載第一章講義");
		forumPostVO2.setUpdateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		forumPostVO2.setCreateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		dao.update(forumPostVO2);

    	dao.delete("1");

		ForumPostVO forumPostVO3 = dao.findByPrimaryKey("2");
		System.out.print(forumPostVO3.getForumPostNo() + ",");
		System.out.print(forumPostVO3.getForumTopicNo() + ",");
		System.out.print(forumPostVO3.getStudentNo() + ",");
		System.out.print(forumPostVO3.getTitle() + ",");
		System.out.print(forumPostVO3.getContent() + ",");
		System.out.print(forumPostVO3.getUpdateTime() + ",");
		System.out.print(forumPostVO3.getCreateTime() + ",");
		System.out.println("---------------------");

		List<ForumPostVO> list = dao.getAll();
		for (ForumPostVO aForumPost : list) {
			System.out.print(aForumPost.getForumPostNo() + ",");
			System.out.print(aForumPost.getForumTopicNo() + ",");
			System.out.print(aForumPost.getStudentNo() + ",");
			System.out.print(aForumPost.getTitle() + ",");
			System.out.print(aForumPost.getContent() + ",");
			System.out.print(aForumPost.getUpdateTime() + ",");
			System.out.print(aForumPost.getCreateTime() + ",");
			System.out.println();
		}
	}

}
