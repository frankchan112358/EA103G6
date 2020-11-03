package com.teachingfile.model;

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

public class TeachingFileDAO implements TeachingFileDAO_interface{
	
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
			"INSERT INTO teachingFile (teachingFileNo, courseNo, teachingFileName, teachingFile) VALUES (TEACHINGFILE_SEQ.nextval, ?, ?, ?)";
	private static final String GET_ALL_STMT =
			"SELECT teachingFileNo, courseNo, teachingFileName, teachingFile FROM teachingFile order by to_number(teachingFileNo)";
	private static final String GET_ONE_STMT = 
			"SELECT teachingFileNo, courseNo, teachingFileName, teachingFile FROM teachingFile where teachingFileNo = ?";
	private static final String DELETE = 
			"DELETE FROM teachingFile where teachingFileNo = ?";
	private static final String UPDATE = 
			"UPDATE teachingFile set courseNo=?, teachingFileName=?, teachingFile=? where teachingFileNo = ?";
	private static final String UPDATENOFILE = 
			"UPDATE teachingFile set courseNo=?, teachingFileName=? where teachingFileNo = ?";

	@Override
	public void insert(TeachingFileVO teachingFileVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, teachingFileVO.getCourseNo());
			pstmt.setString(2, teachingFileVO.getTeachingFileName());
			pstmt.setBytes(3, teachingFileVO.getTeachingFile());

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
	public void update(TeachingFileVO teachingFileVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, teachingFileVO.getCourseNo());
			pstmt.setString(2, teachingFileVO.getTeachingFileName());
			pstmt.setBytes(3, teachingFileVO.getTeachingFile());
			pstmt.setString(4, teachingFileVO.getTeachingFileNo());
			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
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
	public void updateNoFile(TeachingFileVO teachingFileVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATENOFILE);
			
			pstmt.setString(1, teachingFileVO.getCourseNo());
			pstmt.setString(2, teachingFileVO.getTeachingFileName());
			pstmt.setString(3, teachingFileVO.getTeachingFileNo());
			
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
	public void delete(String teachingFileNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, teachingFileNo);

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
	public TeachingFileVO findByPrimaryKey(String teachingFileNo) {
		
		TeachingFileVO teachingFileVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, teachingFileNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				teachingFileVO = new TeachingFileVO();
				teachingFileVO.setTeachingFileNo(rs.getString("teachingFileNo"));
				teachingFileVO.setCourseNo(rs.getString("courseNo"));
				teachingFileVO.setTeachingFileName(rs.getString("teachingFileName"));
				teachingFileVO.setTeachingFile(rs.getBytes("teachingFile"));				
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				teachingFileVO = new TeachingFileVO();
				teachingFileVO.setTeachingFileNo(rs.getString("teachingFileNo"));
				teachingFileVO.setCourseNo(rs.getString("CourseNo"));
				teachingFileVO.setTeachingFileName(rs.getString("teachingFileName"));
				teachingFileVO.setTeachingFile(rs.getBytes("teachingFile"));
				list.add(teachingFileVO); 
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
