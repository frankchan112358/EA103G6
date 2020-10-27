<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.forumpost.model.*"%>
<%@ page import="com.student.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<% 
ForumPostVO forumPostVO = (ForumPostVO)request.getAttribute("forumPostVO");
%>
<jsp:useBean id="studentSvc" scope="page" class="com.student.model.StudentService"/>
<jsp:useBean id="userSvc" scope="page" class="com.user.model.UserService"/>

<!DOCTYPE HTML>
<html>

<head>
<title>WJL</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>

<body class="subpage">
	
			 <h4><a href="<%=request.getContextPath() %>/front-end/forumpost/forumPost_index.jsp">回討論區</a></h4>
	
	
	<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
				<h2>WJL 討論區</h2>
			</header>
		</div>
	</section>
	<!-- Two -->
	
<%-- 	<c:if test="${userSvc.getOneUser(studentSvc.getOneStudent(forumPostVO.studentNo).userNo).photo eq null}"> --%>
<%-- 															<img src="<%=request.getContextPath() %>/images/noPicture.png"> --%>
<%-- 														</c:if> --%>
<%-- 														<c:if test="${userSvc.getOneUser(studentSvc.getOneStudent(forumPostVO.studentNo).userNo).photo ne null}"> --%>
<%-- 															<img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${studentSvc.getOneStudent(forumPostVO.studentNo).userNo}"> --%>
<%-- 														</c:if> --%>
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
									<input type="submit" value="我要回覆">
								</form>
							</div>
						</div>
<%-- 											<%@ include file="page1_ByCompositeQuery.file"%> --%>
						
						<div class="row">
							<div class="col-md-2" id="studentInfo">
								<div><b>樓主</b></div>
								
                            <c:if test="${userSvc.getOneUser(studentSvc.getOneStudent(forumPostVO.studentNo).userNo).photo eq null}">
<img src="<%=request.getContextPath() %>/images/noPicture.png" style="width: 150px; height: 150px;">
</c:if>
<c:if test="${userSvc.getOneUser(studentSvc.getOneStudent(forumPostVO.studentNo).userNo).photo ne null}">
<img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${studentSvc.getOneStudent(forumPostVO.studentNo).userNo}" style="width: 150px; height: 150px;">
</c:if>							
							
							<div class="col-md-12" id="AstudentName"><b>${studentSvc.getOneStudent(forumPostVO.studentNo).studentName}</b></div>
							</div>
							<div class="col-md-10">
									<div class="row">
										<div class="col-md-8" style="font-size:26px;"><b>${forumPostVO.title}</b></div>
										<div class="col-md-4">
<!-- 											<div class="reportBtn"> -->
<%-- 												<form method="post" action="<%=request.getContextPath() %>/front-end/report/report.do">		 --%>
<!-- 													<input type="submit" value="檢舉">  -->
<!-- 													<input type="hidden" name="action" value="getOne_Fp_Report">  -->
<%-- 													<input type="hidden" name="faId" value="${forumPostVO.forumPostNo}"> --%>
<!-- 												</form> -->
<!-- 												<input type="submit" value="追蹤"> -->
<!-- 												<div id="starImg"> -->
<%-- 													<img src="<%=request.getContextPath()%>/images/forum/1.png" style="width: 35px;height: 35px;float: right; margin-right: 15px;"> --%>
<%-- 													<input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}"> --%>
<%-- 													<input type="hidden" name="studentNo" value="${sessionScope.studentVO.studentNo}">		 --%>
<!-- 												</div> -->
											</div>
										</div>
									</div>
									<div class="clear"></div>
									
									<hr>
									<div class="row">
										<div class="col-md-12">${forumPostVO.content}</div>
									</div>
							</div>
						</div>
						
						<hr>
						<c:forEach var="forumCommentVO" items="${list}" varStatus="tag">
								<div class="row">
									<div class="col-md-2" id="studentInfo">
										<div id="reply"><b>${tag.index}樓</b></div>
                           <c:if test="${userSvc.getOneUser(studentSvc.getOneStudent(forumCommentVO.studentNo).userNo).photo eq null}">
<img src="<%=request.getContextPath() %>/images/noPicture.png" style="width: 150px; height: 150px;">
</c:if>
<c:if test="${userSvc.getOneUser(studentSvc.getOneStudent(forumCommentVO.studentNo).userNo).photo ne null}">
<img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${studentSvc.getOneStudent(forumCommentVO.studentNo).userNo}" style="width: 150px; height: 150px;">
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

<%-- 								<form method="post" action="<%=request.getContextPath() %>/front-end/report/report.do"> --%>
<!-- 									<div class="reportBtn"> -->
<!-- 										<input type="submit" value="檢舉">  -->
<!-- 										<input type="hidden"name="action" value="getOne_Fc_Report">  -->
<%-- 										<input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}">  --%>
<%-- 										<input type="hidden" name="forumCommentNo" value="${forumCommentVO.forumCommentNo}"> --%>
<!-- 									</div> -->
<!-- 								</form> -->
								<div class="clear"></div>
								<hr>
						</c:forEach>
<%-- 												<%@ include file="page2_ByCompositeQuery.file"%> --%>
						
					</div>
				</div>
			</div>
		
	</section>
	
	
	
</body>

</html>