<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.report.model.*"%>
<%
ReportService reportSvc= new ReportService();
pageContext.setAttribute("reportSvc", reportSvc);
%>
<html>
<head>
<title>所有檢舉資料 - listAllReport.jsp</title>

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

<table id="table-1">
	<tr><td>
		 <h3>所有檢舉資料 - listAllReport.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/report/forum_select_page.jsp"><img src="<%=request.getContextPath() %>/back-end/report/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>檢舉編號</th>
		<th>貼文編號</th>
		<th>留言編號</th>
		<th>學員編號</th>
		<th>檢舉類型</th>
		<th>描述</th>
		<th>檢舉時間</th>
		<th>檢舉狀態</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="reportVO" items="${reportSvc.all}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${reportVO.reportNo}</td>
			<td>${reportVO.forumPostNo}</td>
			<td>${reportVO.forumCommentNo}</td>
			<td>${reportVO.studentNo}</td>
			<td>${reportSvc.getReportTypeText(reportVO.type)}</td>
			<td>${reportVO.description}</td>
			<td>${reportVO.reportTime}</td>
			<td>${reportSvc.getReportStatusText(reportVO.status)}</td> 
			<td>
			  <form method="post" action="<%=request.getContextPath()%>/report/report.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="reportNo"  value="${reportVO.reportNo}">
			     <input type="hidden" name="action"	value="getOne_For_Update">
		      </form>
			</td>
			<td>
			  <form method="post" action="<%=request.getContextPath()%>/report/report.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="reportNo"  value="${reportVO.reportNo}">
			     <input type="hidden" name="action" value="delete">
		      </form>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>