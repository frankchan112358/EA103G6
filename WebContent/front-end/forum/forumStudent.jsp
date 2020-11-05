<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>

<head>
    <%@ include file="/front-end/template/head.jsp" %>
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
                        <li class="breadcrumb-item">討論區發文紀錄</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-comments-alt'></i>
                            討論區發文紀錄
                        </h1>
                    </div>
                    <div class="row align-items-center justify-content-center">
                        <div class="col-xl-9 col-lg-10">
                            <div class="card mb-g border shadow-0">
                                <div class="card-header">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col">
                                            <span class="h6 font-weight-bold text-uppercase">討論區發文紀錄</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-header p-0">
                                    <div class="row no-gutters row-grid align-items-stretch">
                                        <div class="col-12 col-md">
                                            <div class="text-uppercase text-muted py-2 px-3">貼文標題</div>
                                        </div>
                                        <div class="col-sm-6 col-md-2 col-xl-1 hidden-md-down">
                                            <div class="text-uppercase text-muted py-2 px-3">回覆狀態</div>
                                        </div>
                                        <div class="col-sm-6 col-md-3 hidden-md-down">
                                            <div class="text-uppercase text-muted py-2 px-3">操作</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body p-0">
                                    <div class="row no-gutters row-grid">
                                        <c:forEach var="forumPostVO" items="${forumPostList}">
                                            <div class="col-12">
                                                <div class="row no-gutters row-grid align-items-stretch">
                                                    <div class="col-md">
                                                        <div class="p-3">
                                                            <div class="d-flex flex-column">
                                                                <a forumPostNo="${forumPostVO.forumPostNo}" href="javascript:void(0)" class="forumPost fs-xxl fw-500 d-flex align-items-start">
                                                                    ${forumPostVO.title}
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-4 col-md-2 col-xl-1 hidden-md-down">
                                                        <div class="p-3 p-md-3">
                                                            <span class="d-block text-muted">${forumPostVO.forumCommentList.size()} <i>回覆數量</i></span>
                                                            <span class="d-block text-muted">${forumPostVO.forumPostViews} <i>觀看次數</i></span>
                                                        </div>
                                                    </div>
                                                    <div class="col-8 col-md-3 hidden-md-down">
                                                        <div class="p-3 p-md-3">
                                                            <div class="d-flex align-items-center">
                                                                <button forumPostNo="${forumPostVO.forumPostNo}" type="button" class="update btn btn-info mr-1"><span class="fal fa-edit mr-1"></span><span>修改</span></button>
                                                                <button forumPostNo="${forumPostVO.forumPostNo}" type="button" class="delete btn btn-danger mr-1"><span class="fal fa-times mr-1"></span><span>刪除</span></button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <ul class="pagination mt-3">
                                <li class="page-item disabled">
                                    <a class="page-link" href="javascript:void(0)" aria-label="Previous">
                                        <span aria-hidden="true"><i class="fal fa-chevron-left"></i></span>
                                    </a>
                                </li>
                                <li class="page-item active" aria-current="page">
                                    <span class="page-link">
                                        1
                                        <span class="sr-only">(current)</span>
                                    </span>
                                </li>
                                <li class="page-item"><a class="page-link" href="javascript:void(0)">2</a></li>
                                <li class="page-item"><a class="page-link" href="javascript:void(0)">3</a></li>
                                <li class="page-item">
                                    <a class="page-link" href="javascript:void(0)" aria-label="Next">
                                        <span aria-hidden="true"><i class="fal fa-chevron-right"></i></span>
                                    </a>
                                </li>
                            </ul>
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
    <script>
        'use strict';

        $(document).ready(function () {

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

            $(document).on('click', 'button.update', function (e) {
                let _this = $(this);
                let forumPostNo = _this.attr('forumPostNo');
                let myForm = createMyFrom('<%=request.getContextPath()%>/forum/forum.do');
                document.body.appendChild(myForm);
                myForm.append(createFormInput('hidden', 'forumPostNo', forumPostNo));
                myForm.append(createFormInput('hidden', 'action', 'forumPostUpdatePage'));
                myForm.submit();
            });

        });
    </script>
</body>

</html>