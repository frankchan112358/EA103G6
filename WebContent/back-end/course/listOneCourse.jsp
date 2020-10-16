<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.course.model.*"%>


<%
    CourseService courseSvc = new CourseService();
    List<CourseVO> list = courseSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService"/>
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService"/>
<jsp:useBean id="classroomSvc" scope="page" class="com.classroom.model.ClassroomService"/>

<html>
<head>
<title>課程資料</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>

<style>
  table#table-1 {
  	border-bottom: 2px solid #0e6e95;
    text-align: center;
  }
  
  .h3-1 {
    margin: 10px;
  }
 
 .table-responsive {
    display: block;
    width: 100%;
    overflow-x: auto;
    -webkit-overflow-scrolling: touch;
}
 
</style>

<style>
  table {
  	width:100%;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
    
  }
  table, th, td {
  	border-bottom: 2px solid #0e6e95;
    
  }
  th, td {
    padding: 5px;
    text-align: center;    
  }
  
    
</style>

</head>
<body>

<div class="container-fluid">
<div class="table-responsive">

	<table id="table-1">
		<tr><td>
				<h3 class="h3-1">課程資料</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/course/select_page.jsp"><img src="<%=request.getContextPath()%>/images/homepage.svg" width="80" height="50" border="0">Home</a></h4>
		</td></tr>
	</table>

<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>


	<table>
		<tr>
			<th>課程編號</th>
			<th>課程名稱</th>
			<th width="30%">課程大綱</th>
			<th>班級</th>
			<th>講師</th>
			<th>教室</th>
			<th>堂數</th>
			<th>開始日期</th>
			<th>結束日期</th>
			<th>狀態</th>
		</tr>

		<tr>
			<td>${courseVO.courseNo}</td>
			<td>${courseVO.courseName}</td>
			<td>${courseVO.courseOutline}</td>
			<td>${banjiSvc.getOneBanji(courseVO.banjiNo).banjiName}</td>
			<td>${teacherSvc.getOneTeacher(courseVO.teacherNo).teacherName}</td>
			<td>${classroomSvc.getOneClassroom(courseVO.classroomNo).classroomName}</td>
			<td>${courseVO.lesson}</td>
			<td>${courseVO.startDate}</td>
			<td>${courseVO.endDate}</td>
			<td>
			<c:choose>
				<c:when test="${courseVO.status=='0'}">課程未開始</c:when>   
				<c:when test="${courseVO.status=='1'}">課程進行中</c:when>
				<c:when test="${courseVO.status=='2'}">課程結束</c:when> 					 					    
			</c:choose>
			</td>
		</tr>

	</table>
	
</div>
</div>


</body>
</html>