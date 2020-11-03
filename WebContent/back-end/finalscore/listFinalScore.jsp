<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/back-end/template/check.jsp"%>
<%@ page import="com.course.model.*, com.finalscore.model.*, com.student.model.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService" />
<jsp:useBean id="finalScoreSvc" scope="page" class="com.finalscore.model.FinalScoreService" />

<!DOCTYPE html>
<html>

<head>
	<%@ include file="/back-end/template/head.jsp" %>
	<style>
		.table th,
		.table td {
			vertical-align: middle;
			text-align: center;
		}

		img {
			width: 40%;
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
							成績管理
						</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class="subheader-icon fal fa-clipboard-list-check"></i>
							成績管理
						</h1>
					</div>
					<div class="row">
						<div class="col-xl-12">
							<jsp:include page="/back-end/course/courseNav.jsp"></jsp:include>
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-gradient-info">
									<h2>成績列表</h2>
								</div>
								<div class="panel-container show">
									<div class="panel-content">
										<button type="button" class="mr-1 btn btn-success waves-effect waves-themed float-left edit">
											<span class="fal fa-edit mr-1"></span>
											<span>修改成績</span>
										</button>
										<button type="button" class="mr-1 btn btn-info waves-effect waves-themed float-left submit">
											<span class="fal fa-plus mr-1"></span>
											<span>送出</span>
										</button>
										<button type="button" class="mr-1 btn btn-danger waves-effect waves-themed float-left cancel">
											<span class="fal fa-cancel mr-1"></span>
											<span>取消</span>
										</button>
										<!-- datatable start -->
										<table id="coursetable" class="table table-bordered table-hover table-striped w-100">
											<thead style="background-color:#E5F4FF;">

												<tr>
													<th width="10%">頭像</th>
													<th>學號</th>
													<th>姓名</th>
													<th>成績</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="studentVO" items="${courseSvc.getOneCourse(courseNo).banjiVO.studentList}">
													<tr>
														<td class="pic">
															<img class="rounded profile-image" src="<%=request.getContextPath()%>/user.do?action=getPhoto&userNo=${studentVO.userNo}">
														</td>
														<td>
															<input type="hidden" name="studentNo" value="${studentVO.studentNo}">
															${studentVO.studentNo}</td>
														<td>
															<input type="hidden" name="studentName" value="${studentVO.studentName}">
															${studentVO.studentName}</td>
														<td class="scoreTd">
															<c:if test="${finalScoreSvc.getScore(courseNo, studentVO.studentNo)!=null}">
																<input studentNo="${studentVO.studentNo}" class="score" type="number" value="${finalScoreSvc.getScore(courseNo, studentVO.studentNo)}" oldValue="${finalScoreSvc.getScore(courseNo, studentVO.studentNo)}" style="width:4em;text-align:center">
																<span class="score">${finalScoreSvc.getScore(courseNo, studentVO.studentNo)}</span>
															</c:if>
															<c:if test="${finalScoreSvc.getScore(courseNo, studentVO.studentNo)==null}">
																<input studentNo="${studentVO.studentNo}" class="score" type="number" value="" oldValue="" style="width:4em;text-align:center">
																<span class="score">尚未評分</span>
															</c:if>
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
	<script>

		$(document).ready(function () {
			$('input.score').hide();
			$('span.score').show();
			$('button.submit').hide();
			$('button.cancel').hide();
			$('button.edit').show();
			$('#coursetable').dataTable({
				responsive: true,
				language: { url: '<%=request.getContextPath()%>/SmartAdmin4/js/datatable/lang/tw.json' },
				"columnDefs": [{
					"targets": [-2, -4],
					"orderable": false,
				}]
			});

			//切換到編輯模式
			$("button.edit").click(function () {
				//1.隱藏按鈕
				$('button.submit').show();
				$('button.cancel').show();
				//2.顯示按鈕
				$('button.edit').hide();
				//3.顯示input
				$('input.score').show();
				//4.隱藏span
				$('span.score').hide();
			});

			//TO DO 取消編輯，並切換到檢視模式 (按鈕顯示/隱藏/input消失/值還原)
			$('button.cancel').click(function () {
				//1.顯示按鈕
				$('button.edit').show();
				//2.隱藏按鈕
				$('button.submit').hide();
				$('button.cancel').hide();
				//3.還原input的value，提示input的attr有偷存oldValue
				let scorelist = $('input.score');
				for (let i = 0; i < scorelist.length; i++) {
					let scoreinput = scorelist[i];
					let oldvalue = scoreinput.getAttribute('oldvalue');
					scoreinput.value = oldvalue;
				}
				//4.隱藏input
				$('input.score').hide();
				//5.顯示span
				$('span.score').show();
			});

			//送出成績資料
			$('button.submit').click(function () {
				//TO DO 前端資料驗證，提示如果不合格，要return;，不然就會資料出去
				//Future 可以提示使用者，你哪個沒有填

				//驗證資料ok之後，把前面的input的資料包成陣列
				let scoreList = [];
				$('input.score').each(function (index, score) {
					let finalScoreVO = {};
					finalScoreVO.finalScoreNo = "";
					finalScoreVO.courseNo = '${courseNo}';
					finalScoreVO.studentNo = $(score).attr('studentNo');
					finalScoreVO.score = $(score).val();
					scoreList.push(finalScoreVO);
				});

				//利用ajax的方式送到後台
				$.ajax({
					beforeSend: function () {
						//再送出之前，可以做一些處理，but不能取消送出!!
					},
					type: 'POST',
					url: '<%=request.getContextPath()%>/finalScore/finalScore.ajax',
					data: {
						action: 'update',
						scoreList: JSON.stringify(scoreList)
					},
					success: function (res) {
						//servlet的res會在這邊處理
						if (res == "ok") {
							//TO DO 如果成功，請切換回檢視模式
							//1.顯示按鈕
							$('button.edit').show();
							//2.隱藏按鈕
							$('button.submit').hide();
							$('button.cancel').hide();


							let scorelist = $('input.score');
							let spanlist = $('span.score');
							for (let i = 0; i < scorelist.length; i++) {
								//3.更新input的oldValue，提示oldValue = value
								let scoreinput = scorelist[i];
								scoreinput.setAttribute('oldvalue', scoreinput.value);
								//4.更新span的text
								let spanscore = spanlist[i];
								spanscore.innerHTML = scoreinput.value;
							}

							//5.隱藏input
							$('input.score').hide();
							//6.顯示span
							$('span.score').show();

							alert('成績上傳成功');
						} else {
							console.log(res);
							alert('成績上傳失敗');
							//Future 可以提示使用者，你哪個成績是失敗的
						}
					},
					error: function (err) {
						console.log(err);
						alert('成績上傳失敗');
					},
					complete() {
						//整個ajax完成之後，收尾前還有想做的事情
					}
				});
			});
			document.getElementById('aListAllCourse').addEventListener('click', function (e) {
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
				banjiNoInput.value = banjiNo;
				myForm.append(banjiNoInput);
				myForm.submit();
			}, false);
		});
	</script>
</body>

</html>