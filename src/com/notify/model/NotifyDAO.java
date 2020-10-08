package com.notify.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
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

public class NotifyDAO implements NotifyDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO NOTIFY(NOTIFYNO,USERNO,NOTIFYCONTENT,NOTIFYDATE) VALUES(NOTIFY_SEQ.nextval,?,?,?)";
	private static final String GET_ALL_STMT="SELECT * FROM NOTIFY WHERE USERNO=?";

	
	@Override
	public List<NotifyVO> getAllByThemself(String userNo) {
		List<NotifyVO> list =new ArrayList<NotifyVO>();
		NotifyVO notifyVO=null;
		
		Connection con=null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			pstmt.setString(1,userNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				notifyVO=new NotifyVO();
				notifyVO.setUserNo(rs.getString("USERNO"));
				
				Clob clob = rs.getClob("NOTIFYCONTENT");
				StringBuilder sb = new StringBuilder();
				BufferedReader br = new BufferedReader(clob.getCharacterStream());
				String str;
				while ((str = br.readLine()) != null) {
					sb.append(str);
					sb.append("\t");
				}
				br.close();
				notifyVO.setNotifyContent(sb.toString());
				notifyVO.setNotifyDate(rs.getTimestamp("NOTIFYDATE"));
				list.add(notifyVO);
			}
			
			pstmt.executeUpdate();

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
		return list;
	}




	@Override
	public void insert(NotifyVO notifyVO) {
		Connection con=null;
		PreparedStatement pstmt =null;
		
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, notifyVO.getUserNo());
			if(notifyVO.getNotifyContent()!=null) {
				Clob clob = con.createClob();
				String str = notifyVO.getNotifyContent();
				clob.setString(1, str);
				pstmt.setClob(2, clob);
			}else {
				Clob clob = null;
				pstmt.setClob(2, clob);
			}
			pstmt.setTimestamp(3, notifyVO.getNotifyDate());

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
