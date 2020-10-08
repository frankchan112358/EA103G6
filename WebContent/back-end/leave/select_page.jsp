<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>WJL Leave: Home</title>

<style>
table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
	border: 3px ridge Gray;
	height: 80px;
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

</head>
<body bgcolor='white'>
	<table id="table-1">
	   <tr><td><h3>WJL Leave: Home</h3><h4>( MVC )</h4></td></tr>
	</table>
	
	<p>This is the Home page for WJL Leave: Home</p>
	
	<h3>資料查詢:</h3>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
		    <c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<ul>
		<li>
			<a href="<%=request.getContextPath() %>/back-end/leave/listAllLeave.jsp">List</a> all Leaves. <br><br>
		</li>
		<li>
			<form method="post" action="<%=request.getContextPath() %>/leave/leave.do">
				<b>輸入請假編號(如1):</b>
				<input type="text" name="leaveNo"/>
				<input type="hidden" name="action" value="getOne_For_Display"/>
				<input type="submit" value="送出"/>
			</form>
		</li>
		
		<jsp:useBean id="leaveSvc" scope="page" class="com.leave.model.LeaveService" />
		
		<li>
			<form method="post" action="<%=request.getContextPath() %>/leave/leave.do">
				<b>選擇請假編號:</b>
				<select size="1" name="leaveNo">
					<c:forEach var="leaveVO" items="${leaveSvc.all }" >
						<option value="${leaveVO.leaveNo }">${leaveVO.leaveNo }</option>
					</c:forEach> 
				</select>
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</form>
		</li>
	</ul>
	
	<h3>請假管理</h3>
	
	<ul>
	  <li><a href='<%=request.getContextPath() %>/back-end/leave/addLeave.jsp'>Add</a> a new Leave.</li>
	</ul>

</body>
</html>