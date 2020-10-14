<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.classroom.model.*"%>

<%
	ClassroomVO classroomVO = (ClassroomVO) request.getAttribute("classroomVO");
%>

<html>
<head>
<title>教室資料</title>

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
</style>

<style>
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
				<h3>公告資料</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/classroom/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
	
		<tr>
		<th>教室名稱</th>
		<th>學生人數</th>
		</tr>
		
		<tr>
			<td>${classroomVO.classroomName}</td>
			<td>${classroomVO.numberOfStudent}</td>

		</tr>
	</table>

</body>
</html>