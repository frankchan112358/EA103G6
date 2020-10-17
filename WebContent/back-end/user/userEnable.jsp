<%@ page import="com.user.model.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	UserVO userVO = (UserVO) request.getAttribute("userVO");  
	
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/back-end/template/head.jsp" %> 
        <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
		<!-- notifications 的css連結 -->
   		<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/notifications/sweetalert2/sweetalert2.bundle.css">
    </head>
    <body>
        <script>
            var classHolder = document.getElementsByTagName("BODY")[0];
        </script>
        <div class="page-wrapper auth">
            <div class="page-inner bg-brand-gradient">
                <div class="page-content-wrapper bg-transparent m-0">
                    <div class="height-10 w-100 shadow-lg px-4 bg-brand-gradient">
                        <div class="d-flex align-items-center container p-0">
                            <div class="page-logo width-mobile-auto m-0 align-items-center justify-content-center p-0 bg-transparent bg-img-none shadow-0 height-9 border-0">
                                <a href="javascript:void(0)" class="page-logo-link press-scale-down d-flex align-items-center">
                                    <i class="fal fa-thumbs-up" style="color: aliceblue;font-size: x-large;"></i>
                                    <span class="page-logo-text mr-1">Work Join Learn</span>
                                </a>
                            </div>
                        </div>
                    </div>
                    
               <div class="container" style="margin-top:4em;width: 30rem;">
               		<div class="card text-center">
						<div class="card-header" style="font-size:1.5em;">帳號啟用</div>
						<div class="card-body">
						<form class="needs-validation" method="post" action="<%=request.getContextPath()%>/user.do">
							<div class="row text-left">
								<div class="col mb-3">
									<label class="form-label " for="name">帳號 (預設為信箱帳號)</label> 
									<input type="text" class="form-control" id="account" placeholder="Account" value="${userVO.mail}" disabled>
								</div>
							</div>
							<div class="row text-left">
								<div class="col mb-3">
									<label class="form-label " for="name">密碼 (密碼長度需大於五碼)</label> 
									<input type="text" class="form-control" id="password" placeholder="Password" name="password" value="${userVO eq null?'':userVO.password }" required>
									<div class="invalid-feedback" id="wrongPassword">請輸入密碼</div>
								</div>
							</div>
							<div class="row text-left">
								<div class="col mb-3">
									<label class="form-label " for="name">請再次確認密碼</label> 
									<input type="password" class="form-control" id="passwordAgain" placeholder="Password" name="passwordAgain" required>
									<div class="invalid-feedback" id="wrongPasswordAgain">請再次輸入密碼</div>
								</div>
							</div>
							<input type="hidden" name="action" value="enableUpdate">
							<input type="hidden" name="account" value="${userVO.mail}">
							<input type="hidden" name="id" value="${userVO.id}">
							<input type="hidden" name="type" value="${userVO.type}">
							<button id="submitAddEmp" class="btn btn-primary ml-auto">確認送出</button>
						</form>
						</div>
					</div>
                   		 <script>
                                                // Example starter JavaScript for disabling form submissions if there are invalid fields
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
                                                            document.getElementById('submitAddEmp').addEventListener('click', function(event)
                                                            {
                                                                event.preventDefault();
                                                                if (form.checkValidity() === false)
                                                                {                                
                                                                    event.stopPropagation();
                                                                } else {
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
                                                                            title: "請再次確認",
                                                                            text: "資料無誤再按送出",
                                                                            type: "warning",
                                                                            showCancelButton: true,
                                                                            confirmButtonText: "確認送出",
                                                                            cancelButtonText: "暫不送出",
                                                                            reverseButtons: true
                                                                        })
                                                                        .then(function(result)
                                                                        {
                                                                            if (result.value)
                                                                            {
                                                                                swalWithBootstrapButtons.fire(
                                                                                    "成功送出",
                                                                                    "新增資料中",
                                                                                    "success"                                                                                    
                                                                                );
                                                                                setTimeout(function(){form.submit()},1000); //一秒後自動跳轉
                                                                                
                                                                            }
                                                                            else if (
                                                                                // Read more about handling dismissals
                                                                                result.dismiss === Swal.DismissReason.cancel
                                                                                
                                                                            )
                                                                            {
                                                                                swalWithBootstrapButtons.fire(
                                                                                    "取消送出",
                                                                                    "請再次確認資料正確與否",
                                                                                    "error"
                                                                                );
                                                                            }
                                                                        });
                                                                }
                                                                form.classList.add('was-validated');
                                                            }, false);
                                                        });
                                                    }, false);
                                                })();
                                                
                                                
                                                
                                              //後端抓到錯誤前端改樣式
                                                
                                                
                                                <c:if test="${not empty errorMsgs.passwordAgain}">
                                                	$("#passwordAgain").attr("class","form-control is-invalid");
                                                	$("#wrongPasswordAgain").text("${errorMsgs.passwordAgain}");
                                                </c:if>
                                                
                                                <c:if test="${not empty errorMsgs.password}">
                                                	$("#password").attr("class","form-control is-invalid");
                                                	$("#wrongPassword").text("${errorMsgs.password}");
                                                </c:if>        
                                                
                                  </script>
                            <div class="position-absolute pos-bottom pos-left pos-right p-3 text-center text-white">
                                2020 © Work Join Learn
                            </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/notifications/sweetalert2/sweetalert2.bundle.js"></script>
    </body>
    <!-- END Body -->
</html>
