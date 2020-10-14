<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>教室</title>

<style>
input[type=button] {
	background-color:#336666;
	border: none;
	padding: 16px 32px;
	text-decoration: none;
	font-size: 32px;
	hover
}

a, b {
	font-family: DFKai-sb;
}

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
	color: purple;
	display: block;
	margin-bottom: 1px;
}

input, select, textarea {
	padding: 5px 15px;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}

h4 {
	display: inline;
}
</style>

</head>
<body bgcolor='#c8c8c8'>
	<h1>
		教室管理
	</h1>

	<br>

	<h3>教室管理:</h3>

	<div align="left">
		<input type="button" value="教室資料"
			onclick="location.href='listAllClassroom.jsp'"><br>
	</div>

	<div align="center">
		<input type="button" value="新增教室"
			onclick="location.href='addClassroom.jsp'"><br>
	</div>

	<h3>資料查詢:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

<jsp:useBean id="classroomSvc" scope="page"
		class="com.classroom.model.ClassroomService" />

	<ul>

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/classroom/classroom.do">
				<b>輸入教室編號 (如1):</b> <input type="text" name="classroomNo"> <input
					type="hidden" name="action" value="getOne_For_Display"> <input
					type="submit" value="送出">
			</FORM>
		</li>

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/classroom/classroom.do">
				<b>查看教室:</b> <select size="1" name="classroomNo">
					<c:forEach var="classroomVO" items="${classroomSvc.all}">
						<option value="${classroomVO.classroomNo}">${classroomVO.classroomName}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

	</ul>


</body>

</html>

