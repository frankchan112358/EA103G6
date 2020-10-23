<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ include file="/back-end/template/check.jsp"%> --%>
<%@ page import="com.course.model.*"%>
<%@ page import="java.util.*"%>


<%
	CourseService courseSvc = new CourseService();
	List<CourseVO> list = courseSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService" />
<jsp:useBean id="classroomSvc" scope="page" class="com.classroom.model.ClassroomService" />

<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">

<style>
.table th, .table td {
	vertical-align: middle;
	text-align: center;
}

#showphoto2 {
	text-align: center;
}

#showphoto2 img {
	width: 300px;
	margin: 20px;
	border: 2px #C4B1B1 dashed;
	text-align: center;
}

img {
	vertical-align: sub;
	width: 100%;
	height: auto;
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
							<a href="<%=request.getContextPath()%>/back-end/course/listAllCourse.jsp">課程總覽</a>
						</li>
						<li class="breadcrumb-item">課程資料</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon far fa-book'></i>
							課程資料
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-10">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>課程資料</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">

										<!-- datatable start -->
										<table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<tr>
												<td colspan="2">
													<div id="showphoto2">
														<c:if test="${courseVO.courseImg eq null}">
															<img src="<%=request.getContextPath()%>/images/尚無圖片.jpg">
														</c:if>
														<c:if test="${courseVO.courseImg ne null}">
															<img src="<%=request.getContextPath() %>/course/course.do?action=getCourseImg&courseNo=${courseVO.courseNo}">
														</c:if>
													</div>
												</td>
											</tr>
											<tr>
												<th width="20%">課程編號</th>
												<td>${courseVO.courseNo}</td>
											</tr>

											<tr>
												<th>課程名稱</th>
												<td>${courseVO.courseName}</td>
											</tr>
											<tr>
												<th>課程大綱</th>
												<td>${courseVO.courseOutline}</td>
											</tr>
											<tr>
												<th>班級</th>
												<td>${banjiSvc.getOneBanji(courseVO.banjiNo).banjiName}</td>
											</tr>
											<tr>

												<th>講師</th>
												<td>${teacherSvc.getOneTeacher(courseVO.teacherNo).teacherName}</td>
											</tr>
											<tr>
												<th>教室</th>
												<td>${classroomSvc.getOneClassroom(courseVO.classroomNo).classroomName}</td>
											</tr>
											<tr>
												<th>堂數</th>
												<td>${courseVO.lesson}</td>
											</tr>
											<tr>
												<th>開始日期</th>
												<td>${courseVO.startDate}</td>
											</tr>
											<tr>
												<th>結束日期</th>
												<td>${courseVO.endDate}</td>
											</tr>
											<tr>
												<th>狀態</th>
												<td>
													<c:choose>
														<c:when test="${courseVO.status=='0'}">課程未開始</c:when>
														<c:when test="${courseVO.status=='1'}">課程進行中</c:when>
														<c:when test="${courseVO.status=='2'}">課程結束</c:when>
													</c:choose>
												</td>
											</tr>

										</table>

										<div class="form-row align-items-center justify-content-center">
											<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do">
												<button type="submit" class="btn btn-primary">
													<span class="fal fa-edit mr-1"></span>
													<span>修改</span>
												</button>
												<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
												<input type="hidden" name="action" value="getOne_For_Update">
											</FORM>
											&emsp;
											<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do">
												<button type="submit" class="btn btn-danger">
													<span class="fal fa-times mr-1"></span>
													<span>刪除</span>
												</button>

												<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
												<input type="hidden" name="action" value="delete">
											</FORM>
											&emsp;
											<FORM METHOD="post" ACTION="">
												<button type="submit" class="btn btn-outline-success">
													<i class="fal fa-bug"></i>
													<span>管理</span>
												</button>

												<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
												<input type="hidden" name="action" value="delete">
											</FORM>
											&emsp;
											<FORM METHOD="post" ACTION="">
												<button type="submit" class="btn btn-outline-success">
													<span class="fal fa-bug"></span>
													<span>管理</span>
												</button>

												<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
												<input type="hidden" name="action" value="delete">
											</FORM>
										</div>




										<!-- datatable end -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</main>


				<div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
				<%@ include file="/back-end/template/footer.jsp"%>
			</div>
		</div>
	</div>


	<%@ include file="/back-end/template/quick_menu.jsp"%>
	<%@ include file="/back-end/template/messager.jsp"%>
	<%@ include file="/back-end/template/basic_js.jsp"%>




	<script>
		
	</script>
</body>
</html>