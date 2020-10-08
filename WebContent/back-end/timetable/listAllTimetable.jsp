<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.timetable.model.*"%>

<%
	TimetableService timetableSvc = new TimetableService();
	List<TimetableVO> list = timetableSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>所有課表資料 - listAllTimetable.jsp</title>

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
	width: 800px;
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
		<h3>所有課表資料 - listAllTimetable.jsp</h3>
		<h4><a href="<%=request.getContextPath() %>/back-end/timetable/select_page.jsp"><img src="<%=request.getContextPath() %>/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤列表 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>課表編號</th>
		<th>課程編號</th>
		<th>教室編號</th>
		<th>上課時段</th>
		<th>上課日期</th>
		<th>教學日誌</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %>
	<c:forEach var="timetableVO" items="${list}" begin="<%= pageIndex%>" end="<%=pageIndex+rowsPerPage-1 %>">

		<tr>
			<td>${timetableVO.timetableNo}</td>
			<td>${timetableVO.courseNo}</td>
			<td>${timetableVO.classroomNo}</td>
			<td>${timetableVO.timetablePeriod}</td>
			<td>${timetableVO.timetableDate}</td>
			<td>${timetableVO.teachingLog}</td>
			<td>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/timetable/timetable.do" style="margin-bottom:0px;">
					<input type="submit" value="修改">
					<input type="hidden" name="timetableNo" value="${timetableVO.timetableNo}">
					<input type="hidden" name="action" value="getOne_For_Update"></FORM>
			</td>
			<td>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/timetable/timetable.do" style="margin-bottom:0px;">
					<input type="submit" value="刪除">
					<input type="hidden" name="timetableNo" value="${timetableVO.timetableNo}">
					<input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</body>
</html>