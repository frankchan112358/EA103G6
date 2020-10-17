<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.courseask.model.*"%>
<%@ page import="com.course.model.*"%>
<%@ page import="com.student.model.*"%>
<%
	CourseAskVO courseAskVO = (CourseAskVO) request.getAttribute("courseAskVO");
%>
<%
	CourseService courseSvc = new CourseService();
	CourseVO courseVO = courseSvc.getOneCourse(courseAskVO.getCourseNo());
%>

<%
	StudentService studentSvc = new StudentService();
	StudentVO studentVO = studentSvc.getOneStudent(courseAskVO.getStudentNo());
%>


<html>
<head>
<title>提問資料</title>

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
				<h3>提問資料</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/courseAsk/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
	
		<tr>
		<th>課程</th>
		<th>學生</th>
		<th>標題</th>
		<th>問題</th>
		<th>時間</th>
		</tr>
		
		<tr>
			<td><%=courseVO.getCourseName()%></td>
			<td><%=studentVO.getStudentName()%></td>
			<td>${courseAskVO.title}</td>
			<td>${courseAskVO.question}</td>
			<td><fmt:formatDate value="${courseAskVO.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

		</tr>
	</table>

</body>
</html>