<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.forumtopic.model.*"%>

<%
   ForumTopicVO forumTopicVO = (ForumTopicVO) request.getAttribute("forumTopicVO"); 
%>

<!DOCTYPE html>
<html>
<head>

<title>修改主題資料 - update_forumTopic_input.jsp</title>
<style>
a {
	text-decoration: none;
	color: green;
}

a:hover {
	color: red;
	text-decoration: underline;
}

div {
	width: 150px;
	height: 200px;
}

img {
	max-width: 100%;
	max-height: 100%;
}

</style>
</head>
<body>
	<h5>
		<a href="<%=request.getContextPath() %>/back-end/forumtopic/listAllForumTopic.jsp">顯示所有主題</a>
	</h5>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

<form method="post" action="<%=request.getContextPath() %>/forumTopic/forumTopic.do" name="form1">
		<table>
		
		<tr>
			<td>主題編號:<font color=red><b>*</b></font></td>
			<td><%=forumTopicVO.getForumTopicNo()%></td>
		</tr>

			<tr>
			<td>
				  班級編號:
			</td>
			<td>
				<select size="1" name="banjiNo">
					<option value="B001" ${('B001'==forumTopicVO.banjiNo)?'selected':''} >B001</option>
					<option value="B002" ${('B002'==forumTopicVO.banjiNo)?'selected':''} >B002</option>
					<option value="B003" ${('B003'==forumTopicVO.banjiNo)?'selected':''} >B003</option>
				</select>
			</td>
	</tr>

	<tr>
			<td>
				  主題名稱:
			</td>
			<td>
				<select size="1" name="forumTopicName">
					<option value="學術版" ${('學術版'==forumTopicVO.forumTopicName)?'selected':''} >學術版</option>
					<option value="閒聊版" ${('閒聊版'==forumTopicVO.forumTopicName)?'selected':''} >閒聊版</option>
					<option value="運動版" ${('運動版'==forumTopicVO.forumTopicName)?'selected':''} >運動版</option>
					<option value="發問版" ${('發問版'==forumTopicVO.forumTopicName)?'selected':''} >發問版</option>
					
				</select>
			</td>
	</tr>
	
	<tr>
			<td>
				  主題介紹:
			</td>
			<td>
				<textarea style="resize:none;" name="content" rows="5" cols="47"><%=(forumTopicVO==null)?"學員可以在這討論任何問題":forumTopicVO.getContent() %></textarea>
			</td>
	</tr>
	
	<tr>
			<td>
				  板規:
			</td>
			<td>
				<textarea style="resize:none;" name="rule" rows="5" cols="47"><%=(forumTopicVO==null)?"嚴禁色情暴力言語霸凌等字言":forumTopicVO.getRule() %></textarea>
			</td>
	</tr>
	
	<tr>
			<td>
				  發文格式:
			</td>
			<td>
				<textarea style="resize:none;" name="postTemplete" rows="5" cols="47"><%=(forumTopicVO==null)?"閒聊版-發文樣板(請照格式發文)	":forumTopicVO.getPostTemplete() %></textarea>
			</td>
	</tr>

</table>
		<br> <input type="hidden" name="action" value="update"> 
		<input type="hidden" name="forumTopicNo" value="${forumTopicVO.forumTopicNo}"> 
		<input type="submit" value="送出修改">
	</form>


</body>
</html>