<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.basiccourse.model.*"%>


<%
    BasicCourseService basicCourseSvc = new BasicCourseService();
    List<BasicCourseVO> list = basicCourseSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>基本課程資料</title>

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
				<h3 class="h3-1">基本課程資料</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/basiccourse/select_page.jsp"><img src="<%=request.getContextPath()%>/images/homepage.svg" width="80" height="50" border="0">Home</a></h4>
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
			<th>基本課程編號</th>
			<th>基本課程名稱</th>
			<th width="60%">基本課程介紹</th>
			<th>堂數</th>
		</tr>

		<tr>
			<td>${basicCourseVO.basicCourseNo}</td>
			<td>${basicCourseVO.basicCourseName}</td>
			<td>${basicCourseVO.basicCourseInfo}</td>
			<td>${basicCourseVO.lesson}</td>
		</tr>

	</table>
	
</div>
</div>


</body>
</html>