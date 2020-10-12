package com.basiccourse.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BasicCourseJNDIDAO implements BasicCourseDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	private static final String INSERT_STMT = "INSERT INTO BASICCOURSE (BASICCOURSENO, BASICCOURSENAME, BASICCOURSEINFO, LESSON) VALUES('BC'||LPAD(to_char(BASICCOURSE_SEQ.NEXTVAL), '3', '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT BASICCOURSENO, BASICCOURSENAME, BASICCOURSEINFO, LESSON FROM BASICCOURSE WHERE ISDELETE=0 ORDER BY BASICCOURSENO";
	private static final String GET_ONE_STMT = "SELECT BASICCOURSENO, BASICCOURSENAME, BASICCOURSEINFO, LESSON FROM BASICCOURSE WHERE BASICCOURSENO =?";
	private static final String DELETE = "UPDATE BASICCOURSE SET ISDELETE=1 WHERE BASICCOURSENO =?";
	private static final String UPDATE = "UPDATE BASICCOURSE SET BASICCOURSENAME=?, BASICCOURSEINFO=?, LESSON=? WHERE BASICCOURSENO =?";

	@Override
	public void insert(BasicCourseVO basicCourseVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, basicCourseVO.getBasicCourseName());
			pstmt.setString(2, basicCourseVO.getBasicCourseInfo());
			pstmt.setInt(3, basicCourseVO.getLesson());

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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(BasicCourseVO basicCourseVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, basicCourseVO.getBasicCourseName());
			pstmt.setString(2, basicCourseVO.getBasicCourseInfo());
			pstmt.setInt(3, basicCourseVO.getLesson());
			pstmt.setString(4, basicCourseVO.getBasicCourseNo());

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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String basicCourseNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, basicCourseNo);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public BasicCourseVO findByPrimaryKey(String basicCourseNo) {

		BasicCourseVO basicCourseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, basicCourseNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				basicCourseVO = new BasicCourseVO();
				basicCourseVO.setBasicCourseNo(rs.getString("basicCourseNo"));
				basicCourseVO.setBasicCourseName(rs.getString("basicCourseName"));
				basicCourseVO.setBasicCourseInfo(rs.getString("basicCourseInfo"));
				basicCourseVO.setLesson(rs.getInt("lesson"));

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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return basicCourseVO;
	}

	@Override
	public List<BasicCourseVO> getAll() {

		List<BasicCourseVO> list = new ArrayList<BasicCourseVO>();
		BasicCourseVO basicCourseVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				basicCourseVO = new BasicCourseVO();
				basicCourseVO.setBasicCourseNo(rs.getString("basicCourseNo"));
				basicCourseVO.setBasicCourseName(rs.getString("basicCourseName"));
				basicCourseVO.setBasicCourseInfo(rs.getString("basicCourseInfo"));
				basicCourseVO.setLesson(rs.getInt("lesson"));
				list.add(basicCourseVO);
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
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;
	}
}