<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="page-sidebar">
    <div class="page-logo">
        <a href="" class="page-logo-link press-scale-down d-flex align-items-center position-relative" data-toggle="modal" data-target="#modal-shortcut">
            <i class="fal fa-thumbs-up" style="color: aliceblue;font-size: x-large;"></i>
            <span class="page-logo-text mr-1">Work Join Learn</span>
            <span class="position-absolute text-white opacity-50 small pos-top pos-right mr-2 mt-n2"></span>
        </a>
    </div>
    <!-- BEGIN PRIMARY NAVIGATION -->
    <nav id="js-primary-nav" class="primary-nav" role="navigation">
        <div class="nav-filter">
            <div class="position-relative">
                <input type="text" id="nav_filter_input" placeholder="Filter menu" class="form-control" tabindex="0">
                <a href="" onclick="return false;" class="btn-primary btn-search-close js-waves-off" data-action="toggle" data-class="list-filter-active" data-target=".page-sidebar">
                    <i class="fal fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <ul id="js-nav-menu" class="nav-menu" style="justify-content:center;">
            <li>
                <a href="<%=request.getContextPath()%>/back-end/index/index.jsp" title="後台首頁">
                    <i class="fal fa-chart-area"></i>
                    <span class="nav-link-text">後台首頁</span>
                </a>
            </li>
            <li id="menberManage">
                <a href="" title="成員管理">
                    <i class="fal fa-users"></i>
                    <span class="nav-link-text">成員管理</span>
                </a>
                <ul>
                    <li>
                        <a href="<%=request.getContextPath()%>/back-end/student/studentList.jsp?goto=studentList" title="學員管理">
                            <span class="nav-link-text">學員管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/back-end/teacher/teacherList.jsp?goto=teacherList" title="講師管理">
                            <span class="nav-link-text">講師管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/back-end/emp/empList.jsp?goto=empList" title="導師管理">
                            <span class="nav-link-text">導師管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="" title="權限管理">
                            <span class="nav-link-text">權限管理</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li id="banjiManage">
                <c:if test="${userVO!=null && userVO.type==2}">
                    <a href="<%=request.getContextPath()%>/banji/banji.manage" title="養成班管理">
                        <i class="fal fa-users-class"></i>
                        <span class="nav-link-text">養成班管理</span>
                    </a>
                    <jsp:useBean id="as_banjiSvc" scope="page" class="com.banji.model.BanjiService" />
                    <jsp:useBean id="as_banjiTypeSvc" scope="page" class="com.banjitype.model.BanjiTypeService" />
                    <c:if test="${as_banjiSvc.getBanjiGroup(empVO.empNo).keySet().size()>0}">
                        <ul>
                            <c:forEach var="key" items="${as_banjiSvc.getBanjiGroup(empVO.empNo).keySet()}">
                                <li>
                                    <a href="" title="${as_banjiTypeSvc.getOneBanjiType(key).banjiTypeName}">
                                        <span class="nav-link-text">${as_banjiTypeSvc.getOneBanjiType(key).banjiTypeName}</span>
                                    </a>
                                    <ul>
                                        <c:forEach var="banjiVO" items="${as_banjiSvc.getBanjiGroup(empVO.empNo).get(key).values()}">
                                            <li>
                                                <form method="post" action="<%=request.getContextPath()%>/banji/banji.manage">
                                                    <button style="width:100%;background-color: transparent;border: none;padding: 0;" type="submit"><a><span class="nav-link-text">${banjiVO.banjiName}</span></a></button>
                                                    <input type="hidden" name="banjiNo" value="${banjiVO.banjiNo}">
                                                    <input type="hidden" name="action" value="manage">
                                                </form>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </c:if>
            </li>
            <li>
                <a href="<%=request.getContextPath()%>/back-end/course/listAllCourse.jsp"" title="課程管理">
                    <i class="fal fa-book"></i>
                    <span class="nav-link-text">課程管理</span>
                </a>
            </li>
            <li id="banjiTypeManage">
                <a href="<%=request.getContextPath()%>/back-end/banjiType/homeBanjiType.jsp" title="班種管理">
                    <i class="fal fa-chalkboard-teacher"></i>
                    <span class="nav-link-text">班種管理</span>
                </a>
                <ul>
                    <li>
                        <a href="" title="Java雲端">
                            <span class="nav-link-text">Java雲端</span>
                        </a>
                    </li>
                    <li>
                        <a href="" title="前端設計">
                            <span class="nav-link-text">前端設計</span>
                        </a>
                    </li>
                    <li>
                        <a href="" title="大數據分析">
                            <span class="nav-link-text">大數據分析</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="" title="基本課程管理">
                    <i class="fal fa-book-open"></i>
                    <span class="nav-link-text">基本課程管理</span>
                </a>
            </li>
            <li>
                <a href="<%=request.getContextPath()%>/leave/leave.manage" title="學員請假審核">
                    <i class="fal fa-file-edit"></i>
                    <span class="nav-link-text">學員請假審核</span>
                </a>
            </li>
            <li>
                <a href="" title="帳號管理">
                    <i class="fal fa-user-cog"></i>
                    <span class="nav-link-text">帳號管理</span>
                </a>
            </li>
            <li>
                <a href="" title="訊息中心">
                    <i class="fal fa-comments"></i>
                    <span class="nav-link-text">訊息中心</span>
                </a>
            </li>
            <li >
                <a href="<%=request.getContextPath()%>/back-end/banjiPost/listAllBanjiPost.jsp" title="公告管理">
                    <i class="fal fa-book"></i>
                    <span class="nav-link-text">公告管理</span>
                </a>
            </li>
            <li>
                <a href="" title="通知中心">
                    <i class="fal fa-bell"></i>
                    <span class="nav-link-text">通知中心</span>
                </a>
            </li>
        </ul>
        <div class="filter-message js-filter-message bg-success-600"></div>
    </nav>
    <!-- END PRIMARY NAVIGATION -->
    <!-- NAV FOOTER -->
    <div class="nav-footer shadow-top">
        <a href="" onclick="return false;" data-action="toggle" data-class="nav-function-minify" class="hidden-md-down">
            <i class="ni ni-chevron-right"></i>
            <i class="ni ni-chevron-right"></i>
        </a>
    </div> <!-- END NAV FOOTER -->
</aside>