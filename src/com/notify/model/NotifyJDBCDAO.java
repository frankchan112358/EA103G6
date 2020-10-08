package com.notify.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class NotifyJDBCDAO implements NotifyDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";
	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			pstmt.setString(1,userNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				notifyVO=new NotifyVO();
				notifyVO.setUserNo(rs.getString("USERNO"));
				
				if(rs.getClob("NOTIFYCONTENT")!=null) {
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
				}else {
					notifyVO.setNotifyContent(null);
				}
				notifyVO.setNotifyDate(rs.getTimestamp("NOTIFYDATE"));
				list.add(notifyVO);
			}
			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	
	public static void main(String[] args) {
		NotifyJDBCDAO dao=new NotifyJDBCDAO();
		
		//insert
//		NotifyVO notifyVO =new NotifyVO();
//		notifyVO.setUserNo("U000002");
//		notifyVO.setNotifyContent("您的貨物已抵達指定的便利商店");
//		
//		Timestamp time= new Timestamp(new Date().getTime());
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//		String timeStr=df.format(time); 
//		time = Timestamp.valueOf(timeStr);
//		notifyVO.setNotifyDate(time);
//		
//		dao.insert(notifyVO);
//		System.out.println("新增成功");
		
		//getAllByThemself
		List<NotifyVO> list=dao.getAllByThemself("U000002");
		for(NotifyVO notifyVO :list) {
			System.out.println(notifyVO.getUserNo());
			System.out.println(notifyVO.getNotifyContent());
			//方法一：直接印出timestamp
//			System.out.println(notifyVO.getNotifyDate());
			
			//方法二：修改時間呈現模式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String timeStr = df.format(notifyVO.getNotifyDate());
			System.out.println(timeStr);
			System.out.println();
			System.out.println("------------------------");
			
		}
	}

}
