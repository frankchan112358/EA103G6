<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.leave.model.*"%>

<%
LeaveVO leaveVO=(LeaveVO)request.getAttribute("leaveVO");
LeaveService leaveSvc= new LeaveService();
%>

<html>
<head>
<title>請假資料 - listOneLeave.jsp</title>

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

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>請假資料 - ListOneLeave.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/leave/select_page.jsp"><img src="<%=request.getContextPath() %>/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>請假編號</th>
		<th>學員編號</th>
		<th>課表編號</th>
		<th>假別</th>
		<th>描述</th>
		<th>審核狀態</th>
	</tr>
	<tr>
		<td><%=leaveVO.getLeaveNo()%></td>
		<td><%=leaveVO.getStudentNo()%></td>
		<td><%=leaveVO.getTimetableNo()%></td>
		<td><%=leaveSvc.getLeaveTypeText(leaveVO.getType())%></td>
		<td><%=leaveVO.getDescription()%></td>
		<td><%=leaveSvc.getLeaveStatusText(leaveVO.getStatus())%></td>
	</tr>
</table>
</body>
</html>