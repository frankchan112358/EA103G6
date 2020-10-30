<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.user.model.*"%>

<% 
UserVO userVOForUpdateAccount= (UserVO)request.getSession().getAttribute("userVO");
UserVO userVOForMistake = (UserVO) request.getAttribute("userVOForUpdateAccount");
if(userVOForMistake!=null)userVOForUpdateAccount=userVOForMistake;

pageContext.setAttribute("userVOForUpdateAccount", userVOForUpdateAccount);
%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
		
<style>   
    img {
	max-width: 100%;
	max-height: 100%;
	border:2px #C4B1B1 dashed;
	}

	#picDiv{
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
                        <li class="breadcrumb-item">修改個人資料</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-democrat'></i> 修改個人資料
                        </h1>
                    </div>
           					<div id="panel-2" class="panel">
                                    <div class="panel-hdr">
                                        <h2>
                                            Update <span class="fw-300"><i>Account</i></span>
                                        </h2>
                                    </div>
                                    <div class="panel-container show">
                                        
                                        <div class="panel-content p-0">
                                            <form class="needs-validation"  method="post" action="<%=request.getContextPath()%>/user.do" enctype="multipart/form-data" novalidate>
                                                <div class="panel-content">
                                                	<div  id="picDiv">
														<c:if test="${userVOForUpdateAccount.photo eq null}">
															<img src="<%=request.getContextPath() %>/images/noPicture.png">
														</c:if>
														<c:if test="${userVOForUpdateAccount.photo ne null}">
															<img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${userVOForUpdateAccount.userNo}">
														</c:if>
                                                        
                                                    </div>
                                                    <div class="form-row">
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="name">姓名 <span class="text-danger">*</span> </label>
                                                            <input type="text" class="form-control" id="name" placeholder="Name" name="name" value="${userVOForUpdateAccount eq null?'':userVOForUpdateAccount.name }" required>
                                                            <div class="invalid-feedback" id="wrongName">
																請輸入姓名
                                                            </div>
                                                        </div>
                                                        
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="phone">手機號碼 </label>
                                                            <input type="text" class="form-control" id="phone" placeholder="Phone Number" value="${userVOForUpdateAccount eq null?'':userVOForUpdateAccount.phone }" name="phone" >
                                                            <div class="invalid-feedback" id="wrongPhone">
																僅接受台灣連絡電話且僅能輸入10碼
                                                            </div>
                                                        </div>
                                                        
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="id">身分證字號 <span class="text-danger">*</span> </label>
                                                            <input type="text" class="form-control" id="id" placeholder="Id Number" name="id" value="${userVOForUpdateAccount eq null?'':userVOForUpdateAccount.id }" required>
                                                            <div class="invalid-feedback" id="wrongId">
																請輸入身分證字號
                                                            </div>
                                                            
                                                        </div>
                                                        
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="mail">電子郵件<span class="text-danger">*</span></label>
                                                            <input type="text" class="form-control" id="mail" placeholder="E-mail" name="mail" value="${userVOForUpdateAccount eq null?'':userVOForUpdateAccount.mail }" required>
                                                            <div class="invalid-feedback" id="wrongMail">
																請輸入電子郵件
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div >地址</div>
                                                    <div id="twzipcode"></div>
                                                    <div class="form-group">
                                                    	<input type="text" id="address" name="address" class="form-control" placeholder="Address" value="${userVOForUpdateAccount eq null?'':userVOForUpdateAccount.address }">
                                                	</div>
                                                    
                                                    <div class="form-group mb-3">
                                                    	<label class="form-label">上傳大頭照</label>
                                                    	<div class="custom-file">
                                                       	 <input type="file" class="custom-file-input" id="photo" accept="image/*" name="photo">
                                                       	 <label class="custom-file-label" for="customFile">Choose Picture</label>
                                                    	</div>
                                               		</div>
                                                    
                                                </div>
                                                <div class="panel-content border-faded border-left-0 border-right-0 border-bottom-0 d-flex flex-row align-items-center">
                                                    <div class="custom-control custom-checkbox">
                                                        <input type="checkbox" class="custom-control-input" id="invalidCheck" required>
                                                        <label class="custom-control-label" for="invalidCheck">確認送出 <span class="text-danger">*</span></label>
                                                        <div class="invalid-feedback">
                                                            	您必須再次確認後送出
                                                        </div>
                                                    </div>
                                                    <button id="updateTeacher" class="btn btn-primary ml-auto">提交修改</button>
                                                </div>
                                                <input type="hidden" name="action" value="update_themselves">
                                            </form>
                                            
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
                                                            document.getElementById('updateTeacher').addEventListener('click', function(event)
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

                                                $("#twzipcode").twzipcode({
                                                    zipcodeIntoDistrict: true, // 郵遞區號自動顯示在區別選單中
                                                    css: ["custom-select col-md-6 mb-3", "custom-select col-md-6 mb-3"], // 自訂 "城市"、"地別" class 名稱 
                                                    countyName: "city", // 自訂城市 select 標籤的 name 值
                                                    districtName: "town" // 自訂區別 select 標籤的 name 值
                                                });
                                            
                                            
                                            
                                            //地址下拉式表單自動填入
                                            $("select[name='city']").change(function(){
                                            	$("#address").val($("select[name='city']").find(":selected").val()+$("select[name='town']").find(":selected").val());
                                            	$("select[name='town']").change(function(){
                                            		$("#address").val($("select[name='city']").find(":selected").val()+$("select[name='town']").find(":selected").val());
                                            	});
                                            });
                                            	                                                                                      
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
    
 	<script>
  	//後端抓到錯誤前端改樣式
    <c:if test="${errorMsgs.name ne null}">
    	$("#name").attr("class","form-control is-invalid");
    	$("#wrongName").text("${errorMsgs.name}");
    </c:if>
    
    <c:if test="${errorMsgs.id ne null}">
    	$("#id").attr("class","form-control is-invalid");
    	$("#wrongId").text("${errorMsgs.id}");
    </c:if>
    
    <c:if test="${errorMsgs.phone ne null}">
    	$("#phone").attr("class","form-control is-invalid");
    </c:if>
    
    <c:if test="${errorMsgs.mail ne null}">
    	$("#mail").attr("class","form-control is-invalid");
    	$("#wrongMail").text("${errorMsgs.mail}");
    </c:if>
    
    
    </script>
</body>
</html>