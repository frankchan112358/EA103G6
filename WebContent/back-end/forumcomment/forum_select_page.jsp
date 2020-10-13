<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>討論區留言</title>

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

	<table id="table-1">
		<tr>
			<td><h3>ForumComment: Home</h3>
				<h4>( WJL )</h4></td>
		</tr>
	</table>

	<p>Welcome WJL</p>

	<h3>留言查詢:</h3>

	<%-- 錯誤表列 --%>

	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<ul>
		<li><a
			href="<%=request.getContextPath()%>/back-end/forumcomment/listAllForumComment.jsp">List</a>
			all forumcomment. <br>
		<br></li>


		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/forumComment/forumComment.do">
				<b>輸入留言編號 (如1):</b> <input type="text" name="forumCommentNo">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="forumcommentSvc" scope="page"
			class="com.forumcomment.model.ForumCommentService" />

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/forumComment/forumComment.do">
				<b>選擇留言編號:</b> <select size="1" name="forumCommentNo">
					<c:forEach var="forumCommentVO" items="${forumcommentSvc.all}">
						<option value="${forumCommentVO.forumCommentNo}">${forumCommentVO.forumCommentNo}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/forumComment/forumComment.do">
				<b>選擇貼文編號:</b> <select size="1" name="forumCommentNo">
					<c:forEach var="forumCommentVO" items="${forumcommentSvc.all}">
						<option value="${forumCommentVO.forumCommentNo}">${forumCommentVO.forumPostNo}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
	</ul>


	<h3>留言管理</h3>

	<ul>
		<li><a href='addForumComment.jsp'>Add</a> a new Post.</li>
	</ul>

</body>
</html>