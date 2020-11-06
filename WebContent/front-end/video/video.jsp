<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.video.model.*"%>
<%@ page import="com.course.model.*"%>
<%@ page import="com.timetable.model.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<jsp:useBean id="videoSvc" scope="page" class="com.video.model.VideoService" />
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="timetableSvc" scope="page" class="com.timetable.model.TimetableService" />
<%
session.setAttribute("courseWork", "courseVideo");
%>
<!DOCTYPE html>
<html>

<head>
	<%@ include file="/front-end/template/head.jsp"%>
	<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/formplugins/summernote/summernote.css">
	<style>
		.panel .panel-container .panel-content {
			padding: 0;
		}
		.player {
			width: 100%;
			background-color: #000;
		}
video {
	display: block;
	width: 100%;
	margin: 12px auto;
}
		.container-fluid,
		.container-sm,
		.container-md,
		.container-lg,
		.container-xl {
			padding-right: 0;
			padding-left: 0;
		}
		.bg-light {
			background-color: #000 !important;
		}
		.border-right {
			border-right: 3px solid #2d3947 !important;
		}
		.border-bottom {
			border-bottom: none !important;
		}
		.border-left {
			border-bottom: none !important;
		}
		.in-sb{
			font-size: 1.6em;
			color : white;
			/* 	margin-left: 40px; */
		}
		a[target]:not(.btn) {
			text-decoration: none !important;
		}
		.sb1 {
			padding: 15px;
			border-bottom: 1px solid #666 !important;
		}
		.btn1{
			/* 	margin-left: 80px; */
			margin-top: 10px;
		}
		.btn2 {
			background-color: #252f3b;
			border-color: #2d3947;
			margin-left: -17px;
			width: 40px;
			height: 40px;
			padding: 5px;
			border-radius: 0 5px 5px 0;
			-webkit-box-shadow: inset 0 0px 0 rgba(255, 255, 255, 0.15), 0 0px 0px rgba(0, 0, 0, 0.075);
			box-shadow: inset 0 0px 0 rgba(255, 255, 255, 0.15), 0 0px 0px rgba(0, 0, 0, 0.075);
			font-size: 23px;
		}
		.btn2:hover {
			color: #212529;
			background-color: #2d3947;
			border-color: #2d3947;
			-webkit-box-shadow: inset 0 0px 0 rgba(255, 255, 255, 0.15), 0 0px 0px rgba(0, 0, 0, 0.075);
			box-shadow: inset 0 0px 0 rgba(255, 255, 255, 0.15), 0 0px 0px rgba(0, 0, 0, 0.075);
		}
		.btn2:focus {
			color: #212529;
			background-color: #2d3947;
			border-color: #2d3947;
			-webkit-box-shadow: inset 0 0px 0 rgba(255, 255, 255, 0.15), 0 0px 0px rgba(0, 0, 0, 0.075);
			box-shadow: inset 0 0px 0 rgba(255, 255, 255, 0.15), 0 0px 0px rgba(0, 0, 0, 0.075);
		}
		.fa-list-ul:before {
			color: #82a82d;
			;
		}
		.videoname {
			margin-left: 10px;
			margin-top: 5px;
		}
		#sbLog{
			font-size: 1.3em;
			color: #FFFAE6;
			background-color: #658F4B;
		}
		#wrapper {
			overflow: hidden;
		}
		#sidebar-wrapper {
			background-color: #2d3947 !important;
			min-height: 100vh;
			margin-left: -20rem;	
			-webkit-transition: margin .25s ease-out;
			-moz-transition: margin .25s ease-out;
			-o-transition: margin .25s ease-out;
			transition: margin .25s ease-out;
		}
			#sidebar-wrapper .list-group {
			width: 20rem;
			height: 55em;
			overflow-y: scroll;
		}
		#page-content-wrapper {
			min-width: 100%;
			background-color: #000;
		}
		
		#wrapper.toggled #sidebar-wrapper {
			margin-left: 0;
		}
		@media (min-width: 768px) {
 		 #sidebar-wrapper {
			margin-left: 0;
  		}
  		#page-content-wrapper {
  			min-width: 0;
  			width: 100%;
  			background-color: #000;
  		}
  		#wrapper.toggled #sidebar-wrapper {
			margin-left: -20rem;
  		}
}
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
						<li class="breadcrumb-item">課程影片</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class="subheader-icon fal fa-camera-movie"></i>
							教學影片
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-12">
							<jsp:include page="/front-end/course/courseDetail.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">${courseVO.courseName}</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<div class="d-flex" id="wrapper">
											<div class="bg-light border-right" id="sidebar-wrapper">
												<div class="list-group list-group-flush">
															<c:forEach var="timetableVO" items="${timetableSvc.getAllWithCourseNo(courseNo)}">
																	<div class="sb1">
																		<a class=vpath videoNo="${videoSvc.getOneVideoWithTimetableNo(timetableVO.timetableNo).videoNo}" href="javascript:void(0)">
																			<span class="video-title in-sb">
																				<i class="fas fa-caret-circle-right"></i>
																				${timetableVO.timetableDate}
																				${timetableVO.periodText}
																			</span>
																		</a>
																		<div class="in-sb-log">
																			<button timeteableNo="${timetableVO.timetableNo}" type="button"
																							class="btn btn-primary btn-pills waves-effect waves-themed btn1" style="font-size: 13px;"><i class="fal fa-book-spells"></i>教學筆記</button>
																		</div>
																	</div>
															</c:forEach>
												</div>
											</div>
											<div id="page-content-wrapper">
												<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
													<button class="btn btn-primary btn2" id="menu-toggle">
														<i class="far fa-list-ul"></i>
													</button>
												</nav>
												<div class="player container-fluid">
													<h1 class="text-white videoname">${courseSvc.getOneCourse(courseNo).courseName}</h1>
													<video id="videoPlayer" width="100%" height="100%" src="" type="video/mp4" loop autoplay poster="<%=request.getContextPath()%>/images/bg.png"></video>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</main>
				<div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
				<%@ include file="/front-end/template/footer.jsp"%>
			</div>
		</div>
	</div>
	<div class="modal fade" id="sbLogModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" style="font-size:2em">教學筆記</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">
							<i class="fal fa-times"></i>
						</span>
					</button>
				</div>
				<div id="sbLog" class="modal-body"></div>
			</div>
		</div>
	</div>
	<%@ include file="/front-end/template/quick_menu.jsp"%>
	<%@ include file="/front-end/template/messager.jsp"%>
	<%@ include file="/front-end/template/basic_js.jsp"%>
	<script src="<%=request.getContextPath()%>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
	<script>
		'use strict';
		$(document).ready(function () {
			//這裡是滑鼠進入slide-bar會讓區塊變色
			// 			$(".in-sb").hover(function () {
			// 				$(this).css("background-color", "#a9ec62");
			// 			},
			// 				function () {
			// 					$(this).css("background-color", " #c3dbaa");
			// 				})
			var vIndex = 1000;
			var path = null;
			$('.vpath').click(function (e) {
				e.preventDefault();
				let _this = $(this);
				let _videoNo = _this.attr('videoNo');
				let _videoPlayer = $('#videoPlayer');
				_videoPlayer.attr('src', '');
				if (_videoNo != '') {
					_videoPlayer.attr('src', `<%=request.getContextPath()%>/videos/${'${_videoNo}'}.mp4?_=${'${Math.random()}'}`);
					_videoPlayer.prop("controls", true);
				}else{
					$("video").prop("controls", false);
				}
				$('h1.videoname').html(_this.find('span.video-title').html());
			});
			//這裡是click(in-sb-log)可以查看日誌
			$(document).on('click', '.in-sb-log button', function (event) {
				let timeteableNo = this.getAttribute('timeteableNo');
				$.ajax({
					type: 'POST',
					url: '<%=request.getContextPath()%>/timetable/teachingNoteSummernote',
					data: {
						action: 'read',
						timetableNo: timeteableNo
					},
					success(res) {
						if (res != null) {
							$('#sbLog').html('');
							$('#sbLog').html(res);
							$('#sbLogModal').modal('show');
						}
					}
				});
			});
		});
		$("#menu-toggle").click(function (e) {
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
		});
	</script>
</body>

</html>