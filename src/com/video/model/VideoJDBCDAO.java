package com.video.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

public class VideoJDBCDAO implements VideoDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, videoVO.getTimetableNo());
			pstmt.setString(2, videoVO.getVideoName());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, videoVO.getTimetableNo());
			pstmt.setString(2, videoVO.getVideoName());
			pstmt.setString(3, videoVO.getVideoNo());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt = con.prepareStatement(DELETE_VIDEOLOG);

			pstmt.setString(1, videoNo);

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
	public VideoVO findByPrimaryKey(String videoNo) {

		VideoVO videoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public void insert2(VideoVO videoVO, Part video, String realPath) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			String cols[] = {"VIDEONO"};
			pstmt = con.prepareStatement(INSERT_STMT , cols);			

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

//			File fDestination = new File(realPath);
//			if (!fDestination.exists())
//				fDestination.mkdirs();
//
//			File f = new File(fDestination, next_videono + ".mp4");
//			video.write(f.toString());
		} catch (ClassNotFoundException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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

	public static void main(String[] args) {

		VideoJDBCDAO dao = new VideoJDBCDAO();

//		// 新增
//		VideoVO videoVO1 = new VideoVO();
//		videoVO1.setTimetableNo("TT000001");
//		videoVO1.setVideoName("2020-01-02 早上");
//		dao.insert(videoVO1);
//
//		// 修改
//		VideoVO videoVO2 = new VideoVO();
//		videoVO2.setVideoNo("4");
//		videoVO2.setTimetableNo("TT000001");
//		videoVO2.setVideoName("2020-12-02 下午");
//		dao.update(videoVO2);
//
//		// 刪除
//		dao.delete("1");
//
//		// 查詢
//		VideoVO videoVO3 = dao.findByPrimaryKey("44");
//		System.out.print(videoVO3 );
//		System.out.println("---------------------");
//		System.out.print(videoVO3.getVideoNo() );
//		System.out.print(videoVO3.getTimetableNo() );
//		System.out.print(videoVO3.getVideoName() );
//		System.out.print(videoVO3.getVideo() );
//		System.out.println("---------------------");
//
//		// 查詢
//		List<VideoVO> list = dao.getAll();
//		for (VideoVO videoVO4 : list) {
//			System.out.print(videoVO4.getVideoNo() + ",");
//			System.out.print(videoVO4.getTimetableNo() + ",");
//			System.out.print(videoVO4.getVideoName() + ",");
//			System.out.print(videoVO4.getVideo() + ",");
//			System.out.println("------");
//			System.out.println(videoVO4);
//		}

//		 新增
//		VideoVO videoVO5 = new VideoVO();
//		videoVO5.setTimetableNo("TT000001");
//		videoVO5.setVideoName("2020-01-02 早上");
//		dao.insert2(videoVO5, null, "C://");
	}
}
