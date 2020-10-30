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
	<link rel="stylesheet" media="screen, print" href="<%=request.getContextPath()%>/SmartAdmin4/css/datagrid/datatables/datatables.bundle.css">


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
							<a href="<%=request.getContextPath()%>/back-end/course/listAllCourse.jsp">課程總覽</a>
						</li>
						<li class="breadcrumb-item">
							成績管理
						</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class="fas fa-star-half-alt" style="color: #374EFA;"></i>
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
															<c:if test="${studentVO.face==null}">
																<img src="<%=request.getContextPath()%>/images/noPicture.png">

															</c:if>

															<c:if test="${studentVO.face!=null}">
																<img src="<%=request.getContextPath()%>/user.do?action=getPhoto&userNo=${studentVO.userNo}">
															</c:if>

														</td>
														<td>
															<input type="hidden" name="studentNo" value="${studentVO.studentNo}">
															${studentVO.studentNo}</td>
														<td>
															<input type="hidden" name="studentName" value="${studentVO.studentName}">
															${studentVO.studentName}</td>
														<td class="scoreTd">
															<c:if test="${finalScoreSvc.getScore(courseNo, studentVO.studentNo)!=null}">
  																<input studentNo="studentVO.studentNo" class="score" type="number" value="${finalScoreSvc.getScore(courseNo, studentVO.studentNo)}" style="width:4em;text-align:center">  
																<span class="score">${finalScoreSvc.getScore(courseNo, studentVO.studentNo)}</span>
																
															</c:if>
															<c:if test="${finalScoreSvc.getScore(courseNo, studentVO.studentNo)==null}">
 																<input studentNo="studentVO.studentNo" class="score" type="number" value="" disabled style="width:4em;text-align:center" >
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


	<script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
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


			$("button.edit").click(function () {		
				$('button.submit').show();
				$('button.cancel').show();
				$('button.edit').hide();
				$('input.score').show();
				$('span.score').hide();
				
				//前端資料驗證
				
				let scoreList = [];
				$('input.score').each(function(index,score){
					   let finalScoreVO ={}; 
					   finalScoreVO.courseNo = '${courseNo}';
					   finalScoreVO.studentNo = $(score).attr('studentNo');
					   finalScoreVO.score = $(score).val();
					   scoreList.add(finalScoreVO);
				});
				
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/finalScore/finalScore.ajax',
                    data: {
                    	action:'update',
                    	scoreList:JSON.stringify(scoreList)
                    },
                    success(res){
                        if(res=='ok'){
                            
                        }                       
                    },
                    error(err){
                        console.log(err);
                    }
                });     
				
			}
			)
		});
	</script>
</body>

</html>