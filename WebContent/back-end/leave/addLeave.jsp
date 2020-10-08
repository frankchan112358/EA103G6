<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.leave.model.*" %>

<%
	LeaveVO leaveVO = (LeaveVO) request.getAttribute("leaveVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>請假資料新增 - addLeave.jsp</title>

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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>
<jsp:useBean id="leaveSvc" scope="page" class="com.leave.model.LeaveService" />
<table id="table-1">
	<tr><td>
		 <h3>請假資料新增 - addLeave.jsp</h3></td><td>
		 <h4><a href="<%=request.getContextPath() %>/back-end/leave/select_page.jsp"><img src="<%=request.getContextPath() %>/images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<form method="post" action="<%=request.getContextPath() %>/leave/leave.do" name="form1">
	<table>
		<tr>
			<td>
				學員編號:
			</td>
			<td>
				<select size="1" name="studentNo">
					<option value="S000001" ${('S000001'==leaveVO.studentNo)?'selected':''} >S000001</option>
					<option value="S000002" ${('S000002'==leaveVO.studentNo)?'selected':''} >S000002</option>
					<option value="S000003" ${('S000003'==leaveVO.studentNo)?'selected':''} >S000003</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				課表編號:
			</td>
			<td>
				<select size="1" name="timetableNo">
					<option value="TT000001" ${('TT000001'==leaveVO.timetableNo)?'selected':''} >TT000001</option>
					<option value="TT000002" ${('TT000002'==leaveVO.timetableNo)?'selected':''} >TT000002</option>
					<option value="TT000003" ${('TT000003'==leaveVO.timetableNo)?'selected':''} >TT000003</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				假別:
			</td>
			<td>
				<select size="1" name="type">
					<c:forEach var="type" items="${leaveSvc.getLeaveTypeAll()}">
						<option value="${type.num}" ${(leaveVO.type==type.num)?'selected':''}>${type.text}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				描述:
			</td>
			<td>
				<textarea style="resize:none;" name="description" rows="5" cols="47"><%=(leaveVO==null)?"發燒了":leaveVO.getDescription() %></textarea>
			</td>
		</tr>
		<tr>
			<td>
				審核狀態:
			</td>
			<td>
				<select size="1" name="status">
					<c:forEach var="status" items="${leaveSvc.getLeaveStatusAll()}">
						<option value="${status.num}" ${(leaveVO.status==status.num)?'selected':'' }>${status.text}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
	</table>
	<br>
	<input type="hidden" name="action" value="insert"/>
	<input type="submit" value="送出新增"/>
</form>
</body>
</html>