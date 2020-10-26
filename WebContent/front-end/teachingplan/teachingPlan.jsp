<%@page import="com.teachingplan.model.*"%>
<%@page import="com.course.model.*"%>


<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />


<%
	@SuppressWarnings("unchecked")
	List<TeachingPlanVO> list = (List<TeachingPlanVO>) session.getAttribute("teachingPlanVO");
	session.setAttribute("list", list);
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
						<li class="breadcrumb-item">教學計劃</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-calendar-exclamation'></i>
							教學計劃
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-11">
							<%@ include file="/front-end/course/courseDetail.jsp"%>
							<div id="panel-2" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">教學計劃總覽</h2>
								</div>
								<div class="panel-container show" style="margin: 20px 20px;">
									<div class="panel-content">
										<c:forEach var="teachingPlanVO" items="${list}">
<table>
											<tr>
												<td>${teachingPlanVO.teachingPlanNo}</td>
											</tr>
											<tr>
												<td>${teachingPlanVO.week}</td>
											</tr>
											<tr>
												<td>${teachingPlanVO.lesson}</td>
											</tr>
											<tr>
												<td>${teachingPlanVO.planContent}</td>
											</tr>
											
											
										</table>	


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
		// 		$(document).ready(function() {
		// 			$('#dt-basic-example').dataTable({
		// 				responsive : true,
		// 				pageLength : 15,
		// 				order : [ [ 2, 'desc' ] ],
		// 				rowGroup : {
		// 					dataSrc : 2
		// 				}
		// 			});
		// 		});
	</script>
</body>
</html>