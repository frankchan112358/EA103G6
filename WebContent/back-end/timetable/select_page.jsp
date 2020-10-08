<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM Timetable: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='white'>

<table>
	<tr><td><h3>IBM Timetable: HOME</h3></td></tr>
</table>

<p>This is the Home page for IBM Timetable: HOME</p>

<h3>��Ƭd��:</h3>

<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
</c:if>
<%-- ���~�C�� --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
	<li><a href='listAllTimetable.jsp'>List</a> all Timetable<br><br></li>
	
	<li>
	 <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/timetable/timetable.do">
	 	<b>�п�J�Ҫ�s��(�pTT000001):</b>
	 	<input type="text" name="timetableNo">
	 	<input type="hidden" name="action" value="getOne_For_Display">
	 	<input type="submit" value="�e�X">
	 </FORM>
	</li>
	
	<jsp:useBean id="timetableSvc" scope="page" class="com.timetable.model.TimetableService" />
	
	<li>
	 <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/timetable/timetable.do">
		<b>�п�ܽҪ�s��:</b>
		<select size="1" name="timetableNo">
			<c:forEach var="timetableVO" items="${timetableSvc.all}">
				<option value="${timetableVO.timetableNo}">${timetableVO.timetableNo}
			</c:forEach>
		</select>
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="�e�X">
 	 </FORM>
	</li>
	
	<li>
	  <form METHOD="post" ACTION="<%=request.getContextPath() %>/timetable/timetable.do">
	  <b>�п�ܽҵ{�W��:</b>
	  <select size="1" name="timetableNo">
	  	<c:forEach var="timetableVO" items="${timetableSvc.all}">
	  		<option value="${timetableVO.timetableNo}">${timetableVO.courseNo}
	  	</c:forEach>
	  </select>
	  <input type="hidden" name="action" value="getOne_For_Display">
	  <input type="submit" value="�e�X">
	  </form>
	</li>
</ul>


<h3>�Ҫ�޲z</h3>

<ul>
	<li><a href='addTimetable.jsp'>Add</a> a new timetable.</li>
</ul>
</body>
</html>