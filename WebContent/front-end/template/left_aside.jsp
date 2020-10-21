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
                <a href="<%=request.getContextPath()%>/front-end/index/index.jsp" title="前台首頁">
                    <i class="fal fa-chart-area"></i>
                    <span class="nav-link-text">後台首頁</span>
                </a>
            </li>
            <li>
                <a href="#" title="我的養成班">
                    <i class="fal fa-users-class"></i>
                    <span class="nav-link-text">我的養成班</span>
                </a>
            </li>
            <li>
                <a href="#" title="我的課程">
                    <i class="fal fa-book"></i>
                    <span class="nav-link-text">我的課程</span>
                </a>
            </li>
            <li>
                <a href="<%=request.getContextPath() %>/front-end/leave/leave.jsp" title="請假">
                    <i class="fal fa-file-edit"></i>
                    <span class="nav-link-text">請假申請</span>
                </a>
            </li>
             <li>
                <a href="<%=request.getContextPath() %>/front-end/courseAsk/courseAsk.jsp" title="課程提問">
                    <i class="fal fa-file-edit"></i>
                    <span class="nav-link-text">課程提問</span>
                </a>
            </li>
            <li>
                <a href="#" title="出缺勤">
                    <i class="fal fa-briefcase"></i>
                    <span class="nav-link-text">出缺勤</span>
                </a>
            </li>
            <li>
                <a href="<%=request.getContextPath() %>/front-end/evaluation/selectEvaluation.jsp" title="教學評鑑">
                    <i class="fal fa-clipboard-check"></i>
                    <span class="nav-link-text">教學評鑑</span>
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
            <li>
                <a href="<%=request.getContextPath() %>/front-end/democrat/democrat.jsp" title="DEMOCRAT">
                    <i class="fal fa-democrat"></i>
                    <span class="nav-link-text">DEMOCRAT</span>
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