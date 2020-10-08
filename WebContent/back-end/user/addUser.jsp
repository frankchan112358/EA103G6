<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*"%>
<%@ page import="com.permission.model.*"%>
<%
	UserVO userVO = (UserVO) request.getAttribute("userVO");
%>
<html>
<head>

<title>使用者資料新增 - addUser.jsp</title>

<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js" integrity="sha384-LtrjvnR4Twt/qOuYxE721u19sVFLVSA4hf/rRt6PrZTmiPltdZcI7q7PXQBYTKyf" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>


</head>
<body>


	<h3>資料新增</h3>

	<a href="<%=request.getContextPath() %>/back-end/user/select_page.jsp">回首頁</a>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs }">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>


	<form method="post" action="<%=request.getContextPath()%>/user.do" enctype="multipart/form-data">
		<table>

			<tr>
				<td>身分別：</td>
				<td><select name="type" id="typeSelect">
						<option value="0" <% if(userVO!=null)if(userVO.getType()==0) out.print("selected"); %>>學生
						<option value="1" <% if(userVO!=null)if(userVO.getType()==1) out.print("selected"); %>>講師
						<option value="2" <% if(userVO!=null)if(userVO.getType()==2) out.print("selected"); %>>導師
				</select></td>
			</tr>
			

			<tr>
				<td>帳號：<font color=red><b>*</b></font></td>
				<td><input type="text" name="account" size="20" value="<%=(userVO == null) ? "" : userVO.getAccount()%>"></td>
			</tr>

			<tr>
				<td>密碼：<font color=red><b>*</b></font></td>
				<td><input type="password" name="password" size="20" value="<%=(userVO == null) ? "" : userVO.getPassword()%>"></td>
			</tr>

			<tr>
				<td>姓名：<font color=red><b>*</b></font></td>
				<td><input type="text" name="name" size="20" value="<%=(userVO == null) ? "" : userVO.getName()%>"></td>
			</tr>
			
			<tr>
				<td>身份證字號：<font color=red><b>*</b></font></td>
				<td><input type="text" name="id" size="20" value="<%=(userVO == null) ? "" : userVO.getId()%>"></td>
			</tr>

			<tr>
				<td>電子信箱：<font color=red><b>*</b></font></td>
				<td><input type="text" name="mail" size="40" value="<%=(userVO == null) ? "" : userVO.getMail()%>"></td>
			</tr>

			<tr>
				<td>手機號碼：</td>
				<td><input type="text" name="phone" size="40" value="<%=(userVO == null) ? "" : userVO.getPhone()%>"></td>
			</tr>

			<tr>
				<td>地址：</td>
				<td><input type="text" name="address" size="60" value="<%=(userVO == null) ? "" : userVO.getAddress()%>"></td>
			</tr>

			
			

		</table><br/>
		
		<div id="empPermission" style="display:none">
		<jsp:useBean id="permissionSvc" scope="page" class="com.permission.model.PermissionService"/>
	  
       <b>調整導師權限:</b> <br/>
       <table  cellpadding="8">
       <tr><td></td><td>可讀取</td><td>可編輯</td></tr>
       
      	 <%int i=0,r=0,e=0;%>
         <c:forEach var="permissionVO" items="${permissionSvc.all}" > 
          <tr><td>${permissionVO.permissionName}</td>  <td><input type="checkbox" name="readable<%=++r%>" data-toggle="toggle" data-size="xs" value="0" id="test<%=++i%>" ></td>
          <td><input type="checkbox" name="editabld<%=++e%>" data-toggle="toggle" data-size="xs" value="0" id="test<%=++i %>" ></td></tr>
         </c:forEach>  
         </table>
		</div>
		
		<br> 
		<input type="hidden" name="action" value="insert"> 
		<input type="submit" value="送出新增" onclick="javascript:return check();">
	</form>
	
	

<script>
$(document).ready(function(){
		if($("#typeSelect").val()==2){
			$("#empPermission").removeAttr("style");
		}
});

$("#typeSelect").change(function(){
	if($("#typeSelect").val()==2){
		$("#empPermission").show("slow");
	}else{
		$("#empPermission").hide("slow");
	}
});

<%for(int j=1;j<=i;j++){%>
	$("#test<%=j%>").change(function(){
		if($(this).prop("checked")){
			$(this).val(1);}
	});
<%}%>


function check() {
	var msg = "您確定要送出嗎？";
	if (confirm(msg)==true){
		return true;
	}else{
		return false;
	}
}

</script>

</body>
</html>