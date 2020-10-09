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

<h3>��Ƭd��:</h3>

<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
	<li><a href='listAllVideo.jsp'>List</a> all Video<br><br></li>

	<li><FORM METHOD="post" ACTION="<%=request.getContextPath() %>/video/video.do">
		<b>�п�J�v���s��(�p1):</b>
		<input type="text" name="videoNo">
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="�e�X">
	</FORM>
	</li>

	<jsp:useBean id="videoSvc" scope="page" class="com.video.model.VideoService"/>

	<li>
	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/video/video.do">
		<b>�п�ܼv���s��:</b>
		<select size="1" name="videoNo">
			<c:forEach var="videoVO" items="${videoSvc.all}">
				<option value="${videoVO.videoNo}">${videoVO.videoNo}
			</c:forEach>
		</select>
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="�e�X">
	</FORM>
	</li>

	<li>
		<form METHOD="post" ACTION="<%=request.getContextPath() %>/video/video.do">
		<b>�п�ܽҪ�s��:</b>
		<select size="1" name="videoNo">
		  <c:forEach var="videoVO" items="${videoSvc.all}">
		  	<option value="${videoVO.videoNo}">${videoVO.timetableNo}
		  </c:forEach>
		</select>
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="�e�X">
		</form>
	</li>
</ul>

<h3>�[�ݼv���޲z</h3>

<ul>
	<li><a href="addVideo.jsp">Add</a> a new video.</li>
</ul>
</body>
</html> 