<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*,com.teacher.model.*"%>
<%@ page import="java.util.*"%>
<%
	UserVO userVO = (UserVO) request.getAttribute("userVO");  
	TeacherVO teacherVO=(TeacherVO) request.getAttribute("teacherVO");
%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
    
    <link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>
	
<!-- notifications 的css連結 -->
   <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/notifications/sweetalert2/sweetalert2.bundle.css">
	
		
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
           					<div id="panel-2" class="panel">
                                    <div class="panel-hdr">
                                        <h2>
                                            Add <span class="fw-300"><i>Employee</i></span>
                                        </h2>
                                        <div class="panel-toolbar">
                                            <button class="btn btn-panel" data-action="panel-collapse" data-toggle="tooltip" data-offset="0,10" data-original-title="Collapse"></button>
                                            <button class="btn btn-panel" data-action="panel-fullscreen" data-toggle="tooltip" data-offset="0,10" data-original-title="Fullscreen"></button>
                                            <button class="btn btn-panel" data-action="panel-close" data-toggle="tooltip" data-offset="0,10" data-original-title="Close"></button>
                                        </div>
                                    </div>
                                    <div class="panel-container show">
                                        
                                        <div class="panel-content p-0">
                                            <form class="needs-validation" method="post" action="<%=request.getContextPath()%>/user.do" enctype="multipart/form-data" novalidate>
                                                <div class="panel-content">
                                                    <div class="form-row">
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="name">姓名 <span class="text-danger">*</span> </label>
                                                            <input type="text" class="form-control" id="name" placeholder="Name" name="name" value="${userVO eq null?'':userVO.name }" required>
                                                            <div class="invalid-feedback" id="wrongName">
																請輸入姓名
                                                            </div>
                                                        </div>
                                                        
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="phone">手機號碼 </label>
                                                            <input type="text" class="form-control" id="phone" placeholder="Phone Number" value="${userVO eq null?'':userVO.phone }" name="phone" >
                                                            <div class="invalid-feedback" id="wrongPhone">
																僅接受台灣連絡電話且僅能輸入10碼
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="id">身分證字號 <span class="text-danger">*</span> </label>
                                                            <input type="text" class="form-control" id="id" placeholder="Id" name="id" value="${userVO eq null?'':userVO.id }" required>
                                                            <div class="invalid-feedback" id="wrongId">
																請輸入身分證字號
                                                            </div>
                                                            
                                                        </div>
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="mail">電子郵件<span class="text-danger">*</span></label>
                                                            <input type="text" class="form-control" id="mail" placeholder="E-mail" name="mail" value="${userVO eq null?'':userVO.mail }" required>
                                                            <div class="invalid-feedback" id="wrongMail">
																請輸入電子郵件
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div >地址</div>
                                                    <div id="twzipcode"></div>
                                                    <div class="form-group">
                                                    	<input type="text" id="address" name="address" class="form-control" placeholder="Address" value="${userVO eq null?'':userVO.address }">
                                                	</div>
                                                    
                                                    <div class="form-group mb-3">
                                                         <label class="form-label" for="skill">講師技術簡介  </label>
                                                         <input type="text" class="form-control" id="id" placeholder="Skill" name="skill" value="${teacherVO eq null?'':teacherVO.skill}" >
                                                    </div>
                                                    
                                                    <div class="form-group mb-3">
                                                         <label class="form-label" for="description">講師詳細描述 </label>
                                                         <textarea class="form-control" id="description"  name="description" placeholder="Description" >${teacherVO eq null?'':teacherVO.description}</textarea>
                                                    </div>
                                                    
                                                    <div class="form-group mb-3">
                                                    	<label class="form-label">上傳大頭照</label>
                                                    	<div class="custom-file">
                                                       	 <input type="file" class="custom-file-input" id="photo" accept="image/*" name="photo">
                                                       	 <label class="custom-file-label" for="customFile">Choose Picture</label>
                                                    	</div>
                                               		</div>
                                                    <div class="form-row form-group">
                                                        
                                                        
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
                                                    <button id="submitAddEmp" class="btn btn-primary ml-auto">Submit form</button>
                                                </div>
                                            <input type="hidden" name="action" value="insert"> 
                                            <input type="hidden" name="type" value="1"> 
                                                
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

                                                $("#twzipcode").twzipcode({
                                                    zipcodeIntoDistrict: true, // 郵遞區號自動顯示在區別選單中
                                                    css: ["custom-select col-md-6 mb-3", "custom-select col-md-6 mb-3"], // 自訂 "城市"、"地別" class 名稱 
                                                    countyName: "city", // 自訂城市 select 標籤的 name 值
                                                    districtName: "town" // 自訂區別 select 標籤的 name 值
                                                });
                                            
                                            
                                            //後端抓到錯誤前端改樣式
                                            <c:if test="${not empty errorMsgs.name}">
                                            	$("#name").attr("class","form-control is-invalid");
                                            	$("#wrongName").text("${errorMsgs.name}");
                                            </c:if>
                                            
                                            <c:if test="${not empty errorMsgs.id}">
                                            	$("#id").attr("class","form-control is-invalid");
                                            	$("#wrongId").text("${errorMsgs.id}");
                                            </c:if>
                                            
                                            <c:if test="${not empty errorMsgs.phone}">
                                            	$("#phone").attr("class","form-control is-invalid");
                                            </c:if>
                                            
                                            <c:if test="${not empty errorMsgs.mail}">
                                            	$("#mail").attr("class","form-control is-invalid");
                                            	$("#wrongMail").text("${errorMsgs.mail}");
                                            </c:if>
                                            
                                            
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
	<!--     把法蘭克原本預設的inclide的js刪掉 -->
    
    
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/vendors.bundle.js"></script>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/app.bundle.js"></script>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/notifications/sweetalert2/sweetalert2.bundle.js"></script>
        
</body>
</html>