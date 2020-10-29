<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="page-header" role="banner">
    <!-- we need this logo when user switches to nav-function-top -->
    <div class="page-logo">
        <a href="javascript:void(0)" class="page-logo-link press-scale-down d-flex align-items-center position-relative" data-toggle="modal" data-target="#modal-shortcut">
            <i class="fal fa-thumbs-up" style="color: aliceblue;font-size: x-large;"></i>
            <span class="page-logo-text mr-1">Work Join Learn</span>
            <span class="position-absolute text-white opacity-50 small pos-top pos-right mr-2 mt-n2"></span>
        </a>
    </div>
    <!-- DOC: nav menu layout change shortcut -->
    <div class="hidden-md-down dropdown-icon-menu position-relative">
        <a href="javascript:void(0)" class="header-btn btn js-waves-off" data-action="toggle" data-class="nav-function-hidden" title="Hide Navigation">
            <i class="ni ni-menu"></i>
        </a>
        <ul>
            <li>
                <a href="javascript:void(0)" class="btn js-waves-off" data-action="toggle" data-class="nav-function-minify" title="Minify Navigation">
                    <i class="ni ni-minify-nav"></i>
                </a>
            </li>
            <li>
                <a href="javascript:void(0)" class="btn js-waves-off" data-action="toggle" data-class="nav-function-fixed" title="Lock Navigation">
                    <i class="ni ni-lock-nav"></i>
                </a>
            </li>
        </ul>
    </div>
    <!-- DOC: mobile button appears during mobile width -->
    <div class="hidden-lg-up">
        <a href="javascript:void(0)" class="header-btn btn press-scale-down" data-action="toggle" data-class="mobile-nav-on">
            <i class="ni ni-menu"></i>
        </a>
    </div>
    <div class="ml-auto d-flex">
        <!-- app message -->
        <a href="javascript:void(0)" class="header-icon" data-toggle="modal" data-target=".js-modal-messenger">
            <i class="fal fa-comments"></i>
            <span class="badge badge-icon">!</span>
        </a>
        <!-- app notification -->
        <div>
            <a href="javascript:void(0)" class="header-icon" data-toggle="dropdown" title="You got 2 notifications">
                <i class="fal fa-bell"></i>
                <span class="badge badge-icon">2</span>
            </a>
            <div class="dropdown-menu dropdown-menu-animated dropdown-xl">
                <div class="dropdown-header bg-trans-gradient d-flex justify-content-center align-items-center rounded-top mb-2">
                    <h4 class="m-0 text-center color-white">
                        2 個未讀
                        <small class="mb-0 opacity-80">你的通知</small>
                    </h4>
                </div>
                <ul class="nav nav-tabs nav-tabs-clean" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link px-4 fs-md js-waves-on fw-500" data-toggle="tab" href="#tab-1">重要</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link px-4 fs-md js-waves-on fw-500" data-toggle="tab" href="#tab-2">未讀</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link px-4 fs-md js-waves-on fw-500" data-toggle="tab" href="#tab-3">已讀</a>
                    </li>
                </ul>
                <div class="tab-content tab-notification">
                    <div class="tab-pane" id="tab-1" role="tabpanel">
                        <div class="custom-scroll h-100">
                            <ul class="notification" id="decorateForNotification">
                                <li class="unread">
                                    <div class="d-flex align-items-center show-child-on-hover">
                                        <span class="d-flex flex-column flex-1">
                                            <span class="name d-flex align-items-center">Administrator <span class="badge badge-success fw-n ml-1">UPDATE</span></span>
                                            <span class="msg-a fs-sm">
                                                System updated to version <strong>4.5.1</strong> <a href="<%=request.getContextPath() %>/SmartAdmin4/docs_buildnotes.html">(patch notes)</a>
                                            </span>
                                            <span class="fs-nano text-muted mt-1">5 mins ago</span>
                                        </span>
                                        <div class="show-on-hover-parent position-absolute pos-right pos-bottom p-3">
                                            <a href="javascript:void(0)" class="text-muted" title="delete"><i class="fal fa-trash-alt"></i></a>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="tab-pane" id="tab-2" role="tabpanel">
                        <div class="custom-scroll h-100">
                            <ul class="notification">
                                <li class="unread">
                                    <div class="d-flex align-items-center show-child-on-hover">
                                        <span class="d-flex flex-column flex-1">
                                            <span class="name d-flex align-items-center">Administrator <span class="badge badge-success fw-n ml-1">UPDATE</span></span>
                                            <span class="msg-a fs-sm">
                                                System updated to version <strong>4.5.1</strong> <a href="<%=request.getContextPath() %>/SmartAdmin4/docs_buildnotes.html">(patch notes)</a>
                                            </span>
                                            <span class="fs-nano text-muted mt-1">5 mins ago</span>
                                        </span>
                                        <div class="show-on-hover-parent position-absolute pos-right pos-bottom p-3">
                                            <a href="javascript:void(0)" class="text-muted" title="delete"><i class="fal fa-trash-alt"></i></a>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="tab-pane" id="tab-3" role="tabpanel">
                        <div class="custom-scroll h-100">
                            <ul class="notification">
                                <li class="unread">
                                    <div class="d-flex align-items-center show-child-on-hover">
                                        <span class="d-flex flex-column flex-1">
                                            <span class="name d-flex align-items-center">Administrator <span class="badge badge-success fw-n ml-1">UPDATE</span></span>
                                            <span class="msg-a fs-sm">
                                                System updated to version <strong>4.5.1</strong> <a href="<%=request.getContextPath() %>/SmartAdmin4/docs_buildnotes.html">(patch notes)</a>
                                            </span>
                                            <span class="fs-nano text-muted mt-1">5 mins ago</span>
                                        </span>
                                        <div class="show-on-hover-parent position-absolute pos-right pos-bottom p-3">
                                            <a href="javascript:void(0)" class="text-muted" title="delete"><i class="fal fa-trash-alt"></i></a>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="py-2 px-3 bg-faded d-block rounded-bottom text-right border-faded border-bottom-0 border-right-0 border-left-0">
                    <a href="javascript:void(0)" class="fs-xs fw-500 ml-auto">觀看所有通知</a>
                </div>
            </div>
        </div>
        <!-- app user menu -->
        <div>
            <a href="javascript:void(0)" data-toggle="dropdown" class="header-icon d-flex align-items-center justify-content-center ml-2">
                <c:if test="${userVO.photo != null}" var="condition" scope="page">
                    <img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${userVO.userNo}" class="rounded-circle profile-image">
                </c:if>
                <c:if test="${condition == false}">
                    <img src="<%=request.getContextPath() %>/images/noPicture.png" class="rounded-circle profile-image">
                </c:if>
            </a>
            <div class="dropdown-menu dropdown-menu-animated dropdown-lg">
                <div class="dropdown-header bg-trans-gradient d-flex flex-row py-4 rounded-top">
                    <div class="d-flex flex-row align-items-center mt-1 mb-1 color-white">
                        <span class="mr-2">
                        	<c:if test="${userVO.photo != null}" var="condition" scope="page">
                     	    	<img src="<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${userVO.userNo}" class="rounded-circle profile-image">
                        	</c:if>
							<c:if test="${condition == false}">
								<img src="<%=request.getContextPath() %>/images/noPicture.png" class="rounded-circle profile-image">
							</c:if>
                        </span>
                        <div class="info-card-text">
                            <div class="fs-lg text-truncate text-truncate-lg">${userVO.name }</div>
                        </div>
                    </div>
                </div>
                <div class="dropdown-divider m-0"></div>                                    
                 <a href="<%=request.getContextPath() %>/front-end/accountmanager/listOneStu.jsp" class="dropdown-item fw-500 pt-3 pb-3">
                    <span data-i18n="drpdwn.settings">帳號設定</span>
                </a>
                
                <div class="dropdown-divider m-0"></div>
                <a href="<%=request.getContextPath() %>/front-end/forumpost/studentCenter_forum.jsp" class="dropdown-item fw-500 pt-3 pb-3">
                    <span data-i18n="drpdwn.post-record">討論區發文紀錄</span>
                </a>
<%--                <a class="submenu-item" href="<%= request.getContextPath()%>/front-end/forumpost/studentCenter_forum.jsp"></a> --%>
                
                
                <div class="dropdown-divider m-0"></div>
                <a href="<%=request.getContextPath() %>/logout/logout.do" class="dropdown-item fw-500 pt-3 pb-3">
                    <span data-i18n="drpdwn.page-logout">登出</span>
                </a>
            </div>
        </div>
    </div>
</header>