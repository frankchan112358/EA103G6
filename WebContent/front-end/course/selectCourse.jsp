<%@page import="com.course.model.*"%>
<%@page import="com.student.model.*"%>

<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="studentSvc" scope="page" class="com.student.model.StudentService" />




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
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/index/index.jsp">前台首頁</a></li>
                        <li class="breadcrumb-item">我的課程</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-book'></i> 我的課程
                        </h1>
                    </div>
                     <div class="row">
                        <div class="col">
                            <div id="panel-1" class="panel">
                                    <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                        <h2 class="text-white">我的課程總覽</h2>
                                    </div>
                                    
                                    <div class="panel-container show">
                                        <div class="panel-content">
                                                <div class="card border m-auto m-lg-0" style="max-width: 320px; max-hiegth: auto">
                                                <img src="<%=request.getContextPath()%>/images/JAVA.jpg" class="card-img-top" alt="...">
                                                <div class="card-body">
                     							     <table id="tableEvaluation" class="table table-bordered table-hover table-striped w-100">
                                                    <thead>
                                                        <tr>
                                                            <th>課程</th>
                                                            <th>講師</th>
                                                            <th>狀態</th>
                                                            <th>填寫</th>
                                                        </tr>                                                            
                                                    </thead>
                                                    <tbody>
                                                    	<c:forEach var="courseVO" items="${studentVO.getCourseList()}" >
                                                        <tr>
                                                            <td>${courseVO.courseName }</td>
                                                            <td>${teacherSvc.getOneTeacher(courseVO.teacherNo).getTeacherName() }</td>
                                                            <td>${courseSvc.getCourseStatusText(courseVO.status) }</td>
                                                            <td>
                                                            <c:if test="${addedList.contains(courseVO.courseNo)}" var="toDo" scope="page">
                                                                <button todo="update" courseStatus="${courseVO.status}" courseNo="${courseVO.courseNo}" type="button" class="btn-write btn btn-sm btn-info" data-toggle="modal" data-target="#editorEvaluation">
                                                                    <span class="fal fa-edit mr-1"></span>
                                                                    <span>修改</span>
                                                                </button>                                                            
                                                            </c:if>
											                <c:if test="${toDo == false}">
                                                                <button todo="add" courseStatus="${courseVO.status}" courseNo="${courseVO.courseNo}" type="button" class="btn-write btn btn-sm btn-primary" data-toggle="modal" data-target="#editorEvaluation">
                                                                    <span class="fal fa-edit mr-1"></span>
                                                                    <span>填寫</span>
                                                                </button>  
											                </c:if>                                                            
                                                            </td>
                                                        </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                                
                                                </div>
                                                
                                                <div class="card-body">
                                                    <div class="progress">
                                                <div class="progress-bar" role="progressbar" style="width: 25%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
                                            </div>
                                                </div>
                                            </div>
                                            
                                        </div>
                                        </div>
                                        </div>
                                        </div>
                                    </div>
                                </main>
                               </div>
                          </div>
                     </div>
                      
                
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
                <%@ include file="/front-end/template/footer.jsp" %>
  
   
    <%@ include file="/front-end/template/quick_menu.jsp" %>
    <%@ include file="/front-end/template/messager.jsp" %>
<%--     <%@ include file="/front-end/template/basic_js.jsp" %>    --%>
    
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/vendors.bundle.js"></script>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/app.bundle.js"></script>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
    
    <script>
    
            $(document).ready(function()
            {
                $('#coursetable').dataTable(
                {
                    responsive: true,
                    language:{url:'<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json'},
					"columnDefs":[{
						"targets":[ -1, -2 ],
						"orderable":false,
					
				}]
                });
           
            });
     </script>
</body>
</html>