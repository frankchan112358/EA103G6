<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*,com.emp.model.*"%>
<%@ page import="java.util.*"%>
<%
	UserVO userVO = (UserVO) request.getAttribute("userVO"); 
	EmpVO empVO = (EmpVO) request.getAttribute("empVO"); 
	
%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">
    
<style>
    
    img {
	max-width: 100%;
	max-height: 100%;
	border:2px #C4B1B1 dashed;
	
}

#pic{
	width: 150px;
	height: 150px;
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
                        <li class="breadcrumb-item"><a href="javascript:void(0);">後台首頁</a></li>
                        <li class="breadcrumb-item">Democrat</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-democrat'></i> Democrat
                        </h1>
                    </div>
                    <div class="row">
                            <div class="col-xl-12">
                                <div id="panel-1" class="panel">
                                    <div class="panel-hdr">
                                        <h2>
                                            ${userVO.name} <span class="fw-300"><i>個人資料</i></span>
                                        </h2>
                                        <div class="panel-toolbar">
                                            <button class="btn btn-primary btn-sm" data-toggle="dropdown">Table Style</button>
                                            <div class="dropdown-menu dropdown-menu-animated dropdown-menu-right position-absolute pos-top">
                                                <button class="dropdown-item active" data-action="toggle" data-class="table-bordered" data-target="#dt-basic-example"> Bordered Table </button>
                                                <button class="dropdown-item" data-action="toggle" data-class="table-sm" data-target="#dt-basic-example"> Smaller Table </button>
                                                <button class="dropdown-item" data-action="toggle" data-class="table-dark" data-target="#dt-basic-example"> Table Dark </button>
                                                <button class="dropdown-item active" data-action="toggle" data-class="table-hover" data-target="#dt-basic-example"> Table Hover </button>
                                                <div class="dropdown-divider m-0"></div>
                                                <div class="dropdown-multilevel dropdown-multilevel-left">
                                                    
                                                    
                                                </div>
                                                
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-container show">
                                        <div class="panel-content">
                                            
                                            <!-- datatable start -->
                                            <table id="dt-basic-example" class="table table-responsive dtr-details" >
											
											<tr>
												<td>
													<div id="pic" >
														<%if (userVO.getPhoto() == null) {%>
														<img src="<%=request.getContextPath()%>/images/noPicture.png">
														
														<%} else {%>
														<img src="<%=request.getContextPath()%>/user.do?action=getPhoto&userNo=<%=userVO.getUserNo()%>">
														<%} %>
													</div>
												<td>
											
											</tr>
											
											<tr data-dt-row="14">
													<th>姓名</th>
													<td>${userVO.name}</td>									
												</tr>
                                                <tr>
													<th>導師編號</th>
													<td>${empVO.empNo}</td>									
												</tr>                                                
                                                <tr>
													<th>帳號</th>
													<td>${userVO.account}</td>									
												</tr>
                                                <tr>
													<th>電話</th>
													<td>${userVO.phone eq null?"暫無輸入":userVO.phone}</td>									
												</tr>
                                                <tr>
													<th>電子信箱</th>
													<td>${userVO.mail}</td>									
												</tr>
                                                <tr>
													<th>身分證字號</th>
													<td>${userVO.id}</td>									
												</tr>
                                                <tr>
													<th>帳號狀態</th>
													<td>${userVO.enable==0?"停用中":"啟用中"}</td>									
												</tr>
                                                <tr>
													<th>導師狀態</th>
													<td>
														<c:choose>
                                                    			<c:when test="${empVO.empStatus==0}">離職</c:when>
                                                    			<c:when test="${empVO.empStatus==1}">在職</c:when>
                                                    			<c:when test="${empVO.empStatus==2}">停職</c:when>
                                                    	</c:choose>											
													</td>									
												</tr>
                                                <tr>
													<th>地址</th>
													<td>${userVO.address eq null?"暫無輸入":userVO.address}</td>									
												</tr>
																					
                                            </table>
                                            <!-- datatable end -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                </main>
                <!-- this overlay is activated only when mobile menu is triggered -->
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div> <!-- END Page Content -->
                
               
                
                
                
                
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
                <%@ include file="/back-end/template/footer.jsp" %>
            </div>
        </div>
    </div>
    
    
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
	<!--     把法蘭克原本預設的inclide的js刪掉 -->
    
    
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/vendors.bundle.js"></script>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/app.bundle.js"></script>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>

</body>
</html>