<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.banji.model.*"%>

<%
	BanjiVO banjiVO = (BanjiVO) request.getAttribute("banjiVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>班級資料修改</title>

<style>
table#table-1 {
	background-color: #c8c8c8;
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

table {
	width: 450px;
	background-color: #c8c8c8;
	margin-top: 1px;
	margin-bottom: 1px;
}

input, select, textarea {
	padding: 5px 15px;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}

td {
	padding: 1px;
	border: 0px solid #CCCCFF;
}
</style>

</head>
<body bgcolor='#c8c8c8'>

	<table id="table-1">
		<tr>
			<td>
				<h3>班級資料修改</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/banji/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料修改:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<jsp:useBean id="banjiTypeSvc" scope="page"
		class="com.banjitype.model.BanjiTypeService" />

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/banji/banji.do" name="form1">
		<table>
			<tr>
				<td>班級編號:</td>
				<td><%=banjiVO.getBanjiNo()%></td>
			</tr>

	<jsp:useBean id="empSvc" scope="page"
				class="com.emp.model.EmpService" />

			<tr>
				<td>導師名稱:</td>
				<td><select size="1" name="empNo">
						<c:forEach var="empVO" items="${empSvc.all}">
							<option value="${empVO.empNo}"
								${(banjiVO.empNo==empVO.empNo)?'selected':'' }>${empVO.empName}
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td>班級種類:<font color=red><b>*</b></font></td>
				<td><select size="1" name="banjiTypeNo">
						<c:forEach var="banjiTypeVO" items="${banjiTypeSvc.all}">
							<option value="${banjiTypeVO.banjiTypeNo}"
								${(banjiVO.banjiTypeNo==banjiTypeVO.banjiTypeNo)?'selected':'' }>${banjiTypeVO.banjiTypeName}
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td>開訓日期:</td>
				<td><input type="TEXT" name="startDay" size="45" id="f_date1"
					value="<%=banjiVO.getStartDay()%>" /></td>
			</tr>
			<tr>
				<td>結訓日期:</td>
				<td><input type="TEXT" name="endDay" size="45" id="f_date2"
					value="<%=banjiVO.getEndDay()%>" /></td>
			</tr>

			<tr>
				<td>班級名稱:</td>
				<td><input type="TEXT" name="banjiName" size="45"
					value="<%=banjiVO.getBanjiName()%>" /></td>
			</tr>

			<tr>
				<td>上課時數:</td>
				<td><input name="classHours" size="45" type="text"
					value="<%=banjiVO.getClassHours()%>"></td>
			</tr>

			<tr>
				<td>學生人數:</td>
				<td><input type="TEXT" name="numberOfStudent" size="45"
					value="<%=banjiVO.getNumberOfStudent()%>" /></td>
			</tr>

			<jsp:useBean id="classroomSvc" scope="page"
				class="com.classroom.model.ClassroomService" />

			<tr>
				<td>班級教室:<font color=red><b>*</b></font></td>
				<td><select size="1" name="classroomNo">
						<c:forEach var="classroomVO" items="${classroomSvc.all}">
							<option value="${classroomVO.classroomNo}"
								${(banjiVO.classroomNo==classroomVO.classroomNo)?'selected':'' }>${classroomVO.classroomName}
						</c:forEach>
				</select></td>
			</tr>
			
			<tr>
				<td>狀態:</td>
				<td><input type="TEXT" name="status" size="45"
					value="<%=banjiVO.getStatus()%>" /></td>
			</tr>
			

			<jsp:useBean id="banjiSvc" scope="page"
				class="com.banji.model.BanjiService" />

			<tr>
				<td>班級內容:</td>
				<td><textarea name="banjiContent"
						style="resize: none; width: 300px; height: 150px;"><%=(banjiVO == null) ? "安安" : banjiVO.getBanjiContent()%></textarea></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="banjiNo" value="<%=banjiVO.getBanjiNo()%>">
		<input type="submit" value="送出修改">
	</FORM>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->
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
		   value: '<%=banjiVO.getStartDay()%>'
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
		   value: '<%=banjiVO.getEndDay()%>'
	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	//startDate:	            '2017/07/10',  // 起始日
	//minDate:               '-1970-01-01', // 去除今日(不含)之前
	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	});
</script>
</html>