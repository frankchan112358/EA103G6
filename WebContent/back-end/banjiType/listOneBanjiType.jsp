<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.banji.model.*"%>
<%@ page import="com.banjitype.model.*"%>
<%
	BanjiTypeVO banjiTypeVO = (BanjiTypeVO) request.getAttribute("banjiTypeVO");
%>

<html>
<head>
<title>班種資料</title>

<style>
body{
background-color:#c8c8c8;
font-family: DFKai-sb;
}

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

table {
	width: 1200px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>班種資料</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/banjiType/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
		<th>班種名稱</th>
		<th>上課時數</th>
		<th>內容</th>
		<th>狀態</th>

		</tr>
		<tr>
			<td><%=banjiTypeVO.getBanjiTypeName()%></td>
			<td><%=banjiTypeVO.getClassHours()%></td>
			<td><%=banjiTypeVO.getBanjiTypeContent()%></td>
			<td><%=banjiTypeVO.getBanjiTypeEnable()%></td>
		</tr>
	</table>

</body>
</html>