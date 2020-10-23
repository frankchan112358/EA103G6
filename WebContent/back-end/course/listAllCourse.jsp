<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ include file="/back-end/template/check.jsp"%> --%>
<%@ page import="com.course.model.*"%>
<%@ page import="java.util.*"%>
<%
	CourseService courseSvc = new CourseService();
	List<CourseVO> list = courseSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService" />
<jsp:useBean id="classroomSvc" scope="page" class="com.classroom.model.ClassroomService" />

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">


<style>

.table th, .table td {
    vertical-align: middle;
    text-align: center;  
}


</style>

</head>

<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
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
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/back-end/index/index.jsp">後台首頁</a>
						</li>
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/back-end/course/listAllCourse.jsp">課程總覽</a>
						</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-book'></i>
							課程總覽
						</h1>
					</div>
					<div class="row">
						<div class="col-xl-12">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>課程列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
									<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do">
                                        <button id="addCourse" type="submit" class="btn btn-primary waves-effect waves-themed float-left">
                                       <span class="far fa-plus-circle mr-1"></span>
                                        <span>新增</span>                 
                                        </button>
                                        <input type="hidden" name="courseNo" value="${courseVO.courseNo}">
										<input type="hidden" name="action" value="insert">
										</FORM>
										<!-- datatable start -->
                                            <table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color:#E5F4FF;">
												<tr>
													<th width="6%">課程編號</th>
													<th width="6%">課程名稱</th>
													<th width="20%">課程大綱</th>
													<th width="6%">班級</th>
													<th width="7%">講師</th>
													<th width="6%">教室</th>
													<th width="6%">堂數</th>
													<th width="6%">開始日期</th>
													<th width="6%">結束日期</th>
													<th width="7%">狀態</th>
													<th width="7%">修改</th>
													<th width="7%">刪除</th>
													<th width="5%">影片管理</th>
													<th width="5%">教材管理</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="courseVO" items="${list}">
													<tr>
														<td>${courseVO.courseNo}</td>
														<td>${courseVO.courseName}</td>
														<td>${courseVO.courseOutline}</td>
														<td>${banjiSvc.getOneBanji(courseVO.banjiNo).banjiName}</td>
														<td>${teacherSvc.getOneTeacher(courseVO.teacherNo).teacherName}</td>
														<td>${classroomSvc.getOneClassroom(courseVO.classroomNo).classroomName}</td>
														<td>${courseVO.lesson}</td>
														<td>${courseVO.startDate}</td>
														<td>${courseVO.endDate}</td>
														<td>
															<c:choose>
																<c:when test="${courseVO.status=='0'}">課程未開始</c:when>
																<c:when test="${courseVO.status=='1'}">課程進行中</c:when>
																<c:when test="${courseVO.status=='2'}">課程結束</c:when>
															</c:choose>
														</td>
														<td>
															<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do">
																<button type="submit" class="btn btn-sm btn-outline-primary">
																<span class="fal fa-edit mr-1"></span>	<br>
																<span>修改</span>
                                                                 </button>
																<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																<input type="hidden" name="action" value="getOne_For_Update">
                                                                    
															</FORM>
														</td>
														<td>
															<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do">
																<button type="submit" class="btn btn-sm btn-outline-danger">
																<i class="fal fa-times mr-1"></i><br>
																<span>刪除</span>
																</button>
																
																<input type="hidden" name="courseNo" value="<%=request.getContextPath()%>/course/course.do">
																<input type="hidden" name="action" value="delete">
															</FORM>
														</td>
														<td>
															<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/courseTT.do">
																<button type="submit"  class="btn btn-outline-success btn-icon waves-effect waves-themed">
																<i class="fal fa-bug"></i>	<br>
																<span>管理</span>
																</button>
																
																<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																<input type="hidden" name="action" value="getTTDisplayList">
															</FORM>
														</td>
														<td>
															<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/courseTT.do">
																<button type="submit"  class="btn btn-outline-success btn-icon waves-effect waves-themed">
																<span class="fal fa-bug"></span>	<br>
																<span>管理</span>
																</button>
																
																<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																<input type="hidden" name="action" value="getTFDisplayList">
															</FORM>
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
    
    
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
     <script>
    
     
            $(document).ready(function()
            {
                $('#coursetable').dataTable(
                {
                    responsive: true,
                    language:{url:'<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json'},
					"columnDefs":[{
						"targets":[ -1, -2 ],
						"orderable":false,
					
				}]
                });
           
            });
     </script>
</body>
</html>
