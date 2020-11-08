package com.banjitype.model;

import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.banji.model.BanjiVO;

public class BanjiTypeJNDIDAO implements BanjiTypeDAO_interface {
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
	private static final String INSERT_STMT = "INSERT INTO BANJITYPE(BANJITYPENO,BANJITYPENAME,CLASSHOURS,BANJITYPECONTENT,BANJITYPEENABLE) VALUES ('BT'||LPAD(to_char(BANJITYPE_SEQ.NEXTVAL),'3','0'),?,?,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT BANJITYPENO,BANJITYPENAME,CLASSHOURS,BANJITYPECONTENT,BANJITYPEENABLE FROM BANJITYPE WHERE BANJITYPEENABLE=1 ORDER BY BANJITYPENO";
	// 查單個
	private static final String GET_ONE_STMT = "SELECT BANJITYPENO,BANJITYPENAME,CLASSHOURS,BANJITYPECONTENT,BANJITYPEENABLE FROM BANJITYPE WHERE BANJITYPENO =?";
	// 查特別
	private static final String GET_Banji_ByBanjiType_STMT = "SELECT BANJINO,EMPNO,BANJITYPENO,to_char(STARTDAY,'yyyy-mm-dd')STARTDAY,to_char(ENDDAY,'yyyy-mm-dd')ENDDAY,BANJINAME,CLASSHOURS,NUMBEROFSTUDENT,CLASSROOMNO,BANJICONTENT FROM BANJI  WHERE BANJITYPENO =? ORDER BY BANJINO";
	// 刪除
	private static final String DELETE = "UPDATE BANJITYPE SET BANJITYPEENABLE=0 WHERE BANJITYPENO=?";
	// 修改
	private static final String UPDATE = "UPDATE BANJITYPE SET BANJITYPENAME=?,CLASSHOURS=?,BANJITYPECONTENT=?, BANJITYPEENABLE=? WHERE BANJITYPENO=?";

	@Override
	public void insert(BanjiTypeVO banjiTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, banjiTypeVO.getBanjiTypeName());
			pstmt.setInt(2, banjiTypeVO.getClassHours());
			pstmt.setString(3, banjiTypeVO.getBanjiTypeContent());
			pstmt.setInt(4, banjiTypeVO.getBanjiTypeEnable());

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
	public void update(BanjiTypeVO banjiTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, banjiTypeVO.getBanjiTypeName());
			pstmt.setInt(2, banjiTypeVO.getClassHours());
			pstmt.setString(3, banjiTypeVO.getBanjiTypeContent());
			pstmt.setInt(4, banjiTypeVO.getBanjiTypeEnable());
			pstmt.setString(5, banjiTypeVO.getBanjiTypeNo());

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
	public void delete(String banjiTypeNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, banjiTypeNo);

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
	public BanjiTypeVO findByPrimaryKey(String banjiTypeNo) {

		BanjiTypeVO banjiTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, banjiTypeNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				banjiTypeVO = new BanjiTypeVO();
				banjiTypeVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiTypeVO.setBanjiTypeName(rs.getString("banjiTypeName"));
				banjiTypeVO.setClassHours(rs.getInt("classHours"));
				banjiTypeVO.setBanjiTypeContent(rs.getString("banjiTypeContent"));
				banjiTypeVO.setBanjiTypeEnable(rs.getInt("banjiTypeEnable"));
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				banjiTypeVO = new BanjiTypeVO();
				banjiTypeVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiTypeVO.setBanjiTypeName(rs.getString("banjiTypeName"));
				banjiTypeVO.setClassHours(rs.getInt("classHours"));
				banjiTypeVO.setBanjiTypeContent(rs.getString("banjiTypeContent"));
				banjiTypeVO.setBanjiTypeEnable(rs.getInt("banjiTypeEnable"));

				list.add(banjiTypeVO);
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
			con = ds.getConnection();
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
		return set;
	}
}