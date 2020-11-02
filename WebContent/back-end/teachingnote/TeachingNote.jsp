<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.course.model.*,com.video.model.*,com.timetable.model.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />

<%
%>

<!DOCTYPE html>
<html>

<head>
	<%@ include file="/back-end/template/head.jsp" %>
<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/formplugins/summernote/summernote.css">

<style>
	.table th,
	.table td {
		vertical-align: middle;
		text-align: center;
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
							教學筆記管理
						</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class="subheader-icon far fa-video"></i>
							教學筆記管理
						</h1>
					</div>
					<div class="row">
						<div class="col-xl-12">
							<jsp:include page="/back-end/course/courseNav.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>教學筆記列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<!-- datatable start -->
										<table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color:#E5F4FF;">
												<tr>
													<th width="30%">上課日期</th>
													<th width="30%">時段</th>
													<th width="40%">教學筆記</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="timetableVO" items="${courseVO.timetableList}">
													<tr>
														<td>${timetableVO.timetableDate}</td>
														<td>${timetableVO.periodText}</td>
														<td class="d-flex p-1 justify-content-center">
															<c:if test="${timetableVO.teachingLog==null}">
																<button timetableNo="${timetableVO.timetableNo}" type="button" class="btn-write btn btn-success mr-2 update" style="display: none;">
																	<span class="fal fa-edit"></span>
																	<span>修改</span>
																</button>
																<button timetableNo="${timetableVO.timetableNo}" type="button" class="btn-write btn btn-danger mr-2 delete" style="display: none;">
																	<span class="fal fa-times"></span>
																	<span>刪除</span>
																</button>
																<button timetableNo="${timetableVO.timetableNo}" type="button" class="btn-write btn btn-info mr-2 insert">
																	<span class="fal fa-upload"></span>
																	<span>新增</span>
																</button>
															</c:if>
															<c:if test="${timetableVO.teachingLog!=null}">
																<button timetableNo="${timetableVO.timetableNo}" type="button" class="btn-write btn btn-success mr-2 update">
																	<span class="fal fa-edit"></span>
																	<span>修改</span>
																</button>
																<button timetableNo="${timetableVO.timetableNo}" type="button" class="btn-write btn btn-danger mr-2 delete">
																	<span class="fal fa-times"></span>
																	<span>刪除</span>
																</button>
																<button timetableNo="${timetableVO.timetableNo}" type="button" class="btn-write btn btn-info mr-2 insert" style="display: none;">
																	<span class="fal fa-upload"></span>
																	<span>新增</span>
																</button>
															</c:if>
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
	<div id="teachingNoteModal" class="modal fade example-modal-centered-transparent" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-xl modal-dialog modal-dialog-centered modal-transparent" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title text-white"></h4>
					<button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"><i class="fal fa-times"></i></span>
					</button>
				</div>
				<div class="modal-body">
					<textarea class="js-summernote" id="teachingNote"></textarea>
				</div>
				<div class="modal-footer">
					<button id="cancelSend" type="button" class="btn btn-secondary">取消</button>
					<button id="sendNote" type="button" class="btn btn-primary">送出</button>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/back-end/template/quick_menu.jsp" %>
	<%@ include file="/back-end/template/messager.jsp" %>
	<%@ include file="/back-end/template/basic_js.jsp" %>
	<script src="<%=request.getContextPath() %>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
	<script>
		$(document).ready(function () {
			var workStatus = 'none';
			var _timetableNo = '';
			var _todo = '';

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

			$('#tableEvaluation').dataTable({
				responsive: true,
				language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' }
			});

			$('#teachingNote').summernote({
				height: 300,
				tabsize: 2,
				placeholder: "請輸入",
				dialogsFade: true,
				toolbar: [
					['style', ['style']],
					['font', ['strikethrough', 'superscript', 'subscript']],
					['font', ['bold', 'italic', 'underline', 'clear']],
					['fontsize', ['fontsize']],
					['fontname', ['fontname']],
					['color', ['color']],
					['para', ['ul', 'ol', 'paragraph']],
					['height', ['height']]
					['table', ['table']],
					['insert', ['link', 'picture', 'video']],
					['view', ['fullscreen', 'codeview', 'help']]
				],
				callbacks: {
				}
			});

			$(document).on('click', 'button.insert', function (e) {
				_timetableNo = this.getAttribute('timetableNo');
				_todo = 'insert';
				$('h4.modal-title').text('新增筆記');
				$('#teachingNoteModal').modal('show');
			});

			$(document).on('click', 'button.update', function (e) {
				_timetableNo = this.getAttribute('timetableNo');
				_todo = 'update';
				$.ajax({
					url: '<%=request.getContextPath() %>/timetable/teachingNoteSummernote',
					type: 'post',
					data: {
						action: 'read',
						timetableNo: _timetableNo
					},
					success: function (res) {
						if (res != null) {
							$('#teachingNote').summernote('code', res);
						}
					},
					complete: function () {
						$('h4.modal-title').text('修改筆記');
						$('#teachingNoteModal').modal('show');
					}
				});
			});

			$(document).on('click', 'button.delete', function (e) {
				_timetableNo = this.getAttribute('timetableNo');
				$.ajax({
					url: '<%=request.getContextPath() %>/timetable/teachingNoteSummernote',
					type: 'post',
					data: {
						action: 'delete_teachingLog',
						timetableNo: _timetableNo
					},
					success: function (res) {
						if (res == 'ok') {
							switchToInsert();
						}
					},
					complete: function () {
					}
				});
			});

			$('#sendNote').click(function () {
				if (_todo == 'insert') {
					let form = new FormData();
					form.append("teachingNote", $('#teachingNote').summernote('code'));
					form.append('timetableNo', _timetableNo);
					form.append('action', 'insert');
					$.ajax({
						url: '<%=request.getContextPath() %>/timetable/teachingNoteSummernote',
						type: 'post',
						processData: false,
						contentType: false,
						data: form,
						success: function (res) {
							if (res == 'ok') {
								switchToUpdateDelete();
								$('#teachingNoteModal').modal('hide');
							}
						},
						complete: function () {
						}
					});
				}
				if (_todo == 'update') {
					let form = new FormData();
					form.append("teachingNote", $('#teachingNote').summernote('code'));
					form.append('timetableNo', _timetableNo);
					form.append('action', 'update');
					$.ajax({
						url: '<%=request.getContextPath() %>/timetable/teachingNoteSummernote',
						type: 'post',
						processData: false,
						contentType: false,
						data: form,
						success: function (res) {
							if (res == 'ok') {
								$('#teachingNoteModal').modal('hide');
							}
						},
						complete: function () {
						}
					});
				}
			});

			$('#cancelSend').click(function () {
				$('#teachingNoteModal').modal('hide');
			});

			$("#teachingNoteModal").on("hidden.bs.modal", function (e) {
				$('#teachingNote').summernote('code', '');
				_timetableNo = '';
				_todo = '';
			});

			function teachingNoteInput(type, name, value) {
				let courseInput = document.createElement('input');
				courseInput.type = type;
				courseInput.name = name;
				courseInput.value = value;
				return courseInput;
			}

			function switchToUpdateDelete() {
				$(`button.insert[timetableNo=${"${_timetableNo}"}]`).css('display', 'none');
				$(`button.update[timetableNo=${"${_timetableNo}"}]`).css('display', 'block');
				$(`button.delete[timetableNo=${"${_timetableNo}"}]`).css('display', 'block');
			}

			function switchToInsert() {
				$(`button.insert[timetableNo=${"${_timetableNo}"}]`).css('display', 'block');
				$(`button.update[timetableNo=${"${_timetableNo}"}]`).css('display', 'none');
				$(`button.delete[timetableNo=${"${_timetableNo}"}]`).css('display', 'none');
			}

		});

	</script>
</body>

</html>