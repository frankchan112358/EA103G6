<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.course.model.*"%>
<%@ page import="java.util.*"%>
<%

%>

<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService" />
<jsp:useBean id="classroomSvc" scope="page" class="com.classroom.model.ClassroomService" />

<!DOCTYPE html>
<html>

<head>
	<%@ include file="/back-end/template/head.jsp" %>
	<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">


	<style>
		th {
			font-size: 15px;
		}

		td {
			font-size: 15px;
		}


		.table th,
		.table td {
			vertical-align: middle;
			text-align: center;
		}

		img {
			vertical-align: sub;
			width: 20%;
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
						<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a></li>
						<li class="breadcrumb-item">課程總覽</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-book'></i>
							課程總覽
						</h1>
					</div>
					<div class="row">
						<div class="col-12">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>課程列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<div class="btn-group mb-3" role="group">
											<c:forEach var="banjiVO" items="${empVO.banjiList}">
												<button type="button" class="banji btn btn-lg ${banjiVO.banjiNo == banjiNo ? 'btn-primary' : 'btn-outline-primary'} " banjiNo="${banjiVO.banjiNo}">
													<span class="fal fa-book mr-1"></span>${banjiVO.banjiName}
												</button>
											</c:forEach>
										</div>
										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do">
											<button id="addCourse" type="submit" class="btn btn-success waves-effect waves-themed float-left">
												<span class="far fa-plus-circle mr-1"></span>
												<span>新增</span>
											</button>
											<input type="hidden" name="banjiNo" value="${banjiNo}">
											<input type="hidden" name="action" value="new">
										</FORM>
										<!-- datatable start -->
										<table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color:#E5F4FF;">
												<tr>
													<th>課程編號</th>
													<th>課程名稱</th>
													<th>班級</th>
													<th>講師</th>
													<th>教室</th>
													<th>堂數</th>
													<th>開始日期</th>
													<th>結束日期</th>
													<th>狀態</th>
													<th>課程圖</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="courseVO" items="${courseList}">
													<tr courseNo="${courseVO.courseNo}" class="courseManage" style="cursor: pointer;">
														<td>${courseVO.courseNo}</td>
														<td>${courseVO.courseName}</td>
														<td>${banjiSvc.getOneBanji(courseVO.banjiNo).banjiName}</td>
														<td>${teacherSvc.getOneTeacher(courseVO.teacherNo).teacherName}</td>
														<td>${classroomSvc.getOneClassroom(courseVO.classroomNo).classroomName}</td>
														<td>${courseVO.lesson}</td>
														<td>${courseVO.startDate}</td>
														<td>${courseVO.endDate}</td>
														<td>
															<c:choose>
																<c:when test="${courseVO.status=='0'}">課程未開始</c:when>
																<c:when test="${courseVO.status=='1'}">課程進行中</c:when>
																<c:when test="${courseVO.status=='2'}">課程結束</c:when>
															</c:choose>
														</td>
														<td>
															<c:if test="${courseVO.courseImg eq null}">
																<span> </span>
																<i class="fal fa-file fa-2x"></i>
															</c:if>
															<c:if test="${courseVO.courseImg ne null}">
																<span></span>
																<i class="fal fa-file-image fa-2x"></i>
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
				});

			$(document).on('click', 'button.banji', function (event) {
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
			});

			$(document).on('click','tr.courseManage',function (e) {
				e.preventDefault();
				let _this = this;
				let courseNo = this.getAttribute('courseNo');
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/course.do';
				myForm.method = 'POST';
				let courseNoInput = document.createElement('input');
				courseNoInput.type = 'hidden';
				courseNoInput.name = 'courseNo';
				courseNoInput.value = courseNo;
				myForm.append(courseNoInput);
				let actionInput = document.createElement('input');
				actionInput.type = 'hidden';
				actionInput.name = 'action';
				actionInput.value = 'getOne_For_Display';
				myForm.append(actionInput);
				myForm.submit();
			  });
		});
		
	    var swalWithBootstrapButtons = Swal.mixin({
			customClass : {
				confirmButton : "btn btn-primary",
				cancelButton : "btn btn-danger mr-2"
			},
			buttonsStyling : false
	    });
			<c:if test="${not empty alert.insertOK }">
			swalWithBootstrapButtons.fire({
				icon:'success',
				title:'新增成功',
				text:"課程已新增完成"
				});
			<%request.removeAttribute("insertOK");%>
			</c:if>
		
		

	</script>
</body>

</html>