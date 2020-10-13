<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Student</title>
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

	<a href='listAllStudent.jsp'>顯示目前所有學生</a>
	<br/>
	<a href='addStudent.jsp'>加入學生</a>
	<br/>
	<br/>

	<jsp:useBean id="studentSvc" scope="page" class="com.student.model.StudentService"/>
	  
	   <FORM METHOD="post" ACTION="student.do" >
        <b>輸入學生編號 (如S000001):</b>
        <input type="text" name="studentNo">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
	      
	  <FORM METHOD="post" ACTION="student.do" >
       <b>選擇學生姓名:</b>
       <select size="1" name="studentNo">
         <c:forEach var="studentVO" items="${studentSvc.all}" > 
          <option value="${studentVO.studentNo}">${studentVO.studentName}
         </c:forEach>   
       </select>

       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
    
</body>
</html>