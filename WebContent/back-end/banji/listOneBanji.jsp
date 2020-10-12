<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.banji.model.*"%>
<%@ page import="com.banjitype.model.*"%>
<%@ page import="com.classroom.model.*"%>
<%@ page import="com.emp.model.*"%>
<%
	BanjiVO banjiVO = (BanjiVO) request.getAttribute("banjiVO");
%>
<%
	BanjiTypeService banjiTypeSvc = new BanjiTypeService();
	BanjiTypeVO banjiTypeVO = banjiTypeSvc.getOneBanjiType(banjiVO.getBanjiTypeNo());
%>
<%
	ClassroomService classroomSvc = new ClassroomService();
	ClassroomVO classroomVO = classroomSvc.getOneClassroom(banjiVO.getClassroomNo());
%>

<%
	EmpService empSvc = new EmpService();
	EmpVO empVO = empSvc.getOneEmp(banjiVO.getEmpNo());
%>

<html>
<head>
<title>班級資料</title>

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
				<h3>班級資料</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/banji/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>導師名稱</th>
			<th>班級種類</th>
			<th>開訓日期</th>
			<th>結訓日期</th>
			<th>班級名稱</th>
			<th>學生人數</th>
			<th>上課時數</th>
			<th>班級教室</th>
			<th>班級內容</th>

		</tr>
		<tr>
			<td><%=empVO.getEmpName()%></td>
			<td><%=banjiTypeVO.getBanjiTypeName()%></td>
			<td>${banjiVO.startDay}</td>
			<td>${banjiVO.endDay}</td>
			<td>${banjiVO.banjiName}</td>
			<td>${banjiVO.numberOfStudent}</td>
			<td>${banjiVO.classHours}</td>
			<td><%=classroomVO.getClassroomName()%></td>
			<td>${banjiVO.banjiContent}</td>
		</tr>
	</table>

</body>
</html>