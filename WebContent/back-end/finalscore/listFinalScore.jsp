<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.course.model.*,com.timetable.model.*, com.teachingfile.model.*"%>
<%@ page import="java.util.*"%>

<%
CourseService courseSvc = new CourseService();
List<CourseVO> courseList = courseSvc.getAll();

TimetableService timetableSvc =new TimetableService();
List<TimetableVO> timetableList = timetableSvc.getAll();

TeachingFileService teachingFileSvc =new TeachingFileService();
List<TeachingFileVO> teachingFileList = teachingFileSvc.getAll();

pageContext.setAttribute("courseList", courseList);
pageContext.setAttribute("timetableList", timetableList);
pageContext.setAttribute("teachingFileList", teachingFileList);

CourseVO choose_courseVO = (CourseVO) request.getAttribute("courseVO");
pageContext.setAttribute("choose_courseVO", choose_courseVO);
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
						<li class="breadcrumb-item">
							成績管理
						</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class="fas fa-star-half-alt" style="color: #374EFA;"></i>
							成績管理
						</h1>
					</div>
					<div class="row">
						<div class="col-xl-12">
						<jsp:include page="/back-end/course/courseNav.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>教材列表75</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
									<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/teachingFile/addTeachingFile.jsp">
                                        <input type="hidden" name="courseNo" value="${courseVO.courseNo}">
										</FORM>
										<!-- datatable start -->
										<table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color:#E5F4FF;">

												<tr>
													<th width="15%">課程編號</th>
													<th width="15%">教材編號</th>
													<th width="15%">教材名稱</th>
													<th width="10%">操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="courseVO" items="${courseList}">
													<c:if test="${courseVO.courseNo eq choose_courseVO.courseNo }">
														<c:forEach var="timetableVO" items="${timetableList}">
															<c:if test="${courseVO.courseNo eq timetableVO.courseNo}">
																<c:forEach var="teachingFileVO" items="${teachingFileList}">
																	<c:if test="${timetableVO.timetableNo eq teachingFileVO.timetableNo}">
																		<tr>
																			<td>${courseVO.courseNo}</td>
																			<td>${teachingFileVO.teachingFileNo}</td>
																			<td>${teachingFileVO.teachingFileName}</td>
																			<td>
																			
																				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/teachingFile/download.do">
																				<button id="addCourse" type="submit" class="btn btn-success">
																					<span class="far fa-plus-circle mr-1"></span>
																					<span>預覽</span>
																				</button>
																				<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																				<input type="hidden" name="timetableNo" value="${timetableVO.timetableNo}">
																				<input type="hidden" name="teachingFileNo" value="${teachingFileVO.teachingFileNo}">
																				<input type="hidden" name="action" value="preRead">
																			</FORM>
																			<br>
																				<button id="addCourse" type="submit" class="btn btn-primary">
																					<a href="<%=request.getContextPath()%>/teachingFile/download.do?${teachingFileVO.teachingFileNo}" download>
																					<span class="far fa-plus-circle mr-1" style="color:white">下載</span>
																			    	<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																			    	<input type="hidden" name="teachingFileNo" value="${teachingFileVO.teachingFileNo}">
																			    	</a>
																				</button>
																			<br>
																			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/teachingFile/teachingFile.do">
																				<br>
																				<button id="addCourse" type="submit" class="btn btn-danger">
																					<span class="far fa-plus-circle mr-1"></span>
																					<span>刪除</span>
																				</button>
																				<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																				<input type="hidden" name="timetableNo" value="${timetableVO.timetableNo}">
																				<input type="hidden" name="teachingFileNo" value="${teachingFileVO.teachingFileNo}">
																				<input type="hidden" name="action" value="delete">
																			</FORM>
																			</td>
																		</tr>
																	</c:if>
																</c:forEach>
															</c:if>
														</c:forEach>
													</c:if>
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


	<%@ include file="/back-end/template/quick_menu.jsp" %>
	<%@ include file="/back-end/template/messager.jsp" %>
	<%@ include file="/back-end/template/basic_js.jsp" %>


	<script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
	<script>


		$(document).ready(function () {
			$('#coursetable').dataTable(
				{
					responsive: true,
					language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' },
					"columnDefs": [{
						"targets": [-1, -2],
						"orderable": false,

					}]
				});
		});
	</script>
</body>

</html>