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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TeacherDAO implements TeacherDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO TEACHER(TEACHERNO,USERNO,TEACHERNAME,SKILL,DESCRIPTION) VALUES('T'||LPAD(to_char(TEACHER_SEQ.NEXTVAL), '6', '0'),?,?,?,?)";
	private static final String UPDATE = "UPDATE TEACHER SET USERNO=?,TEACHERNAME=?,SKILL=?,DESCRIPTION=?,TEACHERSTATUS=? WHERE TEACHERNO=?";
	private static final String GET_ONE_STMT = "SELECT TEACHERNO,USERNO,TEACHERNAME,SKILL,DESCRIPTION,TEACHERSTATUS FROM TEACHER WHERE TEACHERSTATUS>0 AND TEACHERNO=?";
	private static final String GET_ALL_STMT="SELECT TEACHERNO,USERNO,TEACHERNAME,SKILL,DESCRIPTION,TEACHERSTATUS FROM TEACHER WHERE TEACHERSTATUS>0";
	private static final String GET_ONE_STMT_USERNO = "SELECT TEACHERNO,USERNO,TEACHERNAME,SKILL,DESCRIPTION,TEACHERSTATUS FROM TEACHER WHERE TEACHERSTATUS>0 AND USERNO=?";
	private static final String DELETE = "UPDATE TEACHER SET TEACHERSTATUS=0 WHERE TEACHERNO=?";

	
	@Override
	public void insert(TeacherVO teacherVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, teacherVO.getUserNo());
			pstmt.setString(2, teacherVO.getTeacherName());
			pstmt.setString(3, teacherVO.getSkill());
			if(teacherVO.getDescription()!=null) {
				Clob clob = con.createClob();
				String str = teacherVO.getDescription();
				clob.setString(1, str);
				pstmt.setClob(4, clob);
			}else {
				Clob clob = null;
				pstmt.setClob(4, clob);
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
	public void update(TeacherVO teacherVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, teacherVO.getUserNo());
			pstmt.setString(2, teacherVO.getTeacherName());
			pstmt.setString(3, teacherVO.getSkill());
			
			if(teacherVO.getDescription()!=null) {
				Clob clob = con.createClob();
				String str = teacherVO.getDescription();
				clob.setString(1, str);
				pstmt.setClob(4, clob);
			}else {
				Clob clob = null;
				pstmt.setClob(4, clob);
			}
			
			pstmt.setInt(5, teacherVO.getTeacherStatus());
			pstmt.setString(6, teacherVO.getTeacherNo());

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
	public TeacherVO findByPrimaryKey(String teacherNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TeacherVO teacherVO = null;
		try {
			con=ds.getConnection();
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
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
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

	public TeacherVO findByUserNo(String userNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TeacherVO teacherVO = null;
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_USERNO);

			pstmt.setString(1, userNo);
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
	public void delete(String teacherNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, teacherNo);
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


}
