<%@page import="com.evaluation.model.EvaluationQuestion"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="evaluationSvc" scope="page" class="com.evaluation.model.EvaluationService" />
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
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
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/index/index.jpg">前台首頁</a></li>
                        <li class="breadcrumb-item"><a href="javascript:void(0);">教學評鑑</a></li>
                        <li class="breadcrumb-item">新增教學評鑑</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-clipboard-check'></i> 新增教學評鑑
                        </h1>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div id="panel-1" class="panel">
                                <form class="needs-validation" novalidate method="GET" action="">
                                    <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                        <h2 class="text-white">課程意見調查表</h2>
                                    </div>
                                    <div class="panel-container show">
                                        <div class="panel-content">
                                            <div class="form-group">
                                                <label class="form-label" for="selectCourse">課程</label>
                                                <div class="input-group flex-nowrap">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fal fa-book fs-xl"></i></span>
                                                    </div>
                                                    <select class="custom-select form-control" id="selectCourse">
                                                        <c:forEach var="course" items="${courseSvc.all}">
                                                            <option value="${course.courseNo}">${course.courseName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <c:forEach var="question" items="${evaluationSvc.getEvaluationQuestionAll()}">
                                                    <h5 class="frame-heading fs-md m-0 mb-1 mt-1">${question.text}</h5>
                                                    <%for(int i=9;i>-1;i--) {%>
                                                        <div style="display:inline;" class="custom-control custom-radio custom-control-inline">                                                   
                                                            <input type="radio" class="custom-control-input" id="Q${question.num}_<%=i %>" name="Q${question.num}" value="<%=i %>" required=""/>
                                                            <label class="custom-control-label" for="Q${question.num}_<%=i %>"><%=i %></label>
                                                            <%if(i==0){%><div class="invalid-feedback">Please select at least one</div><%}%>                                                                                                   	
                                                        </div>                                                                                        			
                                                    <%} %>                                                                                          
                                                </c:forEach>
                                                <div class="">
                                                    <button type="submit" class="btn btn-lg btn-primary waves-effect waves-themed text-white">
                                                        <span class="fal fa-share-square mr-1"></span> 送出點評                                          
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <script>
                                    // Example starter JavaScript for disabling form submissions if there are invalid fields
                                    (function()
                                    {
                                        'use strict';
                                        window.addEventListener('load', function()
                                        {
                                            // Fetch all the forms we want to apply custom Bootstrap validation styles to
                                            var forms = document.getElementsByClassName('needs-validation');
                                            // Loop over them and prevent submission
                                            var validation = Array.prototype.filter.call(forms, function(form)
                                            {
                                                form.addEventListener('submit', function(event)
                                                {
                                                    if (form.checkValidity() === false)
                                                    {
                                                        event.preventDefault();
                                                        event.stopPropagation();
                                                    }
                                                    form.classList.add('was-validated');
                                                }, false);
                                            });
                                        }, false);
                                    })();
                                </script>
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
</body>
</html>