<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.forumpost.model.*"%>

<%@ page import="com.student.model.*"%>

<%@ page import="java.util.*"%>
<% 
ForumPostVO forumPostVO = (ForumPostVO)request.getAttribute("forumPostVO");
%>
<jsp:useBean id="studentSvc" scope="page" class="com.student.model.StudentService"/>
<jsp:useBean id="userSvc" scope="page" class="com.user.model.UserService"/>

<!DOCTYPE HTML>
<html>


<head>
    <%@ include file="/front-end/template/head.jsp" %>
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/formplugins/summernote/summernote.css">
</head>

<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
	
			 <h4><a href="<%=request.getContextPath() %>/front-end/forumpost/forumPost_index.jsp">回討論區</a></h4>
	
	
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
			<div class="box">
				<div class="content">
					<div class="container">
						<div class="row">
							<div class="col-md-12" id="replyBtn">
								<form method="post" action="<%=request.getContextPath()%>/forumComment/forumComment.do">
									<input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}">
									<input type="hidden" name="studentNo" value="${sessionScope.studentVO.studentNo}">
									<input type="hidden" name="action" value="getOne_For_AddFc">
									<button type="submit"class="btn btn-primary justify-content-center"style="float:right">新增留言</button>

								</form>
							</div>
						</div>
						
						 <div class="card mb-g border shadow-0">
                                    <div class="card-header p-0">
                                        <div class="p-3 d-flex flex-row">
                                            <div class="d-block flex-shrink-0">
                                            </div>
							<div class="d-block ml-2"${forumPostVO.title}>
								<div><b>樓主</b></div>
								
                            <c:if test="${userSvc.getOneUser(studentSvc.getOneStudent(forumPostVO.studentNo).userNo).photo eq null}">
                           <img src="<%=request.getContextPath() %>/images/noPicture.png" style="width: 100px; height: 100px;">
                          </c:if>
                          <c:if test="${userSvc.getOneUser(studentSvc.getOneStudent(forumPostVO.studentNo).userNo).photo ne null}">
                          <img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${studentSvc.getOneStudent(forumPostVO.studentNo).userNo}" style="width: 100px; height: 100px;">
                         </c:if>							

							<div class="col-md-12" id="AstudentName"><b>${studentSvc.getOneStudent(forumPostVO.studentNo).studentName}</b></div>
							</div>
							
							 <div class="card-header p-0">
                                        <div class="p-10 d-flex flex-row" style="font-size:20px;text-align:center;">
                                            <div class="d-block flex-shrink-1">
                                            <i class="fas fa-star mr-1" style="text-align:center;">標題：</i>
                                            <span> ${forumPostVO.title}</span>
                                            <div class="card-body "><span>${forumPostVO.content}</span>
                                    	</div>
                                    	
                                    	<div class="card-footer">
                                        <div class="d-flex align-items-center">
                                         <span class="text-sm text-muted font-italic"><i class="fal fa-clock mr-1"style="font-size:10px;"></i><span>發文時間:</span><fmt:formatDate value="${forumPostVO.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                        </div>
                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                    
							

							

									


							</div>
						</div>
						
						
						
						
						<c:forEach var="forumCommentVO" items="${list}" varStatus="tag">
								 <div class="card mb-g border shadow-0">
                                    <div class="card-header p-0">
                                        <div class="p-3 d-flex flex-row">
                                            <div class="d-block flex-shrink-0">
                                            </div>
                                            							<div class="d-block ml-2"${forumPostVO.title}>
                                            
										<div id="reply"><b>${tag.index}樓</b></div>
                           <c:if test="${userSvc.getOneUser(studentSvc.getOneStudent(forumCommentVO.studentNo).userNo).photo eq null}">
                          <img src="<%=request.getContextPath() %>/images/noPicture.png" style="width: 100px; height: 100px;">
</c:if>
                          <c:if test="${userSvc.getOneUser(studentSvc.getOneStudent(forumCommentVO.studentNo).userNo).photo ne null}">
                          <img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${studentSvc.getOneStudent(forumCommentVO.studentNo).userNo}" style="width: 100px; height: 100px;">                 
</c:if>									
							
							<div class="col-md-12" id="studentName${tag.index}"><b>${studentSvc.getOneStudent(forumCommentVO.studentNo).studentName}</b></div>
									</div>
									
									<div class="col-md-10">
										<div class="container">
											<div class="row" id="content">
												<div class="col-md-12"><b>${forumCommentVO.content}</b></div>
											</div>
										</div>
									</div>
								</div>
								
								<div class="card-footer">
                                        <div class="d-flex align-items-center">
                                         <span class="text-sm text-muted font-italic"><i class="fal fa-clock mr-1"></i><span>回覆時間:</span><fmt:formatDate value="${forumCommentVO.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                        </div>
                                    </div>
								
								
								<form method="post" action="<%=request.getContextPath() %>/front-end/report/report.do">
									<div class="reportBtn">
										<input type="submit" value="檢舉"style="float:right;width:120px;height:40px;border:3px green double;"> 
										<input type="hidden"name="action" value="getOne_Fc_Report"> 
										<input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}"> 
										<input type="hidden" name="forumCommentNo" value="${forumCommentVO.forumCommentNo}">
									</div>
								</form>




								<div class="clear"></div>
								
								</div>
								</div>
						</c:forEach>
						
					</div>
				</div>
			</div>
		</div>
		</div>
	</section>
	
	
	
		<%@ include file="/front-end/template/footer.jsp" %>
		<%@ include file="/front-end/template/quick_menu.jsp" %>
    <%@ include file="/front-end/template/messager.jsp" %>
    <%@ include file="/front-end/template/basic_js.jsp" %>   

	
	</main>
	</div>
	</div>
	</div>
	
</body>

</html>
