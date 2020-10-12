<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banji.model.*"%>
<%@ page import="com.banjitype.model.*"%>

<jsp:useBean id="banjiTypeSvc" scope="page" class="com.banjitype.model.BanjiTypeService" />
<jsp:useBean id="listBanji_ByBanjiTypeNo" scope="request" type="java.util.Set<BanjiVO>" />
<jsp:useBean id="classroomSvc" scope="page" class="com.classroom.model.ClassroomService" />
<html>

<head><title>班級種類 - listBanji_ByBanjiTypeNo.jsp</title>

<style>
  table#table-2 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-2 h4 {
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
	width: 1100px;
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

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-2">
	<tr><td>
		 <h3>班級種類 - listBanji_ByBanjiTypeNo.jsp.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/banjiType/select_page.jsp">回首頁</a></h4>
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
	
	<c:forEach var="banjiVO" items="${listBanji_ByBanjiTypeNo}" >
		<tr>
			<td>${banjiVO.empNo}</td>
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
			    <input type="hidden" name="banjiNo"      value="${banjiVO.banjiNo}">
			    <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
			    <input type="hidden" name="action"	   value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banji/banji.do" style="margin-bottom: 0px;">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="banjiNo"      value="${banjiVO.banjiNo}">
			    <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="action"     value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>