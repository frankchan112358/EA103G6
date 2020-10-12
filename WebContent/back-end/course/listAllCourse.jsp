<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.course.model.*"%>

<%
    CourseService courseSvc = new CourseService();
    List<CourseVO> list = courseSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有課程資料</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
 

<style>

	#container { 
	margin: 0 auto; 
	width: 80%; 
	}

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
    text-align: center;
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

<body bgcolor='white'>
<div class="container-fluid">
<div class="table-responsive">
<table id="table-1">
	<tr><td>
		 <h3 class="h3-1">所有課程資料</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/course/select_page.jsp"><img src="<%=request.getContextPath()%>/images/homepage.svg" width="80" height="50" border="0">Home</a></h4>
	</td></tr>
</table>
<%-- 錯誤表列 --%>
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
<!-- 			<th>基本課程編號</th> -->
			<th>課程名稱</th>
			<th width="30%">課程大綱</th>
			<th>班級</th>
			<th>講師</th>
			<th>教室</th>
			<th>堂數</th>
			<th>開始日期</th>
			<th>結束日期</th>
			<th>狀態</th>
			<th>修改</th>
			<th>刪除</th>
	</tr>
	
	
	<%@ include file="page1.file" %>
	<c:forEach var="courseVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${courseVO.courseNo}</td>
<%-- 			<td>${courseVO.basicCourseNo}</td> --%>
			<td>${courseVO.courseName}</td>
			<td>${courseVO.courseOutline}</td>
			<td>${courseVO.banjiNo}</td>
			<td>${courseVO.teacherNo}</td>
			<td>${courseVO.classroomNo}</td>
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
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do" style="margin-bottom: 0px;">
			  <button type="submit" class="btn btn-info">revise</button>
			     <input type="hidden" name="courseNo"  value="${courseVO.courseNo}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM id="delete1" METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do" style="margin-bottom: 0px;">
			    <button type="submit" class="btn btn-danger">delete</button>
			     <input type="hidden" name="courseNo"  value="${courseVO.courseNo}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
			
	</c:forEach>
</table>
</div>


<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
    <%if (rowsPerPage<rowNumber) {%>
    <%if(pageIndex>=rowsPerPage){%>
    <li class="page-item"><a class="page-link" href="<%=request.getRequestURI()%>?whichPage=<%=whichPage-1%>">Previous</a></li>
     <%}%>
     <%if(pageIndex<pageIndexArray[pageNumber-1]){%>

    <li class="page-item"><a class="page-link" href="<%=request.getRequestURI()%>?whichPage=<%=whichPage+1%>">Next</a></li>
        <%}%>
  <%}%>   
  </ul>
</nav>


<%if (pageNumber>1) {%>
	<FORM METHOD="post" ACTION="<%=request.getRequestURI()%>"> 
  <div class="form-row align-items-center justify-content-center">
    <div class="col-auto my-1">
      <select class="custom-select mr-sm-2" name="whichPage">
        <%for (int i=1; i<=pageNumber; i++){%>
             <option value="<%=i%>">跳至第<%=i%>頁
         <%}%> 
      </select>
    </div>
    <div class="col-auto my-1">
      <button type="submit" class="btn btn-primary">Go</button>
    </div>
  </div>
    <%}%>
</FORM>
</div>

</body>

<script>

// $(function(){
// $('#delete1').on('click', function(event){
// // 	event.preventDefault();
// swal({
// 	  title: "Are you sure?",
// 	  text: "刪除後就回不來惹",
// 	  icon: "warning",
// 	  buttons: true,
// 	  dangerMode: true,
// 	})

// 	.then((willDelete) => {
// 	  if (willDelete) {
//           $scope.swal("Poof! Your imaginary file has been deleted!", {
// 	    icon: "success",
// 	    });
// 	  } else {
// 	    swal("Your imaginary file is safe!");
// 	  }
// 	});
// });
// });

	</script>
</html>