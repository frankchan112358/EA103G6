<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.course.model.*,com.video.model.*,com.timetable.model.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="videoSvc" scope="page" class="com.video.model.VideoService" />

<%
%>

<!DOCTYPE html>
<html>

<head>
	<%@ include file="/back-end/template/head.jsp" %>
<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">


<style>
	.table th,
	.table td {
		vertical-align: middle;
		text-align: center;
	}

	#film {
		padding-top: 15%;
		box-sizing: border-box;
	}

	#film video {
		width: 100%;
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
							<a id="aListAllCourse" banjiNo="${courseSvc.getOneCourse(courseNo).banjiNo}" href="javascript:void(0)">課程總覽</a>
						</li>
						<li class="breadcrumb-item">
							影片管理
						</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class="subheader-icon far fa-video"></i>
							影片管理
						</h1>
					</div>
					<div class="row">
						<div class="col-xl-12">
							<jsp:include page="/back-end/course/courseNav.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>影片列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<!-- datatable start -->
										<table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color:#E5F4FF;">
												<tr>
													<th width="30%">上課日期</th>
													<th width="30%">時段</th>
													<th width="40%">影片</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="timetableVO" items="${courseVO.timetableList}">
													<tr>
														<td>${timetableVO.timetableDate}</td>
														<td>${timetableVO.periodText}</td>
														<td class="d-flex p-1 justify-content-center">
															<c:if test="${videoSvc.getOneVideoWithTimetableNo(timetableVO.timetableNo)!=null}">
																<FORM class="m-1 mb-0" METHOD="post" ACTION="<%=request.getContextPath()%>/video/video.do">
																	<button type="submit" class="btn btn-success ">
																		<span class="fal fa-edit mr-1"></span>
																		<span>重新上傳</span>
																	</button>
																	<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																	<input type="hidden" name="videoNo" value="${videoSvc.getOneVideoWithTimetableNo(timetableVO.timetableNo).videoNo}">
																	<input type="hidden" name="timetableNo" value="${timetableVO.timetableNo}">
																	<input type="hidden" name="action" value="getOne_For_Update">
																</FORM>
																<FORM class="m-1 mb-0" METHOD="post" ACTION="<%=request.getContextPath()%>/video/video.do" style="margin-bottom:0px;">
																	<button type="submit" class="btn btn-danger">
																		<span class="fal fa-times mr-1"></span>
																		<span>下架影片</span>
																	</button>
																	<input type="hidden" name="videoNo" value="${videoSvc.getOneVideoWithTimetableNo(timetableVO.timetableNo).videoNo}">
																	<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																	<input type="hidden" name="action" value="delete">
																</FORM>
															</c:if>
															<c:if test="${videoSvc.getOneVideoWithTimetableNo(timetableVO.timetableNo)==null}">
																<FORM class="m-1 mb-0" METHOD="Post" ACTION="<%=request.getContextPath()%>/back-end/video/addVideo.jsp">
																	<button type="submit" class="btn btn-info ">
																		<span class="fal fa-upload"></span>
																		<span>上傳影片</span>
																	</button>
																	<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																	<input type="hidden" name="timetableNo" value="${timetableVO.timetableNo}">
																</FORM>
															</c:if>
															<button timetableNo="${timetableVO.timetableNo}" type="button" class="btn btn btn-dark" data-toggle="modal" data-target=".example-modal-centered-transparent">Transparent Centered</button>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<!-- datatable end -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</main>
				<div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
				<%@ include file="/back-end/template/footer.jsp" %>
			</div>
		</div>
	</div>
	<div class="modal fade example-modal-centered-transparent" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-xl modal-dialog modal-dialog-centered modal-transparent" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title text-white">
						Basic Modals
						<small class="m-0 text-white opacity-70">
							Below is a static modal example
						</small>
					</h4>
					<button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"><i class="fal fa-times"></i></span>
					</button>
				</div>
				<div class="modal-body">
					...
					<div id="film">
						<video id="videoViewer" src="" preload controls loop allowFullScreen></video>
					</div>
					<p>File Name : <span id="filename"></span></p>
					<p>File Type : <span id="filetype"></span></p>
					<p>File Size :<span id="filesize"></span></p>
				</div>
				<div class="modal-footer">
					<input id="videoFile" type="file" />
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					<button id="uploadVideo" type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>


	<%@ include file="/back-end/template/quick_menu.jsp" %>
	<%@ include file="/back-end/template/messager.jsp" %>
	<%@ include file="/back-end/template/basic_js.jsp" %>


	<script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
	<script>


		$(document).ready(function () {
			var workStatus = 'none';
			var _timetableNo = '';
			$('#coursetable').dataTable({
				responsive: true,
				language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' },
				"columnDefs": [{
					"targets": [-1, -2],
					"orderable": false
				}]
			});

			document.getElementById('aListAllCourse').addEventListener('click', function (e) {
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
				banjiNoInput.value = banjiNo;
				myForm.append(banjiNoInput);
				myForm.submit();
			}, false);

			$('#videoFile').on('change', function () {
				let file = this.files[0];
				let reader = new FileReader();
				reader.onload = viewer.load;
				reader.readAsDataURL(file);
				viewer.setProperties(file);
			});

			var viewer = {
				load: function (e) {
					$('#videoViewer').attr('src', e.target.result);
				}, setProperties: function (file) {
					$('#filename').text(file.name);
					$('#filetype').text(file.type);
					$('#filesize').text(Math.round(file.size / 1024));
				}
			}

			$(document).on('click', 'button[timetableNo]', function (e) {
				_timetableNo = this.getAttribute('timetableNo');
			});

			$('#uploadVideo').click(function () {
				if (workStatus == 'none') {
					let form = new FormData();
					form.append('video', $('#videoFile')[0].files[0]);
					form.append('action', 'insert');
					form.append('timetableNo', _timetableNo);
					$.ajax({
						beforeSend() {
							workStatus = 'uploading';
						},
						type: 'POST',
						url: `<%=request.getContextPath()%>/NewVideo`,
						processData: false,
						contentType: false,
						data: form,
						success(res) {
							alert(res);
						},
						complete() {
							workStatus = 'none';
						}
					}
					);
				}
			});

		});
	</script>
</body>

</html>