package com.user.model;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDAO implements UserDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO WJLUSER (USERNO,ACCOUNT,PASSWORD,TYPE,NAME,MAIL,PHONE,ADDRESS,ID,PHOTO) VALUES ('U'||LPAD(to_char(USER_SEQ.NEXTVAL), '6', '0'),?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALLACCOUNT = "SELECT ACCOUNT FROM WJLUSER WHERE ISDELETE=0 AND TYPE=?";
	private static final String GET_ALLID = "SELECT ID FROM WJLUSER WHERE ISDELETE=0 AND USERNO!= ?";
	private static final String GET_ALL_STMT = "SELECT USERNO,ACCOUNT,PASSWORD,TYPE,NAME,MAIL,PHONE,ADDRESS,ID,PHOTO,ENABLE FROM WJLUSER WHERE ISDELETE=0 ORDER BY TYPE";
	private static final String GET_PIC = "SELECT PHOTO FROM WJLUSER WHERE PHOTO IS NOT NULL AND USERNO=?";
	private static final String UPDATE = "UPDATE WJLUSER SET ACCOUNT=?, PASSWORD=?, TYPE=?, NAME=?, MAIL=?, PHONE=?,ADDRESS=? ,ID=?,PHOTO=?,ENABLE=? WHERE USERNO = ?";
	private static final String UPDATE_NOPIC = "UPDATE WJLUSER SET ACCOUNT=?, PASSWORD=?, TYPE=?, NAME=?, MAIL=?, PHONE=?,ADDRESS=? ,ID=?,ENABLE=? WHERE USERNO = ?";
	private static final String GET_ONE_STMT = "SELECT USERNO,ACCOUNT,PASSWORD,TYPE,NAME,MAIL,PHONE,ADDRESS,ID,PHOTO,ENABLE FROM WJLUSER WHERE ISDELETE=0 AND USERNO = ?";
	private static final String GET_ONE_STMT_ID = "SELECT USERNO,ID FROM WJLUSER WHERE ID = ?";
	private static final String DELETE = "UPDATE WJLUSER SET ISDELETE=1 WHERE USERNO=?";

	@Override
	public void insert(UserVO userVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, userVO.getAccount());
			pstmt.setString(2, userVO.getPassword());
			pstmt.setInt(3, userVO.getType());
			pstmt.setString(4, userVO.getName());
			pstmt.setString(5, userVO.getMail());
			pstmt.setString(6, userVO.getPhone());
			pstmt.setString(7, userVO.getAddress());
			pstmt.setString(8, userVO.getId());
			pstmt.setBinaryStream(9, userVO.getPhoto());

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
	public void update(UserVO userVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			

			if (userVO.getPhoto() == null) {
				pstmt = con.prepareStatement(UPDATE_NOPIC);
				pstmt.setString(1, userVO.getAccount());
				pstmt.setString(2, userVO.getPassword());
				pstmt.setInt(3, userVO.getType());
				pstmt.setString(4, userVO.getName());
				pstmt.setString(5, userVO.getMail());
				pstmt.setString(6, userVO.getPhone());
				pstmt.setString(7, userVO.getAddress());
				pstmt.setString(8, userVO.getId());
				pstmt.setInt(9, userVO.getEnable());
				pstmt.setString(10, userVO.getUserNo());
			} else {
				pstmt = con.prepareStatement(UPDATE);
				pstmt.setString(1, userVO.getAccount());
				pstmt.setString(2, userVO.getPassword());
				pstmt.setInt(3, userVO.getType());
				pstmt.setString(4, userVO.getName());
				pstmt.setString(5, userVO.getMail());
				pstmt.setString(6, userVO.getPhone());
				pstmt.setString(7, userVO.getAddress());
				pstmt.setString(8, userVO.getId());
				pstmt.setBinaryStream(9, userVO.getPhoto());
				pstmt.setInt(10, userVO.getEnable());
				pstmt.setString(11, userVO.getUserNo());
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
	public void empUpdate(UserVO userVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String userNo) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try{
			con=ds.getConnection();
			pstmt=con.prepareStatement(DELETE);
			
			pstmt.setString(1, userNo);
			pstmt.executeQuery();
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
	public UserVO findByPrimaryKey(String userNo) {
		UserVO userVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, userNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				userVO = new UserVO();
				userVO.setUserNo(rs.getString("USERNO"));
				userVO.setAccount(rs.getString("ACCOUNT"));
				userVO.setPassword(rs.getString("PASSWORD"));
				userVO.setType(rs.getInt("TYPE"));
				userVO.setName(rs.getString("NAME"));
				userVO.setMail(rs.getString("MAIL"));
				userVO.setPhone(rs.getString("PHONE"));
				userVO.setAddress(rs.getString("ADDRESS"));
				userVO.setId(rs.getString("ID"));
				Blob photo=rs.getBlob("PHOTO");
				if(photo==null) {
					userVO.setPhoto(null);
				}else {
					userVO.setPhoto(rs.getBlob("PHOTO").getBinaryStream());
				}
				userVO.setEnable(rs.getInt("ENABLE"));
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

		return userVO;
	}
	
	@Override
	public UserVO findById(String id) {
		UserVO userVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_ID);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				userVO = new UserVO();
				userVO.setUserNo(rs.getString("USERNO"));
				userVO.setId(rs.getString("ID"));
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
		
		return userVO;
	}

	@Override
	public List<UserVO> getAll() {
		List<UserVO> list = new ArrayList<UserVO>();
		UserVO userVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				userVO = new UserVO();
				userVO.setUserNo(rs.getString("USERNO"));
				userVO.setAccount(rs.getString("ACCOUNT"));
				userVO.setPassword(rs.getString("PASSWORD"));
				userVO.setType(rs.getInt("TYPE"));
				userVO.setName(rs.getString("NAME"));
				userVO.setMail(rs.getString("MAIL"));
				userVO.setPhone(rs.getString("PHONE"));
				userVO.setAddress(rs.getString("ADDRESS"));
				userVO.setId(rs.getString("ID"));
				Blob photo=rs.getBlob("PHOTO");
				if(photo==null) {
					userVO.setPhoto(null);
				}else {
					userVO.setPhoto(rs.getBlob("PHOTO").getBinaryStream());
				}
				userVO.setEnable(rs.getInt("ENABLE"));
				list.add(userVO);

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
	public InputStream getPic(String userNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		InputStream in = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PIC);
			pstmt.setString(1, userNo);
			
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
			// 依建立順序關閉資源 (越晚建立越早關閉)
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
	public List<String> checkAccount(Integer type) {
		List<String> list = new ArrayList<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALLACCOUNT);
			pstmt.setInt(1, type);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String account = rs.getString(1);
				list.add(account);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (Exception se) {
			throw new RuntimeException("A null " + se.getMessage());
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
	public List<String> checkId(String userNo) {
		List<String> list = new ArrayList<String>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALLID);
			pstmt.setString(1, userNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1);
				list.add(id);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (Exception se) {
			throw new RuntimeException("A null " + se.getMessage());
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
