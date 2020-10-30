<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.teachingplan.model.*"%>
<%@ page import="java.util.*"%>

<%
TeachingPlanVO teachingPlanVO = (TeachingPlanVO) request.getAttribute("teachingPlanVO");
%>

<jsp:useBean id="teachingPlanSvc" scope="page" class="com.teachingplan.model.TeachingPlanService" />



<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">


<style>


th{
font-size: 15px;
}

td{
font-size: 15px;
}

table.dataTable tr.dtrg-group.dtrg-level-0 td {
    font-size: 20px;
}

.table th, .table td {
    vertical-align: middle;
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
							<a href="<%=request.getContextPath()%>/back-end/course/listAllCourse.jsp">課程總覽</a>
						</li>
						<li class="breadcrumb-item">教學計劃管理</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-chalkboard-teacher'></i>
							教學計劃管理
						</h1>
					</div>
					<div class="row">
						<div class="col-12">
							<jsp:include page="/back-end/course/courseNav.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>教學計劃列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/teachingPlan/teachingPlan.do">
											<button type="submit" class="btn btn-success waves-effect waves-themed float-left">
												<span class="far fa-plus-circle mr-1"></span>
												<span>新增</span>
											</button>
											<input type="hidden" name=teachingPlanNo value="${teachingPlanVO.teachingPlanNo}">
											<input type="hidden" name="action" value="insert">
										</FORM>
										<!-- datatable start -->
										<table id="teachingPlanTable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color: #E5F4FF;" align="center">
												<tr>
													<th>週次</th>
													<th>堂數</th>
													<th width="60%">教學內容</th>
													<th width="20%">操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="teachingPlanVO" items="${teachingPlanSvc.getTeachingPlanByCourseNo(courseNo)}">
												<tr>
														<td align="center" onclick="location.href='<%=request.getContextPath()%>/teachingPlan/teachingPlan.do?action=getOne_For_Display&teachingPlanNo=${teachingPlanVO.teachingPlanNo}';" style="cursor: pointer;">第${teachingPlanVO.week}週</td>
														<td align="center" onclick="location.href='<%=request.getContextPath()%>/teachingPlan/teachingPlan.do?action=getOne_For_Display&teachingPlanNo=${teachingPlanVO.teachingPlanNo}';" style="cursor: pointer;">${teachingPlanVO.lesson}</td>
														<td align="center" onclick="location.href='<%=request.getContextPath()%>/teachingPlan/teachingPlan.do?action=getOne_For_Display&teachingPlanNo=${teachingPlanVO.teachingPlanNo}';" style="cursor: pointer;">${teachingPlanVO.planContent}</td>
														
														<td class="d-flex p-1 justify-content-center ">
															<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/teachingPlan/teachingPlan.do" style="margin-bottom: 0px;" class="m-1">
																<button type="submit" class="btn btn-outline-primary btn-icon rounded-circle">
																<i class="fal fa-edit"></i>
																</button>
																<input type="hidden" name="teachingPlanNo" value="${teachingPlanVO.teachingPlanNo}">
																<input type="hidden" name="action" value="getOne_For_Update">
															</FORM>
																<FORM id="deleteTeachingPlan" METHOD="post" ACTION="<%=request.getContextPath()%>/teachingPlan/teachingPlan.do" style="margin-bottom: 0px;" class="m-1">
																<button id="submitDeleteTeachingPlan" type="submit" class="btn btn-outline-danger btn-icon rounded-circle">
                                                        		<i class="fal fa-times"></i>
																</button>
																<input type="hidden" name="teachingPlanNo" value="${teachingPlanVO.teachingPlanNo}">
																<input type="hidden" name="action" value="delete">
															</FORM>
														</td>
													</tr>
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
				<%@ include file="/back-end/template/footer.jsp"%>
			</div>
		</div>
	</div>


	<%@ include file="/back-end/template/quick_menu.jsp"%>
	<%@ include file="/back-end/template/messager.jsp"%>
	<%@ include file="/back-end/template/basic_js.jsp"%>


	<script src="<%=request.getContextPath()%>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
	<script>
    
     
    $(document).ready(function()
            {
                $('#teachingPlanTable').dataTable(
                {
                    responsive: true,
                    language:{url:'<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json'},
					pageLength : 25,
					rowGroup : {
						dataSrc : 0
					}
					});
                
        		$("#submitDeleteTeachingPlan").on("click", function(event) {
					event.preventDefault();
					var swalWithBootstrapButtons = Swal.mixin({
						customClass : {
							confirmButton : "btn btn-primary",
							cancelButton : "btn btn-danger mr-2"
						},
						buttonsStyling : false
					});
					swalWithBootstrapButtons.fire({
						icon : "warning",
						title : "請再次確認是否刪除",
						text : "教學計劃一旦刪除並無復原可能",
						showCancelButton : true,
						confirmButtonText : "確定刪除",
						cancelButtonText : "暫不刪除",
						reverseButtons : true
					}).then(function(result) {
						if (result.value) {
							swalWithBootstrapButtons.fire("刪除請求送出", "請稍等跳轉頁面", "success");
							setTimeout(function() {
								$('#deleteTeachingPlan').submit();
							}, 1000);
						} else if (
						// Read more about handling dismissals
						result.dismiss === Swal.DismissReason.cancel) {
							swalWithBootstrapButtons.fire("刪除請求取消", "刪除教學計劃請再三確認", "error");
						}
					});
					event.stopPropagation();
				}); // A message with a custom image and CSS animation disabled		

                
			});
	</script>
</body>
</html>
