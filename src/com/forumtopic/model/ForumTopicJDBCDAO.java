package com.forumtopic.model;

import java.util.*;

import com.forumpost.model.ForumPostJDBCDAO;
import com.forumpost.model.ForumPostVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ForumTopicJDBCDAO implements ForumTopicDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO forumtopic (forumtopicno, banjino, forumtopicname, content, rule, posttemplete) VALUES (forumtopic_seq.NEXTVAL,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT forumtopicno, banjino, forumtopicname, content, rule, posttemplete FROM forumtopic where isDelete = 0 order by to_number(forumtopicno)";
	private static final String GET_ONE_STMT = "SELECT forumtopicno, banjino, forumtopicname, content, rule, posttemplete FROM forumtopic where forumtopicno = ?";
	private static final String DELETE = "UPDATE forumtopic set isDelete=1 where forumtopicno = ?";
	private static final String UPDATE = "UPDATE forumtopic set banjino=?, forumtopicname=?, content=?, rule=?, posttemplete=? where forumtopicno = ?";
	private static final String GETBYBANJINO = "select * from forumtopic where banjino = ?";

	@Override
	public void insert(ForumTopicVO forumTopicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, forumTopicVO.getBanjiNo());
			pstmt.setString(2, forumTopicVO.getForumTopicName());
			pstmt.setString(3, forumTopicVO.getContent());
			pstmt.setString(4, forumTopicVO.getRule());
			pstmt.setString(5, forumTopicVO.getPostTemplete());

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
	public void update(ForumTopicVO forumTopicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, forumTopicVO.getBanjiNo());
			pstmt.setString(2, forumTopicVO.getForumTopicName());
			pstmt.setString(3, forumTopicVO.getContent());
			pstmt.setString(4, forumTopicVO.getRule());
			pstmt.setString(5, forumTopicVO.getPostTemplete());
			pstmt.setString(6, forumTopicVO.getForumTopicNo());

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
	public void delete(String forumTopicNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, forumTopicNo);

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
	public ForumTopicVO findByPrimaryKey(String forumTopicNo) {

		ForumTopicVO forumTopicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, forumTopicNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumTopicVO = new ForumTopicVO();
				forumTopicVO.setForumTopicNo(rs.getString("forumTopicNo"));
				forumTopicVO.setBanjiNo(rs.getString("banjiNo"));
				forumTopicVO.setForumTopicName(rs.getString("forumTopicName"));
				forumTopicVO.setContent(rs.getString("content"));
				forumTopicVO.setRule(rs.getString("rule"));
				forumTopicVO.setPostTemplete(rs.getString("postTemplete"));

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
		return forumTopicVO;

	}

	@Override
	public List<ForumTopicVO> getAll() {
		List<ForumTopicVO> list = new ArrayList<ForumTopicVO>();
		ForumTopicVO forumTopicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumTopicVO = new ForumTopicVO();
				forumTopicVO.setForumTopicNo(rs.getString("forumTopicNo"));
				forumTopicVO.setBanjiNo(rs.getString("banjiNo"));
				forumTopicVO.setForumTopicName(rs.getString("forumTopicName"));
				forumTopicVO.setContent(rs.getString("content"));
				forumTopicVO.setRule(rs.getString("rule"));
				forumTopicVO.setPostTemplete(rs.getString("postTemplete"));
				list.add(forumTopicVO);
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
		ForumTopicJDBCDAO dao = new ForumTopicJDBCDAO();

		ForumTopicVO forumTopicVO1 = new ForumTopicVO();
		forumTopicVO1.setBanjiNo("B003");
		forumTopicVO1.setForumTopicName("學術版");
		forumTopicVO1.setContent("學員可以在這討論程式相關問題");
		forumTopicVO1.setRule("嚴禁色情暴力言語霸凌等字言");
		forumTopicVO1.setPostTemplete("學術版-發文樣板");
		dao.insert(forumTopicVO1);

		ForumTopicVO forumTopicVO2 = new ForumTopicVO();
		forumTopicVO2.setForumTopicNo("2");
		forumTopicVO2.setBanjiNo("B002");
		forumTopicVO2.setForumTopicName("閒聊版");
		forumTopicVO2.setContent("學員可以在這打屁聊天");
		forumTopicVO2.setRule("嚴禁色情暴力言語霸凌等字言");
		forumTopicVO2.setPostTemplete("閒聊版發文格式");
		dao.update(forumTopicVO2);

		dao.delete("1");

		ForumTopicVO forumTopicVO3 = dao.findByPrimaryKey("2");
		System.out.print(forumTopicVO3.getForumTopicNo() + ",");
		System.out.print(forumTopicVO3.getBanjiNo() + ",");
		System.out.print(forumTopicVO3.getForumTopicName() + ",");
		System.out.print(forumTopicVO3.getContent() + ",");
		System.out.print(forumTopicVO3.getRule() + ",");
		System.out.print(forumTopicVO3.getPostTemplete() + ",");
		System.out.println("---------------------");

		List<ForumTopicVO> list = dao.getAll();
		for (ForumTopicVO aForumTopic : list) {
			System.out.print(aForumTopic.getForumTopicNo() + ",");
			System.out.print(aForumTopic.getBanjiNo() + ",");
			System.out.print(aForumTopic.getForumTopicName() + ",");
			System.out.print(aForumTopic.getContent() + ",");
			System.out.print(aForumTopic.getRule() + ",");
			System.out.print(aForumTopic.getPostTemplete() + ",");
			System.out.println();
		}
	}

	@Override
	public List<ForumTopicVO> getByBanJiNo(String banjiNo) {
		List<ForumTopicVO> list = new ArrayList<ForumTopicVO>();
		ForumTopicVO forumTopicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETBYBANJINO);
			
			pstmt.setString(1, banjiNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumTopicVO = new ForumTopicVO();
				forumTopicVO.setForumTopicNo(rs.getString("forumTopicNo"));
				forumTopicVO.setBanjiNo(rs.getString("banjiNo"));
				forumTopicVO.setForumTopicName(rs.getString("forumTopicName"));
				forumTopicVO.setContent(rs.getString("content"));
				forumTopicVO.setRule(rs.getString("rule"));
				forumTopicVO.setPostTemplete(rs.getString("postTemplete"));
				list.add(forumTopicVO);
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

}
