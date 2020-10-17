<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.reply.model.*"%>

<%
    ReplyService replySvc = new ReplyService();
    List<ReplyVO> list = replySvc.getAll();
    pageContext.setAttribute("list",list);
%>

<jsp:useBean id="courseAskSvc" scope="page"
			class="com.courseask.model.CourseAskService" />
			
<jsp:useBean id="teacherSvc" scope="page"
			class="com.teacher.model.TeacherService" />
			
<jsp:useBean id="studentSvc" scope="page"
			class="com.student.model.StudentService" />

<html>
<head>
<title>所有回覆資料</title>

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
		 <h3>回覆資料</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/reply/select_page.jsp">回首頁</a></h4>
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
		<th>標題</th>
		<th>講師</th>
		<th>學生</th>
		<th>回覆內容</th>
		<th>回覆時間</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="pages/page1.file" %> 
	<c:forEach var="replyVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${courseAskSvc.getOneCourseAsk(replyVO.getCourseAskNo()).title}</td>
			<td>${teacherSvc.getOneTeacher(replyVO.getTeacherNo()).teacherName}</td>
			<td>${studentSvc.getOneStudent(replyVO.getStudentNo()).studentName}</td>
			<td>${replyVO.replyContent}</td>
			<td><fmt:formatDate value="${replyVO.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reply/reply.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="replyNo"  value="${replyVO.replyNo}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reply/reply.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="replyNo"  value="${replyVO.replyNo}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="pages/page2.file" %>

</body>
</html>