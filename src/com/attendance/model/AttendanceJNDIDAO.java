package com.attendance.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AttendanceJNDIDAO implements AttendanceDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO attendance (attendanceNo,studentNo,timetableNo,time,status,note) VALUES (ATTENDANCE_SEQ.nextval, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT attendanceNo,studentNo,timetableNo,to_char(time,'YYYY-MM-DD HH24:MI:SS') time,status,note FROM attendance order by to_number(attendanceNo)";
	private static final String GET_ONE_STMT = "SELECT attendanceNo,studentNo,timetableNo,to_char(time,'YYYY-MM-DD HH24:MI:SS') time,status,note FROM attendance where attendanceNo = ?";
	private static final String DELETE = "DELETE FROM attendance where attendanceNo = ?";
	private static final String UPDATE = "UPDATE attendance set studentNo=?, timetableNo=?, time=?, status=?, note=? where attendanceNo = ?";
	private static final String DELETE_TIMETABLE = "DELETE FROM attendance where timetableNo = ?";
	
	@Override
	public void insert(AttendanceVO attendanceVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, attendanceVO.getStudentNo());
			pstmt.setString(2, attendanceVO.getTimetableNo());
			pstmt.setTimestamp(3, attendanceVO.getTime());
			pstmt.setInt(4, attendanceVO.getStatus());
			pstmt.setString(5, attendanceVO.getNote());
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
	public void update(AttendanceVO attendanceVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, attendanceVO.getStudentNo());
			pstmt.setString(2, attendanceVO.getTimetableNo());
			pstmt.setTimestamp(3, attendanceVO.getTime());
			pstmt.setInt(4, attendanceVO.getStatus());
			pstmt.setString(5, attendanceVO.getNote());
			pstmt.setString(6, attendanceVO.getAttendanceNo());
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
	public void delete(String attendanceNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, attendanceNo);
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
	public AttendanceVO findByPrimaryKey(String attendanceNo) {
		AttendanceVO attendanceVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
	
	public void deleteWithTimetableNo(String timetableNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_TIMETABLE);
			pstmt.setString(1, timetableNo);
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
}
