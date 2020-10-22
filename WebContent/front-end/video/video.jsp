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
            overflow: hidden;
        }
        .panel-content {
            height:500px;
        }
        .in-sb {
        	border-bottom:5px #9EDF56 double;
        	height:20%;
            font-size: large;
            margin:auto;
        }
        .fa-pencil-alt:hover {
            transform: scale(1.5);
        }
		video{
		position:absolute;
		width:100%;
		margin:auto;
        align-self: center;
		}
		
		a {
		color:black;
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
                                                    <a class=vpath href="<%=request.getContextPath()%>/videos/${videoVO.videoName}.mp4"  target="player">
                                        		<div class="in-sb" > 
                                                    <input type="hidden" name="videoNo" value="${videoVO.videoNo}">
                                                    <input  type="hidden" name="timetableNo" value="${videoVO.timetableNo}">
                                        	          	上課日期 : ${videoVO.timetableVO.timetableDate}<br>
                                                    <input class="videoname" type="hidden" name="videoName" value="${videoVO.videoName}">
                                       		 			影片名稱 : ${videoVO.videoName}<br>
                                        				<div class="in-sb-log">
                                        					<div class="log">
                                        					<button timeteableNo="${videoVO.timetableVO.timetableNo}" type="button" class="btn btn-warning btn-pills waves-effect waves-themed" style="font-size:small;">教學日誌</button>
                                                     	   </div>
                                                    	</div>
                                        		</div>
                                                    </a>
                                        	</c:forEach>
                                        	</div>
                                        <div class="player">
                                        	<video src="#"   type="video/mp4" loop controls ></video>
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
    <div class="modal fade" id="sbLogModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">教學日誌</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"><i class="fal fa-times"></i></span>
                    </button>
                </div>
                <div id="sbLog" class="modal-body"></div>
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
            //這裡是滑鼠進入slide-bar會讓區塊變色
        	$(".in-sb").hover(function(){
                $(this).css("background-color", "#a9ec62");},
                function(){
                $(this).css("background-color", " #c3dbaa");
            })

            
            var vIndex=8;
            var path = null;
            //這裡是click(slide-bar)可以開啟影片
			$(".in-sb").click(function(e){
				e.preventDefault();
				vIndex = $(".in-sb").index(this);
				path = $('.vpath:eq('+vIndex+')').attr('href');
				$("video").attr('src',path);
			})

            
            //這裡是click(in-sb-log)可以查看日誌
            $(document).on('click', '.in-sb-log button', function (event) {
                let timeteableNo = this.getAttribute('timeteableNo');
                $.ajax({
                    type: 'GET',
                    url: '<%=request.getContextPath()%>/video/video.getsblog',
                    data: {
                        timetableNo: timeteableNo
                    },
                    success(res) {
                        if (res != null) {
                            $('#sbLog').html('');
                            $('#sbLog').html(res);
                            $('#sbLogModal').modal('show');
                        }
                    }
                });
            });   
        });
    </script>
</body>

</html>