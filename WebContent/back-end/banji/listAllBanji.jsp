<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.banji.model.*"%>

<%
    BanjiService banjiSvc = new BanjiService();
    List<BanjiVO> list = banjiSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<jsp:useBean id="banjiTypeSvc" scope="page"
			class="com.banjitype.model.BanjiTypeService" />
<jsp:useBean id="classroomSvc" scope="page"
			class="com.classroom.model.ClassroomService" />
<jsp:useBean id="empSvc" scope="page"
			class="com.emp.model.EmpService" />

<html>
<head>
<title>所有班級資料</title>

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
		 <h3>班級資料</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/banji/select_page.jsp">回首頁</a></h4>
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
		<th>導師名稱</th>
		<th>班級種類</th>
		<th>開訓日期</th>
		<th>結訓日期</th>
		<th>班級名稱</th>
		<th>學生人數</th>
		<th>上課時數</th>
		<th>班級教室</th>
		<th>班級內容</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="pages/page1.file" %> 
	<c:forEach var="banjiVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${empSvc.getOneEmp(banjiVO.getEmpNo()).empName}</td>
			<td>${banjiTypeSvc.getOneBanjiType(banjiVO.banjiTypeNo).banjiTypeName}</td>
			<td>${banjiVO.startDay}</td>
			<td>${banjiVO.endDay}</td>
			<td>${banjiVO.banjiName}</td>
			<td>${banjiVO.numberOfStudent}</td>
			<td>${banjiVO.classHours}</td>
			<td>${classroomSvc.getOneClassroom(banjiVO.classroomNo).classroomName}</td>
			<td>${banjiVO.banjiContent}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banji/banji.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="banjiNo"  value="${banjiVO.banjiNo}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banji/banji.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="banjiNo"  value="${banjiVO.banjiNo}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="pages/page2.file" %>

</body>
</html>