package com.teachingfile.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeachingFileJDBCDAO implements TeachingFileDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";
	
	private static final String INSERT_STMT =
			"INSERT INTO teachingFile (teachingFileNo, timetableNo, teachingFileName, teachingFile) VALUES (TEACHINGFILE_SEQ.nextval, ?, ?, ?)";
	private static final String GET_ALL_STMT =
			"SELECT teachingFileNo, timetableNo, teachingFileName, teachingFile FROM teachingFile order by teachingFileNo";
	private static final String GET_ONE_STMT = 
			"SELECT teachingFileNo, timetableNo, teachingFileName, teachingFile FROM teachingFile where teachingFileNo = ?";
	private static final String DELETE = 
			"DELETE FROM teachingFile where teachingFileNo = ?";
	private static final String UPDATE = 
			"UPDATE teachingFile set timetableNo=?, teachingFileName=?, teachingFile=? where teachingFileNo = ?";

	@Override
	public void insert(TeachingFileVO teachingFileVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, teachingFileVO.getTimetableNo());
			pstmt.setString(2, teachingFileVO.getTeachingFileName());
			pstmt.setBytes(3, teachingFileVO.getTeachingFile());

			pstmt.executeUpdate();
			
		}catch (ClassNotFoundException e){
			throw new RuntimeException("Couldn't load database driver."
					+ e.getMessage());
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured." 
					+ se.getMessage());
		}finally {
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
	public void update(TeachingFileVO teachingFileVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, teachingFileVO.getTimetableNo());
			pstmt.setString(2, teachingFileVO.getTeachingFileName());
			pstmt.setBytes(3, teachingFileVO.getTeachingFile());
			pstmt.setString(4, teachingFileVO.getTeachingFileNo());
			
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." 
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured."
					+ se.getMessage());
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
	public void delete(String teachingFileNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, teachingFileNo);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public TeachingFileVO findByPrimaryKey(String teachingFileNo) {
		
		TeachingFileVO teachingFileVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, teachingFileNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				teachingFileVO = new TeachingFileVO();
				teachingFileVO.setTeachingFileNo(rs.getString("teachingFileNo"));
				teachingFileVO.setTimetableNo(rs.getString("TimetableNo"));
				teachingFileVO.setTeachingFileName(rs.getString("teachingFileName"));
				teachingFileVO.setTeachingFile(rs.getBytes("teachingFile"));				
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
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
		return teachingFileVO;
	}

	@Override
	public List<TeachingFileVO> getAll() {
		List<TeachingFileVO> list = new ArrayList<TeachingFileVO>();
		TeachingFileVO teachingFileVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				teachingFileVO = new TeachingFileVO();
				teachingFileVO.setTeachingFileNo(rs.getString("teachingFileNo"));
				teachingFileVO.setTimetableNo(rs.getString("timetableNo"));
				teachingFileVO.setTeachingFileName(rs.getString("teachingFileName"));
				teachingFileVO.setTeachingFile(rs.getBytes("teachingFile"));
				list.add(teachingFileVO); 
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
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
	
	public static void main(String[] args) {

		TeachingFileJDBCDAO dao = new TeachingFileJDBCDAO();

		// 新增
		TeachingFileVO teachingFileVO1 = new TeachingFileVO();
		teachingFileVO1.setTimetableNo("TT000001");
		teachingFileVO1.setTeachingFileName("JavaScript講義");
		dao.insert(teachingFileVO1);

		// 修改
		TeachingFileVO teachingFileVO2 = new TeachingFileVO();
		teachingFileVO2.setTeachingFileNo("2");
		teachingFileVO2.setTimetableNo("TT000001");
		teachingFileVO2.setTeachingFileName("JavaScript講義");
		dao.update(teachingFileVO2);

		// 刪除
		dao.delete("3");

		// 查詢
		TeachingFileVO teachingFileVO3 = dao.findByPrimaryKey("4");
		System.out.print(teachingFileVO3);
		System.out.println("---------------------");
		System.out.print(teachingFileVO3.getTeachingFileNo() + ",");
		System.out.print(teachingFileVO3.getTimetableNo() + ",");
		System.out.print(teachingFileVO3.getTeachingFileName() + ",");
		System.out.print(teachingFileVO3.getTeachingFile() + ",");
		System.out.println("---------------------");

		// 查詢
		List<TeachingFileVO> list = dao.getAll();
		for (TeachingFileVO teachingFileVO4 : list) {
			System.out.print(teachingFileVO4.getTeachingFileNo() + ",");
			System.out.print(teachingFileVO4.getTimetableNo() + ",");
			System.out.print(teachingFileVO4.getTeachingFileName() + ",");
			System.out.print(teachingFileVO4.getTeachingFile() + ",");
			System.out.println("------");
			System.out.println(teachingFileVO4);

		}
	}
}
