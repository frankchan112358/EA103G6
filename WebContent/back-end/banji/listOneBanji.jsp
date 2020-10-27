<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"%>
<%@ page import="com.banji.model.*"%>


<%
BanjiService banjiSvc = new BanjiService();
List<BanjiVO> list = banjiSvc.getAll();
pageContext.setAttribute("list", list);
%>
<jsp:useBean id="banjiTypeSvc" scope="page" class="com.banjitype.model.BanjiTypeService" />
<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />
<jsp:useBean id="classroomSvc" scope="page" class="com.classroom.model.ClassroomService" />

<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">

<style>
.table th, .table td {
	vertical-align: middle;
	text-align: center;
}
#add{
text-align: center ;
}
</style>

</head>
<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
    <script>
        var classHolder = document.getElementsByTagName("BODY")[0];
    </script>
    <div class="page-wrapper">
        <div class="page-inner">
            <%@ include file="/back-end/template/left_aside.jsp" %>
            <div class="page-content-wrapper">
                <%@ include file="/back-end/template/header.jsp" %>
                <main id="js-page-content" role="main" class="page-content">
                    <ol class="breadcrumb page-breadcrumb">
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/banji/homeBanji.jsp">養成班管理</a></li>
                         <li class="breadcrumb-item">總覽</li>
                    </ol>
                    
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-book-user' ></i> ${banjiVO.banjiTypeVO.banjiTypeName }
                        </h1>
                    </div>
					<div class="row align-items-center justify-content-center">
                        <div class="col col-xl-12">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">總覽</h2>
                                </div>
								<div class="panel-container show">
									<div class="panel-content">
										<!-- datatable start -->
										<table id="banjitable" class="table table-bordered table-hover table-striped w-100">
  											<tr>
												<th>班級編號:</th>
												<td>${banjiVO.banjiNo}</td>
											</tr>
  											
  											<tr>
												<th>導師:</th>
												<td>${banjiVO.empVO.empName}</td>
											</tr>
  											<tr>
                                                <th>班種名稱:</th>
                                                <td>${banjiVO.banjiTypeVO.banjiTypeName}</td>
                                            </tr>  
                                                    
											<tr>
												<th>課級名稱:</th>
												<td>${banjiVO.banjiName}</td>
											</tr>
											
											
											<tr>
												<th>開訓日:</th>
												<td>${banjiVO.startDay}</td>
											</tr>
											<tr>

												<th>結訓日:</th>
												<td>${banjiVO.endDay}</td>
											</tr>
											<tr>
												<th>上課時數:</th>
												<td>${banjiVO.classHours}</td>
											</tr>
											<tr>
												<th>學員人數:</th>
												<td>${banjiVO.numberOfStudent}</td>
											</tr>
											<tr>
												<th>教室:</th>
												<td>${classroomSvc.getOneClassroom(banjiVO.classroomNo).classroomName}</td>
											</tr>
											
											<tr>
												<th>內容:</th>
												<td>${banjiVO.banjiContent}</td>
											</tr>
											
											<tr>
											<th>狀態:</th>
												<td>
                                                    <c:choose>
														<c:when test="${banjiVO.status=='0'}">結業</c:when>
														<c:when test="${banjiVO.status=='1'}">開課</c:when>
														<c:when test="${banjiVO.status=='2'}">延期</c:when>
														<c:when test="${banjiVO.status=='3'}">未開課</c:when>
													</c:choose>
												</td>
											</tr>
										</table>
										<div class="form-group" id="add" >
										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/banji/homeBanji.jsp">
												<button type="submit" class="btn btn-primary justify-content-center">
													<span>確定</span>
												</button>
											</FORM>
											</div>
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




	<script>
		
	</script>
</body>
</html>