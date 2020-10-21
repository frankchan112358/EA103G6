<%@page import="com.courseask.model.*"%>
<%@page import="com.course.model.*"%>
<%@page import="com.student.model.*"%>
<%@page import="com.teacher.model.*"%>
<%@page import="com.reply.model.*"%>
<%@page import="java.util.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="courseSvc" scope="page"
	class="com.course.model.CourseService" />
<jsp:useBean id="studentSvc" scope="page"
	class="com.student.model.StudentService" />
<jsp:useBean id="teacherSvc" scope="page"
	class="com.teacher.model.TeacherService" />
<jsp:useBean id="replySvc" scope="page"
	class="com.reply.model.ReplyService" />

<%
	CourseAskService courseAskSvc = new CourseAskService();
	List<CourseAskVO> list = courseAskSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<%
	ReplyVO replyVO = (ReplyVO) request.getAttribute("replyVO");
	CourseAskVO courseAskVO = (CourseAskVO) request.getAttribute("courseAskVO");
%>


<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
<link rel="stylesheet" media="screen, print"
	href="<%=request.getContextPath()%>/SmartAdmin4/css/formplugins/summernote/summernote.css">
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
						<li class="breadcrumb-item"><a
							href="<%=request.getContextPath()%>/front-end/index/index.jpg">前台首頁</a></li>
						<li class="breadcrumb-item"><a href="javascript:void(0);">課程提問</a></li>
						<li class="breadcrumb-item">課程提問總覽</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-clipboard-check'></i> 課程提問總覽
						</h1>
					</div>
					<div class="row">
						<div class="col">
							<div id="panel-1" class="panel">

								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">課程提問總表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<div class="form-group">
											<!-- datatable start -->
											<table id="tableEvaluation"
												class="table table-bordered table-hover table-striped w-100">

												<tr>
													<th>課程</th>
													<th>學員</th>
													<th>標題</th>
													<th>問題</th>
													<th>時間</th>
													<th>回覆</th>
												</tr>
												<c:forEach var="courseAskVO" items="${list }">
													<tr>
														<td>${courseSvc.getOneCourse(courseAskVO.getCourseNo()).courseName }</td>
														<td>${studentSvc.getOneStudent(courseAskVO.getStudentNo()).studentName}</td>
														<td>${courseAskVO.title }</td>
														<td>${courseAskVO.question }</td>
														<td><fmt:formatDate value="${courseAskVO.updateTime}"
																pattern="yyyy-MM-dd HH:mm:ss" /></td>
														<td>
															<form name="myForm" class="needs-validation" novalidate
																method="POST"
																action="<%=request.getContextPath()%>/courseAsk/courseAsk.do">
																<button type="button" id="btn-add"
																	class="btn-write btn btn-sm btn-primary"
																	data-toggle="modal" data-target="#editorEvaluation">
																	<strong>回覆</strong>
																</button>
																<input type="hidden" name="replyNo"
																	value="${replyVO.replyNo}"> <input
																	type="hidden" name="studentNo"
																	value="${studentVO.studentNo}" /> <input type="hidden"
																	name="courseAskNo" value="${courseAskVO.courseAskNo}" />
																<input type="hidden" name="teacherNo"
																	value="${teacherVO.teacherNo}" /> <input type="submit"
																	style="display: none;">
															</form>
														</td>
													</tr>
												</c:forEach>

											</table>
										</div>
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
			<main id="js-page-content" role="main" class="page-content">
				<div class="modal fade" id="editorEvaluation" tabindex="-1"
					role="dialog" aria-hidden="true">
					<div class="modal-dialog modal-lg modal-dialog-centered"
						role="document">
						<div class="modal-content">
							<c:forEach var="courseAskVO" items="${list }">
								<div class="modal-body">
									<div id="panel-2" class="panel">
										<div class="panel-container show">
											<div class="panel-content">
												<FORM METHOD="post"
													ACTION="<%=request.getContextPath()%>/reply/reply.do"
													name="form1">
													<textarea class="js-summernote" id="democratNote"name="replyContent"></textarea>
													<button id="sendNote" type="submit"class="mb-3 mt-3 btn btn-info waves-effect waves-themed float-left">送出</button>
													<input type="hidden" name="studentNo"
														value="${studentVO.studentNo}" /> <input type="hidden"
														name="courseNo" value="${courseVO.courseNo}" /> <input
														type="hidden" name="courseAskNo"
														value="${courseAskVO.courseAskNo}" /> <input type="hidden"
														name="teacherNo" value="${teacherVO.teacherNo}" /> <input
														type="hidden" name="action" value="insert2">
												</FORM>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>
	<%@ include file="/back-end/template/quick_menu.jsp"%>
	<%@ include file="/back-end/template/messager.jsp"%>
	<%@ include file="/back-end/template/basic_js.jsp"%>
	<script
		src="<%=request.getContextPath()%>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
	<script>
        'use strict';
        $(document).ready(function () {
            $('#democratTable').dataTable({
                responsive: true,
                language: { url: `<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json` }
            });

            $('#democratNote').summernote({
            	height: 300,
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
                            url: '<%=request.getContextPath()%>/Summernote',
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
                $.ajax({
                    url: '<%=request.getContextPath()%>/Summernote',
                    type: 'post',
                    processData: false,
                    contentType: false,
                    data: form,
                    success(res) {
                        console.log(res);
                    }
                });
            });
        });
    </script>
</body>
</html>