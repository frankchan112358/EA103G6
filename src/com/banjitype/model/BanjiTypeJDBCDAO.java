package com.banjitype.model;

import java.sql.*;
import java.util.*;

import com.banji.model.*;

public class BanjiTypeJDBCDAO implements BanjiTypeDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	// 新增
	private static final String INSERT_STMT = "INSERT INTO BANJITYPE(BANJITYPENO,BANJITYPENAME,CLASSHOURS,BANJITYPECONTENT) VALUES ('BT'||LPAD(to_char(BANJITYPE_SEQ.NEXTVAL),'3','0'),?,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT BANJITYPENO,BANJITYPENAME,CLASSHOURS,BANJITYPECONTENT FROM BANJITYPE WHERE  BANJITYPEENABLE=1 ORDER BY BANJITYPENO";
	// 查單個
	private static final String GET_ONE_STMT = "SELECT BANJITYPENO,BANJITYPENAME,CLASSHOURS,BANJITYPECONTENT FROM BANJITYPE WHERE BANJITYPENO =?";
	// 查特別
	private static final String GET_Banji_ByBanjiType_STMT = "SELECT BANJINO,EMPNO,BANJITYPENO,to_char(STARTDAY,'yyyy-mm-dd')STARTDAY,to_char(ENDDAY,'yyyy-mm-dd')ENDDAY,BANJINAME,CLASSHOURS,NUMBEROFSTUDENT,CLASSROOMNO,BANJICONTENT FROM BANJI  WHERE BANJITYPENO =? ORDER BY BANJINO";
	// 刪除
	private static final String DELETE = "UPDATE BANJITYPE SET BANJITYPEENABLE=0 WHERE BANJITYPENO=?";
	// 修改
	private static final String UPDATE = "UPDATE BANJITYPE SET BANJITYPENAME=?,CLASSHOURS=?,BANJITYPECONTENT=? WHERE BANJITYPENO=?";

	@Override
	public void insert(BanjiTypeVO banjiTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, banjiTypeVO.getBanjiTypeName());
			pstmt.setInt(2, banjiTypeVO.getClassHours());
			pstmt.setString(3, banjiTypeVO.getBanjiTypeContent());

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
	public void update(BanjiTypeVO banjiTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, banjiTypeVO.getBanjiTypeName());
			pstmt.setInt(2, banjiTypeVO.getClassHours());
			pstmt.setString(3, banjiTypeVO.getBanjiTypeContent());
			pstmt.setString(4, banjiTypeVO.getBanjiTypeNo());

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
	public void delete(String banjiTypeNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, banjiTypeNo);

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
	public BanjiTypeVO findByPrimaryKey(String banjiTypeNo) {
		BanjiTypeVO banjiTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, banjiTypeNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				banjiTypeVO = new BanjiTypeVO();
				banjiTypeVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiTypeVO.setBanjiTypeName(rs.getString("banjiTypeName"));
				banjiTypeVO.setClassHours(rs.getInt("classHours"));
				banjiTypeVO.setBanjiTypeContent(rs.getString("banjiTypeContent"));
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
		return banjiTypeVO;
	}

	@Override
	public List<BanjiTypeVO> getAll() {
		List<BanjiTypeVO> list = new ArrayList<BanjiTypeVO>();
		BanjiTypeVO banjiTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				banjiTypeVO = new BanjiTypeVO();
				banjiTypeVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiTypeVO.setBanjiTypeName(rs.getString("banjiTypeName"));
				banjiTypeVO.setClassHours(rs.getInt("classHours"));
				banjiTypeVO.setBanjiTypeContent(rs.getString("banjiTypeContent"));

				list.add(banjiTypeVO);
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

	@Override
	public Set<BanjiVO> getBanTypeByBanjiTypeNo(String banjiTypeNo) {
		Set<BanjiVO> set = new LinkedHashSet<BanjiVO>();
		BanjiVO banjiVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Banji_ByBanjiType_STMT);
			pstmt.setString(1, banjiTypeNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				banjiVO = new BanjiVO();
				banjiVO.setBanjiNo(rs.getString("BANJINO"));
				banjiVO.setEmpNo(rs.getString("EMPNO"));
				banjiVO.setBanjiTypeNo(rs.getString("BANJITYPENO"));
				banjiVO.setStartDay(rs.getDate("STARTDAY"));
				banjiVO.setEndDay(rs.getDate("ENDDAY"));
				banjiVO.setBanjiName(rs.getString("BANJINAME"));
				banjiVO.setClassHours(rs.getInt("CLASSHOURS"));
				banjiVO.setNumberOfStudent(rs.getInt("NUMBEROFSTUDENT"));
				banjiVO.setClassroomNo(rs.getString("CLASSROOMNO"));
				banjiVO.setBanjiContent(rs.getString("BANJICONTENT"));
				set.add(banjiVO);
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
		return set;
	}

	public static void main(String[] args) {

		BanjiTypeJDBCDAO dao = new BanjiTypeJDBCDAO();

		// 新增
		BanjiTypeVO banjiTypeVO1 = new BanjiTypeVO();
		banjiTypeVO1.setBanjiTypeName("BIGEGG班");
		banjiTypeVO1.setClassHours(600);
		banjiTypeVO1.setBanjiTypeContent("加入巨蛋，擁有巨蛋!!!");

		dao.insert(banjiTypeVO1);

		// 查詢
		BanjiTypeVO banjiTypeVO2 = dao.findByPrimaryKey("BT004");
		System.out.println(banjiTypeVO2.getBanjiTypeNo() + ",");
		System.out.println(banjiTypeVO2.getBanjiTypeName() + ",");
		System.out.println(banjiTypeVO2.getClassHours() + ",");
		System.out.println(banjiTypeVO2.getBanjiTypeContent());
		System.out.println("---------------------");

		// 查詢
		List<BanjiTypeVO> list = dao.getAll();
		for (BanjiTypeVO aBanjiTypeVO : list) {
			System.out.println(aBanjiTypeVO.getBanjiTypeNo() + ",");
			System.out.println(aBanjiTypeVO.getBanjiTypeName() + ",");
			System.out.println(aBanjiTypeVO.getClassHours() + ",");
			System.out.println(aBanjiTypeVO.getBanjiTypeContent());
			System.out.println("---------------------");
		}

		// 查詢
		Set<BanjiVO> set = dao.getBanTypeByBanjiTypeNo("BT001");
		for (BanjiVO vBanjiVO : set) {
			System.out.println(vBanjiVO.getBanjiNo() + ",");
			System.out.println(vBanjiVO.getEmpNo() + ",");
			System.out.println(vBanjiVO.getBanjiTypeNo() + ",");
			System.out.println(vBanjiVO.getStartDay() + ",");
			System.out.println(vBanjiVO.getEndDay() + ",");
			System.out.println(vBanjiVO.getBanjiName() + ",");
			System.out.println(vBanjiVO.getClassHours() + ",");
			System.out.println(vBanjiVO.getNumberOfStudent() + ",");
			System.out.println(vBanjiVO.getClassroomNo() + ",");
			System.out.println(vBanjiVO.getBanjiContent() + ",");
			System.out.println("------------------------------");
		}

		// 刪除狀態(isdelete)
		dao.delete("BT002");

		// 修改
		BanjiTypeVO banjiTypeVO3 = new BanjiTypeVO();
		banjiTypeVO3.setBanjiTypeName("SMALLEGG班");
		banjiTypeVO3.setClassHours(556);
		banjiTypeVO3.setBanjiTypeContent("加入大巨蛋，擁有小巨蛋!!");
		banjiTypeVO3.setBanjiTypeNo("BT004");
		dao.update(banjiTypeVO3);
	}
}