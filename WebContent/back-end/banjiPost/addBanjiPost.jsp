<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banjipost.model.*"%>

<%
	BanjiPostVO banjiPostVO = (BanjiPostVO) request.getAttribute("banjiPostVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>新增班級公告</title>
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
				<h3>新增班級公告</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/banjiPost/select_page.jsp">回首頁</a>
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

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banjiPost/banjiPost.do" name="form1">
		<table>

			<tr>
				<td>班級公告標題:</td>
				<td><input type="TEXT" name="title" size="40"
					value="<%=(banjiPostVO == null) ? "EA103" : banjiPostVO.getTitle()%>" /></td>
			</tr>

			<jsp:useBean id="banjiSvc" scope="page"
				class="com.banji.model.BanjiService" />

			<tr>
				<td>班級名稱:</td>
				<td><select size="1" name="banjiNo">
						<c:forEach var="banjiVO" items="${banjiSvc.all}">
							<option value="${banjiVO.banjiNo}"
								${(banjiPostVO.banjiNo==banjiVO.banjiNo)? 'selected':'' }>${banjiVO.banjiName}
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td>公告內容:</td>
				<td><textarea name="banjiPostContent"
						style="resize: none; width: 300px; height: 150px;"><%=(banjiPostVO == null) ? "安安" : banjiPostVO.getBanjiPostContent()%></textarea></td>
			</tr>
			
				<tr>
				<td>狀態:</td>
				<td><input type="TEXT" name="status" size="40"
					value="<%=(banjiPostVO == null) ? "1" : banjiPostVO.getStatus()%>"></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出">
	</FORM>
</body>

</html>