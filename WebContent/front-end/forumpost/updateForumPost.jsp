<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.forumpost.model.*"%>
<%@ page import="java.util.*"%>

<%
  ForumPostVO forumPostVO = (ForumPostVO) request.getAttribute("forumPostVO"); 
%>

<!DOCTYPE HTML>
<html>


<head>
    <%@ include file="/front-end/template/head.jsp" %>
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/formplugins/summernote/summernote.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
</head>

<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
    <script>
        var classHolder = document.getElementsByTagName("BODY")[0];
    </script>
    <div class="page-wrapper">
        <div class="page-inner">
            <%@ include file="/front-end/template/left_aside.jsp" %>
            <div class="page-content-wrapper">
                <%@ include file="/front-end/template/header.jsp" %>
                <main id="js-page-content" role="main" class="page-content">
                    <ol class="breadcrumb page-breadcrumb">
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/index/index.jsp">前台首頁</a></li>
                        <li class="breadcrumb-item">討論區</li>
                    </ol>
                         <div class="input-group input-group-lg mb-g">
                                    <input type="text" class="form-control shadow-inset-2" placeholder="Search Discussion">
                                    <div class="input-group-append">
                                        <span class="input-group-text"><i class="fal fa-search"></i></span>
                                    </div>
                                </div>

<!-- <table id="table-1"> -->
<!-- 	<tr><td> -->
<!-- 		 <h3>修改貼文</h3> -->
<%-- 		 <h4><a href="<%=request.getContextPath()%>/front-end/forumpost/studentCenter_forum.jsp"></a></h4> --%>
<!-- 	</td></tr> -->
<!-- </table> -->


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
					<div id="addform">
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/forumPost/forumPost.do" name="form">
							<fieldset>
								<legend>
								</legend>
								<p>
									<label for="title">標題 :</label> 
									<input type="text" name="yitle" value="${forumPostVO.title}">
								</p>
								<p>
									<label for="content">內容 :</label>
									<textarea id="summernote" name="faContent">	${forumPostVO.content}</textarea>
								</p>
								<p>
									<input type="submit"> 
                                   <input type="hidden" name="action" value="update">
                                   <input type="hidden" name="forumTopicNo" value="${forumTopicVO.getForumTopicNo}">
                                  <input type="hidden" name="forumPostNo" value="<%=forumPostVO.getForumPostNo()%>">
                                  <input type="hidden" name="studentNo" value="${studentVO.studentNo}">
									
								</p>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
						







		<%@ include file="/front-end/template/footer.jsp" %>
		<%@ include file="/front-end/template/quick_menu.jsp" %>
    <%@ include file="/front-end/template/messager.jsp" %>
    <%@ include file="/front-end/template/basic_js.jsp" %>   
<script>
    $(document).ready(function() {
        $('#summernote').summernote();
    });
  </script>
	
	</main>
	</div>
		</div>
		</div>
	
</body>

</html>