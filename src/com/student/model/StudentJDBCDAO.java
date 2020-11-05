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





public class StudentJDBCDAO implements StudentDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";
	private static final String INSERT_STMT = "INSERT INTO student (studentNO,Userno,Banjino,Studentname) VALUES ('S'||LPAD(to_char(STUDENTNO_SEQ.NEXTVAL), '6', '0'), ?, ?, ?)";
	private static final String DELETE = "UPDATE student SET Studentstatus=3 where studentno = ?";
	private static final String GET_ONE_STMT = "SELECT STUDENTNO,USERNO,BANJINO,Studentname,studentdescription,studentstatus FROM  student where studentno = ? AND studentstatus=1";
	private static final String GET_ALL_STMT = "SELECT studentno,userno,banjino,studentname,studentdescription,studentstatus FROM student WHERE studentstatus=1 ";
	private static final String UPDATE = "UPDATE student set userno=?,banjino=?,studentname=?, studentdescription=?, studentstatus=? where studentno = ?";
	private static final String UPDATE_NOPIC = "UPDATE student set userno=?,banjino=?,studentname=?, studentdescription=?, studentstatus=? where studentno = ?";
	
	public void insert(StudentVO studentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, studentVO.getUserNo());
			pstmt.setString(2, studentVO.getBanjiNo());
			pstmt.setString(3, studentVO.getStudentName());

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
			
			

			
				pstmt = con.prepareStatement(UPDATE_NOPIC);
				pstmt.setString(1, studentVO.getUserNo());
				pstmt.setString(2, studentVO.getBanjiNo());
				pstmt.setString(3, studentVO.getStudentName());
				pstmt.setString(4, studentVO.getStudentDescription());
				pstmt.setInt(5, studentVO.getStudentStatus());
				pstmt.setString(6, studentVO.getStudentNo());
			

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
				studentVO.setStudentNo(rs.getString("studentNo"));
				studentVO.setUserNo(rs.getString("userno"));
				studentVO.setBanjiNo(rs.getString("banjino"));
				studentVO.setStudentName(rs.getString("studentname"));
				
				studentVO.setStudentDescription(rs.getString("studentdescription"));
				studentVO.setStudentStatus(rs.getInt("studentstatus"));
				

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
				studentVO.setStudentNo(rs.getString("studentNo"));
				studentVO.setUserNo(rs.getString("userno"));
				studentVO.setBanjiNo(rs.getString("banjino"));
				studentVO.setStudentName(rs.getString("studentname"));
				studentVO.setStudentDescription(rs.getString("studentdescription"));
				studentVO.setStudentStatus(rs.getInt("studentstatus"));
				

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
//		studentVO1.setUserNo("U000010");
//		studentVO1.setBanjiNo("B001");
//		studentVO1.setStudentName("美女");
//		studentVO1.setStudentStatus(1);
//		dao.insert(studentVO1);
//		System.out.println("新增成功");
		
		
//		dao.delete("S000003");
//		System.out.println("������");

//		StudentVO studentVO3 = dao.findByPrimaryKey("S000002");
//		System.out.print(studentVO3.getStudentNo() + ",");
//		System.out.print(studentVO3.getUserNo() + ",");
//		System.out.print(studentVO3.getBanjiNo() + ",");
//		System.out.print(studentVO3.getStudentName() + ",");
//
//		System.out.print(studentVO3.getStudentDescription() + ",");
//		System.out.println(studentVO3.getStudentStatus());
//		System.out.println("---------------------");
		
//		StudentVO studentVO4 = new StudentVO();
//		studentVO4.setStudentNo("S000002");
//		studentVO4.setUserNo("吳永志2");
//		studentVO4.setBanjiNo("MANAGER2");
//		studentVO4.setStudentName("123");
//		studentVO4.setStudentDescription("456ssssssssssssssss");
//		studentVO4.setStudentStatus(1);
//		dao.update(studentVO4);
//		
//		System.out.println("---------------------");
		
		
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

	@Override
	public InputStream getPic(String studentNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudentVO findByPrimaryKeyByuserNo(String userNo) {
		// TODO Auto-generated method stub
		return null;
	}
}
