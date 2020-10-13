
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>討論區檢舉</title>

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
	   <tr><td><h3>WJL Report: Home</h3><h4>( MVC )</h4></td></tr>
	</table>
	
	<p>This is the Home page for WJL Report: Home</p>
	
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
			<a href="<%=request.getContextPath() %>/back-end/report/listAllReport.jsp">List</a> all Report. <br><br>
		</li>
		<li>
			<form method="post" action="<%=request.getContextPath() %>/report/report.do">
				<b>輸入檢舉編號(如1):</b>
				<input type="text" name="reportNo"/>
				<input type="hidden" name="action" value="getOne_For_Display"/>
				<input type="submit" value="送出"/>
			</form>
		</li>
		
		<jsp:useBean id="reportSvc" scope="page" class="com.report.model.ReportService" />
		
		<li>
			<form method="post" action="<%=request.getContextPath() %>/report/report.do">
				<b>選擇檢舉編號:</b>
				<select size="1" name="reportNo">
					<c:forEach var="reportVO" items="${reportSvc.all }" >
						<option value="${reportVO.reportNo }">${reportVO.reportNo }</option>
					</c:forEach> 
				</select>
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</form>
		</li>
	</ul>
	
	<h3>檢舉管理</h3>
	
	<ul>
	  <li><a href='<%=request.getContextPath() %>/back-end/report/addReport.jsp'>Add</a> a new Report.</li>
	</ul>

</body>
</html>