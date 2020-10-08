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
	<tr><td><h3>IBM VideoLog: HOME</h3></td></tr>
</table>

<p>This is the Home page for IBM VideoLog: HOME</p>

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
	<li><a href='listAllVideoLog.jsp'>List</a> all VideoLog<br><br></li>

	<li><FORM METHOD="post" ACTION="<%=request.getContextPath() %>/videoLog/videoLog.do">
		<b>�п�J�v���[�ݬ����s��(�p1):</b>
		<input type="text" name="videoLogNo">
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="�e�X">
	</FORM>
	</li>

	<jsp:useBean id="videoLogSvc" scope="page" class="com.videolog.model.VideoLogService"/>

	<li>
	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/videoLog/videoLog.do">
		<b>�п���[�ݼv���O���s��:</b>
		<select size="1" name="videoLogNo">
			<c:forEach var="videoLogVO" items="${videoLogSvc.all}">
				<option value="${videoLogVO.videoLogNo}">${videoLogVO.videoLogNo}
			</c:forEach>
		</select>
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="�e�X">
	</FORM>
	</li>

	<li>
		<form METHOD="post" ACTION="<%=request.getContextPath() %>/videoLog/videoLog.do">
		<b>�п�ܼv���s��:</b>
		<select size="1" name="videoLogNo">
		  <c:forEach var="videoLogVO" items="${videoLogSvc.all}">
		  	<option value="${videoLogVO.videoLogNo}">${videoLogVO.videoNo}
		  </c:forEach>
		</select>
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="�e�X">
		</form>
	</li>
</ul>

<h3>�[�ݬ����޲z</h3>

<ul>
	<li><a href="addVideoLog.jsp">Add</a> a new videoLog.</li>
</ul>
</body>
</html> 