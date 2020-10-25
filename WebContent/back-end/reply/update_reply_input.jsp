<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.reply.model.*"%>

<%
	ReplyVO replyVO = (ReplyVO) request.getAttribute("replyVO");
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
				<h3>回覆修改</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/reply/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
		
		<jsp:useBean id="studentSvc" scope="page"
				class="com.student.model.StudentService" />
		
			<jsp:useBean id="teacherSvc" scope="page"
				class="com.teacher.model.TeacherService" />

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reply/reply.do" name="form1">
		<table>
			<tr>
				<td>講師名稱:</td>
				<td><input type="TEXT" name=teacherNo size="40"
					value="<%=replyVO.getTeacherNo()%>" /></td>
			</tr>


			<tr>
				<td>學生名稱:<font color=red><b>*</b></font></td>
				<td><input type="TEXT" name=studentNo size="40"
					value="<%=replyVO.getStudentNo()%>" /></td>
			</tr>			

			<tr>
				<td>回覆內容:</td>
				<td><textarea name="replyContent"
						style="resize: none; width: 300px; height: 150px;"><%=(replyVO == null) ? "是的" : replyVO.getReplyContent()%></textarea></td>
			</tr>
			
	
		</table>
		<br> <input type="hidden" name="action" value="update"> 
		     <input type="hidden" name="teacherNo" value="<%=replyVO.getTeacherNo()%>">
		     <input type="hidden" name="replyNo" value="<%=replyVO.getReplyNo()%>">
		      <input type="hidden" name="courseAskNo" value="<%=replyVO.getCourseAskNo()%>">
		<input type="submit" value="送出修改">
	</FORM>
</body>


</html>