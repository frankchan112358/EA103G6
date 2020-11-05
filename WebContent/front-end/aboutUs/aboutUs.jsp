<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/front-end/template/head.jsp"%>

<style>
}
</style>
</head>
<body class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
	<script>
		var classHolder = document.getElementsByTagName("BODY")[0];
	</script>
	<div class="page-wrapper">
		<div class="page-inner">
			<%@ include file="/front-end/template/left_aside.jsp"%>
			<div class="page-content-wrapper">
				<%@ include file="/front-end/template/header.jsp"%>
				<main id="js-page-content" role="main" class="page-content">
					<ol class="breadcrumb page-breadcrumb">
						<li class="breadcrumb-item">
							<a href="<%=request.getContextPath()%>/front-end/index/index.jsp">前台首頁</a>
						</li>
						<li class="breadcrumb-item">關於我們</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-info-circle'></i>
							關於Work Join Learn
							<small>提供未來的工程師們最即時、最完整且不間斷的學習平台</small>
						</h1>
					</div>
					<div class="row align-items-center justify-content-center">
						<div class="col-10" style="text-align: center">
							<div class="fs-lg fw-300 p-5 bg-white border-faded rounded mb-g">
								<h3 class="mb-g">Hi～ 未來的工程師們</h3>
								<h2 style="color: #9fcb3d;">
									<strong>想轉職工程師卻不知道從何開始？</strong>
								</h2>
								<br>
								<h4>Work Join Learn以培養一位專業工程師為目標，平台結合實體課程落實扎實學習</h4>
								<br>
								<h4>上完實體課程後學生可以在平台上複習上課影片，並提供學生管理上課相關的事務，成為學生轉職學習時的最佳後盾</h4>
								<br>
								<div class="row align-items-center justify-content-center">
									<ul style="list-style-type: none; text-align: left;">
										<li style="color: #2A86BD; font-size: 1.3em;">
											<i class="fal fa-check-circle mr-2"></i>
											<b>線下教育中心最貼心服務</b>
										</li>
										<li style="color: #2A86BD; font-size: 1.3em;">
											<i class="fal fa-check-circle mr-2"></i>
											<b>就業養成班-扎實學習</b>
										</li>
										<li style="color: #2A86BD; font-size: 1.3em;">
											<i class="fal fa-check-circle mr-2"></i>
											<b>上課出缺席控管</b>
										</li>
										<li style="color: #2A86BD; font-size: 1.3em;">
											<i class="fal fa-check-circle mr-2"></i>
											<b>班級討論區與同學切磋學習</b>
										</li>
										<li style="color: #2A86BD; font-size: 1.3em;">
											<i class="fal fa-check-circle mr-2"></i>
											<b>課程影片隨時觀看</b>
										</li>
										<li style="color: #2A86BD; font-size: 1.3em;">
											<i class="fal fa-check-circle mr-2"></i>
											<b>導師與講師線上即時回覆</b>
										</li>
									</ul>
								</div>
								<div style="margin: 50px;">
									<img style="width: 60%;" src="<%=request.getContextPath()%>/images/wjl.png" />
								</div>
								<h4 style="color: #9fcb3d;">
									Work Join Learn 團隊
									<br>
									祝福您未來成為一位優秀的工程師
									<br>
								</h4>
							</div>
							<h3  style="margin:50px;">
								<i class="fal fa-users fa-lg mr-1"></i>
								<strong style="font-size: 1.3em;"> Work Join Learn 團隊</strong>
								<i class="ml-1 fal fa-users fa-lg"></i>
							</h3>
							<div class="d-flex flex-wrap demo demo-h-spacing mt-3 mb-3 align-items-center justify-content-center">
								<div class="rounded-pill bg-white shadow-sm p-2 border-faded mr-3 d-flex flex-row align-items-center justify-content-center flex-shrink-0">
									<img src="<%=request.getContextPath()%>/images/test/詹詠琪.png" alt="詹詠琪" class="img-thumbnail img-responsive rounded-circle" style="width: 5rem; height: 5rem;">
									<div class="ml-2 mr-3">
										<h5 class="m-0">
											詹詠琪 (執行長)
											<small class="m-0 fw-300"> Chief Executive officer </small>
										</h5>
									</div>
								</div>
								<div class="rounded-pill bg-white shadow-sm p-2 border-faded mr-3 d-flex flex-row align-items-center justify-content-center flex-shrink-0">
									<img src="<%=request.getContextPath()%>/images/test/卓宜潔.jpg" alt="卓宜潔" class="img-thumbnail img-responsive rounded-circle" style="width: 5rem; height: 5rem;">
									<div class="ml-2 mr-3">
										<h5 class="m-0">
											卓宜潔 (營運長)
											<small class="m-0 fw-300"> Chief Operating Officer </small>
										</h5>
									</div>
								</div>
								<div class="rounded-pill bg-white shadow-sm p-2 border-faded mr-3 d-flex flex-row align-items-center justify-content-center flex-shrink-0">
									<img src="<%=request.getContextPath()%>/images/test/葉泓翔.png" alt="葉泓翔" class="img-thumbnail img-responsive rounded-circle" style="width: 5rem; height: 5rem;">
									<div class="ml-2 mr-3">
										<h5 class="m-0">
											葉泓翔 (工程師)
											<small class="m-0 fw-300"> Software Engineer </small>
										</h5>
									</div>
								</div>
								<div class="rounded-pill bg-white shadow-sm p-2 border-faded mr-3 d-flex flex-row align-items-center justify-content-center flex-shrink-0">
									<img src="<%=request.getContextPath()%>/images/test/陳浩偉.jpg" alt="陳浩偉" class="img-thumbnail img-responsive rounded-circle" style="width: 5rem; height: 5rem;">
									<div class="ml-2 mr-3">
										<h5 class="m-0">
											陳浩偉 (工程師)
											<small class="m-0 fw-300"> Software Engineer </small>
										</h5>
									</div>
								</div>
								<div class="rounded-pill bg-white shadow-sm p-2 border-faded mr-3 d-flex flex-row align-items-center justify-content-center flex-shrink-0">
									<img src="<%=request.getContextPath()%>/images/test/陳怡秀.jpg" alt="陳怡秀" class="img-thumbnail img-responsive rounded-circle" style="width: 5rem; height: 5rem;">
									<div class="ml-2 mr-3">
										<h5 class="m-0">
											陳怡秀 (工程師)
											<small class="m-0 fw-300"> Software Engineer </small>
										</h5>
									</div>
								</div>
								<div class="rounded-pill bg-white shadow-sm p-2 border-faded mr-3 d-flex flex-row align-items-center justify-content-center flex-shrink-0">
									<img src="<%=request.getContextPath()%>/images/test/何丞祐.jpg" alt="何丞祐" class="img-thumbnail img-responsive rounded-circle" style="width: 5rem; height: 5rem;">
									<div class="ml-2 mr-3">
										<h5 class="m-0">
											何丞祐 (工程師)
											<small class="m-0 fw-300"> Software Engineer </small>
										</h5>
									</div>
								</div>
								<div class="rounded-pill bg-white shadow-sm p-2 border-faded mr-3 d-flex flex-row align-items-center justify-content-center flex-shrink-0">
									<img src="<%=request.getContextPath()%>/images/test/王靖芳.png" alt="王靖芳" class="img-thumbnail img-responsive rounded-circle" style="width: 5rem; height: 5rem;">
									<div class="ml-2 mr-3">
										<h5 class="m-0">
											王靖芳 (工程師)
											<small class="m-0 fw-300"> Software Engineer </small>
										</h5>
									</div>
								</div>
							</div>
						</div>
					</div>
				</main>
			</div>
		</div>
	</div>

	<div class="page-content-overlay" data-action="toggle" data-class="mobile-nav-on"></div>
	<%@ include file="/front-end/template/footer.jsp"%>
	<%@ include file="/front-end/template/quick_menu.jsp"%>
	<%@ include file="/front-end/template/messager.jsp"%>
	<%@ include file="/front-end/template/basic_js.jsp"%>
</body>

</html>