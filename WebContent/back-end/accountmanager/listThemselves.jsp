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
                                            
                                            <div class="demo row" id="managerCannotDelete">
                                            	<button id="submitDeleteEmp" data-toggle="modal" data-target="#sendCodeForCheck" type="button" class="btn btn-danger ml-auto">修改密碼</button>
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
                        
                        
     <div class="modal fade" id="sendCodeForCheck" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-md modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">寄送驗證信</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"><i class="fal fa-times"></i></span>
                    </button>
                </div>
                  <form id="form-leave" class="needs-validation" novalidate>
               		<div class="modal-body">
                        <div class="form-group">
                            <div class="row text-left">
								<div class="col mb-2" id="codeSpace">
									<label class="form-label " for="code">驗證碼</label> 
									<input type="text" class="form-control" id="code" placeholder="Code" name="code" required>
									<div class="invalid-feedback" id="wrongCode">請輸入驗證碼</div>
								</div>
							</div>
                            <div class="row text-left">
								<div class="col mb-2">
									<button type="button" class="btn btn-outline-success btn-pills btn-lg btn-block" id="sendMail">寄送驗證碼
                                    </button>
								</div>
							</div>
                        </div>
                	</div>
                	<div class="modal-footer">
                    	<button type="button" class="btn btn-primary" id="submitCode">送出</button>
                	</div>
                </form>
                <script>
    			(function()
           		{
                'use strict';
                window.addEventListener('load', function()
                {
                    // Fetch all the forms we want to apply custom Bootstrap validation styles to
                    var forms = document.getElementsByClassName('needs-validation');
                    // Loop over them and prevent submission
                    var validation = Array.prototype.filter.call(forms, function(form)
                    {
                        form.addEventListener('click', function(event)
                        {
                            if (form.checkValidity() === false)
                            {
                                event.preventDefault();
                                event.stopPropagation();
                            }
                            form.classList.add('was-validated');
                        }, false);
                    });
                }, false);
            })();
    		</script>
            </div>
        </div>
    </div>
    
    
     <div class="modal fade" id="updatePSW" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-md modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">修改密碼</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"><i class="fal fa-times"></i></span>
                    </button>
                </div>
                  <form id="form-leave" class="needs-validation" novalidate>
               		<div class="modal-body">
                        <div class="form-group">
                            <div class="row text-left">
								<div class="col mb-2" >
									<label class="form-label " for="oldPSW">舊密碼</label> 
									<input type="password" class="form-control" id="oldPSW" placeholder="OldPassword" name="oldPSW" required>
									<div class="invalid-feedback" id="wrongOldPSW">請輸入舊密碼</div>
								</div>
							</div>
                            <div class="row text-left">
								<div class="col mb-2" >
									<label class="form-label " for="newPSW">新密碼</label> 
									<input type="password" class="form-control" id="newPSW" placeholder="Password" name="newPSW" required>
									<div class="invalid-feedback" id="wrongNewPSW">請輸入新密碼</div>
								</div>
							</div>
                            <div class="row text-left">
								<div class="col mb-2" >
									<label class="form-label " for="newPSWCheck">再次輸入新密碼</label> 
									<input type="password" class="form-control" id="newPSWCheck" placeholder="Password" name="newPSWCheck" required>
									<div class="invalid-feedback" id="wrongNewPSWCheck">請再次輸入新密碼</div>
								</div>
							</div>
                        </div>
                	</div>
                	<div class="modal-footer">
                    	<button type="button" class="btn btn-primary" id="submitPSW">送出</button>
                	</div>
                </form>
                <script>
    			(function()
           		{
                'use strict';
                window.addEventListener('load', function()
                {
                    // Fetch all the forms we want to apply custom Bootstrap validation styles to
                    var forms = document.getElementsByClassName('needs-validation');
                    // Loop over them and prevent submission
                    var validation = Array.prototype.filter.call(forms, function(form)
                    {
                        form.addEventListener('click', function(event)
                        {
                            if (form.checkValidity() === false)
                            {
                                event.preventDefault();
                                event.stopPropagation();
                            }
                            form.classList.add('was-validated');
                        }, false);
                    });
                }, false);
            })();
    		</script>
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
	$("#submitPSW").on("click", function(event) {
		$('#updatePSW').modal('hide');
		event.preventDefault();
		var swalWithBootstrapButtons = Swal.mixin({
			customClass : {
				confirmButton : "btn btn-primary",
				cancelButton : "btn btn-danger mr-2"
			},
			buttonsStyling : false
		});
		swalWithBootstrapButtons.fire({
			title : "請再次確認",
			text : "資料無誤再按送出",
			type : "warning",
			showCancelButton : true,
			confirmButtonText : "確定送出",
			cancelButtonText : "暫不送出",
			reverseButtons : true
		}).then(function(result) {
			if (result.value) {
				submitPSW(); //呼叫方法
			} else if (
			// Read more about handling dismissals
			result.dismiss === Swal.DismissReason.cancel) {
				$('#updatePSW').modal('show');
			}
		});
	}); // A message with a custom image and CSS animation disabled
	
	
	$("#sendMail").click(function(){  
		$("#codeSpace").show("fast");
		$("#submitCode").show("fast");
		$("#sendMail").text("請在一分鐘內完成驗證");
		$("#sendMail").attr("class","btn btn-outline-danger btn-lg btn-block");
		$("#sendMail").attr('disabled', true);


		$.ajax({
			 type: "POST",
			 url: '<%=request.getContextPath()%>/user.do', //此對應form:action，請去對應後端程式
			 data: {
                 action: 'updatePassword',
                 CodeAction:'sendCode'
             },
			 success: function (data){ 
				 var down = setTimeout(freshSendMail, 1000*60) //一分鐘內一到馬上禁止輸入，後端仍也有錯誤處理
		     }
        });
	});
	
	$("#submitCode").click(function(event){ 
		$.ajax({
			 type: "POST",
			 url: '<%=request.getContextPath()%>/user.do', //此對應form:action，請去對應後端程式
			 data: {
                action: 'updatePassword',
                CodeAction:'checkCode',
                inputCode:$("#code").val()
            },
			 success: function (data){  //後端在設定時已經拆解過json
				if("mistake"===data.status){ 
					$("#code").val("");
					$("#wrongCode").text(data.mistake);
				}
				 
				if("success"===data.status){
					$("#sendCodeForCheck").modal("hide");
					$("#updatePSW").modal("show");
				}
				
		     }
       });
	});
	
	$(document).ready(function (){
		$("#codeSpace").hide("fast");
		$("#submitCode").hide("fast");
		
	});
	
	
	function freshSendMail() {
		$("#codeSpace").hide("fast");
		$("#sendMail").text("重新寄送驗證碼");
		$("#sendMail").attr("class","btn btn-outline-success btn-pills btn-lg btn-block");
		$("#sendMail").attr('disabled', false);
		$("#submitCode").hide("fast");

	}
	function submitPSW(){
		$.ajax({
			 type: "POST",
			 url: '<%=request.getContextPath()%>/user.do', //此對應form:action，請去對應後端程式
			 data: {
               action: 'updatePassword',
               CodeAction:'checkPSW',
               oldPSW:$("#oldPSW").val(),
               newPSW:$("#newPSW").val(),
               newPSWCheck:$("#newPSWCheck").val()
           },
			 success: function (data){  //後端在設定時已經拆解過json
				if("mistake"===data.status){ 
					$("#updatePSW").modal("show");
					if(data.misOldPSW != null){
						$("#oldPSW").val("");
						$("#wrongOldPSW").text(data.misOldPSW);
					}
					if(data.misNewPSW != null){
						$("#newPSW").val("");
						$("#wrongNewPSW").text(data.misNewPSW);
					}
					if(data.misNewPSWCheck!=null){
						$("#newPSWCheck").val("");
						$("#wrongNewPSWCheck").text(data.misNewPSWCheck);
					}
					
				}
				 
				if("success"===data.status){
					$("#updatePSW").modal("hide");
					Swal.fire("修改成功", "您的密碼已修改完成", "success");
				}
				
		     }
      });
	}
</script>

<script>
<c:if test="${userVO.userNo eq 'U000001'}">
$("#managerCannotDelete").empty();
</c:if>
</script>

</body>
</html>