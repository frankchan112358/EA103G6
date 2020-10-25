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
    <style>
        body.swal2-shown:not(.swal2-no-backdrop):not(.swal2-toast-shown) {
            overflow-y: visible !important;
        }

        .fc-other-month {
            background-image: none;
            background-color: none;
            background-size: none;
        }

        .fc-past {
            background-image: linear-gradient(135deg, rgba(0, 0, 0, 0.02) 25%, transparent 25%, transparent 50%, rgba(0, 0, 0, 0.02) 50%, rgba(0, 0, 0, 0.02) 75%, transparent 75%, transparent);
            background-color: #FAFCFD;
            background-size: 1rem 1rem;
        }

        .fc-before-course-end {
            background-image: linear-gradient(135deg, rgba(0, 0, 0, 0.02) 25%, transparent 25%, transparent 50%, rgba(0, 0, 0, 0.02) 50%, rgba(0, 0, 0, 0.02) 75%, transparent 75%, transparent);
            background-color: #FAFCFD;
            background-size: 1rem 1rem;
        }
    </style>
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
                            <div id="panel-2" class="panel">
                                <div class="panel-hdr">
                                    <h2>
                                        課程 <span class="fw-300">列表</span>
                                    </h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <div class="d-flex justify-content-start flex-wrap demo">
                                            <div class="btn-group mr-2" role="group">
                                                <button type="button" class="banji btn btn-lg btn-primary" courseNo="" banjiNo="${banjiVO.banjiNo}"><span class="fal fa-book mr-1"></span>目前課表</button>
                                                <c:forEach var="courseVO" items="${banjiVO.courseList}">
                                                    <button type="button" class="course btn btn-lg btn-outline-danger" courseNo="${courseVO.courseNo}" banjiNo="${banjiVO.banjiNo}"><span class="fal fa-book mr-1"></span>${courseVO.courseName}</button>
                                                </c:forEach>
                                            </div>
                                            <div class="custom-control custom-checkbox">
                                                <input type="checkbox" class="custom-control-input" id="testMode">
                                                <label class="custom-control-label" for="testMode">測試模式</label>
                                            </div>
                                        </div>
                                        <div class="desc-body">
                                            <div class="desc-name">班級:${banjiVO.banjiName}</div>
                                            <div class="desc-person">導師:${banjiVO.empVO.empName}</div>
                                            <div class="desc-status">狀態:${banjiVO.statusText}</div>
                                            <div class="desc-start">開始:${banjiVO.startDay}</div>
                                            <div class="desc-end">結束:${banjiVO.endDay}</div>
                                            <div class="desc-plan">預計時數:${banjiVO.classHours}</div>
                                            <div class="desc-now">目前時數:${banjiVO.timetableList.size()*3}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr">
                                    <h2>
                                        ${banjiVO.banjiName} <span class="fw-300">課表</span>
                                    </h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <div id="calendar"></div>
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
    <div class="modal fade" id="editorTimetableModal" role="dialog">
        <div class="modal-dialog modal-dialog-centered modal-transparent" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title text-white"></h4>
                    <button type="button" class="close" data-dismiss="modal">
                        <span><i class="fal fa-times"></i></span>
                    </button>
                </div>
                <div class="modal-body">
                    <h5 class="text-white"></h5>
                </div>
                <div class="modal-footer">
                    <c:forEach var="period" items="${timetableSvc.timetablePeriodAll}">
                        <button periodNum="${period.num}" timetableNo="" dateStr="" type="button" class="timetableBtn editor btn btn-lg btn-info">
                            <span class="fal fa-book-reader mr-1"></span>
                            ${period.text}
                        </button>
                    </c:forEach>
                    <button timetableNo="" type="button" class="timetableBtn delete btn btn-lg btn-danger">
                        <span class="fal fa-times mr-1"></span>
                        刪除
                    </button>
                    <button type="button" class="timetableBtn cancel btn btn-lg btn-dark" data-dismiss="modal">
                        <span class="fal  fa-eject mr-1"></span>
                        取消
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
        $(document).ready(function () {
            var calendarEl = document.getElementById('calendar');
            var _banjiVO = JSON.parse('${jsonData}')._banjiVO;
            var _courseVO = null;
            var _banjiNo = '${banjiVO.banjiNo}';
            var _active = 'no';
            var mode = 'user';
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
                events: JSON.parse('${jsonData}').events,
                visibleRange: {
                    start: '2020-10-10',
                    end: '2020-10-30'
                },
                viewSkeletonRender: function () {
                    $('.fc-toolbar .btn-default').addClass('btn-sm');
                    $('.fc-header-toolbar h2').addClass('fs-md');
                    $('#calendar').addClass('fc-reset-order')
                },
                dateClick: function (info) {
                    if (_courseVO == null)
                        return;
                    let d = new Date();
                    d.setDate(d.getDate() - 1);
                    if (info.date < d && mode != 'test') {
                        warningMsgPopup('過去的日期不能編輯!');
                        return;
                    }
                    _active = 'insert';
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
                    $('.timetableBtn.cancel').show();
                    let editorTimetableModal = $('#editorTimetableModal');
                    editorTimetableModal.find('h4.modal-title').html(`新增${"${_courseVO.courseName}"}課表在${"${info.dateStr}"}`);
                    if (period.morning || period.afternoon || period.evening) {
                        editorTimetableModal.find('.modal-body h5').html(`請選擇你要安排的時段`);
                        if (_courseVO.timetableSize > _courseVO.lesson)
                            editorTimetableModal.find('.modal-body h5').append(`，注意 ! 目前堂數已經超過預計堂數`);
                    }
                    else
                        editorTimetableModal.find('.modal-body h5').html(`在${"${info.dateStr}"}已無任何時段可供排課`);
                    editorTimetableModal.modal('show');
                },
                eventClick: function (info) {
                    if (_courseVO == null || info.event.extendedProps.courseNo != _courseVO.courseNo)
                        return;
                    if (info.event.start < new Date() && mode != 'test') {
                        warningMsgPopup('過去的課表不能編輯!');
                        return;
                    }
                    _active = 'update_period';
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
                    $('.timetableBtn').attr('dateStr', info.event.start.toLocaleDateString());
                    $('.timetableBtn').attr('timetableNo', info.event.id);
                    if (period.morning)
                        $('.timetableBtn[periodNum=0]').show();
                    if (period.afternoon)
                        $('.timetableBtn[periodNum=1]').show();
                    if (period.evening)
                        $('.timetableBtn[periodNum=2]').show();
                    $('.timetableBtn.delete').show();
                    $('.timetableBtn.cancel').show();
                    let editorTimetableModal = $('#editorTimetableModal');
                    editorTimetableModal.find('h4.modal-title').html(`調整${"${info.event.start.toLocaleDateString()}"}${"${info.event.extendedProps.periodText}"}的${"${_courseVO.courseName}"}課表`);
                    editorTimetableModal.find('.modal-body h5').html(`請進行你的調整，注意 ! 課表刪除後無法復原`);
                    editorTimetableModal.modal('show');
                }, eventDragStart: function (info) {

                }, eventDragStop: function (info) {

                }, eventDrop: function (info) {
                    let d = new Date();
                    d.setHours(0);
                    d.setMinutes(0);
                    d.setSeconds(0);
                    d.setMinutes(0);
                    if (info.event.start < d && mode != 'test') {
                        warningMsgPopup('不能調課到過去的日期!');
                        info.revert();
                        return;
                    }
                    _active = 'update_date';
                    let period = {
                        morning: true,
                        afternoon: true,
                        evening: true,
                    }
                    for (let i = 0; i < calendar.getEvents().length; i++) {
                        let e = calendar.getEvents()[i];
                        let eD = e.start;
                        let iD = info.event.start;
                        if (e.id != info.event.id) {
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
                    }
                    $('.timetableBtn').hide();
                    $('.timetableBtn').attr('dateStr', info.event.start.toLocaleDateString());
                    $('.timetableBtn').attr('timetableNo', info.event.id);
                    if (period.morning)
                        $('.timetableBtn[periodNum=0]').show();
                    if (period.afternoon)
                        $('.timetableBtn[periodNum=1]').show();
                    if (period.evening)
                        $('.timetableBtn[periodNum=2]').show();
                    $('.timetableBtn.cancel').show();
                    let editorTimetableModal = $('#editorTimetableModal');
                    editorTimetableModal.find('h4.modal-title').html(`把${"${info.oldEvent.start.toLocaleDateString()}"}${"${info.event.extendedProps.periodText}"}的${"${_courseVO.courseName}"}調課到${"${info.event.start.toLocaleDateString()}"}`);
                    if (period.morning || period.afternoon || period.evening)
                        editorTimetableModal.find('.modal-body h5').html(`請選擇你要安排的時段`);
                    else
                        editorTimetableModal.find('.modal-body h5').html(`在${"${info.event.start.toLocaleDateString()}"}已無任何時段可供排課`);
                    editorTimetableModal.modal('show');
                },
                viewSkeletonRender(info){
                    console.log('viewSkeletonRender');
                },
                viewSkeletonDestroy(info){
                    console.log('viewSkeletonDestroy');
                },
                datesRender(info){
                    console.log('datesRender');
                    renderFcBeforeCourseEnd();
                },
                datesDestroy(info){
                    console.log('datesDestroy');
                },
            });
            calendar.render();

            $(document).on('click', 'button.course', function (event) {
                let _this = this;
                let courseNo = this.getAttribute('courseNo');
                let banjiNo = _banjiNo;
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
                            courseDescChange(_courseVO);
                            courseBtnChange($(_this), courseNo);
                            renderFcBeforeCourseEnd();
                        }
                    }
                });
            });

            $(document).on('click', 'button.banji', function (event) {
                let _this = this;
                let banjiNo = _banjiNo;
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/banji/banji.timetable',
                    data: {
                        action: 'now',
                        banjiNo: banjiNo
                    },
                    success(res) {
                        if (res != null) {
                            calendar.removeAllEvents();
                            calendar.addEventSource(res.events);
                            _courseVO = null;
                            _banjiVO = res._banjiVO;
                            courseDescChange(_banjiVO);
                            courseBtnChange($(_this), '');
                            renderFcBeforeCourseEnd();
                        }
                    }
                });
            });

            function courseBtnChange(btn, courseNo) {
                if (btn.hasClass('banji')) {
                    $('button.banji').addClass('btn-primary').removeClass('btn-outline-primary');
                    $('button.course.btn-danger').addClass('btn-outline-danger').removeClass('btn-danger');
                } else {
                    $('button.banji.btn-primary').addClass('btn-outline-primary').removeClass('btn-primary');
                    $('button.course.btn-danger').addClass('btn-outline-danger').removeClass('btn-danger');
                    $(`button.course[courseNo=${"${courseNo}"}]`).addClass('btn-danger').removeClass('btn-outline-danger');
                }
            }

            function courseDescChange(obj) {
                let desc = $('.desc-body');
                if (_courseVO != null) {
                    desc.find('.desc-name').html(`${"課程 : ${obj.courseName}"}`);
                    desc.find('.desc-person').html(`${"講師 : ${obj.teacherName}"}`);
                    desc.find('.desc-status').html(`${"狀態 : ${obj.statusText}"}`);
                    desc.find('.desc-start').html(`${"開始 : ${obj.startDate}"}`);
                    desc.find('.desc-end').html(`${"結束 : ${obj.endDate}"}`);
                    desc.find('.desc-plan').html(`${"預計堂數 : ${obj.lesson}"}`);
                    desc.find('.desc-now').html(`${"目前堂數 : ${obj.timetableSize}"}`);
                } else {
                    desc.find('.desc-name').html(`${"班級 : ${obj.banjiName}"}`);
                    desc.find('.desc-person').html(`${"導師 : ${obj.empName}"}`);
                    desc.find('.desc-status').html(`${"狀態 : ${obj.statusText}"}`);
                    desc.find('.desc-start').html(`${"開始 : ${obj.startDay}"}`);
                    desc.find('.desc-end').html(`${"結束 : ${obj.endDay}"}`);
                    desc.find('.desc-plan').html(`${"預計時數 : ${obj.classHours}"}`);
                    desc.find('.desc-now').html(`${"目前時數 : ${obj.timetableSize*3}"}`);
                }
            }

            function warningMsgPopup(msg) {
                Swal.fire(
                    {
                        position: "top-end",
                        title: msg,
                        showConfirmButton: false,
                        timer: 1500
                    });
            }

            $('#testMode').change(function (event) {
                if ($(this).prop('checked')) {
                    Swal.fire("切換測試模式", "沒有任何防呆機制，請小心使用，尤其注意課程開始與結束時間，以免塞入不符合的資料", "warning");
                    mode = 'test';
                } else {
                    mode = 'user';
                }
                uploadTimetable();
            });

            function renderFcBeforeCourseEnd() {
                $('.fc-before-course-end').removeClass('fc-before-course-end');
                if (_courseVO != null) {
                    let array = $('.fc-future[data-date]');
                    for (let index = 0; index < array.length; index++) {
                        let element = array[index];
                        let end = new Date(_courseVO.endDate);
                        let date = new Date(element.getAttribute('data-date'));
                        if (date > end)
                            $(element).addClass('fc-before-course-end');
                    }
                }
            }

            $('.fc-prev-button').click(function () {
                renderFcBeforeCourseEnd();
            });

            $('.fc-next-button').click(function () {
                renderFcBeforeCourseEnd();
            });

            $(document).on('click', 'button.timetableBtn.cancel', function (event) {

            });

            $(document).on('click', 'button.timetableBtn.editor', function (event) {
                if (_active == 'insert') {
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
                            timetableDate: timetableDate,
                            mode: mode
                        },
                        success(res) {
                            if (res == 'ok') {
                            } else {
                                warningMsgPopup(res);
                            }
                        },
                        complete() {
                            $('#editorTimetableModal').modal('hide');
                        }
                    });
                }
                else if (_active == 'update_period') {
                    let courseNo = _courseVO.courseNo;
                    let banjiNo = _banjiNo;
                    let timetablePeriod = this.getAttribute('periodNum');
                    let timetableNo = this.getAttribute('timetableNo');
                    let timetableDate = this.getAttribute('dateStr');
                    $.ajax({
                        type: 'POST',
                        url: '<%=request.getContextPath()%>/banji/banji.timetable',
                        data: {
                            action: 'update_period',
                            banjiNo: banjiNo,
                            courseNo: courseNo,
                            timetablePeriod: timetablePeriod,
                            timetableNo: timetableNo,
                            timetableDate: timetableDate,
                            mode: mode
                        },
                        success(res) {
                            if (res == 'ok') {
                            } else {
                                warningMsgPopup(res);
                            }
                        },
                        complete() {
                            $('#editorTimetableModal').modal('hide');
                        }
                    });
                }
                else if (_active == 'update_date') {
                    let courseNo = _courseVO.courseNo;
                    let banjiNo = _banjiNo;
                    let timetablePeriod = this.getAttribute('periodNum');
                    let timetableNo = this.getAttribute('timetableNo');
                    let timetableDate = this.getAttribute('dateStr');
                    $.ajax({
                        type: 'POST',
                        url: '<%=request.getContextPath()%>/banji/banji.timetable',
                        data: {
                            action: 'update_date',
                            banjiNo: banjiNo,
                            courseNo: courseNo,
                            timetablePeriod: timetablePeriod,
                            timetableNo: timetableNo,
                            timetableDate: timetableDate,
                            mode: mode
                        },
                        success(res) {
                            if (res == 'ok') {
                            } else {
                                warningMsgPopup(res);
                            }
                        },
                        complete() {
                            $('#editorTimetableModal').modal('hide');
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
                        if (res == 'ok') {
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
                let editorTimetableModal = $('#editorTimetableModal');
                editorTimetableModal.find('h4.modal-title').html('');
                editorTimetableModal.find('.modal-body h5').html('');
            }

            $('#editorTimetableModal').on('hide.bs.modal', function () {
                uploadTimetable();
            });

            function uploadTimetable() {
                let courseNo = _courseVO.courseNo;
                let banjiNo = _banjiNo;
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/banji/banji.timetable',
                    data: {
                        action: 'banji_and_teacher_timetable',
                        banjiNo: banjiNo,
                        courseNo: courseNo,
                        mode: mode
                    },
                    success(res) {
                        if (res != null) {
                            calendar.removeAllEvents();
                            calendar.addEventSource(res.events);
                            _courseVO = JSON.parse(res._courseVO);
                            courseDescChange(_courseVO);
                            resetTimetableModal();
                            renderFcBeforeCourseEnd();
                        }
                    }
                });
            }

        });
    </script>
</body>

</html>