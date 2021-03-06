<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.report.model.*"%>

<%
  ReportVO reportVO = (ReportVO) request.getAttribute("reportVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>檢舉資料修改 - update_report_input.jsp</title>

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
		 <h3>檢舉資料修改 - update_report_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/report/forum_select_page.jsp"><img src="<%=request.getContextPath() %>/back-end/report/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>檢舉修改:</h3>
<form method="post" action="<%=request.getContextPath() %>/report/report.do" name="form1">
	<table>
		<tr>
			<td>檢舉編號:<font color=red><b>*</b></font></td>
			<td><%=reportVO.getReportNo()%></td>
		</tr>
		
		<jsp:useBean id="forumpostSvc" scope="page"
				class="com.forumpost.model.ForumPostService" />

			<tr>
				<td>貼文編號:</td>
				<td><select size="1" name="forumPostNo">
						<c:forEach var="forumPostVO" items="${forumpostSvc.all}">
							<option value="${forumPostVO.forumPostNo}"
								${(reportVO.forumPostNo==forumPostVO.forumPostNo)? 'selected':'' }>${forumPostVO.forumPostNo}
						</c:forEach>
				</select></td>
			</tr>
		
		<jsp:useBean id="forumcommentSvc" scope="page"
				class="com.forumcomment.model.ForumCommentService" />

			<tr>
				<td>留言編號:</td>
				<td><select size="1" name="forumCommentNo">
						<c:forEach var="forumCommentVO" items="${forumcommentSvc.all}">
							<option value="${forumCommentVO.forumCommentNo}"
								${(reportVO.forumCommentNo==forumCommentVO.forumCommentNo)? 'selected':'' }>${forumCommentVO.forumCommentNo}
						</c:forEach>
				</select></td>
			</tr>
		
		<jsp:useBean id="studentSvc" scope="page"
				class="com.student.model.StudentService" />

			<tr>
				<td>學員編號:</td>
				<td><select size="1" name="studentNo">
						<c:forEach var="studentVO" items="${studentSvc.all}">
							<option value="${studentVO.studentNo}"
								${(reportVO.studentNo==studentVO.studentNo)? 'selected':'' }>${studentVO.studentNo}
						</c:forEach>
				</select></td>
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
				<textarea style="resize:none;" name="description" rows="5" cols="47"><%=reportVO.getDescription() %></textarea>
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
<input type="hidden" name="action" value="update">
<input type="hidden" name="reportNo" value="<%=reportVO.getReportNo() %>">
<input type="submit" value="送出修改"></FORM>

</body>
</html>