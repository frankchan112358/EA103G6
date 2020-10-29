<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*,com.student.model.*"%>
<%@ page import="java.util.*"%>



<!DOCTYPE html>
<html>
<head>
    <%@ include file="/front-end/template/head.jsp" %> 
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
            <%@ include file="/front-end/template/left_aside.jsp" %> 
            <div class="page-content-wrapper">
                <%@ include file="/front-end/template/header.jsp" %> 
                <main id="js-page-content" role="main" class="page-content">
                    
                    
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-democrat'></i>${userVO.name}
                        </h1>
                    </div>
                    <div class="row">
                            <div class="col-xl-12">
                                <div id="panel-1" class="panel">
                                    <div class="panel-hdr">
                                        <h2>
                                             <span class="fw-300"><i>個人資料</i></span>
                                        </h2>
                                    </div>
                                   
                                            
                                            
                                            <table id="dt-basic-example" class="table table-responsive dtr-details" >
									
											
											<tr>
												<td>
													<div id="pic" >													
														<c:if test="${userVO.photo eq null }">
														<img src="<%=request.getContextPath()%>/images/noPicture.png">
														</c:if>
														<c:if test="${userVO.photo ne null }">
														<img src="<%=request.getContextPath()%>/user.do?action=getPhoto&userNo=${userVO.userNo}">
														</c:if>
													</div>
												<td>											
											</tr>
											
											<tr data-dt-row="14">
													<th>姓名</th>
													<td>${userVO.name}</td>									
												</tr>
                                                <tr>
													<th>學員編號</th>
													<td>${studentVO.studentNo}</td>									
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
													<th>自我介紹</th>
													<td>${studentVO.studentDescription eq null?"暫無輸入":studentVO.studentDescription}</td>									
												</tr>
                                               
                                              
                                                <tr>
													<th>地址</th>
													<td>${userVO.address eq null?"暫無輸入":userVO.address}</td>									
												</tr>
																					
                                            </table>
                                             <div class="demo row">
                                            

                                              <form method="post" action="<%=request.getContextPath()%>/user.do">
                                            	 <%-- 註解 <input type="hidden" name="userNo" value="<%=userVO.getUserNo()%>"> --%>
												<input type="hidden" name="action" value="getOne_For_Update_stuselves">
											
                                            	<button id="submitUpdateStudent" class="btn btn-primary ml-auto" >修改基本資料</button>
                                            </form>
                                             <form method="post" action="<%=request.getContextPath()%>/user.do">
												 <input type="hidden" name="id" value="${userVO.id}"> 
												  <input type="hidden" name="mail" value="${userVO.mail}"> 
												<input type="hidden" name="action" value="update_passwordstu">
                                            	<button id="submitUpdateStudent" class="btn btn-primary ml-auto" >修改密碼</button>
                                            </form>
                                             </div>
                                            </div>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                </main>
                <!-- this overlay is activated only when mobile menu is triggered -->
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div> <!-- END Page Content -->
                
               
                
                
                
                
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
                <%@ include file="/front-end/template/footer.jsp" %>
     
    
    <%@ include file="/front-end/template/quick_menu.jsp" %>
    <%@ include file="/front-end/template/messager.jsp" %>
    <%@ include file="/front-end/template/basic_js.jsp" %>
    
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/notifications/sweetalert2/sweetalert2.bundle.js"></script>
    
    
    <script>

$("#deleteStudent").on("click", function(event)
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
                        setTimeout(function(){$('#deleteStudent').submit();},1000);
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
</body>
</html>