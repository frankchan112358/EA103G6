package com.timetable.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TimetableDAO implements TimetableDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO TIMETABLE (timetableNo, courseNo, classroomNo, timetablePeriod, timetableDate, teachingLog) VALUES ('TT'||LPAD(to_char(TIMETABLE_SEQ.NEXTVAL), '6', '0'), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT timetableNo, courseNo, classroomNo, timetablePeriod, timetableDate, teachingLog FROM timetable where isDelete=0 order by timetableDate, timetablePeriod ";
	private static final String GET_ONE_STMT = "SELECT timetableNo, courseNo, classroomNo, timetablePeriod, timetableDate, teachingLog FROM timetable where timetableNo = ?";
	private static final String DELETE = "UPDATE timetable set isDelete = 1 where timetableNo=?";
	private static final String UPDATE = "UPDATE timetable set courseNo=?, classroomNo=?, timetablePeriod=?, timetableDate=?, teachingLog=? where timetableNo=? ";

	@Override
	public void insert(TimetableVO timetableVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, timetableVO.getCourseNo());
			pstmt.setString(2, timetableVO.getClassroomNo());
			pstmt.setInt(3, timetableVO.getTimetablePeriod());
			pstmt.setDate(4, timetableVO.getTimetableDate());;
			pstmt.setString(5, timetableVO.getTeachingLog());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("Couldn't load database driver." + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(TimetableVO timetableVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, timetableVO.getCourseNo());
			pstmt.setString(2, timetableVO.getClassroomNo());
			pstmt.setInt(3, timetableVO.getTimetablePeriod());
			pstmt.setDate(4, timetableVO.getTimetableDate());
			pstmt.setString(5, timetableVO.getTeachingLog());
			pstmt.setString(6, timetableVO.getTimetableNo());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("Couldn't load database driver." + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String timetableNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

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

	@Override
	public TimetableVO findByPrimaryKey(String timetableNo) {

		TimetableVO timetableVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, timetableNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				timetableVO = new TimetableVO();
				timetableVO.setTimetableNo(rs.getString("timetableNo"));
				timetableVO.setCourseNo(rs.getString("courseNo"));
				timetableVO.setClassroomNo(rs.getString("classroomNo"));
				timetableVO.setTimetablePeriod(rs.getInt("timetablePeriod"));
				timetableVO.setTimetableDate(rs.getDate("timetableDate"));
				timetableVO.setTeachingLog(rs.getString("teachingLog"));
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
		return timetableVO;
	}

	@Override
	public List<TimetableVO> getAll() {
		List<TimetableVO> list = new ArrayList<TimetableVO>();
		TimetableVO timetableVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				timetableVO = new TimetableVO();
				timetableVO.setTimetableNo(rs.getString("timetableNo"));
				timetableVO.setCourseNo(rs.getString("courseNo"));
				timetableVO.setClassroomNo(rs.getString("classroomNo"));
				timetableVO.setTimetablePeriod(rs.getInt("timetablePeriod"));
				timetableVO.setTimetableDate(rs.getDate("timetableDate"));
				timetableVO.setTeachingLog(rs.getString("teachingLog"));
				
				list.add(timetableVO);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured." 
					+ se.getMessage());
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
