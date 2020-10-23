<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banjitype.model.*"%>

<%
	BanjiTypeVO banjiTypeVO = (BanjiTypeVO) request.getAttribute("banjiTypeVO");
%>

<jsp:useBean id="banjiTypeSvc" scope="page"
				class="com.banjitype.model.BanjiTypeService" />

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
<style>

#add{
text-align:center ;
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
                        <li class="breadcrumb-item"><a href="javascript:void(0);">後台首頁</a></li>
                        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/back-end/banji/homeBanji.jsp">班種管理</a></li>
                          <li class="breadcrumb-item">修改資料</li>
                    </ol>
                    <div class="subheader" >
                        <h1 class="subheader-title">
                            <i class='subheader-icon fal fa-users-class' ></i> 修改資料
                        </h1>
                    </div>
					 <div class="panel-container show" >
                           <div class="panel-content">
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banjiType/banjiType.do" name="form1" >
		
										<div class="form-group">
                                               <label class="form-label" for="simpleinput">班種編號:</label>
                                                 <input type="text" id="simpleinput" class="form-control" readonly name="banjiTypeNo" value="${banjiTypeVO.banjiTypeNo}">
                                            </div>
								
                                             <div class="form-group">
                                               <label class="form-label" for="simpleinput">班種名稱:</label>
                                                 <input type="text" id="simpleinput" class="form-control"  name="banjiTypeName" value="${banjiTypeVO.banjiTypeName}">
                                            </div>   
                                            
                                             <div class="form-group">
                                               <label class="form-label" for="simpleinput">上課時數:</label>
                                                 <input type="text" id="simpleinput" class="form-control"  name="classHours" value="${banjiTypeVO.classHours}">
                                            </div>   
                                      
                                      		<div class="form-group">
                                                    <label class="form-label" for="example-textarea">班種內容:</label>
                                                    <textarea name="banjiTypeContent" class="form-control" id="example-textarea" rows="5" ><%=(banjiTypeVO == null) ? "" : banjiTypeVO.getBanjiTypeContent()%></textarea>
                                            </div>
                                      
		 								 <div class="form-group" >
                                                    <label class="form-label" for="example-select">狀態:</label>
                                               <select class="custom-select form-control" name="banjiTypeEnable" >
                                               <c:if test="${ banjiTypeVO.banjiTypeEnable==0}" >
													<option  value="1" ${(banjiTypeVO.banjiTypeEnable==1)?'selected':'' }>上架</option></c:if>
												
													<option value="0" ${(banjiVO.banjiTypeEnable==0)?'selected':'' }>下架</option>
												</select>           
                                                </div>
                                                
                                           <div class="form-group" id="add" >
												<input type="hidden" name="action" value="update"> 
												<input type="hidden" name="banjiTypeNo" value="<%=banjiTypeVO.getBanjiTypeNo()%>">
												<button type="submit" class="btn btn-primary justify-content-center" >送出修改</button>
											</div>     
							</FORM>
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
</body>
</html>