<%@page import="com.course.model.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/front-end/template/head.jsp"%>

<style>
.card-title {
	text-align: center;
	font-size: 18px;
	margin: 0;
	color: #617A28;
}

.card-img-top {
	object-fit: cover;
}

@media ( min-width : 576px) {
	.card-columns {
		column-count: 2;
	}
	.card-img-top {
		height: 19vw;
	}
}

@media ( min-width : 768px) {
	.card-columns {
		column-count: 3;
	}
	.card-img-top {
		height: 16vw;
	}
}

@media ( min-width : 992px) {
	.card-columns {
		column-count: 3;
	}
	.card-img-top {
		height: 14vw;
	}
}

@media ( min-width : 1200px) {
	.card-columns {
		column-count: 4;
	}
	.card-img-top {
		height: 11vw;
	}
}

.img {
	overflow: hidden;
}

.img img {
	transform: scale(1, 1);
	transition: all 0.8s ease-out;
}

.fa-search {
	position: absolute;
	right: 0;
	top: 12px;
	display: block;
	padding: 0 7px;
	color: #a4a3a4;
}

input[type=search]::-webkit-search-cancel-button {
	-webkit-appearance: none;
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
						<li class="breadcrumb-item">我的課程</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-book'></i>
							我的課程
						</h1>
						<div class="row">
							<div class="col-12">
								<div class="input-group mb-g">
									<input type="search" id="search" placeholder="輸入課程關鍵字" name="search" class="searchbox-input form-control form-control-lg" required>
									<div class="input-group-append">
										<div class="input-group-text" style="width: 40px;">
											<i class="fal fa-search fa-2x"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row  align-items-center justify-content-center">
						<div class="col-12">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">我的課程總覽</h2>
								</div>
								<div class="container-fluid ">
									<div class="card-columns">
										<div class="row justify-content-center">
											<div class="col">
												<c:forEach var="courseVO" items="${studentVO.courseList}">
													<div class="card border my-3 course" style="cursor: pointer;" courseNo="${courseVO.courseNo}">
														<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
														<c:if test="${courseVO.courseImg eq null}">
															<div class="img">
																<img src="<%=request.getContextPath()%>/images/尚無圖片.jpg" class="card-img-top img-fluid" alt="課程封面圖">
															</div>
														</c:if>

														<c:if test="${courseVO.courseImg ne null}">
															<div class="img">
																<img src="<%=request.getContextPath() %>/course/course.do?action=getCourseImg&courseNo=${courseVO.courseNo}" class="card-img-top img-fluid" alt="課程封面圖">
															</div>
														</c:if>
														<div class="card-body">
															<h5 class="card-title">
																【${courseVO.banjiVO.banjiName}】
																<i class="fal fa-hand-point-right"></i>
																${courseVO.courseName}
															</h5>
														</div>
														<div class="card-footer">
															<i class="fal fa-smile"></i>
															${courseVO.teacherVO.teacherName}
														</div>
													</div>
												</c:forEach>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</main>
			</div>
		</div>
	</div>

	<div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
	<%@ include file="/front-end/template/footer.jsp"%>
	<%@ include file="/front-end/template/quick_menu.jsp"%>
	<%@ include file="/front-end/template/messager.jsp"%>
	<%@ include file="/front-end/template/basic_js.jsp"%>
	<script>
	
		$(document).ready(function () {
			$('.searchbox-input').on("keyup", function () {
				var value = $(this).val().toLowerCase();
				$(".card").filter(function () {
					$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
				});
			});
			$(document).on('click', 'div.course[courseNo]', function (e) {
				let _courseNo = this.getAttribute('courseNo');
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/course.student';
				myForm.append(selectCourseInput('hidden', 'action', 'studentSelectCourseAndForwardToCoursePost'));
				myForm.append(selectCourseInput('hidden', 'courseNo', _courseNo));
				myForm.method = 'POST';
				myForm.submit();
			});
			function selectCourseInput(type, name, value) {
				let courseInput = document.createElement('input');
				courseInput.type = type;
				courseInput.name = name;
				courseInput.value = value;
				return courseInput;
			}
		});
	</script>
</body>

</html>