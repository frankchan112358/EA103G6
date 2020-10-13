<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.student.model.*"%>

<%
StudentVO studentVO = (StudentVO) request.getAttribute("studentVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>���u��Ʒs�W </title>

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
	width: 450px;
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
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>�ǭ���Ʒs�W </h3></td><td>
		 <h4><a href="selectpage.jsp">�^����</a></h4>
	</td></tr>
</table>

<h3>��Ʒs�W:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/student/student.do" name="form1">
<table>
	<tr>
		<td>�ϥΪ̽s��:</td>
		<td><input type="TEXT" name="userNo" size="45" 
			 value="<%= (studentVO==null) ? "" : studentVO.getUserNo()%>" /></td>
	</tr>
	<tr>
		<td>�Z�Žs��:</td>
		<td><input type="TEXT" name="banjiNo" size="45"
			 value="<%= (studentVO==null)? "" : studentVO.getBanjiNo()%>" /></td>
	</tr>
	<tr>
		<td>�m�W:</td>
		<td><input type="TEXT" name="studentName" size="45"
			 value="<%= (studentVO==null)? "" : studentVO.getStudentName()%>" /></td>
	</tr>
	<tr>
		<td>���A:</td>
		<td>
		<select name="studentStatus">
			<option value="0">�����V
			<option value="1">���V��
		</select>
		
	</tr>

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="�e�X�s�W"></FORM>
</body>