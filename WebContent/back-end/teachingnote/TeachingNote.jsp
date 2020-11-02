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
<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">
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
															<c:if test="${timetableVO.teachingLog!=null}">
															${timetableVO.teachingLog}
																<button timetableNo="${timetableVO.timetableNo}" type="button" id="btn-add" class="btn-write btn btn-success mr-2 edit" data-toggle="modal" data-target="#editorEvaluation">
																	<span class="fal fa-edit"></span>
																	<span>修改</span>
																</button>

																<FORM class="m-1 mb-0" METHOD="post" ACTION="<%=request.getContextPath()%>/timetable/timetable.do" style="margin-bottom:0px;">
																	<button timetableNo="${timetableVO.timetableNo}" type="submit" class="btn-write btn btn-danger mr-2">
																		<span class="fal fa-times "></span>
																		<span>刪除</span>
																	</button>
																	<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																	<input type="hidden" name="timetableNo" value="${timetableVO.timetableNo}">
																	<input type="hidden" name="action" value="delete_teachingLog">
																</FORM>
															</c:if>
															<c:if test="${timetableVO.teachingLog==null}">
																<button timetableNo="${timetableVO.timetableNo}" type="button" id="btn-add" class="btn-write btn btn-info mr-1 edit" data-toggle="modal" data-target="#editorEvaluation">
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

	<main id="js-page-content" role="main" class="page-content">
		<div class="modal fade" id="editorEvaluation" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
				<div class="modal-content">

					<div class="modal-header">
						<h5 class="modal-title">新增教學筆記</h5>
					</div>
						<div class="modal-footer">
							<div id="panel-2" class="panel">
								<div class="panel-container show">
									<div class="panel-content">
										<textarea class="js-summernote" id="democratNote" name="question" required></textarea>
										<div class="invalid-feedback">
											請勿空白.
										</div>
										<button id="sendNote" type="submit" class="mb-3 mt-3 btn btn-info waves-effect waves-themed float-left">送出</button>

									</div>
								</div>
							</div>
						</div>
				</div>
			</div>
		</div>
	</main>
	<%@ include file="/back-end/template/quick_menu.jsp" %>
	<%@ include file="/back-end/template/messager.jsp" %>
	<%@ include file="/back-end/template/basic_js.jsp" %>
	<script src="<%=request.getContextPath() %>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
	<script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
	<script>

			var _timetableNo = '';
		$(document).ready(
				
			function () {

			$(document).on('click', 'button.edit', function (e) {
				_timetableNo = this.getAttribute('timetableNo');
			})

			$('#tableEvaluation').dataTable({
				responsive: true,
				language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' }
			});

			$('#democratNote').summernote();

			$('#democratNote').summernote({
				height: 250,
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
					onInit: function (e) {
						$.ajax({
							url: '<%=request.getContextPath() %>/timetable/teachingNoteSummernote',
							type: 'get',
							success(res) {
								$('#democratNote').summernote('code', res);
							}
						});
					},
					onChange: function (contents, $editable) { }
				}
			});

			$('#sendNote').click(function () {
				let form = new FormData();
				form.append("democratNote", $('#democratNote').summernote('code'));
				form.append('timetableNo', _timetableNo);

				$.ajax({
					url: '<%=request.getContextPath() %>/timetable/teachingNoteSummernote',
					type: 'post',
					processData: false,
					contentType: false,
					data: form,
					success(res) {
						console.log(res);
						location.reload(); 
					}
				});
			});
		});

	</script>
</body>

</html>