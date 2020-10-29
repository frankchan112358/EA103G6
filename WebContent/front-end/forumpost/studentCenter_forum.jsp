<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.forumpost.model.*"%>
<%@ page import="com.forumtopic.model.*"%>
<%@ page import="com.user.model.*"%>
<%@ page import="com.forumcomment.model.*"%>
<%@ page import="com.student.model.*"%>

<%@ page import="java.util.*"%>

<%
	UserVO userVO1 = (UserVO)session.getAttribute("userVO");
	StudentService studentsvc = new StudentService();
	StudentVO studentVO1 = studentsvc.getOneStudentByUserNo(userVO1.getUserNo());
	ForumPostService forumpostSvc = new ForumPostService();
	List<ForumPostVO> list = forumpostSvc.getOneSTUDENT(studentVO1.getStudentNo());
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="userSvc" scope="page" class="com.user.model.UserService"/>
<jsp:useBean id="studentSvc" scope="page" class="com.student.model.StudentService"/>
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
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-democrat'></i> 發文紀錄
                        </h1>
                    </div>
                    			 <h4><a href="<%=request.getContextPath() %>/front-end/forumpost/forumPost_index.jsp">回討論區</a></h4>
                    
                   <hr>
					<div class="row">
							<div class="col-md-8" id="title" style="font-weight: bold;">貼文標題</div>
							<div class="col-md-2" style="font-weight: 900;">更新時間</div>
						<div class="col-md-1"></div>
						<div class="col-md-1"></div>
					</div>
					<hr>
					<%@ include file="page1_ByCompositeQuery.file" %>
					<c:forEach var="forumPostVO" items="${list}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">
					
					<hr>
					     	
						<div class="row" id=forum_record>
							<div class="col-md-8"><a href="<%=request.getContextPath()%>/forumPost/forumPost.do?action=getOne_For_Display&forumPostNo=${forumPostVO.forumPostNo}"> ${forumPostVO.title}</a></div>
							<div class="col-md-2">
								<fmt:formatDate value="${forumPostVO.updateTime}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</div>
							
							
							
							
							
							
							
							
							<div class="col-md-1">
							<form method="post" action="<%=request.getContextPath()%>/forumPost/forumPost.do">
								<input type="submit" value="修改">
								<input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}">
								<input type="hidden" name="action" value="getOne_For_Update">
							</form>
							</div>
							<div class="col-md-1">
							<form method="post" action="<%=request.getContextPath()%>/forumPost/forumPost.do">
								<input type="submit" value="刪除">
								<input type="hidden" name="action" value="delete">
								<input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}">
							</form>
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
						<hr>
					</c:forEach>
					<%@ include file="page2_ByCompositeQuery.file" %>

</main>
				</div>
				

			</div>
		</div>
		<%@ include file="/front-end/template/footer.jsp" %>
		<%@ include file="/front-end/template/quick_menu.jsp" %>
    <%@ include file="/front-end/template/messager.jsp" %>
    <%@ include file="/front-end/template/basic_js.jsp" %>   

	
	
</body>

</html>

