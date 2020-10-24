<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.video.model.*"%>
<%@ page import="com.timetable.model.*"%>
<%@ page import="com.course.model.*"%>

<%
String courseNo =request.getParameter("courseNo");
pageContext.setAttribute("courseNo", courseNo);

VideoVO videoVO = (VideoVO) request.getAttribute("videoVO");
pageContext.setAttribute("videoVO", videoVO);

String timetableNo = request.getParameter("timetableNo"); 
TimetableService timetableSvc = new TimetableService();
TimetableVO timetableVO =  timetableSvc.getOneTimetable(timetableNo);
pageContext.setAttribute("timetableVO", timetableVO);




%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>影片資料修改 - update_video_input.jsp</title>

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
	width: 900px;
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
<body bgcolor="white">

<table id="table-1">
	<tr><td>
		<h3>影片紀錄修改 - update_video_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/video/select_page.jsp"><img src="<%=request.getContextPath() %>/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤列表 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/video/video.do" NAME="form1" ENCTYPE="multipart/form-data">
<table>
	<tr>
		<td>影片編號:<font color=red><b>*</b></font></td>
		<td>${videoVO.videoNo}</td>
	</tr>
	<tr>
		<td>課表編號:</td>
		<td>${timetableVO.timetableNo}</td>
	</tr>

	<tr>
		<td>影片:</td>
		<td><input type="file" name="upfile2" ></td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="update">

<input type="hidden" name="videoNo" value="<%=videoVO.getVideoNo()%>">
<input type="hidden" name="timetableNo" value="${timetableVO.timetableNo}">
<input type="hidden" name="courseNo" value="<%=courseNo%>">
<input type="submit" value="送出修改">
</FORM>
</body>
</html>