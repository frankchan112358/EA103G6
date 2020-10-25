<%@page import="com.course.model.*"%>

<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />
<jsp:useBean id="coursePostSvc" scope="page" class="com.coursepost.model.CoursePostService" />
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService" />




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

.card:hover {
	box-shadow: 0 5px 10px rgba(0, 0, 0, .3);
	transition: box-shadow 0.3s ease-in-out;
}

.img{
overflow:hidden;
}

.img img {
	transform: scale(1, 1);
	transition: all 0.8s ease-out;
}

.img img:hover {
	transform: scale(1.2, 1.2);
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
					</div>
					<div class="card">
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
														<div class="card border my-3"
															onclick="location.href='<%=request.getContextPath()%>/coursePost/coursePost.do?courseNo=${courseVO.courseNo}&action=listCoursePost_ByCourseNo';">
															<c:if test="${courseVO.courseImg eq null}">
																<div class="img">
																	<img src="<%=request.getContextPath()%>/images/尚無圖片.jpg" class="card-img-top img-fluid" alt="課程封面圖" style="cursor: pointer;">
																</div>
															</c:if>

															<c:if test="${courseVO.courseImg ne null}">
																<div class="img">
																	<img src="<%=request.getContextPath() %>/course/course.do?action=getCourseImg&courseNo=${courseVO.courseNo}" class="card-img-top img-fluid" alt="課程封面圖"
																		style="cursor: pointer;">
																</div>
															</c:if>
															<div class="card-body"
																onclick="location.href='<%=request.getContextPath()%>/coursePost/coursePost.do?courseNo=${courseVO.courseNo}&action=listCoursePost_ByCourseNo';"
																style="cursor: pointer;">
																<h5 class="card-title">
																	【${banjiSvc.getOneBanji(courseVO.banjiNo).banjiName}】
																	<i class="fal fa-hand-point-right"></i>
																	${courseVO.courseName}
																</h5>
															</div>
															<div class="card-footer" style="cursor: pointer;"
																onclick="location.href='<%=request.getContextPath()%>/coursePost/coursePost.do?courseNo=${courseVO.courseNo}&action=listCoursePost_ByCourseNo';">
																<i class="fal fa-smile"></i>
																${teacherSvc.getOneTeacher(courseVO.teacherNo).teacherName}
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
		
	</script>
</body>
</html>