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
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/leave/leave.handle">請假申請總覽</a></li>
                        <li class="breadcrumb-item">${mode eq 'new' ? '請假申請':'請假修改'}</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-file-edit'></i> ${mode eq 'new' ? '請假申請':'請假修改'}
                        </h1>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">學員請假單</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <h1>請務必照實填寫</h1>
                                        <form method="POST" action="<%=request.getContextPath()%>/leave/leave.handle" id="leaveForm" class="needs-validation" novalidate>
                                            <input type="hidden" name="action" value="${mode eq 'new' ? 'insert':'update'}" />
                                            <input id="studentNo" type="hidden" name="studentNo" value="${studentVO.studentNo}" />
                                            <input id="leaveNo" type="hidden" name="leaveNo" value="${mode eq 'new' ? '':leaveVO.leaveNo}" />
                                            <input id="timetableNo" type="hidden" name="timetableNo" value="${mode eq 'new' ? '':leaveVO.timetableNo}" />
                                            <div class="form-group">
                                                <label class="form-label">選擇課堂</label>
                                                <label class="form-label" id="timetableInfo">未選擇</span>
                                            </div>
                                            <div id="calendar"></div>
                                            <div class="form-group">
                                                <label class="form-label" for="type">假別</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class='fal fa-file-edit'></i></span>
                                                    </div>
                                                    <select class="custom-select" id="type" name="type" required="">
                                                        <c:forEach var="type" items="${leaveSvc.getLeaveTypeAll()}">
                                                            <option value="${type.num}" ${(mode eq 'update' && type.num==leaveVO.type)?'selected':'' }>${type.text}</option>
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
                                                    <textarea id="description" name="description" class="form-control" required="">${mode eq 'new' ? '':leaveVO.description}</textarea>
                                                    <div class="invalid-feedback">描述不能空白</div>
                                                </div>
                                            </div>
                                            <button type="button" class="btn btn-primary" id="save">送出</button>
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
    <script>
        'use strict';
        var action = "${mode}"

        $(document).ready(function () {
            <c:if test="${mode == 'update'}">
                $('#timetableInfo').html('${timetableInfo}');
            </c:if>
            var leaveForm = document.getElementById('leaveForm');
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                plugins: ['dayGrid', 'list', 'timeGrid', 'interaction', 'bootstrap'],
                themeSystem: 'bootstrap',
                locale: 'local',
                displayEventTime: false,
                buttonText: {
                    today: '今天',
                    month: '月',
                    week: '周',
                    day: '天',
                    list: '清單'
                },
                eventTimeFormat: {
                    hour: 'numeric',
                    minute: '2-digit',
                    meridiem: 'short'
                },
                navLinks: true,
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth'
                },
                footer: {
                    left: '',
                    center: '',
                    right: ''
                },
                eventLimit: true,
                events: JSON.parse('${calenderEvents}'),
                viewSkeletonRender: function () {
                    $('.fc-toolbar .btn-default').addClass('btn-sm');
                    $('.fc-header-toolbar h2').addClass('fs-md');
                    $('#calendar').addClass('fc-reset-order')
                },
                eventClick: function (info) {
                    calendar.getEvents().forEach(e => {
                        e.setProp('borderColor', '#2198F3');
                    });
                    info.event.setProp('borderColor', 'red');
                    $('#timetableNo').val(info.event.id);
                    $('#timetableInfo').html(info.event.extendedProps.timetableInfo);
                    Swal.fire({
                        position: "top-center",
                        title: info.event.extendedProps.timetableInfo,
                        showConfirmButton: false,
                        timer: 1500
                    });
                },
                eventRender: function (event) {
                    event.el.setAttribute('timetableNo', event.event.id)
                }
            });
            calendar.render();

            document.getElementById('save').addEventListener('click', function (e) {
                e.preventDefault();
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
                leaveForm.submit();
            }, false);
        });
    </script>
</body>

</html>