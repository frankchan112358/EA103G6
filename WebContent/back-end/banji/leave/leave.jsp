<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="leaveSvc" scope="page" class="com.leave.model.LeaveService" />
<%
//init code
%>

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/back-end/template/head.jsp" %>
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
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.manage?action=read&banjiNo=${banjiVO.banjiNo}">${banjiVO.banjiName}</a></li>
                        <li class="breadcrumb-item">學員請假審核</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-file-edit'></i> 學員請假審核
                        </h1>
                    </div>
                    <div class="row">
                        <div class="col col-xl-12">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">總覽</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <!-- datatable start -->
                                        <table id="leaveTable" class="table table-bordered table-hover table-striped w-100">
                                            <thead>
                                                <tr>
                                                    <th>班級</th>
                                                    <th>學號</th>
                                                    <th>學生</th>
                                                    <th>日期</th>
                                                    <th>時段</th>
                                                    <th>假別</th>
                                                    <th>狀態</th>
                                                    <th>操作</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="leaveVO" items="${list}">
                                                    <tr>
                                                        <td>${leaveVO.studentVO.banjiVO.banjiName}</td>
                                                        <td>${leaveVO.studentVO.studentNo}</td>
                                                        <td>${leaveVO.studentVO.studentName}</td>
                                                        <td>${leaveVO.timetableVO.timetableDate}</td>
                                                        <td>${leaveVO.timetableVO.periodText}</td>
                                                        <td>${leaveVO.typeText}</td>
                                                        <td>${leaveVO.statusText}</td>
                                                        <td class="d-flex p-1">
                                                            <form method="post" action="<%=request.getContextPath()%>/banji/banji.leave" class="m-1">
                                                                <button type="submit" class="btn btn-sm btn-success">
                                                                    <span class="fal fa-edit mr-1"></span>
                                                                    <span>檢視</span>
                                                                </button>
                                                                <input type="hidden" name="banjiNo" value="${banjiVO.banjiNo}">
                                                                <input type="hidden" name="leaveNo" value="${leaveVO.leaveNo}">
                                                                <input type="hidden" name="action" value="read">                                                            
                                                            </form>
                                                            <c:if test="${leaveVO.status==0}">
                                                                <form method="post" action="<%=request.getContextPath()%>/banji/banji.leave" class="m-1">
                                                                    <button type="submit" class="btn btn-sm btn-info">
                                                                        <span class="fal fa-edit mr-1"></span>
                                                                        <span>審核</span>
                                                                    </button>
                                                                    <input type="hidden" name="banjiNo" value="${banjiVO.banjiNo}">
                                                                    <input type="hidden" name="leaveNo" value="${leaveVO.leaveNo}">
                                                                    <input type="hidden" name="action" value="review">
                                                                </form>
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
                        </div>
                    </div>
                </main>
                <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
                <%@ include file="/back-end/template/footer.jsp" %>
            </div>
        </div>
    </div>
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %>
    <script>
        'use strict';
        $(document).ready(function () {
            $('#leaveTable').dataTable({
                responsive: true,
                language: { url: `<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json` }
            });
        });
    </script>
</body>

</html>