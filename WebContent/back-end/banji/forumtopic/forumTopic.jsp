<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp" %>
<%@ page import="com.forumtopic.model.*"%>
<%@ page import="com.banji.model.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />
<jsp:useBean id="forumtopicSvc" scope="page" class="com.forumtopic.model.ForumTopicService" />
<%
%>

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/back-end/template/head.jsp" %>
<style type="text/css">
    .table th,
    .table td {
        vertical-align: middle;
        text-align: center;
    }

    #add {
        position: absolute;
        top: 65px;
        right: 70px;
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
            <%@ include file="/back-end/template/left_aside.jsp"%>
            <div class="page-content-wrapper">
                <%@ include file="/back-end/template/header.jsp"%>
                <main id="js-page-content" role="main" class="page-content">
                    <ol class="breadcrumb page-breadcrumb">
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.manage">養成班管理</a></li>
                        <li class="breadcrumb-item"><a id="gotoReadBanji" href="javascript:void(0)">${banjiSvc.getOneBanji(banjiNo).banjiName}</a></li>
                        <li class="breadcrumb-item">主題管理</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-comments-alt'></i> 主題總覽
                        </h1>
                    </div>
                    <div id="add">
                        <input type="hidden" name="action">
                        <button id="btnAdd" type="button" class="btn btn-sm btn-success" style="width:100px;float:right;" style="margin-bottom: 0px'" class="btn-write btn btn-sm btn-primary">
                            <strong>新增</strong>
                        </button>
                    </div>
                    <div class="row">
                        <div class="col col-xl-12">
                            <div id="panel-1" class="panel">
                                <div class="panel-hdr bg-primary-800 bg-success-gradient ">
                                    <h2 class="text-white">主題列表</h2>
                                </div>
                                <div class="panel-container show">
                                    <div class="panel-content">
                                        <!-- datatable start -->
                                        <table id="topicTable" class="table table-bordered table-hover table-striped w-100">
                                            <thead>
                                                <tr>
                                                    <th>主題編號</th>
                                                    <th>班級名稱</th>
                                                    <th>主題名稱</th>
                                                    <th>管理</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="forumTopicVO" items="${forumtopicSvc.getByBanJiNo(banjiNo)}">
                                                    <tr>
                                                        <td>${forumTopicVO.forumTopicNo}</td>
                                                        <td>${banjiSvc.getOneBanji(banjiNo).banjiName}</td>
                                                        <td>${forumTopicVO.forumTopicName}</td>
                                                        <td class="d-flex">
                                                            <form method="post" action="<%=request.getContextPath()%>/banji/banji.forumtopic">
                                                                <input type="hidden" name="action" value="getOne_For_Update">
                                                                <input type="hidden" name="forumTopicNo" value="${forumTopicVO.forumTopicNo}">
                                                                <input type="hidden" name="banjiNo" value="${banjiNo}">
                                                                <button type="submit" class="btn btn-sm btn-info">
                                                                    <span class="fal fa-edit mr-1"></span>
                                                                    <span>修改</span>
                                                                </button>
                                                            </form>
                                                            <form method="post" action="<%=request.getContextPath()%>/banji/banji.forumtopic" class="ml-2">
                                                                <input type="hidden" name="action" value="delete">
                                                                <input type="hidden" name="forumTopicNo" value="${forumTopicVO.forumTopicNo}">
                                                                <input type="hidden" name="banjiNo" value="${banjiNo}">
                                                                <button type="submit" class="btn btn-sm btn-danger">
                                                                    <span class="fal fa-times mr-1"></span>
                                                                    <span>刪除</span>
                                                                </button>
                                                            </form>
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
                <%@ include file="/back-end/template/footer.jsp"%>
            </div>
        </div>
    </div>
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %>

    <script>
        'use strict';
        $(document).ready(function () {
            $('#topicTable').dataTable({
                responsive: true,
                language: { url: `<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json` }
            });

            $('#btnAdd').click(function () {
                let _this = this;
                let myForm = banjiGotoFrom('<%=request.getContextPath()%>/back-end/banji/forumtopic/addforumTopic.jsp');
                document.body.appendChild(myForm);
                myForm.submit();
            });

            $('#gotoReadBanji').click(function () {
                let _this = this;
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