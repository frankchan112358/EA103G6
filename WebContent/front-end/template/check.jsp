<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.user.model.UserVO"%>
<%@page import="com.student.model.StudentVO"%>
<%
UserVO userVO = (UserVO)session.getAttribute("userVO");
if(userVO == null){
	response.sendRedirect(request.getContextPath() + "/back-end/test/login.jsp");
	return;
} else if(((UserVO)userVO).getType() != 0){
	response.sendRedirect(request.getContextPath() + "/back-end/index/index.jsp");
	return;
}
StudentVO studentVO = (StudentVO)session.getAttribute("studentVO");
%>