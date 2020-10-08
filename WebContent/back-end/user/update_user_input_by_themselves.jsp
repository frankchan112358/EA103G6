<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*"%>

<%
	UserVO userVO = (UserVO) request.getAttribute("userVO");
%>

<!DOCTYPE html>
<html>
<head>

<title>使用者資料修改 - update_user_input_by_themselves.jsp</title>
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
</head>
<body>
	<h5>
		<a href="<%=request.getContextPath() %>/back-end/user/select_page.jsp">回首頁</a>

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

	<form method="post" action="<%=request.getContextPath()%>/user.do" enctype="multipart/form-data">
		<table>

			<div>
				<%
					if (userVO.getPhoto() == null) {
				%>
				<img src="<%=request.getContextPath()%>/images/noPicture.png">
				<%
					} else {
				%>
				<img src="<%=request.getContextPath()%>/user.do?action=getPhoto&userNo=<%=userVO.getUserNo()%>">
				<%
					}
				%>
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
				<td>身分別：</td>
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
			</tr>

			<tr>
				<td>啟用狀態：</td>
				<td>
					<%
						if (userVO.getEnable() == 0) {
							out.print("停用中");
						} else {
							out.print("啟用中");
					%>

				</td>
			</tr>

			<tr>
				<td>密碼：<font color=red><b>*</b></font></td>
				<td><input type="text" name="password" size="20" value="${userVO.password}"></td>
			</tr>

			<tr>
				<td>姓名：<font color=red><b>*</b></font></td>
				<td><input type="text" name="name" size="20" value="${userVO.name}"></td>
			</tr>

			<tr>
				<td>身分證字號：<font color=red><b>*</b></font></td>
				<td><input type="text" name="id" size="20" value="${userVO.id}"></td>
			</tr>

			<tr>
				<td>電子信箱：<font color=red><b>*</b></font></td>
				<td><input type="text" name="mail" size="40" value="${userVO.mail}"></td>
			</tr>

			<tr>
				<td>手機號碼：</td>
				<td><input type="text" name="phone" size="40" value="${userVO.phone}"></td>
			</tr>

			<tr>
				<td>地址：</td>
				<td><input type="text" name="address" size="60" value="${userVO.address}"></td>
			</tr>

			<tr>
				<td>更換大頭貼：</td>
				<td><input type="file" name="photo" accept="image/*"></td>
			</tr>
		</table><br>
		<input type="hidden" name="action" value="update"> 
		<input type="hidden" name="userNo" value="${userVO.userNo}">
		<input type="hidden" name="account" value="${userVO.account}">
		<input type="hidden" name="type" value="${userVO.type}"> 
		<input type="hidden" name="enable" value="${userVO.enable}">
		 <input type="submit" value="送出修改" onclick="javascript:return check();">
	</form>
	<%
		}
	%>

<script>
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