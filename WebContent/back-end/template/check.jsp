<%@page import="com.user.model.UserVO"%>
<%@page import="com.teacher.model.TeacherVO"%>
<%@page import="com.emp.model.EmpVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
UserVO userVO = (UserVO)session.getAttribute("userVO");
if(userVO == null){
	response.sendRedirect(request.getContextPath() + "/back-end/test/login.jsp");
	return;
} else if(((UserVO)userVO).getType() == 0){
	response.sendRedirect(request.getContextPath() + "/fornt-end/index/index.jsp");
	return;
} else if(((UserVO)userVO).getType() == 1){
	TeacherVO teacherVO = (TeacherVO)session.getAttribute("teacherVO");
} else if(((UserVO)userVO).getType() == 2){
	EmpVO empVO = (EmpVO)session.getAttribute("empVO");
}

%>