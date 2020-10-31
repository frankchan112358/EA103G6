<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.forumpost.model.*"%>
<%@ page import="java.util.*"%>
<%
%>
<jsp:useBean id="forumtopicSvc" scope="page" class="com.forumtopic.model.ForumTopicService" />

<!DOCTYPE html>
<html>

<head>
<%@ include file="/front-end/template/head.jsp"%>
<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/formplugins/summernote/summernote.css">
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
                               href="<%=request.getContextPath()%>/front-end/index/index.jpg">前台首頁</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/forumpost/forumPost_index.jsp">班級討論區</a></li>
                        <li class="breadcrumb-item">修改貼文</li>
                    </ol>
                    <div class="subheader">
                        <div class="row">
                            <div class="col col-xl-12">
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <section id="One" class="wrapper style3">
                                            <div class="inner">
                                                <header class="align-center">
                                                    <h2>修改貼文</h2>
                                                </header>
                                            </div>
                                        </section>
                                        <form method="post" action="<%=request.getContextPath() %>/forumPost/forumPost.do" name="form1" class="was-validated">
                                            <div class="form-group">
                                                <label class="form-label" for="simpleinput">主題:</label>
                                                <input type="text" class="form-control" readonly value="${forumtopicSvc.getOneForumTopic(forumPostVO.forumTopicNo).forumTopicName}">
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label" for="simpleinput">貼文標題:</label>
                                                <input type="text" class="form-control" name="title" required value="${forumPostVO.title}" required>
                                                <div class="invalid-feedback">貼文標題請勿空白</div>
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label" for="simpleinput">貼文內容:</label>
                                                <textarea class="js-summernote" id="democratNote" class="form-control" name="content" required>${forumPostVO.content}</textarea>
                                            </div>
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="forumTopicNo" value="${forumPostVO.forumTopicNo}">
                                            <input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}">
                                            <input type="hidden" name="studentNo" value="${forumPostVO.studentNo}">
                                            <button type="submit" class="btn btn-primary justify-content-center">送出修改</button>
                                        </form>
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
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
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

            $('#tableEvaluation').dataTable({
                responsive: true,
                language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' }
            });

            $('#democratNote').summernote({
                height: 500,
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
                    onInit: function (e) {},
                    onChange: function (contents, $editable) { }
                }
            });
        });
    </script>
</body>

</html>