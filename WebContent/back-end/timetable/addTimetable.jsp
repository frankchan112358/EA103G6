<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.timetable.model.*" %>

<%
  TimetableVO timetableVO = (TimetableVO) request.getAttribute("timetableVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>課表新增資料 - addTimetable.jsp</title>

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
		<h3>課表資料新增</h3></td><td>
		<h4><a href="<%=request.getContextPath() %>/back-end/timetable/select_page.jsp"><img src="<%=request.getContextPath() %>/images/back1.gif" width="50" height="20" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%--錯誤列表 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/timetable/timetable.do" name="form1">
<table>
	<tr>
		<td>課程編號:</td>
		<td><input type="TEXT" name="courseNo" size="45"
			value="<%= (timetableVO==null)? "C003" : timetableVO.getCourseNo()%>"/></td>
	</tr>
	<tr>
		<td>教室編號:</td>
		<td><input type="TEXT" name="classroomNo" size="45"
			value="<%= (timetableVO==null)? "3" : timetableVO.getClassroomNo()%>"/></td>
	</tr>
	<tr>
		<td>上課時段:</td>
		<td><input type="TEXT" name="timetablePeriod" size="45"
			value="<%= (timetableVO==null)? "1" : timetableVO.getTimetablePeriod()%>"/></td>
	</tr>
	<tr>
		<td>上課日期:</td>
		<td><input name="timetableDate" id="f_date1" type="text"></td>
	</tr>
	<tr>
		<td>教學日誌:</td>
		<td><input type="TEXT" name="teachingLog" size="45"
			value="<%= (timetableVO==null)? "課本內容第50~80頁，老師今天飆車" : timetableVO.getCourseNo()%>"/></td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

<!-- ====== -->
<% 
  java.sql.Date timetableDate = null;
  try {
	  timetableDate = timetableVO.getTimetableDate();
   } catch (Exception e) {
	   timetableDate = new java.sql.Date(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.full.js"></script>
<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=timetableDate%>', // value:   new Date(),
        });

</script>
</html>