package com.banjitypecourse.model;

import java.sql.*;
import java.util.*;

public class BanjiTypeCourseJDBCDAO implements BanjiTypeCourseDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO BANJITYPECOURSE (BANJITYPECOURSENO, BANJITYPENO, BASICCOURSENO) VALUES(BANJITYPECOURSE_SEQ.NEXTVAL, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT BANJITYPECOURSENO, BANJITYPENO, BASICCOURSENO FROM BANJITYPECOURSE ORDER BY BANJITYPECOURSENO";
	private static final String GET_ONE_STMT = "SELECT BANJITYPECOURSENO, BANJITYPENO, BASICCOURSENO FROM BANJITYPECOURSE WHERE BANJITYPECOURSENO=?";
	private static final String DELETE = "DELETE FROM BANJITYPECOURSE WHERE BANJITYPECOURSENO=?";
	private static final String UPDATE = "UPDATE BANJITYPECOURSE SET BANJITYPENO=?, BASICCOURSENO=? WHERE BANJITYPECOURSENO=?";

	@Override
	public void insert(BanjiTypeCourseVO banjiTypeCourseVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, banjiTypeCourseVO.getBanjiTypeNo());
			pstmt.setString(2, banjiTypeCourseVO.getBasicCourseNo());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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
	public void update(BanjiTypeCourseVO banjiTypeCourseVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, banjiTypeCourseVO.getBanjiTypeNo());
			pstmt.setString(2, banjiTypeCourseVO.getBasicCourseNo());
			pstmt.setString(3, banjiTypeCourseVO.getBanjiTypeCourseNo());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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
	public void delete(String banjiTypeCourseNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, banjiTypeCourseNo);

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
	}

	@Override
	public BanjiTypeCourseVO findByPrimaryKey(String banjiTypeCourseNo) {

		BanjiTypeCourseVO banjiTypeCourseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, banjiTypeCourseNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				banjiTypeCourseVO = new BanjiTypeCourseVO();
				banjiTypeCourseVO.setBanjiTypeCourseNo(rs.getString("banjiTypeCOurseNo"));
				banjiTypeCourseVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiTypeCourseVO.setBasicCourseNo(rs.getString("basicCourseNo"));

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
		return banjiTypeCourseVO;
	}

	@Override
	public List<BanjiTypeCourseVO> getAll() {

		List<BanjiTypeCourseVO> list = new ArrayList<BanjiTypeCourseVO>();
		BanjiTypeCourseVO banjiTypeCourseVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				banjiTypeCourseVO = new BanjiTypeCourseVO();
				banjiTypeCourseVO.setBanjiTypeCourseNo(rs.getString("banjiTypeCourseNo"));
				banjiTypeCourseVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiTypeCourseVO.setBasicCourseNo(rs.getString("basicCourseNo"));
				list.add(banjiTypeCourseVO);
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
		return list;
	}

	public static void main(String[] args) {

		BanjiTypeCourseJDBCDAO dao = new BanjiTypeCourseJDBCDAO();

		// 新增
		BanjiTypeCourseVO banjiTypeCourseVO1 = new BanjiTypeCourseVO();
		banjiTypeCourseVO1.setBanjiTypeNo("BT001");
		banjiTypeCourseVO1.setBasicCourseNo("BC001");
		dao.insert(banjiTypeCourseVO1);

		// 修改
		BanjiTypeCourseVO banjiTypeCourseVO2 = new BanjiTypeCourseVO();
		banjiTypeCourseVO2.setBanjiTypeCourseNo("4");
		banjiTypeCourseVO2.setBanjiTypeNo("BT002");
		banjiTypeCourseVO2.setBasicCourseNo("BC002");
		dao.update(banjiTypeCourseVO2);

		// 刪除
		dao.delete("4");

		// 查詢
		BanjiTypeCourseVO banjiTypeCourseVO3 = dao.findByPrimaryKey("4");
		System.out.println(banjiTypeCourseVO3.getBanjiTypeCourseNo());
		System.out.println(banjiTypeCourseVO3.getBanjiTypeNo());
		System.out.println(banjiTypeCourseVO3.getBasicCourseNo());

		// 查詢
		List<BanjiTypeCourseVO> list = dao.getAll();
		for (BanjiTypeCourseVO abanjiTypeCourse : list) {
			System.out.println(abanjiTypeCourse.getBanjiTypeCourseNo());
			System.out.println(abanjiTypeCourse.getBanjiTypeNo());
			System.out.println(abanjiTypeCourse.getBasicCourseNo());
			System.out.println("===========================");
		}
	}
}