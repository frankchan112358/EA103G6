<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.video.model.*"%>
<%@ page import="com.timetable.model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="videoSvc" scope="page" class="com.video.model.VideoService" />
<jsp:useBean id="timetableSvc" scope="page" class="com.timetable.model.TimetableService" />
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<%
List<VideoVO> videoList = new ArrayList<VideoVO>();
for(VideoVO item : videoSvc.getAll()){
	videoList.add(item);
}
pageContext.setAttribute("videoList", videoList);
VideoVO videoVO = (VideoVO) request.getAttribute("videoVO");

%>
<!DOCTYPE html>
<html>

<head>
    <%@ include file="/front-end/template/head.jsp" %>
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/formplugins/summernote/summernote.css">
    <style>
        .slide-bar {
            position: absolute;
            height:100%;
            width: 30%;
            left:0px;
            top:0px;
            bottom:0px;
            background-color: #c3dbaa;
        	overflow-y: scroll;
        }
        .player {
            position: absolute;
            height:100%;
            width: 70%;
            right:0px;
            top:0px;
            bottom:0px;
            background-color: black;
        }
        .panel-content {
            height:500px;
        }
        .in-sb {
        	border-bottom:5px #9EDF56 double;
        	height:20%;
            font-size: medium;
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
                        <li class="breadcrumb-item"> (#塞href)我的課程 / 教學影片</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class="fas fa-video" style="color: yellowgreen;">  教學影片</i>
                        </h1>
                    </div>
                     <div class="row">
                        <div class="col col-xl-12">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">#課程名稱</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <div class="slide-bar">
                                        	<c:forEach var="videoVO" items="${videoList}">
                                        		<div class="in-sb">
                                                    <input type="hidden" name="videoNo" value="${videoVO.videoNo}">
                                                    ${videoVO.videoNo}<br>
                                                    <input type="hidden" name="timetableNo" value="${videoVO.timetableNo}">
                                                    ${videoVO.timetableNo}<br>
                                                    <input type="hidden" name="videoName" value="${videoVO.videoName}">
                                        			${videoVO.videoName}<br>
                                        			<div class="in-sb-log">
                                                        <i class="fas fa-pencil-alt" style="color: green;">
                                                        </i>#要放教學日誌連結orz</div>
                                        		</div>
                                        	</c:forEach>
                                        	</div>
                                        <div class="player">
                                        	<video loop="true" autoplay="autoplay"  muted="true" >
                                                
                                            </video>
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
    <div class="modal fade" id="democratEditor" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Modal
                        <small class="m-0 text-muted">描述</small>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"><i class="fal fa-times"></i></span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="democratForm" class="needs-validation" novalidate>
                        <div class="form-group">
                            ...
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
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
    <script>
        'use strict';
        $(document).ready(function () {
            //這裡是click可以連接到日誌
        	$(".in-sb-log").hover(function(){
        		$(this).click(function(){
        		
        	    })
            })
            
            //這裡是click可以播放影片
            $(".in-sb-log").hover(function(){
        		$(this).click(function(){
                    
        	    })
            })            
        });
    </script>
</body>

</html>