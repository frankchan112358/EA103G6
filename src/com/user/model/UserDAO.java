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

	private static final String INSERT_STMT = "INSERT INTO WJLUSER (USERNO,ACCOUNT,TYPE,NAME,MAIL,ID) VALUES ('U'||LPAD(to_char(USER_SEQ.NEXTVAL), '6', '0'),LOWER(?),?,?,LOWER(?),?)";
	private static final String GET_ALLACCOUNT = "SELECT ACCOUNT FROM WJLUSER WHERE ISDELETE=0 AND TYPE=?";
	private static final String GET_ALLID = "SELECT ID FROM WJLUSER WHERE ISDELETE=0 AND USERNO!= ?";
	private static final String GET_ALL_STMT = "SELECT USERNO,ACCOUNT,PASSWORD,TYPE,NAME,MAIL,PHONE,ADDRESS,ID,PHOTO,ENABLE FROM WJLUSER WHERE ISDELETE=0 ORDER BY TYPE";
	private static final String GET_PIC = "SELECT PHOTO FROM WJLUSER WHERE PHOTO IS NOT NULL AND USERNO=?";
	private static final String UPDATE = "UPDATE WJLUSER SET TYPE=?, NAME=?, MAIL=LOWER(?), PHONE=?,ADDRESS=? ,ID=?,PHOTO=?,ENABLE=? WHERE USERNO = ?";
	private static final String UPDATE_NOPIC = "UPDATE WJLUSER SET TYPE=?, NAME=?, MAIL=LOWER(?), PHONE=?,ADDRESS=? ,ID=?,ENABLE=? WHERE USERNO = ?";
	private static final String USERENABLE = "UPDATE WJLUSER SET PASSWORD=?,ENABLE=? WHERE USERNO = ?";
	private static final String GET_ONE_STMT = "SELECT USERNO,ACCOUNT,PASSWORD,TYPE,NAME,MAIL,PHONE,ADDRESS,ID,PHOTO,ENABLE FROM WJLUSER WHERE ISDELETE=0 AND USERNO = ?";
	private static final String GET_ONE_STMT_ID = "SELECT USERNO,ID FROM WJLUSER WHERE ID = ?";
	private static final String DELETE = "UPDATE WJLUSER SET ISDELETE=1 WHERE USERNO=?";
	private static final String LOGIN = "SELECT USERNO,ACCOUNT,PASSWORD,TYPE,NAME,MAIL,PHONE,ADDRESS,ID,PHOTO,ENABLE FROM WJLUSER WHERE ISDELETE=0 AND LOWER(ACCOUNT)=? AND PASSWORD=? AND TYPE=?";
	private static final String FORGET = "SELECT USERNO,ACCOUNT,PASSWORD,TYPE,NAME,MAIL,PHONE,ADDRESS,ID,PHOTO,ENABLE FROM WJLUSER WHERE ISDELETE=0 AND ID = ? ";
	private static final String UPDATE_PASSWORD = "UPDATE WJLUSER SET PASSWORD=? WHERE ID = ?";
	private static final String UPDATE_PASSWORD_BACKEND = "UPDATE WJLUSER SET PASSWORD=? WHERE USERNO = ?";
	private static final String STUDENTENABLE = "UPDATE WJLUSER SET ENABLE=1 WHERE USERNO = ?";
	private static final String STUDENTNOENABLE = "UPDATE WJLUSER SET ENABLE=2 WHERE USERNO = ?";
	
	@Override
	public void insert(UserVO userVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, userVO.getAccount());
			pstmt.setInt(2, userVO.getType());
			pstmt.setString(3, userVO.getName());
			pstmt.setString(4, userVO.getMail());
			pstmt.setString(5, userVO.getId());

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
				pstmt.setInt(1, userVO.getType());
				pstmt.setString(2, userVO.getName());
				pstmt.setString(3, userVO.getMail());
				pstmt.setString(4, userVO.getPhone());
				pstmt.setString(5, userVO.getAddress());
				pstmt.setString(6, userVO.getId());
				pstmt.setInt(7, userVO.getEnable());
				pstmt.setString(8, userVO.getUserNo());
			} else {
				pstmt = con.prepareStatement(UPDATE);
				pstmt.setInt(1, userVO.getType());
				pstmt.setString(2, userVO.getName());
				pstmt.setString(3, userVO.getMail());
				pstmt.setString(4, userVO.getPhone());
				pstmt.setString(5, userVO.getAddress());
				pstmt.setString(6, userVO.getId());
				pstmt.setBinaryStream(7, userVO.getPhoto());
				pstmt.setInt(8, userVO.getEnable());
				pstmt.setString(9, userVO.getUserNo());
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
	public void userEnable(UserVO userVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(USERENABLE);
			pstmt.setString(1, userVO.getPassword());
			pstmt.setInt(2, userVO.getEnable());
			pstmt.setString(3, userVO.getUserNo());

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
	public void delete(String userNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, userNo);
			pstmt.executeQuery();
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
				Blob photo = rs.getBlob("PHOTO");
				if (photo == null) {
					userVO.setPhoto(null);
				} else {
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
				Blob photo = rs.getBlob("PHOTO");
				if (photo == null) {
					userVO.setPhoto(null);
				} else {
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

	@Override
	public UserVO UserLogin(String account, String password, Integer type) {
		UserVO userVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN);

			pstmt.setString(1, account);
			pstmt.setString(2, password);
			pstmt.setInt(3, type);

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
				Blob photo = rs.getBlob("PHOTO");
				if (photo == null) {
					userVO.setPhoto(null);
				} else {
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
	public UserVO Login_stu(String account, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVO Login_emp(String account, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVO Login_tea(String account, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVO UserForget(String id) {
		UserVO userVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FORGET);

			pstmt.setString(1, id);

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
				Blob photo = rs.getBlob("PHOTO");
				if (photo == null) {
					userVO.setPhoto(null);
				} else {
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
	public void update_Password(UserVO userVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_PASSWORD);

			pstmt.setString(1, userVO.getPassword());
			pstmt.setString(2, userVO.getId());

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
	public void update_Password_backEnd(UserVO userVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PASSWORD_BACKEND);

			pstmt.setString(1, userVO.getPassword());
			pstmt.setString(2, userVO.getUserNo());

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
	public void studentEnable(String userNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(STUDENTENABLE);

			pstmt.setString(1, userNo);
			pstmt.executeQuery();
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
	
	public void studentNoEnable(String userNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(STUDENTNOENABLE);

			pstmt.setString(1, userNo);
			pstmt.executeQuery();
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



}


