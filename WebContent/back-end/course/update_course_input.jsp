<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.course.model.*"%>

<%
	CourseVO courseVO = (CourseVO) request.getAttribute("courseVO");
%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>課程資料修改</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>
table#table-1 {
	border-bottom: 2px solid #0e6e95;
    text-align: center;
    margin-left:auto;
    margin-right:auto;
}

  .h3-1 {
    margin: 10px;
  }
  
 table#table-3 {
    text-align: center;
}


h5{
	margin: 5px auto;
	text-align: left;
}
  
</style>

<style>
table {
	width: 550px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
	margin-left:auto;
	margin-right:auto;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}

.swal-text {
  background-color: #FEFAE3;
  padding: 17px;
  border: 1px solid #F0E1A1;
  font-size: 17px;
  font-weight:bold;
  display: block;
  margin: 22px;
  text-align: center;
  color: #F56455;
}

</style>
</head>
<body bgcolor='white'>
<div class="container">

	<table id="table-1">	
	<tr><td>
		 <h3 class="h3-1">課程資料修改</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/course/select_page.jsp"><img src="<%=request.getContextPath()%>/images/homepage.svg" width="80" height="50" border="0">Home</a></h4>
	</td></tr>
	</table>
	

 
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/course/course.do" name="form1">
		<table id="table-3">
			
				<tr><td><h5>課程編號</h5></td></tr>
				<tr><td><input type="TEXT" name="courseNo" size="67" disabled
					value="${courseVO.courseNo}" /></td></tr>
			
			
				<tr><td><h5>基本課程編號</h5></td></tr>
				<tr><td><input type="TEXT" name="basicCourseNo" size="67" 
					value="${courseVO.basicCourseNo}"/><font color=red>${errorMsgs.basicCourseNo}</font></td></tr>
			
			
				<tr><td><h5>課程名稱</h5></td></tr>
				<tr><td><input type="TEXT" name="courseName" size="67"
					value="${courseVO.courseName}" /><font color=red>${errorMsgs.courseName}</font></td></tr>
			
			
				<tr><td><h5>課程大綱</h5></td></tr>
				<tr><td><textarea id="courseOutline" name="courseOutline" rows="8"  style= "width: 550px; resize:none" >${courseVO.courseOutline}</textarea></td></tr>
			
			
				<tr><td><h5>班級</h5></td></tr>
				<tr><td><input type="TEXT" name="banjiNo" size="67"
					value="${courseVO.banjiNo}" /><font color=red>${errorMsgs.banjiNo}</font></td></tr>
			
			
				<tr><td><h5>講師</h5></td></tr>
				<tr><td><input type="TEXT" name="teacherNo" size="67"
					value="${courseVO.teacherNo}" /><font color=red>${errorMsgs.teacherNo}</font></td></tr>
			
			
				<tr><td><h5>教室</h5></td></tr>
				<tr><td><input type="TEXT" name="classroomNo" size="67"
					value="${courseVO.classroomNo}" /><font color=red>${errorMsgs.classroomNo}</font></td></tr>
			
			
				<tr><td><h5>堂數</h5></td></tr>
				<tr><td><input type="TEXT" name="lesson" size="67"
					value="${courseVO.lesson} " /><font color=red>${errorMsgs.lesson}</font></td></tr>
			
			
				<tr><td><h5>開始日期</h5></td></tr>
				<tr><td><input name="startDate" id="f_date1" type="TEXT" size="67" onfocus="this.blur()"><font color=red>${errorMsgs.startDate}</font></td></tr>
			
			
				<tr><td><h5>結束日期</h5></td></tr>
				<tr><td><input name="endDate" id="f_date2" type="TEXT" size="67" onfocus="this.blur()"><font color=red>${errorMsgs.endDate}</font></td></tr>
			
			
				<tr><td><h5>狀態</h5></td></tr>
				<tr><td><select size="1" name="status" style="font-size:16px; width:550px; height:26px;">
         				<option value="0"${(courseVO.status==0)?'selected':'' }>課程未開始 </option>
						<option value="1"${(courseVO.status==1)?'selected':'' }>課程進行中 </option>
						<option value="2"${(courseVO.status==2)?'selected':'' }>課程結束 </option>
						</select>
				</td></tr>
          		
		</table>
		<br>
		
		<div class="form-row align-items-center justify-content-center">
		<input type="hidden" name="action" value="update">
		<input type="hidden" name="courseNo" value="${courseVO.courseNo}">
		<input class="btn btn-primary justify-content-center" type="submit" value="Submit">
		</div>
		
		</FORM>

</div>
	

</body>

<% 
 	java.sql.Date startDate = null;
  try {
	  startDate = courseVO.getStartDate();
   } catch (Exception e) {
	   startDate = new java.sql.Date(System.currentTimeMillis());
   }
%>

<% 
 	java.sql.Date endDate = null;
  try {
	  endDate = courseVO.getEndDate();
   } catch (Exception e) {
	   endDate = new java.sql.Date(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
		$(function(){
			$.datetimepicker.setLocale('zh');
			$('#f_date1').datetimepicker({
			  format:'Y-m-d',
			  timepicker:false,
			  step: 1,
			  scrollInput : false,      
			  value: '<%=courseVO.getStartDate()%>',
		 	  onShow:function(){
	        	   this.setOptions({
	        	    minDate: new Date()
	        	   })
	        	  },
	        	  timepicker:false
	        	 });
        
			$('#f_date2').datetimepicker({
				format:'Y-m-d',
	        	timepicker:false,
	        	step: 1,
	        	scrollInput : false,
		   		value: '<%=courseVO.getEndDate()%>',
				onShow : function() {
					this.setOptions({
					minDate : $('#f_date1').val()
				})
			},
			timepicker : false
		});
	});
		
		
		<c:if test="${not empty errorMsgs}">
		<c:forEach var="message" items="${errorMsgs}">
			swal({
				icon:'error',
				title:'手指是否有問題',
				text:'⚠有項目未填⚠'
				});
		</c:forEach>
		</c:if>
		
</script>
</html>