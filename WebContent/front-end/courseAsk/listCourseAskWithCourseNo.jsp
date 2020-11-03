<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.courseask.model.*"%>
<%@page import="com.student.model.*"%>
<%@page import="com.course.model.*"%>
<%@page import="com.teacher.model.*"%>
<%@page import="com.reply.model.*"%>
<%@page import="java.util.*"%>
<%@ include file="/front-end/template/check.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="studentSvc" scope="page" class="com.student.model.StudentService" />
<jsp:useBean id="replySvc" scope="page" class="com.reply.model.ReplyService" />
<%
%>
<!DOCTYPE html>
<html>

<head>
    <%@ include file="/front-end/template/head.jsp"%>

<link rel="stylesheet" media="screen, print"
      href="<%=request.getContextPath()%>/SmartAdmin4/css/formplugins/summernote/summernote.css">
<style>
    .num {
        font-size: 30px !important;
        color: #336666 !important;
        padding-right: 30px !important;
        text-align: center !important;
        position: absolute !important;
        bottom: 0 !important;
        right: 0 !important;
        padding-bottom: 16px !important;
    }

    .ask-title {
        display: inline-flex !important;
    }

    .replyList {
        background-color: #f7f9fa;
        min-height: 3em;
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
                               href="<%=request.getContextPath()%>/front-end/index/index.jsp">前台首頁</a></li>
                        <li class="breadcrumb-item">
                            <a href="<%=request.getContextPath()%>/front-end/course/selectCourse.jsp">我的課程</a>
                        </li>
                        <li class="breadcrumb-item">問題討論</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-books'></i>
                            課程提問
                        </h1>
                    </div>
                    <div class="row align-items-center justify-content-center">
                        <div class="col-11">
                            <jsp:include page="/front-end/course/courseDetail.jsp"></jsp:include>
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">課程提問總覽</h2>
                                </div>
                                <div class="panel-container show" style="margin: 20px 20px;">
                                    <div style="text-align: center;">
                                        <c:if test="${empty courseAskList}">
                                            <h2>
                                                <i class="fal fa-calendar-times"></i>
                                                目前尚無任何課程提問
                                                <i class="fal fa-calendar-times"></i>
                                            </h2>
                                        </c:if>
                                    </div>
                                    <div>
                                        <button type="button" id="btn-add" class="btn-write btn btn-sm btn-primary">
                                            <strong>我要問問題</strong>
                                        </button>
                                    </div><br>
                                    <div class="accordion accordion-outline" id="courseAskList">
                                        <c:forEach var="courseAskVO" items="${courseAskList}">
                                            <div class="card courseAsk" courseAskNo="${courseAskVO.courseAskNo}" data-toggle="collapse" data-target="#courseAsk${courseAskVO.courseAskNo}" aria-expanded="false">
                                                <div class="card-header">
                                                    <div class="ask-title card-title collapsed">
                                                        <i class="fal fa-books mr-3 fa-2x"></i>
                                                        <span>
                                                            ${courseAskVO.title }
                                                            <br><span>發問者:</span>${studentSvc.getOneStudent(courseAskVO.getStudentNo()).studentName}
                                                            <br><span>發問時間:</span>
                                                            <fmt:formatDate value="${courseAskVO.updateTime}" pattern="yyyy-MM-dd HH:mm" />
                                                        </span>
                                                    </div>
                                                    <c:set var="replySize" value="${fn:length(replySvc.getAllWithCouseAskNo(courseAskVO.courseAskNo))}"></c:set>
                                                    <div class="num">
                                                        <span courseAskNo="${courseAskVO.courseAskNo}" class="replySize">${replySize}</span>
                                                        <div style="font-size:15px;">回覆</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="courseAsk${courseAskVO.courseAskNo}" class="collapse" data-parent="#courseAskList">
                                                <div class="card-body">
                                                    <div class="card-text fs-xxl" style="padding-left: 30px">${courseAskVO.question}</div>
                                                    <div class="m-5">
                                                        <div courseAskNo="${courseAskVO.courseAskNo}" class="replyList">
                                                            <span>目前沒有回覆</span>
                                                        </div>
                                                        <button courseAskNo="${courseAskVO.courseAskNo}" type="button" class="reply mt-3 btn btn-info">回覆</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </div>
    <div class="modal fade" id="courseAskModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">新增問題</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        <span><i class="fal fa-times"></i></span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="<%=request.getContextPath()%>/courseAsk/courseAsk.do" class="needs-validation" novalidate>
                        <input id="add" placeholder="輸入您的問題" name="title" style="color: black; width: 95%; height: 50px; margin: 20px ;" value="${courseAskVO.title }" required>
                        <div class="invalid-feedback">請勿空白</div>
                        <textarea class="js-summernote" id="democratNote" name="question" required></textarea>
                        <div class="invalid-feedback">請勿空白</div>
                        <input type="hidden" name="studentNo" value="${studentVO.studentNo}" />
                        <input type="hidden" name="action" value="insert">
                        <button id="sendCourseAsk" type="submit" class="mb-3 mt-3 btn btn-info waves-effect waves-themed float-left">送出</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="replyModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">回覆問題</h4>
                    <button type="button" class="close" data-dismiss="modal">
                        <span><i class="fal fa-times"></i></span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="formReply" class="needs-validation" novalidate>
                        <textarea name="replyContent" required style="width:100%;height:200px;border:2px green double;" placeholder="請輸入..."></textarea>
                        <div class="invalid-feedback">請勿空白</div>
                        <input type="hidden" name="courseAskNo" value="" />
                        <input type="hidden" name="userNo" value="${userVO.userNo}" />
                        <input type="hidden" name="action" value="insertWithStudent">
                        <button id="sendReply" type="button" class="mb-3 mt-3 btn btn-info waves-effect waves-themed float-left">送出</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
    <%@ include file="/front-end/template/footer.jsp"%>
    <%@ include file="/front-end/template/quick_menu.jsp"%>
    <%@ include file="/front-end/template/messager.jsp"%>
    <%@ include file="/front-end/template/basic_js.jsp"%>
    <script src="<%=request.getContextPath() %>/SmartAdmin4/js/formplugins/summernote/summernote.js"></script>
    <script>
        $(document).ready(function () {

            var myUserNo = '${userVO.userNo}';

            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });

            $('#democratNote').summernote({
                height: 300,
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
                callbacks: {}
            });

            $('#btn-add').click(function () {
                $('#courseAskModal').modal('show');
            });

            $(document).on('click', 'button.reply', function () {
                let _courseAskNo = this.getAttribute('courseAskNo');
                $('#formReply').find('[name=courseAskNo]').val(_courseAskNo);
                $('#replyModal').modal('show');
            });

            $(document).on('click', 'div.card.courseAsk', function (e) {
                let _courseAskNo = this.getAttribute('courseAskNo');
                $.ajax({
                    beforeSend() { },
                    type: 'POST',
                    url: `<%=request.getContextPath()%>/reply/reply.ajax`,
                    data: {
                        action: 'getAllWithCourseAskNo',
                        courseAskNo: _courseAskNo
                    },
                    success(res) {
                        loadReplyList(_courseAskNo, res);
                    },
                    complete() { }
                });
            });

            $('#sendReply').click(function (e) {
                e.preventDefault();
                let _courseAskNo = $(formReply).find('input[name=courseAskNo]').val();
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/reply/reply.ajax',
                    data: $(formReply).serialize(),
                    success(res) {
                        loadReplyList(_courseAskNo, res);
                        $('#replyModal').modal('hide');
                    }
                });
            });

            function loadReplyList(courseAskNo, array) {
                let divReplyList = $(`div.replyList[courseAskNo=${'${courseAskNo}'}]`);
                if (array.length == 0)
                    return;
                divReplyList.html('');
                for (let i = 0; i < array.length; i++) {
                    let replyContent = array[i].replyContent;
                    let userName = array[i].userName;
                    let updateTime = array[i].updateTime;
                    let userNo = array[i].userNo;

                    let _html = `
<div class="d-flex flex-row px-3 pt-3 pb-2">
  <span>
      <span class="profile-image rounded-circle d-inline-block" style="background-image:url('<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${'${userNo}'}')"></span>
  </span>
  <div class="ml-3">
      <a href="javascript:void(0);" title="Lisa Hatchensen" class="fs-b d-block fw-700 text-dark">${'${userName}'} 時間 ${'${updateTime}'}</a>
      <div class="fs-xl">
        ${'${replyContent}'}     
      </div>
  </div>
</div>
                    
                    `;
                    divReplyList.append(_html);
                    $(`span.replySize[courseAskNo=${'${courseAskNo}'}]`).text(array.length);
                }
            }
        });

    </script>
</body>

</html>