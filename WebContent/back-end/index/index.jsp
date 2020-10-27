<%@page import="com.user.model.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp" %>

<% String permission =(String)request.getAttribute("permission"); //浩偉權限通知 %>
<!DOCTYPE html>
<html>

<head>
    <%@include file="/back-end/template/head.jsp" %>
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
                        <li class="breadcrumb-item">後台首頁</li>
                    </ol>
                    <div class="subheader" style="display: block;">
                        <h1 class="subheader-title mb-4">
                            <i class='subheader-icon fal fa-chart-area'></i> 後台首頁
                        </h1>
                        <div class="bg-danger-gradient bg-info-300 card p-2 pl-5 pr-5" style="display: inline-block;">
                            <c:if test="${userVO.photo != null}" var="condition" scope="page">
                                <img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${userVO.userNo}" class="profile-image rounded-circle">
                            </c:if>
                            <c:if test="${condition == false}">
                                <img src="<%=request.getContextPath() %>/images/noPicture.png" class="profile-image rounded-circle">
                            </c:if>
                            <div class="info-card-text ml-3">
                                <span class="fs-xxl fw-500">${userVO.name}</span>
                                <span class="float-lg-right fs-xl">導師</span>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xl-6 col-lg-12">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-info-300 bg-info-gradient">
                                    <h2><span class="fw-300">班級課表</span></h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="btn-group m-2" role="group">
                                        <c:forEach var="banjiVO" items="${empVO.banjiList}">
                                            <button type="button" class="course btn btn-lg btn-outline-primary" banjiNo="${banjiVO.banjiNo}">
                                                <span class="fal fa-book mr-1"></span>${banjiVO.banjiName}
                                            </button>
                                        </c:forEach>
                                    </div>
                                    <div class="panel-content">
                                        <div id="calendar"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-12">
                            <div id="panel-2" class="panel">
                                <div class="panel-hdr bg-success-300 bg-info-gradient">
                                    <h2 class="text-white">待審核假單</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content p-0">
                                        <table id="leaveTable" class="table table-bordered table-hover table-striped w-100">
                                            <thead>
                                                <tr>
                                                    <th>班級</th>
                                                    <th>學生</th>
                                                    <th>日期</th>
                                                    <th>時段</th>
                                                    <th>假別</th>
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
                    <h1 class="modal-title">審核請假單</h1>
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
                    <button id="rejectBtn" leaveNo="" type="button" class="btn btn-success" id="reject">
                        <span class="fal fa-edit mr-1"></span>
                        <span>拒絕</span>
                    </button>
                    <button id="passBtn" leaveNo="" type="button" class="btn btn-danger" id="pass">
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
    //浩偉新增權限通知
    <% if ("forbid".equals(permission)) { %>
            Swal.fire("無權限", "請向管理者確認權限", "error");
    <%} %>

            $(document).ready(function () {
                var imgSrc = '<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=';
                var noImgSrc = '<%=request.getContextPath() %>/images/noPicture.png';
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
                    events: [],
                    viewSkeletonRender: function () {
                        $('.fc-toolbar .btn-default').addClass('btn-sm');
                        $('.fc-header-toolbar h2').addClass('fs-md');
                        $('#calendar').addClass('fc-reset-order')
                    }
                });
                calendar.render();

                var columnSet = [
                    {
                        title: "班級",
                        id: "banjiName",
                        data: "banjiName",
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
                        title: "假別",
                        id: "typeText",
                        data: "typeText",
                        type: "text"
                    },
                    {
                        title: "操作",
                        id: "action",
                        data: "action",
                        render(data, type, row, meta) {
                            html = `<button type="button" class="decide btn btn-info" leaveNo="${"${row.action.id}"}">審核</button>`;
                            return html;
                        }
                    }];
                var leaveTable = $('#leaveTable').DataTable({
                    responsive: true,
                    language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' },
                    columns: columnSet,
                    searching: false,
                    paging: false,
                    info: false,
                    ajax: {
                        url: '<%=request.getContextPath()%>/leave/leave.manage',
                        type: 'GET',
                        async: true,
                        cache: false,
                        data: {
                            action: 'datatable_review',
                            empNo: '${empVO.empNo}'
                        }
                    }
                });

                $(document).on('click', 'button.decide', function (event) {
                    let leaveNo = this.getAttribute('leaveNo');
                    $.ajax({
                        type: 'POST',
                        url: '<%=request.getContextPath()%>/leave/leave.manage',
                        data: {
                            action: 'decide_index',
                            leaveNo: leaveNo
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
                }

                $('#passBtn').click(function (event) {
                    let leaveNo = this.getAttribute('leaveNo');
                    $.ajax({
                        type: 'POST',
                        url: '<%=request.getContextPath()%>/leave/leave.manage',
                        data: {
                            action: 'pass',
                            leaveNo: leaveNo
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
                        url: '<%=request.getContextPath()%>/leave/leave.manage',
                        data: {
                            action: 'reject',
                            leaveNo: leaveNo
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