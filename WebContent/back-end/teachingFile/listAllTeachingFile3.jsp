<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.course.model.*, com.teachingfile.model.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="teachingFileSvc" scope="page" class="com.teachingfile.model.TeachingFileService" />

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

	#fileUpload {
		box-sizing: border-box;
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
							教材管理
						</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class="subheader-icon fal fa-file-code"></i>
							教材管理
						</h1>
					</div>
					<div class="row">
						<div class="col-xl-12">
							<jsp:include page="/back-end/course/courseNav.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>教材列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<button ID="insert" type="button" class="btn btn-primary waves-effect waves-themed float-left">
											<span class="far fa-plus-circle mr-1"></span>
											<span>新增</span>
										</button>
										<!-- datatable start -->
										<table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color:#E5F4FF;">
												<tr>
													<th width="30%">檔案名稱</th>
													<th>操作</th>
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
	<div id="teachingFileModal" class="modal fade example-modal-centered-transparent" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-xl modal-dialog modal-dialog-centered modal-transparent" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title text-white"></h4>
					<button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"><i class="fal fa-times"></i></span>
					</button>
				</div>
				<div class="modal-body">
					<table class="teachingFileInfo table table-bordered text-white">
						<thead>
							<tr>
								<th>檔案名稱</th>
								<th>檔案類型</th>
								<th>檔案大小</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<input id="teachingFileName" type="text" size="20" />
								</td>
								<td class="teachingFileInfo filetype"></td>
								<td class="teachingFileInfo filesize"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<input id="teachingFile" type="file" accept=".pdf" />
					<button id="cancelUpload" type="button" class="btn btn-secondary">取消上傳</button>
					<button id="uploadFile" type="button" class="btn btn-primary">上傳檔案</button>
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
			//建立變數們
			var workStatus = 'none';
			var _teachingFileNo = '';
			var _todo = '';
			var _inputFileName = '';
			var _courseNo = `${'courseNo'}`;

			//隱藏modal的table
			$('table.teachingFileInfo').hide();

			//table欄位
			var columnSet = [
				{
					title: "檔案名稱",
					id: "teachingFileName",
					data: "teachingFileName",
					type: "text"
				},
				{
					title: "操作",
					id: "teachingFileNo",
					data: "teachingFileNo",
					class: "justify-content-center d-flex",
					render(data, type, row, meta) {
						let html = `
						<form  class="m-1 mb-0" method="post" action="<%=request.getContextPath()%>/teachingFile/download.do">
							<button id="addCourse" type="submit" class="btn btn-success">
								<span class=" fal fa-file-code mr-1"></span>
								<span>預覽</span>
							</button>
							<input type="hidden" name="courseNo" value="${courseNo}">
							<input type="hidden" name="timetableNo" value="${'${row.teachingFileNo}'}">
							<input type="hidden" name="teachingFileNo" value="${'${row.teachingFileNo}'}">
							<input type="hidden" name="action" value="preRead">
						</form>
									<a href="<%=request.getContextPath()%>/teachingFile/download.do?${'${row.teachingFileNo}'}" download>
									<button teachingFileNo="${'${row.teachingFileNo}'}" type="button" class="download btn btn-info m-1 mb-0">
										<span class="fal fa-file-download mr-1"></span>
										<span>下載</span>
									</button>
									<input type="hidden" name="teachingFileNo" value="${'${row.teachingFileNo}'}">
									</a>
									<button teachingFileNo="${'${row.teachingFileNo}'}" type="button" class="delete btn btn-danger m-1 mb-0">
										<span class="fal fa-times mr-1"></span>
										<span>刪除</span>
									</button>`;
						return html;
					}
				}];

			var coursetable = $('#coursetable').DataTable(
				{
					responsive: true,
					language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' },
					"columnDefs": [{
						"targets": [-1, -2],
						"orderable": false,
					}],
					columns: columnSet,
					ajax: {
						url: '<%=request.getContextPath()%>/teachingFile/teachingFileAjax',
						type: 'POST',
						async: true,
						cache: false,
						data: {
							action: 'datatable',
							courseNo: '${courseNo}'
						}
					}
				});

			//about新增檔案的按鈕
			$(document).on('click', '#insert', function (e) {
				_todo = 'insert';
				$('h4.modal-title').text('新增檔案(僅支援PDF格式)');
				$('#teachingFileModal').modal('show');
			});

			//關閉modal
			$('#teachingFileModal').on('hidden.bs.modal', function (e) {
				$('table.teachingFileInfo').hide();
				$('#teachingFile').val('');
				$('#teachingFileName').val('');
				$('.teachingFileInfo.filetype').text('');
				$('.teachingFileInfo.filesize').text('');
			})

			//選擇檔案、更新table
			$('#teachingFile').on('change', function (e) {
				let files = e.target.files;
				$('#teachingFileName').val(files[0].name);
				$('.teachingFileInfo.filetype').text(files[0].type);
				$('.teachingFileInfo.filesize').text(Math.round(files[0].size / 1024) + 'KB');
				$('table.teachingFileInfo').show();
			})

			//about上傳的按鈕
			$('#uploadFile').click(function (e) {
				if ($('#teachingFile').val() == '') {
					Swal.fire({
							position: "top-end",
							type: "danger",
							title: "請選擇上傳檔案",
							showConfirmButton: false,
							timer: 1000
						});
					return;
				}
				if ($('#teachingFileName').val() == '') {
					Swal.fire({
							position: "top-end",
							type: "danger",
							title: "請填寫檔案名稱",
							showConfirmButton: false,
							timer: 1000
						});
					return;
				}
				if (workStatus == 'none') {
					let form = new FormData();
					form.append('teachingFile', $('#teachingFile')[0].files[0]);
					form.append('action', 'insert');
					form.append('teachingFileName', $('#teachingFileName').val());
					$.ajax({
						beforeSend: function () {
							workStatus = 'upload';
						},
						type: 'POST',
						url: `<%=request.getContextPath() %>/teachingFile/teachingFileAjax`,
						processData: false,
						contentType: false,
						data: form,
						success: function (res) {
							if (res == 'ok') {
								coursetable.ajax.reload(null, false);
								$('#teachingFileModal').modal('hide');
							} else {
								alert(res);
							}
						},
						complete: function () {
							workStatus = 'none';
							_todo = '';
						}
					})
				}
			});

			//about取消的按鈕
			$('#cancelUpload').click(function () {
				$('#teachingFileModal').modal('hide');
			});

			//about預覽的按鈕
			$(document).on('click', 'button.preview', function (e) {
				_teachingFileNo = this.getAttribute('teachingFileNo');
				console.log("_teachingFileNo:" + _teachingFileNo);
				if (workStatus == 'none') {
					$.ajax({
						beforeSend() {
							workStatus = 'preview';
						},
						type: 'POST',
						url: `<%=request.getContextPath()%>/teachingFile/download.do`,
						data: {
							action: 'preRead',
							teachingFileNo: _teachingFileNo
						},
						complete() {
							workStatus = 'none';
							_todo = '';
						}
					})
				}
			});

			//about下載的按鈕(在上面a標籤裡面)

			//about刪除的按鈕
			$(document).on('click', 'button.delete', function (e) {
				_teachingFileNo = this.getAttribute('teachingFileNo');
				if (workStatus == 'none') {
					$.ajax({
						beforeSend() {
							workStatus = 'delete';
						},
						type: 'POST',
						url: `<%=request.getContextPath() %>/teachingFile/teachingFileAjax`,
						data: {
							action: 'delete',
							teachingFileNo: _teachingFileNo
						},
						success(res) {
							coursetable.ajax.reload(null, false);
							$('#videoModal').modal('hide');
						},
						complete() {
							workStatus = 'none';
							_todo = '';
						}
					});
				}
			});

			//nav-bar的部分
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
		});
	</script>
</body>

</html>