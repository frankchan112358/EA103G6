<%@page import="com.user.model.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<!DOCTYPE html>
<html>

<head>
    <%@ include file="/front-end/template/head.jsp"%>
    <style>
        body {
            background-color: #FFD382;
        }

        .abgne-menu-20140101-1 input[type="radio"] {
            display: none;
        }

        abgne-menu-20140101-1 input[type="radio"]+label {

            background-color: #ccc;
            cursor: pointer;
            padding: 5px 10px;
        }

        .abgne-menu-20140101-1 input[type="radio"]:checked+label {
            /* 	background-color: 	#FF8888; */
            color: #888888;
        }

        .span-change {
            background-color: #FFFFFF;
            font-size: 20px;
            font-color: #00000;
            font-weight: bold;
            border-radius: 10px;
            padding: 20px 40px 20px 40px;
            opacity: 0.3
        }
        
         
/*         #SeperationLine{ */
/*     height:1px; */
   
/*     margin:10px 0px; */
/* } */
    </style>
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
                <div class="flex-1" style="background: url(<%=request.getContextPath() %>/SmartAdmin4/img/svg/pattern-1.svg) no-repeat center bottom fixed; background-size: cover;">
                    <form id="js-login" method="post" action="<%=request.getContextPath() %>/login.do">
                        <div class="container py-4 py-lg-5 my-lg-5 px-4 px-sm-0">
                            <div class="row">
                                <div class="col col-md-4 col-lg-7 ">
                                    <div class="abgne-menu-20140101-1">
                                     <div class="row">
                                     <div>
                                      <div class="row">
                                        <span class="m-1 span-change" person="emp" >
                                            <input type="radio" id="emp" name="type" value="emp" onclick="javascript:document.getElementById('mytheme').href='';javascript:document.getElementById('img').src='<%=request.getContextPath() %>/images/emp.png'">
                                            <label style="cursor: pointer;" for="emp" style="border:200px">我是導師</label>
                                        </span>
                                        
                                        
                                        <span class="m-1 span-change" person="teacher" >
                                            <input type="radio" id="teacher" name="type" value="teacher" onclick="javascript:document.getElementById('mytheme').href='<%=request.getContextPath() %>/SmartAdmin4/css/themes/cust-theme-14.css';javascript:document.getElementById('img').src='<%=request.getContextPath() %>/images/teacher.png'">
                                            <label style="cursor: pointer;" for="teacher">我是講師</label>
                                        </span>
                                       
                                        
                                        <span class="m-1 span-change" person="student">
                                            <input type="radio" id="student" name="type" value="student" checked onclick="javascript:document.getElementById('mytheme').href='<%=request.getContextPath() %>/SmartAdmin4/css/themes/cust-theme-2.css';javascript:document.getElementById('img').src='<%=request.getContextPath() %>/images/stu.png'">
                                            <label style="cursor: pointer;" for="student"  style="border:200px">我是學生</label>
                                        </span>
                                        </div>
                                        
                                         <div class="col col-lg-6   hidden-md-down hidden-sm-down">
                                        <div class="mt-6">
                                            <img class="img" src="" id="img" style="margin-left:100px;" />
                                        </div>
                                        </div></div>
                                         </div>
                                        
                                    </div>
                                </div>
                                <div class="col-sm-12 col-md-8 col-lg-5 col-xl-4 ml-auto">
                                    <div id="div1" class="card p-4 rounded-plus bg-faded">
                                        <div class="form-group">
                                            <%-- 註解 <label class="form-label" for="username">帳號</label>--%>
                                            <input type="text" id="username" class="form-control form-control-lg" placeholder="請輸入您的帳號" name="account">
                                            <div class="invalid-feedback">請勿遺漏帳號欄位</div>
                                            <div class="help-block"></div>
                                        </div>
                                        <div class="form-group">
                                            <%--<label class="form-label" for="password">密碼</label>--%>
                                            <input type="password" id="password" name="password" class="form-control form-control-lg" placeholder="請輸入您的密碼">
                                            <div class="invalid-feedback">請勿遺漏密碼欄位</div>
                                            <div class="help-block"></div>
                                        </div>
                                        <a href="<%=request.getContextPath() %>/login/forgetPassword.jsp" style="color:#A42D00 ;font-weight:bold">
                                            忘記密碼?
                                        </a>
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
                                        <div class="row no-gutters">
                                            <div class="col-xl-12 my-2">
                                                <button id="btnForRD" type="button" class="btn btn-danger btn-block btn-lg">工程師快速登入按這裡</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="position-absolute pos-bottom pos-left pos-right p-3 text-center text-white">
                                2020 © Work Join Learn
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <p id="js-color-profile" class="d-none"></p>
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/vendors.bundle.js"></script>
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/app.bundle.js"></script>
    <script>
        $(document).ready(function () {
            $("#js-login-btn").click(function (event) {

                // Fetch form to apply custom Bootstrap validation
                var form = $("#js-login")

                if (form[0].checkValidity() === false) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.addClass('was-validated');
                // Perform ajax submit here...
            });
            reset('emp');
            $('#typeselect').change(function () {
                reset($(this).val());
            });
            function reset(type) {
                $('#empselect').parent().hide();
                $('#teacherselect').parent().hide();
                $('#studentselect').parent().hide();
                $('#empselect').val("");
                $('#teacherselect').val("");
                $('#studentselect').val("");
                $(`#${"${type}"}select`).parent().show();
            }

            $('#btnForRD').click(function (e) {
                e.preventDefault();
                let _this = this;
                let myForm = document.createElement('form');
                myForm.action = '<%=request.getContextPath() %>/test/testlogin.do';
                myForm.method = 'GET';
                document.body.appendChild(myForm);
                myForm.submit();
            });

            function img() {
                var img = document.getElementById('img');
                if (img.src === null) {
                    img.src = '<%=request.getContextPath() %>/images/emp.png'
                }
            }

            javascript: document.getElementById('mytheme').href = '<%=request.getContextPath() %>/SmartAdmin4/css/themes/cust-theme-2.css'; javascript: document.getElementById('img').src = '<%=request.getContextPath() %>/images/stu.png'
        });
    </script>
</body>

</html>