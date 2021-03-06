<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banji.model.*"%>

<%
	BanjiVO banjiVO = (BanjiVO) request.getAttribute("banjiVO");
%>

<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />
<jsp:useBean id="banjiTypeSvc" scope="page"
	class="com.banjitype.model.BanjiTypeService" />
<jsp:useBean id="classroomSvc" scope="page"
	class="com.classroom.model.ClassroomService" />

<!DOCTYPE html>
<html>
<head>
<%@ include file="/back-end/template/head.jsp"%>
<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}

#add {
	text-align: center;
}
</style>
</head>
<body
	class="mod-bg-1 mod-nav-link header-function-fixed nav-function-top nav-mobile-push nav-function-fixed mod-panel-icon">
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
						<li class="breadcrumb-item"><a href="javascript:void(0);">後台首頁</a></li>
						<li class="breadcrumb-item"><a
							href="<%=request.getContextPath()%>/banji/banji.manage">養成班管理</a></li>
						<li class="breadcrumb-item">新增養成班</li>
					</ol>
					<div class="subheader">
						<h1 class="subheader-title">
							<i class='subheader-icon fal fa-plus-square'></i> 新增養成班
						</h1>
					</div>
					<div class="row">
						<div class="col col-xl-12">
							<div id="panel-1" class="panel">
								<div class="panel-hdr bg-primary-800 bg-success-gradient ">
									<h2 class="text-white">總覽</h2>
								</div>
					<div class="panel-container show">
						<div class="panel-content">

							<form METHOD="post" ACTION="<%=request.getContextPath()%>/banji/banji.do"
								name="form1" class="needs-validation" novalidate>
								<div class="form-group">
									<label class="form-label" for="simpleinput">選擇班種:</label> <select required
										class="custom-select form-control" name="banjiTypeNo"  > 
										<option value="">請選擇班種</option>
										<c:forEach var="banjiTypeVO" items="${banjiTypeSvc.all}">
											<c:if test="${ banjiTypeVO.banjiTypeEnable==1}">
												<option value="${banjiTypeVO.banjiTypeNo}"
													${(banjiVO.banjiTypeNo==banjiTypeVO.banjiTypeNo)? 'selected':'' }>${banjiTypeVO.banjiTypeName}
											</c:if>
										</c:forEach>
									</select> 
										<font color=red>${errorMsgs.banjiTypeNo}</font>
									<div class="invalid-feedback">
                                                       		班級種類請勿空白.
                                         </div>
								</div>

								<div class="form-group">
									<label class="form-label" for="simpleinput">輸入班級:</label> <input
										type="text" id="simpleinput" class="form-control" required
										placeholder="請輸入班級" name="banjiName"> <font color=red>${errorMsgs.banjiName}</font>
										<div class="invalid-feedback">
                                                      		  班級名稱請勿空白且只能是中、英文字母、數字，其他不行。
                                         </div>
								</div>

								<div class="form-group">
									<label class="form-label" for="example-date">開訓日:</label> <input
										class="form-control" name="startDay" id="f_date1" 
										onfocus="this.blur()"> <font color=red>${errorMsgs.compare}</font>
										<font color=red>${errorMsgs.compare1}</font>
								</div>

								<div class="form-group">
									<label class="form-label" for="example-date">結訓日:</label> <input
										class="form-control" name="endDay" id="f_date2"
										onfocus="this.blur()"> <font color=red>${errorMsgs.compare}</font>
										<font color=red>${errorMsgs.compare1}</font>
								</div>

								<div class="form-group">
									<label class="form-label" for="simpleinput">上課時數:</label> <input
										type="text" id="simpleinput" class="form-control" required
										placeholder="請輸入時數" name="classHours"> <font color=red>${errorMsgs.classHours}</font>
								<div class="invalid-feedback">
                                                      		  請填寫上課時數.
                                         </div>
								</div>

								<div class="form-group">
									<label class="form-label" for="simpleinput">學員人數:</label> <input
										type="text" id="simpleinput" class="form-control" required
										placeholder="請輸入學員人數" name="numberOfStudent"> <font
										color=red>${errorMsgs.numberOfStudent}</font>
										<div class="invalid-feedback">
                                                       		 請填寫上課人數.
                                         </div>
								</div>
								<div class="form-group">
									<label class="form-label" for="simpleinput">教室:</label> <select
										class="custom-select form-control" name="classroomNo" required>
										<option value="">請選擇教室</option>
										<c:forEach var="classroomVO" items="${classroomSvc.all}">
											<option value="${classroomVO.classroomNo}"
												${(banjiVO.classroomNo==classroomVO.classroomNo)? 'selected':'' }>${classroomVO.classroomName}
										</c:forEach>
									</select> <font color=red>${errorMsgs.classroomNo}</font>
									<div class="invalid-feedback">
                                                        		教室請勿空白.
                                         </div>
								</div>

								<div class="form-group">
									<label class="form-label" for="example-textarea">班級內容:</label>
									<textarea name="banjiContent" class="form-control" required
										id="example-textarea" rows="5" placeholder="請輸入內容"></textarea>
									<font color=red>${errorMsgs.banjiContent}</font>
											<div class="invalid-feedback">
                                                        		班級內容請勿空白.
                                         </div>
								</div>

								<div class="form-group">
									<label class="form-label" for="example-select">狀態:</label> <select
										class="custom-select form-control" name="status">
										<option value="1" ${(banjiVO.status==1)?'selected':'' }>開課</option>
										<option value="0" ${(banjiVO.status==0)?'selected':'' }>結訓</option>
										<option value="2" ${(banjiVO.status==2)?'selected':'' }>延期</option>
										<option value="3" ${(banjiVO.status==3)?'selected':'' }>未開課</option>
									</select> <font color=red>${errorMsgs.status}</font>
								</div>

								<div class="form-group" id="add">
									<input type="hidden" name="action" value="insert">
									<input type="hidden"name="empNo" value="${empVO.empNo}">
									<button type="submit"class="btn btn-primary justify-content-center">送出</button>
								</div>
							</form>
						</div>
						</div>
						</div>
						</div>
					</div>
				</main>
				<div class="page-content-overlay" data-action="toggle"
					data-class="mobile-nav-on"></div>
				<%@ include file="/back-end/template/footer.jsp"%>
			</div>
		</div>
	</div>
	<%@ include file="/back-end/template/quick_menu.jsp"%>
	<%@ include file="/back-end/template/messager.jsp"%>
	<%@ include file="/back-end/template/basic_js.jsp"%>

	<%
		java.sql.Date startDay = null;
		try {
			startDay = banjiVO.getStartDay();
		} catch (Exception e) {
			startDay = new java.sql.Date(System.currentTimeMillis());
		}

		java.sql.Date endDay = null;
		try {
			endDay = banjiVO.getEndDay();
		} catch (Exception e) {
			endDay = new java.sql.Date(System.currentTimeMillis());
		}
	%>
	
	<script
		src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.full.js"></script>

<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.css" />	

	<script>
$(function(){
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
	    	scrollInput : false,  //滾輪
		   value:'<%=startDay%>',
			   onShow:function(){
	               this.setOptions({
	                minDate: new Date()
	               })
	              },
	              timepicker:false,
	});

        $('#f_date2').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       scrollInput : false,  //滾輪
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=endDay%>',
				onShow : function() {
					this.setOptions({
						minDate : $('#f_date1').val()
					})
				},
				timepicker : false
			});
		});
	</script>
											<script>
                                                // Example starter JavaScript for disabling form submissions if there are invalid fields
                                                (function()
                                                {
                                                    'use strict';
                                                    window.addEventListener('load', function()
                                                    {
                                                        // Fetch all the forms we want to apply custom Bootstrap validation styles to
                                                        var forms = document.getElementsByClassName('needs-validation');
                                                        // Loop over them and prevent submission
                                                        var validation = Array.prototype.filter.call(forms, function(form)
                                                        {
                                                            form.addEventListener('submit', function(event)
                                                            {
                                                                if (form.checkValidity() === false)
                                                                {
                                                                    event.preventDefault();
                                                                    event.stopPropagation();
                                                                }
                                                                form.classList.add('was-validated');
                                                            }, false);
                                                        });
                                                    }, false);
                                                })();

                                            </script>
</body>
</html>