package com.videolog.model;

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

public class VideoLogDAO implements VideoLogDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT =
			"INSERT INTO videoLog (videoLogNo, videoNo, watchTime, status) VALUES (VIDEOLOG_SEQ.nextval, ?, ?, ?)";
	private static final String GET_ALL_STMT =
			"SELECT videoLogNo, videoNo, watchTime, status FROM videoLog order by to_number(videoLogNo)";
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, videoLogVO.getVideoNo());
			pstmt.setInt(2, videoLogVO.getWatchTime());
			pstmt.setInt(3, videoLogVO.getStatus());

			pstmt.executeUpdate();
			
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
		}
	}
	
	@Override
	public void update(VideoLogVO videoLogVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, videoLogVO.getVideoNo());
			pstmt.setInt(2, videoLogVO.getWatchTime());
			pstmt.setInt(3, videoLogVO.getStatus());
			pstmt.setString(4, videoLogVO.getVideoLogNo());
			
			pstmt.executeUpdate();

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, videoLogNo);

			pstmt.executeUpdate();

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

			con = ds.getConnection();
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

			con = ds.getConnection();
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
}
