<%@page import="com.courseask.model.*"%>
<%@page import="com.course.model.*"%>
<%@page import="com.student.model.*"%>
<%@page import="com.teacher.model.*"%>
<%@page import="com.reply.model.*"%>
<%@page import="java.util.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="courseSvc" scope="page"
	class="com.course.model.CourseService" />
<jsp:useBean id="studentSvc" scope="page"
	class="com.student.model.StudentService" />
<jsp:useBean id="teacherSvc" scope="page"
	class="com.teacher.model.TeacherService" />
<jsp:useBean id="replySvc" scope="page"
	class="com.reply.model.ReplyService" />
<%
	CourseAskService courseAskSvc = new CourseAskService();
	List<CourseAskVO> list = courseAskSvc.getAll();
	pageContext.setAttribute("list", list);
%>


<%
	CourseAskVO courseAskVO = (CourseAskVO) request.getAttribute("courseAskVO");
%>
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

#btn-add:hover {
	background-color: #467500;
	font-size: 20px;
}

.modal-header {
	background-color: #E0E0E0;
}

#time {
	font-size: 11px;
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
						<li class="breadcrumb-item"><a href="javascript:void(0);">課程提問</a></li>
						<li class="breadcrumb-item">課程提問總覽</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-clipboard-check'></i> 課程提問總覽
						</h1>
						<div style="text-algin: right">
							<button type="button" id="btn-add"
								class="btn-write btn btn-sm btn-primary" data-toggle="modal"
								data-target="#editorEvaluation">
								<strong>我要問問題</strong>
							</button>
						</div>
					</div>
					<c:forEach var="courseAskVO" items="${list}">
					
						<div class="panel-container show">
							<div class="panel-content">
								<div class="accordion" id="js_demo_accordion-2">
									<div class="card">
										<div class="card-header">
											<a href="javascript:void(0);" class="card-title" 
												data-toggle="collapse" data-target="#js_demo_accordion-2a "
												aria-expanded="true"> ${courseAskVO.title} </a>
												<div><span style="margin-left:50px;">發問者:</span>${studentSvc.getOneStudent(courseAskVO.getStudentNo()).studentName}</div>
										</div>
										<div id="js_demo_accordion-2a" class="collapse show"
											data-parent="#js_demo_accordion-2">
											<div class="card-body">${courseAskVO.question}</div>
										
											<div id="time" class="card-body">
												<span style="display:inline-block;">發文時間:</span>
												<fmt:formatDate value="${courseAskVO.updateTime}"
													pattern="yyyy-MM-dd HH:mm:ss" />
													 
													 <div style="display:inline-block;margin-left:10px;">
                                                                  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/courseAsk/courseAsk.do" style="margin-bottom: 0px;">
                                                                   <input type="hidden" name="replyNo"  value="${replyVO.replyNo}">
			     														<input type="hidden" name="studentNo" value="${studentVO.studentNo}"/>
			     														<input type="hidden" name="courseAskNo" value="${courseAskVO.courseAskNo}"/>
			     														<input type="hidden" name="teacherNo" value="${teacherVO.teacherNo}"/>
			     														<input type="hidden" name="action" value="insert1"> 
                                                              			<span class="fal fa-edit mr-1"><input type="submit"   value="回覆">
							 															</span>
							 										 </FORM>	
							 										 </div>
											</div>
										</div>
										
									</div>

								</div>
							</div>
						</div>
					</c:forEach>
				</main>
			</div>
		</div>
	</div>
<main id="js-page-content" role="main" class="page-content">
    <div class="modal fade" id="editorEvaluation" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
            <div class="modal-content">
               
                <div class="modal-header">
                    <h4 class="modal-title" >新增問題</h4> 
                </div>
                 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/courseAsk/courseAsk.do" name="form1">
            
                    <h4 class="modal-title" >選擇課程</h4> 

       <select size="1" name="courseNo" style="color: black; width: 566px; height: 50px; margin: 20px ;">
         <c:forEach var="courseVO" items="${courseSvc.all}" > 
          <option value="${courseVO.courseNo}">${courseVO.courseName}
         </c:forEach>   
       </select>

             
     
                 <input placeholder="輸入您的問題" name="title" style="color: black; width: 566px; height: 50px; margin: 20px ;" 
		value="<%=(courseAskVO == null) ? "" : courseAskVO.getTitle()%>">
		
		
                   <div class="modal-footer">

                            <div id="panel-2" class="panel">
                                <div class="panel-container show">
                                    <div class="panel-content" >
                                        <textarea class="js-summernote" id="democratNote" name="question"></textarea>
                                        <button id="sendNote" type="submit" class="mb-3 mt-3 btn btn-info waves-effect waves-themed float-left">送出</button>
										<input type="hidden" name="studentNo" value="${studentVO.studentNo}"/>
										<input type="hidden" name="action" value="insert">
                                    </div>
                            
                            </div>
                        </div>
                            </div>
                            </FORM>
                        </div>
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
	
</body>
</html>