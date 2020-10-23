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
    
    <style>
/* .card-columns { */
/*   padding: 10px; */
/* } */
.card {
  color: #aaaa55;
  border: 2px solid #aaaa55;
}
@media (min-width: 576px) {
  .card-columns {
    column-count: 2;
    }
       .card-img-top { 
     height: 23vw;
     }
}
@media (min-width: 768px) {
  .card-columns {
    column-count: 3;
    }
     .card-img-top { 
     height: 18vw;
     }
  }
@media (min-width: 992px) {
  .card-columns {
    column-count: 3;
    }  
     .card-img-top { 
     height: 16vw;
     }
}
@media (min-width: 1200px) {
  .card-columns {
    column-count: 5;
  }
   .card-img-top { 
     height: 11vw;
     }
}

 .card-img-top {
     object-fit:cover  ; 
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
                        <li class="breadcrumb-item">我的課程</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-book'></i> 我的課程
                        </h1>
                    </div>
                     <div class="row ">
                        <div class="col">
                            <div id="panel-1" class="panel">
                                    <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                        <h2 class="text-white">我的課程總覽</h2>
                                    </div>
								<div class="container-fluid ">	
								 <div class="card-columns">
  								       <a href="<%=request.getContextPath()%>/front-end/index/index.jsp">
  								       
                                         <c:forEach var="courseVO" items="${studentVO.courseList}" >
                                           <div class="card border my-3" >
                                              <c:if test="${courseVO.courseImg eq null}">
                                                    <img src="<%=request.getContextPath() %>/images/尚無圖片.jpg" class="card-img-top img-fluid"  alt="課程封面圖">
												</c:if>
												
												<c:if test="${courseVO.courseImg ne null}">
												<img src="<%=request.getContextPath() %>/course/course.do?action=getCourseImg&courseNo=${courseVO.courseNo}" class="card-img-top img-fluid" alt="課程封面圖">
                                                </c:if>
                                                <div class="card-body">
                                                    <h5 class="card-title">${courseVO.courseName}</h5>
                                                </div>
                                                  <div class="card-footer"> 
                                                 <div class="progress">
                                                <div class="progress-bar" role="progressbar" style="width: 25%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>  
                                            </div>
                                           </div>
                                            </div>
                                            </c:forEach>
                                            </a>
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
				
                });
           
            });
     </script>
</body>
</html>