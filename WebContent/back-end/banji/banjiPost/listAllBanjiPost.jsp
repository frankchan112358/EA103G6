<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.banjipost.model.*"%>

<%

%>

<jsp:useBean id="banjiSvc" scope="page"
			class="com.banji.model.BanjiService" />

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/back-end/template/head.jsp" %>
    <style type="text/css">
    
    #add{
    position:absolute;
    top:65px;
	right:70px;
    }
    #add1{
    position:absolute;
    top:65px;
	right:120px;
	 -webkit-box-shadow: 0 2px 6px 0 rgba(255,178, 15);
    }
    #add2{
    position:absolute;
    top:65px;
	right:20px;
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
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.manage">養成班管理</a></li>
                        <li class="breadcrumb-item"><a id="gotoReadBanji" href="javascript:void(0)">${banjiVO.banjiName}</a></li>
                        <li class="breadcrumb-item">公告管理</li>
                    </ol>
                    
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon ni ni-note' ></i> 公告管理
                        </h1>
                    </div>
                    <form method="post"action="<%=request.getContextPath()%>/banji/banji.banjipost"class="m-1">
                    <div id="add">
							<input type="hidden" name="action" value="insert"> 
							<button type="submit" id="btn-add" style="width:150px;heigh:50px;"style="margin-bottom: 0px"
								class="btn-write btn btn-sm btn-primary" >
								<strong>新增公告</strong>
							</button>
						</div>
						</form>
						<div class="row">
						<div class="col col-xl-12">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">總覽</h2>
								</div>
                               <div class="panel-container show" style="margin: 20px 20px;">
                                    <div style="text-align: center;">
                                        <c:if test="${empty list}">
                                            <h2>
                                                <i class="fal fa-calendar-times"></i>
                                                目前尚無任何公告
                                                <i class="fal fa-calendar-times"></i>
                                            </h2>
                                        </c:if>
                                    </div>
                                    <div>
                                    <div class="panel-content">
                                        <!-- datatable start -->
                                        <c:forEach var="banjiPostVO" items="${list}">
                                       <div class="card mb-g border shadow-0">
                                    <div class="card-header p-0">
                                        <div class="p-3 d-flex flex-row">
                                            <div class="d-block flex-shrink-1">
                                            <i class="fas fa-star mr-1"></i>
                                            <span> ${banjiPostVO.title}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-body ">
                                        <span>
                                        ${banjiPostVO.banjiPostContent}</span>
                                    	</div>
                           
                                     <form method="post"action="<%=request.getContextPath()%>/banji/banji.banjipost"class="m-1">
										<button  id="add1" type="submit" class="btn btn-sm btn-warning">
											<span class="fal fa-edit mr-1"></span> <span>修改</span>
										</button>
										<input type="hidden" name="action"value="getOne_For_Update"> <input type="hidden"name="banjiPostNo" value="${banjiPostVO.banjiPostNo}">
									</form>
									 <form method="post"action="<%=request.getContextPath()%>/banji/banji.banjipost"class="m-1">
									<button  id="add2" type="submit" class="btn1 btn-sm btn-danger">
											<span class="fal fa-times mr-1"></span> <span>刪除</span>
										</button>
										<input type="hidden" name="action"value="delete"> <input type="hidden"name="banjiPostNo" value="${banjiPostVO.banjiPostNo}">
                                    </form>
                                    <div class="card-footer">
                                        <div class="d-flex align-items-center">
                                         <span class="text-sm text-muted font-italic"><i class="fal fa-clock mr-1"></i><span>編輯時間:</span><fmt:formatDate value="${banjiPostVO.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                        </div>
                                    </div>
                                </div>
                                     </c:forEach>
                                    </div>
                                </div> </div> </div> </div>
                		</div></main>
                </div>
                </div>
                </div>
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
                <%@ include file="/back-end/template/footer.jsp" %>
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %>
    <script>
    $(document).ready(function () {
            $("th").addClass("align-middle");
            $("td").addClass("align-middle");
            
            $(document).on('click', 'button.btn1', function (event) {
                event.preventDefault();
                let _this = $(this);
                Swal.fire({
                    title: "你確定要刪除嗎?",
                    text: "如果刪除後，就無法復原了!",
                    type: "warning",
                    showCancelButton: true,
                    cancelButtonText: "先緩緩",
                    confirmButtonText: "我要刪除"
                }).then(function (result) {
                    if (result.value)
                        _this.parent()[0].submit();
                });
            });
            
            $('#gotoReadBanji').click(function () {
                let _this = this;
                let banjiNo = this.getAttribute('banjiNo');
                let myForm = banjiGotoFrom('<%=request.getContextPath()%>/banji/banji.manage');
                document.body.appendChild(myForm);
                myForm.append(banjiGotoInput('hidden', 'action', 'read'));
                myForm.submit();
            });

            function banjiGotoFrom(url) {
                let myForm = document.createElement('form');
                myForm.action = url;
                myForm.method = 'POST';
                return myForm;
            }

            function banjiGotoInput(type, name, value) {
                let banjiInput = document.createElement('input');
                banjiInput.type = type;
                banjiInput.name = name;
                banjiInput.value = value;
                return banjiInput;
            }

        });
    </script>
</body>

</html>