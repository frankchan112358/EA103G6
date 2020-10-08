<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.leave.model.*"%>
<%
LeaveService leaveSvc= new LeaveService();
pageContext.setAttribute("leaveSvc", leaveSvc);
%>
<html>
<head>
<title>所有請假資料 - listAllLeave.jsp</title>

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
	width: 800px;
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

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有請假資料 - listAllLeave.jsp</h3>
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
	<%@ include file="page1.file" %> 
	<c:forEach var="leaveVO" items="${leaveSvc.all}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${leaveVO.leaveNo}</td>
			<td>${leaveVO.studentNo}</td>
			<td>${leaveVO.timetableNo}</td>
			<td>${leaveSvc.getLeaveTypeText(leaveVO.type)}</td>
			<td>${leaveVO.description}</td>
			<td>${leaveSvc.getLeaveStatusText(leaveVO.status)}</td> 
			<td>
			  <form method="post" action="<%=request.getContextPath()%>/leave/leave.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="leaveNo"  value="${leaveVO.leaveNo}">
			     <input type="hidden" name="action"	value="getOne_For_Update">
		      </form>
			</td>
			<td>
			  <form method="post" action="<%=request.getContextPath()%>/leave/leave.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="leaveNo"  value="${leaveVO.leaveNo}">
			     <input type="hidden" name="action" value="delete">
		      </form>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>