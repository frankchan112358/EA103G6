<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.coursepost.model.*"%>
<%@ page import="java.util.*"%>

<%
CoursePostVO coursePostVO = (CoursePostVO) request.getAttribute("coursePostVO");

%>


<jsp:useBean id="coursePostSvc" scope="page" class="com.coursepost.model.CoursePostService" />


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
							<a href="<%=request.getContextPath()%>/back-end/course/listAllCourse.jsp">課程總覽</a>
						</li>
						<li class="breadcrumb-item">課程公告管理</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-calendar-edit'></i>
							課程公告管理
						</h1>
					</div>
					<div class="row">
						<div class="col-12">
							<jsp:include page="/back-end/course/courseNav.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>課程公告列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/coursePost/coursePost.do">
											<button id="addCoursePost" type="submit" class="btn btn-success waves-effect waves-themed float-left">
												<span class="far fa-plus-circle mr-1"></span>
												<span>新增</span>
											</button>
											<input type="hidden" name="coursePostNo" value="${coursePostVO.coursePostNo}">
											<input type="hidden" name="action" value="new">
										</FORM>
										<!-- datatable start -->
										<table id="coursePostTable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color: #E5F4FF;">
												<tr>
													<th>公告編號</th>
													<th width="50%">公告標題</th>
													<th width="20%">公告時間</th>
													<th width="20%">操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="coursePostVO" items="${coursePostSvc.getCoursePostByCourseNo(courseNo)}">
<%-- 												<tr data-toggle="modal" data-id="${coursePostVO.coursePostNo}" data-target="#coursePostModal"> --%>
												<tr onclick="location.href='<%=request.getContextPath()%>/coursePost/coursePost.do?action=getOne_For_Display&coursePostNo=${coursePostVO.coursePostNo}';" style="cursor: pointer;" >
														
														<td>${coursePostVO.coursePostNo}</td>
														<td>${coursePostVO.title}</td>
														<td>
															<fmt:formatDate value="${coursePostVO.updateTime}" pattern="yyyy年MM月dd日 HH'點'mm'分'" />
														</td>
														<td class="d-flex p-1 justify-content-center" >
															<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/coursePost/coursePost.do" style="margin-bottom: 0px;" class="m-1">
																<button type="submit" class="btn btn-outline-primary btn-icon rounded-circle">
																<i class="fal fa-edit"></i>
																</button>
																<input type="hidden" name="coursePostNo" value="${coursePostVO.coursePostNo}">
																<input type="hidden" name="action" value="getOne_For_Update">
															</FORM>
																<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/coursePost/coursePost.do" style="margin-bottom: 0px;" class="m-1">
																<button type="submit" class="btn btn-outline-danger btn-icon rounded-circle">
                                                        		<i class="fal fa-times"></i>
																</button>
																<input type="hidden" name="coursePostNo" value="${coursePostVO.coursePostNo}">
																<input type="hidden" name="action" value="delete">
															</FORM>
														</td>
<!-- 													</tr> -->
												</c:forEach>
											</tbody>

										</table>
<!-- 										     <div class="modal fade" id="coursePostModal" tabindex="-1" role="dialog" aria-hidden="true"> -->
<!--                                                 <div class="modal-dialog modal-lg modal-dialog-centered" role="document"> -->
<!--                                                     <div class="modal-content"> -->
<!--                                                         <div class="modal-header"> -->
<!--                                                             <h5 class="modal-title">Modal title</h5> -->
<!--                                                             <button type="button" class="close" data-dismiss="modal" aria-label="Close"> -->
<!--                                                                 <span aria-hidden="true"><i class="fal fa-times"></i></span> -->
<!--                                                             </button> -->
<!--                                                         </div> -->
<!--                                                         <div class="modal-body" id="coursePostDetails"> -->
<!--  															<table id="teachingPlanTable" class="table table-bordered table-hover table-striped w-100"> -->
<!-- 																<tr> -->
<!-- 																<th>公告標題</th> -->
<%-- 																<td>${coursePostVO.title}</td> --%>
<!-- 																</tr>				 -->
<!-- 																<tr> -->
<!-- 																<th>公告內容</th> -->
<%-- 																<td  width="85%">${coursePostVO.postContent}</td> --%>
<!-- 																</tr> -->
<!-- 																<tr> -->
<!-- 																<th>公告時間</th> -->
<%-- 																<td class="updateTime"><fmt:formatDate value="${coursePostVO.updateTime}" pattern="yyyy年MM月dd日 HH'點'mm'分'" /></td> --%>
<!-- 																</tr> -->
<!-- 																</table>                                                            -->
<!--                                                         </div> -->
<!--                                                         <div class="modal-footer"> -->
<!--                                                             <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> -->
<!--                                                         </div> -->
<!--                                                     </div> -->
<!--                                                 </div> -->
<!--                                             </div> -->
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
                $('#coursePostTable').dataTable(
                {
                    responsive: true,
                    language:{url:'<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json'
					},
					});
			});
	</script>
</body>
</html>
