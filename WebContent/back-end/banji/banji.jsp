<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />
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
                        <li class="breadcrumb-item">${banjiVO.banjiName}</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-users-class'></i> ${banjiVO.banjiName}
                        </h1>
                        <div class="btn-group hidden-xl-up">
                            <button type="button" class="btn btn-secondary dropdown-toggle btn-info" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fal fa-toolbox"></i> 班級管理功能
                            </button>
                            <div class="dropdown-menu dropdown-menu-right">
                                <button id="gotoUpdateBanji2" class="dropdown-item" type="button">班級設定</button>
                                <button id="gotoBanjiPost2" class="dropdown-item" type="button">公告管理</button>
                                <button id="gotoTimetable2" class="dropdown-item" type="button">課表管理</button>
                                <button id="gotoLeave2" class="dropdown-item" type="button">請假管理</button>
                                <button id="gotoForumTopic2" class="dropdown-item" type="button">討論區主題</button>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="hidden-lg-down col-xl-3 col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div id="panel-0" class="panel" data-panel-sortable data-panel-fullscreen data-panel-close data-panel-locked data-panel-refresh data-panel-reset data-panel-color>
                                <div class="panel-hdr bg-info-500">
                                    <h2>班級管理功能</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content p-0">
                                        <ul id="js_nested_list" class="nav-menu nav-menu-reset nav-menu-compact mb-sm-4 mb-md-0 rounded" data-nav-accordion="true">
                                            <li>
                                                <a id="gotoUpdateBanji" href="javascript:void(0)">
                                                    <span class="text-info">班級設定</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a id="gotoBanjiPost" href="javascript:void(0)">
                                                    <span class="text-info">公告管理</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a id="gotoTimetable" href="javascript:void(0)">
                                                    <span class="text-info">課表管理</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a id="gotoLeave" href="javascript:void(0)">
                                                    <span class="text-info">請假管理</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a id="gotoForumTopic" href="javascript:void(0)">
                                                    <span class="text-info">討論區主題</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-9 col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-info-300 bg-info-gradient">
                                    <h2></h2>
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
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %>
    <script>
        'use strict';
        $(document).ready(function () {
            var calendarEl = document.getElementById('calendar');

            var calendar = new FullCalendar.Calendar(calendarEl, {
                plugins: ['dayGrid', 'list', 'timeGrid', 'interaction', 'bootstrap'],
                themeSystem: 'bootstrap',
                editable: false,
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
                events: '<%=request.getContextPath()%>/banji/banji.calendar?banjiNo=${banjiVO.banjiNo}',
                viewSkeletonRender: function () {
                    $('.fc-toolbar .btn-default').addClass('btn-sm');
                    $('.fc-header-toolbar h2').addClass('fs-md');
                    $('#calendar').addClass('fc-reset-order')
                }
            });
            calendar.render();

            $('#gotoUpdateBanji,#gotoUpdateBanji2').click(function () {
                let _this = this;
                let banjiNo = this.getAttribute('banjiNo');
                let myForm = banjiGotoFrom('<%=request.getContextPath()%>/banji/banji.do');
                document.body.appendChild(myForm);
                myForm.append(banjiGotoInput('hidden', 'action', 'getOne_For_Update'));
                myForm.append(banjiGotoInput('hidden', 'banjiNo', '${banjiNo}'));
                myForm.submit();
            });

            $('#gotoBanjiPost,#gotoBanjiPost2').click(function () {
                let _this = this;
                let banjiNo = this.getAttribute('banjiNo');
                let myForm = banjiGotoFrom('<%=request.getContextPath()%>/banji/banji.banjipost');
                document.body.appendChild(myForm);
                myForm.submit();
            });

            $('#gotoTimetable,#gotoTimetable2').click(function () {
                let _this = this;
                let banjiNo = this.getAttribute('banjiNo');
                let myForm = banjiGotoFrom('<%=request.getContextPath()%>/banji/banji.timetable');
                document.body.appendChild(myForm);
                myForm.submit();
            });

            $('#gotoLeave,#gotoLeave2').click(function () {
                let _this = this;
                let banjiNo = this.getAttribute('banjiNo');
                let myForm = banjiGotoFrom('<%=request.getContextPath()%>/banji/banji.leave');
                document.body.appendChild(myForm);
                myForm.submit();
            });
        
            $('#gotoForumTopic,#gotoForumTopic2').click(function () {
                let _this = this;
                let banjiNo = this.getAttribute('banjiNo');
                let myForm = banjiGotoFrom('<%=request.getContextPath()%>/banji/banji.forumtopic');
                document.body.appendChild(myForm);
                myForm.append(banjiGotoInput('hidden', 'banjiNo', '${banjiNo}'));
                myForm.submit();
            });

            function banjiGotoFrom(url) {
                let myForm = document.createElement('form');
                myForm.action = url;
                myForm.method = 'POST';
                return myForm;
            }

            function banjiGotoInput(type, name, value) {
                let banjiInput = document.createElement('input');
                banjiInput.type = type;
                banjiInput.name = name;
                banjiInput.value = value;
                return banjiInput;
            }
        });
    </script>
</body>

</html>