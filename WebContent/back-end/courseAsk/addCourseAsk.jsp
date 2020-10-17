<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.courseask.model.*"%>

<%
	CourseAskVO courseAskVO = (CourseAskVO) request.getAttribute("courseVO");
%>


<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>提問問題</title>
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
	<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
	<style type="text/css" media="screen">
		input,
		select,
		textarea {
			padding: 5px 15px;
			border: 1px solid #ccc;
			cursor: pointer;
			-webkit-border-radius: 5px;
			border-radius: 5px;
		}

	</style>
</head>

<body>
	<table id="table-1">
		<tr>
			<h3>	新增問題	</h3>
			<td>
				<h3>
					<a href="<%=request.getContextPath()%>/back-end/courseAsk/select_page.jsp">回首頁</a>
				</h3>
			</td>
		</tr>
	</table>
	
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/courseAsk/courseAsk.do" name="form1">
	
		<jsp:useBean id="studentSvc" scope="page" class="com.student.model.StudentService" />
		<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<table>
			<tr>
				<td>課程名稱:</td>
				<td><input type="TEXT" name="courseNo" size="40"
					value="<%=(courseAskVO == null) ? "C001" : courseAskVO.getCourseNo()%>" /></td>
			</tr>

			<tr>
				<td>學生名稱:</td>
				<td><input type="TEXT" name="studentNo" size="40"
					value="<%=(courseAskVO == null) ? "S000002" : courseAskVO.getStudentNo()%>" /></td>
			</tr>
	
	<tr>
				<td>狀態:</td>
				<td><input type="TEXT" name="status" size="40"
					value="<%=(courseAskVO == null) ? "1" : courseAskVO.getStatus()%>"></td>
			</tr>
		</table>
		<input placeholder="輸入您的問題" name="title" style="color: black; width: 566px; height: 50px; margin-bottom: 20px;" 
		value="<%=(courseAskVO == null) ? "" : courseAskVO.getTitle()%>">
		


		 <textarea id="summernote"  name="question"></textarea>
        
         <p><input type="hidden" name="action" value="insert"> 
				<input type="submit"></p>

        </FORM>
   <script>
   $(document).ready(function() { 
        $('#summernote').summernote(); 
     });  
		
 	</script>
</body>

</html>
