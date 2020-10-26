<%@page import="com.leave.model.LeaveVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/front-end/template/head.jsp" %>
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/notifications/sweetalert2/sweetalert2.bundle.css">
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
                        <li class="breadcrumb-item">請假申請總覽</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-file-edit'></i> 請假申請總覽
                        </h1>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">請假申請總覽</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">

                                        <form method="post" action="<%=request.getContextPath()%>/leave/leave.handle" class="m-1">
                                            <button type="submit" class="btn btn-primary waves-effect waves-themed float-left">
                                                <span class="fal fa-edit mr-1"></span>
                                                <span>申請</span>
                                            </button>
                                            <input type="hidden" name="action" value="new">
                                        </form>

                                        <!-- datatable start -->
                                        <table id="leaveTable" class="table table-bordered table-hover table-striped w-100">
                                            <thead>
                                                <tr>
                                                    <th>日期</th>
                                                    <th>時段</th>
                                                    <th>課程</th>
                                                    <th>假別</th>
                                                    <th>狀態</th>
                                                    <th>操作</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="leaveVO" items="${list}">
                                                    <tr>
                                                        <td>${leaveVO.timetableVO.timetableDate}</td>
                                                        <td>${leaveVO.timetableVO.periodText}</td>
                                                        <td>${leaveVO.timetableVO.courseVO.courseName}</td>
                                                        <td>${leaveVO.typeText}</td>
                                                        <td>${leaveVO.statusText}</td>
                                                        <td class="d-flex p-1">
                                                            <button type="button" class="review btn btn-primary m-1" leaveNo="${leaveVO.leaveNo}">檢視</button>
                                                            <c:if test="${leaveVO.status==0}">
                                                                <form method="post" action="<%=request.getContextPath()%>/leave/leave.handle" class="m-1">
                                                                    <button type="submit" class="update btn btn-info" leaveNo="${leaveVO.leaveNo}">修改</button>
                                                                    <input type="hidden" name="leaveNo" value="${leaveVO.leaveNo}">
                                                                    <input type="hidden" name="action" value="display_for_update">
                                                                </form>
                                                            </c:if>
                                                            <c:if test="${leaveVO.status==0||leaveVO.status==1}">
                                                                <form method="post" action="<%=request.getContextPath()%>/leave/leave.handle" class="m-1">
                                                                    <button type="button" class="cancel btn btn-danger" leaveNo="${leaveVO.leaveNo}">取消</button>
                                                                    <input type="hidden" name="leaveNo" value="${leaveVO.leaveNo}">
                                                                    <input type="hidden" name="action" value="cancel">
                                                                </form>
                                                            </c:if>
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
                <%@ include file="/front-end/template/footer.jsp" %>
            </div>
        </div>
    </div>
    <div class="modal fade" id="leaveReview" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-md modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title">檢視請假單</h1>
                    <button type="button" class="close" data-dismiss="modal">
                        <span><i class="fal fa-times"></i></span>
                    </button>
                </div>
                <div class="modal-body">
                    <h5 class="text-muted">日期</h5>
                    <span style="font-size:x-large;" data="timetableDate"></span>
                    <h5 class="text-muted">時段</h5>
                    <span style="font-size:x-large;" data="periodText"></span>
                    <h5 class="text-muted">課程</h5>
                    <span style="font-size:x-large;" data="courseName"></span>
                    <h5 class="text-muted">假別</h5>
                    <span style="font-size:x-large;" data="typeText"></span>
                    <h5 class="text-muted">狀態</h5>
                    <span style="font-size:x-large;" data="statusText"></span>
                    <h5 class="text-muted">事由</h5>
                    <p style="font-size: x-large;" data="description"></p>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/front-end/template/quick_menu.jsp" %>
    <%@ include file="/front-end/template/messager.jsp" %>
    <%@ include file="/front-end/template/basic_js.jsp" %>
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/notifications/sweetalert2/sweetalert2.bundle.js"></script>
    <script>
        'use strict';
        $(document).ready(function () {
            var leaveTable = $('#leaveTable').DataTable({
                responsive: true,
                language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' }
            });

            $(document).on('click', 'button.review', function (event) {
                let leaveNo = this.getAttribute('leaveNo');
                $.ajax({
                    type: 'GET',
                    url: '<%=request.getContextPath()%>/leave/one',
                    data: {
                        leaveNo: leaveNo
                    },
                    success(res) {
                        if (res != null) {
                            let review = $('#leaveReview .modal-body');
                            review.find('[data=timetableDate]').text(res.timetableDate);
                            review.find('[data=periodText]').text(res.periodText);
                            review.find('[data=courseName]').text(res.courseName);
                            review.find('[data=typeText]').text(res.typeText);
                            review.find('[data=statusText]').text(res.statusText);
                            review.find('[data=description]').text(res.description);
                            $('#leaveReview').modal('show');
                        }
                    }
                })
            });

            $(document).on('click', 'button.cancel', function (event) {
                event.preventDefault();
                let _this = $(this);
                Swal.fire({
                    title: "你確定要取消該請假申請嗎?",
                    text: "如果取消後，無法復原，需要重新申請!",
                    type: "warning",
                    showCancelButton: true,
                    cancelButtonText: "先不要",
                    confirmButtonText: "是的，我要取消!"
                }).then(function (result) {
                    if (result.value)
                        _this.parent()[0].submit();
                });
            });
        });
    </script>
</body>

</html>