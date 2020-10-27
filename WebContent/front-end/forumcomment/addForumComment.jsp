<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.forumcomment.model.*"%>

<%@ page import="java.util.*"%>


<%
  ForumCommentVO forumCommentVO = (ForumCommentVO) request.getAttribute("forumCommentVO");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/front-end/template/head.jsp" %>
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/formplugins/summernote/summernote.css">
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


<head>
  <meta charset="UTF-8">
  <title>回覆貼文</title>
  <meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
</head>
	<!-- Header -->
	<!-- One -->
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
							<b>新增留言</b>
						</h2>
					</header>
					<div id="addforumComment">
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/forumComment/forumComment.do" name="form1">
	
	
	<fieldset>
<!--                 <p> -->
<!--                     <label for="forumPostNo">貼文編號:</label> -->
<!--                     <select size="1" name="forumPostNo"> -->
<%-- 					<option value="1" ${('1'==forumCommentVO.forumPostNo)?'selected':''} >1</option> --%>
<%-- 					<option value="2" ${('2'==forumCommentVO.forumPostNo)?'selected':''} >2</option> --%>
<%-- 					<option value="3" ${('3'==forumCommentVO.forumPostNo)?'selected':''} >3</option> --%>
					
<!-- 				    </select> -->
<!--                 </p> -->
	   

	
       
<!--        <p> -->
<!--                     <label for="studentNo">學員編號 :</label> -->
<!--                     <input type="text" name="studentNo" -->
<%-- 					value="<%=(forumCommentVO == null) ? "S000001" : forumCommentVO.getStudentNo()%>"> --%>
<%--                   	<input type="hidden" name="forumCommentNo" value="${forumCommentVO.forumCommentNo}"> --%>
<!--                 </p> -->
	 
           
                
                
                
								<p>
									<label for="content">留言內容 :</label>
								</p>
								<textarea id="summernote" name="content"></textarea>
								
								<p>
								   <input type="hidden" name="action" value="insert"/>
								   <input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}">								
	                                <input type="hidden" name="studentNo" value="${studentVO.studentNo}">
	                               <input type="submit" value="送出新增"/>
	                            </p>
											</fieldset>
				
							</form>
							</div>
				</div>
			</div>
		</div>
	</section>
              
       
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