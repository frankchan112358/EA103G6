<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.classroom.model.*"%>

<%
    ClassroomService classroomSvc = new ClassroomService();
    List<ClassroomVO> list = classroomSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有教室資料</title>

<style>


body{
background-color:#c8c8c8;
font-family: DFKai-sb;
}


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
	width: 1200px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>教室資料</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/classroom/select_page.jsp">回首頁</a></h4>
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
		<th>教室名稱</th>
		<th>學生人數</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="pages/page1.file" %> 
	<c:forEach var="classroomVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${classroomVO.classroomName}</td>
			<td>${classroomVO.numberOfStudent}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/classroom/classroom.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="classroomNo"  value="${classroomVO.classroomNo}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			     
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/classroom/classroom.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="classroomNo"  value="${classroomVO.classroomNo}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="pages/page2.file" %>

</body>
</html>