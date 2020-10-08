package com.videolog.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VideoLogJDBCDAO implements VideoLogDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";
	
	private static final String INSERT_STMT =
			"INSERT INTO videoLog (videoLogNo, videoNo, watchTime, status) VALUES (VIDEOLOG_SEQ.nextval, ?, ?, ?)";
	private static final String GET_ALL_STMT =
			"SELECT videoLogNo, videoNo, watchTime, status FROM videoLog order by videoLogNo";
	private static final String GET_ONE_STMT = 
			"SELECT videoLogNo, videoNo, watchTime, status FROM videoLog where videoLogNo = ?";
	private static final String DELETE = 
			"DELETE FROM videoLog where videoLogNo = ?";
	private static final String UPDATE = 
			"UPDATE videoLog set videoNo=?, watchTime=?, status=? where videoLogNo = ?";
	
	@Override
	public void insert(VideoLogVO videoLogVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, videoLogVO.getVideoNo());
			pstmt.setInt(2, videoLogVO.getWatchTime());
			pstmt.setInt(3, videoLogVO.getStatus());

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
	public void update(VideoLogVO videoLogVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, videoLogVO.getVideoNo());
			pstmt.setInt(2, videoLogVO.getWatchTime());
			pstmt.setInt(3, videoLogVO.getStatus());
			pstmt.setString(4, videoLogVO.getVideoLogNo());
			
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
	public void delete(String videoLogNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, videoLogNo);

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
	public VideoLogVO findByPrimaryKey(String videoLogNo) {
		
		VideoLogVO videoLogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, videoLogNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoLogVO = new VideoLogVO();
				videoLogVO.setVideoLogNo(rs.getString("videoLogNo"));
				videoLogVO.setVideoNo(rs.getString("videoNo"));
				videoLogVO.setWatchTime(rs.getInt("watchTime"));
				videoLogVO.setStatus(rs.getInt("status"));				
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
		return videoLogVO;
	}

	@Override
	public List<VideoLogVO> getAll() {
		List<VideoLogVO> list = new ArrayList<VideoLogVO>();
		VideoLogVO videoLogVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoLogVO = new VideoLogVO();
				videoLogVO.setVideoLogNo(rs.getString("videoLogNo"));
				videoLogVO.setVideoNo(rs.getString("videoNo"));
				videoLogVO.setWatchTime(rs.getInt("watchTime"));
				videoLogVO.setStatus(rs.getInt("status"));	
				list.add(videoLogVO); 
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
	
//	public static void main(String[] args) {
//
//		VideoLogJDBCDAO dao = new VideoLogJDBCDAO();
//
//		// 新增
//		VideoLogVO videoLogVO1 = new VideoLogVO();
//		videoLogVO1.setVideoNo("2");
//		videoLogVO1.setWatchTime(130);
//		videoLogVO1.setStatus(1);
//		dao.insert(videoLogVO1);
//
//		// 修改
//		VideoLogVO videoLogVO1 = new VideoLogVO();
//		videoLogVO1.setVideoNo("2");
//		videoLogVO1.setWatchTime(200);
//		videoLogVO1.setStatus(0);
//		videoLogVO1.setVideoLogNo("2");
//		dao.update(videoLogVO1);
//
//		// 刪除
//		dao.delete("3");
//
//		// 查詢
//		VideoLogVO videoLogVO1 = dao.findByPrimaryKey("4");
//		System.out.print(videoLogVO1);
//		System.out.println("---------------------");
//		System.out.print(videoLogVO1.getVideoLogNo() + ",");
//		System.out.print(videoLogVO1.getVideoNo() + ",");
//		System.out.print(videoLogVO1.getWatchTime() + ",");
//		System.out.print(videoLogVO1.getStatus() + ",");
//		System.out.println("---------------------");
//
//		// 查詢
//		List<VideoLogVO> list = dao.getAll();
//		for (VideoLogVO videoLogVO1 : list) {
//			System.out.print(videoLogVO1);
//			System.out.println("---------------------");
//			System.out.print(videoLogVO1.getVideoLogNo() + ",");
//			System.out.print(videoLogVO1.getVideoNo() + ",");
//			System.out.print(videoLogVO1.getWatchTime() + ",");
//			System.out.print(videoLogVO1.getStatus() + ",");
//			System.out.println("---------------------");
//		}
//	}
}
