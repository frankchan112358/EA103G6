<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- to add more items, please make sure to change the variable '$menu-items: number;' in your _page-components-shortcut.scss -->
<nav class="shortcut-menu d-none d-sm-block">
    <input type="checkbox" class="menu-open" name="menu-open" id="menu_open" />
    <label for="menu_open" class="menu-open-button ">
        <span class="app-shortcut-icon d-block"></span>
    </label>
    <a href="javascript:void(0)" class="menu-item btn" data-toggle="tooltip" data-placement="left" title="返回頂部">
        <i class="fal fa-arrow-up"></i>
    </a>
    <a href="<%=request.getContextPath() %>/test/testlogout.do" class="menu-item btn" data-toggle="tooltip" data-placement="left" title="登出">
        <i class="fal fa-sign-out"></i>
    </a>
    <a href="javascript:void(0)" class="menu-item btn" data-action="app-fullscreen" data-toggle="tooltip" data-placement="left" title="全螢幕">
        <i class="fal fa-expand"></i>
    </a>
</nav>