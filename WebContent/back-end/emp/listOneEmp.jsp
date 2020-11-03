<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.user.model.*,com.emp.model.*,com.userpermission.model.*,com.userpermission.model.*"%>
<%@ page import="java.util.*"%>
<%

	UserPermissionService checkPermission = new UserPermissionService();

	UserVO userVOForShow = (UserVO) request.getAttribute("userVOForShow"); 
	EmpVO empVOForShow = (EmpVO) request.getAttribute("empVOForShow"); 
	
	pageContext.setAttribute("checkPermission", checkPermission);
	
	UserPermissionService userPermissionSvc=new UserPermissionService();
	List<UserPermissionVO> userPermissionList=userPermissionSvc.getAllByThemselves(userVOForShow.getUserNo());
 
	pageContext.setAttribute("userPermissionList", userPermissionList);
	
	
%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
    
    <link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
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
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/emp/empList.jsp?goto=empList">導師管理</a></li>
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
                                        
                                        <div class="panel-toolbar">
                                            <button id="addEmpBtn" data-toggle="modal" data-target="#addEmp" type="button" class="btn btn-outline-info btn-pills waves-effect waves-themed">顯示權限</button>
                                        </div>
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
													<th>導師編號</th>
													<td>${empVOForShow.empNo}</td>									
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
													<th>帳號狀態</th>
													<td>${userVOForShow.enable==0?"停用中":"啟用中"}</td>									
												</tr>
                                                <tr>
													<th>導師狀態</th>
													<td>
														<c:choose>
                                                    			<c:when test="${empVOForShow.empStatus==0}">離職</c:when>
                                                    			<c:when test="${empVOForShow.empStatus==1}">在職</c:when>
                                                    			<c:when test="${empVOForShow.empStatus==2}">停職</c:when>
                                                    	</c:choose>											
													</td>									
												</tr>
                                                <tr>
													<th>地址</th>
													<td>${userVOForShow.address eq null?"暫無輸入":userVOForShow.address}</td>									
												</tr>
																					
                                            </table>
                                            
                                            <div class="demo row">
                                            <form id="deleteEmp" method="post" action="<%=request.getContextPath()%>/user.do">
                                            	<button id="submitDeleteEmp" class="btn btn-danger ml-auto">刪除</button>
                                            	<input type="hidden" name="action" value="delete">
                                            	<input type="hidden" name="userNo" value="<%=userVOForShow.getUserNo()%>"> 
                                            </form>
                                            <form id="updateEmp" method="post" action="<%=request.getContextPath()%>/user.do" id="updateEmp">
                                            	<input type="hidden" name="userNo" value="<%=userVOForShow.getUserNo()%>"> 
												<input type="hidden" name="action" value="getOne_For_Update">
                                            	<button id="submitUpdateEmp" class="btn btn-primary ml-auto">修改</button>
                                            </form>
                                            </div>
                                            <!-- datatable end -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
					<div class="modal fade" id="addEmp" tabindex="-1" role="dialog"
						aria-hidden="true">
						<div class="modal-dialog modal-md modal-dialog-centered"
							role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h4 class="modal-title" style="font-size:2em">顯示權限</h4>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true"><i class="fal fa-times"></i></span>
									</button>
								</div>
								<div class="modal-body">
									<div class="form-group">

										<div class="mb-3 " style="text-align: center; border-bottom:#21679C 1px solid;">
											<label class="form-label mb-2" style="font-size:1.5em">班級管理之權限 </label>
											<div class="mb-2">
												<label for="editable1">可否編輯</label> <input type="checkbox"
													name="editable1" data-toggle="toggle" data-size="xs"
													 id="permission1" disabled>
											</div>
										</div>
										<div class="mb-3" style="text-align: center;border-bottom:#21679C 1px solid;">
											<label class="form-label mb-2" style="font-size:1.5em">課程管理之權限 </label>
											<div class="mb-2">
												<label for="editable2">可否編輯</label> <input type="checkbox"
													name="editable2" data-toggle="toggle" data-size="xs"
													 id="permission2" disabled>
											</div>
										</div>
										<div class="mb-3" style="text-align: center;border-bottom:#21679C 1px solid;">
											<label class="form-label mb-2" style="font-size:1.5em">班種管理之權限 </label>
											<div class="mb-2">
												<label for="editable3">可否編輯</label> <input type="checkbox"
													name="editable3" data-toggle="toggle" data-size="xs"
													 id="permission3" disabled>
											</div>
										</div>
										<div class="mb-3" style="text-align: center;">
											<label class="form-label mb-2" style="font-size:1.5em">成員管理之權限</label>
											<div class="mb-2">
												<label for="editable4">可否編輯</label> <input type="checkbox"
													name="editable4" data-toggle="toggle" data-size="xs"
													 id="permission4" disabled>
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
                <%@ include file="/back-end/template/footer.jsp" %>
            </div>
        </div>
    </div>
    
    
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %>
    


<script>
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


<script>
//顯示其原本的權限值
<c:forEach var="userPermission" items="${userPermissionList}">
	<c:choose>	
		<c:when test="${userPermission.permissionNo eq '1' and userPermission.permissionEdit eq 1}">
			$("#permission1").attr("checked",true);
		</c:when>
		<c:when test="${userPermission.permissionNo eq '2'and userPermission.permissionEdit eq 1}">
			$("#permission2").attr("checked",true);
		</c:when>
		<c:when test="${userPermission.permissionNo eq '3' and userPermission.permissionEdit eq 1}">
			$("#permission3").attr("checked",true);
		</c:when>
		<c:when test="${userPermission.permissionNo eq '4' and userPermission.permissionEdit eq 1}">
			$("#permission4").attr("checked",true);
		</c:when>
	</c:choose>
</c:forEach>

</script>

	<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>

<script>
<c:if test="${userVOForShow.userNo eq userVO.userNo}">
	$("#deleteEmp").empty();
</c:if>
</script>

</body>
</html>