<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/front-end/template/check.jsp" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.forumpost.model.*"%>
<%@ page import="com.forumtopic.model.*"%>
<%@ page import="com.forumcomment.model.*"%>
<%@ page import="com.student.model.*"%>


<%

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	 <c:forEach var="forumPostVO" items="${list}">
     	${forumPostVO.title}
	</c:forEach>
</body>
</html>