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
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.manage">養成班管理</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.manage?action=read&banjiNo=${banjiVO.banjiNo}">${banjiVO.banjiName}</a></li>
                        <li class="breadcrumb-item">學員請假審核</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-file-edit'></i> 學員請假審核
                        </h1>
                    </div>
                    <div class="row">
                        <div class="col col-xl-12">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">總覽</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <table id="leaveTable" class="table table-bordered table-hover table-striped w-100">
                                            <thead>
                                                <tr>
                                                    <th>學號</th>
                                                    <th>學生</th>
                                                    <th>日期</th>
                                                    <th>時段</th>
                                                    <th>課程</th>
                                                    <th>假別</th>
                                                    <th>狀態</th>
                                                    <th>操作</th>
                                                </tr>
                                            </thead>
                                        </table>
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
                    <div class="d-flex align-items-center mb-g">
                        <img id="userImg" src="<%=request.getContextPath() %>/images/noPicture.png" class="rounded-circle profile-image mr-3">
                        <h1 class="fw-300 m-0 l-h-n">
                            <span class="text-contrast">[學員請假單]</span>
                            <small class="fw-300 m-0 l-h-n">
                                work join learn
                            </small>
                        </h1>
                    </div>
                    <h5 class="text-muted mb-0">事由</h5>
                    <span style="font-size:x-large;" data="description"></span>
                    <h5 class="text-muted mb-0">姓名</h5>
                    <span style="font-size:x-large;" data="studentName"></span>
                    <h5 class="text-muted mb-0">班級</h5>
                    <span style="font-size:x-large;" data="banjiName"></span>
                    <h5 class="text-muted mb-0">學號</h5>
                    <span style="font-size:x-large;" data="studentNo"></span>
                    <h5 class="text-muted mb-0">日期</h5>
                    <span style="font-size:x-large;" data="timetableDate"></span>
                    <h5 class="text-muted mb-0">時段</h5>
                    <span style="font-size:x-large;" data="periodText"></span>
                    <h5 class="text-muted mb-0">課程</h5>
                    <span style="font-size:x-large;" data="courseName"></span>
                    <h5 class="text-muted mb-0">假別</h5>
                    <span style="font-size:x-large;" data="typeText"></span>
                    <h5 class="text-muted mb-0">狀態</h5>
                    <span style="font-size:x-large;" data="statusText"></span>
                </div>
                <div class="modal-footer">
                    <button id="rejectBtn" banjiNo="${banjiVO.banjiNo}" leaveNo="" type="button" class="btn btn-success" id="reject">
                        <span class="fal fa-edit mr-1"></span>
                        <span>拒絕</span>
                    </button>
                    <button id="passBtn" banjiNo="${banjiVO.banjiNo}" leaveNo="" type="button" class="btn btn-danger" id="pass">
                        <span class="fal fa-edit mr-1"></span>
                        <span>同意</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %>
    <script>
        'use strict';
        var imgSrc = '<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=';
        var noImgSrc = '<%=request.getContextPath() %>/images/noPicture.png';
        $(document).ready(function () {
            var columnSet = [
                {
                    title: "學號",
                    id: "studentNo",
                    data: "studentNo",
                    type: "text"
                },
                {
                    title: "學生",
                    id: "studentName",
                    data: "studentName",
                    type: "text"
                },
                {
                    title: "日期",
                    id: "timetableDate",
                    data: "timetableDate",
                    type: "text"
                },
                {
                    title: "時段",
                    id: "periodText",
                    data: "periodText",
                    type: "text"
                },
                {
                    title: "課程",
                    id: "courseName",
                    data: "courseName",
                    type: "text"
                },
                {
                    title: "假別",
                    id: "typeText",
                    data: "typeText",
                    type: "text"
                },
                {
                    title: "狀態",
                    id: "statusText",
                    data: "statusText",
                    type: "text"
                },
                {
                    title: "操作",
                    id: "action",
                    data: "action",
                    render(data, type, row, meta) {
                        let html = `<button type="button" class="read btn btn-success" leaveNo="${"${row.action.id}"}">檢視</button>`;
                        if (row.action.status == 0)
                            html = `<button type="button" class="decide btn btn-info" leaveNo="${"${row.action.id}"}">審核</button>`;
                        return html;
                    }
                }];
            var leaveTable = $('#leaveTable').DataTable({
                responsive: true,
                language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' },
                columns: columnSet,
                ajax: {
                    url: '<%=request.getContextPath()%>/banji/banji.leave',
                    type: 'GET',
                    async: true,
                    cache: false,
                    data: {
                        action: 'datatable',
                        banjiNo: '${banjiVO.banjiNo}'
                    }
                }
            });

            $(document).on('click', 'button.read', function (event) {
                let leaveNo = this.getAttribute('leaveNo');
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/banji/banji.leave',
                    data: {
                        action: 'read',
                        leaveNo: leaveNo,
                        banjiNo: '${banjiVO.banjiNo}'
                    },
                    success(res) {
                        if (res != null) {
                            setLeaveModal(res, 'read');
                            $('#leaveReview').modal('show');
                        }
                    }
                });
            });

            $(document).on('click', 'button.decide', function (event) {
                let leaveNo = this.getAttribute('leaveNo');
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/banji/banji.leave',
                    data: {
                        action: 'decide',
                        leaveNo: leaveNo,
                        banjiNo: '${banjiVO.banjiNo}'
                    },
                    success(res) {
                        if (res != null) {
                            setLeaveModal(res, 'decide');
                            $('#leaveReview').modal('show');
                        }
                    }
                });
            });

            function setLeaveModal(res, status) {
                let review = $('#leaveReview .modal-body');
                review.find('[data=description]').text(res.description);
                review.find('[data=studentName]').text(res.studentName);
                review.find('[data=banjiName]').text(res.banjiName);
                review.find('[data=studentNo]').text(res.studentNo);
                review.find('[data=timetableDate]').text(res.timetableDate);
                review.find('[data=periodText]').text(res.periodText);
                review.find('[data=courseName]').text(res.courseName);
                review.find('[data=typeText]').text(res.typeText);
                review.find('[data=statusText]').text(res.statusText);
                if (res.hasHeadImg)
                    $('#userImg').attr('src', imgSrc + res.userNo);
                else
                    $('#userImg').attr('src', noImgSrc);
                $('#rejectBtn').attr('leaveNo', res.leaveNo);
                $('#passBtn').attr('leaveNo', res.leaveNo);
                if (status == 'decide'){
                    $('#leaveReview .modal-footer').show();
                    $('#leaveReview modal-title').html('審核請假單');
                }                    
                else{
                    $('#leaveReview .modal-footer').hide();
                    $('#leaveReview modal-title').html('檢視請假單');
                }              
            }

            $('#passBtn').click(function (event) {
                let leaveNo = this.getAttribute('leaveNo');
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/banji/banji.leave',
                    data: {
                        action: 'pass',
                        leaveNo: leaveNo,
                        banjiNo: '${banjiVO.banjiNo}'
                    },
                    success(res) {
                        if (res == 'ok') {
                            leaveTable.ajax.reload(null, false);
                            $('#leaveReview').modal('hide');

                        }
                    }
                });
            });

            $('#rejectBtn').click(function (event) {
                let leaveNo = this.getAttribute('leaveNo');
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/banji/banji.leave',
                    data: {
                        action: 'reject',
                        leaveNo: leaveNo,
                        banjiNo: '${banjiVO.banjiNo}'
                    },
                    success(res) {
                        if (res == 'ok') {
                            leaveTable.ajax.reload(null, false);
                            $('#leaveReview').modal('hide');
                        }
                    }
                });
            });
        });
    </script>
</body>

</html>