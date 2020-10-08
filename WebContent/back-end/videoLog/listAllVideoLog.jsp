<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.videoLog1.model.*"%>

<%
	VideoLogService videoLogSvc = new VideoLogService();
	List<VideoLogVO> list = videoLogSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<html>
<head>
<title>所有影片觀看紀錄資料 - listAllVideoLog.jsp</title>


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
		<h3>所有影片觀看紀錄資料 - listAllVideoLog.jsp</h3>
		<h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤列表 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正下錯誤</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>觀看影片記錄編號</th>
		<th>影片編號</th>
		<th>觀看時間</th>
		<th>狀態</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %>
	<c:forEach var="videoLogVO" items="${list}" begin="<%= pageIndex%>" end="<%=pageIndex+rowsPerPage-1 %>">
	
	<tr>
		<td>${videoLogVO.videoLogNo}</td>
		<td>${videoLogVO.videoNo}</td>
		<td>${videoLogVO.watchTime}</td>
		<td>${videoLogVO.status}</td>
		<td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/videoLog/videoLog.do" style="margin-bottom:0px;">
				<input type="submit" value="修改">
				<input type="hidden" name="videoLogNo" value="${videoLogVO.videoLogNo}">
				<input type="hidden" name="action" value="getOne_For_Update">
			</FORM>
		</td>
		<td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/videoLog/videoLog.do" style="margin-bottom:0px;">
				<input type="submit" value="刪除">
				<input type="hidden" name="videoLogNo" value="${videoLogVO.videoLogNo}">
				<input type="hidden" name="action" value="delete">
			</FORM>
		</td>
	</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</body>
</html>