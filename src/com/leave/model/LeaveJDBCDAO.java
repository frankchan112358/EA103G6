package com.leave.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveJDBCDAO implements LeaveDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO Leave (leaveNo,studentNo,timetableNo,type,description,status) VALUES (leave_seq.nextval,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT leaveNo,studentNo,timetableNo,type,description,status FROM Leave ORDER BY to_number(leaveNo)";
	private static final String GET_ONE_STMT = "SELECT leaveNo,studentNo,timetableNo,type,description,status FROM Leave WHERE leaveNo = ?";
	private static final String DELETE = "DELETE FROM Leave WHERE leaveNo = ?";
	private static final String UPDATE = "UPDATE Leave SET studentNo=?,timetableNo=?,type=?,description=?,status=? WHERE leaveNo = ?";

	@Override
	public void insert(LeaveVO leaveVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, leaveVO.getStudentNo());
			pstmt.setString(2, leaveVO.getTimetableNo());
			pstmt.setInt(3, leaveVO.getType());
			pstmt.setString(4, leaveVO.getDescription());
			pstmt.setInt(5, leaveVO.getStatus());
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
	public void update(LeaveVO leaveVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, leaveVO.getStudentNo());
			pstmt.setString(2, leaveVO.getTimetableNo());
			pstmt.setInt(3, leaveVO.getType());
			pstmt.setString(4, leaveVO.getDescription());
			pstmt.setInt(5, leaveVO.getStatus());
			pstmt.setString(6, leaveVO.getLeaveNo());
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
	public void delete(String leaveNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, leaveNo);
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
	public LeaveVO findByPribaryKey(String leaveNo) {
		LeaveVO leaveVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, leaveNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				leaveVO = new LeaveVO();
				leaveVO.setLeaveNo(rs.getString("leaveNo"));
				leaveVO.setStudentNo(rs.getString("studentNo"));
				leaveVO.setTimetableNo(rs.getString("timetableNo"));
				leaveVO.setType(rs.getInt("type"));
				leaveVO.setDescription(rs.getString("description"));
				leaveVO.setStatus(rs.getInt("status"));
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
		return leaveVO;
	}

	@Override
	public List<LeaveVO> getAll() {
		List<LeaveVO> list = new ArrayList<LeaveVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				LeaveVO leaveVO = new LeaveVO();
				leaveVO.setLeaveNo(rs.getString("leaveNo"));
				leaveVO.setStudentNo(rs.getString("studentNo"));
				leaveVO.setTimetableNo(rs.getString("timetableNo"));
				leaveVO.setType(rs.getInt("type"));
				leaveVO.setDescription(rs.getString("description"));
				leaveVO.setStatus(rs.getInt("status"));
				list.add(leaveVO);
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

	@Override
	public void updateStatusWhenTimetableEdit(String timetableNo, Integer status) {
	}

	public static void main(String[] args) {
		LeaveJDBCDAO dao = new LeaveJDBCDAO();

		LeaveVO leaveVO1 = new LeaveVO();
		leaveVO1.setStudentNo("S000001");
		leaveVO1.setTimetableNo("TT000001");
		leaveVO1.setType(1);
		leaveVO1.setDescription("發燒");
		leaveVO1.setStatus(1);
		dao.insert(leaveVO1);

		LeaveVO leaveVO2 = new LeaveVO();
		leaveVO2.setLeaveNo("2");
		leaveVO2.setStudentNo("S000002");
		leaveVO2.setTimetableNo("TT000001");
		leaveVO2.setType(3);
		leaveVO2.setDescription("親人過世，喪假");
		leaveVO2.setStatus(1);
		dao.update(leaveVO2);

		dao.delete("3");

		LeaveVO leaveVO3 = dao.findByPribaryKey("4");
		if (leaveVO3 != null) {
			System.out.printf("%s,%s,%s,%s,%s,%s%n", leaveVO3.getLeaveNo(), leaveVO3.getStudentNo(),
					leaveVO3.getTimetableNo(), leaveVO3.getType(), leaveVO3.getDescription(), leaveVO3.getStatus());
			System.out.println("---------------------");
		}

		List<LeaveVO> list = dao.getAll();
		for (LeaveVO leaveVO : list) {
			System.out.printf("%s,%s,%s,%s,%s,%s%n", leaveVO.getLeaveNo(), leaveVO.getStudentNo(),
					leaveVO.getTimetableNo(), leaveVO.getType(), leaveVO.getDescription(), leaveVO.getStatus());
		}
	}
}
