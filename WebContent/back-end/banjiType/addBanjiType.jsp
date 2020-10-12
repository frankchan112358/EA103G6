<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banjitype.model.*"%>

<%
	BanjiTypeVO banjiTypeVO = (BanjiTypeVO) request.getAttribute("banjiTypeVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>新增班種</title>
<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}

input, select, textarea {
	padding: 5px 15px;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}

table {
	width: 500px;
	background-color: #c8c8c8;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
	font-family: DFKai-sb;
}
</style>

</head>
<body bgcolor=#c8c8c8>
	<table id="table-1">
		<tr>
			<td>
				<h3>新增班種</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/banjiType/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>


	<h3>內容:</h3>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banjiType/banjiType.do" name="form1">
		<table>

			<tr>
				<td>班種名稱:</td>
				<td><input type="TEXT" name="banjiTypeName" size="40"
					value="<%=(banjiTypeVO == null) ? "java" : banjiTypeVO.getBanjiTypeName()%>" /></td>
			</tr>


<tr>
				<td>上課時數:</td>
				<td><input type="TEXT" name="classHours" size="40"
					value="<%=(banjiTypeVO == null) ? "600" : banjiTypeVO.getClassHours()%>" /></td>
			</tr>
		

			<tr>
				<td>內容:</td>
				<td><textarea name="banjiTypeContent"
						style="resize: none; width: 300px; height: 150px;"><%=(banjiTypeVO == null) ? "安安" : banjiTypeVO.getBanjiTypeContent()%></textarea></td>
			</tr>
			
				<tr>
				<td>啟用:</td>
				<td><input type="TEXT" name="banjiTypeEnable" size="40"
					value="<%=(banjiTypeVO == null) ? "1" : banjiTypeVO.getBanjiTypeEnable()%>"></td>
			</tr>
			
			
		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出">
	</FORM>
</body>

</html>