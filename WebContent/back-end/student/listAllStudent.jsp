<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.student.model.*"%>


<%
	StudentService studentSvc = new StudentService();
    List<StudentVO> list = studentSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>�Ҧ��ǭ����</title>

<style>
  table#table-1 {
	
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
		 <h3>�Ҧ��ǭ���� - listAllStudent.jsp</h3>
		 <h4><a href="selectpage.jsp">�^����</a></h4>
	</td></tr>
</table>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
	
		<th>�ǭ��s��</th>
		<th>�ϥΪ̽s��</th>
		<th>�Z��</th>
		<th>�m�W</th>
		<th>�y���ؼ�</th>
		<th>�ǭ��ۭz</th>
		<th>�ϥΪ̪��A</th>
		<th>�ק�</th>
		<th>�R��</th>
	</tr>
	<%--<%@ include file="page1.file" %> 
	<c:forEach var="studentVO" items="${studentSvc.all}" >  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">--%>
	<c:forEach var="studentVO" items="${list}" > 
		
		<tr>
		  
			<td>${studentVO.studentNo}</td>
			<td>${studentVO.userNo}</td>
			<td>${studentVO.banjiNo}</td>
			<td>${studentVO.studentName}</td>
			<td>${studentVO.face}</td> 
			<td>${studentVO.studentDescription}</td>
			<td>${studentVO.studentStatus}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/student/student.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�ק�">
			     <input type="hidden" name="studentNo"  value="${studentVO.studentNo}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td><FORM METHOD="post" ACTION="<%=request.getContextPath()%>/student/student.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�R��">
			     <input type="hidden" name="studentNo"  value="${studentVO.studentNo}">
			     <input type="hidden" name="action" value="delete"></FORM></td>
			<%-- ���~��C 
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/student/student.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�ק�">
			     <input type="hidden" name="studentno"  value="${studentVO.studentno}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/emp/emp.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�R��">
			     <input type="hidden" name="empno"  value="${empVO.empno}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>--%>
		</tr>
	</c:forEach>
</table>
<%--<%@ include file="page2.file" %>--%>

</body>
</html>