<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.coursepost.model.*"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<%
	CoursePostVO coursePostVO = (CoursePostVO) request.getAttribute("coursePostVO");
%>


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
							<a id="aListAllCourse" banjiNo="${courseSvc.getOneCourse(courseNo).banjiNo}" href="javascript:void(0)">課程總覽</a>
						</li>
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/back-end/coursepost/listAllCoursePost.jsp">課程公告管理</a>
						</li>
						<li class="breadcrumb-item">課程公告新增</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon far fa-plus-circle mr-1'></i>
							課程公告新增
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-10">
							<div id="panel-2" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>課程公告新增</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">

										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/coursePost/coursePost.do" name="form1" class="needs-validation" novalidate>

											<div class="form-group">
												<label class="form-label">公告標題 <span class="text-danger">*</span></label>
												<input type="text" name="title" class="form-control" value="${coursePostVO.title}" required placeholder="公告標題"/>
												<font color=red>${errorMsgs.title}</font>
												<div class="invalid-feedback">請填寫公告標題.</div>
											</div>


											<div class="form-group">
												<label class="form-label">公告內容</label>
												<textarea class="form-control" name="postContent" rows="5" placeholder="公告內容">${coursePostVO.postContent}</textarea>
											</div>


											<div class="form-row align-items-center justify-content-center">
												<input type="hidden" name="action" value="insert">
												<button type="submit" class="btn btn-primary justify-content-center">送出</button>
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



	<div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>


	<script>
    
        $(function(){
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


	</script>

</body>
</html>