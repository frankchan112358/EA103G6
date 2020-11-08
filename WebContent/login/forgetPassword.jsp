<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.user.model.UserVO"%>
<%
Object userVO = session.getAttribute("userVO");


%>
<html lang="en">
    <head>
        <%@ include file="/front-end/template/head.jsp" %> 
    </head>
 
        <script>
            /**
             *	This script should be placed right after the body tag for fast execution 
             *	Note: the script is written in pure javascript and does not depend on thirdparty library
             **/
            'use strict';

            var classHolder = document.getElementsByTagName("BODY")[0],
                /** 
                 * Load from localstorage
                 **/
                themeSettings = (localStorage.getItem('themeSettings')) ? JSON.parse(localStorage.getItem('themeSettings')) :
                {},
                themeURL = themeSettings.themeURL || '',
                themeOptions = themeSettings.themeOptions || '';
            /** 
             * Load theme options
             **/
            if (themeSettings.themeOptions)
            {
                classHolder.className = themeSettings.themeOptions;
                console.log("%c✔ Theme settings loaded", "color: #148f32");
            }
            else
            {
                console.log("%c✔ Heads up! Theme settings is empty or does not exist, loading default settings...", "color: #ed1c24");
            }
            if (themeSettings.themeURL && !document.getElementById('mytheme'))
            {
                var cssfile = document.createElement('link');
                cssfile.id = 'mytheme';
                cssfile.rel = 'stylesheet';
                cssfile.href = themeURL;
                document.getElementsByTagName('head')[0].appendChild(cssfile);

            }
            else if (themeSettings.themeURL && document.getElementById('mytheme'))
            {
                document.getElementById('mytheme').href = themeSettings.themeURL;
            }
            /** 
             * Save to localstorage 
             **/
            var saveSettings = function()
            {
                themeSettings.themeOptions = String(classHolder.className).split(/[^\w-]+/).filter(function(item)
                {
                    return /^(nav|header|footer|mod|display)-/i.test(item);
                }).join(' ');
                if (document.getElementById('mytheme'))
                {
                    themeSettings.themeURL = document.getElementById('mytheme').getAttribute("href");
                };
                localStorage.setItem('themeSettings', JSON.stringify(themeSettings));
            }
            /** 
             * Reset settings
             **/
            var resetSettings = function()
            {
                localStorage.setItem("themeSettings", "");
            }

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
                    
                            <a href="<%=request.getContextPath() %>/login/login.jsp" class="btn-link text-white ml-auto ml-sm-0">
                              	  登入
                            </a>
                        </div>
                    </div>
                    <div class="flex-1" style="background: url(img/svg/pattern-1.svg) no-repeat center bottom fixed; background-size: cover;">
                        <div class="container py-4 py-lg-5 my-lg-5 px-4 px-sm-0">
                            <div class="row">
                                <div class="col-xl-12">
                                    <h2 class="fs-xxl fw-500 mt-4 text-white text-center">
                                        	忘記密碼了嗎？
                                        <small class="h3 fw-300 mt-3 mb-5 text-white opacity-60 hidden-sm-down">
                                           	沒問題！我們將會協助你找回密碼
                                        </small>
                                    </h2>
                                </div>
                                <div class="col-xl-6 ml-auto mr-auto">
                                    <div class="card p-4 rounded-plus bg-faded">
                                        <form id="js-login"  method="post" action="<%=request.getContextPath() %>/forget/forget.do">
                                            <div class="form-group">
                                                <label class="form-label" for="lostaccount">身分證字號</label>
                                                <input type="id" id="lostaccount" name="id" class="form-control" placeholder="請填寫您的身分證字號" required>                    
                                            </div>
                                              <div class="form-group">
                                                <label class="form-label" for="lostaccount">信箱</label>
                                                <input type="mail" id="lostaccount" name="mail" class="form-control" placeholder="請填寫您所註冊的信箱" required>
                                                <div class="invalid-feedback">請輸入你的信箱</div>
                                                <div class="help-block">我們將會寄送驗證信至您的信箱</div>
                                            </div>
                                               <%-- 錯誤表列  --%>
											<c:if test="${not empty errorMsgs}">
												<ul>
													<c:forEach var="message" items="${errorMsgs}">
														<li style="color: #ff0000"><b>${message}</b></li>												
													</c:forEach>
												</ul>
											</c:if> 
                                            <div class="row no-gutters">
                                                <div class="col-md-4 ml-auto text-right">
                                                    <button id="js-login-btn" type="submit" class="btn btn-danger">確認</button>
                                                </div>                                        
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
        
         
        <script src="js/vendors.bundle.js"></script>
        <script src="js/app.bundle.js"></script>
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

        </script>
    </body>
    <!-- END Body -->
</html>
