<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.user.model.*"%>

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
                                            ${userVO.name} <span class="fw-300"><i>個人資料</i></span>
                                        </h2>
                                    </div>
                                    <div class="panel-container show">
                                        <div class="panel-content">
                                            
                                            <!-- datatable start -->
                                            <table id="dt-basic-example" class="table table-responsive dtr-details" >
											
											<tr>
												<td>
													<div id="pic" >
														<c:if test="${userVO.photo eq null}">
															<img src="<%=request.getContextPath() %>/images/noPicture.png">
														</c:if>
														<c:if test="${userVO.photo ne null}">
															<img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${userVO.userNo}">
														</c:if>
													</div>
												<td>
											
											</tr>
											
											<tr data-dt-row="14">
													<th>姓名</th>
													<td>${userVO.name}</td>									
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
													<th>地址</th>
													<td>${userVO.address eq null?"暫無輸入":userVO.address}</td>									
												</tr>
																					
                                            </table>
                                            
                                            <div class="demo row">
                                            <form id="deleteEmp" method="post" action="<%=request.getContextPath()%>/user.do">
                                            	<button id="submitDeleteEmp" class="btn btn-danger ml-auto">修改密碼</button>
                                            </form>
                                            <form id="updateEmp" method="post" action="<%=request.getContextPath()%>/back-end/accountmanager/updateThemselves.jsp" id="updateEmp">
                                            	<button id="submitUpdateEmp" class="btn btn-primary ml-auto">個資修改</button>
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
    


<script> //暫時保留可視情況刪除
	$("#submitDeleteEmp").on("click", function(event) {
		event.preventDefault();
		var swalWithBootstrapButtons = Swal.mixin({
			customClass : {
				confirmButton : "btn btn-primary",
				cancelButton : "btn btn-danger mr-2"
			},
			buttonsStyling : false
		});
		swalWithBootstrapButtons.fire({
			title : "請再次確認是否刪除",
			text : "帳號一旦刪除並無復原可能",
			type : "warning",
			showCancelButton : true,
			confirmButtonText : "確定刪除",
			cancelButtonText : "暫不刪除",
			reverseButtons : true
		}).then(function(result) {
			if (result.value) {
				swalWithBootstrapButtons.fire("刪除請求送出", "請稍等跳轉頁面", "success");
				setTimeout(function() {
					$('#deleteEmp').submit();
				}, 1000);
			} else if (
			// Read more about handling dismissals
			result.dismiss === Swal.DismissReason.cancel) {
				swalWithBootstrapButtons.fire("刪除請求取消", "刪除帳號請再三確認", "error");
			}
		});
	}); // A message with a custom image and CSS animation disabled
</script>

</body>
</html>