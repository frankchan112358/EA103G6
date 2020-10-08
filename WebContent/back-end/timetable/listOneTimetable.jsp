<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.timetable.model.*"%>

<%
	TimetableVO timetableVO = (TimetableVO) request.getAttribute("timetableVO");
%>

<html>
<head>
<title>課表資料 - listOneTimetable.jsp</title>

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
</style>

<style>
  table {
	width: 600px;
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
	<tr><td>
		<h3>員工資料 - ListOneTimetable.jsp</h3>
		<h4><a href="<%=request.getContextPath() %>/back-end/timetable/select_page.jsp"><img src="<%=request.getContextPath() %>/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>課表編號</th>
		<th>課程編號</th>
		<th>教室編號</th>
		<th>上課時段</th>
		<th>上課日期</th>
		<th>教學日誌</th>
	</tr>
	<tr>
		<td><%=timetableVO.getTimetableNo()%></td>
		<td><%=timetableVO.getCourseNo() %></td>
		<td><%=timetableVO.getClassroomNo() %></td>
		<td><%=timetableVO.getTimetablePeriod() %></td>
		<td><%=timetableVO.getTimetableDate() %></td>
		<td><%=timetableVO.getTeachingLog() %></td>
	</tr>
</table>

</body>
</html>