package com.banjipost.model;

import java.sql.*;
import java.util.*;

public class BanjiPostJDBCDAO implements BanjiPostDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	// 新增
	private static final String INSERT_STMT = "INSERT INTO BANJIPOST(BANJIPOSTNO,BANJINO,TITLE,BANJIPOSTCONTENT,UPDATETIME,STATUS) VALUES (BANJIPOST_SEQ.NEXTVAL,?,?,?,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT BANJIPOSTNO,BANJINO,TITLE,BANJIPOSTCONTENT,UPDATETIME,STATUS FROM BANJIPOST ORDER BY BANJIPOSTNO";
	// 查單個
	private static final String GET_ONE_STMT = "SELECT BANJIPOSTNO,BANJINO,TITLE,BANJIPOSTCONTENT,UPDATETIME,STATUS FROM BANJIPOST  WHERE BANJIPOSTNO =?";
	// 刪除狀態
	private static final String DELETE = "DELETE FROM BANJIPOST WHERE BANJIPOSTNO=?";
	// 修改
	private static final String UPDATE = "UPDATE BANJIPOST SET BANJINO=?,TITLE=?,BANJIPOSTCONTENT=?,UPDATETIME=?,STATUS=? WHERE BANJIPOSTNO=?";

	@Override
	public void insert(BanjiPostVO banjiPostVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, banjiPostVO.getBanjiNo());
			pstmt.setString(2, banjiPostVO.getTitle());
			pstmt.setString(3, banjiPostVO.getBanjiPostContent());
			pstmt.setTimestamp(4, banjiPostVO.getUpdateTime());
			pstmt.setInt(5, banjiPostVO.getStatus());

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
	public void update(BanjiPostVO banjiPostVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, banjiPostVO.getBanjiNo());
			pstmt.setString(2, banjiPostVO.getTitle());
			pstmt.setString(3, banjiPostVO.getBanjiPostContent());
			pstmt.setTimestamp(4, banjiPostVO.getUpdateTime());
			pstmt.setInt(5, banjiPostVO.getStatus());
			pstmt.setString(6, banjiPostVO.getBanjiPostNo());

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
	public void delete(String banjiPostNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, banjiPostNo);

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
	public BanjiPostVO findByPrimaryKey(String banjiPostNo) {
		BanjiPostVO banjiPostVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, banjiPostNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				banjiPostVO = new BanjiPostVO();
				banjiPostVO.setBanjiPostNo(rs.getString("banjiPostNo"));
				banjiPostVO.setBanjiNo(rs.getString("banjiNo"));
				banjiPostVO.setTitle(rs.getString("title"));
				banjiPostVO.setBanjiPostContent(rs.getString("banjiPostContent"));
				banjiPostVO.setUpdateTime(rs.getTimestamp("updateTime"));
				banjiPostVO.setStatus(rs.getInt("status"));
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
		return banjiPostVO;
	}

	@Override
	public List<BanjiPostVO> getAll() {
		List<BanjiPostVO> list = new ArrayList<BanjiPostVO>();
		BanjiPostVO banjiPostVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				banjiPostVO = new BanjiPostVO();
				banjiPostVO.setBanjiPostNo(rs.getString("banjiPostNo"));
				banjiPostVO.setBanjiNo(rs.getString("banjiNo"));
				banjiPostVO.setTitle(rs.getString("title"));
				banjiPostVO.setBanjiPostContent(rs.getString("banjiPostContent"));
				banjiPostVO.setUpdateTime(rs.getTimestamp("updateTime"));
				banjiPostVO.setStatus(rs.getInt("status"));

				list.add(banjiPostVO);
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

		BanjiPostJDBCDAO dao = new BanjiPostJDBCDAO();

		// 新增
		BanjiPostVO banjiPostVO1 = new BanjiPostVO();
		banjiPostVO1.setBanjiNo("B003");
		banjiPostVO1.setTitle("我姊超正");
		banjiPostVO1.setBanjiPostContent("怎麼可能!!!假的");
		banjiPostVO1.setUpdateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		banjiPostVO1.setStatus(0);

		dao.insert(banjiPostVO1);

		// 查詢
		BanjiPostVO banjiPostVO2 = dao.findByPrimaryKey("1");
		System.out.println(banjiPostVO2.getBanjiPostNo() + ",");
		System.out.println(banjiPostVO2.getBanjiNo() + ",");
		System.out.println(banjiPostVO2.getTitle() + ",");
		System.out.println(banjiPostVO2.getBanjiPostContent() + ",");
		System.out.println(banjiPostVO2.getUpdateTime());
		System.out.println("---------------------");
//		
		// 查詢
		List<BanjiPostVO>list=dao.getAll();
		for(BanjiPostVO aBanjiPostVO:list) {
			System.out.println(aBanjiPostVO.getBanjiPostNo() + ",");
			System.out.println(aBanjiPostVO.getBanjiNo() + ",");
			System.out.println(aBanjiPostVO.getTitle() + ",");
			System.out.println(aBanjiPostVO.getBanjiPostContent() + ",");
			System.out.println(aBanjiPostVO.getUpdateTime());
			System.out.println("---------------------");
		}

		// 刪除
//		dao.delete("5");

		// 修改
		BanjiPostVO banjiPostVO3 = new BanjiPostVO();
		banjiPostVO3.setBanjiNo("B002");
		banjiPostVO3.setTitle("誰是帥哥");
		banjiPostVO3.setBanjiPostContent("認為是帥哥傳自拍照至班網");
		banjiPostVO3.setUpdateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		banjiPostVO3.setStatus(2);
		banjiPostVO3.setBanjiPostNo("2");
		dao.update(banjiPostVO3);
	}
}