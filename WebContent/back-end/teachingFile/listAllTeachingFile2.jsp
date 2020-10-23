<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.course.model.*,com.timetable.model.*"%>
<%@ page import="java.util.*"%>

<%
CourseService courseSvc = new CourseService();
List<CourseVO> courseList = courseSvc.getAll();

TimetableService timetableSvc =new TimetableService();
List<TimetableVO> timetableList = timetableSvc.getAll();

pageContext.setAttribute("courseList", courseList);
pageContext.setAttribute("timetableList", timetableList);

CourseVO choose_courseVO = (CourseVO) request.getAttribute("courseVO");
pageContext.setAttribute("choose_courseVO", choose_courseVO);
%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">


<style>

.table th, .table td {
    vertical-align: middle;
    text-align: center;  
}


</style>

</head>

<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
	<script>
		var classHolder = document.getElementsByTagName("BODY")[0];
	</script>

	<div class="page-wrapper">
		<div class="page-inner">
			<%@ include file="/back-end/template/left_aside.jsp"%>
			<div class="page-content-wrapper">
				<%@ include file="/back-end/template/header.jsp"%>
				<main id="js-page-content" role="main" class="page-content">
					<ol class="breadcrumb page-breadcrumb">
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a>
						</li>
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/back-end/course/listAllCourse.jsp">課程總覽</a>${choose_courseVO.courseName}/教材管理
						</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class="fas fa-video" style="color: #374EFA;"></i>
							教材管理
						</h1>
					</div>
					<div class="row">
						<div class="col-xl-12">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>教材列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<!-- datatable start -->
                      <table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color:#E5F4FF;">
												<tr>
													<th width="15%">課表編號</th>
													<th width="15%">上課日期</th>
													<th width="10%">時段</th>


												</tr>
											</thead>
											<tbody>
												 <c:forEach var="courseVO" items="${courseList}">
												 	<c:if test="${courseVO.courseNo eq choose_courseVO.courseNo }">
                                                    <c:forEach var="timetableVO" items="${timetableList}">                                                                                                                                                     
                                                    	<c:if test="${courseVO.courseNo eq timetableVO.courseNo}">
                                                    	<tr>
                                                    		<td>${timetableVO.timetableNo}</td>
                                                    		<td>${timetableVO.timetableDate}</td>
                                                    		<td>${timetableVO.periodText}</td>
                                                     		<c:if test="${timetableSvc.getOneVideoWithTimetableNo(timetableVO.timetableNo)!=null}">
                                                     			
                                                     		</c:if>
                                                    		<c:if test="${timetableSvc.getOneVideoWithTimetableNo(timetableVO.timetableNo)==null}">
                                                    			
                                                    		</c:if>
                                                    		</tr>
                                                    	</c:if>
                                                    </c:forEach>
                                                    </c:if>
                                                 </c:forEach>
											</tbody>

										</table>
										<!-- datatable end -->
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