package com.leave.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class LeaveJNDIDAO implements LeaveDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, leaveVO.getStudentNo());
			pstmt.setString(2, leaveVO.getTimetableNo());
			pstmt.setInt(3, leaveVO.getType());
			pstmt.setString(4, leaveVO.getDescription());
			pstmt.setInt(5, leaveVO.getStatus());
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
	public void update(LeaveVO leaveVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, leaveVO.getStudentNo());
			pstmt.setString(2, leaveVO.getTimetableNo());
			pstmt.setInt(3, leaveVO.getType());
			pstmt.setString(4, leaveVO.getDescription());
			pstmt.setInt(5, leaveVO.getStatus());
			pstmt.setString(6, leaveVO.getLeaveNo());
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
	public void delete(String leaveNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, leaveNo);
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
	public LeaveVO findByPribaryKey(String leaveNo) {
		LeaveVO leaveVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
