<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.report.model.*" %>

<%
	ReportVO reportVO = (ReportVO) request.getAttribute("reportVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>新增檢舉資料 - addReport.jsp</title>

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
<jsp:useBean id="reportSvc" scope="page" class="com.report.model.ReportService" />
<table id="table-1">
	<tr><td>
		 <h3>新增檢舉資料 - addReport.jsp</h3></td><td>
		 <h4><a href="<%=request.getContextPath() %>/back-end/report/forum_select_page.jsp"><img src="<%=request.getContextPath() %>/back-end/report/images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<form method="post" action="<%=request.getContextPath() %>/report/report.do" name="form1">
	<table>
		<tr>
			<td>
				貼文編號:
			</td>
			<td>
				<select size="1" name="forumPostNo">
					<option value="1" ${('1'==reportVO.forumPostNo)?'selected':''} >1</option>
					<option value="2" ${('2'==reportVO.forumPostNo)?'selected':''} >2</option>
					<option value="3" ${('3'==reportVO.forumPostNo)?'selected':''} >3</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				留言編號:
			</td>
			<td>
				<select size="1" name="forumCommentNo">
					<option value="1" ${('1'==reportVO.forumCommentNo)?'selected':''} >1</option>
					<option value="2" ${('2'==reportVO.forumCommentNo)?'selected':''} >2</option>
					<option value="3" ${('3'==reportVO.forumCommentNo)?'selected':''} >3</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				學員編號:
			</td>
			<td>
				<select size="1" name="studentNo">
					<option value="S000001" ${('S000001'==reportVO.studentNo)?'selected':''} >S000001</option>
					<option value="S000002" ${('S000002'==reportVO.studentNo)?'selected':''} >S000002</option>
					<option value="S000003" ${('S000003'==reportVO.studentNo)?'selected':''} >S000003</option>
				</select>
			</td>
		</tr>	
		<tr>
			<td>
				檢舉類型:
			</td>
			<td>
				<select size="1" name="type">
					<c:forEach var="type" items="${reportSvc.getReportTypeAll()}">
						<option value="${type.num}" ${(reportVO.type==type.num)?'selected':''}>${type.text}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				描述:
			</td>
			<td>
				<textarea style="resize:none;" name="description" rows="5" cols="47"><%=(reportVO==null)?"1.內容含有色情內容、暴力內容、十八禁連結者。":reportVO.getDescription() %></textarea>
			</td>
		</tr>
		<tr>
			<td>
				審核狀態:
			</td>
			<td>
				<select size="1" name="status">
					<c:forEach var="status" items="${reportSvc.getReportStatusAll()}">
						<option value="${status.num}" ${(reportVO.status==status.num)?'selected':'' }>${status.text}</option>
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