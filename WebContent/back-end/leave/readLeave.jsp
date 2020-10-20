<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="leaveSvc" scope="page" class="com.leave.model.LeaveService" />
<%
//init code
%>

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/back-end/template/head.jsp" %>
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/notifications/sweetalert2/sweetalert2.bundle.css">
</head>

<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
    <script>
        var classHolder = document.getElementsByTagName("BODY")[0];
    </script>
    <div class="page-wrapper">
        <div class="page-inner">
            <%@ include file="/back-end/template/left_aside.jsp" %>
            <div class="page-content-wrapper">
                <%@ include file="/back-end/template/header.jsp" %>
                <main id="js-page-content" role="main" class="page-content">
                    <ol class="breadcrumb page-breadcrumb">
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/leave/leave.manage">學員請假審核</a></li>
                        <li class="breadcrumb-item">檢視請假單</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-file-edit'></i> 檢視請假單
                        </h1>
                    </div>
                    <div class="row">
                        <div class="col col-xl-12">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">檢視</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <div class="card h-100 rounded overflow-hidden position-relative">
                                            <div class="card-body p-4">
                                                <div class="d-flex align-items-center mb-g">
                                                    <c:if test="${leaveVO.studentVO.userVO.photo != null}" var="checkphoto" scope="page">
                                                        <img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${leaveVO.studentVO.userVO.userNo}" class="rounded-circle profile-image mr-3">
                                                    </c:if>
                                                    <c:if test="${checkphoto == false}">
                                                        <img src="<%=request.getContextPath() %>/images/noPicture.png" class="mr-3">
                                                    </c:if>
                                                    <h1 class="fw-300 m-0 l-h-n">
                                                        <span class="text-contrast">[學員請假單]</span>
                                                        <small class="fw-300 m-0 l-h-n">
                                                            work join learn
                                                        </small>
                                                    </h1>
                                                </div>
                                                <div class="col">
                                                    <span>事由</span>
                                                    <p class="fs-xxl">${leaveVO.description}</p>
                                                    <ul class="mt-3 list-spaced">
                                                        <li>
                                                            姓名 : ${leaveVO.studentVO.studentName}
                                                        </li>
                                                        <li>
                                                            班級 : ${leaveVO.studentVO.banjiVO.banjiName}
                                                        </li>
                                                        <li>
                                                            學號 : ${leaveVO.studentVO.studentNo}
                                                        </li>
                                                        <li>
                                                            日期 : ${leaveVO.timetableVO.timetableDate}
                                                        </li>
                                                        <li>
                                                            時段 : ${leaveVO.timetableVO.periodText}
                                                        </li>
                                                        <li>
                                                            課程 : ${leaveVO.timetableVO.courseVO.courseName}
                                                        </li>
                                                        <li>
                                                            假別 : ${leaveVO.typeText}
                                                        </li>
                                                        <li>
                                                            狀態 : ${leaveVO.statusText}
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <c:if test="${leaveVO.status == 0}">
                                                <div class="card-footer py-2 d-flex m-auto">
                                                    <form method="post" action="<%=request.getContextPath()%>/leave/leave.manage" class="m-1">
                                                        <button type="submit" class="btn btn-sm btn-success">
                                                            <span class="fal fa-edit mr-1"></span>
                                                            <span>同意</span>
                                                        </button>
                                                        <input type="hidden" name="leaveNo" value="${leaveVO.leaveNo}">
                                                        <input type="hidden" name="action" value="decide">
                                                        <input type="hidden" name="todo" value="pass">
                                                    </form>
                                                    <form id="rejectForm" method="post" action="<%=request.getContextPath()%>/leave/leave.manage" class="m-1">
                                                        <button type="button" class="reject btn btn-sm btn-danger">
                                                            <span class="fal fa-edit mr-1"></span>
                                                            <span>拒絕</span>
                                                        </button>
                                                        <input type="hidden" name="leaveNo" value="${leaveVO.leaveNo}">
                                                        <input type="hidden" name="action" value="decide">
                                                        <input type="hidden" name="todo" value="reject">
                                                    </form>
                                                </div>
                                            </c:if>
                                        </div>
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
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/notifications/sweetalert2/sweetalert2.bundle.js"></script>
    <script>
        'use strict';
        $(document).ready(function () {
            $(document).on('click', 'button.reject', function (event) {
                let rejectForm = $('#rejectForm');
                event.preventDefault();
                Swal.fire({
                    title: "你確定要拒絕該請假申請嗎?",
                    text: "如果拒絕後，無法復原!",
                    type: "warning",
                    showCancelButton: true,
                    cancelButtonText: "先不要",
                    confirmButtonText: "是的，我要拒絕!"
                }).then(function (result) {
                    if (result.value) {
                        rejectForm.submit();
                    }
                });
            });
        });
    </script>
</body>

</html>