<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banji.model.*"%>
<%@ page import="com.banjitype.model.*"%>

<jsp:useBean id="banjiTypeSvc" scope="page" class="com.banjitype.model.BanjiTypeService" />
<jsp:useBean id="listBanji_ByBanjiTypeNo" scope="request" type="java.util.Set<BanjiVO>" />
<jsp:useBean id="classroomSvc" scope="page" class="com.classroom.model.ClassroomService" />
<html>

<head><title>�Z�ź��� - listBanji_ByBanjiTypeNo.jsp</title>

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

<h4>�����m�߱ĥ� EL ���g�k����:</h4>
<table id="table-2">
	<tr><td>
		 <h3>�Z�ź��� - listBanji_ByBanjiTypeNo.jsp.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/banjiType/select_page.jsp">�^����</a></h4>
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
		<th>�ɮv�W��</th>
		<th>�Z�ź���</th>
		<th>�}�V���</th>
		<th>���V���</th>
		<th>�Z�ŦW��</th>
		<th>�ǥͤH��</th>
		<th>�W�Үɼ�</th>
		<th>�Z�űЫ�</th>
		<th>�Z�Ť��e</th>
		<th>�ק�</th>
		<th>�R��</th>
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
			    <input type="submit" value="�ק�"> 
			    <input type="hidden" name="banjiNo"      value="${banjiVO.banjiNo}">
			    <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller--><!-- �ثe�|���Ψ�  -->
			    <input type="hidden" name="action"	   value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banji/banji.do" style="margin-bottom: 0px;">
			    <input type="submit" value="�R��">
			    <input type="hidden" name="banjiNo"      value="${banjiVO.banjiNo}">
			    <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
			    <input type="hidden" name="action"     value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>