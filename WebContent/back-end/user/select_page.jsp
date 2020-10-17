<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>User</title>
<style>
a {
	text-decoration: none;
	color: green;
}

a:hover {
	color: red;
	text-decoration: underline;
}
</style>

<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body>



	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<a href='<%=request.getContextPath() %>/back-end/user/listAllUser.jsp'>顯示目前所有使用者</a>
	<br />
	<a href='<%=request.getContextPath() %>/back-end/user/addUser.jsp'>加入使用者</a>
	<br />
	<br />
	<h3>資料查詢:</h3>
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/user.do">
		<b>輸入使用者編號 (如1001):</b>
		<input type="text" name="userNo"> 
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="送出">
	</FORM>

	<jsp:useBean id="userSvc" scope="page"
		class="com.user.model.UserService" />
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/user.do">
		<b>選擇使用者姓名:</b> <select size="1" name="userNo">
			<c:forEach var="userVO" items="${userSvc.all}">
				<option value="${userVO.userNo}">${userVO.name}
			</c:forEach>
		</select> <input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="送出">
	</FORM>


<!-- 	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/user.do"> -->
<!-- 		<b>請輸入身分別：</b> <select id="chooseType" name=""> -->
<!-- 			<option value="null">請選擇 -->
<!-- 			<option value="0">學生 -->
<!-- 			<option value="1">講師 -->
<!-- 			<option value="2">導師 -->
<!-- 		</select> -->
<!-- 		<div id="waitInset"></div> -->



<!-- 		<input type="hidden" name="action" value="getOne_For_Display"> -->
<!-- 		<input type="submit" value="送出"> -->
<!-- 	</FORM> -->


	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/user.do">
		<jsp:useBean id="teacherSvc" scope="page"
			class="com.teacher.model.TeacherService" />
		<b>選擇講師編號:</b>
		 <select size="1" name="userNo">
			<c:forEach var="teacherVO" items="${teacherSvc.all}">
				<option value="${teacherVO.userNo}">${teacherVO.teacherNo}
			</c:forEach>
		</select> 
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="送出">
	</FORM>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/user.do">
		<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />
		<b>選擇導師編號:</b> 
		<select size="1" name="userNo">
			<c:forEach var="empVO" items="${empSvc.all}">
				<option value="${empVO.userNo}">${empVO.empNo}
			</c:forEach>
		</select> 
		<input type="hidden" name="action" value="getOne_For_Display">
		<input type="submit" value="送出">
	</FORM>


	<script>
		$("#chooseType").change(function() {
			if ($("#chooseType").val() == 0) {
				$("#waitInset").html("請輸入學生編號： <input type='text' name='studentNo' size='20'>")
			} else if ($("#chooseType").val() == 1) {
				$("#waitInset").html("請輸入講師編號： <input type='text' name='teacherNo' size='20'>")
			} else if ($("#chooseType").val() == 2) {
				$("#waitInset").html("請輸入導師編號： <input type='text' name='empNo' size='20'>")
			} else {
				$("#waitInset").empty();
			}
		});
	</script>

</body>
</html>