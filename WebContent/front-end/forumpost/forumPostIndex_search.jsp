<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.forumpost.model.*"%>
<%@ page import="com.forumtopic.model.*"%>

<%@ page import="com.forumcomment.model.*"%>
<%@ page import="com.student.model.*"%>

<%@ page import="java.util.*"%>

<%
ForumPostService forumpostSvc = new ForumPostService();
List<ForumPostVO> list = (List<ForumPostVO>) request.getAttribute("list");
pageContext.setAttribute("list", list);			
%>
<jsp:useBean id="forumcommentSvc" scope="page" class="com.forumcomment.model.ForumCommentService"/>

<!DOCTYPE HTML>
<html>


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

	<section id="two" class="wrapper style2">
		<div class="inner">
			<div class="box" style="text-align:center">
				<div class="content">
					
					<div class="container">
						<div class="row">
							
							<div class="col-md-5" id="search">
								<form method="post" action="<%=request.getContextPath() %>/forumPost/forumPost.do">
									<input type="text" name="title">
									<input type="hidden" name="action" value="search">
									<button type="submit"class="btn btn-primary justify-content-center">搜尋</button>
								</form>
							</div>
							<header class="align-center">
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
					</header>
							
						</div>
						
					</div>
				<hr>
			
					<div class="container">
						<div class="row">
							<div class="col-md-8" id="title" style="font-weight: bold;">發文時間</div>
							<div class="col-md-2" style="font-weight: 900;">更新時間</div>
							<div class="col-md-2" style="font-weight: 900;">回應數</div>
							
							<div class="col-md-2" style="font-weight: 900;">貼文標題</div>
							
							
						</div>
						<hr>
						 					<%@ include file="page1_ByCompositeQuery.file"%>
						 
						<c:forEach var="forumPostVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
															<div class="row">
								<div class="col-md-8" id="title">
					
								
<!-- 								<div class="col-md-2"> -->
<%-- 									<fmt:formatDate value="${forumPostVO.updateTime}" --%>
<%-- 										pattern="yyyy-MM-dd HH:mm:ss" /> --%>
<!-- 								</div> -->
								
												
								</div>
								<div class="col-md-2">
									<fmt:formatDate value="${forumPostVO.updateTime}"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</div>
								
								 <div class="card-body p-0">
                                        <div class="row no-gutters row-grid">
                                            <!-- thread -->
                                            <div class="col-12">
                                                <div class="row no-gutters row-grid align-items-stretch">
                                                    <div class="col-md">
                                                        <div class="p-3">
                                                            <div class="d-flex">
                                                                <span class="icon-stack display-4 mr-3 flex-shrink-0">
                                                                    <i class="base-2 icon-stack-3x color-primary-400"></i>
                                                                    <i class="base-10 text-white icon-stack-1x"></i>
                                                                    <i class="ni md-profile color-primary-800 icon-stack-2x"></i>
                                                                </span>
                                                                <div class="d-inline-flex flex-column">
                                                                    <a href="<%=request.getContextPath()%>/forumPost/forumPost.do?action=getOne_For_Display&forumPostNo=${forumPostVO.forumPostNo}" class="fs-lg fw-500 d-block">
                                                                        ${forumPostVO.title}
                                                                    </a>
                                                                    <div class="d-block text-muted fs-sm">
                                                                        <fmt:formatDate value="${forumPostVO.createTime}"
										                                 pattern="yyyy-MM-dd HH:mm:ss" />
                                                                        
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
<!--                                                     <div class="col-4 col-md-2 col-xl-1 hidden-md-down"> -->
<!-- <!--                                                         <div class="p-3 p-md-3"> --> 
<!-- <!--                                                             <span class="d-block text-muted">243 <i>Topics</i></span>  -->
<!-- <!--                                                             <span class="d-block text-muted">407 <i>Posts</i></span>  -->
<!-- <!--                                                         </div> --> 
<!--                                                     </div> -->
                                                    <div class="col-8 col-md-3 hidden-md-down">
                                                        <div class="p-3 p-md-3">
                                                            <div class="d-flex align-items-center">
<!--                                                                 <div class="d-inline-block align-middle status status-success status-sm mr-2"> -->
<!--                                                                     <span class="profile-image-md rounded-circle d-block" style="background-image:url('img/demo/avatars/avatar-a.png'); background-size: cover;"></span> -->
<!--                                                                 </div> -->
                                                                <div class="flex-1 min-width-0">
<!--                                                                     <a href="javascript:void(0)" class="d-block text-truncate"> -->
<!--                                                                         Duis vitae sapien urna. Proin pellentesque laoreet ligula pharetra semper. -->
<!--                                                                     </a> -->
<!--                                                                     <div class="text-muted small text-truncate"> -->
<!--                                                                         Today, 12:12 <a href="javascript:void(0)" class="text-info">katty60</a> -->
<!--                                                                     </div> -->
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                   						</div>
                   					</div>


								<div class="col-md-2">${forumcommentSvc.getFcResponsesByFpNo(forumPostVO.forumPostNo)}</div>


							</div>
							<hr>

						</c:forEach>
						<%@ include file="page2_ByCompositeQuery.file"%>
					</div>
										</div>
										</div>
					

				</div>

			
		
		<%@ include file="/front-end/template/footer.jsp" %>
		<%@ include file="/front-end/template/quick_menu.jsp" %>
    <%@ include file="/front-end/template/messager.jsp" %>
    <%@ include file="/front-end/template/basic_js.jsp" %>   

	
</section>
</main>
</div>
</div>
</div>

</body>


</html>

