<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="page-sidebar">
    <div class="page-logo">
        <a href="#" class="page-logo-link press-scale-down d-flex align-items-center position-relative" data-toggle="modal" data-target="#modal-shortcut">
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
                <a href="#" onclick="return false;" class="btn-primary btn-search-close js-waves-off" data-action="toggle" data-class="list-filter-active" data-target=".page-sidebar">
                    <i class="fal fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <ul id="js-nav-menu" class="nav-menu" style="justify-content:center;">
            <li>
                <a href="#" title="後台首頁">
                    <i class="fal fa-chart-area"></i>
                    <span class="nav-link-text">後台首頁</span>
                </a>
            </li>
            <li>
                <a href="#" title="成員管理">
                    <i class="fal fa-users"></i>
                    <span class="nav-link-text">成員管理</span>
                </a>
                <ul>
                    <li>
                        <a href="<%=request.getContextPath()%>/user.do?action=checkPermission&goto=studentList" title="學員管理">
                            <span class="nav-link-text">學員管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/user.do?action=checkPermission&goto=teacherList" title="講師管理">
                            <span class="nav-link-text">講師管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/user.do?action=checkPermission&goto=empList" title="導師管理">
                            <span class="nav-link-text">導師管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" title="權限管理">
                            <span class="nav-link-text">權限管理</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#" title="養成班管理">
                    <i class="fal fa-users-class"></i>
                    <span class="nav-link-text">養成班管理</span>
                </a>
                <ul>
                    <li>
                        <a href="#" title="Java雲端">
                            <span class="nav-link-text">Java雲端</span>
                        </a>
                        <ul>
                            <li>
                                <a href="#" title="EA101">
                                    <span class="nav-link-text">EA101</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" title="EA102">
                                    <span class="nav-link-text">EA102</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" title="EA103">
                                    <span class="nav-link-text">EA103</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#" title="前端設計">
                            <span class="nav-link-text">前端設計</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" title="大數據分析">
                            <span class="nav-link-text">大數據分析</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#" title="課程管理">
                    <i class="fal fa-book"></i>
                    <span class="nav-link-text">課程管理</span>
                </a>
            </li>
            <li>
                <a href="#" title="班種管理">
                    <i class="fal fa-chalkboard-teacher"></i>
                    <span class="nav-link-text">班種管理</span>
                </a>
                <ul>
                    <li>
                        <a href="#" title="Java雲端">
                            <span class="nav-link-text">Java雲端</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" title="前端設計">
                            <span class="nav-link-text">前端設計</span>
                        </a>
                    </li>
                    <li>
                        <a href="#" title="大數據分析">
                            <span class="nav-link-text">大數據分析</span>
                        </a>
                    </li>
                </ul>
            </li>                        
            <li>
                <a href="#" title="基本課程管理">
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
                <a href="#" title="帳號管理">
                    <i class="fal fa-user-cog"></i>
                    <span class="nav-link-text">帳號管理</span>
                </a>
            </li>
            <li>
                <a href="#" title="訊息中心">
                    <i class="fal fa-comments"></i>
                    <span class="nav-link-text">訊息中心</span>
                </a>
            </li>
            <li>
                <a href="#" title="通知中心">
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
        <a href="#" onclick="return false;" data-action="toggle" data-class="nav-function-minify" class="hidden-md-down">
            <i class="ni ni-chevron-right"></i>
            <i class="ni ni-chevron-right"></i>
        </a>
    </div> <!-- END NAV FOOTER -->
</aside>