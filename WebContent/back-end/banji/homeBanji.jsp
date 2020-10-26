<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banji.model.*"%>
<%@page import="java.util.*"%>
<%
	BanjiService banjiSvc = new BanjiService();
	List<BanjiVO> list = banjiSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="classroomSvc" scope="page"
	class="com.classroom.model.ClassroomService" />
<!DOCTYPE html>
<html>

<head>
<%@ include file="/back-end/template/head.jsp"%>
<style type="text/css">
#add {
	position: absolute;
	top: 65px;
	right: 70px;
}
</style>
</head>

<body
	class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
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
						<li class="breadcrumb-item"><a
							href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a></li>
						<li class="breadcrumb-item">養成班管理</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-users-class'></i> 養成班管理
						</h1>
					</div>
					<div id="add">
							<input type="hidden" name="action" > 
							<button type="submit" id="btn-add" style="width:150px;heigh:50px;" onclick="location.href='<%=request.getContextPath()%>/back-end/banji/addBanji.jsp'" style="margin-bottom: 0px'"
								class="btn-write btn btn-sm btn-primary" >
								<strong>新增班級</strong>
							</button>
						</div>
					<div class="row">
						<div class="col col-xl-12">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">總覽</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<!-- datatable start -->
										<table id="banjiTable"
											class="table table-bordered table-hover table-striped w-100">
											<thead>
												<tr>
													<th>班種</th>
													<th>班級</th>
													<th>開訓日</th>
													<th>結訓日</th>
													<th>狀態</th>
													<th>管理</th>
													<th>修改</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="banjiVO" items="${empVO.banjiList}">
													<tr
														onclick="location.href='<%=request.getContextPath()%>/banji/banji.do?action=getOne_For_Display&banjiNo=${banjiVO.banjiNo}';">
														<td>${banjiVO.banjiTypeVO.banjiTypeName}</td>
														<td>${banjiVO.banjiName}</td>
														<td>${banjiVO.startDay}</td>
														<td>${banjiVO.endDay}</td>
														<td><c:choose>
																<c:when test="${banjiVO.status=='0'}">結業</c:when>
																<c:when test="${banjiVO.status=='1'}">開課</c:when>
																<c:when test="${banjiVO.status=='2'}">延期</c:when>
																<c:when test="${banjiVO.status=='3'}">未開課</c:when>
															</c:choose></td>
														<td>
															<form method="post"
																action="<%=request.getContextPath()%>/banji/banji.manage"
																class="m-1">
																<button type="submit" class="btn btn-sm btn-success">
																	<span class="fal fa-edit mr-1"></span> <span>管理</span>
																</button>
																<input type="hidden" name="action" value="read">
																<input type="hidden" name="banjiNo"
																	value="${banjiVO.banjiNo}">
															</form>
														</td>
														<td>
															<form method="post"
																action="<%=request.getContextPath()%>/banji/banji.do"
																class="m-1">
																<button type="submit" class="btn btn-sm btn-danger">
																	<span class="fal fa-edit mr-1"></span> <span>修改</span>
																</button>
																<input type="hidden" name="action"
																	value="getOne_For_Update"> <input type="hidden"
																	name="banjiNo" value="${banjiVO.banjiNo}">
															</form>
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
				<div class="page-content-overlay" data-action="toggle"
					data-class="mobile-nav-on"></div>
				<%@ include file="/back-end/template/footer.jsp"%>
			</div>
		</div>
	</div>
	<%@ include file="/back-end/template/quick_menu.jsp"%>
	<%@ include file="/back-end/template/messager.jsp"%>
	<%@ include file="/back-end/template/basic_js.jsp"%>
	<script>
        'use strict';
        $(document).ready(function () {
            $('#banjiTable').dataTable({
                responsive: true,
                language: { url: `<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json`
												}
											});
							$("th").addClass("align-middle");
							$("td").addClass("align-middle");
						});
	</script>
</body>

</html>