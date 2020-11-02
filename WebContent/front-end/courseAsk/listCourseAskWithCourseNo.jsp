<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.courseask.model.*"%>
<%@page import="com.student.model.*"%>
<%@page import="com.course.model.*"%>
<%@page import="com.teacher.model.*"%>
<%@page import="com.reply.model.*"%>
<%@page import="java.util.*"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="studentSvc" scope="page" class="com.student.model.StudentService" />
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService" />
<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />
<jsp:useBean id="replySvc" scope="page" class="com.reply.model.ReplyService" />
<%
%>
<!DOCTYPE html>
<html>

<head>
    <%@ include file="/front-end/template/head.jsp"%>

<link rel="stylesheet" media="screen, print"
      href="<%=request.getContextPath()%>/SmartAdmin4/css/formplugins/summernote/summernote.css">
<style>
#num{
font-size:30px;
color:#336666;
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
            <%@ include file="/front-end/template/left_aside.jsp"%>
            <div class="page-content-wrapper">
                <%@ include file="/front-end/template/header.jsp"%>
                <main id="js-page-content" role="main" class="page-content">
                    <ol class="breadcrumb page-breadcrumb">
                        <li class="breadcrumb-item"><a
                               href="<%=request.getContextPath()%>/front-end/index/index.jsp">前台首頁</a></li>
                        <li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/front-end/course/selectCourse.jsp">我的課程</a>
						</li>
						      <li class="breadcrumb-item">問題討論</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-books'></i>
                            課程提問
                        </h1>
                    </div>
                    <div class="row align-items-center justify-content-center">
                        <div class="col-11">
                            <jsp:include page="/front-end/course/courseDetail.jsp"></jsp:include>
                            <div id="panel-2" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">課程提問總覽</h2>
                                </div>
                                <div class="panel-container show" style="margin: 20px 20px;">
                                    <div>
                                        <button type="button" id="btn-add"
                                                class="btn-write btn btn-sm btn-primary" data-toggle="modal"
                                                data-target="#editorEvaluation">
                                            <strong>我要問問題</strong>
                                        </button>
                                    </div><br>
                                    <div style="text-align: center;">
                                        <c:if test="${empty courseAskList}">
                                            <h2>
                                                <i class="fal fa-calendar-times"></i>
                                                目前尚無任何課程提問
                                                <i class="fal fa-calendar-times"></i>
                                            </h2>
                                        </c:if>
                                    </div>
                                    <div class="accordion accordion-outline" id="courseAsk">
                                        <c:forEach var="courseAskVO" items="${courseAskList}">
                                            <div class="card">
                                                <div class="card-header">
                                                    <a href="javascript:void(0);" class="card-title collapsed" data-toggle="collapse" data-target="#courseAsk${courseAskVO.courseAskNo}" aria-expanded="false">
                                                        <i class="fal fa-books mr-3 fa-2x"></i>
                                                        <span>
                                                            ${courseAskVO.title }
                                                            <br><span>發問者:</span>${studentSvc.getOneStudent(courseAskVO.getStudentNo()).studentName}
                                                        </span>
                                                    </a>
                                                </div>
                                            </div>
                                            <div id="courseAsk${courseAskVO.courseAskNo}" class="collapse" data-parent="#courseAsk">
                                                <div class="card-body">
                                                    <p class="card-text" style="padding-left: 30px">${courseAskVO.question}</p>
                                                    <c:set var="replySize" value="${fn:length(replySvc.getAllWithCouseAskNo(courseAskVO.courseAskNo))}"></c:set>
                                                    <div id="num" style="float:right;padding-right: 30px;text-align:center;">${replySize}<div style="font-size:15px;">回覆</div>
                                                    </div>
                                                    <div align="center">
                                                        <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/courseAsk/courseAsk.do" class="m-1">
                                                            <input type="hidden" name="replyNo" value="${replyVO.replyNo}">
                                                            <input type="hidden" name="studentNo" value="${studentVO.studentNo}" />
                                                            <input type="hidden" name="courseAskNo" value="${courseAskVO.courseAskNo}" />
                                                            <input type="hidden" name="teacherNo" value="${teacherVO.teacherNo}" />
                                                            <input type="hidden" name="action" value="insert1">
                                                            <button type="submit" class="btn-write btn btn-sm btn-primary">
                                                                <strong>我要回覆</strong>
                                                            </button>
                                                        </FORM>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="d-flex align-items-center">
                                                <span class="text-sm text-muted font-italic" style="padding-left: 30px ;padding-bottom: 10px;"><i class="fal fa-clock mr-1"></i><span>發問時間:</span>
                                                    <fmt:formatDate value="${courseAskVO.updateTime}" pattern="yyyy-MM-dd HH:mm" /></span>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </div>
    <main id="js-page-content" role="main" class="page-content">
        <div class="modal fade" id="editorEvaluation" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">新增問題</h4>
                    </div>
                    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/courseAsk/courseAsk.do" name="form1" class="needs-validation" novalidate>
                       <div class="col mb-2">
                        <input id="add" placeholder="輸入您的問題" name="title" style="color: black; width: 95%; height: 50px; margin: 20px ;"
                               value="${courseAskVO.title }" required>
                        <div class="invalid-feedback" style=" margin-left: 20px ;">
                            請勿空白.
                        </div>
                        </div>
                        <div class="modal-footer">
                            <div id="panel-2" class="panel">
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <textarea class="js-summernote" id="democratNote" name="question" required></textarea>
                                        <div class="invalid-feedback">
                                            請勿空白.
                                        </div>
                                        <button id="sendNote" type="submit" class="mb-3 mt-3 btn btn-info waves-effect waves-themed float-left">送出</button>
                                        <input type="hidden" name="studentNo" value="${studentVO.studentNo}" />
                                        <input type="hidden" name="action" value="insert">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </FORM>
                </div>
            </div>
        </div>
    </main>
    <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
    <%@ include file="/front-end/template/footer.jsp"%>
    <%@ include file="/front-end/template/quick_menu.jsp"%>
    <%@ include file="/front-end/template/messager.jsp"%>
    <%@ include file="/front-end/template/basic_js.jsp"%>
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
    <script>
        // Example starter JavaScript for disabling form submissions if there are invalid fields
        (function () {
            'use strict';
            window.addEventListener('load', function () {
                // Fetch all the forms we want to apply custom Bootstrap validation styles to
                var forms = document.getElementsByClassName('needs-validation');
                // Loop over them and prevent submission
                var validation = Array.prototype.filter.call(forms, function (form) {
                    form.addEventListener('submit', function (event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();
    </script>

    <script>

        $(document).ready(function () {
            $('#tableEvaluation').dataTable({
                responsive: true,
                language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' }
            });

            $('#democratNote').summernote();

            $('#democratNote').summernote({
                height: 250,
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
                            url: '<%=request.getContextPath() %>/Summernote',
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
                    url: '<%=request.getContextPath() %>/Summernote',
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