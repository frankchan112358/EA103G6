package com.video.model;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;

public class VideoDAO implements VideoDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO VIDEO (videoNo, timetableNo, videoName) VALUES (VIDEO_SEQ.nextval, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT videoNo, timetableNo, videoName, video FROM video order by to_number(videoNo)";
	private static final String GET_ONE_STMT = "SELECT videoNo, timetableNo, videoName, video FROM video where videoNo = ?";
	private static final String DELETE = "DELETE FROM video where videoNo = ?";
	private static final String DELETE_VIDEOLOG = "DELETE FROM videoLog where videoNo = ?";
	private static final String UPDATE = "UPDATE video set timetableNo=?, videoName=? where videoNo = ?";

	@Override
	public void insert(VideoVO videoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, videoVO.getTimetableNo());
			pstmt.setString(2, videoVO.getVideoName());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void update(VideoVO videoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, videoVO.getTimetableNo());
			pstmt.setString(2, videoVO.getVideoName());
			pstmt.setString(3, videoVO.getVideoNo());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void delete(String videoNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			System.out.println("DAO - 108 + videoNo" + videoNo);
			con = ds.getConnection();
			deleteVideoLog(videoNo);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, videoNo);

			pstmt.executeUpdate();
			System.out.println("DAO line115 OK");

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
	public VideoVO findByPrimaryKey(String videoNo) {

		VideoVO videoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, videoNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoVO = new VideoVO();
				videoVO.setVideoNo(rs.getString("videoNo"));
				videoVO.setTimetableNo(rs.getString("TimetableNo"));
				videoVO.setVideoName(rs.getString("videoName"));
				videoVO.setVideo(rs.getBytes("video"));
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
		return videoVO;
	}

	@Override
	public List<VideoVO> getAll() {
		List<VideoVO> list = new ArrayList<VideoVO>();
		VideoVO videoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoVO = new VideoVO();
				videoVO.setVideoNo(rs.getString("videoNo"));
				videoVO.setTimetableNo(rs.getString("timetableNo"));
				videoVO.setVideoName(rs.getString("videoName"));
				videoVO.setVideo(rs.getBytes("video"));
				list.add(videoVO);
			}

		} catch (SQLException se) {
			se.getStackTrace();
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

	public static void insert2(VideoVO videoVO, Part video, String filePath) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			con.setAutoCommit(false);
			String cols[] = { "VIDEONO" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, videoVO.getTimetableNo());
			pstmt.setString(2, videoVO.getVideoName());
			pstmt.executeUpdate();
			con.commit();

			String next_videono = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_videono = rs.getString(1);
				System.out.println("自增主鍵值= " + next_videono + "(剛新增成功的影片編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();

			insertFile(filePath, video, next_videono);

		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
			throw new RuntimeException("A database error occured." + se.getMessage());
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
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

	private static void insertFile(String filePath, Part video, String next_videono) throws IOException {
		File fDestination = new File(filePath);
		if (!fDestination.exists())
			fDestination.mkdirs();

		File f = new File(fDestination, next_videono + ".mp4");
		video.write(f.toString());
	}

	private void deleteVideoLog(String videoNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_VIDEOLOG);
			pstmt.setString(1, videoNo);

			pstmt.executeUpdate();
			System.out.println("DAO line323 OK");

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