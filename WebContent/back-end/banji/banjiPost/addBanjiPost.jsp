<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banjipost.model.*"%>
<%@ page import="com.banji.model.*"%>
<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />
<%
%>


<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
<style>
	#add {
		text-align: center;
	}
</style>
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
						<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a></li>
						<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.manage">養成班管理</a></li>
						<li class="breadcrumb-item"><a id="gotoReadBanji" href="javascript:void(0)">${banjiSvc.getOneBanji(banjiNo).banjiName}</a></li>
						<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.banjipost">公告管理</a></li>
						<li class="breadcrumb-item">新增班級公告</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-plus-square'></i> 新增班級公告
						</h1>
					</div>
					<div class="row">
						<div class="col col-xl-12">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">總覽</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<form METHOD="post" ACTION="<%=request.getContextPath()%>/banji/banji.banjipost" name="form1" class="needs-validation" novalidate>
											<div class="form-group">
												<label class="form-label" for="example-textarea">公告標題:</label>
												<input type="text" id="simpleinput" name="title" class="form-control" required placeholder="請輸入標題">
												<div class="invalid-feedback">
													公告標題請勿空白.
												</div>
											</div>
											<div class="form-group">
												<label class="form-label" for="example-textarea">班級公告內容:</label>
												<textarea name="banjiPostContent" class="form-control" required
																	id="example-textarea" rows="5" placeholder="請輸入內容"></textarea>
												<div class="invalid-feedback">
													班級公告內容請勿空白.
												</div>
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
												<button type="submit" class="btn btn-primary justify-content-center">送出</button>
											</div>
										</form>
									</div>
								</div>
							</div>
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

	<script>
		$(document).ready(function () {
			var forms = document.getElementsByClassName('needs-validation');
			var validation = Array.prototype.filter.call(forms, function (form) {
				form.addEventListener('submit', function (event) {
					if (form.checkValidity() === false) {
						event.preventDefault();
						event.stopPropagation();
					}
					form.classList.add('was-validated');
				}, false);
			});

			$('#gotoReadBanji').click(function () {
				let _this = this;
				let myForm = banjiGotoFrom('<%=request.getContextPath()%>/banji/banji.manage');
				document.body.appendChild(myForm);
				myForm.append(banjiGotoInput('hidden', 'action', 'read'));
				myForm.submit();
			});

			function banjiGotoFrom(url) {
				let myForm = document.createElement('form');
				myForm.action = url;
				myForm.method = 'POST';
				return myForm;
			}

			function banjiGotoInput(type, name, value) {
				let banjiInput = document.createElement('input');
				banjiInput.type = type;
				banjiInput.name = name;
				banjiInput.value = value;
				return banjiInput;
			}

		});
	</script>
</body>

</html>