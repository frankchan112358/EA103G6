<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>

<% String permission =(String)request.getAttribute("permission"); //浩偉權限通知 %>

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
                        <div class="col-xl-4 col-lg-12 col-md-12">
                            <div class="row">
                                <div class="col-xl-12 col-lg-6 col-md-12">
                                    <h2 class="text-black"><i class="fal fa-stopwatch"></i> 結訓倒數</h2>
                                    <div class="demo js-progress-animated">
                                        <div class="progress progress-xl">
                                            <div class="progress-bar progress-bar-striped bg-primary-700 progress-bar-animated text-white" role="progressbar" style="width: 60%" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">倒數87天</div>
                                        </div>
                                    </div>
                                    <div id="panel-1" class="panel">
                                        <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                            <h2 class="text-white">影片</h2>
                                        </div>
                                        <div class="panel-container show">
                                            <div class="panel-content p-3 bg-gray-900">
                                                <video style="width: 100%;" src="<%=request.getContextPath() %>/resources/videos/demo001.mp4"  autobuffer autoplay loop controls muted></video>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-12 col-lg-6 col-md-12">
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
                        </div>
                        <div class="col-xl-8 col-lg-12 col-md-12">
                            <div id="panel-3" class="panel">
                                <div class="panel-hdr bg-danger-700 bg-danger-gradient ">
                                    <h2 class="text-white">班級公告</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content p-0">
                                        <ul class="list-group">
                                            <li class="list-group-item">
                                                <a href="" title="夜輔未簽到同學請補簽">
                                                    <span class="text-danger">夜輔未簽到同學請補簽...etc</span>
                                                </a>
                                            </li>
                                            <li class="list-group-item">
                                                <a href="" title="上課同學請繳交作業">
                                                    <span class="text-danger">未繳交作業的同學請趕緊上傳...etc</span>
                                                </a>
                                            </li>
                                            <li class="list-group-item">
                                                <a href="" title="上課時請到櫃檯拿書">
                                                    <span class="text-danger">請有空同學至櫃檯拿書...etc</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xl-6">
                                    <div id="panel-4" class="panel">
                                        <div class="panel-hdr bg-primary-800 bg-warning-gradient ">
                                            <h2 class="text-white">學習紀錄</h2>
                                        </div>
                                        <div class="panel-container show">
                                            <div class="panel-content p-0">
                                                <img style="width:100%;" src="<%=request.getContextPath()%>/images/test/學習紀錄.png"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-6">
                                    <div id="panel-5" class="panel">
                                        <div class="panel-hdr bg-primary-800 bg-info-gradient ">
                                            <h2 class="text-white">班級討論區</h2>
                                        </div>
                                        <div class="panel-container show">
                                            <div class="panel-content p-0">
                                                <ul class="list-group">
                                                    <li class="list-group-item">
                                                        <a href="" title="Hello Java">
                                                            <span class="text-secondary">Hello Java</span>
                                                        </a>
                                                    </li>
                                                    <li class="list-group-item">
                                                        <a href="" title="今日約跑步">
                                                            <span class="text-secondary">今日約跑步</span>
                                                        </a>
                                                    </li>
                                                    <li class="list-group-item">
                                                        <a href="" title="下課吃竹香">
                                                            <span class="text-secondary">下課吃竹香</span>
                                                        </a>
                                                    </li>
                                        		</ul>
                                            </div>
                                        </div>
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
    <%@ include file="/front-end/index/index_js.jsp" %>
	
	<script>
    //浩偉新增權限通知
		<% if("forbid".equals(permission)){ %>
		 Swal.fire("無權限", "請向管理者確認權限", "error");
        
		<%} %>
	</script>

</body>
</html>