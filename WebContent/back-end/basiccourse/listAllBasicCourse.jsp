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
<title>所有基本課程資料</title>
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
		 <h3 class="h3-1">所有基本課程資料</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/basiccourse/select_page.jsp"><img src="<%=request.getContextPath()%>/images/homepage.svg" width="80" height="50" border="0">Home</a></h4>
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
			<th>基本課程編號</th>
			<th>基本課程名稱</th>
			<th width="60%">基本課程介紹</th>
			<th>堂數</th>
			<th>修改</th>
			<th>刪除</th>
	</tr>
	
	
	<%@ include file="page1.file" %>
	<c:forEach var="basicCourseVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${basicCourseVO.basicCourseNo}</td>
			<td>${basicCourseVO.basicCourseName}</td>
			<td>${basicCourseVO.basicCourseInfo}</td>
			<td>${basicCourseVO.lesson}</td>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/basicCourse/basicCourse.do" style="margin-bottom: 0px;">
			  <button type="submit" class="btn btn-info">revise</button>
			     <input type="hidden" name="basicCourseNo"  value="${basicCourseVO.basicCourseNo}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/basicCourse/basicCourse.do" style="margin-bottom: 0px;">
			    <button type="submit" class="btn btn-danger">delete</button>
			     <input type="hidden" name="basicCourseNo"  value="${basicCourseVO.basicCourseNo}">
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
</html>