<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.forumpost.model.*"%>

<%@ page import="java.util.*"%>

<%
   ForumPostVO forumPostVO = (ForumPostVO) request.getAttribute("forumPostVO");
%>
<jsp:useBean id="forumtopicSvc" scope="page" class="com.forumtopic.model.ForumTopicService" />

<!DOCTYPE html>
<html>
<head>
<%@ include file="/front-end/template/head.jsp"%>

<link rel="stylesheet" media="screen, print"
	href="<%=request.getContextPath()%>/SmartAdmin4/css/formplugins/summernote/summernote.css">
<style>
#btn-add {
	border-radius: 5px;
	height: 50px;
	width: 200px;
	background-color: #64A600;
}



.modal-header {
	background-color: #E0E0E0;
}

#time {
	font-size: 11px;
}
#add{
border-color:gray;
border-radius: 5px;
}




</style>
</head>

<body
	class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
	<script>
		var classHolder = document.getElementsByTagName("BODY")[0];
	</script>


	<div class="page-wrapper">
		<div class="page-inner">
			<%@ include file="/front-end/template/left_aside.jsp"%>
			<div class="page-content-wrapper">
				<%@ include file="/front-end/template/header.jsp"%>
				<main id="js-page-content" role="main" class="page-content">
					<ol class="breadcrumb page-breadcrumb">
						<li class="breadcrumb-item"><a
							href="<%=request.getContextPath()%>/front-end/index/index.jpg">前台首頁</a></li>
						<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/forumpost/forumPost_index.jsp">班級討論區</a></li>
						<li class="breadcrumb-item">新增留言</li>
					</ol>



	<div class="subheader">
						
						<div class="row">
						<div class="col col-xl-12">
						<div class="panel-container show">
						<div class="panel-content">
						
						<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
				<h2>新增留言</h2>
			</header>
		</div>
	</section>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/forumPost/forumPost.do" name="form1" class="was-validated">
	
	<div class="form-group">
                                               <label class="form-label" for="simpleinput">主題:</label>
                                                 <input type="text" id="simpleinput" class="form-control" readonly name="forumTopicNo" value="${forumtopicSvc.getOneForumTopic(forumPostVO.forumTopicNo).forumTopicName}">
                                            </div> 
	
	
	<div class="form-group">
                                               <label class="form-label" for="simpleinput">學員編號:</label>
                                                 <input type="text" id="simpleinput" class="form-control" readonly name="studentNo" value="${forumPostVO.studentNo}">
                                            </div>
                                            
                      <div class="form-group">
                                               <label class="form-label" for="simpleinput">貼文標題:</label>
                                                 <input type="text" id="simpleinput" class="form-control"  name="banjiName" required value="${forumPostVO.title}">
                                            <div class="invalid-feedback">
                                                        		  貼文標題請勿空白
                                         </div>                      
	
	            
	 
              <div id="panel-2" class="panel">
                                <div class="panel-container show">
                                    <div class="panel-content" >
                                        <textarea class="js-summernote" id="democratNote" class="form-control" name="content"  required>${forumPostVO.content}</textarea>
                                        
</div>
                
<!--                 <p> -->
<!--                     <label for="title">標題 :</label> -->
<%-- 									<input type="text" name="title" value="${forumPostVO.title}"> --%>
<!--                 </p> -->
<!--                 <p> -->
<!--                     <label for="content">貼文內容 :</label> -->
<%--                             <textarea id="summernote"  name="content">${forumPostVO.content}</textarea> --%>
                    
<!--                 </p> -->

                      <div class="form-group" id="add" >
												<input type="hidden" name="action" value="update">
<%-- 												<input type="hidden" name="studentNo" value="${studentVO.studentNo}"> --%>
												<input type="hidden" name="forumTopicNo" value="${forumTopicVO.forumTopicNo}">
												<button type="submit" class="btn btn-primary justify-content-center" >送出修改</button>
											</div>
                
              
        
        
<!--          <p> -->
<!--          <input type="hidden" name="action" value="getOne_For_Update">  -->
<!-- 				<input type="submit"> -->
<%-- 	 <input type="hidden" name="studentNo" value="${studentVO.studentNo}"> --%>
				
<%-- 	  <input type="hidden" name="forumTopicNo" value="${forumTopicVO.forumTopicNo}"> --%>
<!-- 				</p> -->

</div>
</div>
        </div></form>
        </div>
        
        
  <main id="js-page-content" role="main" class="page-content">
    <div class="modal fade" id="editorEvaluation" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
            
                        </div>
                    </div>
 </main>

	<div class="page-content-overlay" data-action="toggle"
		data-class="mobile-nav-on"></div>
	<%@ include file="/front-end/template/footer.jsp"%>


	<%@ include file="/front-end/template/quick_menu.jsp"%>
	<%@ include file="/front-end/template/messager.jsp"%>
	<%@ include file="/front-end/template/basic_js.jsp"%>
	<script src="<%=request.getContextPath() %>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
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
    
    <script> 

    $(document).ready(function(){
        $('#tableEvaluation').dataTable({
            responsive: true,
            language:{url:'<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json'}
        });   
       
        $('#democratNote').summernote();
        
        $('#democratNote').summernote({
            height: 250,
            tabsize: 2,
            placeholder: "請輸入",
            dialogsFade: true,
            toolbar: [
                ['style', ['style']],
                ['font', ['strikethrough', 'superscript', 'subscript']],
                ['font', ['bold', 'italic', 'underline', 'clear']],
                ['fontsize', ['fontsize']],
                ['fontname', ['fontname']],
                ['color', ['color']],
                ['para', ['ul', 'ol', 'paragraph']],
                ['height', ['height']]
                ['table', ['table']],
                ['insert', ['link', 'picture', 'video']],
                ['view', ['fullscreen', 'codeview', 'help']]
            ],
            callbacks: {
                onInit: function (e) {
                    $.ajax({
                        url: '<%=request.getContextPath() %>/Summernote',
                        type: 'get',
                        success(res) {
                            $('#democratNote').summernote('code', res);
                        }
                    });
                },
                onChange: function (contents, $editable) { }
            }
        });

        $('#sendNote').click(function () {
            let form = new FormData();
            form.append("democratNote", $('#democratNote').summernote('code'));
            $.ajax({
                url: '<%=request.getContextPath() %>/Summernote',
                type: 'post',
                processData: false,
                contentType: false,
                data: form,
                success(res) {
                    console.log(res);
                }
            });
        });
    });
    
        </script>
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
                                            </div>
                                            </div>
                                            </div>
                                            </div>
                                            </main>
                                            </div>
                                            </div>
                                            </div>
                                            
</body>
</html>