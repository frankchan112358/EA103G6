<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.teachingplan.model.*"%>
<%@ page import="java.util.*"%>


<%
	TeachingPlanService teachingPlanSvc = new TeachingPlanService();
	List<TeachingPlanVO> list = teachingPlanSvc.getAll();
	pageContext.setAttribute("list", list);
%>



<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">

<style>

th{
font-size: 15px;
}

td{
font-size: 15px;
}

.table th, .table td {
	vertical-align: middle;
	text-align: center;
}

.form-row {
   margin:auto;
   margin-bottom: 20px;
}

</style>

</head>
<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">




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
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a>
						</li>
						<li class="breadcrumb-item">
							<a id="aListAllCourse" banjiNo="${courseVO.banjiNo}" href="javascript:void(0)">課程總覽</a>
						</li>
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/back-end/teachingplan/listAllTeachingPlan.jsp">教學計劃管理</a>
						</li>
						<li class="breadcrumb-item">教學計劃資料</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-chalkboard-teacher'></i>
							教學計劃資料
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-12">
					<jsp:include page="/back-end/course/courseNav.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>教學計劃資料</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<!-- datatable start -->
										<table class="table table-bordered table-hover table-striped w-100">
											<tr>
												<th>週次</th>
												<td>第${teachingPlanVO.week}週</td>
											</tr>
											<tr>
												<th>堂數</th>
												<td>${teachingPlanVO.lesson}</td>
											</tr>
											<tr>
												<th>教學內容</th>
												<td  width="85%">${teachingPlanVO.planContent}</td>
											</tr>
										</table>
										<!-- datatable end -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</main>


				<div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
				<%@ include file="/back-end/template/footer.jsp"%>
			</div>
		</div>
	</div>


	<%@ include file="/back-end/template/quick_menu.jsp"%>
	<%@ include file="/back-end/template/messager.jsp"%>
	<%@ include file="/back-end/template/basic_js.jsp"%>




	<script>
	$(document).ready(function(){
		
		document.getElementById('aListAllCourse').addEventListener('click',function(e){
				e.preventDefault();
				let _this = this;
                let banjiNo = this.getAttribute('banjiNo');
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/course.do';
				myForm.method = 'POST';
				let banjiNoInput = document.createElement('input');
				banjiNoInput.type = 'hidden';
				banjiNoInput.name = 'banjiNo';
				banjiNoInput.value= banjiNo;
				myForm.append(banjiNoInput);
				myForm.submit();
			}, false);

	});		
	</script>
</body>
</html>