package com.user.model;

import java.util.*;

import com.userpermission.model.UserPermissionService;

import java.io.InputStream;
import java.sql.*;

public class UserJDBCDAO implements UserDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO emp2 (empno,ename,job,hiredate,sal,comm,deptno) VALUES (emp2_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT empno,ename,job,to_char(hiredate,'yyyy-mm-dd') hiredate,sal,comm,deptno FROM emp2 order by empno";
	private static final String GET_ONE_STMT = 
		"SELECT empno,ename,job,to_char(hiredate,'yyyy-mm-dd') hiredate,sal,comm,deptno FROM emp2 where empno = ?";
	private static final String DELETE = 
		"DELETE FROM emp2 where empno = ?";
	private static final String UPDATE = 
		"UPDATE emp2 set ename=?, job=?, hiredate=?, sal=?, comm=?, deptno=? where empno = ?";

	private static final String GET_ALLACCOUNT="SELECT ACCOUNT FROM WJLUSER WHERE ISDELETE=0 AND TYPE= ?";

	public List<String> checkAccount(Integer type){
		
		List<String> list = new ArrayList<String>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALLACCOUNT);
			pstmt.setInt(1, type);
			rs = pstmt.executeQuery();
			System.out.println(rs.next());
			while(rs.next()) {
				String account=rs.getString(1);
				list.add(account);
			}
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
			+ se.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
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
	public void insert(UserVO userVO) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void update(UserVO userVO) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void delete(String userNO) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public UserVO findByPrimaryKey(String userNO) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<UserVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public InputStream getPic(String userNo) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public UserVO findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<String> checkId(String userNo) {
		// TODO Auto-generated method stub
		return null;
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
	public UserVO UserLogin(String account, String password, Integer type) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void userEnable(UserVO userVO) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public static void main(String[] args) {

		UserJDBCDAO dao = new UserJDBCDAO();

		
	}


	@Override
	public UserVO UserForget(String id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void update_Password(UserVO userVO) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update_Password_backEnd(UserVO userVO) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void studentEnable(String userNo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void studentNoEnable(String userNo) {
		// TODO Auto-generated method stub
		
	}
}