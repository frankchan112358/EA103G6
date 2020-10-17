<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.teachingfile.model.*"%>

<%
	TeachingFileVO teachingFileVO = (TeachingFileVO) request.getAttribute("teachingFileVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>教材資料修改 - update_teachingFile_input.jsp</title>

<style>
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
	width: 900px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor="white">

<table id="table-1">
	<tr><td>
		<h3>教材紀錄修改 - update_teachingFile_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/teachingFile/select_page.jsp"><img src="<%=request.getContextPath() %>/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤列表 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/teachingFile/teachingFile.do" NAME="form1" ENCTYPE="multipart/form-data">
<input type="hidden" name="oriTeachingFileName" value="<%=teachingFileVO.getTeachingFileName()%>">
<table>
	<tr>
		<td>教材編號:<font color=red><b>*</b></font></td>
		<td><%=teachingFileVO.getTeachingFileNo()%></td>
	</tr>
	<tr>
		<td>課表編號:</td>
		<td><input type="TEXT" name="timetableNo" size="45" value="<%=teachingFileVO.getTimetableNo()%>"></td>
	</tr>
	<tr>
		<td>教材名稱:</td>
		<td><input type="TEXT" name="teachingFileName" size="45" value="<%=teachingFileVO.getTeachingFileName()%>"></td>
	</tr>
	<tr>
		<td>教材:</td>
		<td><input type="file" name="upfile2" ></td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="teachingFileNo" value="<%=teachingFileVO.getTeachingFileNo()%>">
<input type="submit" value="送出修改">
	</FORM>
</body>
</html>