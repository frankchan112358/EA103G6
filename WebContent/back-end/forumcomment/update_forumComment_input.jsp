<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.forumcomment.model.*"%>

<%
	ForumCommentVO forumCommentVO = (ForumCommentVO) request.getAttribute("forumCommentVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>修改留言 - update_forumComment_input.jsp</title>

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
<jsp:useBean id="forumcommentSvc" scope="page" class="com.forumcomment.model.ForumCommentService" />


	<table id="table-1">
		<tr>
			<td>
				<h3>修改留言 - update_forumPost_input.jsp</h3>
				<h4>
					<a
						href="<%=request.getContextPath()%>/back-end/forumcomment/forum_select_page.jsp"><img
						src="<%=request.getContextPath()%>/back-end/forumcomment/images/tomcat.png"
						width="100" height="32" border="0">回首頁</a>
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

	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/forumComment/forumComment.do"
		name="form1">
		<table>

			<tr>
			<td>
				  貼文編號:
			</td>
			<td>
				<select size="1" name="forumPostNo">
					<option value="1" ${('1'==forumCommentVO.forumPostNo)?'selected':''} >1</option>
					<option value="2" ${('2'==forumCommentVO.forumPostNo)?'selected':''} >2</option>
					<option value="3" ${('3'==forumCommentVO.forumPostNo)?'selected':''} >3</option>
				</select>
			</td>
	</tr>

	<tr>
			<td>
				  學員編號:
			</td>
			<td>
				<select size="1" name="studentNo">
					<option value="S000001" ${('S000001'==forumCommentVO.studentNo)?'selected':''} >S000001</option>
					<option value="S000002" ${('S000002'==forumCommentVO.studentNo)?'selected':''} >S000002</option>
					<option value="S000003" ${('S000003'==forumCommentVO.studentNo)?'selected':''} >S000003</option>
				</select>
			</td>
	</tr>
	<tr>
			<td>
				  貼文留言:
			</td>
			<td>
				<textarea style="resize:none;" name="content" rows="5" cols="47"><%=(forumCommentVO==null)?"學員可以在留言":forumCommentVO.getContent() %></textarea>
			</td>
	</tr>

		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="forumCommentNo"
			value="<%=forumCommentVO.getForumCommentNo()%>"> <input
			type="submit" value="送出修改">
	</FORM>
</body>




<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>
</html>