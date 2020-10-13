<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.forumcomment.model.*"%>


<%
  ForumCommentVO forumCommentVO = (ForumCommentVO) request.getAttribute("forumCommentVO"); 
%>

<html>
<head>
<title>留言資料 - listOneForumComment.jsp</title>

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
	width: 600px;
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
		 <h3>貼文資料 - listOneForumComment.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/forumcomment/forum_select_page.jsp"><img src="<%=request.getContextPath()%>/back-end/forumcomment/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>留言編號</th>
			<th>貼文編號</th>
			<th>學員編號</th>
			<th>貼文留言</th>
			<th>更新時間</th>
			<th>留言時間</th>
	</tr>
	<tr>
		<td><%=forumCommentVO.getForumCommentNo()%></td>
		<td><%=forumCommentVO.getForumPostNo()%></td>
		<td><%=forumCommentVO.getStudentNo()%></td>
		<td><%=forumCommentVO.getContent()%></td>
		<td><%=forumCommentVO.getUpdateTime()%></td>
		<td><%=forumCommentVO.getCreateTime()%></td>
	</tr>
</table>

</body>
</html>