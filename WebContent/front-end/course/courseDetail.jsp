<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="panel-10" class="panel">
	<ul class="nav nav-pills nav-justified" role="tablist">
		<li class="nav-item">
			<a class="nav-link active"data-toggle="" href="<%=request.getContextPath()%>/front-end/course/coursePost.jsp" style="font-size: 15px">課程公告</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" data-toggle="" href="<%=request.getContextPath()%>/teachingPlan/teachingPlan.do?action=listTeachingPlan_ByCourseNo" style="font-size: 15px">教學計劃</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" data-toggle="" href="<%=request.getContextPath()%>/front-end/video/video.jsp" style="font-size: 15px">課程影片</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" data-toggle="" href="#js_change_pill_justified-5" style="font-size: 15px">課程教材</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" data-toggle="" href="#js_change_pill_justified-4" style="font-size: 15px">問題討論</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" data-toggle="" href="#js_change_pill_justified-5" style="font-size: 15px">教學評鑑</a>
		</li>

	</ul>
	<div class="tab-content"></div>
</div>


