<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banji.model.*"%>

<%
	BanjiVO banjiVO = (BanjiVO) request.getAttribute("banjiVO");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>新增班級</title>
<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}

input, select, textarea {
	padding: 5px 15px;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}

table {
	width: 500px;
	background-color: #c8c8c8;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
	font-family: DFKai-sb;
}
</style>

</head>
<body bgcolor=#c8c8c8>
	<table id="table-1">
		<tr>
			<td>
				<h3>班級資料新增</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/banji/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>



	<h3>內容:</h3>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banji/banji.do" name="form1">
		<table>

			<tr>
				<td>班級名稱:</td>
				<td><input type="TEXT" name="banjiName" size="40"
					value="<%=(banjiVO == null) ? "EA103" : banjiVO.getBanjiName()%>" /></td>
			</tr>

			<jsp:useBean id="banjiTypeSvc" scope="page"
				class="com.banjitype.model.BanjiTypeService" />

			<tr>
				<td>班種名稱:</td>
				<td><select size="1" name="banjiTypeNo">
						<c:forEach var="banjiTypeVO" items="${banjiTypeSvc.all}">
							<option value="${banjiTypeVO.banjiTypeNo}"
								${(banjiVO.banjiTypeNo==banjiTypeVO.banjiTypeNo)? 'selected':'' }>${banjiTypeVO.banjiTypeName}
						</c:forEach>
				</select></td>
			</tr>
			
	<jsp:useBean id="empSvc" scope="page"
				class="com.emp.model.EmpService" />

			<tr>
				<td>導師名稱:</td>
				<td><select size="1" name="empNo">
						<c:forEach var="empVO" items="${empSvc.all}">
							<option value="${empVO.empNo}"
								${(banjiVO.empNo==empVO.empNo)? 'selected':'' }>${empVO.empName}
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>開訓日期:</td>
				<td><input type="TEXT" name="startDay" id="f_date1"></td>
			</tr>
			<tr>
				<td>結訓日期:</td>
				<td><input type="TEXT" name="endDay" id="f_date2"></td>
			</tr>
			<tr>
				<td>上課時數:</td>
				<td><input type="TEXT" name="classHours" size="40"
					value="<%=(banjiVO == null) ? "600" : banjiVO.getClassHours()%>" /></td>
			</tr>
			<tr>
				<td>學生人數:</td>
				<td><input type="TEXT" name="numberOfStudent" size="40"
					value="<%=(banjiVO == null) ? "47" : banjiVO.getNumberOfStudent()%>" /></td>
			</tr>

			<jsp:useBean id="classroomSvc" scope="page"
				class="com.classroom.model.ClassroomService" />

			<tr>

				<td>教室編號:</td>
				<td><select size="1" name="classroomNo"><c:forEach
							var="classroomVO" items="${classroomSvc.all}">
							<option value="${classroomVO.classroomNo}"
								${(banjiVO.classroomNo==classroomVO.classroomNo)? 'selected':'' }>${classroomVO.classroomName}
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>狀態:</td>
				<td><input type="TEXT" name="status" size="40"
					value="<%=(banjiVO == null) ? "1" : banjiVO.getStatus()%>"></td>
			</tr>

			<tr>
				<td>班級內容:</td>
				<td><textarea name="banjiContent"
						style="resize: none; width: 300px; height: 150px;"><%=(banjiVO == null) ? "安安" : banjiVO.getBanjiContent()%></textarea></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出">
	</FORM>
</body>
<%
	java.sql.Date startDay = null;
	try {
		startDay = banjiVO.getStartDay();
	} catch (Exception e) {
		startDay = new java.sql.Date(System.currentTimeMillis());
	}
%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=startDay%>'
	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	//startDate:	            '2017/07/10',  // 起始日
	//minDate:               '-1970-01-01', // 去除今日(不含)之前
	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	});
</script>
<%
	java.sql.Date endDay = null;
	try {
		endDay = banjiVO.getEndDay();
	} catch (Exception e) {
		endDay = new java.sql.Date(System.currentTimeMillis());
	}
%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date2').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=endDay%>'
	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	//startDate:	            '2017/07/10',  // 起始日
	//minDate:               '-1970-01-01', // 去除今日(不含)之前
	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	});
</script>
</html>