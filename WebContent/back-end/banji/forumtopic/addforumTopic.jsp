<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.forumtopic.model.*"%>
<%@ page import="com.banji.model.*"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="forumtopicSvc" scope="page"
	class="com.forumtopic.model.ForumTopicService" />

<%
	String no = request.getParameter("banjiNo");
	pageContext.setAttribute("no", no);
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
<link rel="stylesheet" media="screen, print"
	href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>

<link
	href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>

<!-- notifications 的css連結 -->
<link rel="stylesheet" media="screen, print"
	href="<%=request.getContextPath()%>/SmartAdmin4/css/notifications/sweetalert2/sweetalert2.bundle.css">


<style>
img {
	max-width: 100%;
	max-height: 100%;
	border: 2px #C4B1B1 dashed;
}

#pic {
	width: 150px;
	height: 150px;
}
</style>

</head>
<body
	class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
	<script>
		var classHolder = document.getElementsByTagName("BODY")[0];
	</script>

	<div class="page-wrapper">
	${banjiVO.banjiNo}
		<div class="page-inner">
			<%@ include file="/back-end/template/left_aside.jsp"%>
			<div class="page-content-wrapper">
				<%@ include file="/back-end/template/header.jsp"%>
				<main id="js-page-content" role="main" class="page-content">
					<ol class="breadcrumb page-breadcrumb">
						<li class="breadcrumb-item"><a href="javascript:void(0);">後台首頁</a></li>
						<li class="breadcrumb-item">Democrat</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-democrat'></i> Democrat
						</h1>
					</div>
					<div id="panel-2" class="panel">
					<form METHOD="post"
								ACTION="<%=request.getContextPath()%>/banji/banji.forumtopic" name="form1">
						<div class="panel-hdr">
							

								<h2>
									新增 <span class="fw-300"><i>主題名稱</i></span>
								</h2>


								<div class="panel-toolbar">
									<button class="btn btn-panel" data-action="panel-collapse" data-toggle="tooltip" data-offset="0,10"
										data-original-title="Collapse"></button>
									<button class="btn btn-panel" data-action="panel-fullscreen"
										data-toggle="tooltip" data-offset="0,10"
										data-original-title="Fullscreen"></button>
									<button class="btn btn-panel" data-action="panel-close"
										data-toggle="tooltip" data-offset="0,10"
										data-original-title="Close"></button>
								</div>
						</div>

						<div class="panel-container show">
							<div class="panel-content">

								<div class="form-group">
									<label class="form-label" for="description">主題名稱<span
										class="text-danger">*</span></label> 
										<input type="text" class="form-control" id="description" name="forumTopicName" required>
									<div class="invalid-feedback">請勿空白</div>
								</div>
								<div class="form-group" id="add">
									<input type="hidden" name="action" value="insert">
									<input type="hidden" name="banjiNo" value="${no}">
									<button type="submit"
										class="btn btn-primary justify-content-center">送出</button>
								</div>

								
							</div>
						</div>
						</form>
					</div>
				</main>
			</div>

		</div>

		<div class="page-content-overlay" data-action="toggle"
			data-class="mobile-nav-on"></div>
		<%@ include file="/back-end/template/footer.jsp"%>
	</div>

	<%@ include file="/back-end/template/quick_menu.jsp"%>
	<%@ include file="/back-end/template/messager.jsp"%>
	<%@ include file="/back-end/template/basic_js.jsp"%>
	<script>
		'use strict';
		$(document).ready(function() {
			var forms = document.getElementsByClassName('needs-validation');
			var validation = Array.prototype.filter.call(forms, function(form) {
				form.addEventListener('submit', function(event) {
					if (form.checkValidity() === false) {
						event.preventDefault();
						event.stopPropagation();
					}
					form.classList.add('was-validated');
				}, false);
			});
		});
	</script>
</body>

</html>