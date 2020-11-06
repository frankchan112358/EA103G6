<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.course.model.*"%>

<%
	CourseVO courseVO = (CourseVO) request.getAttribute("courseVO");
%>

<jsp:useBean id="banjiSvc" scope="page" class="com.banji.model.BanjiService" />
<jsp:useBean id="teacherSvc" scope="page" class="com.teacher.model.TeacherService" />
<jsp:useBean id="classroomSvc" scope="page" class="com.classroom.model.ClassroomService" />

<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />

<style>

.form-group{
font-size: 15px;
}

.form-control{
font-size: 15px;
}

.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}

#showphoto2 {
	text-align: center;
}

#showphoto2 img {
	width: 300px;
	margin: 20px;
	border: 2px #C4B1B1 dashed;
	text-align: center;
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
							<a id="aListAllCourse" banjiNo="${courseVO.banjiNo}" href="javascript:void(0)">課程總覽</a>
						</li>
						<li class="breadcrumb-item">
							<a id="aListOneCourse" courseNo="${courseNo}" href="javascript:void(0)">課程資料管理</a>
						</li>
						<li class="breadcrumb-item">課程資料修改</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-comment-alt-edit mr-1'></i>
							課程資料修改
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-10">
							<div id="panel-2" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>課程修改</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">

										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do" enctype="multipart/form-data" name="form1" class="was-validated">


											<input type="hidden" name="basicCourseNo" class="form-control" value="BC001" />

											<div class="form-group">
												<label class="form-label">課程名稱<span class="text-danger">*</span></label>
												<input type="text" name="courseName" class="form-control" value="${courseVO.courseName}" required placeholder="課程名稱"/>
												<font color=red>${errorMsgs.courseName}</font>
												<div class="invalid-feedback">請填寫課程名稱.</div>
											</div>


											<div class="form-group">
												<label class="form-label">課程大綱</label>
												<textarea class="form-control" name="courseOutline" rows="5" placeholder="課程大綱">${courseVO.courseOutline}</textarea>
											</div>

											<input type="hidden" name="banjiNo" value="${courseVO.banjiNo}">

											<div class="form-group">
												<label class="form-label">講師<span class="text-danger">*</span></label>
												<select class="custom-select form-control" name="teacherNo" required>
													<option value="">請選擇講師</option>
													<c:forEach var="teacherVO" items="${teacherSvc.all}">
														<option value="${teacherVO.teacherNo}" ${(courseVO.teacherNo==teacherVO.teacherNo)?'selected':'' }>${teacherVO.teacherName}</option>
													</c:forEach>
												</select>
												<font color=red>${errorMsgs.teacherNo}</font>
												<div class="invalid-feedback">請選擇講師.</div>
											</div>


											<div class="form-group">
												<label class="form-label">教室<span class="text-danger">*</span></label>
												<select class="custom-select form-control" name="classroomNo" required>
													<option value="">請選擇教室</option>
													<c:forEach var="classroomVO" items="${classroomSvc.all}">
														<option value="${classroomVO.classroomNo}" ${(courseVO.classroomNo==classroomVO.classroomNo)?'selected':'' }>${classroomVO.classroomName}</option>
													</c:forEach>
												</select>
												<font color=red>${errorMsgs.classroomNo}</font>
												<div class="invalid-feedback">請選擇教室.</div>
											</div>


											<div class="form-group">
												<label class="form-label">堂數<span class="text-danger">*</span></label>
												<input class="form-control" type="number" name="lesson" min="0" value="${courseVO.lesson}" required>
												<font color=red>${errorMsgs.lesson}</font>
												<div class="invalid-feedback">請填寫堂數.</div>
											</div>


											<div class="form-group">
												<label class="form-label">開始日期<span class="text-danger">*</span></label>
												<input class="form-control" id="f_date1" type="text" name="startDate" onfocus="this.blur()" required>
												<font color=red>${errorMsgs.startDate}</font>
												<div class="invalid-feedback">請選擇開始日期.</div>

											</div>

											<div class="form-group">
												<label class="form-label">結束日期<span class="text-danger">*</span></label>
												<input class="form-control" id="f_date2" type="text" name="endDate" onfocus="this.blur()" required>
												<font color=red>${errorMsgs.endDate}</font>
												<div class="invalid-feedback">請選擇結束日期.</div>
											</div>


											<div class="form-group">
												<label class="form-label">狀態<span class="text-danger">*</span></label>
												<select class="custom-select form-control" name="status">
													<option value="0" ${(courseVO.status==0)?'selected':'' }>課程未開始</option>
													<option value="1" ${(courseVO.status==1)?'selected':'' }>課程進行中</option>
													<option value="2" ${(courseVO.status==2)?'selected':'' }>課程結束</option>
												</select>
											</div>


											<div class="form-group mb-3">
												<label class="form-label">上傳課程圖片</label>
												<div class="custom-file">
													<input type="file" class="custom-file-input" name="courseImg" accept="image/*" onchange="show(courseImg)">
													<label class="custom-file-label" for="customFile">Choose Image</label>
													<div id="showphoto2">
														<c:if test="${courseVO.courseImg eq null}">
															<img src="<%=request.getContextPath()%>/images/default.png">
														</c:if>
														<c:if test="${courseVO.courseImg ne null}">
															<img src="<%=request.getContextPath() %>/course/course.do?action=getCourseImg&courseNo=${courseVO.courseNo}">
														</c:if>
													</div>
												</div>
											</div>

											<div class="form-row align-items-center justify-content-center">
												<button type="submit" class="btn btn-primary justify-content-center">
													<span>送出</span>
												</button>
												<input type="hidden" name="action" value="update">
												<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
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

	<%@ include file="/back-end/template/footer.jsp"%>

	<%@ include file="/back-end/template/quick_menu.jsp"%>
	<%@ include file="/back-end/template/messager.jsp"%>
	<%@ include file="/back-end/template/basic_js.jsp"%>


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
        	
    		document.getElementById('aListAllCourse').addEventListener('click',function(e){
				e.preventDefault();
				let _this = this;
                let banjiNo = this.getAttribute('banjiNo');
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/course.do';
				myForm.method = 'POST';
				let banjiNoInput = document.createElement('input');
				banjiNoInput.type = 'hidden';
				banjiNoInput.name = 'banjiNo';
				banjiNoInput.value= banjiNo;
				myForm.append(banjiNoInput);
				myForm.submit();
			}); 

    		document.getElementById('aListOneCourse').addEventListener('click',function(e){
				e.preventDefault();
				let _this = this;
                let courseNo = this.getAttribute('courseNo');
                
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/course.do';
				myForm.method = 'POST';
				
				let actionInput = document.createElement('input');
				actionInput.type = 'hidden';
				actionInput.name = 'action';
				actionInput.value= 'getOne_For_Display';
				myForm.append(actionInput);
				
				let courseNoInput = document.createElement('input');
				courseNoInput.type = 'hidden';
				courseNoInput.name = 'courseNo';
				courseNoInput.value= courseNo;
				myForm.append(courseNoInput);
				
				myForm.submit();
			}); 
    		
			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document
					.getElementsByClassName('needs-validation');
			// Loop over them and prevent submission
			var validation = Array.prototype.filter.call(forms,
					function(form) {
						form.addEventListener('submit', function(
								event) {
							if (form.checkValidity() === false) {
								event.preventDefault();
								event.stopPropagation();
							}
							form.classList.add('was-validated');
						}, false);
					});
    		
		});

		var courseImg = $("#courseImg");
		function show(courseImg) {
			let img = URL.createObjectURL(courseImg.files[0]);
			document.getElementById("showphoto2").innerHTML = "<img src ="
					+ img + " width='100%' height='100%'>";
		}
	</script>

</body>
</html>