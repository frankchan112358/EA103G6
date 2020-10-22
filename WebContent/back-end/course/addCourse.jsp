<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.course.model.*"%>xw

<%
	CourseVO courseVO = (CourseVO) request.getAttribute("courseVO");
%>

<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService"/>
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService"/>
<jsp:useBean id="classroomSvc" scope="page" class="com.classroom.model.ClassroomService"/>

<!DOCTYPE html>
<html>
<head>
 <%@ include file="/back-end/template/head.jsp" %> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />


<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}

 #showphoto2{ 
  text-align:center;  
  }  

  #showphoto2 img {  
   	width: 320px;  
    margin: 40px;   
   	border:2px #C4B1B1 dashed;  
    text-align:center;  
  }  
  img {  
    vertical-align: sub;  
    width: 100%;  
  	height: auto;  
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
						<li class="breadcrumb-item">課程資料新增</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon far fa-plus-circle mr-1'></i>
							課程資料新增
						</h1>
					</div>
					<div class="row justify-content-center">
						<div class="col-xl-8">
							<div id="panel-2" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>課程新增</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">

										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do" enctype="multipart/form-data" name="form1">

											<div class="form-group">
												<label class="form-label">基本課程編號</label>
												<input id="basicCourseNo" type="text" name="basicCourseNo" class="form-control" value="${courseVO.basicCourseNo}" />
												<font color=red>${errorMsgs.basicCourseNo}</font>
											</div>


												<div class="form-group">
												<label class="form-label">課程名稱</label>
												<input id="courseName" type="text" name="courseName" class="form-control" value="${courseVO.courseName}" />
												<font color=red>${errorMsgs.courseName}</font>
											</div>


											<div class="form-group">
												<label class="form-label">課程大綱</label>
												<textarea id="courseOutline" class="form-control" name="courseOutline" rows="5">${courseVO.courseOutline}</textarea>
											</div>


											<div class="form-group">
												<label class="form-label">班級</label>
												<select class="custom-select form-control" name="banjiNo">
													<option value="">請選擇班級</option>
													<c:forEach var="banjiVO" items="${banjiSvc.all}">
														<option value="${banjiVO.banjiNo}" ${(courseVO.banjiNo==banjiVO.banjiNo)?'selected':'' }>${banjiVO.banjiName}</option>
													</c:forEach>
												</select>
												<font color=red>${errorMsgs.banjiNo}</font>
											</div>


											<div class="form-group">
												<label class="form-label">講師</label>
												<select class="custom-select form-control" name="teacherNo">
													<option value="">請選擇講師</option>
													<c:forEach var="teacherVO" items="${teacherSvc.all}">
														<option value="${teacherVO.teacherNo}" ${(courseVO.teacherNo==teacherVO.teacherNo)?'selected':'' }>${teacherVO.teacherName}</option>
													</c:forEach>
												</select>
												<font color=red>${errorMsgs.teacherNo}</font>
											</div>


											<div class="form-group">
												<label class="form-label">教室</label>
												<select class="custom-select form-control" name="classroomNo">
													<option value="">請選擇教室</option>
													<c:forEach var="classroomVO" items="${classroomSvc.all}">
														<option value="${classroomVO.classroomNo}" ${(courseVO.classroomNo==classroomVO.classroomNo)?'selected':'' }>${classroomVO.classroomName}</option>
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
												                                                   
												<div class="form-group mb-3">
												<label class="form-label">上傳課程圖片</label>
												 <div class="custom-file">
												 <input type="file" class="custom-file-input"  name="courseImg" accept="image/*" onchange="show(courseImg)">
												 <label class="custom-file-label" for="customFile">Choose Image</label>
												    <div id="showphoto2">
                                                        <c:if test="${courseVO.courseImg eq null}">
															<img src="<%=request.getContextPath() %>/images/尚無圖片.jpg">
														</c:if>
														<c:if test="${courseVO.courseImg ne null}">
															<img src="<%=request.getContextPath() %>/course/course.do?action=getCourseImg&courseNo=${courseVO.courseNo}">
														</c:if>
                                                  	</div>	
							                      </div>
							                      </div>
							
											<div class="form-row align-items-center justify-content-center">
												<input type="hidden" name="action" value="insert">
												<button type="submit" class="btn btn-primary justify-content-center" id="addCourse" >送出</button>
											</div>
										</FORM>
									</div>
								</div>
							</div>
							</div>
							</div>
							</main>
						</div>
					</div>	
			</div>

     <%@ include file="/back-end/template/footer.jsp" %>
     
    <%@ include file="/back-end/template/quick_menu.jsp" %>
    <%@ include file="/back-end/template/messager.jsp" %>
    <%@ include file="/back-end/template/basic_js.jsp" %> 


<% 
 	java.sql.Date startDate = null;
  try {
	  startDate = courseVO.getStartDate();
   } catch (Exception e) {
	   startDate = new java.sql.Date(System.currentTimeMillis());
   }
%>

<% 
 	java.sql.Date endDate = null;
  try {
	  endDate = courseVO.getEndDate();
   } catch (Exception e) {
	   endDate = new java.sql.Date(System.currentTimeMillis());
   }
%>


 <div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
     
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<script>
    
        $(function(){
        	$.datetimepicker.setLocale('zh');
        	$('#f_date1').datetimepicker({
        	  format:'Y-m-d',
        	  timepicker:false,
        	  step: 1,
        	  scrollInput : false,
        	  value: '<%=startDate%>',
        	  onShow:function(){
        	   this.setOptions({
        	    minDate: new Date()
        	   })
        	  },
        	  timepicker:false
        	 });
        	
        	$('#f_date2').datetimepicker({
        	  format:'Y-m-d',
        	  timepicker:false,
        	  step: 1,
        	  scrollInput : false,
        	  value:'<%=endDate%>',
			onShow : function() {
				this.setOptions({
					minDate : $('#f_date1').val()
				})
			},
			timepicker : false
		});
	});
        
		var courseImg = $("#courseImg");
		function show(courseImg) {
			let img = URL.createObjectURL(courseImg.files[0]);
			document.getElementById("showphoto2").innerHTML = "<img src =" + img
					+ " width='100%' height='100%'>";
		}
        
	
</script>

</body>
</html>