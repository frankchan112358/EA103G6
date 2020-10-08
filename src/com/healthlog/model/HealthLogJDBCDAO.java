package com.healthlog.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HealthLogJDBCDAO implements HealthLogDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, healthLogVO.getStudentNo());
			pstmt.setInt(2, healthLogVO.getStatus());
			pstmt.setDate(3, healthLogVO.getHealthLogDate());
			pstmt.execute();
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
	public void update(HealthLogVO healthLogVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, healthLogVO.getStudentNo());
			pstmt.setInt(2, healthLogVO.getStatus());
			pstmt.setDate(3, healthLogVO.getHealthLogDate());
			pstmt.setString(4, healthLogVO.getHealthLogNo());
			pstmt.execute();
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
	public void delete(String healthLogNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, healthLogNo);
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
	public HealthLogVO findByPrimaryKey(String healthLogNo) {
		HealthLogVO healthLogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Clouldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Coulen't load database driver. " + e.getMessage());
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
		HealthLogJDBCDAO dao = new HealthLogJDBCDAO();

		HealthLogVO healthLogVO1 = new HealthLogVO();
		healthLogVO1.setStudentNo("S000001");
		healthLogVO1.setStatus(1);
		healthLogVO1.setHealthLogDate(Date.valueOf("2020-09-26"));
		dao.insert(healthLogVO1);

		HealthLogVO healthLogVO2 = new HealthLogVO();
		healthLogVO2.setHealthLogNo("2");
		healthLogVO2.setStudentNo("S000002");
		healthLogVO2.setStatus(0);
		healthLogVO2.setHealthLogDate(Date.valueOf("2020-09-25"));
		dao.update(healthLogVO2);

		dao.delete("3");

		HealthLogVO healthLogVO3 = dao.findByPrimaryKey("4");
		if (healthLogVO3 != null) {
			System.out.printf("%s,%s,%s,%s%n", healthLogVO3.getHealthLogNo(), healthLogVO3.getStudentNo(),
					healthLogVO3.getStatus(), healthLogVO3.getHealthLogDate());
			System.out.println("---------------------");
		}

		List<HealthLogVO> list = dao.getAll();
		for (HealthLogVO healthLogVO : list) {
			System.out.printf("%s,%s,%s,%s%n", healthLogVO.getHealthLogNo(), healthLogVO.getStudentNo(),
					healthLogVO.getStatus(), healthLogVO.getHealthLogDate());
		}
	}
}
