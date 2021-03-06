<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.courseask.model.*"%>

<%
	CourseAskVO courseAskVO = (CourseAskVO) request.getAttribute("courseAskVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>修改</title>

<style>
table#table-1 {
	background-color: #c8c8c8;
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

table {
	width: 450px;
	background-color: #c8c8c8;
	margin-top: 1px;
	margin-bottom: 1px;
}

input, select, textarea {
	padding: 5px 15px;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}

td {
	padding: 1px;
	border: 0px solid #CCCCFF;
}
</style>

</head>
<body bgcolor='#c8c8c8'>

	<table id="table-1">
		<tr>
			<td>
				<h3>提問修改</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/courseAsk/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<jsp:useBean id="courseSvc" scope="page"
		class="com.course.model.CourseService" />
		
		<jsp:useBean id="studentSvc" scope="page"
		class="com.student.model.StudentService" />

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/courseAsk/courseAsk.do" name="form1">
		<table>
		
			<tr>
				<td>課程名稱:<font color=red><b>*</b></font></td>
 				<td>${courseSvc.getOneCourse(courseAskVO.getCourseNo()).courseName}</td>
				
			</tr>
			
			<tr>
				<td>學生名字:</td>
				<td>${studentSvc.getOneStudent(courseAskVO.getStudentNo()).studentName}</td>
			</tr>

			<tr>
				<td>標題:</td>
				<td><input type="TEXT" name=title size="40"
					value="<%=courseAskVO.getTitle()%>" /></td>
			</tr>
			<tr>
				<td>問題:</td>
				<td><input type="TEXT" name="question" size="40"
					value="<%=courseAskVO.getQuestion()%>" /></td>
			</tr>
			<tr>
				<td>狀態:</td>
				<td><input type="TEXT" name="status" size="40"
					value="<%=courseAskVO.getStatus()%>" /></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="update"> 
 		     <input type="hidden" name="courseNo" value="<%=courseAskVO.getCourseNo()%>">
		     <input type="hidden" name="courseAskNo" value="<%=courseAskVO.getCourseAskNo()%>">
		     <input type="hidden" name="studentNo" value="<%=courseAskVO.getStudentNo()%>">
		<input type="submit" value="送出修改">
	</FORM>
</body>


</html>