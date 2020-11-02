<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.coursepost.model.*"%>
<%@ page import="java.util.*"%>


<%
	CoursePostService coursePostSvc = new CoursePostService();
	List<CoursePostVO> list = coursePostSvc.getAll();
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

.panel .panel-container .panel-content {
    margin: auto;
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
							<a href="<%=request.getContextPath()%>/back-end/coursepost/listAllCoursePost.jsp">課程公告管理</a>
						</li>
						<li class="breadcrumb-item">課程公告資料</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-calendar-edit'></i>
							課程公告管理
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-12">
					<jsp:include page="/back-end/course/courseNav.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>課程公告資料</h2>
								</div>
								<div class="panel-container show justify-content-center">
									<div class="panel-content col-8">
										    <div class="card border ">
                                                <div class="card-header py-2" style="background-color:#E5F4FF;">
                                                    <div class="card-title" style="font-size:1.5em">
                                                        ${coursePostVO.title}
                                                    </div>
                                                </div>
                                                <div class="card-body" style="font-size:15px">
                                                    <blockquote class="blockquote mb-0">
                                                        <p>${coursePostVO.postContent}</p>
                                                        <footer class="blockquote-footer"><i class="fal fa-clock mr-1"></i><fmt:formatDate value="${coursePostVO.updateTime}" pattern="yyyy年MM月dd日 HH'點'mm'分'" /></footer>
                                                    </blockquote>
                                                </div>
                                            </div>
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