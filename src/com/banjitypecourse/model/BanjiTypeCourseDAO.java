package com.banjitypecourse.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BanjiTypeCourseDAO implements BanjiTypeCourseDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, banjiTypeCourseVO.getBanjiTypeNo());
			pstmt.setString(2, banjiTypeCourseVO.getBasicCourseNo());

			pstmt.executeUpdate();

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, banjiTypeCourseVO.getBanjiTypeNo());
			pstmt.setString(2, banjiTypeCourseVO.getBasicCourseNo());
			pstmt.setString(3, banjiTypeCourseVO.getBanjiTypeCourseNo());

			pstmt.executeUpdate();

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, banjiTypeCourseNo);

			pstmt.executeUpdate();

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, banjiTypeCourseNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				banjiTypeCourseVO = new BanjiTypeCourseVO();
				banjiTypeCourseVO.setBanjiTypeCourseNo(rs.getString("banjiTypeCOurseNo"));
				banjiTypeCourseVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiTypeCourseVO.setBasicCourseNo(rs.getString("basicCourseNo"));

			}

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				banjiTypeCourseVO = new BanjiTypeCourseVO();
				banjiTypeCourseVO.setBanjiTypeCourseNo(rs.getString("banjiTypeCourseNo"));
				banjiTypeCourseVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiTypeCourseVO.setBasicCourseNo(rs.getString("basicCourseNo"));
				list.add(banjiTypeCourseVO);
			}

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
}
