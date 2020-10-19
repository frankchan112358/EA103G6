<%@page import="com.leave.model.LeaveVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="leaveSvc" scope="page" class="com.leave.model.LeaveService" />
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="timetableSvc" scope="page" class="com.timetable.model.TimetableService" />
<%
pageContext.setAttribute("list",leaveSvc.getLeaveWithStudent(studentVO.getStudentNo()));
%>

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/front-end/template/head.jsp" %>
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/miscellaneous/fullcalendar/fullcalendar.bundle.css">
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
                                            <tbody>
                                                <c:forEach var="leaveVO" items="${list }">
                                                    <tr>
                                                        <td>${leaveVO.timetableVO.timetableDate}</td>
                                                        <td>${leaveVO.timetableVO.periodText}</td>
                                                        <td>${leaveVO.timetableVO.courseVO.courseName}</td>
                                                        <td>${leaveVO.typeText}</td>
                                                        <td>${leaveVO.statusText}</td>
                                                        <td></td>
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
                        <input type="hidden" name="studentNo" value="${studentVO.studentNo}" />
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
                                <select class="custom-select" id="type">
                                    <c:forEach var="type" items="${leaveSvc.getLeaveTypeAll()}">
                                        <option value="${type.num}">${type.text}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="description">描述事由</label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class='fal fa-file-edit'></i></span>
                                </div>
                                <textarea id="description" name="description" class="form-control"></textarea>
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
    <%@ include file="/front-end/template/quick_menu.jsp" %>
    <%@ include file="/front-end/template/messager.jsp" %>
    <%@ include file="/front-end/template/basic_js.jsp" %>
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/miscellaneous/fullcalendar/fullcalendar.bundle.js"></script>
    <script>
        'use strict';
        // 把java取值得結果，先放入var變數
        var ContextPath = '<%=request.getContextPath()%>';
        $(document).ready(function () {
            var leaveForm = document.getElementById('leaveForm');

            $('#leaveTable').dataTable({
                responsive: true,
                language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' }
            });

            var calendarEl = document.getElementById('calendar');

            var calendar = new FullCalendar.Calendar(calendarEl, {
                plugins: ['dayGrid'],
                themeSystem: 'bootstrap',
                editable: false,
                displayEventTime: false,
                locale: 'zh-tw',
                events: [
                    <c:forEach var="timetableVO" items="${leaveSvc.getTimetableEvents(studentVO.studentNo)}">
                        {
                            id:'${timetableVO.timetableNo}',
                            title: '${timetableVO.periodEnum.text},${timetableVO.courseVO.courseName}',
                            start: '${timetableVO.timetableDate}T${timetableVO.periodEnum.start}',
                            borderColor:'white',
                            extendedProps:{'timetableInfo':'${timetableVO.timetableDate},${timetableVO.periodEnum.text},${timetableVO.courseVO.courseName}'}
                        },
                    </c:forEach >
                ],
                eventClick: function (info) {
                    calendar.getEvents().forEach(e => {
                        e.setProp('borderColor','white');
                    });
                    info.event.setProp('borderColor','red');
                    $('#timetableNo').val(info.event.id);
                    $('#timetableInfo').html(info.event.extendedProps.timetableInfo);
                }
            });

            $('#leaveEditor').on('shown.bs.modal', function () {
                calendar.render();
            });

            $('#save').click(function(){
                $.ajax({
                    type:'post',
                    url:'<%=request.getContextPath()%>/leave/add',
                    data:$(formLeave).serialize(),
                    success(res){
                        if(res=='ok'){
                            $('#editorLeave').modal('hide');
                        }
                    },
                    error(err){
                        console.log(err);
                    }
                })
            });
        });
    </script>
</body>

</html>