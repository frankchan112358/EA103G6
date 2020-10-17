<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.user.model.UserVO"%>
<%
Object userVO = session.getAttribute("userVO");
if(userVO!=null){
	if(((UserVO)userVO).getType() == 0){
		response.sendRedirect(request.getContextPath() + "/front-end/index/index.jsp");
	}else{
		response.sendRedirect(request.getContextPath() + "/back-end/index/index.jsp");
	}
	return;
}
%>
<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService"></jsp:useBean>
<jsp:useBean id="studentSvc" scope="page" class="com.student.model.StudentService"></jsp:useBean>
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService"></jsp:useBean>

<!DOCTYPE html>

<html lang="en">
    
     <head>
        <%@ include file="/back-end/template/head.jsp" %> 
    </head>
    
    <body>
      
 
        <div class="page-wrapper auth">
            <div class="page-inner bg-brand-gradient">
                <div class="page-content-wrapper bg-transparent m-0">
                    <div class="height-10 w-100 shadow-lg px-4 bg-brand-gradient">
                        <div class="d-flex align-items-center container p-0">
                            <div class="page-logo width-mobile-auto m-0 align-items-center justify-content-center p-0 bg-transparent bg-img-none shadow-0 height-9 border-0">
                                <a href="javascript:void(0)" class="page-logo-link press-scale-down d-flex align-items-center">
                                    <img src="<%=request.getContextPath() %>/SmartAdmin4/img/logo.png" alt="SmartAdmin WebApp" aria-roledescription="logo">
                                    <span class="page-logo-text mr-1">Work Join Learn</span>
                                </a>
                            </div>
                            
                        </div>
                    </div>
                    
                                <div class="col-sm-12 col-md-6 col-lg-5 col-xl-4 ml-auto">
                                    <h1 class="text-white fw-300 mb-3 d-sm-block d-md-none">
                                        Secure login
                                    </h1>
                                    <div class="card p-4 rounded-plus bg-faded">
                                    
                                        <form id="js-login" novalidate="" method="post" action="<%=request.getContextPath() %>/login.do">
                                            <div class="form-group">
                                                <label class="form-label" for="typeselect">身分選單</label>
                                                <select class="form-control" id="typeselect" name="type">
                                                    <option value="emp">導師</option>
                                                    <option value="teacher">講師</option>
                                                    <option value="student">學員</option>
                                                </select>
                                            </div>
                                           
                                            <div class="form-group">
                                                <%-- 註解 <label class="form-label" for="username">帳號</label>--%>
                                                <input type="text" id="username" class="form-control form-control-lg" placeholder="請輸入您的帳號" name="account" >
                                                <div class="invalid-feedback">No, you missed this one.</div>
                                                <div class="help-block"></div>
                                            </div>
                                            <div class="form-group">
                                                <%--<label class="form-label" for="password">密碼</label>--%>
                                                <input type="password" id="password" name="password" class="form-control form-control-lg" placeholder="請輸入您的密碼" >
                                                <div class="invalid-feedback">Sorry, you missed this one.</div>
                                                <div class="help-block"></div>
                                            </div>
                                            
                                            
                                            <div class="form-group text-left">                                               
                                            </div>
                                            <div class="row no-gutters">
                                                <div class="col-xl-12 my-2">
                                                    <button type="submit" class="btn btn-info btn-block btn-lg">登入 </button>
                                                </div>
                                                
                                           <%-- 錯誤表列  --%>
											<c:if test="${not empty errorMsgs}">
												<ul>
													<c:forEach var="message" items="${errorMsgs}">
														<li style="color: #ff0000"><b>${message}</b></li>												
													</c:forEach>
												</ul>
											</c:if> 
                                            </div>
                                        </form>
                                         <form id="js-login" novalidate="" method="post" action="<%=request.getContextPath() %>/test/testlogin.do">
                                         <div class="form-group">
                                                <label class="form-label" for="typeselect">身分選單</label>
                                                <select class="form-control" id="typeselect" name="type">
                                                    <option value="emp">導師</option>
                                                    <option value="teacher">講師</option>
                                                    <option value="student">學員</option>
                                                </select>
                                            </div>
                                         <div class="form-group">
                                                <label class="form-label" for="empselect">導師選單</label>
                                                <select class="form-control" id="empselect" name="empNo">
                                                    <option value="">請選擇</option>
                                                    <c:forEach var="empVO" items="${empSvc.all }">
                                                        <option value="${empVO.empNo }">${empVO.empName }(${empVO.empNo})</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label" for="teacherselect">講師選單</label>
                                                <select class="form-control" id="teacherselect" name="teacherNo">
                                                    <option value="">請選擇</option>
                                                    <c:forEach var="teacherVO" items="${teacherSvc.all }">
                                                        <option value="${teacherVO.teacherNo }">${teacherVO.teacherName }(${teacherVO.teacherNo})</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label" for="studentselect">學員選單</label>
                                                <select class="form-control" id="studentselect" name="studentNo">
                                                    <option value="">請選擇</option>
                                                    <c:forEach var="studentVO" items="${studentSvc.all }">
                                                        <option value="${studentVO.studentNo }">${studentVO.studentName }(${studentVO.studentNo})</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-xl-12 my-2">
                                                <button id="js-login-btn" type="submit" class="btn btn-danger btn-block btn-lg"> 工程師們快速登入 </button>
                                                </div>
                                             </form>
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
       
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/vendors.bundle.js"></script>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/app.bundle.js"></script>
        <script>
            $("#js-login-btn").click(function(event)
            {

                // Fetch form to apply custom Bootstrap validation
                var form = $("#js-login")

                if (form[0].checkValidity() === false)
                {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.addClass('was-validated');
                // Perform ajax submit here...
            });
            reset('emp');
            $('#typeselect').change(function(){
                reset($(this).val());
            });
            function reset(type){
                $('#empselect').parent().hide();
                $('#teacherselect').parent().hide();
                $('#studentselect').parent().hide();
                $('#empselect').val("");
                $('#teacherselect').val("");
                $('#studentselect').val("");
                $(`#${"${type}"}select`).parent().show();
            }

        </script>
    </body>

</html>
