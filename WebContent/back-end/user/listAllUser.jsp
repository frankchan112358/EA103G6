<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*"%>
<%@ page import="java.util.*,java.io.*"%>

<%
	UserService userSvc = new UserService();
	List<UserVO> list = userSvc.getAll();
%>
<html>
<head>

<title>所有使用者資料 - listAllEmp.jsp</title>
<style>
a {
	text-decoration: none;
	color: green;
}

a:hover {
	color: red;
	text-decoration: underline;
}

th {
	font-size: 1em;
}

td {
	font-size: 0.8em;
}

div {
	width: 100px;
	height: 150px;
}

img {
	max-width: 100%;
	max-height: 100%;
}
</style>
</head>
<body>
	<h4>
		<a href="<%=request.getContextPath() %>/back-end/user/select_page.jsp">回首頁</a><br/>		
	</h4>

	<table style="border: 1px GREEN solid;" border='1'>
		<tr>
			<th>使用者編號</th>
			<th>帳號</th>
			<th>密碼</th>
			<th>使用者種類</th>
			<th>姓名</th>
			<th>信箱</th>
			<th>電話</th>
			<th>地址</th>
			<th>身分證字號</th>
			<th>啟用狀態</th>
			<th>大頭照</th>
			<th>修改</th>
			<th>自行修改</th>
			<th>刪除</th>
		</tr>
		<%
			for (UserVO userVO : list) {
		%>
		<tr>

			<td><%=userVO.getUserNo()%></td>
			<td><%=userVO.getAccount()%></td>
			<td><%=userVO.getPassword()%></td>
			<td>
				<%
					if (userVO.getType() == 0) {
							out.print("學生");
						} else if (userVO.getType() == 1) {
							out.print("講師");
						} else {
							out.print("導師");
						}
				%>
			</td>
			<td><%=userVO.getName()%></td>
			<td><%=userVO.getMail()%></td>
			<td><%=userVO.getPhone()==null? "暫無輸入":userVO.getPhone()%></td>
			<td><%=userVO.getAddress()==null? "暫無輸入":userVO.getAddress()%></td>
			<td><%=userVO.getId()%></td>
			<td>
				<%
					if (userVO.getEnable() == 0) {
							out.print("停用中");
						} else {
							out.print("啟用中");
						}
				%>
			</td>

			<td>
				<div>
				<% if(userVO.getPhoto()==null){ %>
				<img src="<%=request.getContextPath() %>/images/noPicture.png">
				<%}else{ %>
					<img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=<%=userVO.getUserNo()%>">
				<%} %>
				</div>
			</td>

			<td align="center">
				<form action="<%=request.getContextPath()%>/user.do" method="post" style="margin: auto">
					<input type="submit" value="管理員修改"> 
					<input type="hidden" name="userNo" value="<%=userVO.getUserNo()%>"> 
					<input type="hidden" name="action" value="getOne_For_Update">
				</form>
			</td>
			
			<td align="center">
				<form action="<%=request.getContextPath()%>/user.do" method="post" style="margin: auto">
					<input type="submit" value="自行修改"> 
					<input type="hidden" name="userNo" value="<%=userVO.getUserNo()%>"> 
					<input type="hidden" name="action" value="getOne_For_Update_themselves">
				</form>
			</td>
			
			<td>
			  <FORM action="<%=request.getContextPath()%>/user.do" method="post" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除"  onclick="javascript:return check();">
			     <input type="hidden" name="userNo"  value="<%=userVO.getUserNo()%>">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>

			<%
				}
			%>


		</tr>

	</table>

<script>
function check() {
	var msg = "您確定要刪除嗎？";
	if (confirm(msg)==true){
		return true;
	}else{
		return false;
	}
}
</script>
</body>
</html>