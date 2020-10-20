<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.reply.model.*"%>
<%@ page import="com.courseask.model.*"%>
<%@ page import="com.student.model.*"%>
<%@ page import="com.teacher.model.*"%>
<%@ page import="com.course.model.*"%>
<%@page import="java.util.*"%>
<jsp:useBean id="replySvc" scope="page" class="com.reply.model.ReplyService" />
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService" />
<jsp:useBean id="studentSvc" scope="page" class="com.student.model.StudentService" />
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="courseAskSvc" scope="page" class="com.courseask.model.CourseAskService" />
<%
ReplyVO replyVO = (ReplyVO) request.getAttribute("replyVO");
CourseAskVO courseAskVO = (CourseAskVO) request.getAttribute("courseAskVO");

%>

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/front-end/template/head.jsp" %>
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/formplugins/summernote/summernote.css">
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
                        <li class="breadcrumb-item"><a href="javascript:void(0);">課程提問</a></li>
                        <li class="breadcrumb-item">課程提問總覽</li>
                         <li class="breadcrumb-item">回覆</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-democrat'></i> 回覆
                        </h1>
                    </div>
                    <div class="row">
                       
                        <div class="col col-xl-12">
                            <div id="panel-2" class="panel">
                                <div class="panel-container show">
                                    <div class="panel-content">
                                    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/reply/reply.do" name="form1">
		<table id="tableEvaluation" class="table table-bordered table-hover table-striped w-100">
                                                    <thead>
                                                        <tr>
                                                            <th>課程</th>
                                                            <th>標題</th>
                                                            <th>問題</th>
                                                            <th>時間</th>
                                                       
                                                        </tr>                                                            
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td>${courseSvc.getOneCourse(courseAskSvc.getOneCourseAsk(replyVO.courseAskNo).courseNo).courseName}</td>
                                                            <td>${courseAskSvc.getOneCourseAsk(replyVO.courseAskNo).title}</td>
                                                            <td>${courseAskSvc.getOneCourseAsk(replyVO.courseAskNo).question }</td>
                                                            <td><fmt:formatDate value="${courseAskSvc.getOneCourseAsk(replyVO.courseAskNo).updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
                                                          
                                                                  
                                                        </tr>
                                                    </tbody>
                                                </table>
	


                                        <textarea class="js-summernote" id="democratNote" name="replyContent"></textarea>
                                        <button id="sendNote" type="submit" class="mb-3 mt-3 btn btn-info waves-effect waves-themed float-left">送出</button>
                                        <input type="hidden" name="studentNo" value="${studentVO.studentNo}"/>
                                        <input type="hidden" name="courseNo" value="${courseVO.courseNo}"/>
                                         <input type="hidden" name="courseAskNo" value="${replyVO.courseAskNo}"/>
                                        <input type="hidden" name="teacherNo" value="${teacherVO.teacherNo}"/>
                                        <input type="hidden" name="action" value="insert">
                                       	</FORM> 
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
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
    <script>
        'use strict';
        $(document).ready(function () {
            $('#democratTable').dataTable({
                responsive: true,
                language: { url: `<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json` }
            });

            $('#democratNote').summernote({
            	height: 300,
                tabsize: 2,
                placeholder: "請輸入",
                dialogsFade: true,
                toolbar: [
                    ['style', ['style']],
                    ['font', ['strikethrough', 'superscript', 'subscript']],
                    ['font', ['bold', 'italic', 'underline', 'clear']],
                    ['fontsize', ['fontsize']],
                    ['fontname', ['fontname']],
                    ['color', ['color']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['height', ['height']]
                    ['table', ['table']],
                    ['insert', ['link', 'picture', 'video']],
                    ['view', ['fullscreen', 'codeview', 'help']]
                ],
                callbacks: {
                    onInit: function (e) {
                        $.ajax({
                            url: '<%=request.getContextPath() %>/Summernote',
                            type: 'get',
                            success(res) {
                                $('#democratNote').summernote('code', res);
                            }
                        });
                    },
                    onChange: function (contents, $editable) { }
                }
            });

            $('#sendNote').click(function () {
                let form = new FormData();
                form.append("democratNote", $('#democratNote').summernote('code'));
                $.ajax({
                    url: '<%=request.getContextPath() %>/Summernote',
                    type: 'post',
                    processData: false,
                    contentType: false,
                    data: form,
                    success(res) {
                        console.log(res);
                    }
                });
            });
        });
    </script>
</body>

</html>