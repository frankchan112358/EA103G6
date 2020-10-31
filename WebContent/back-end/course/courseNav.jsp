<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="panel-10" class="panel">
	<ul class="nav nav-pills nav-justified" role="tablist">
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="courseInfo" href="javascript:void(0)" style="font-size: 15px">課程資料管理</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="coursePost" href="javascript:void(0)" style="font-size: 15px">課程公告管理</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="teachingPlan" href="javascript:void(0)" style="font-size: 15px">教學計劃管理</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="courseVideo" href="javascript:void(0)" style="font-size: 15px">課程影片管理</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="courseVideoAjax" href="javascript:void(0)" style="font-size: 15px">課程影片管理(Ajax)</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="teachingFile" href="javascript:void(0)" style="font-size: 15px">課程教材管理</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="finalScore" href="javascript:void(0)" style="font-size: 15px">成績管理</a>
		</li>
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
				myForm.append(courseNavInput('hidden','action','getOne_For_Display'));
				myForm.append(courseNavInput('hidden','courseNo','${courseNo}'));
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
				myForm.append(courseNavInput('hidden','action','getTTDisplayList'));
				myForm.submit();
			}
			if (courseWork == 'courseVideoAjax') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/courseTT.do';
				myForm.method = 'POST';
				myForm.append(courseNavInput('hidden','action','getTTDisplayListAjax'));
				myForm.submit();
			}
			if (courseWork == 'teachingFile') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/courseTT.do';
				myForm.method = 'POST';
				myForm.append(courseNavInput('hidden','action','getTFDisplayList'));
				myForm.submit();
			}
			if (courseWork == 'finalScore') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/course/courseTT.do';
				myForm.method = 'POST';
				myForm.append(courseNavInput('hidden','action','getFSDisplayList'));
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