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
    <div class="modal fade" id="addTimetableModal" tabindex="-1" role="dialog">
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

                            <button active="update" periodNum="${period.num}" type="button" class="btn btn-lg btn-outline-info">
                                <span class="fal fa-book-reader mr-1"></span>
                                ${period.text}
                            </button>

                        </c:forEach>

                        <button active="delete" type="button" class="btn btn-lg btn-outline-danger">
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
                    console.log(period);
                    $('[periodNum]').hide();
                    if (period.morning)
                        $('[periodNum=0]').show();
                    if (period.afternoon)
                        $('[periodNum=1]').show();
                    if (period.evening)
                        $('[periodNum=2]').show();

                    let addTimetableModal = $('#addTimetableModal');
                    addTimetableModal.find('h1.modal-title').html(info.dateStr + ' 新增 ' + _courseVO.courseName + ' 課表');
                    addTimetableModal.modal('show');
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


        });
    </script>
</body>

</html>