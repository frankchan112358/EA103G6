<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banjitype.model.*"%>

<%
	BanjiTypeVO banjiTypeVO = (BanjiTypeVO) request.getAttribute("banjiTypeVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>班種資料修改</title>

<style>
table#table-1 {
	background-color: #c8c8c8;
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

table {
	width: 450px;
	background-color: #c8c8c8;
	margin-top: 1px;
	margin-bottom: 1px;
}

input, select, textarea {
	padding: 5px 15px;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}

td {
	padding: 1px;
	border: 0px solid #CCCCFF;
}
</style>

</head>
<body bgcolor='#c8c8c8'>

	<table id="table-1">
		<tr>
			<td>
				<h3>班種資料修改</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/banjiType/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<jsp:useBean id="banjiTypeSvc" scope="page"
		class="com.banjitype.model.BanjiTypeService" />

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banjiType/banjiType.do" name="form1">
		<table>
			<tr>
				<td>班種編號:</td>
				<td><%=banjiTypeVO.getBanjiTypeNo()%></td>
			</tr>

			<tr>
				<td>班種名稱:<font color=red><b>*</b></font></td>
				<td><input type="TEXT" name="banjiTypeName" size="40"
					value="<%=(banjiTypeVO == null) ? "java" : banjiTypeVO.getBanjiTypeName()%>" /></td>
			</tr>

			<tr>
				<td>上課時數:</td>
				<td><input name="classHours" size="45" type="text"
					value="<%=banjiTypeVO.getClassHours()%>"></td>
			</tr>

			<tr>
				<td>班種內容:</td>
				<td><textarea name="banjiTypeContent"
						style="resize: none; width: 300px; height: 150px;"><%=(banjiTypeVO == null) ? "安安" : banjiTypeVO.getBanjiTypeContent()%></textarea></td>
			</tr>
			
				<tr>
				<td>狀態:</td>
				<td><input type="TEXT" name="banjiTypeEnable" size="45"
					value="<%=banjiTypeVO.getBanjiTypeEnable()%>" /></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="banjiTypeNo" value="<%=banjiTypeVO.getBanjiTypeNo()%>">
		<input type="submit" value="送出修改">
	</FORM>
</body>



</html>