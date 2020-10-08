package com.student.model;

import java.util.*;
import java.io.InputStream;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.student.model.StudentVO;

public class StudentDAO implements StudentDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO student (studentNO,Userno,Banjino,Studentname,Faceid,Face,Studentdescription,Studentstatus) VALUES ('S'||LPAD(to_char(STUDENTNO_SEQ.NEXTVAL), '6', '0'), ?, ?, ?, ?, ?, ?,?)";
	private static final String DELETE = "UPDATE student SET Studentstatus=3 where studentno = ?";
	private static final String GET_ONE_STMT = "SELECT STUDENTNO,USERNO,BANJINO,Studentname,faceid,face,studentdescription,studentstatus FROM  student where studentno = ? ";
	private static final String GET_PIC = "SELECT PHOTO FROM WJLUSER WHERE PHOTO IS NOT NULL AND USERNO=";
	private static final String GET_ALL_STMT = "SELECT studentno,userno,banjino,studentname,faceid,face,studentdescription,studentstatus FROM student WHERE studentstatus=1 ";
	private static final String UPDATE = "UPDATE student set userno=?,banjino=?,studentname=?, faceid=?, face=?, studentdescription=?, studentstatus=? where studentno = ?";
	private static final String UPDATE_NOPIC = "UPDATE student set userno=?,banjino=?,studentname=?, faceid=?, studentdescription=?, studentstatus=? where studentno = ?";
	
	@Override
	public StudentVO findByPrimaryKey(String studentno) {
		StudentVO studentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, studentno);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				studentVO = new StudentVO();
				studentVO.setStudentno(rs.getString("studentNo"));
				studentVO.setUserno(rs.getString("userno"));
				studentVO.setBanjino(rs.getString("banjino"));
				studentVO.setStudentname(rs.getString("studentname"));
				studentVO.setFaceid(rs.getString("faceid"));
				studentVO.setStudentdescription(rs.getString("studentdescription"));
				studentVO.setStudentstatus(rs.getInt("studentstatus"));
				Blob face = rs.getBlob("face");
				studentVO.setFace(null);
				if (face == null) {
				} else {
					studentVO.setFace(rs.getBlob("Face").getBinaryStream());
				}

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

		return studentVO;
	}

	@Override
	public List<StudentVO> getAll() {
		List<StudentVO> list = new ArrayList<StudentVO>();
		StudentVO studentVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				studentVO = new StudentVO();
				studentVO.setStudentno(rs.getString("studentNo"));
				studentVO.setUserno(rs.getString("userno"));
				studentVO.setBanjino(rs.getString("banjino"));
				studentVO.setStudentname(rs.getString("studentname"));
				studentVO.setFaceid(rs.getString("faceid"));
				studentVO.setStudentdescription(rs.getString("studentdescription"));
				studentVO.setStudentstatus(rs.getInt("studentstatus"));
				Blob face = rs.getBlob("face");
				studentVO.setFace(null);
				if (face == null) {
				} else {
					studentVO.setFace(rs.getBlob("Face").getBinaryStream());
				}list.add(studentVO);

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

	@Override
	public InputStream getPic(String studentno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		InputStream in = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PIC + studentno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Blob b = rs.getBlob(1);
				if (b != null) {
					in = b.getBinaryStream();
				}
			}
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

		return in;
	}

	@Override
	public void insert(StudentVO studentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			

			pstmt.setString(1, studentVO.getUserno());
			pstmt.setString(2, studentVO.getBanjino());
			pstmt.setString(3, studentVO.getStudentname());
			pstmt.setString(4, studentVO.getFaceid());
			pstmt.setBinaryStream(5, studentVO.getFace());
			pstmt.setString(6, studentVO.getStudentdescription());
			pstmt.setInt(7, studentVO.getStudentstatus());
		
			

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void update(StudentVO studentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			
			

			if (studentVO.getFace() == null) {
				pstmt = con.prepareStatement(UPDATE_NOPIC);
				pstmt.setString(1, studentVO.getUserno());
				pstmt.setString(2, studentVO.getBanjino());
				pstmt.setString(3, studentVO.getStudentname());
				pstmt.setString(4, studentVO.getFaceid());
				pstmt.setString(5, studentVO.getStudentdescription());
				pstmt.setInt(6, studentVO.getStudentstatus());
				pstmt.setString(7, studentVO.getStudentno());
			} else {
				pstmt = con.prepareStatement(UPDATE);
				pstmt.setString(1, studentVO.getUserno());
				pstmt.setString(2, studentVO.getBanjino());
				pstmt.setString(3, studentVO.getStudentname());
				pstmt.setString(4, studentVO.getFaceid());
				pstmt.setBinaryStream(5, studentVO.getFace());
				pstmt.setString(6, studentVO.getStudentdescription());
				pstmt.setInt(7, studentVO.getStudentstatus());
				pstmt.setString(7, studentVO.getStudentno());
			}

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
	public void delete(String studentno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, studentno);

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