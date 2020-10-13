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
                                            <form class="needs-validation" novalidate>
                                                <div class="panel-content">
                                                    <div class="form-row">
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="validationCustom01">姓名 <span class="text-danger">*</span> </label>
                                                            <input type="text" class="form-control" id="validationCustom01" placeholder="Name" name="name" required>
                                                            <div class="invalid-feedback">
																請輸入姓名
                                                            </div>
                                                        </div>
                                                        
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="validationCustom03">電話 </label>
                                                            <input type="text" class="form-control" id="validationCustom03" placeholder="Phone Number" name="phone" >
                                                            
                                                        </div>
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="validationCustom04">身分證字號 <span class="text-danger">*</span> </label>
                                                            <input type="text" class="form-control" id="validationCustom04" placeholder="Id" name="id" required>
                                                            <div class="invalid-feedback">
																請輸入身分證字號
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label" for="validationCustom05">電子郵件<span class="text-danger">*</span></label>
                                                            <input type="text" class="form-control" id="validationCustom05" placeholder="E-mail" name="mail" required>
                                                            <div class="invalid-feedback">
																請輸入電子郵件
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div >地址</div>
                                                    <div id="twzipcode"></div>
                                                    <div class="form-group">
                                                    	<input type="text" id="validationCustom06" name="address" class="form-control" placeholder="Address">
                                                	</div>
                                                    
                                                    <div class="form-group mb-3">
                                                    	<label class="form-label">上傳大頭照</label>
                                                    	<div class="custom-file">
                                                       	 <input type="file" class="custom-file-input" id="customFile" accept="image/*">
                                                       	 <label class="custom-file-label" for="customFile">Choose Picture</label>
                                                    	</div>
                                               		</div>
                                                    <div class="form-row form-group">
                                                        
                                                        <div class="col-md-4 mb-3" style="text-align:center;">
                                                            <label class="form-label mb-2">編輯班種之權限 <span class="text-danger">*</span></label>
                                                            <div class="mb-2">
                                                            	<label for="readable">可否讀取</label>
                                                                <input type="checkbox" name="readable" data-toggle="toggle" data-size="xs" value="0" id="permission1" >
                                                            </div>
                                                            <div class="mb-2">
                                                            <label for="editable">可否編輯</label>
                                                                <input type="checkbox" name="editable" data-toggle="toggle" data-size="xs" value="0" id="permission2" >
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4 mb-3" style="text-align:center;">
                                                            <label class="form-label mb-2">編輯教室之權限 <span class="text-danger">*</span></label>
                                                            <div class="mb-2">
                                                            	<label for="readable1">可否讀取</label>
                                                                <input type="checkbox" name="readable1" data-toggle="toggle" data-size="xs" value="0" id="permission3" >
                                                            </div>
                                                            <div class="mb-2">
                                                            <label for="editable1">可否編輯</label>
                                                                <input type="checkbox" name="editable1" data-toggle="toggle" data-size="xs" value="0" id="permission4" >
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4 mb-3" style="text-align:center;">
                                                            <label class="form-label mb-2">請假審核之權限 <span class="text-danger">*</span></label>
                                                            <div class="mb-2">
                                                            	<label for="readable2">可否讀取</label>
                                                                <input type="checkbox" name="readable2" data-toggle="toggle" data-size="xs" value="0" id="permission5" >
                                                            </div>
                                                            <div class="mb-2">
                                                            <label for="editable2">可否編輯</label>
                                                                <input type="checkbox" name="editable2" data-toggle="toggle" data-size="xs" value="0" id="permission6" >
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="panel-content border-faded border-left-0 border-right-0 border-bottom-0 d-flex flex-row align-items-center">
                                                    <div class="custom-control custom-checkbox">
                                                        <input type="checkbox" class="custom-control-input" id="invalidCheck" required>
                                                        <label class="custom-control-label" for="invalidCheck">Agree to terms and conditions <span class="text-danger">*</span></label>
                                                        <div class="invalid-feedback">
                                                            You must agree before submitting.
                                                        </div>
                                                    </div>
                                                    <button class="btn btn-primary ml-auto" type="submit"  onclick="javascript:return check();">Submit form</button>
                                                   	<a href="javascript:void(0);" class="btn btn-outline-primary" id="js-sweetalert2-example-8">Try me!</a>
                                                </div>
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
                                                            form.addEventListener('submit', function(event)
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

                                                $("#twzipcode").twzipcode({
                                                    zipcodeIntoDistrict: true, // 郵遞區號自動顯示在區別選單中
                                                    css: ["custom-select col-md-6 mb-3", "custom-select col-md-6 mb-3"], // 自訂 "城市"、"地別" class 名稱 
                                                    countyName: "city", // 自訂城市 select 標籤的 name 值
                                                    districtName: "town" // 自訂區別 select 標籤的 name 值
                                                });
                                            
                                            //控制權限之value值
                                            <%for(int i=1;i<=6;i++){%>
                                            	$("#permission<%=i%>").change(function(){
                                            		if($(this).prop("checked")){
                                            			$(this).val(1);}
                                            	});
                                            <%}%>
                                                
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
        
<script>
//submit
		$("#js-sweetalert2-example-8").on("click", function()
                {
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
                            title: "Are you sure?",
                            text: "You won't be able to revert this!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonText: "Yes, delete it!",
                            cancelButtonText: "No, cancel!",
                            reverseButtons: true
                        })
                        .then(function(result)
                        {
                            if (result.value)
                            {
                                swalWithBootstrapButtons.fire(
                                    "Deleted!",
                                    "Your file has been deleted.",
                                    "success"
                                    
                                );
                            }
                            else if (
                                // Read more about handling dismissals
                                result.dismiss === Swal.DismissReason.cancel
                                
                            )
                            {
                                swalWithBootstrapButtons.fire(
                                    "Cancelled",
                                    "Your imaginary file is safe :)",
                                    "error"
                                );
                            }
                        });
                }); // A message with a custom image and CSS animation disabled
        
        function check() {

        	var msg = "您確定要送出嗎？";
        	if (confirm(msg)==true){
        		return true;
        	}else{
        		return false;
        	}
        }
</script>

</body>
</html>