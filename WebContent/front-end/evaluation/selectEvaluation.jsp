<%@page import="com.teacher.model.TeacherService"%>
<%@page import="com.evaluation.model.EvaluationQuestion"%>
<%@page import="com.course.model.CourseService"%>
<%@page import="com.course.model.CourseVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="evaluationSvc" scope="page" class="com.evaluation.model.EvaluationService" />
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService" />
<%
pageContext.setAttribute("addedList",evaluationSvc.getStudentAddedCourseEvaluation(studentVO.getStudentNo()));
%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/front-end/template/head.jsp" %> 
</head>
<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
    <script>
        var classHolder = document.getElementsByTagName("BODY")[0];
    </script>
    <div class="page-wrapper">
        <div class="page-inner">
            <%@ include file="/front-end/template/left_aside.jsp" %> 
            <div class="page-content-wrapper">
                <%@ include file="/front-end/template/header.jsp" %> 
                <main id="js-page-content" role="main" class="page-content">
                    <ol class="breadcrumb page-breadcrumb">
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/index/index.jpg">前台首頁</a></li>
                        <li class="breadcrumb-item"><a href="javascript:void(0);">教學評鑑</a></li>
                        <li class="breadcrumb-item">教學評鑑總覽</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-clipboard-check'></i> 教學評鑑總覽
                        </h1>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div id="panel-1" class="panel">
                                <form class="needs-validation" novalidate method="POST" action="<%=request.getContextPath()%>/evaluation/add">
                                    <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                        <h2 class="text-white">教學評鑑總表</h2>
                                    </div>
                                    <div class="panel-container show">
                                        <div class="panel-content">
                                            <div class="form-group">
                                                <!-- datatable start -->
                                                <table id="tableEvaluation" class="table table-bordered table-hover table-striped w-100">
                                                    <thead>
                                                        <tr>
                                                            <th>課程</th>
                                                            <th>講師</th>
                                                            <th>狀態</th>
                                                            <th>填寫</th>
                                                        </tr>                                                            
                                                    </thead>
                                                    <tbody>
                                                    	<c:forEach var="courseVO" items="${studentVO.courseList}" >
                                                        <tr>
                                                            <td>${courseVO.courseName }</td>
                                                            <td>${teacherSvc.getOneTeacher(courseVO.teacherNo).getTeacherName() }</td>
                                                            <td>${courseSvc.getCourseStatusText(courseVO.status) }</td>
                                                            <td>
                                                            <c:if test="${addedList.contains(courseVO.courseNo)}" var="toDo" scope="page">
                                                                <button todo="update" courseStatus="${courseVO.status}" courseNo="${courseVO.courseNo}" type="button" class="btn-write btn btn-sm btn-info" data-toggle="modal" data-target="#editorEvaluation">
                                                                    <span class="fal fa-edit mr-1"></span>
                                                                    <span>修改</span>
                                                                </button>                                                            
                                                            </c:if>
											                <c:if test="${toDo == false}">
                                                                <button todo="add" courseStatus="${courseVO.status}" courseNo="${courseVO.courseNo}" type="button" class="btn-write btn btn-sm btn-primary" data-toggle="modal" data-target="#editorEvaluation">
                                                                    <span class="fal fa-edit mr-1"></span>
                                                                    <span>填寫</span>
                                                                </button>  
											                </c:if>                                                            
                                                            </td>
                                                        </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                                <!-- datatable end -->
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>                            
                        </div>
                    </div>
                </main>
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
                <%@ include file="/front-end/template/footer.jsp" %>
            </div>
        </div>
    </div>
    <div class="modal fade" id="editorEvaluation" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">課程意見調查表
                        <small class="m-0 text-muted">請務必評分每個問題</small>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"><i class="fal fa-times"></i></span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="formEvaluation" class="needs-validation" novalidate>
                        <div class="form-group">
                            <input type="hidden" name="studentNo" value="${studentVO.studentNo}"/>
                            <input type="hidden" name="courseNo" value=""/>
                            <c:forEach var="question" items="${evaluationSvc.getEvaluationQuestionAll()}">
                            	<div style="height: 67px;"> 
                                    <h5 class="frame-heading fs-md m-0 mb-1 mt-1">${question.text}</h5>
                                    <%for(int i=9;i>-1;i--) {%>                                                                        
                                        <div style="display:inline;" class="custom-control custom-radio custom-control-inline">                                                   
                                            <input type="radio" class="custom-control-input" id="Q${question.num}_<%=i %>" name="Q${question.num}" value="<%=i %>" required=""/>
                                            <label class="custom-control-label" for="Q${question.num}_<%=i %>"><%=i %></label>
                                            <%if(i==0){%><div class="invalid-feedback">請選擇分數</div><%}%>                                                                                                   	
                                        </div>                                                                                                                          			
                                    <%} %>
                                </div>                                                                                          
                            </c:forEach>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="cancel">取消</button>
                    <button type="button" class="btn btn-primary" id="save">儲存</button>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/front-end/template/quick_menu.jsp" %>
    <%@ include file="/front-end/template/messager.jsp" %>
    <%@ include file="/front-end/template/basic_js.jsp" %>   
    <script>
        'use strict';
        $(document).ready(function(){
            var formEvaluation =  document.getElementById('formEvaluation');

            $('#tableEvaluation').dataTable({
                responsive: true,
                language:{url:'<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json'}
            });

            document.getElementById('save').addEventListener('click', function(event){
                if (formEvaluation.checkValidity() === false){
                    event.preventDefault();
                    event.stopPropagation();
                    formEvaluation.classList.add('was-validated');
                    return;                          
                } else{
                    formEvaluation.classList.add('was-validated');                           
                }
                let courseNo = $(formEvaluation).find('input[name=courseNo]').val();
                let btn = $(`.btn-write[courseNo=${"${courseNo}"}]`);
                if(btn.attr('todo')==='add'){
                    $.ajax({
                        beforeSend(){},
                        type: 'POST',
                        url: '<%=request.getContextPath()%>/evaluation/add',
                        data: $(formEvaluation).serialize(),
                        success(res){
                            if(res=='ok'){
                                $('#editorEvaluation').modal('hide');
                                btn.addClass('btn-info');
                                btn.removeClass('btn-primary');
                                btn.find('span').eq(1).text('修改');
                                btn.attr('todo','update');
                            }                       
                        },
                        error(err){
                            alert(err);
                        }
                    });
                }
                if(btn.attr('todo')==='update'){
                    $.ajax({
                        beforeSend(){},
                        type: 'POST',
                        url: '<%=request.getContextPath()%>/evaluation/update',
                        data: $(formEvaluation).serialize(),
                        success(res){
                            if(res=='ok'){
                                $('#editorEvaluation').modal('hide');
                            }                       
                        },
                        error(err){
                            alert(err);
                        }
                    });                    
                }
            });

            document.getElementById('cancel').addEventListener('click', function(event){
                resetEvaluation();
            });

            $('button[courseStatus]').each(function(index,element){
                let btn = $(element);
                if(btn.attr('courseStatus')!=2){
                    btn.attr('disabled',true);
                }
            });

            $(document).on("click", "button.btn-write", function(event) {
                resetEvaluation();
                let courseNo = this.getAttribute('courseNo')
                $('#formEvaluation input[name=courseNo]').val(courseNo);
                if(this.getAttribute('todo')=='update'){
                    $.ajax({
                        beforeSend(){},
                        type: 'GET',
                        url: '<%=request.getContextPath()%>/evaluation/get',
                        data: {
                            studentNo:'${studentVO.studentNo}',
                            courseNo:courseNo
                        },
                        success(res){
                            for(let val in res)
                                $(`input[name*=${"${val}"}][value=${"${res[val]}"}]`).prop('checked',true);                                               
                        },
                        error(err){
                            alert(err);
                        }
                    });
                }
            });

            function resetEvaluation(){
                formEvaluation.classList.remove('was-validated');
                $('input[type=radio]').prop('checked',false);
            }
        });
    </script>
</body>
</html>