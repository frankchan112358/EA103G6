package com.student.model;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;





public class StudentJDBCDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103";
	String passwd = "123456";
	private static final String INSERT_STMT = "INSERT INTO student (studentNO,Userno,Banjino,Studentname,Faceid,Face,Studentdescription,Studentstatus) VALUES ('S'||LPAD(to_char(STUDENTNO_SEQ.NEXTVAL), '6', '0'), ?, ?, ?, ?, ?, ?,?)";
	private static final String DELETE = "UPDATE student SET Studentstatus=3 where studentno = ?";
	private static final String GET_ONE_STMT = "SELECT STUDENTNO,USERNO,BANJINO,Studentname,faceid,face,studentdescription,studentstatus FROM  student where studentno = ? AND studentstatus=1";
	private static final String GET_ALL_STMT = "SELECT studentno,userno,banjino,studentname,faceid,face,studentdescription,studentstatus FROM student WHERE studentstatus=1 ";
	private static final String UPDATE = "UPDATE student set userno=?,banjino=?,studentname=?, faceid=?, face=?, studentdescription=?, studentstatus=? where studentno = ?";
	private static final String UPDATE_NOPIC = "UPDATE student set userno=?,banjino=?,studentname=?, faceid=?, studentdescription=?, studentstatus=? where studentno = ?";
	
	public void insert(StudentVO studentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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

	public void update(StudentVO studentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			

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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	
	public void delete(String studentno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, studentno);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public StudentVO findByPrimaryKey(String studentno) {
		StudentVO studentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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

	

	public List<StudentVO> getAll() {
		List<StudentVO> list = new ArrayList<StudentVO>();
		StudentVO studentVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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

		StudentJDBCDAO dao = new StudentJDBCDAO();

//		StudentVO studentVO1 = new StudentVO();
//		studentVO1.setUserno("U000010");
//		studentVO1.setBanjino("B001");
//		studentVO1.setStudentname("詹Q保");
//		studentVO1.setFaceid("4");
//		studentVO1.setFace(null);
//		studentVO1.setStudentdescription("我很QQQQQQQQ");
//		studentVO1.setStudentstatus(1);
//		dao.insert(studentVO1);
//		System.out.println("新增成功");
//		dao.delete("S000003");
//		System.out.println("刪除成功");
//
//		StudentVO studentVO3 = dao.findByPrimaryKey("S000002");
//		System.out.print(studentVO3.getStudentno() + ",");
//		System.out.print(studentVO3.getUserno() + ",");
//		System.out.print(studentVO3.getBanjino() + ",");
//		System.out.print(studentVO3.getStudentname() + ",");
//		System.out.print(studentVO3.getFaceid() + ",");
//		System.out.print(studentVO3.getFace() + ",");
//		System.out.print(studentVO3.getStudentdescription() + ",");
//		System.out.println(studentVO3.getStudentstatus());
//		System.out.println("---------------------");
//		
		StudentVO studentVO4 = new StudentVO();
		studentVO4.setStudentno("S000002");
		studentVO4.setUserno("吳永志2");
		studentVO4.setBanjino("MANAGER2");
		studentVO4.setStudentname("123");
		studentVO4.setFace(null);
		studentVO4.setFaceid("789");
		studentVO4.setStudentdescription("456ssssssssssssssss");
		studentVO4.setStudentstatus(1);
		dao.update(studentVO4);
		
		
		
		
//
//		List<String> list = dao.checkAccount(1);
////		System.out.println(111);
//		for (String account : list) {
//			System.out.println(account);
			
//			List<StudentVO> list = dao.getAll();
//			for (StudentVO aStudent : list) {
//				System.out.print(aStudent.getStudentno() + ",");
//				System.out.print(aStudent.getUserno() + ",");
//				System.out.print(aStudent.getBanjino() + ",");
//				System.out.print(aStudent.getStudentname() + ",");
//				System.out.print(aStudent.getFaceid() + ",");
//				System.out.print(aStudent.getFace() + ",");
//				System.out.print(aStudent.getStudentdescription()+ ",");
//				System.out.print(aStudent.getStudentstatus());
//				System.out.println();
//			
//		}

	}
}
