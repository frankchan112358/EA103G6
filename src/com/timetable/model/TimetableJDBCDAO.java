package com.timetable.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimetableJDBCDAO implements TimetableDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO TIMETABLE (timetableNo, courseNo, classroomNo, timetablePeriod, timetableDate, teachingLog) VALUES ('TT'||LPAD(to_char(TIMETABLE_SEQ.NEXTVAL), '6', '0'), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT timetableNo, courseNo, classroomNo, timetablePeriod, timetableDate, teachingLog FROM timetable where isDelete=0 order by timetableNo ";
	private static final String GET_ONE_STMT = "SELECT timetableNo, courseNo, classroomNo, timetablePeriod, timetableDate, teachingLog FROM timetable where timetableNo = ? and isDelete=0";
	private static final String DELETE = "UPDATE timetable set isDelete = 1 where timetableNo=?";
	private static final String UPDATE = "UPDATE timetable set courseNo=?, classroomNo=?, timetablePeriod=?, timetableDate=?, teachingLog=? where timetableNo=? ";

	@Override
	public void insert(TimetableVO timetableVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, timetableVO.getCourseNo());
			pstmt.setString(2, timetableVO.getClassroomNo());
			pstmt.setInt(3, timetableVO.getTimetablePeriod());
			pstmt.setDate(4, timetableVO.getTimetableDate());;
			pstmt.setString(5, timetableVO.getTeachingLog());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, timetableVO.getCourseNo());
			pstmt.setString(2, timetableVO.getClassroomNo());
			pstmt.setInt(3, timetableVO.getTimetablePeriod());
			pstmt.setDate(4, timetableVO.getTimetableDate());
			pstmt.setString(5, timetableVO.getTeachingLog());
			pstmt.setString(6, timetableVO.getTimetableNo());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, timetableNo);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." 
					+ e.getMessage());
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

	public static void main(String[] args) throws IOException {
		
		TimetableJDBCDAO dao = new TimetableJDBCDAO();
		
		//新增
		TimetableVO timetableVO1 = new TimetableVO();
		timetableVO1.setCourseNo("C001");
		timetableVO1.setClassroomNo("1");
		timetableVO1.setTimetablePeriod(2);
		timetableVO1.setTimetableDate(Date.valueOf("2015-03-31"));
		timetableVO1.setTeachingLog("C:\\20201112日誌.txt");
		dao.insert(timetableVO1);
		
		//修改
		TimetableVO timetableVO2 = new TimetableVO();
		timetableVO2.setTimetableNo("TT000003");
		timetableVO2.setCourseNo("C001");
		timetableVO2.setClassroomNo("2");
		timetableVO2.setTimetablePeriod(2);
		timetableVO2.setTimetableDate(Date.valueOf("2015-03-31"));
		timetableVO2.setTeachingLog("Hello everybody, my name is Ivy.");
		dao.update(timetableVO2);
		
		//刪除
		dao.delete("TT000003");
		
		//查詢
		TimetableVO timetableVO3 = dao.findByPrimaryKey("TT000002");
		System.out.println(timetableVO3.getTimetableNo());
		System.out.println(timetableVO3.getCourseNo());
		System.out.println(timetableVO3.getClassroomNo());
		System.out.println(timetableVO3.getTimetablePeriod());
		System.out.println(timetableVO3.getTimetableDate());
		System.out.println(timetableVO3.getTeachingLog());
		
		//查詢
		List<TimetableVO> list = dao.getAll();
		for (TimetableVO timetableVO : list) {
			System.out.println(timetableVO.getTimetableNo());
			System.out.println(timetableVO.getCourseNo());
			System.out.println(timetableVO.getClassroomNo());
			System.out.println(timetableVO.getTimetablePeriod());
			System.out.println(timetableVO.getTimetableDate());
			System.out.println(timetableVO.getTeachingLog());		
			System.out.println("-------");
		}
	}
	
}
