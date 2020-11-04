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
<style>
	.table th,
	.table td {
		vertical-align: middle;
		text-align: center;
	}

	#film {
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
	<div id="videoModal" class="modal fade example-modal-centered-transparent" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-xl modal-dialog modal-dialog-centered modal-transparent" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title text-white"></h4>
					<button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"><i class="fal fa-times"></i></span>
					</button>
				</div>
				<div class="modal-body">
					<div id="film">
						<video id="videoViewer" src="" preload controls loop allowFullScreen></video>
					</div>
					<table class="videoinfo table table-bordered text-white">
						<thead>
							<tr>
								<th>影片名稱</th>
								<th>影片類型</th>
								<th>影片大小</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="videoinfo filename"></td>
								<td class="videoinfo filetype"></td>
								<td class="videoinfo filesize"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<input id="videoFile" type="file" />
					<button id="cancelUpload" type="button" class="btn btn-secondary">取消上傳</button>
					<button id="uploadVideo" type="button" class="btn btn-primary">上傳影片</button>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/back-end/template/quick_menu.jsp" %>
	<%@ include file="/back-end/template/messager.jsp" %>
	<%@ include file="/back-end/template/basic_js.jsp" %>
	<script>
		$(document).ready(function () {
			var workStatus = 'none';
			var _timetableNo = '';
			var _videoNo = '';
			var _todo = '';
			var viewer = {
				load: function (e) {
					$('#videoViewer').attr('src', e.target.result);
				}, setProperties: function (file) {
					$('.videoinfo.filename').text(file.name);
					$('.videoinfo.filetype').text(file.type);
					$('.videoinfo.filesize').text(Math.round(file.size / 1024 / 1024) + 'MB');
				}
			}
			$('table.videoinfo').hide();

			var columnSet = [
				{
					title: "上課日期",
					id: "timetableDate",
					data: "timetableDate",
					type: "text"
				},
				{
					title: "時段",
					id: "periodText",
					data: "periodText",
					type: "text"
				},
				{
					title: "影片",
					id: "videoNo",
					data: "videoNo",
					render(data, type, row, meta) {
						let html = ``;
						if (row.videoNo == '') {
							html = `<button timetableNo="${'${row.timetableNo}'}" type="button" class="insert btn btn-info m-1 mb-0">
														<span class="fal fa-upload mr-1"></span>
														<span>新增影片</span>
													 </button>`;
						} else {
							html = `<button videoNo="${'${row.videoNo}'}" type="button" class="update btn btn-success m-1 mb-0">
														<span class="fal fa-edit mr-1"></span>
														<span>重新上傳</span>
													</button>
													<button videoNo="${'${row.videoNo}'}" type="button" class="delete btn btn-danger m-1 mb-0">
														<span class="fal fa-times mr-1"></span>
														<span>下架影片</span>
													</button>`;
						}
						return html;
					}
				}];

			var coursetable = $('#coursetable').DataTable({
				responsive: true,
				language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' },
				"columnDefs": [{
					"targets": [-1, -2],
					"orderable": false
				}],
				columns: columnSet,
				ajax: {
					url: '<%=request.getContextPath()%>/video/video.ajax',
					type: 'POST',
					async: true,
					cache: false,
					data: {
						action: 'datatable',
						courseNo: '${ courseNo}'
					}
				}
			});

			$('#videoFile').on('change', function () {
				let file = this.files[0];
				let reader = new FileReader();
				reader.onload = viewer.load;
				reader.readAsDataURL(file);
				viewer.setProperties(file);
				$('table.videoinfo').show();
			});

			function resetViewer() {
				$('#videoViewer').attr('src', '');
				$('table.videoinfo').hide();
				$('.videoinfo.filename').text('');
				$('.videoinfo.filetype').text('');
				$('.videoinfo.filesize').text('');
				$('h4.modal-title').text('');
				$('#videoFile').val('');
			}

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

			$(document).on('click', 'button.insert', function (e) {
				_timetableNo = this.getAttribute('timetableNo');
				_todo = 'insert';
				$('h4.modal-title').text('新增影片');
				$('#videoModal').modal('show');
			});

			$(document).on('click', 'button.update', function (e) {
				_videoNo = this.getAttribute('videoNo');
				_todo = 'update';
				$('#videoViewer').attr('src', `<%=request.getContextPath()%>/videos/${'${_videoNo}'}.mp4`);
				$('h4.modal-title').text('重新上傳');
				$('#videoModal').modal('show');
			});

			$(document).on('click', 'button.delete', function (e) {
				_videoNo = this.getAttribute('videoNo');
				if (workStatus == 'none') {
					$.ajax({
						beforeSend() {
							workStatus = 'delete';
						},
						type: 'POST',
						url: `<%=request.getContextPath()%>/video/video.ajax`,
						data: {
							action: "delete",
							videoNo: _videoNo
						},
						success(res) {
							coursetable.ajax.reload(null, false);
							$('#videoModal').modal('hide');
						},
						complete() {
							workStatus = 'none';
						}
					});
				}
			});

			$('#uploadVideo').click(function () {
				if (workStatus == 'none') {
					if (_todo == 'insert') {
						let form = new FormData();
						form.append('video', $('#videoFile')[0].files[0]);
						form.append('action', 'insert');
						form.append('timetableNo', _timetableNo);
						$.ajax({
							beforeSend() {
								workStatus = 'insert';
							},
							type: 'POST',
							url: `<%=request.getContextPath()%>/video/video.ajax`,
							processData: false,
							contentType: false,
							data: form,
							success(res) {
								if (res == 'ok') {
									coursetable.ajax.reload(null, false);
									$('#videoModal').modal('hide');		
								}else{
									alert(res);
								}
							},
							complete() {
								workStatus = 'none';
							}
						});
					}
					if (_todo == 'update') {
						let form = new FormData();
						form.append('video', $('#videoFile')[0].files[0]);
						form.append('action', 'update');
						form.append('videoNo', _videoNo);
						$.ajax({
							beforeSend() {
								workStatus = 'update';
							},
							type: 'POST',
							url: `<%=request.getContextPath()%>/video/video.ajax`,
							processData: false,
							contentType: false,
							data: form,
							success(res) {
									if (res == 'ok') {
									coursetable.ajax.reload(null, false);
									$('#videoModal').modal('hide');		
								}else{
									alert(res);
								}
							},
							complete() {
								workStatus = 'none';
							}
						});
					}
				}
			});

			$('#cancelUpload').click(function () {
				$('#videoModal').modal('hide');
			});

			$("#videoModal").on("hidden.bs.modal", function (e) {
				resetViewer();
				_timetableNo = '';
				_videoNo = '';
				_todo = '';
			});

		});
	</script>
</body>

</html>