<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM VideoLog: HOME</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='white'>

<table>
	<tr><td><h3>IBM Video: HOME</h3></td></tr>
</table>

<p>This is the Home page for IBM Video: HOME</p>

<h3>資料查詢:</h3>

<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
	<li><a href='listAllVideo.jsp'>List</a> all Video<br><br></li>

	<li><FORM METHOD="post" ACTION="<%=request.getContextPath() %>/video/video.do">
		<b>請輸入影片編號(如1):</b>
		<input type="text" name="videoNo">
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="送出">
	</FORM>
	</li>

	<jsp:useBean id="videoSvc" scope="page" class="com.video.model.VideoService"/>

	<li>
	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/video/video.do">
		<b>請選擇影片編號:</b>
		<select size="1" name="videoNo">
			<c:forEach var="videoVO" items="${videoSvc.all}">
				<option value="${videoVO.videoNo}">${videoVO.videoNo}
			</c:forEach>
		</select>
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="送出">
	</FORM>
	</li>

	<li>
		<form METHOD="post" ACTION="<%=request.getContextPath() %>/video/video.do">
		<b>請選擇課表編號:</b>
		<select size="1" name="videoNo">
		  <c:forEach var="videoVO" items="${videoSvc.all}">
		  	<option value="${videoVO.videoNo}">${videoVO.timetableNo}
		  </c:forEach>
		</select>
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="送出">
		</form>
	</li>
</ul>

<h3>觀看影片管理</h3>

<ul>
	<li><a href="addVideo.jsp">Add</a> a new video.</li>
</ul>
</body>
</html> 