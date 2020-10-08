package com.attendance.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceJDBCDAO implements AttendanceDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO attendance (attendanceNo,studentNo,timetableNo,time,status,note) VALUES (ATTENDANCE_SEQ.nextval, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT attendanceNo,studentNo,timetableNo,to_char(time,'YYYY-MM-DD HH24:MI:SS') time,status,note FROM attendance order by to_number(attendanceNo)";
	private static final String GET_ONE_STMT = "SELECT attendanceNo,studentNo,timetableNo,to_char(time,'YYYY-MM-DD HH24:MI:SS') time,status,note FROM attendance where attendanceNo = ?";
	private static final String DELETE = "DELETE FROM attendance where attendanceNo = ?";
	private static final String UPDATE = "UPDATE attendance set studentNo=?, timetableNo=?, time=?, status=?, note=? where attendanceNo = ?";

	@Override
	public void insert(AttendanceVO attendanceVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, attendanceVO.getStudentNo());
			pstmt.setString(2, attendanceVO.getTimetableNo());
			pstmt.setTimestamp(3, attendanceVO.getTime());
			pstmt.setInt(4, attendanceVO.getStatus());
			pstmt.setString(5, attendanceVO.getNote());
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
	public void update(AttendanceVO attendanceVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, attendanceVO.getStudentNo());
			pstmt.setString(2, attendanceVO.getTimetableNo());
			pstmt.setTimestamp(3, attendanceVO.getTime());
			pstmt.setInt(4, attendanceVO.getStatus());
			pstmt.setString(5, attendanceVO.getNote());
			pstmt.setString(6, attendanceVO.getAttendanceNo());
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
	public void delete(String attendanceNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, attendanceNo);
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
	public AttendanceVO findByPrimaryKey(String attendanceNo) {
		AttendanceVO attendanceVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, attendanceNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				attendanceVO = new AttendanceVO();
				attendanceVO.setAttendanceNo(rs.getString("attendanceNo"));
				attendanceVO.setStudentNo(rs.getString("studentNo"));
				attendanceVO.setTimetableNo(rs.getString("timetableNo"));
				attendanceVO.setTime(rs.getTimestamp("time"));
				attendanceVO.setStatus(rs.getInt("status"));
				attendanceVO.setNote(rs.getString("note"));
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
		return attendanceVO;
	}

	@Override
	public List<AttendanceVO> getAll() {
		List<AttendanceVO> list = new ArrayList<AttendanceVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AttendanceVO attendanceVO = new AttendanceVO();
				attendanceVO.setAttendanceNo(rs.getString("attendanceNo"));
				attendanceVO.setStudentNo(rs.getString("studentNo"));
				attendanceVO.setTimetableNo(rs.getString("timetableNo"));
				attendanceVO.setTime(rs.getTimestamp("time"));
				attendanceVO.setStatus(rs.getInt("status"));
				attendanceVO.setNote(rs.getString("note"));
				list.add(attendanceVO);
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
		AttendanceJDBCDAO dao = new AttendanceJDBCDAO();

		AttendanceVO attendanceVO1 = new AttendanceVO();
		attendanceVO1.setStudentNo("S000001");
		attendanceVO1.setTimetableNo("TT000001");
		attendanceVO1.setTime(Timestamp.valueOf("2020-07-01 08:55:03"));
		attendanceVO1.setStatus(1);
		attendanceVO1.setNote("");
		dao.insert(attendanceVO1);

		AttendanceVO attendanceVO2 = new AttendanceVO();
		attendanceVO2.setAttendanceNo("2");
		attendanceVO2.setStudentNo("S000002");
		attendanceVO2.setTimetableNo("TT000001");
		attendanceVO2.setTime(null);
		attendanceVO2.setStatus(4);
		attendanceVO2.setNote("補簽時間:" + Timestamp.valueOf("2020-07-01 13:14:15"));
		dao.update(attendanceVO2);

		dao.delete("3");

		AttendanceVO attendanceVO3 = dao.findByPrimaryKey("4");
		if (attendanceVO3 != null) {
			System.out.printf("%s,%s,%s,%s,%s,%s%n", attendanceVO3.getAttendanceNo(), attendanceVO3.getStudentNo(),
					attendanceVO3.getTimetableNo(), attendanceVO3.getTime(), attendanceVO3.getStatus(),
					attendanceVO3.getNote());
			System.out.println("---------------------");
		}

		List<AttendanceVO> list = dao.getAll();
		for (AttendanceVO attendanceVO : list) {
			System.out.printf("%s,%s,%s,%s,%s,%s%n", attendanceVO.getAttendanceNo(), attendanceVO.getStudentNo(),
					attendanceVO.getTimetableNo(), attendanceVO.getTime(), attendanceVO.getStatus(),
					attendanceVO.getNote());
		}
	}
}
