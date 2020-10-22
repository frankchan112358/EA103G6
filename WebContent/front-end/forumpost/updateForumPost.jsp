<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.forumpost.model.*"%>

<%
	ForumPostVO forumpostVO = (ForumPostVO) request.getAttribute("forumpostVO");
%>
<!DOCTYPE HTML>
<html>

<head>
<title>WJL</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

</head>

<body class="subpage">
	
	<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
				<h2>WJL 討論區</h2>
			</header>
		</div>
	</section>
	<!-- Two -->
	<section id="two" class="wrapper style2">
		<div class="inner">
			<div class="box">
				<div class="content">
					<header class="align-center">
						<h2>
							<b>修改貼文</b>
						</h2>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
					</header>
					<div id="addforumPost">
						<form method="post" action="<%=request.getContextPath()%>/front-end/forumPost/forumPost.do">
							<fieldset>
								<legend>
									<b>修改貼文</b>
								</legend>
								<p>
									<label for="title">標題 :</label> 
									<input type="text" name="title" value="${forumpostVO.title}">
								</p>
								<p>
									<label for="content">貼文內容 :</label>
									<textarea id="summernote" name="content">	${forumpostVO.content}</textarea>
								</p>
								<p>
									<input type="submit"> 
									<input type="hidden" name="action" value="update"> 
									<input type="hidden" name="studentNo" value="${studentVO.studentNo}">
									<input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}">
								</p>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Footer -->
	
	<!-- Footer -->
		
	<!-- include summernote css/js -->
		
    	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
   
	<script type="text/javascript">
		$(document).ready(function() {
			$('#summernote').summernote({
				fontSizes: ['8', '9', '10', '11', '12', '14', '18', '24', '36', '48' , '64', '82', '150'],
				height: 300,
				lang: 'zh-CN',
				focus:true
			})
		});
	</script>

</body>

</html>