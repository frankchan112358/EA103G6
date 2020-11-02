<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="panel-10" class="panel">
	<ul class="nav nav-pills nav-justified" role="tablist">
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="coursePost" href="javascript:void(0)" style="font-size: 15px">課程公告</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="teachingPlan" href="javascript:void(0)" style="font-size: 15px">教學計劃</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="courseVideo" href="javascript:void(0)" style="font-size: 15px">課程影片</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="teachingFile" href="javascript:void(0)" style="font-size: 15px">課程教材</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="courseAsk" href="javascript:void(0)" style="font-size: 15px">問題討論</a>
		</li>
		<li class="nav-item">
			<a class="courseWork nav-link" courseWork="evaluation" href="javascript:void(0)" style="font-size: 15px">教學評鑑</a>
		</li>

	</ul>
	<div class="tab-content"></div>
</div>

<script>
	function courseNav() {
		let _courseWork = '${courseWork}';
		$(`[courseWork='${'${_courseWork}'}']`).addClass('active');
		if (_courseWork == '') {
			$(`[courseWork=coursePost]`).addClass('active');
		}
		$(document).on('click', 'a.courseWork', function (e) {
			let courseWork = this.getAttribute('courseWork');
			if (courseWork == '' || courseWork == 'coursePost') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/coursePost/coursePost.do';
				myForm.append(courseNavInput('hidden','action','listCoursePost_ByCourseNo'));
				myForm.method = 'POST';
				myForm.submit();
			}
			if (courseWork == 'teachingPlan') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/teachingPlan/teachingPlan.do';
				myForm.append(courseNavInput('hidden','action','listTeachingPlan_ByCourseNo'));
				myForm.method = 'POST';
				myForm.submit();
			}
			if (courseWork == 'courseVideo') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '<%=request.getContextPath()%>/front-end/video/video.jsp';
				myForm.method = 'POST';
				myForm.submit();
			}
			if (courseWork == 'teachingFile') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '#';
				myForm.method = 'POST';
				myForm.submit();
			}
			if (courseWork == 'courseAsk') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '#';
				myForm.method = 'POST';
				myForm.submit();
			}
			if (courseWork == 'evaluation') {
				e.preventDefault();
				let myForm = document.createElement('form');
				document.body.appendChild(myForm);
				myForm.action = '#';
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

