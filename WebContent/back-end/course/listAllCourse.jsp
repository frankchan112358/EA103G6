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

<%
	CourseVO courseVO = (CourseVO) request.getAttribute("courseVO");
%>


<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService" />
<jsp:useBean id="classroomSvc" scope="page" class="com.classroom.model.ClassroomService" />

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/back-end/template/head.jsp" %> 
    <link rel="stylesheet" media="screen, print" href="<%=request.getContextPath() %>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">

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
							<i class='subheader-icon far fa-book'></i>
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

										<!-- datatable start -->
                                            <table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead>
												<tr>
													<th>課程編號</th>
													<th>課程名稱</th>
													<th width="25%">課程大綱</th>
													<th>班級</th>
													<th>講師</th>
													<th>教室</th>
													<th>堂數</th>
													<th>開始日期</th>
													<th>結束日期</th>
													<th>狀態</th>
													<th>修改</th>
													<th>刪除</th>
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
<%-- 															<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do"> --%>
															   	<button type="submit" class="btn btn-success" data-toggle="modal" data-target="#default-example-modal-lg">Revise</button>
<!-- 																<button type="submit" class="btn btn-success">Revise</button> -->
																<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																<input type="hidden" name="action" value="getOne_For_Update">
<!-- 															</FORM> -->
														</td>
														<td>
															<FORM id="delete" METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do">
																<button type="submit" class="btn btn-danger">Delete</button>
																<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
																<input type="hidden" name="action" value="delete">
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
												<div class="modal fade" id="default-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                                                <div class="modal-dialog modal-lg" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">Modal title</h5>
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true"><i class="fal fa-times"></i></span>
                                                            </button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do" name="form1">

											<div class="form-group">
												<label class="form-label">課程編號</label>
												<input type="text" name="courseNo" class="form-control" value="${courseVO.courseNo}" disabled />
											</div>

											<div class="form-group">
												<label class="form-label">基本課程編號</label>
												<input type="text" name="basicCourseNo" class="form-control" value="${courseVO.basicCourseNo}" />
												<font color=red>${errorMsgs.basicCourseNo}</font>
											</div>

											<div class="form-group">
												<label class="form-label">課程名稱</label>
												<input type="text" name="courseName" class="form-control" value="${courseVO.courseName}" />
												<font color=red>${errorMsgs.courseName}</font>
											</div>


											<div class="form-group">
												<label class="form-label">課程大綱</label>
												<textarea class="form-control" name="courseOutline" rows="5">${courseVO.courseOutline}</textarea>
											</div>


											<div class="form-group">
												<label class="form-label">班級</label>
												<select class="custom-select form-control" name="banjiNo">
													<option value="">請選擇班級</option>
													<c:forEach var="banjiVO" items="${banjiSvc.all}">
														<option value="${courseVO.banjiNo}" ${(courseVO.banjiNo==banjiVO.banjiNo)?'selected':'' }>${banjiVO.banjiName}</option>
													</c:forEach>
												</select>
												<font color=red>${errorMsgs.banjiNo}</font>
											</div>


											<div class="form-group">
												<label class="form-label">講師</label>
												<select class="custom-select form-control" name="teacherNo">
													<option value="">請選擇講師</option>
													<c:forEach var="teacherVO" items="${teacherSvc.all}">
														<option value="${courseVO.teacherNo}" ${(courseVO.teacherNo==teacherVO.teacherNo)?'selected':'' }>${teacherVO.teacherName}</option>
													</c:forEach>
												</select>
												<font color=red>${errorMsgs.teacherNo}</font>
											</div>


											<div class="form-group">
												<label class="form-label">教室</label>
												<select class="custom-select form-control" name="classroomNo">
													<option value="">請選擇教室</option>
													<c:forEach var="classroomVO" items="${classroomSvc.all}">
														<option value="${courseVO.classroomNo}" ${(courseVO.classroomNo==classroomVO.classroomNo)?'selected':'' }>${classroomVO.classroomName}</option>
													</c:forEach>
												</select>
												<font color=red>${errorMsgs.classroomNo}</font>
											</div>


											<div class="form-group">
												<label class="form-label">堂數</label>
												<input class="form-control" type="number" name="lesson" min="0" value="${courseVO.lesson}">
												<font color=red>${errorMsgs.lesson}</font>
											</div>


											<div class="form-group">
												<label class="form-label">開始日期</label>
												<input class="form-control" id="f_date1" type="text" name="startDate" onfocus="this.blur()">
												<font color=red>${errorMsgs.startDate}</font>
											</div>

											<div class="form-group">
												<label class="form-label">結束日期</label>
												<input class="form-control" id="f_date2" type="text" name="endDate" onfocus="this.blur()">
												<font color=red>${errorMsgs.endDate}</font>
											</div>


											<div class="form-group">
												<label class="form-label">狀態</label>
												<select class="custom-select form-control" name="status">
													<option value="0" ${(courseVO.status==0)?'selected':'' }>課程未開始</option>
													<option value="1" ${(courseVO.status==1)?'selected':'' }>課程進行中</option>
													<option value="2" ${(courseVO.status==2)?'selected':'' }>課程結束</option>
												</select>
											</div>

										</FORM>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal" id="cancel">取消</button>
                                                            <button type="button" class="btn btn-primary" id="save">儲存</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>



		
              <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
                <%@ include file="/back-end/template/footer.jsp" %>
            </div>
        </div>
    </div>
    
    
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %>
    
    
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/vendors.bundle.js"></script>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/app.bundle.js"></script>
        <script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
    
    <script>
    
     
            $(document).ready(function(){
                $('#coursetable').dataTable({
                 	responsive: true,
                    language:{url:'<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json'},

                });
            });
            
            
     </script>
</body>
</html>