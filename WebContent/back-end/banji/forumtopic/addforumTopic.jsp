<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.forumtopic.model.*"%>
<%@ page import="com.banji.model.*"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="forumtopicSvc" scope="page"
	class="com.forumtopic.model.ForumTopicService" />

<%
	String no = request.getParameter("banjiNo");
	pageContext.setAttribute("no", no);
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
<link rel="stylesheet" media="screen, print"
	href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>

<link
	href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>

<!-- notifications 的css連結 -->
<link rel="stylesheet" media="screen, print"
	href="<%=request.getContextPath()%>/SmartAdmin4/css/notifications/sweetalert2/sweetalert2.bundle.css">


<style>
#add {
	text-align: center;
}
</style>

</head>
<body
	class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
	<script>
		var classHolder = document.getElementsByTagName("BODY")[0];
	</script>

	<div class="page-wrapper">
	${banjiVO.banjiNo}
		<div class="page-inner">
			<%@ include file="/back-end/template/left_aside.jsp"%>
			<div class="page-content-wrapper">
				<%@ include file="/back-end/template/header.jsp"%>
				<main id="js-page-content" role="main" class="page-content">
					 <ol class="breadcrumb page-breadcrumb">
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.manage">養成班管理</a></li>
                         <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.manage?action=read">${banjiVO.banjiName}</a></li>
                    	<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.forumtopic">主題管理</a></li>
                    	<li class="breadcrumb-item">新增主題</li>
                    </ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-democrat'></i> 新增主題
						</h1>
					</div>
					<div class="row">
						<div class="col col-xl-12">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">主題</h2>
								</div>
					<div class="panel-container show">
						<div class="panel-content">
							<form METHOD="post" ACTION="<%=request.getContextPath()%>/banji/banji.forumtopic" name="form1" class="needs-validation" novalidate>
								<div class="form-group">
									<label class="form-label" for="example-textarea">主題名稱:</label>
									<input type="text" id="simpleinput" name="forumTopicName" class="form-control" required placeholder="請輸入主題">
										<div class="invalid-feedback">
                                                        		主題名稱請勿空白.
                                         </div>
								</div>
								<div class="form-group" id="add">
									<input type="hidden" name="action" value="insert">
									<input type="hidden" name="banjiNo" value="${no}">
									<button type="submit"
										class="btn btn-primary justify-content-center">送出</button>
								</div>
							</form>
						</div></div></div></div>
					</div>
				</main>
				<div class="page-content-overlay" data-action="toggle"
					data-class="mobile-nav-on"></div>
				<%@ include file="/back-end/template/footer.jsp"%>
			</div>
		</div>
	</div>
	<%@ include file="/back-end/template/quick_menu.jsp"%>
	<%@ include file="/back-end/template/messager.jsp"%>
	<%@ include file="/back-end/template/basic_js.jsp"%>

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