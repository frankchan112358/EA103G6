<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.report.model.*"%>

<%
ReportVO reportVO=(ReportVO)request.getAttribute("reportVO");
ReportService reportSvc= new ReportService();
%>

<html>
<head>
<title>檢舉資料 - listOneReport.jsp</title>

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
		 <h3>檢舉資料 - ListOneReport.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/report/forum_select_page.jsp"><img src="<%=request.getContextPath() %>/back-end/report//images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
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
	<tr>
		<td><%=reportVO.getReportNo()%></td>
		<td><%=reportVO.getForumPostNo()%></td>
		<td><%=reportVO.getForumCommentNo()%></td>
		<td><%=reportVO.getStudentNo()%></td>
		<td><%=reportSvc.getReportTypeText(reportVO.getType())%></td>
		<td><%=reportVO.getDescription()%></td>
		<td><%=reportVO.getReportTime()%></td>
		<td><%=reportSvc.getReportStatusText(reportVO.getStatus())%></td>
	</tr>
</table>
</body>
</html>