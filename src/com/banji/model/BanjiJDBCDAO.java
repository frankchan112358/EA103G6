package com.banji.model;

import java.sql.*;
import java.util.*;

public class BanjiJDBCDAO implements BanjiDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	// 新增
	private static final String INSERT_STMT = "INSERT INTO BANJI(BANJINO,EMPNO,BANJITYPENO,STARTDAY,ENDDAY,BANJINAME,CLASSHOURS,NUMBEROFSTUDENT,CLASSROOMNO,STATUS,BANJICONTENT) VALUES ('B'||LPAD(to_char(BANJI_SEQ.NEXTVAL),'3','0'),?,?,?,?,?,?,?,?,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT BANJINO,EMPNO,BANJITYPENO,to_char(STARTDAY,'yyyy-mm-dd')STARTDAY,to_char(ENDDAY,'yyyy-mm-dd')ENDDAY,BANJINAME,CLASSHOURS,NUMBEROFSTUDENT,CLASSROOMNO,BANJICONTENT FROM BANJI WHERE ISDELETE=0 ORDER BY BANJINO";
	// 查單個
	private static final String GET_ONE_STMT = "SELECT BANJINO,EMPNO,BANJITYPENO,to_char(STARTDAY,'yyyy-mm-dd')STARTDAY,to_char(ENDDAY,'yyyy-mm-dd')ENDDAY,BANJINAME,CLASSHOURS,NUMBEROFSTUDENT,CLASSROOMNO,BANJICONTENT FROM BANJI  WHERE BANJINO =?";
	// 更新刪除狀態
	private static final String DELETE = "UPDATE BANJI SET ISDELETE=1 WHERE BANJINO=?";
	// 修改
	private static final String UPDATE = "UPDATE BANJI SET EMPNO=?,BANJITYPENO=?,STARTDAY=?,ENDDAY=?,BANJINAME=?,CLASSHOURS=?,NUMBEROFSTUDENT=?,CLASSROOMNO=? ,STATUS=?,BANJICONTENT=? WHERE BANJINO=?";

	@Override
	public void insert(BanjiVO banjiVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, banjiVO.getEmpNo());
			pstmt.setString(2, banjiVO.getBanjiTypeNo());
			pstmt.setDate(3, banjiVO.getStartDay());
			pstmt.setDate(4, banjiVO.getEndDay());
			pstmt.setString(5, banjiVO.getBanjiName());
			pstmt.setInt(6, banjiVO.getClassHours());
			pstmt.setInt(7, banjiVO.getNumberOfStudent());
			pstmt.setString(8, banjiVO.getClassroomNo());
			pstmt.setInt(9, banjiVO.getStatus());
			pstmt.setString(10, banjiVO.getBanjiContent());

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
	public void update(BanjiVO banjiVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, banjiVO.getEmpNo());
			pstmt.setString(2, banjiVO.getBanjiTypeNo());
			pstmt.setDate(3, banjiVO.getStartDay());
			pstmt.setDate(4, banjiVO.getEndDay());
			pstmt.setString(5, banjiVO.getBanjiName());
			pstmt.setInt(6, banjiVO.getClassHours());
			pstmt.setInt(7, banjiVO.getNumberOfStudent());
			pstmt.setString(8, banjiVO.getClassroomNo());
			pstmt.setInt(9, banjiVO.getStatus());
			pstmt.setString(10, banjiVO.getBanjiContent());
			pstmt.setString(11, banjiVO.getBanjiNo());

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
	public void delete(String banjiNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, banjiNo);

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
	public BanjiVO findByPrimaryKey(String banjiNo) {

		BanjiVO banjiVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, banjiNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				banjiVO = new BanjiVO();
				banjiVO.setBanjiNo(rs.getString("banjiNo"));
				banjiVO.setEmpNo(rs.getString("empNo"));
				banjiVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiVO.setStartDay(rs.getDate("startDay"));
				banjiVO.setEndDay(rs.getDate("endDay"));
				banjiVO.setBanjiName(rs.getString("banjiName"));
				banjiVO.setClassHours(rs.getInt("classHours"));
				banjiVO.setNumberOfStudent(rs.getInt("numberOfStudent"));
				banjiVO.setClassroomNo(rs.getString("classroomNo"));
				banjiVO.setBanjiContent(rs.getString("banjiContent"));
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
		return banjiVO;
	}

	@Override
	public List<BanjiVO> getAll() {
		List<BanjiVO> list = new ArrayList<BanjiVO>();
		BanjiVO banjiVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				banjiVO = new BanjiVO();
				banjiVO.setBanjiNo(rs.getString("banjiNo"));
				banjiVO.setEmpNo(rs.getString("empNo"));
				banjiVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiVO.setStartDay(rs.getDate("startDay"));
				banjiVO.setEndDay(rs.getDate("endDay"));
				banjiVO.setBanjiName(rs.getString("banjiName"));
				banjiVO.setClassHours(rs.getInt("classHours"));
				banjiVO.setNumberOfStudent(rs.getInt("numberOfStudent"));
				banjiVO.setClassroomNo(rs.getString("classroomNo"));
				banjiVO.setBanjiContent(rs.getString("banjiContent"));

				list.add(banjiVO);
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

	public static void main(String[] args) {

		BanjiJDBCDAO dao = new BanjiJDBCDAO();

		// 新增
		BanjiVO banjiVO1 = new BanjiVO();
		banjiVO1.setEmpNo("E000002");
		banjiVO1.setBanjiTypeNo("BT003");
		banjiVO1.setStartDay(java.sql.Date.valueOf("2020-01-01"));
		banjiVO1.setEndDay(java.sql.Date.valueOf("2021-01-01"));
		banjiVO1.setBanjiName("GG940");
		banjiVO1.setClassHours(600);
		banjiVO1.setNumberOfStudent(45);
		banjiVO1.setClassroomNo("2");
		banjiVO1.setStatus(1);
		banjiVO1.setBanjiContent("小孩子才做選擇，我全都要");

		dao.insert(banjiVO1);

		// 查詢
		BanjiVO banjiVO2 = dao.findByPrimaryKey("B001");
		System.out.print(banjiVO2.getBanjiNo() + ",");
		System.out.print(banjiVO2.getEmpNo() + ",");
		System.out.print(banjiVO2.getBanjiTypeNo() + ",");
		System.out.println(banjiVO2.getStartDay());
		System.out.println(banjiVO2.getEndDay());
		System.out.print(banjiVO2.getBanjiName() + ",");
		System.out.print(banjiVO2.getClassHours() + ",");
		System.out.print(banjiVO2.getNumberOfStudent() + ",");
		System.out.print(banjiVO2.getClassroomNo() + ",");
		System.out.println(banjiVO2.getStatus()+ ",");
		System.out.println(banjiVO2.getBanjiContent());
		System.out.println("---------------------");

		// 查詢
		List<BanjiVO> list = dao.getAll();
		for (BanjiVO aBanjiVO : list) {
			System.out.print(aBanjiVO.getBanjiNo() + ",");
			System.out.print(aBanjiVO.getEmpNo() + ",");
			System.out.print(aBanjiVO.getBanjiTypeNo() + ",");
			System.out.println(aBanjiVO.getStartDay());
			System.out.println(aBanjiVO.getEndDay());
			System.out.print(aBanjiVO.getBanjiName() + ",");
			System.out.print(aBanjiVO.getClassHours() + ",");
			System.out.print(aBanjiVO.getNumberOfStudent() + ",");
			System.out.print(aBanjiVO.getClassroomNo() + ",");
			System.out.println(aBanjiVO.getBanjiContent());
			System.out.println("---------------------");
		}

		// 刪除狀態(isdelete)
//		dao.delete("B001");

		// 修改
		BanjiVO banjiVO3 = new BanjiVO();
		banjiVO3.setEmpNo("E000002");
		banjiVO3.setBanjiTypeNo("BT003");
		banjiVO3.setStartDay(java.sql.Date.valueOf("2020-01-01"));
		banjiVO3.setEndDay(java.sql.Date.valueOf("2021-01-01"));
		banjiVO3.setBanjiName("GG940");
		banjiVO3.setClassHours(600);
		banjiVO3.setNumberOfStudent(45);
		banjiVO3.setClassroomNo("2");
		banjiVO3.setStatus(1);
		banjiVO3.setBanjiContent("33333");
		banjiVO3.setBanjiNo("B001");
		dao.update(banjiVO3);
	}
}