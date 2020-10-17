<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>回覆管理</title>

<style>
input[type=button] {
	background-color:#E800E8;
	border: none;
	padding: 16px 32px;
	text-decoration: none;
	font-size: 32px;
	hover
}

a, b {
	font-family: DFKai-sb;
}

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
	color: purple;
	display: block;
	margin-bottom: 1px;
}

input, select, textarea {
	padding: 5px 15px;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}

h4 {
	display: inline;
}
</style>

</head>
<body bgcolor='#c8c8c8'>
	<h1>
		回覆管理
	</h1>

	<br>

	<h3>回覆管理:</h3>

	<div align="left">
		<input type="button" value="所有資料"
			onclick="location.href='listAllReply.jsp'"><br>
	</div>

	<div align="center">
		<input type="button" value="回覆"
			onclick="location.href='addReply.jsp'"><br>
	</div>

	<h3>資料查詢:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<jsp:useBean id="replySvc" scope="page"
		class="com.reply.model.ReplyService" />
	
	<ul>

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/reply/reply.do">
				<b>輸入回覆編號 (如B1):</b> <input type="text" name="replyNo"> <input
					type="hidden" name="action" value="getOne_For_Display"> <input
					type="submit" value="送出">
			</FORM>
		</li>

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/reply/reply.do">
				<b>查看回覆編號:</b> <select size="1" name="replyNo">
					<c:forEach var="replyVO" items="${replySvc.all}">
						<option value="${replyVO.replyNo}">${replyVO.replyNo}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

	</ul>


</body>

</html>

