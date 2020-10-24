<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="timetableSvc" scope="page" class="com.timetable.model.TimetableService" />
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
                        <li class="breadcrumb-item">課表管理</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-calendar-alt'></i> 課表管理
                        </h1>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <c:forEach var="courseVO" items="${banjiVO.courseList}">
                                <button class="course" courseNo="${courseVO.courseNo}" banjiNo="${banjiVO.banjiNo}">${courseVO.courseName}</button>
                            </c:forEach>
                        </div>
                        <div class="col-12">
                            <div id="calendar"></div>
                        </div>
                    </div>
                </main>
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
                <%@ include file="/back-end/template/footer.jsp" %>
            </div>
        </div>
    </div>
    <div class="modal fade" id="editorTimetableModal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title"></h1>
                    <button type="button" class="close" data-dismiss="modal">
                        <span><i class="fal fa-times"></i></span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="timetableActive" class="needs-validation" novalidate>
                        <c:forEach var="period" items="${timetableSvc.timetablePeriodAll}">

                            <button periodNum="${period.num}" timetableNo="" dateStr="" type="button" class="timetableBtn editor btn btn-lg btn-outline-info">
                                <span class="fal fa-book-reader mr-1"></span>
                                ${period.text}
                            </button>

                        </c:forEach>

                        <button timetableNo="" type="button" class="timetableBtn delete btn btn-lg btn-outline-danger">
                            <span class="fal fa-times mr-1"></span>
                            刪除
                        </button>
                        <button type="button" class="btn btn-lg btn-outline-dark" data-dismiss="modal">
                            <span class="fal  fa-eject mr-1"></span>
                            取消
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %>
    <script>
        'use strict';
        $(document).ready(function () {
            var calendarEl = document.getElementById('calendar');
            var events = {};
            var _courseVO = {};
            var _banjiNo = '${banjiVO.banjiNo}';
            var _active = 'no';
            var calendar = new FullCalendar.Calendar(calendarEl, {
                plugins: ['dayGrid', 'list', 'timeGrid', 'interaction', 'bootstrap'],
                themeSystem: 'bootstrap',
                locale: 'zh-tw',
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
                    left: 'title',
                    center: '',
                    right: 'today prev,next'
                },
                footer: {
                    left: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek',
                    center: '',
                    right: ''
                },
                eventLimit: true,
                events: JSON.parse('${events}'),
                viewSkeletonRender: function () {
                    $('.fc-toolbar .btn-default').addClass('btn-sm');
                    $('.fc-header-toolbar h2').addClass('fs-md');
                    $('#calendar').addClass('fc-reset-order')
                },
                dateClick: function (info) {
                    _active = 'new';
                    let period = {
                        morning: true,
                        afternoon: true,
                        evening: true,
                    }
                    for (let i = 0; i < calendar.getEvents().length; i++) {
                        let e = calendar.getEvents()[i];
                        let eD = e.start;
                        let iD = info.date;
                        if (eD.getDate() == iD.getDate() && eD.getMonth() == iD.getMonth() && eD.getFullYear() == iD.getFullYear()) {
                            switch (e.extendedProps.timetablePeriod) {
                                case 0:
                                    period.morning = false;
                                    break;
                                case 1:
                                    period.afternoon = false;
                                    break;
                                case 2:
                                    period.evening = false;
                                    break;
                            }
                        }
                    }
                    $('.timetableBtn').hide();
                    $('.timetableBtn').attr('dateStr', info.dateStr);
                    if (period.morning)
                        $('.timetableBtn[periodNum=0]').show();
                    if (period.afternoon)
                        $('.timetableBtn[periodNum=1]').show();
                    if (period.evening)
                        $('.timetableBtn[periodNum=2]').show();

                    let editorTimetableModal = $('#editorTimetableModal');
                    editorTimetableModal.find('h1.modal-title').html(info.dateStr + ' 新增 ' + _courseVO.courseName + ' 課表');
                    editorTimetableModal.modal('show');
                },
                eventClick: function (info) {
                    _active = 'update';
                    console.log(info);
                    let period = {
                        morning: true,
                        afternoon: true,
                        evening: true,
                    }
                    for (let i = 0; i < calendar.getEvents().length; i++) {
                        let e = calendar.getEvents()[i];
                        let eD = e.start;
                        let iD = info.event.start;
                        if (eD.getDate() == iD.getDate() && eD.getMonth() == iD.getMonth() && eD.getFullYear() == iD.getFullYear()) {
                            switch (e.extendedProps.timetablePeriod) {
                                case 0:
                                    period.morning = false;
                                    break;
                                case 1:
                                    period.afternoon = false;
                                    break;
                                case 2:
                                    period.evening = false;
                                    break;
                            }
                        }
                    }
                    $('.timetableBtn').hide();
                    $('.timetableBtn').attr('dateStr', info.event.start);
                    $('.timetableBtn').attr('timetableNo', info.event.id);
                    if (period.morning)
                        $('.timetableBtn[periodNum=0]').show();
                    if (period.afternoon)
                        $('.timetableBtn[periodNum=1]').show();
                    if (period.evening)
                        $('.timetableBtn[periodNum=2]').show();
                    $('.timetableBtn.delete').show();
                    let editorTimetableModal = $('#editorTimetableModal');
                    editorTimetableModal.find('h1.modal-title').html(info.event.start.toLocaleDateString() + ' 修改 ' + _courseVO.courseName + ' 課表');
                    editorTimetableModal.modal('show');
                }
            });
            calendar.render();

            $(document).on('click', 'button.course', function (event) {
                let courseNo = this.getAttribute('courseNo');
                let banjiNo = this.getAttribute('banjiNo');
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/banji/banji.timetable',
                    data: {
                        action: 'banji_and_teacher_timetable',
                        banjiNo: banjiNo,
                        courseNo: courseNo
                    },
                    success(res) {
                        if (res != null) {
                            calendar.removeAllEvents();
                            calendar.addEventSource(res.events);
                            _courseVO = JSON.parse(res._courseVO);
                        }
                    }
                });
            });

            $(document).on('click', 'button.timetableBtn.editor', function (event) {
                if (_active == 'new') {
                    let courseNo = _courseVO.courseNo;
                    let banjiNo = _banjiNo;
                    let timetablePeriod = this.getAttribute('periodNum');
                    let timetableDate = this.getAttribute('dateStr');
                    $.ajax({
                        type: 'POST',
                        url: '<%=request.getContextPath()%>/banji/banji.timetable',
                        data: {
                            action: 'insert',
                            banjiNo: banjiNo,
                            courseNo: courseNo,
                            timetablePeriod: timetablePeriod,
                            timetableDate: timetableDate
                        },
                        success(res) {
                            if (res != null) {
                                calendar.removeAllEvents();
                                calendar.addEventSource(res.events);
                                _courseVO = JSON.parse(res._courseVO);
                                $('#editorTimetableModal').modal('hide');
                            }
                        }
                    });
                }
                else if (_active == 'update') {
                    let courseNo = _courseVO.courseNo;
                    let banjiNo = _banjiNo;
                    let timetablePeriod = this.getAttribute('periodNum');
                    let timetableNo = this.getAttribute('timetableNo');
                    $.ajax({
                        type: 'POST',
                        url: '<%=request.getContextPath()%>/banji/banji.timetable',
                        data: {
                            action: 'update_period',
                            banjiNo: banjiNo,
                            courseNo: courseNo,
                            timetablePeriod: timetablePeriod,
                            timetableNo: timetableNo
                        },
                        success(res) {
                            if (res != null) {
                                calendar.removeAllEvents();
                                calendar.addEventSource(res.events);
                                _courseVO = JSON.parse(res._courseVO);
                                $('#editorTimetableModal').modal('hide');
                            }
                        }
                    });
                }
            });

            $(document).on('click', 'button.timetableBtn.delete', function (event) {
                let courseNo = _courseVO.courseNo;
                let banjiNo = _banjiNo;
                let timetableNo = this.getAttribute('timetableNo');
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/banji/banji.timetable',
                    data: {
                        action: 'delete',
                        banjiNo: banjiNo,
                        courseNo: courseNo,
                        timetableNo: timetableNo
                    },
                    success(res) {
                        if (res != null) {
                            calendar.removeAllEvents();
                            calendar.addEventSource(res.events);
                            _courseVO = JSON.parse(res._courseVO);
                            $('#editorTimetableModal').modal('hide');
                        }
                    }
                });
            });

            function resetTimetableModal() {
                _active = 'no';
                $('.timetableBtn').hide();
                $('.timetableBtn[dateStr]').attr('dateStr', '');
                $('.timetableBtn[timetableNo]').attr('timetableNo', '');
            }

            $('#editorTimetableModal').on('hidden.bs.modal', function () {
                resetTimetableModal();
            });

            
        });
    </script>
</body>

</html>