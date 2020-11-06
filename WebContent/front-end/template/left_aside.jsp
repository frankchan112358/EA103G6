<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="page-sidebar">
    <div class="page-logo">
        <a href="javascript:void(0)" class="page-logo-link press-scale-down d-flex align-items-center position-relative" data-toggle="modal" data-target="#modal-shortcut">
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
                <a href="javascript:void(0)" onclick="return false;" class="btn-primary btn-search-close js-waves-off" data-action="toggle" data-class="list-filter-active" data-target=".page-sidebar">
                    <i class="fal fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <ul id="js-nav-menu" class="nav-menu" style="justify-content:center;">
            <li>
                <a href="<%=request.getContextPath()%>/front-end/index/index.jsp" title="前台首頁">
                    <i class="fal fa-chart-area"></i>
                    <span class="nav-link-text">前台首頁</span>
                </a>
            </li>
            <li>
                <a href="<%=request.getContextPath() %>/front-end/course/selectCourse.jsp" title="我的課程">
                    <i class="fal fa-book"></i>
                    <span class="nav-link-text">我的課程</span>
                </a>
            </li>
            <li>
                <a href="<%=request.getContextPath() %>/forum/forum.do" title="班級討論區">
                    <i class="fal fa-comments-alt"></i>
                    <span class="nav-link-text">班級討論區</span>
                </a>
            </li>
            <li>
                <a href="<%=request.getContextPath() %>/leave/leave.handle" title="請假申請">
                    <i class="fal fa-file-edit"></i>
                    <span class="nav-link-text">請假申請</span>
                </a>
            </li>
        </ul>
        <div class="filter-message js-filter-message bg-success-600"></div>
    </nav>
    <!-- END PRIMARY NAVIGATION -->
    <!-- NAV FOOTER -->
    <div class="nav-footer shadow-top">
        <a href="javascript:void(0)" onclick="return false;" data-action="toggle" data-class="nav-function-minify" class="hidden-md-down">
            <i class="ni ni-chevron-right"></i>
            <i class="ni ni-chevron-right"></i>
        </a>
    </div> <!-- END NAV FOOTER -->
</aside>