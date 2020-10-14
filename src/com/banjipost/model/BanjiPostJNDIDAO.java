package com.banjipost.model;

import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BanjiPostJNDIDAO implements BanjiPostDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, banjiPostVO.getBanjiNo());
			pstmt.setString(2, banjiPostVO.getTitle());
			pstmt.setString(3, banjiPostVO.getBanjiPostContent());
			pstmt.setTimestamp(4, banjiPostVO.getUpdateTime());
			pstmt.setInt(5, banjiPostVO.getStatus());

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
	public void update(BanjiPostVO banjiPostVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, banjiPostVO.getBanjiNo());
			pstmt.setString(2, banjiPostVO.getTitle());
			pstmt.setString(3, banjiPostVO.getBanjiPostContent());
			pstmt.setTimestamp(4, banjiPostVO.getUpdateTime());
			pstmt.setInt(5, banjiPostVO.getStatus());
			pstmt.setString(6, banjiPostVO.getBanjiPostNo());

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
	public void delete(String banjiPostNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, banjiPostNo);

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
	public BanjiPostVO findByPrimaryKey(String banjiPostNo) {
		BanjiPostVO banjiPostVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
