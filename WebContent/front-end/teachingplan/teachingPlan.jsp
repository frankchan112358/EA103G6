<%@page import="com.teachingplan.model.*"%>
<%@page import="com.course.model.*"%>


<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />


<%
	@SuppressWarnings("unchecked")
	List<TeachingPlanVO> list = (List<TeachingPlanVO>) session.getAttribute("teachingPlanVO");
	session.setAttribute("list", list);
%>





<!DOCTYPE html>
<html>
<head>
<%@ include file="/front-end/template/head.jsp"%>

<style>

th{
font-size: 16px;
}

td{
font-size: 16px;
}

table.dataTable tr.dtrg-group.dtrg-level-0 td {
    font-size: 20px;
}

.dataTables_empty {
    color: #666666;
}

</style>
</head>
<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
	<script>
		var classHolder = document.getElementsByTagName("BODY")[0];
	</script>
	<div class="page-wrapper">
		<div class="page-inner">
			<%@ include file="/front-end/template/left_aside.jsp"%>
			<div class="page-content-wrapper">
				<%@ include file="/front-end/template/header.jsp"%>
				<main id="js-page-content" role="main" class="page-content">
					<ol class="breadcrumb page-breadcrumb">
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/front-end/index/index.jsp">前台首頁</a>
						</li>
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/front-end/course/selectCourse.jsp">我的課程</a>
						</li>
						<li class="breadcrumb-item">教學計劃</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-chalkboard-teacher'></i>
							教學計劃
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-11">
							<%@ include file="/front-end/course/courseDetail.jsp"%>
							<div id="panel-2" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">教學計劃總覽</h2>
								</div>
								<div class="panel-container show" style="margin: 20px 20px;">
									<table id="teachingPlan" class="table table-bordered table-hover table-striped w-100">
										<thead class="thead-light" align="center">
											<tr>
												<th>週次</th>
												<th>堂數</th>
												<th>教學內容</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="teachingPlanVO" items="${list}">
												<c:if test="${!empty list}">
													<tr>
														<td align="center">第${teachingPlanVO.week}週</td>
														<td align="center">${teachingPlanVO.lesson}</td>
														<td align="center">${teachingPlanVO.planContent}</td>
													</tr>
												</c:if>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</main>
			</div>
		</div>
	</div>




	<div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>

	<%@ include file="/front-end/template/footer.jsp"%>


	<%@ include file="/front-end/template/quick_menu.jsp"%>
	<%@ include file="/front-end/template/messager.jsp"%>
	<%@ include file="/front-end/template/basic_js.jsp"%>


        <script src="js/datagrid/datatables/datatables.export.js"></script>

	<script>
				$(document).ready(function() {
					$('#teachingPlan').dataTable({
						responsive : true,
	                    language:{
	                        "emptyTable": 
	                        	"<h2>目前尚無任何教學計劃</h2><div><h4>講師將會在這發佈與課程相關的教學計劃。</h4></div>",
	                        "processing": "處理中...",
	                        "loadingRecords": "載入中...",
	                        "lengthMenu": "顯示 _MENU_ 項結果",
	                        "zeroRecords": "沒有符合的結果",
	                        "info": "顯示第 _START_ 至 _END_ 項結果，共 _TOTAL_ 項",
	                        "infoEmpty": "顯示第 0 至 0 項結果，共 0 項",
	                        "infoFiltered": "(從 _MAX_ 項結果中過濾)",
	                        "infoPostFix": "",
	                        "paginate": {
	                          "first": "第一頁",
	                          "previous": "上一頁",
	                          "next": "下一頁",
	                          "last": "最後一頁"
	                        },
	                        "aria": {
	                          "sortAscending": ": 升冪排列",
	                          "sortDescending": ": 降冪排列"
	                        }
	                    },
						pageLength : 25,
						rowGroup : {
							dataSrc : 0
						}
					});
					
					
				});
				
				
	</script>
</body>
</html>