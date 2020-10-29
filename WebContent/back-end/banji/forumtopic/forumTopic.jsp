<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/back-end/template/check.jsp" %>
<%@ page import="com.forumtopic.model.*"%>
<%@ page import="com.banji.model.*"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="forumtopicSvc" scope="page" class="com.forumtopic.model.ForumTopicService" />
<%
 BanjiVO banjiVO = (BanjiVO)request.getAttribute("banjiVO");


 ForumTopicService forumtopicSvcList =new ForumTopicService();
 List<ForumTopicVO> forumTopicList =forumtopicSvcList.getByBanJiNo(banjiVO.getBanjiNo());

 pageContext.setAttribute("forumTopicList", forumTopicList);

%>

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/back-end/template/head.jsp" %>
    <style type="text/css">
.table th, .table td {
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
                         <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/banji/banji.manage?action=read">${banjiVO.banjiName}</a></li>
                    	<li class="breadcrumb-item">主題管理</li>
                    </ol>
                    <div class="subheader">
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-file-edit'></i> 主題總覽
                        </h1>
                    </div>
<%--                     <form method="post"action="<%=request.getContextPath()%>/banji/banji.forumtopic"class="m-1"> --%>
<!-- 					<div id="add"> -->
<!-- 							<input type="hidden" name="action" value="insert">  -->
<!-- 							<button type="submit" id="btn-add" style="width:150px;heigh:50px;" style="margin-bottom: 0px" -->
<!-- 								class="btn-write btn btn-sm btn-primary" > -->
<!-- 								<strong>新增主題</strong> -->
<!-- 							</button> -->
<!-- 						</div></form> -->
						 <div id="add">
							<input type="hidden" name="action" > 
							 <button type="submit" class="btn btn-sm btn-success" style="width:100px;heigh:10px;float:right;" onclick="location.href='<%=request.getContextPath()%>/back-end/banji/forumtopic/addforumTopic.jsp?banjiNo=${banjiVO.banjiNo}'" style="margin-bottom: 0px'"
								class="btn-write btn btn-sm btn-primary" >
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
                                                <c:forEach var="forumTopicVO" items="${forumTopicList}">
                                       		<tr onclick="location.href='<%=request.getContextPath()%>/banji/banji.forumtopic?action=getOne_For_Display&forumTopicNo=${forumTopicVO.forumTopicNo}';">
                                                        <td>${forumTopicVO.forumTopicNo}</td>
                                                        <td>${banjiVO.banjiName}</td>
                                                        <td>${forumTopicVO.forumTopicName}</td>
                                                        <td>
                                                            <form method="post" action="<%=request.getContextPath()%>/banji/banji.forumtopic" >
                                                               
																<input type="hidden" name="action" value="delete">
                                                                <input type="hidden" name="forumTopicNo" value="${forumTopicVO.forumTopicNo}">    
                                                                <input type="hidden" name="banjiNo" value="${banjiVO.banjiNo}">                                                            
                                                                <button type="submit" class="btn btn-sm btn-danger">
                                                                    <span class="fal fa-edit mr-1"></span>
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
                <div class="page-content-overlay" data-action="toggle"data-class="mobile-nav-on"></div>
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
        });
    </script>
</body>

</html>