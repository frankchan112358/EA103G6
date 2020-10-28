<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.forumpost.model.*"%>
<%@ page import="com.forumtopic.model.*"%>
<%@ page import="com.banji.model.*"%>
<%@ page import="com.forumcomment.model.*"%>
<%@ page import="com.student.model.*"%>

<%@ page import="java.util.*"%>

<%
// 	ForumPostService forumpostSvc = new ForumPostService();
//     List<ForumPostVO> list = forumpostSvc.getAll();
//     pageContext.setAttribute("list", list);
   BanjiService banjiSvc = new BanjiService();
	StudentService studentSvc = new StudentService(); 
	StudentVO student = studentSvc.getOneStudentByUserNo(userVO.getUserNo());
	BanjiVO banjiVO = banjiSvc.getOneBanji(student.getBanjiNo());


	ForumTopicService forumtopicSvcList =new ForumTopicService();
	List<ForumTopicVO> forumTopicList =forumtopicSvcList.getByBanJiNo(banjiVO.getBanjiNo());
	ForumPostService forumpostSvc = new ForumPostService();
	List<ForumPostVO> list = forumpostSvc.getByTopicNo(forumTopicList.get(0).getForumTopicNo());
	pageContext.setAttribute("list", list);
 	pageContext.setAttribute("forumTopicList", forumTopicList);
%>
<jsp:useBean id="forumcommentSvc" scope="page" class="com.forumcomment.model.ForumCommentService" />
<jsp:useBean id="forumtopicSvc" scope="page" class="com.forumtopic.model.ForumTopicService" />
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
<!--                          <div class="input-group input-group-lg mb-g"> -->
<!--                                     <input type="text" class="form-control shadow-inset-2" placeholder="Search Discussion"> -->
<!--                                     <div class="input-group-append"> -->
<!--                                         <span class="input-group-text"><i class="fal fa-search"></i></span> -->
<!--                                     </div> -->
<!--                                 </div> -->
                    
                                
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-democrat'></i> 討論區
                        </h1>
                       
                                
                    </div>
                    <c:forEach var="forumTopicVO" items="${forumtopicSvc.getByBanJiNo(studentVO.banjiNo)}">
                    	<form method="post" action="<%=request.getContextPath()%>/forumPost/forumPost.do">
                    		<input type="hidden" name="action" value="getByTopicNo">
                    		
                    		<input type="hidden" name="forumTopicNo" value="${forumTopicVO.forumTopicNo}">
                    		
                    		<input type="submit" value="${forumTopicVO.forumTopicName}">
                    	</form>
                    	
                    	
                    </c:forEach>
                     <div class="row">
                        <div class="col col-xl-12">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">討論區</h2>
                                </div>
                                
                                <div class="col d-flex">
                                                <a class="btn btn-outline-success btn-sm ml-auto mr-2 flex-shrink-0" onclick="location.href='${pageContext.request.contextPath}/front-end/forumpost/addForumPost.jsp'">新增貼文</a>
                                            </div>
                                
					<div class="container">
						<div class="row">
							<div class="col-md-3">
								<div id="heading1">
									<a href="<%=request.getContextPath()%>/front-end/forumpost/forumPost_index.jsp"><b>最多回覆</b></a>
								</div>
							</div>
							<div class="col-md-3">
								<div id="heading2">
									<b>最多觀看</b>
								</div>
							</div>
							<div class="col-md-5">
								<form method="post"
									action="<%=request.getContextPath()%>/forumPost/forumPost.do">
									<input type="text" name="title"> <input type="hidden" 
										name="action" value="search">  
								</form>
							</div>
							
						</div>

					</div>
					<%@ include file="page1_ByCompositeQuery.file"%>
					<hr>

					<div class="container">
						<div class="row">
							<div class="col-md-8" id="title" style="font-weight: bold;">貼文標題</div>
							<div class="col-md-2" style="font-weight: 900;">更新時間</div>
							<div class="col-md-2" style="font-weight: 900;">觀看數</div>
							<div class="col-md-2" style="font-weight: 900;">發文時間</div>
							
							
						</div>
						<hr>

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
								<div class="col-md-2">${forumPostVO.forumPostViews}</div>
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

