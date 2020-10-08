package com.classroom.model;

import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ClassroomJNDIDAO implements ClassroomDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 新增
	private static final String INSERT_STMT = "INSERT INTO CLASSROOM(CLASSROOMNO,CLASSROOMNAME,NUMBEROFSTUDENT)VALUES(CLASSROOM_SEQ.NEXTVAL,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT CLASSROOMNO,CLASSROOMNAME,NUMBEROFSTUDENT FROM CLASSROOM WHERE ISDELETE=0 ORDER BY CLASSROOMNO";
	// 查單個
	private static final String GET_ONE_STMT = "SELECT CLASSROOMNO,CLASSROOMNAME,NUMBEROFSTUDENT FROM CLASSROOM WHERE CLASSROOMNO =?";
	// 更新刪除狀態
	private static final String DELETE = "UPDATE CLASSROOM SET ISDELETE=1 WHERE CLASSROOMNO=?";
	// 修改
	private static final String UPDATE = "UPDATE CLASSROOM SET CLASSROOMNAME=?,NUMBEROFSTUDENT=? WHERE CLASSROOMNO=?";

	@Override
	public void insert(ClassroomVO classroomVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, classroomVO.getClassroomName());
			pstmt.setInt(2, classroomVO.getNumberOfStudent());

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
	public void update(ClassroomVO classroomVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, classroomVO.getClassroomName());
			pstmt.setInt(2, classroomVO.getNumberOfStudent());
			pstmt.setString(3, classroomVO.getClassroomNo());

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
	public void delete(String classroomNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, classroomNo);

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
	public ClassroomVO findByPrimaryKey(String classroomNo) {
		ClassroomVO classroomVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, classroomNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				classroomVO = new ClassroomVO();
				classroomVO.setClassroomNo(rs.getString("classroomNo"));
				classroomVO.setClassroomName(rs.getString("classroomName"));
				classroomVO.setNumberOfStudent(rs.getInt("numberOfStudent"));
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
		return classroomVO;
	}

	@Override
	public List<ClassroomVO> getAll() {
		List<ClassroomVO> list = new ArrayList<ClassroomVO>();
		ClassroomVO classroomVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				classroomVO = new ClassroomVO();
				classroomVO.setClassroomNo(rs.getString("classroomNo"));
				classroomVO.setClassroomName(rs.getString("classroomName"));
				classroomVO.setNumberOfStudent(rs.getInt("numberOfStudent"));

				list.add(classroomVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
