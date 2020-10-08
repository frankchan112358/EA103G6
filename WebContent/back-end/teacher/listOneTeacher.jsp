<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.user.model.*"%>
<%@ page import="com.teacher.model.*"%>
<!DOCTYPE html>

<%
	UserVO userVO = (UserVO) request.getAttribute("userVO"); 
%>
<%
	TeacherVO teacherVO = (TeacherVO) request.getAttribute("teacherVO"); 
%>

<html>
<head>

<title>listOneUser.jsp</title>
<style>
a {
	text-decoration: none;
	color: green;
}

a:hover {
	color: red;
	text-decoration: underline;
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
		<a href="<%=request.getContextPath() %>/back-end/user/select_page.jsp">回首頁</a>
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
			<th>帳號狀態</th>
			<th>技能</th>
			<th>描述</th>
			<th>講師狀態</th>
			<th>大頭照</th>
			<th>修改</th>
			<th>刪除</th>
			
		</tr>
		<tr>
			<td><%=userVO.getUserNo()%></td>
			<td><%=userVO.getAccount()%></td>
			<td><%=userVO.getPassword()%></td>
			<td>
				<%if (userVO.getType() == 0) {
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
			
			<td><%=teacherVO.getSkill() %></td>
			<td><%=teacherVO.getDescription() %></td>
			
			<td>
				<%
					if (teacherVO.getTeacherStatus()==0) {
						out.print("離職");
					} else if (teacherVO.getTeacherStatus()==1) {
						out.print("在職");
					} else {
						out.print("停職");
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
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/user.do" style="margin:auto">
					<input type="submit" value="修改"> <input type="hidden"
						name="userNo" value="<%=userVO.getUserNo()%>"> <input
						type="hidden" name="action" value="getOne_For_Update">
				</FORM>
			</td>
			
			<td align="center">
			  <FORM action="<%=request.getContextPath()%>/user.do" method="post" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除" onclick="javascript:return check();">
			     <input type="hidden" name="userNo"  value="<%=userVO.getUserNo()%>">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>

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