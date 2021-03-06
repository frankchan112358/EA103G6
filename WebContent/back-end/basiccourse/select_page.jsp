<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Basic Course</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>

table#table-1 {
	width:400px;
	margin-top: 5px;
	margin-bottom: 10px;
	border-bottom: 2px solid #0e6e95;
	height: 80px;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
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
<div class="container-fluid">
	<table id="table-1">
		<tr>
			<td><h3>BasicCourse</h3></td>
		</tr>
	</table>

<h3>資料查詢</h3>

	
	<ul>
	<li>
  		<h4><a href="<%=request.getContextPath()%>/back-end/basiccourse/listAllBasicCourse.jsp"><img src="<%=request.getContextPath()%>/images/list.svg" width="50" height="50" border="0">List all BasicCourse<br><br></a></h4>
	</li>
		<li>
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/basicCourse/basicCourse.do">
			<h4>輸入基本課程編號 (如BC001)</h4> 
			<input type="text" name="basicCourseNo"> 
			<input type="hidden" name="action" value="getOne_For_Display"> 	
       		<input class="btn btn-primary btn-sm" type="submit" value="Submit">
		</FORM>
	</li>
	
		<jsp:useBean id="basicCourseSvc" scope="page" class="com.basiccourse.model.BasicCourseService" />

	<li>
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/basicCourse/basicCourse.do">
			<h4>選擇基本課程編號</h4> 
			<select size="1" name="basicCourseNo" style="font-size:16px; width:173px; height:32px;">
				<c:forEach var="basicCourseVO" items="${basicCourseSvc.all}">
					<option value="${basicCourseVO.basicCourseNo}">${basicCourseVO.basicCourseNo}
				</c:forEach>
			</select> <input type="hidden" name="action" value="getOne_For_Display">
       		<input class="btn btn-primary btn-sm" type="submit" value="Submit">
		</FORM>
	</li>
	
	<li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/basicCourse/basicCourse.do" >
       <h4>選擇基本課程名稱</h4>
       <select size="1" name="basicCourseNo" style="font-size:16px; width:173px; height:32px;">
         <c:forEach var="basicCourseVO" items="${basicCourseSvc.all}" > 
          <option value="${basicCourseVO.basicCourseNo}">${basicCourseVO.basicCourseName}
         </c:forEach>   
       </select>
       
       <input type="hidden" name="action" value="getOne_For_Display">
       <input class="btn btn-primary btn-sm" type="submit" value="Submit">
            
     </FORM>
  </li>
  </ul>
  <br>
	<h3>基本課程管理</h3>
	<ul>
	<li>
		<h4><a href="<%=request.getContextPath()%>/back-end/basiccourse/addBasicCourse.jsp"><img src="<%=request.getContextPath()%>/images/add.svg" width="50" height="50" border="0">Add a new BasicCourse</a></h4>
	</li>
	</ul>


</div>

</body>

<script>
	<c:if test="${not empty errorMsgs}">
	<c:forEach var="message" items="${errorMsgs}">
		swal({
			icon:'error',
			title:'手指是否有問題',
			text:'${message}'
			});
	</c:forEach>
	</c:if>

</script>

</html>