<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.forumpost.model.*"%>


<%
  ForumPostVO forumPostVO = (ForumPostVO) request.getAttribute("forumPostVO"); 
%>

<html>
<head>
<title>�K���� - listOneForumPost.jsp</title>

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

<h4>�����Ƚm�߱ĥ� Script ���g�k����:</h4>
<table id="table-1">
	<tr><td>
		 <h3>�K���� - listOneForumPost.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/forumpost/forum_select_page.jsp"><img src="<%=request.getContextPath()%>/back-end/forumpost/images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>�K��s��</th>
		<th>�D�D�s��</th>
		<th>�ǭ��s��</th>
		<th>�K����D</th>
		<th>�K�夺�e</th>
		<th>��s�ɶ�</th>
		<th>�o��ɶ�</th>
	</tr>
	<tr>
		<td><%=forumPostVO.getForumPostNo()%></td>
		<td><%=forumPostVO.getForumTopicNo()%></td>
		<td><%=forumPostVO.getStudentNo()%></td>
		<td><%=forumPostVO.getTitle()%></td>
		<td><%=forumPostVO.getContent()%></td>
		<td><%=forumPostVO.getUpdateTime()%></td>
		<td><%=forumPostVO.getCreateTime()%></td>
	</tr>
</table>

</body>
</html>