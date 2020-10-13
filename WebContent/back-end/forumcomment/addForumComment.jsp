<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.forumcomment.model.*"%>

<%
  ForumCommentVO forumCommentVO = (ForumCommentVO) request.getAttribute("forumCommentVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>新增留言 - addForumComment.jsp</title>

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
<jsp:useBean id="forumcommentSvc" scope="page" class="com.forumcomment.model.ForumCommentService" />

<table id="table-1">
	<tr><td>
		 <h3>新增留言 - addForumComment.jsp</h3></td><td>
		 <h4><a href="<%=request.getContextPath() %>/back-end/forumcomment/forum_select_page.jsp"><img src="<%=request.getContextPath() %>/back-end/forumcomment/images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>新增留言:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/forumComment/forumComment.do" name="form1">
<table>

	<jsp:useBean id="forumpostSvc" scope="page"
				class="com.forumpost.model.ForumPostService" />

			<tr>
				<td>貼文編號:</td>
				<td><select size="1" name="forumPostNo">
						<c:forEach var="forumPostVO" items="${forumpostSvc.all}">
							<option value="${forumPostVO.forumPostNo}"
								${(forumCommentVO.forumPostNo==forumPostVO.forumPostNo)? 'selected':'' }>${forumPostVO.forumPostNo}
						</c:forEach>
				</select></td>
			</tr>

	<jsp:useBean id="studentSvc" scope="page"
				class="com.student.model.StudentService" />

			<tr>
				<td>學員編號:</td>
				<td><select size="1" name="studentNo">
						<c:forEach var="studentVO" items="${studentSvc.all}">
							<option value="${studentVO.studentNo}"
								${(forumCommentVO.studentNo==studentVO.studentNo)? 'selected':'' }>${studentVO.studentNo}
						</c:forEach>
				</select></td>
			</tr>
	<tr>
			<td>
				  貼文留言:
			</td>
			<td>
				<textarea style="resize:none;" name="content" rows="5" cols="47"><%=(forumCommentVO==null)?"學員可以在留言":forumCommentVO.getContent() %></textarea>
			</td>
	</tr>
	

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

</body>
</html>

