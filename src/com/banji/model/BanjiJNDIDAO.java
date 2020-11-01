package com.banji.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BanjiJNDIDAO implements BanjiDAO_interface {
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
	private static final String INSERT_STMT = "INSERT INTO BANJI(BANJINO,EMPNO,BANJITYPENO,STARTDAY,ENDDAY,BANJINAME,CLASSHOURS,NUMBEROFSTUDENT,CLASSROOMNO,STATUS,BANJICONTENT) VALUES ('B'||LPAD(to_char(BANJI_SEQ.NEXTVAL),'3','0'),?,?,?,?,?,?,?,?,?,?)";
	// 查全部(含結訓、刪除)
	private static final String GET_ALL_STMT = "SELECT BANJINO,EMPNO,BANJITYPENO,to_char(STARTDAY,'yyyy-mm-dd')STARTDAY,to_char(ENDDAY,'yyyy-mm-dd')ENDDAY,BANJINAME,CLASSHOURS,NUMBEROFSTUDENT,CLASSROOMNO,STATUS,BANJICONTENT FROM BANJI WHERE ISDELETE=0";
	// 查單個
	private static final String GET_ONE_STMT = "SELECT BANJINO,EMPNO,BANJITYPENO,to_char(STARTDAY,'yyyy-mm-dd')STARTDAY,to_char(ENDDAY,'yyyy-mm-dd')ENDDAY,BANJINAME,CLASSHOURS,NUMBEROFSTUDENT,CLASSROOMNO,STATUS,BANJICONTENT FROM BANJI  WHERE BANJINO =?";
	// 更新刪除狀態
	private static final String DELETE = "UPDATE BANJI SET ISDELETE=1 WHERE BANJINO=?";
	// 更新(修改)
	private static final String UPDATE = "UPDATE BANJI SET EMPNO=?,BANJITYPENO=?,STARTDAY=?,ENDDAY=?,BANJINAME=?,CLASSHOURS=?,NUMBEROFSTUDENT=?,CLASSROOMNO=? ,STATUS=?,BANJICONTENT=? WHERE BANJINO=?";

	@Override
	public void insert(BanjiVO banjiVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, banjiNo);

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
	public BanjiVO findByPrimaryKey(String banjiNo) {
		BanjiVO banjiVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, banjiNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				banjiVO = new BanjiVO();
				banjiVO.setBanjiNo(banjiNo);
				banjiVO.setEmpNo(rs.getString("empNo"));
				banjiVO.setBanjiTypeNo(rs.getString("banjiTypeNo"));
				banjiVO.setStartDay(rs.getDate("startDay"));
				banjiVO.setEndDay(rs.getDate("endDay"));
				banjiVO.setBanjiName(rs.getString("banjiName"));
				banjiVO.setClassHours(rs.getInt("classHours"));
				banjiVO.setNumberOfStudent(rs.getInt("numberOfStudent"));
				banjiVO.setClassroomNo(rs.getString("classroomNo"));
				banjiVO.setStatus(rs.getInt("status"));
				banjiVO.setBanjiContent(rs.getString("banjiContent"));

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
			con = ds.getConnection();
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
				banjiVO.setStatus(rs.getInt("status"));
				banjiVO.setBanjiContent(rs.getString("banjiContent"));

				list.add(banjiVO);
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
