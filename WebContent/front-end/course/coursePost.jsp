<%@page import="com.coursepost.model.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<%
	@SuppressWarnings("unchecked")
	List<CoursePostVO> list = (List<CoursePostVO>) session.getAttribute("coursePostVO");
	session.setAttribute("list", list);
	session.setAttribute("courseWork", "coursePost");
%>



<!DOCTYPE html>
<html>
<head>
<%@ include file="/front-end/template/head.jsp"%>

<style>
</style>
</head>
<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
	<script>
		var classHolder = document.getElementsByTagName("BODY")[0];
	</script>
	<div class="page-wrapper">
		<div class="page-inner">
			<%@ include file="/front-end/template/left_aside.jsp"%>
			<div class="page-content-wrapper">
				<%@ include file="/front-end/template/header.jsp"%>
				<main id="js-page-content" role="main" class="page-content">
					<ol class="breadcrumb page-breadcrumb">
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/front-end/index/index.jsp">前台首頁</a>
						</li>
						<li class="breadcrumb-item">
						<a href="<%=request.getContextPath()%>/front-end/course/selectCourse.jsp">我的課程</a>
						</li>
						<li class="breadcrumb-item">課程公告</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-calendar-exclamation'></i>
							課程公告
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-11">
							<jsp:include page="/front-end/course/courseDetail.jsp"></jsp:include>
							<div id="panel-2" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">課程公告總覽</h2>
								</div>
								<div class="panel-container show" style="margin: 20px 20px;">
									<div style="text-align: center;">
										<c:if test="${empty list}">
											<h2>
												<i class="fal fa-calendar-times"></i>
												目前尚無任何課程公告
												<i class="fal fa-calendar-times"></i>
											</h2>
											<h4>講師將會在這發佈與課程相關的公告或是通知課程內容更新的公告。</h4>
										</c:if>
									</div>
									<div class="accordion accordion-outline" id="coursePost">
										<c:forEach var="coursePostVO" items="${list}">
											<c:if test="${!empty list}">
												<div class="card">
													<div class="card-header">
														<a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#coursePost${coursePostVO.coursePostNo}" aria-expanded="false">
															<i class="fal fa-comment-alt-smile mr-3 fa-2x"></i>
															<span>
																<b>【${coursePostVO.title}】</b>
															</span>
															<span>
																<fmt:formatDate value="${coursePostVO.updateTime}" pattern="yyyy年MM月dd日 HH'點'mm'分'" />
															</span>
															<span class="ml-auto">
																<span class="collapsed-reveal">
																	<i class="fal fa-minus fs-xl"></i>
																</span>
																<span class="collapsed-hidden">
																	<i class="fal fa-plus fs-xl"></i>
																</span>
															</span>
														</a>
													</div>
												</div>
												<div id="coursePost${coursePostVO.coursePostNo}" class="collapse" data-parent="#coursePost">
													<div class="card-body">
														<h4>${coursePostVO.postContent}</h4>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>
				</main>
			</div>
		</div>
	</div>




	<div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>

	<%@ include file="/front-end/template/footer.jsp"%>


	<%@ include file="/front-end/template/quick_menu.jsp"%>
	<%@ include file="/front-end/template/messager.jsp"%>
	<%@ include file="/front-end/template/basic_js.jsp"%>



	<script>
		
	</script>
</body>
</html>