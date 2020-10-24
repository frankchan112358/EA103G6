<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.video.model.*"%>
<%@ page import="com.timetable.model.*"%>
<%
String courseNo =request.getParameter("courseNo");
pageContext.setAttribute("courseNo", courseNo);

VideoVO videoVO = (VideoVO)request.getAttribute("videoVO");
pageContext.setAttribute("videoVO", videoVO);

String timetableNo = request.getParameter("timetableNo");
TimetableService timetableSvc = new TimetableService();
TimetableVO choose_timetableVO =  timetableSvc.getOneTimetable(timetableNo);
pageContext.setAttribute("choose_timetableVO", choose_timetableVO);
%>
<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>影片新增資料 - addVideo.jsp</title>

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

	<table id="table-1">
		<tr>
			<td>
				<h3>影片新增 - addVideo</h3>
			</td>
			<td>
				<h4>
					<a
						href="<%=request.getContextPath()%>/back-end/video/select_page.jsp"><img
						src="<%=request.getContextPath()%>/images/back1.gif" width="100"
						height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤列表 --%>

	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/video/video.do" name="form1"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>課表編號:</td>
				<td>${choose_timetableVO.timetableNo}</td>
				<input type="hidden" name="timetableNo" value="${choose_timetableVO.timetableNo}">
			</tr>
			
			<tr>
				<td>影片上傳:</td>
				<td><input type="file" name="upfile2">
			</tr>

		</table>
		<br> 
		<input type="hidden" name="action" value="insert"> 
		<input type="hidden" name="courseNo" value="<%=courseNo%>">
		<input type="submit" value="送出新增">
	</FORM>
</body>

</html>