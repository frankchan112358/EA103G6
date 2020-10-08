package com.healthlog.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class HealthLogJNDIDAO implements HealthLogDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO HealthLog (healthLogNo,studentNo,status,healthLogDate) VALUES (healthlog_seq.nextval,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT healthLogNo,studentNo,status,healthLogDate FROM HealthLog ORDER BY to_number(healthLogNo)";
	private static final String GET_ONE_STMT = "SELECT healthLogNo,studentNo,status,healthLogDate FROM HealthLog WHERE healthLogNo = ?";
	private static final String DELETE = "DELETE FROM HealthLog WHERE healthLogNo = ?";
	private static final String UPDATE = "UPDATE HealthLog SET studentNo=?,status=?,healthLogDate=? WHERE healthLogNo = ?";

	@Override
	public void insert(HealthLogVO healthLogVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, healthLogVO.getStudentNo());
			pstmt.setInt(2, healthLogVO.getStatus());
			pstmt.setDate(3, healthLogVO.getHealthLogDate());
			pstmt.execute();
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
	public void update(HealthLogVO healthLogVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, healthLogVO.getStudentNo());
			pstmt.setInt(2, healthLogVO.getStatus());
			pstmt.setDate(3, healthLogVO.getHealthLogDate());
			pstmt.setString(4, healthLogVO.getHealthLogNo());
			pstmt.execute();
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
	public void delete(String healthLogNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, healthLogNo);
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
	public HealthLogVO findByPrimaryKey(String healthLogNo) {
		HealthLogVO healthLogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, healthLogNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				healthLogVO = new HealthLogVO();
				healthLogVO.setHealthLogNo(rs.getString("healthLogNo"));
				healthLogVO.setStudentNo(rs.getString("studentNo"));
				healthLogVO.setStatus(rs.getInt("status"));
				healthLogVO.setHealthLogDate(rs.getDate("healthLogDate"));
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
		return healthLogVO;
	}

	@Override
	public List<HealthLogVO> getAll() {
		List<HealthLogVO> list = new ArrayList<HealthLogVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				HealthLogVO healthLogVO = new HealthLogVO();
				healthLogVO.setHealthLogNo(rs.getString("healthLogNo"));
				healthLogVO.setStudentNo(rs.getString("studentNo"));
				healthLogVO.setStatus(rs.getInt("status"));
				healthLogVO.setHealthLogDate(rs.getDate("healthLogDate"));
				list.add(healthLogVO);
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
