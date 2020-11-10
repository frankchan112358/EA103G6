<%@page import="com.forumpost.model.*"%>
<%@page import="com.banji.model.BanjiService"%>
<%@page import="com.video.model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ page import="com.banjipost.model.*"%>
<%@ page import="java.util.*"%>
<% String permission =(String)request.getAttribute("permission"); //浩偉權限通知 %>
<%
    BanjiPostService banjiPostSvc = new BanjiPostService();
    List<BanjiPostVO> list = banjiPostSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<%
VideoVO videoVO = null;
for (VideoVO item : new VideoService().getAll()) {
	if(item.getTimetableVO().getCourseVO().getBanjiNo().equals(studentVO.getBanjiNo())){
		if(videoVO == null){
			videoVO = item;
		}else if(Integer.parseInt(item.getVideoNo()) > Integer.parseInt(videoVO.getVideoNo())){
			videoVO = item;
		}
	}
}
pageContext.setAttribute("videoVO",videoVO);
%>
<%
List<ForumPostVO> forumPostList = new ArrayList<ForumPostVO>();
for (ForumPostVO item : new ForumPostService().getAll()) {
	if(item.getForumTopicVO().getBanjiNo().equals(studentVO.getBanjiNo())){
		if(forumPostList.size()<5)
			forumPostList.add(item);		
	}
}
pageContext.setAttribute("forumPostList", forumPostList);
%>
<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />

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
                        <li class="breadcrumb-item">前台首頁</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-chart-area'></i> 前台首頁
                        </h1>
                    </div>
                    <div class="row">
                        <div class="col-xl-4 col-lg-4 col-md-12">
                            <h2 class="text-black"><i class="fal fa-stopwatch"></i> 結訓倒數</h2>
                            <div class="demo js-progress-animated">
                                <div class="progress progress-xl">
                                    <div id="banjiProgress" class="progress-bar progress-bar-striped bg-primary-700 progress-bar-animated text-white" role="progressbar" style="width: 60%"></div>
                                </div>
                            </div>
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">最新上架的教學影片</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content p-3 bg-gray-900">
                                    <c:if test="${videoVO == null }">
                                    	<video id="newVideo" style="width: 100%;" src="" autobuffer autoplay loop controls muted></video>
                                    </c:if>
                                    <c:if test="${videoVO != null }">
                                    	<video id="newVideo" style="width: 100%;" src="<%=request.getContextPath()%>/videos/${videoVO.videoNo}.mp4" autobuffer autoplay loop controls muted></video>
                                    </c:if>                                    	
                                    </div>
                                </div>
                            </div>
                            <div id="panel-3" class="panel">
                                <div class="panel-hdr bg-danger-700 bg-danger-gradient ">
                                    <h2 class="text-white">班級公告</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content p-0">
                                        <c:forEach var="banjiPostVO" items="${list}">
                                            <ul class="list-group">
                                                <li class="list-group-item">
                                                    <a banjiPostNo="${banjiPostVO.banjiPostNo}" class="banjiPost" href="javascript:void(0)" title="班級公告標題">
                                                        標題:<span class="text-danger">${banjiPostVO.title}</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <div id="panel-5" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-info-gradient ">
                                    <h2 class="text-white">班級討論區最新5則貼文</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content p-0">
                                        <ul class="list-group">
                                            <c:forEach var="forumPostVO" items="${forumPostList}">
                                                <li class="list-group-item">
                                                    <a href="javascript:void(0)" class="forumPost" forumPostNo="${forumPostVO.forumPostNo}">
                                                        <span class="text-secondary">${forumPostVO.title}</span>
                                                    </a>
                                                </li>                                                
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-8 col-lg-8 col-md-12">
                            <div id="panel-2" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-fusion-gradient ">
                                    <h2 class="text-white">課表</h2>
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
                <%@ include file="/front-end/template/footer.jsp" %>
            </div>
        </div>
    </div>
    <div class="modal fade example-modal-centered-transparent" id="banjiPostModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-xl modal-dialog modal-dialog-centered modal-transparent" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title text-white">班級公告詳情</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">
							<i class="fal fa-times"></i>
						</span>
					</button>
				</div>
				<div id="banjiPostContent" class="modal-body bg-white"></div>
			</div>
		</div>
	</div>
    <%@ include file="/front-end/template/quick_menu.jsp" %>
    <%@ include file="/front-end/template/messager.jsp" %>
    <%@ include file="/front-end/template/basic_js.jsp" %>

    <script>
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
                events: '<%=request.getContextPath()%>/banji/banji.calendar?banjiNo=${studentVO.banjiVO.banjiNo}',
                viewSkeletonRender: function () {
                    $('.fc-toolbar .btn-default').addClass('btn-sm');
                    $('.fc-header-toolbar h2').addClass('fs-md');
                    $('#calendar').addClass('fc-reset-order')
                }
            });
            calendar.render();

            function calcDays() {
                let banjiStartDay = moment(new Date('${studentVO.banjiVO.startDay}'));
                let banjiEndDay = moment(new Date('${studentVO.banjiVO.endDay}'));
                let nowDay = moment(new Date());
                if (nowDay < banjiStartDay) {
                    return '尚未開訓';
                }
                if (nowDay > banjiEndDay) {
                    return '恭喜!已經結訓';
                }
                let full = banjiEndDay.diff(banjiStartDay, 'days');
                let now = nowDay.diff(banjiStartDay, 'days');
                let diff = full - now;
                return `倒數${'${diff}'}天`;
            }

            function calcProgress() {
                let banjiStartDay = moment(new Date('${studentVO.banjiVO.startDay}'));
                let banjiEndDay = moment(new Date('${studentVO.banjiVO.endDay}'));
                let nowDay = moment(new Date());
                let bjProg = $('#banjiProgress');
                if (nowDay < banjiStartDay) {
                    bjProg.css('width', '0%');
                    return;
                }
                if (nowDay > banjiEndDay) {
                    bjProg.css('width', '100%');
                    return;
                }
                let full = banjiEndDay.diff(banjiStartDay, 'days');
                let now = nowDay.diff(banjiStartDay, 'days');
                let percentage = Math.round(now / full * 100);
                bjProg.css('width', `${'${percentage}%'}`)
                return;
            }
            $('#banjiProgress').text(calcDays());
            calcProgress();

            $(document).on('click', 'a.forumPost', function (e) {
                let _this = $(this);
                let forumPostNo = _this.attr('forumPostNo');
                let myForm = createMyFrom('<%=request.getContextPath()%>/forum/forum.do');
                document.body.appendChild(myForm);
                myForm.append(createFormInput('hidden', 'action', 'forumPostHomePage'));
                myForm.append(createFormInput('hidden', 'forumPostNo', forumPostNo));
                myForm.submit();
            });

            function createMyFrom(url) {
                let myForm = document.createElement('form');
                myForm.action = url;
                myForm.method = 'POST';
                return myForm;
            }

            function createFormInput(type, name, value) {
                let formInput = document.createElement('input');
                formInput.type = type;
                formInput.name = name;
                formInput.value = value;
                return formInput;
            }

            $(document).on('click', 'a.banjiPost', function (event) {
				let banjiPostNo = this.getAttribute('banjiPostNo');
				$.ajax({
					type: 'POST',
					url: '<%=request.getContextPath()%>/banjiPost/banjiPost.do',
					data: {
						action: 'readBanjiPostContent',
						banjiPostNo: banjiPostNo
					},
					success(res) {
						if (res != null) {
							$('#banjiPostContent').html('');
							$('#banjiPostContent').html(res);
							$('#banjiPostModal').modal('show');
						}
					}
				});
			});
        });
    //浩偉新增權限通知
		<% if ("forbid".equals(permission)) { %>
            Swal.fire("無權限", "請向管理者確認權限", "error");
        
		<%} %>
    </script>

</body>

</html>