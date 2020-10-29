<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*,com.student.model.*"%>
<%@ page import="java.util.*"%>
<%
	UserService userSvc = new UserService();
	List<UserVO> userList = userSvc.getAll();
	
	StudentService studentSvc =new StudentService();
	List<StudentVO> studentList =studentSvc.getAll();
	
	pageContext.setAttribute("userList", userList);
	pageContext.setAttribute("studentList", studentList);
	
	UserVO userVOForInsert = (UserVO) request.getAttribute("userVOForInsert");
	
%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
    <!-- notifications 的css連結 -->
   	<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/notifications/sweetalert2/sweetalert2.bundle.css">
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
                        <li class="breadcrumb-item">成員管理</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-democrat'></i> 學員管理
                        </h1>
                    </div>
                    <div class="row">
                            <div class="col-xl-12">
                                <div id="panel-1" class="panel">
                                    <div class="panel-hdr">
                                        <h2>
                                            學員列表 <span class="fw-300"><i></i></span>
                                        </h2>
                                        <div class="panel-toolbar">
                                            <button id="addStudentBtn" data-toggle="modal" data-target="#addStudent" type="button" class="btn btn-outline-info btn-pills waves-effect waves-themed">新增學員</button>
                                        </div>
                                        
                                    </div>
                                    <div class="panel-container show">
                                        <div class="panel-content">
                                            
                                            <!-- datatable start -->
                                            <table id="dt-basic-example" class="table table-bordered table-hover table-striped w-100">
                                                <thead>
                                                    <tr>
                                                        <th>學員編號</th>
                                                        <th>姓名</th>
                                                        <th>班級</th>
                                                        <th>信箱</th>
                                                        <th>電話</th>
                                                        <th>敘述</th>                                                       
                                                        <th>狀態</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="studentVO" items="${studentList}">
                                                    <c:forEach var="userVO" items="${userList}">                                                                                                                                                     
                                                    	<c:if test="${studentVO.userNo eq userVO.userNo}">
                                                    	<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />
                                                    		<tr onclick="location.href='<%=request.getContextPath()%>/user.do?action=getOne_For_Display&userNo=${userVO.userNo}';">
                                                    		<td>${studentVO.studentNo}</td>
                                                    		<td>${userVO.name}</td>
                                                    		<td>
															<c:forEach var="banjiVO" items="${banjiSvc.all}">
															<c:if test="${studentVO.banjiNo eq banjiVO.banjiNo}">${banjiVO.banjiName}		
															</c:if>
															</c:forEach>
															</td>
                                                    		<td>${userVO.mail}</td>
                                                    		<td>${userVO.phone eq null?"暫無資料":userVO.phone}</td>
                                                    		<td>${studentVO.studentDescription eq null?"暫無資料":studentVO.studentDescription}</td>
                                                    		<td><c:choose>                                                    	
                                                    			<c:when test="${banjiSvc.getOneBanji(studentVO.banjiNo).status==0}">結訓</c:when>
                                                    			<c:when test="${banjiSvc.getOneBanji(studentVO.banjiNo).status==1}">在訓中</c:when>
                                                    			<c:when test="${banjiSvc.getOneBanji(studentVO.banjiNo).status==2}">班級延期</c:when>
                                                    			<c:when test="${banjiSvc.getOneBanji(studentVO.banjiNo).status==3}">未開課</c:when>
                                                    		</c:choose>	</td>
                                                    		</tr>
                                                    	</c:if>
                                                    </c:forEach>
                                                    </c:forEach>

                                                </tbody>
                                                <tfoot>
                                                    <tr>
                                                        <th>學員編號</th>
                                                        <th>姓名</th>
                                                        <th>班級</th>
                                                        <th>信箱</th>
                                                        <th>電話</th>
                                                        <th>敘述</th>
                                                        <th>狀態</th>
                                                    </tr>
                                                </tfoot>
                                            </table>
                                            <!-- datatable end -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
     <div class="modal fade" id="addStudent" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-md modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">新增學員</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"><i class="fal fa-times"></i></span>
                    </button>
                </div>
                  <form id="form-leave" class="needs-validation" method="post" action="<%=request.getContextPath()%>/user.do" novalidate>
               		<div class="modal-body">
                        <div class="form-group">
                            <div class="row text-left">
								<div class="col mb-2">
									<label class="form-label " for="name">姓名</label> 
									<input type="text" class="form-control" id="name" placeholder="Name" name="name" value="${userVOForInsert eq null?'':userVOForInsert.name }" required>
									<div class="invalid-feedback" id="wrongName">請輸入姓名</div>
								</div>
							</div>
							<div class="row text-left">
								<div class="col mb-2">
									<label class="form-label " for="id">身分證字號</label> 
									<input type="text" class="form-control" id="id" placeholder="ID Number" name="id" value="${userVOForInsert eq null?'':userVOForInsert.id}" required>
									<div class="invalid-feedback" id="wrongId">請輸入身分證字號</div>
								</div>
							</div>
							<div class="row text-left">
								<div class="col mb-2">
									<label class="form-label " for="banji">班級<span class="text-danger">*</span></label> 
									<select class="custom-select" id="banjiNo" name="banjiNo"  required>
									<c:forEach var="banjiVO" items="${banjiSvc.all}">
									<option value="${banjiVO.banjiNo}"
									${(banjiVO.banjiNo==banjiVO.banjiNo)? 'selected':'' } >${banjiVO.banjiName}
									</c:forEach>
									</select>
							</div>
							
                            <div class="row text-left">
								<div class="col mb-2">
									<label class="form-label " for="mail">信箱</label> 
									<input type="text" class="form-control" id="mail" placeholder="E-mail" name="mail" value="${userVOForInsert eq null?'':userVOForInsert.mail}" required>
									<div class="invalid-feedback" id="wrongMail">請輸入信箱</div>
								</div>
							</div>
                            
                        </div>
                        </div>
                	</div>
                	<input type="hidden" name="action" value="insert"> 
                    <input type="hidden" name="type" value="0"> 
                	<div class="modal-footer">
                    	<button type="button" class="btn btn-secondary" data-dismiss="modal" id="cancel">取消</button>
                    	<button type="button" class="btn btn-primary" id="submitAddStudent">送出</button>
                	</div>
                </form>
            </div>
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
                document.getElementById('submitAddStudent').addEventListener('click', function(event)
                {
                    event.preventDefault();
                    if (form.checkValidity() === false)
                    {                                                                   
                        event.stopPropagation();
                    } else {
                    	$('#addStudent').modal('hide')
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
                                    setTimeout(function(){
                                    	form.submit();
                                    	$('#name').val("");
                                    	$('#mail').val("");
                                    	$('#id').val("");
                                    	
                                    },1000); //一秒後自動跳轉
                                    
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
    </script>
                        
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
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/notifications/sweetalert2/sweetalert2.bundle.js"></script>
    
    
     <script>
            /* demo scripts for change table color */
            /* change background */


          
            $(document).ready(function()
            {
                $('#dt-basic-example').dataTable(
                {
                    responsive: true
                });

                $('.js-thead-colors a').on('click', function()
                {
                    var theadColor = $(this).attr("data-bg");
                    console.log(theadColor);
                    $('#dt-basic-example thead').removeClassPrefix('bg-').addClass(theadColor);
                });

                $('.js-tbody-colors a').on('click', function()
                {
                    var theadColor = $(this).attr("data-bg");
                    console.log(theadColor);
                    $('#dt-basic-example').removeClassPrefix('bg-').addClass(theadColor);
                });

            });

         <c:if test="${not empty errorMsgs.id}"> 
       	 $('#addTeacher').modal('show'); 
       	 </c:if> 
               
       	<c:if test="${not empty errorMsgs.name}">
           	$("#name").attr("class","form-control is-invalid");
           	$("#wrongName").text("${errorMsgs.name}");
           </c:if>
           
           <c:if test="${not empty errorMsgs.id}">
           	$("#id").attr("class","form-control is-invalid");
           	$("#wrongId").text("${errorMsgs.id}");
           </c:if>    
           
           <c:if test="${not empty errorMsgs.mail}">
           	$("#mail").attr("class","form-control is-invalid");
           	$("#wrongMail").text("${errorMsgs.mail}");
           </c:if>
            
     </script>
</body>
</html>