<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.user.model.*,com.teacher.model.*"%>
<%@ page import="java.util.*"%>
<%
	UserVO userVOForShow = (UserVO) request.getAttribute("userVOForShow"); 
	TeacherVO teacherVOForShow = (TeacherVO) request.getAttribute("teacherVOForShow"); 
	
%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
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
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/teacher/teacherList.jsp?goto=teacherList">講師管理</a></li>
                    	<li class="breadcrumb-item">個人資料顯示</li>
                    	
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-democrat'></i> 個人資料顯示
                        </h1>
                    </div>
                    <div class="row">
                            <div class="col-xl-12">
                                <div id="panel-1" class="panel">
                                    <div class="panel-hdr">
                                        <h2>
                                            ${userVOForShow.name} <span class="fw-300"><i>個人資料</i></span>
                                        </h2>
                                    </div>
                                    <div class="panel-container show">
                                        <div class="panel-content">
                                            
                                            <!-- datatable start -->
                                            <table id="dt-basic-example" class="table table-responsive dtr-details" >
											
											<tr>
												<td>
													<div id="pic" >
														<%if (userVOForShow.getPhoto() == null) {%>
														<img src="<%=request.getContextPath()%>/images/noPicture.png">
														
														<%} else {%>
														<img src="<%=request.getContextPath()%>/user.do?action=getPhoto&userNo=<%=userVOForShow.getUserNo()%>">
														<%} %>
													</div>
												<td>
											
											</tr>
											
											<tr data-dt-row="14">
													<th>姓名</th>
													<td>${userVOForShow.name}</td>									
												</tr>
                                                <tr>
													<th>講師編號</th>
													<td>${teacherVOForShow.teacherNo}</td>									
												</tr>                                                
                                                <tr>
													<th>帳號</th>
													<td>${userVOForShow.account}</td>									
												</tr>
                                                <tr>
													<th>電話</th>
													<td>${userVOForShow.phone eq null?"暫無輸入":userVOForShow.phone}</td>									
												</tr>
                                                <tr>
													<th>電子信箱</th>
													<td>${userVOForShow.mail}</td>									
												</tr>
                                                <tr>
													<th>身分證字號</th>
													<td>${userVOForShow.id}</td>									
												</tr>
                                                <tr>
													<th>技能</th>
													<td>${teacherVOForShow.skill eq null? "暫無輸入":teacherVOForShow.skill }</td>									
												</tr>
                                                <tr>
													<th>描述</th>
													<td>${teacherVOForShow.description eq null? "暫無輸入":teacherVOForShow.description}</td>									
												</tr>
                                                <tr>
													<th>帳號狀態</th>
													<td>${userVOForShow.enable==0?"停用中":"啟用中"}</td>									
												</tr>
                                                <tr>
													<th>講師狀態</th>
													<td>
														<c:choose>
                                                    			<c:when test="${teacherVOForShow.teacherStatus==0}">離職</c:when>
                                                    			<c:when test="${teacherVOForShow.teacherStatus==1}">在職</c:when>
                                                    			<c:when test="${teacherVOForShow.teacherStatus==2}">停職</c:when>
                                                    	</c:choose>											
													</td>									
												</tr>
                                                <tr>
													<th>地址</th>
													<td>${userVOForShow.address eq null?"暫無輸入":userVOForShow.address}</td>									
												</tr>
																					
                                            </table>
                                            <div class="demo row">
                                            <form id="deleteTeacher">
                                            	<button id="submitDeleteTeacher" class="btn btn-danger ml-auto">刪除</button>
                                            	<input type="hidden" name="action" value="delete">
                                            	<input type="hidden" name="userNo" value="<%=userVOForShow.getUserNo()%>"> 
                                            </form>
                                            <form method="post" action="<%=request.getContextPath()%>/user.do" id="updateTeacher">
                                            	<input type="hidden" name="userNo" value="<%=userVOForShow.getUserNo()%>"> 
												<input type="hidden" name="action" value="getOne_For_Update">
                                            	<button id="submitUpdateTeacher" class="btn btn-primary ml-auto" <% if(teacherVOForShow.getTeacherStatus()==0) out.print("disabled='disabled'");%>>修改</button>
                                            </form>
                                            </div>
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
    <%@ include file="/back-end/template/basic_js.jsp" %>
    
    
    <script>

$("#deleteTeacher").on("click", function(event)
        {
			event.preventDefault();
            var swalWithBootstrapButtons = Swal.mixin(
            {
                customClass:
                {
                    confirmButton: "btn btn-primary",
                    cancelButton: "btn btn-danger mr-2"
                },
                buttonsStyling: false
            });
            swalWithBootstrapButtons
                .fire(
                {
                    title: "請再次確認是否刪除",
                    text: "帳號一旦刪除並無復原可能",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonText: "確定刪除",
                    cancelButtonText: "暫不刪除",
                    reverseButtons: true
                })
                .then(function(result)
                {
                    if (result.value)
                    {
                        swalWithBootstrapButtons.fire(
                            "刪除請求送出",
                            "請稍等跳轉頁面",
                            "success"
                        );
                        setTimeout(function(){$('#deleteTeacher').submit();},1000);
                    }
                    else if (
                        // Read more about handling dismissals
                        result.dismiss === Swal.DismissReason.cancel
                    )
                    {
                        swalWithBootstrapButtons.fire(
                            "刪除請求取消",
                            "刪除帳號請再三確認",
                            "error"
                        );
                    }
                });
        }); // A message with a custom image and CSS animation disabled
        
</script>

<script>
<c:if test="${empVO.empStatus eq 2}">
$("#deleteTeacher").empty();
$("#updateTeacher").empty();
</c:if>
</script>
</body>
</html>