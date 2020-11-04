<%@page import="com.course.model.CourseVO"%>
<%@page import="com.course.model.CourseService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String courseNav_courseNo = (String)session.getAttribute("courseNo");
CourseVO courseNav_courseVO = new CourseService().getOneCourse(courseNav_courseNo);
pageContext.setAttribute("courseNav_courseVO", courseNav_courseVO);
%>
<div class="bg-info-400 card p-2 pl-3 pr-3 mb-4" style="display: inline-block;">
	<img src="<%=request.getContextPath() %>/course/course.do?action=getCourseImg&courseNo=${courseNav_courseVO.courseNo}" class="rounded" style="width: 4.125rem;height: 4.125rem;">
	<div class="info-card-text ml-3" style="vertical-align: top;">
		<h5 class="card-title mb-1">
			<label class="fs-xxl">${courseNav_courseVO.courseName}</label>
			<label class="fs-xxl"> </label>
			<i class="fal fa-hand-point-right"></i>
			<label>【${courseNav_courseVO.banjiVO.banjiName}】</label>
		</h5>
		<span class="float-lg-right fs-xl"> <i class="fal fa-smile"></i>
			${courseNav_courseVO.teacherVO.teacherName}</span>
	</div>
</div>
<div id="panel-10" class="panel">
	<ul class="nav nav-pills nav-justified" role="tablist">
		<li class="nav-item"><a class="courseWork nav-link" courseWork="courseInfo" href="javascript:void(0)" style="font-size: 15px">課程資料管理</a></li>
		<li class="nav-item"><a class="courseWork nav-link" courseWork="coursePost" href="javascript:void(0)" style="font-size: 15px">課程公告管理</a></li>
		<li class="nav-item"><a class="courseWork nav-link" courseWork="teachingPlan" href="javascript:void(0)" style="font-size: 15px">教學計劃管理</a></li>
		<li style="display: none;" class="nav-item"><a class="courseWork nav-link" courseWork="courseVideo" href="javascript:void(0)" style="font-size: 15px">課程影片管理</a></li>
		<li class="nav-item"><a class="courseWork nav-link" courseWork="courseVideoAjax" href="javascript:void(0)" style="font-size: 15px">課程影片管理</a></li>
		<li class="nav-item"><a class="courseWork nav-link" courseWork="teachingNote" href="javascript:void(0)" style="font-size: 15px">教學筆記管理</a></li>
		<li class="nav-item"><a class="courseWork nav-link" courseWork="teachingFileAjax" href="javascript:void(0)" style="font-size: 15px">課程教材管理</a></li>
		<li class="nav-item"><a class="courseWork nav-link" courseWork="finalScore" href="javascript:void(0)" style="font-size: 15px">成績管理</a></li>
		<li class="nav-item"><a class="courseWork nav-link" courseWork="courseAsk" href="javascript:void(0)" style="font-size: 15px">提問管理</a></li>
	</ul>
	<div class="tab-content"></div>
</div>

<script>
	function courseNav() {
		let _courseWork = '${courseWork}';
		$(`[courseWork='${'${_courseWork}'}']`).addClass('active');
		if (_courseWork == '') {
			$(`[courseWork=courseInfo]`).addClass('active');
		}
		$(document).on('click', 'a.courseWork', function (e) {
			let courseWork = this.getAttribute('courseWork');
			if (courseWork == '' || courseWork == 'courseInfo') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/course.do';
				myForm.method = 'POST';
				myForm.append(courseNavInput('hidden', 'action', 'getOne_For_Display'));
				myForm.append(courseNavInput('hidden', 'courseNo', '${courseNo}'));
				myForm.submit();
			}
			if (courseWork == 'coursePost') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/coursePost/coursePost.do';
				myForm.method = 'POST';
				myForm.submit();
			}
			if (courseWork == 'teachingPlan') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/teachingPlan/teachingPlan.do';
				myForm.method = 'POST';
				myForm.submit();
			}
			if (courseWork == 'courseVideo') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/courseTT.do';
				myForm.method = 'POST';
				myForm.append(courseNavInput('hidden', 'action', 'getTTDisplayList'));
				myForm.submit();
			}

			if (courseWork == 'courseVideoAjax') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/courseTT.do';
				myForm.method = 'POST';
				myForm.append(courseNavInput('hidden', 'action', 'getTTDisplayListAjax'));
				myForm.submit();
			}

			if (courseWork == 'teachingNote') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/teachingNote/teachingNote';
				myForm.method = 'POST';
				myForm.append(courseNavInput('hidden', 'action', 'getTNDisplayList'));
				myForm.submit();
			}
			if (courseWork == 'teachingFile') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/courseTT.do';
				myForm.method = 'POST';
				myForm.append(courseNavInput('hidden', 'action', 'getTFDisplayList'));
				myForm.submit();
			}
			if (courseWork == 'teachingFileAjax') {
				
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/courseTT.do';
				myForm.method = 'POST';
				myForm.append(courseNavInput('hidden', 'action', 'getTFDisplayListAjax'));
				myForm.submit();
			}
			if (courseWork == 'finalScore') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/courseTT.do';
				myForm.method = 'POST';
				myForm.append(courseNavInput('hidden', 'action', 'getFSDisplayList'));
				myForm.submit();
			}
			if (courseWork == 'courseAsk') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/courseAsk/courseAsk.do';
				myForm.append(courseNavInput('hidden','action','listCourseAskWithCourseNo1'));
				myForm.method = 'POST';
				myForm.submit();
				}
		});
	}

	function courseNavInput(type, name, value) {
		let courseInput = document.createElement('input');
		courseInput.type = type;
		courseInput.name = name;
		courseInput.value = value;
		return courseInput;
	}

	//這個寫法不安全，應該要把courseNav();，放到有用這個courseNav.jsp的jsp的document.ready裡面
	window.onload = function () {
		courseNav();
	};

</script>