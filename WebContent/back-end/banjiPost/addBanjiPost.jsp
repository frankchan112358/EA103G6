<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banjipost.model.*"%>

<%
	BanjiPostVO banjiPostVO = (BanjiPostVO) request.getAttribute("banjiPostVO");
%>
<jsp:useBean id="banjiSvc" scope="page"
	class="com.banji.model.BanjiService" />

<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
</head>
<body
	class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
	<script>
        var classHolder = document.getElementsByTagName("BODY")[0];
    </script>

	<div class="page-wrapper">
		<div class="page-inner">
			<%@ include file="/back-end/template/left_aside.jsp"%>
			<div class="page-content-wrapper">
				<%@ include file="/back-end/template/header.jsp"%>
				<main id="js-page-content" role="main" class="page-content">
					<ol class="breadcrumb page-breadcrumb">
						<li class="breadcrumb-item"><a href="javascript:void(0);">後台首頁</a></li>
						<li class="breadcrumb-item"><a
							href="<%=request.getContextPath()%>/back-end/banji/homeBanji.jsp">公告管理</a></li>
						<li class="breadcrumb-item">新增公告</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-users-class'></i> 新增班級公告
						</h1>
					</div>
					<div class="panel-container show">
						<div class="panel-content">
							<form METHOD="post" ACTION="<%=request.getContextPath()%>/banjiPost/banjiPost.do"name="form1">
								
								<div class="form-group">
									<label class="form-label" for="example-textarea">班級編號:</label>
									<input type="text" id="simpleinput" name="banjiNo" class="form-control">
								</div>
								<div class="form-group">
									<label class="form-label" for="example-textarea">公告標題:</label>
									<input type="text" id="simpleinput" name="title" class="form-control">
								</div>
								<div class="form-group">
									<label class="form-label" for="example-textarea">班級內容:</label>
									<textarea name="banjiPostContent" class="form-control"
										id="example-textarea" rows="5" placeholder="請輸入內容"></textarea>
								</div>
									<div class="form-group">
									<label class="form-label" for="example-select">狀態:</label> <select
										class="custom-select form-control" name="status">
										<option value="0" ${(banjiPostVO.status==1)?'selected':'' }>一般</option>
										<option value="1" ${(banjiPostVO.status==0)?'selected':'' }>緊急</option>
									</select> 
								</div>
								<div class="form-group" id="add">
									<input type="hidden" name="action" value="insert">
									<input type="hidden" name="banjiNo" value="${banjiVO.banjiNo }">
									<button type="submit"class="btn btn-primary justify-content-center">送出</button>
								</div>
							</form>
						</div>
					</div>
				</main>
				<div class="page-content-overlay" data-action="toggle"
					data-class="mobile-nav-on"></div>
				<%@ include file="/back-end/template/footer.jsp"%>
			</div>
		</div>
	</div>
	<%@ include file="/back-end/template/quick_menu.jsp"%>
	<%@ include file="/back-end/template/messager.jsp"%>
	<%@ include file="/back-end/template/basic_js.jsp"%>


</body>
</html>