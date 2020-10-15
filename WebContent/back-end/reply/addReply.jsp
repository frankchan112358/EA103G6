<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.reply.model.*"%>

<%
	ReplyVO replyVO = (ReplyVO) request.getAttribute("replyVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>新增回覆</title>
<style>
table#table-1 {
	background-color: #01814A;
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

input, select, textarea {
	padding: 5px 15px;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}

table {
	width: 500px;
	background-color: #c8c8c8;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
	width: 600px;
}

th, td {
	padding: 1px;
	font-family: DFKai-sb;
}
</style>

</head>
<body bgcolor=#c8c8c8>
	<table id="table-1">
		<tr>
			<td>
				<h3>新增回覆</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/reply/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>



	<h3>內容:</h3>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reply/reply.do" name="form1">
		<table>

	<jsp:useBean id="courseAskSvc" scope="page"
				class="com.courseask.model.CourseAskService" />
				
			<tr>
				<td>回覆標題:</td>
				<td><select size="1" name="courseAskNo">
						<c:forEach var="courseAskVO" items="${courseAskSvc.all}">
							<option value="${courseAskVO.courseAskNo}"
								${(replyVO.courseAskNo==courseAskVO.courseAskNo)? 'selected':'' }>${courseAskVO.title}
						</c:forEach>
				</select></td>
			</tr>

			<jsp:useBean id="teacherSvc" scope="page"
				class="com.teacher.model.TeacherService" />

			<tr>
				<td>講師名稱:</td>
				<td><select size="1" name="teacherNo">
						<c:forEach var="teacherVO" items="${teacherSvc.all}">
							<option value="${teacherVO.teacherNo}"
								${(replyVO.teacherNo==teacherVO.teacherNo)? 'selected':'' }>${teacherVO.teacherName}
						</c:forEach>
				</select></td>
			</tr>

			<jsp:useBean id="studentSvc" scope="page"
				class="com.student.model.StudentService" />
				

			<tr>
				<td>學生名稱:<font color=red><b>*</b></font></td>
				<td><select size="1" name="studentNo">
						<c:forEach var="studentVO" items="${studentSvc.all}">
							<option value="${studentVO.studentNo}"
								${(courseAskVO.studentNo==studentVO.studentNo)? 'selected':'' }>${studentVO.studentName}
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td>回覆內容:</td>
				<td><textarea name="replyContent"
						style="resize: none; width: 300px; height: 150px;"><%=(replyVO == null) ? "是的" : replyVO.getReplyContent()%></textarea></td>
			</tr>
			
		</table>
		<br> <input type="hidden" name="action" value="insert"> 
<%-- 		<input type="hidden" name="teacherNo" value="<%=replyVO.getTeacherNo()%>">  --%>
<%-- 		<input type="hidden" name="studentNo" value="<%=replyVO.getStudentNo()%>">  --%>
			<input type="submit" value="送出">
	</FORM>
</body>

</html>