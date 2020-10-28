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
  <title>修改貼文</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
</head>
<body>
	 <div id="updateForumPost"> </div>

	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/forumPost/forumPost.do" name="form1">
	
	 
	 
            <fieldset>
                <legend><b>修改貼文</b></legend>
<!--                 <p> -->
<!--                     <label for="forumTopicNo">主題:</label> -->
<!--                     <select size="1" name="forumTopicNo"> -->
<%-- 					<option value="1" ${('學術版'==forumPostVO.forumTopicNo)?'selected':''} >學術版</option> --%>
<%-- 					<option value="2" ${('運動版'==forumPostVO.forumTopicNo)?'selected':''} >運動版</option> --%>
<%-- 					<option value="3" ${('閒聊版'==forumPostVO.forumTopicNo)?'selected':''} >閒聊版</option> --%>
					
<!-- 				    </select> -->
<!--                 </p> -->
                
                
                
<!--                 <p> -->
<!--                     <label for="studentNo">學員編號 :</label> -->
<!--                     <input type="text" name="studentNo" -->
<%-- 					value="<%=(forumPostVO == null) ? "S000001" : forumPostVO.getStudentNo()%>"> --%>
<%--                   	<input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}"> --%>
<!--                 </p> -->
                
                <p>
                    <label for="title">標題 :</label>
									<input type="text" name="title" value="${forumPostVO.title}">
                </p>
                <p>
                    <label for="content">貼文內容 :</label>
                            <textarea id="summernote"  name="content">${forumPostVO.content}</textarea>
                    
                </p>
                
              
        
        
         <p>
         <input type="hidden" name="action" value="getOne_For_Update"> 
				<input type="submit">
	 <input type="hidden" name="studentNo" value="${studentVO.studentNo}">
				
	  <input type="hidden" name="forumTopicNo" value="${forumTopicVO.forumTopicNo}">
				</p>
            </fieldset>
        </form>
  <script>
    $(document).ready(function() {
        $('#summernote').summernote();
    });
  </script>
  </body>
  </main>
  
	</div>
	</div>
	</div>
        
   
	
	
</body>
</html>