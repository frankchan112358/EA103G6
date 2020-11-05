<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ page import="com.course.model.*, com.teachingfile.model.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="finalScoreSvc" scope="page" class="com.finalscore.model.FinalScoreService" />
<%



CourseService courseSvc = new CourseService();
List<CourseVO> courseList = courseSvc.getAll();

pageContext.setAttribute("courseList", courseList);
session.setAttribute("courseWork", "finalScore");
%>

<!DOCTYPE html>
<html>

<head>
	<%@ include file="/front-end/template/head.jsp" %>
	<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">

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
						<li class="breadcrumb-item">課程成績</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class="subheader-icon fal fa-clipboard-list-check"></i>
								課程成績
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-11">
							<jsp:include page="/front-end/course/courseDetail.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>成績列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<!-- datatable start -->
										<table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead style="frontground-color:deeppink;">

												<tr>
													<th>課程名稱</th>
													<th>講師名稱</th>
													<th>成績</th>
													<th>(不)及格</th>
												</tr>
											</thead>
											<tbody>
											
											<c:forEach var="courseVO" items="${studentVO.banjiVO.courseList}">
												<tr>
													<td>${courseVO.courseName}</td>
													<td>${courseVO.teacherVO.teacherName}</td>
													<td>
													<c:if test="${finalScoreSvc.getScore(courseVO.courseNo, studentVO.studentNo)!=null}">
													${finalScoreSvc.getScore(courseVO.courseNo, studentVO.studentNo)}</c:if>
													<c:if test="${finalScoreSvc.getScore(courseVO.courseNo, studentVO.studentNo)==null}">
													尚未評分</c:if>
													</td>
													
													<td>
													<c:if test="${finalScoreSvc.getScore(courseVO.courseNo, studentVO.studentNo) > 60}">
													及格</c:if>
												<c:if test="${ finalScoreSvc.getScore(courseVO.courseNo, studentVO.studentNo) < 60}">
 													不及格</c:if> 
  													<c:if test="${finalScoreSvc.getScore(courseVO.courseNo, studentVO.studentNo) == null}">
  													尚未評分
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
				<%@ include file="/front-end/template/footer.jsp" %>
			</div>
		</div>
	</div>


	<%@ include file="/front-end/template/quick_menu.jsp" %>
	<%@ include file="/front-end/template/messager.jsp" %>
	<%@ include file="/front-end/template/basic_js.jsp" %>


	<script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
	<script>


		$(document).ready(function () {
			$('#coursetable').dataTable(
				{
					responsive: true,
					language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' },
					"columnDefs": [{
						"targets": [-3],
						"orderable": false,

					}]
				});
		});
	</script>
</body>

</html>