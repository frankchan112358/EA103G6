<%@page import="com.forumtopic.model.ForumTopicVO"%>
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
                        <li class="breadcrumb-item">班級討論區</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-comments-alt'></i>
                            班級討論區
                        </h1>
                    </div>
                    <div class="row align-items-center justify-content-center">
                        <div class="col-xl-9 col-lg-10">
                            <div class="card mb-g border shadow-0">
                                <div class="card-header">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col">
                                            <span class="h6 font-weight-bold text-uppercase">${studentVO.banjiVO.banjiName}</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-header p-0">
                                    <div class="row no-gutters row-grid align-items-stretch">
                                        <div class="col-12 col-md">
                                            <div class="text-uppercase text-muted py-2 px-3">討論版主題</div>
                                        </div>
                                        <div class="col-sm-6 col-md-2 col-xl-1 hidden-md-down">
                                            <div class="text-uppercase text-muted py-2 px-3">狀態</div>
                                        </div>
                                        <div class="col-sm-6 col-md-3 hidden-md-down">
                                            <div class="text-uppercase text-muted py-2 px-3">最新發文</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body p-0">
                                    <div class="row no-gutters row-grid">
                                        <c:forEach var="forumTopicVO" items="${forumTopicList}">
                                            <div class="col-12">
                                                <div class="row no-gutters row-grid align-items-stretch">
                                                    <div class="col-md">
                                                        <div class="p-3">
                                                            <div class="d-flex">
                                                                <span class="icon-stack display-4 mr-3 flex-shrink-0">
                                                                    <i class="fal fa-comments-alt icon-stack-3x color-primary-800"></i>
                                                                </span>
                                                                <div class="d-inline-flex flex-column">
                                                                    <a forumTopicNo="${forumTopicVO.forumTopicNo}" href="javascript:void(0)" class="forumTopic fs-xxl fw-500 d-block">
                                                                        ${forumTopicVO.forumTopicName}
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-4 col-md-2 col-xl-1 hidden-md-down">
                                                        <div class="p-3 p-md-3">
                                                            <span class="d-block text-muted">${forumTopicVO.forumPostList.size()} <i>貼文數量</i></span>
                                                            <span class="d-block text-muted">${forumTopicVO.forumCommentTotalSize} <i>回覆數量</i></span>
                                                        </div>
                                                    </div>
                                                    <div class="col-8 col-md-3 hidden-md-down">
                                                        <div class="p-3 p-md-3">
                                                            <c:if test="${forumTopicVO.forumPostList.size()>0}" var="checkForumPostListSize" scope="page">
                                                                <% 
                                                                ForumTopicVO _forumTopicVO = (ForumTopicVO)pageContext.getAttribute("forumTopicVO");
                                                                pageContext.setAttribute("newestForumPost", _forumTopicVO.getForumPostList().get(0)); 
                                                                %>
                                                                <div class="d-flex align-items-center">
                                                                    <div class="d-inline-block align-middle mr-2">
                                                                        <span class="profile-image rounded-circle d-block" style="background-image:url('<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${newestForumPost.studentVO.userNo}'); background-size: cover;"></span>
                                                                    </div>
                                                                    <div class="flex-1 min-width-0">
                                                                        <a href="javascript:void(0)" class="d-block text-truncate">
                                                                            ${newestForumPost.title}
                                                                        </a>
                                                                        <div class="text-muted small text-truncate">
                                                                            <a href="javascript:void(0)" class="text-info">${newestForumPost.studentVO.studentName}</a>
                                                                            <br>
                                                                            <fmt:formatDate value="${newestForumPost.createTime}" pattern="yyyy-MM-dd HH:mm" />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${checkForumPostListSize == false}">
                                                                <div class="d-flex align-items-center">
                                                                    目前尚無任何貼文
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
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
    <script>
        'use strict';

        $(document).ready(function () {
            $(document).on('click', 'a.forumTopic', function (e) {
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