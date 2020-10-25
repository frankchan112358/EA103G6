<%@page import="com.leave.model.LeaveVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="leaveSvc" scope="page" class="com.leave.model.LeaveService" />
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="timetableSvc" scope="page" class="com.timetable.model.TimetableService" />

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/front-end/template/head.jsp" %>
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/notifications/sweetalert2/sweetalert2.bundle.css">
    <style>
        .swal2-container {
            z-index: 100000;
        }
    </style>
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
                        <li class="breadcrumb-item">請假申請</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-file-edit'></i> 請假申請
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
                                        <button id="new" data-toggle="modal" data-target="#leaveEditor" type="button" class="btn btn-primary waves-effect waves-themed float-left">申請</button>
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
    <div class="modal fade" id="leaveEditor" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">學員請假單
                        <small class="m-0 text-muted">請務必照實填寫</small>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal">
                        <span><i class="fal fa-times"></i></span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="leaveForm" class="needs-validation" novalidate>
                        <input id="studentNo" type="hidden" name="studentNo" value="${studentVO.studentNo}" />
                        <input id="leaveNo" type="hidden" name="leaveNo" value="" />
                        <input id="timetableNo" type="hidden" name="timetableNo" value="" />
                        <div class="form-group">
                            <label class="form-label">選擇課堂</label>
                            <span id="timetableInfo">未選擇</span>
                            <div class="panel-content">
                                <div id="calendar"></div>
                                <!-- Modal : TODO -->
                                <!-- Modal end -->
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="type">假別</label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class='fal fa-file-edit'></i></span>
                                </div>
                                <select class="custom-select" id="type" name="type" required="">
                                    <c:forEach var="type" items="${leaveSvc.getLeaveTypeAll()}">
                                        <option value="${type.num}">${type.text}</option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">請選擇假別</div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="description">描述事由</label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class='fal fa-file-edit'></i></span>
                                </div>
                                <textarea id="description" name="description" class="form-control" required=""></textarea>
                                <div class="invalid-feedback">描述不能空白</div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="cancel">取消</button>
                    <button type="button" class="btn btn-primary" id="save">送出</button>
                </div>
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
        var action = "new"
        // 把java取值得結果，先放入var變數
        var ContextPath = '<%=request.getContextPath()%>';
        $(document).ready(function () {
            var leaveForm = document.getElementById('leaveForm');

            var columnSet = [
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
                        let html = `<button type="button" class="review btn btn-primary" leaveNo="${"${row.action.id}"}">檢視</button>`;
                        if (row.action.cando == 1)
                            html = `<button type="button" class="update btn btn-info" leaveNo="${"${row.action.id}"}">修改</button>
                                         <button type="button" class="cancel btn btn-danger" leaveNo="${"${row.action.id}"}">取消</button>`;
                        if (row.action.cando == 2)
                            html += ` <button type="button" class="cancel btn btn-danger" leaveNo="${"${row.action.id}"}">取消</button>`;
                        return html;
                    }
                }]


            var leaveTable = $('#leaveTable').DataTable({
                responsive: true,
                language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' },
                columns: columnSet,
                ajax: {
                    url: '<%=request.getContextPath()%>/leave/student',
                    type: 'GET',
                    async: true,
                    cache: false,
                    data: {
                        studentNo: '${studentVO.studentNo}'
                    }
                }
            });

            var calendarEl = document.getElementById('calendar');

            var calendar = new FullCalendar.Calendar(calendarEl, {
                plugins: ['dayGrid'],
                themeSystem: 'bootstrap',
                editable: false,
                displayEventTime: false,
                locale: 'zh-tw',
                events: '<%=request.getContextPath()%>/leave/calendar?studentNo=${studentVO.studentNo}',
                eventClick: function (info) {
                    calendar.getEvents().forEach(e => {
                        e.setProp('borderColor', '');
                    });
                    info.event.setProp('borderColor', 'red');
                    $('#timetableNo').val(info.event.id);
                    $('#timetableInfo').html(info.event.extendedProps.timetableInfo);
                },
                eventRender:function(event){
                    event.el.setAttribute('timetableNo',event.event.id)
                }
            });

            $('#leaveEditor').on('shown.bs.modal', function () {
                calendar.render();
            });

            $('#leaveEditor').on('hidden.bs.modal', function () {
                resetLeaveForm();
            });

            $('#new').click(function () {
                resetLeaveForm();
                action = "new";
            });

            $('#save').click(function (event) {
                if ($('#timetableNo').val() == '') {
                    Swal.fire("請問請哪節課?", "請在課表中選取課堂", "question");
                    return;
                }

                if (leaveForm.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                    leaveForm.classList.add('was-validated');
                    return;
                } else {
                    leaveForm.classList.add('was-validated');
                }

                if (action == 'new') {
                    $.ajax({
                        type: 'POST',
                        url: '<%=request.getContextPath()%>/leave/add',
                        data: $(leaveForm).serialize(),
                        success(res) {
                            if (res == 'ok') {
                                $('#leaveEditor').modal('hide');
                                resetLeaveForm();
                            }
                        },
                        error(err) {
                            console.log(err);
                        }
                    });
                } else if (action == 'update') {
                    $.ajax({
                        type: 'POST',
                        url: '<%=request.getContextPath()%>/leave/updatestudentleave',
                        data: $(leaveForm).serialize(),
                        success(res) {
                            if (res == 'ok') {
                                $('#leaveEditor').modal('hide');
                                resetLeaveForm();
                            }
                        },
                        error(err) {
                            console.log(err);
                        }
                    });
                }
            });

            function resetLeaveForm() {
                leaveForm.classList.remove('was-validated');
                calendar.getEvents().forEach(e => {
                    e.setProp('borderColor', '');
                });
                leaveTable.ajax.reload(null, false);
                calendar.removeAllEvents()
                calendar.refetchEvents();
                calendar.today();
                $('#timetableInfo').html('未選擇');
                $('#leaveNo').val('');
                $('#timetableNo').val('');
                $('#type').val('0');
                $('#description').val('');
            }

            $(document).on('click', 'button.update', function (event) {
                resetLeaveForm();
                action = "update";
                let leaveNo = this.getAttribute('leaveNo');
                $.ajax({
                    type: 'GET',
                    url: '<%=request.getContextPath()%>/leave/getforupdate',
                    data: {
                        leaveNo: leaveNo
                    },
                    success(res) {
                        if (res != null) {
                            $('#leaveNo').val(res.leaveNo);
                            $('#timetableNo').val(res.timetableNo);
                            $('#type').val(res.type);
                            $('#description').val(res.description);
                            $('#leaveEditor').modal('show');
                            calendar.addEvent(res.timetableEvent);
                            $('#timetableInfo').html(res.timetableEvent.extendedProps.timetableInfo);
                        }
                    }
                });
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
                let leaveNo = this.getAttribute('leaveNo');
                Swal.fire({
                    title: "你確定要取消該請假申請嗎?",
                    text: "如果取消後，無法復原，需要重新申請!",
                    type: "warning",
                    showCancelButton: true,
                    cancelButtonText: "先不要",
                    confirmButtonText: "是的，我要取消!"
                }).then(function (result) {
                    if (result.value) {
                        $.ajax({
                            type: 'POST',
                            url: '<%=request.getContextPath()%>/leave/cancel',
                            data: {
                                leaveNo: leaveNo
                            },
                            success(res) {
                                if (res == 'ok') {
                                    leaveTable.ajax.reload(null, false);
                                    Swal.fire("取消!", "你的請假申請已經取消", "成功");
                                }
                            }
                        });
                    }
                });
            });
        });
    </script>
</body>

</html>