<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.basiccourse.model.*"%>

<%
	BasicCourseVO basicCourseVO = (BasicCourseVO) request.getAttribute("basicCourseVO");
%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>課程資料修改</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>
table#table-1 {
	border-bottom: 2px solid #0e6e95;
    text-align: center;
    margin-left:auto;
    margin-right:auto;
}

  .h3-1 {
    margin: 10px;
  }
  
 table#table-3 {
    text-align: center;
}


h5{
	margin: 5px auto;
	text-align: left;
}
  
</style>

<style>
table {
	width: 550px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
	margin-left:auto;
	margin-right:auto;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}

.swal-text {
  background-color: #FEFAE3;
  padding: 17px;
  border: 1px solid #F0E1A1;
  font-size: 17px;
  font-weight:bold;
  display: block;
  margin: 22px;
  text-align: center;
  color: #F56455;
}

</style>
</head>
<body bgcolor='white'>
<div class="container">
	<table id="table-1">
	
	<tr><td>
		 <h3 class="h3-1">基本課程資料修改</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/basiccourse/select_page.jsp"><img src="<%=request.getContextPath()%>/images/homepage.svg" width="80" height="50" border="0">Home</a></h4>
	</td></tr>
	</table>
	


	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/basicCourse/basicCourse.do" name="form1">
		<table id="table-3">
			
				<tr><td><h5>基本課程編號</h5></td></tr>
				<tr><td><input type="TEXT" name="baiscCourseNo" size="67" disabled
					value="${basicCourseVO.basicCourseNo}" /></td></tr>
			
			
				<tr><td><h5>基本課程名稱</h5></td></tr>
				<tr><td><input type="TEXT" name="basicCourseName" size="67"
					value="${basicCourseVO.basicCourseName}"/><font color=red>${errorMsgs.basicCourseName}</font></td></tr>
			
			
				<tr><td><h5>基本課程介紹</h5></td></tr>
				<tr><td><textarea id="basicCourseInfo" name="basicCourseInfo" rows="8"  style= "width: 550px; resize:none" >${basicCourseVO.basicCourseInfo}</textarea></td></tr>
			
			
				<tr><td><h5>堂數</h5></td></tr>
				<tr><td><input type="TEXT" name="lesson" size="67"
					value="${basicCourseVO.lesson} " /><font color=red>${errorMsgs.lesson}</font></td></tr>
	      		
		</table>
		<br>
		
		<div class="form-row align-items-center justify-content-center">
		<input type="hidden" name="action" value="update">
		<input type="hidden" name="basicCourseNo" value="${basicCourseVO.basicCourseNo}">
		<input class="btn btn-primary justify-content-center" type="submit" value="Submit">
		</div>
		</FORM>

</div>
	

</body>

<script>

<c:if test="${not empty errorMsgs}">
<c:forEach var="message" items="${errorMsgs}">
	swal({
		icon:'error',
		title:'手指是否有問題',
		text:'⚠有項目未填⚠'
		});
</c:forEach>
</c:if>

</script>


</html>