<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/front-end/template/head.jsp" %>
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/formplugins/summernote/summernote.css">
</head>

<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
    <script>
        var classHolder = document.getElementsByTagName("BODY")[0];
    </script>
    <div class="page-wrapper">
        <div class="page-inner">
            <%@ include file="/front-end/template/left_aside.jsp" %>
            <div class="page-content-wrapper">
                <%@ include file="/front-end/template/header.jsp" %>
                <main id="js-page-content" role="main" class="page-content">
                    <ol class="breadcrumb page-breadcrumb">
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/index/index.jsp">前台首頁</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/forum/forum.do">班級討論區</a></li>
                        <li class="breadcrumb-item"><a id="gotoForumTopic" forumTopicNo="${forumTopicVO.forumTopicNo}" href="javascript:void(0)">${forumTopicVO.forumTopicName}</a></li>
                        <li class="breadcrumb-item">新增貼文</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-comments-alt'></i>
                            新增貼文
                        </h1>
                    </div>
                    <div class="row align-items-center justify-content-center">
                        <div class="col-xl-9 col-lg-10">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-gradient-info">
                                    <h2>新增貼文</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <form method="post" action="<%=request.getContextPath() %>/forum/forum.do"  enctype="multipart/form-data" class="needs-validation" novalidate>
                                            <div class="form-group">
                                                <label class="form-label">討論版<span class="text-danger">*</span></label>
                                                <select class="custom-select form-control" disabled required>
                                                    <c:forEach var="pageForumTopicVO" items="${studentVO.banjiVO.forumTopicList}">
                                                        <option value="${pageForumTopicVO.forumTopicNo}" ${(pageForumTopicVO.forumTopicNo==forumTopicVO.forumTopicNo)?'selected':'' }>${pageForumTopicVO.forumTopicName}</option>
                                                    </c:forEach>
                                                </select>
                                                <div class="invalid-feedback">請選擇討論版.</div>
                                                <input type="hidden" name="forumTopicNo" value="${forumTopicVO.forumTopicNo}">
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label">貼文標題<span class="text-danger">*</span></label>
                                                <input type="text" name="title" class="form-control" value="" required placeholder="貼文標題" />
                                                <div class="invalid-feedback">請填寫貼文名稱.</div>
                                            </div>
                                            <div class="form-group">
                                                <label>貼文內容<span class="text-danger">*</span></label>
                                                <textarea id="forumPostContent" class="js-summernote" name="content" required></textarea>
                                                <div class="invalid-feedback">請勿空白.</div>
                                            </div>
                                            <input type="hidden" name="studentNo" value="${studentVO.studentNo}" />
                                            <input type="hidden" name="action" value="forumPostInsert">
                                            <button type="submit" class="mb-3 mt-3 btn btn-info waves-effect waves-themed float-left">送出</button>
                                        </form>
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
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
    <script>
        'use strict';

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

            $('#forumPostContent').summernote({
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

            $('#gotoForumTopic').click(function (e) {
                let _this = $(this);
                let forumTopicNo = _this.attr('forumTopicNo');
                let myForm = createMyFrom('<%=request.getContextPath()%>/forum/forum.do');
                document.body.appendChild(myForm);
                myForm.append(createFormInput('hidden', 'action', 'forumTopicHomePage'));
                myForm.append(createFormInput('hidden', 'forumTopicNo', forumTopicNo));
                myForm.submit();
            });

            function createMyFrom(url) {
                let myForm = document.createElement('form');
                myForm.action = url;
                myForm.method = 'POST';
                return myForm;
            }

            function createFormInput(type, name, value) {
                let formInput = document.createElement('input');
                formInput.type = type;
                formInput.name = name;
                formInput.value = value;
                return formInput;
            }
        });
    </script>
</body>

</html>