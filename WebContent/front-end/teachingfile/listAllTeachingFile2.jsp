<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ page import="com.course.model.*,com.timetable.model.*, com.teachingfile.model.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="teachingFileSvc" scope="page" class="com.teachingfile.model.TeachingFileService" />

<%
List<CourseVO> courseList = courseSvc.getAll();

List<TeachingFileVO> teachingFileList = teachingFileSvc.getAll();

pageContext.setAttribute("courseList", courseList);
pageContext.setAttribute("teachingFileList", teachingFileList);

session.setAttribute("courseWork", "teachingFile");
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
							<a href="<%=request.getContextPath()%>/back-end/index/index.jsp">前台首頁 ${courseNo}</a>
						</li>
						<li class="breadcrumb-item">
							<a id="aListAllCourse" banjiNo="${courseSvc.getOneCourse(courseNo).banjiNo}" href="javascript:void(0)">課程總覽</a>
						</li>
						<li class="breadcrumb-item">
							教材管理 ${courseSvc.getOneCourse(courseNo).banjiNo} 嗨嗨
						</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class="subheader-icon fal fa-file-code"></i>
							教材管理
						</h1>
					</div>
					<div class="row  align-items-center justify-content-center">
						<div class="col-11">
							<jsp:include page="/front-end/course/courseDetail.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>教材列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
									
										<!-- datatable start -->
										<table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color:#E5F4FF;">

												<tr>
													<th width="15%">課程編號</th>
													<th width="15%">教材編號</th>
													<th width="15%">教材名稱</th>
													<th width="10%">下載</th>
												</tr>
											</thead>
											<tbody>
														<c:forEach var="teachingFileVO" items="${teachingFileSvc.getAllWithCourseNo(courseNo)}">
															<c:if test="${courseNo == teachingFileVO.courseNo}">
																		<tr>
																			<td>${courseNo}</td>
																			<td>${teachingFileVO.teachingFileNo}</td>
																			<td>${teachingFileVO.teachingFileName}</td>
																			<td class="d-flex p-1 justify-content-center">
																			
																				<FORM  class="m-1 mb-0" METHOD="post" ACTION="<%=request.getContextPath()%>/teachingFile/download.do">
																				<button id="addCourse" type="submit" class="btn btn-success">
																					<span class=" fal fa-file-code mr-1"></span>
																					<span>預覽</span>
																				</button>
																				<input type="hidden" name="courseNo" value="${courseNo}">
																				<input type="hidden" name="timetableNo" value="${timetableVO.timetableNo}">
																				<input type="hidden" name="teachingFileNo" value="${teachingFileVO.teachingFileNo}">
																				<input type="hidden" name="action" value="preRead">
																			</FORM>

																					<a href="<%=request.getContextPath()%>/teachingFile/download.do?${teachingFileVO.teachingFileNo}" download>
																				<button id="addCourse" type="submit" class="m-1 mb-0 btn btn-primary">
																					<span class="fal fa-file-download mr-1"></span>
																					<span>下載</span>
																					
																			    	<input type="hidden" name="courseNo" value="${courseNo}">
																			    	<input type="hidden" name="teachingFileNo" value="${teachingFileVO.teachingFileNo}">
																				</button>
																			    	</a>

																			</td>
																		</tr>
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
						"targets": [-1, -2],
						"orderable": false,

					}]
				});

				document.getElementById('aListAllCourse').addEventListener('click',function(e){
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
				banjiNoInput.value= banjiNo;
				myForm.append(banjiNoInput);
				myForm.submit();
			}, false);

		});
	</script>
</body>

</html>