<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*"%>

<%
	UserVO userVO = (UserVO) request.getAttribute("userVO");
%>

<!DOCTYPE html>
<html>
<head>

<title>使用者資料修改 - update_user_input.jsp</title>
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
	width: 150px;
	height: 200px;
}

img {
	max-width: 100%;
	max-height: 100%;
}

</style>

<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body>
	<h5>
		<a href='<%=request.getContextPath() %>/back-end/user/listAllUser.jsp'>顯示目前所有使用者</a>
	</h5>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	 <FORM action="<%=request.getContextPath()%>/user.do" method="post">
		<table>
		
			<div>
				<% if(userVO.getPhoto()==null){ %>
				<img src="<%=request.getContextPath() %>/images/noPicture.png">
				<%}else{ %>
					<img src="<%=request.getContextPath()%>/user.do?action=getPhoto&userNo=<%=userVO.getUserNo()%>">
				<%} %>
			</div>
			<tr>
				<td>使用者編號：</td>
				<td>${userVO.userNo}</td>
			</tr>

			<tr>
				<td>帳號：</td>
				<td>${userVO.account}</td>
			</tr>
			<tr>
				<td>原身分別：</td>
				<td>
				<%if (userVO.getType() == 0) out.print("學生");%>
				<%if (userVO.getType() == 1) out.print("講師");%>
				<%if (userVO.getType() == 2) out.print("學生");%>
				</td>
				
			</tr>

			<tr>
				<td>密碼：</td>
				<td>${userVO.password}</td>
			</tr>

			<tr>
				<td>姓名：</td>
				<td>${userVO.name}</td>
			</tr>

			<tr>
				<td>身分證字號：</td>
				<td>${userVO.id}</td>
			</tr>

			<tr>
				<td>電子信箱：</td>
				<td>${userVO.mail}</td>
			</tr>

			<tr>
				<td>手機號碼：</td>
				<td><%=userVO.getPhone()==null?"無資料":userVO.getPhone() %></td>
			</tr>

			<tr>
				<td>地址：</td>
				<td><%=userVO.getAddress()==null?"無資料":userVO.getAddress() %></td>
			</tr>

			
		</table>
		<br>  <input type="submit" value="刪除"  onclick="javascript:return check();">
			  <input type="hidden" name="userNo"  value="<%=userVO.getUserNo()%>">
			  <input type="hidden" name="action" value="delete"></FORM>


<script>

$(document).ready(function(){
	alert("此人已離職或結訓，建議刪除使用者");
});


function check() {
	var msg = "您確定要送出嗎？";
	if (confirm(msg)==true){
		return true;
	}else{
		return false;
	}
}
</script>
</body>
</html>