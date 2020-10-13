<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>討論區主題</title>
<style>
a {
	text-decoration: none;
	color: blue;
}

a:hover {
	color: red;
	text-decoration: underline;
}
</style>

</head>
<body>

	

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<a href='listAllForumTopic.jsp'>顯示所有主題</a>
	<br/>
	<br/>
	<a href='addForumTopic.jsp'>加入新的主題</a>
	<br/>
	<br/>
<h3>資料查詢:</h3>

		<form method="post" action="<%=request.getContextPath() %>/forumTopic/forumTopic.do">
		<b>輸入主題編號 (如1):</b> <input type="text" name="forumTopicNo"> <input
			type="hidden" name="action" value="getOne_For_Display"> <input
			type="submit" value="送出">
	</FORM>
	
	<jsp:useBean id="forumtopicSvc" scope="page" class="com.forumtopic.model.ForumTopicService"/>
		<form method="post" action="<%=request.getContextPath() %>/forumTopic/forumTopic.do">
       <b>選擇班級編號:</b>
       <select size="1" name="forumTopicNo">
         <c:forEach var="forumTopicVO" items="${forumtopicSvc.all}" > 
          <option value="${forumTopicVO.forumTopicNo}">${forumTopicVO.banjiNo}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>

</body>
</html>