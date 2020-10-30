<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banjipost.model.*"%>

<%
	BanjiPostVO banjiPostVO = (BanjiPostVO) request.getAttribute("banjiPostVO");
%>

<jsp:useBean id="banjiPostSvc" scope="page"
				class="com.banjipost.model.BanjiPostService" />

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
<style>

#add{
text-align:center ;
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
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/banji/homeBanji.jsp">公告管理</a></li>
                          <li class="breadcrumb-item">修改資料</li>
                    </ol>
                    <div class="subheader" >
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-users-class' ></i> 修改資料
                        </h1>
                    </div>
                    <div class="row">
						<div class="col col-xl-12">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">總覽</h2>
								</div>
					 <div class="panel-container show" >
                           <div class="panel-content">
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banjiPost/banjiPost.do" name="form1" class="was-validated">
		
										<div class="form-group">
                                               <label class="form-label" for="simpleinput">公告編號:</label>
                                                 <input type="text" id="simpleinput" class="form-control" readonly name="banjiPostNo" value="${banjiPostVO.banjiPostNo}">
                                            </div>
											<div class="form-group">
                                               <label class="form-label" for="simpleinput">班級編號:</label>
                                                 <input type="text" id="simpleinput" class="form-control" readonly name="banjiNo" value="${banjiPostVO.banjiNo}">
                                            </div>
                                             <div class="form-group">
                                               <label class="form-label" for="simpleinput">班級公告標題:</label>
                                                 <input type="text" id="simpleinput" class="form-control" required name="title" value="${banjiPostVO.title}">
                                           <div class="invalid-feedback">
                                                        		班級公告請勿空白.
                                         </div>
                                            </div>   
                                            
                                      		<div class="form-group">
									<label class="form-label" for="example-textarea">班級公告內容:</label>
									<textarea name="banjiPostContent" class="form-control" required
										id="example-textarea" rows="5" >${banjiPostVO.banjiPostContent }</textarea>
										<div class="invalid-feedback">
                                                        		班級公告內容請勿空白.
                                         </div>
										</div>
                                      
		 								 <div class="form-group" >
                                                    <label class="form-label" for="example-select">狀態:</label>
                                               <select class="custom-select form-control" name="status" >
													<option value="0" ${(banjiPostVO.status==0)?'selected':'' }>一般</option>
													<option value="1" ${(banjiPostVO.status==1)?'selected':'' }>緊急</option>
												</select>           
                                                </div>
                                                
                                           <div class="form-group" id="add" >
												<input type="hidden" name="action" value="update"> 
												<input type="hidden" name="banjiPostNo" value="${banjiPostVO.banjiPostNo}">
												<input type="hidden" name="banjiNo" value="<%=banjiPostVO.getBanjiNo()%>">
												<button type="submit" class="btn btn-primary justify-content-center" >送出修改</button>
											</div>     
							</FORM>
									</div>
									</div>
									</div>
									</div>
	  </div>
</main>
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
                <%@ include file="/back-end/template/footer.jsp" %>
            </div>
        </div>
    </div>
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %>
    
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

                                            </script>
</body>
</html>