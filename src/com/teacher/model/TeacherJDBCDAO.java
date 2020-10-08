package com.teacher.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherJDBCDAO implements TeacherDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO TEACHER(TEACHERNO,USERNO,TEACHERNAME,SKILL,DESCRIPTION,TEACHERSTATUS) VALUES(TEACHER_SEQ.NEXTVAL,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE TEACHER SET USERNO=?,TEACHERNAME=?,SKILL=?,DESCRIPTION=?,TEACHERSTATUS=? WHERE TEACHERNO=?";
	private static final String GET_ONE_STMT = "SELECT TEACHERNO,USERNO,TEACHERNAME,SKILL,DESCRIPTION,TEACHERSTATUS FROM TEACHER WHERE TEACHERSTATUS>0 AND TEACHERNO=?";
	private static final String GET_ALL_STMT = "SELECT TEACHERNO,USERNO,TEACHERNAME,SKILL,DESCRIPTION,TEACHERSTATUS FROM TEACHER WHERE TEACHERSTATUS>0";

	@Override
	public void insert(TeacherVO teacherVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, teacherVO.getUserNo());
			pstmt.setString(2, teacherVO.getTeacherName());
			pstmt.setString(3, teacherVO.getSkill());
			Clob clob = con.createClob();
			String str = teacherVO.getDescription();
			clob.setString(1, str);
			pstmt.setClob(4, clob);
			pstmt.setInt(5, teacherVO.getTeacherStatus());

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

	@Override
	public void update(TeacherVO teacherVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, teacherVO.getUserNo());
			pstmt.setString(2, teacherVO.getTeacherName());
			pstmt.setString(3, teacherVO.getSkill());
			Clob clob = con.createClob();
			String str = teacherVO.getDescription();
			clob.setString(1, str);
			pstmt.setClob(4, clob);
			pstmt.setInt(5, teacherVO.getTeacherStatus());
			pstmt.setString(6, teacherVO.getTeacherNo());

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

	@Override
	public TeacherVO findByPrimaryKey(String teacherNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TeacherVO teacherVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, teacherNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				teacherVO = new TeacherVO();
				teacherVO.setTeacherNo(rs.getString("TEACHERNO"));
				teacherVO.setUserNo(rs.getString("USERNO"));
				teacherVO.setTeacherName(rs.getString("TEACHERNAME"));
				teacherVO.setSkill(rs.getString("SKILL"));
				if (rs.getClob("DESCRIPTION") != null) {
					Clob clob = rs.getClob("DESCRIPTION");
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(clob.getCharacterStream());
					String str;
					while ((str = br.readLine()) != null) {
						sb.append(str);
						sb.append("\n");
					}
					br.close();
					teacherVO.setDescription(sb.toString());
				} else {
					teacherVO.setDescription(null);
				}
				teacherVO.setTeacherStatus(rs.getInt("TEACHERSTATUS"));

			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (IOException se) {
			throw new RuntimeException("A IOException error occured. " + se.getMessage());

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

		return teacherVO;
	}

	@Override
	public List<TeacherVO> getAll() {
		List<TeacherVO> list = new ArrayList<TeacherVO>();
		TeacherVO teacherVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				teacherVO = new TeacherVO();
				teacherVO.setTeacherNo(rs.getString("TEACHERNO"));
				teacherVO.setUserNo(rs.getString("USERNO"));
				teacherVO.setTeacherName(rs.getString("TEACHERNAME"));
				teacherVO.setSkill(rs.getString("SKILL"));
				if (rs.getClob("DESCRIPTION") != null) {
					Clob clob = rs.getClob("DESCRIPTION");
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(clob.getCharacterStream());
					String str;
					while ((str = br.readLine()) != null) {
						sb.append(str);
						sb.append("\t");
					}
					br.close();
					teacherVO.setDescription(sb.toString());
				} else {
					teacherVO.setDescription(null);
				}
				teacherVO.setTeacherStatus(rs.getInt("TEACHERSTATUS"));

				list.add(teacherVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (IOException se) {
			throw new RuntimeException("A IOException error occured. " + se.getMessage());
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

		// insert
//		TeacherJDBCDAO dao=new TeacherJDBCDAO();
//		TeacherVO teacherVO = new TeacherVO();
//		teacherVO.setUserNo("7");
//		teacherVO.setTeacherName("陳水");
//		teacherVO.setSkill("人工智慧");
//		teacherVO.setDescription("熟悉前端、後端網頁技術開發，開發過許多網站、網路服務、手機 app 等，目前成立工作室自行接案、曾任 Senior 網頁工程師");
//		teacherVO.setTeacherStatus(1);
//		dao.insert(teacherVO);
//		System.out.println("新增成功");

		// update
//		TeacherJDBCDAO dao=new TeacherJDBCDAO();
//		TeacherVO teacherVO1 = new TeacherVO();
//		teacherVO1.setUserNo("8");
//		teacherVO1.setTeacherName("陳水扁");
//		teacherVO1.setSkill("行動應用");
//		teacherVO1.setDescription("超過 20 年網頁程式開發經驗, 擅長ASP, ASP.NET Web Forms, ASP.NET MVC 專案開發，目前主要擔任ASP.NET技術顧問及課程講");
//		teacherVO1.setTeacherStatus(0);
//		teacherVO1.setTeacherNo("7");
//		dao.update(teacherVO1);	
//		System.out.println("修改成功");

		// delete
//		TeacherJDBCDAO dao=new TeacherJDBCDAO();
//		dao.delete("7");
//		System.out.println("刪除成功");

		// findByPrimaryKey
//		TeacherJDBCDAO dao = new TeacherJDBCDAO();
//		TeacherVO teacherVO = dao.findByPrimaryKey("6");
//		System.out.println(teacherVO.getTeacherNo());
//		System.out.println(teacherVO.getUserNo());
//		System.out.println(teacherVO.getTeacherName());
//		System.out.println(teacherVO.getSkill());
//		System.out.println(teacherVO.getDescription());
//		System.out.println(teacherVO.getTeacherStatus());

		// getAll
		TeacherJDBCDAO dao = new TeacherJDBCDAO();
		List<TeacherVO> list = dao.getAll();
		for (TeacherVO teacherVO : list) {
			System.out.println(teacherVO.getTeacherNo());
			System.out.println(teacherVO.getUserNo());
			System.out.println(teacherVO.getTeacherName());
			System.out.println(teacherVO.getSkill());
			System.out.println(teacherVO.getDescription());
			System.out.println(teacherVO.getTeacherStatus());
			System.out.println("");
			System.out.println("-------------------------------");
		}
	}
}
