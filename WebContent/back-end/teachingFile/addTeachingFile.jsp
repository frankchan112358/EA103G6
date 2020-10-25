<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.teachingfile.model.*" %>
<jsp:useBean id="timetableSvc" scope="page" class="com.timetable.model.TimetableService" />
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<%
String courseNo =request.getParameter("courseNo");
pageContext.setAttribute("courseNo", courseNo);

TeachingFileVO teachingFileVO = (TeachingFileVO) request.getAttribute("teachingFileVO");
pageContext.setAttribute("teachingFileVO", teachingFileVO);
if(teachingFileVO != null){
System.out.println(teachingFileVO.getTimetableNo());}
%>
<html>

<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>教材新增資料 - addTeachingFile.jsp</title>

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

		table,
		th,
		td {
			border: 0px solid #CCCCFF;
		}

		th,
		td {
			padding: 1px;
		}
	</style>

</head>

<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>影片新增 - addTeachingFile</h3>
			</td>
			<td>
				<h4><a href="<%=request.getContextPath() %>/back-end/teachingFile/select_page.jsp"><img src="<%=request.getContextPath() %>/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤列表 --%>

	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/teachingFile/teachingFile.do" name="form1" enctype="multipart/form-data">
		<table>
			<tr>
				<td>課程編號:</td>
				<td>${courseNo}</td>
			</tr>
			<tr>
				<td>課表編號:</td>
				<td>
					<select size="1" name="timetableNo">
						<c:forEach var="timetableVO" items="${timetableSvc.all}">
							<c:if test="${courseNo eq timetableVO.courseNo }">
								<option value="${timetableVO.timetableNo}">${timetableVO.timetableDate}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>教材名稱:</td>
				<td><input type="TEXT" name="teachingFileName" size="45"
								 value="<%= (teachingFileVO==null)? "2020-10-10 上午" : teachingFileVO.getTeachingFileName()%>" /></td>
			</tr>
			<tr>
				<td>教材上傳:</td>
				<td><input type="file" name="upfile2">
					<!-- 				$('input[name=upfile2]').value.split("\\")[$('input[name=upfile2]').value.split("\\").length-1] -->
			</tr>

		</table>
		<br>
		<input type="hidden" name="action" value="insert">
		<input type="hidden" name="courseNo" value="${courseNo}">
		<input type="submit" value="送出新增">
	</FORM>
</body>

</html>