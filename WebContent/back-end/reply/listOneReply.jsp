<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.reply.model.*"%>
<%@ page import="com.courseask.model.*"%>
<%@ page import="com.teacher.model.*"%>
<%@ page import="com.student.model.*"%>
<%
	ReplyVO replyVO = (ReplyVO) request.getAttribute("replyVO");
%>
<%
	CourseAskService courseAskSvc = new CourseAskService();
	CourseAskVO courseAskVO = courseAskSvc.getOneCourseAsk(replyVO.getCourseAskNo());
%>
<%
	TeacherService teacherSvc = new TeacherService();
	TeacherVO teacherVO = teacherSvc.getOneTeacher(replyVO.getTeacherNo());
%>
<%
	StudentService studentSvc = new StudentService();
	StudentVO studentVO = studentSvc.getOneStudent(replyVO.getStudentNo());
%>


<html>
<head>
<title>回覆資料</title>

<style>
body{
background-color:#c8c8c8;
font-family: DFKai-sb;
}

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
	width: 1200px;
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
		<tr>
			<td>
				<h3>回覆清單</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/reply/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
	
		<tr>
		<th>問題</th>
		<th>講師</th>
		<th>學生</th>
		<th>回覆內容</th>
		<th>回覆時間</th>
		</tr>
		
		<tr>
			<td><%=courseAskVO.getQuestion()%></td>
			<td><%=teacherVO.getTeacherName()%></td>
			<td><%=replyVO.getStudentNo()%></td>
			<td>${replyVO.replyContent}</td>
			<td><fmt:formatDate value="${replyVO.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

		</tr>
	</table>

</body>
</html>