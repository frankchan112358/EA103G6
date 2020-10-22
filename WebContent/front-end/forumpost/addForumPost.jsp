<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.forumpost.model.*"%>

<%
   ForumPostVO forumPostVO = (ForumPostVO) request.getAttribute("forumPostVO");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>新增貼文</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
</head>
<body>
	 <div id="addForumPost"> </div>

	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/forumPost/forumPost.do" name="form1">
	
	 
	 
            <fieldset>
                <legend><b>新增貼文</b></legend>
                <p>
                    <label for="forumTopicNo">主題編號:</label>
                    <select size="1" name="forumTopicNo">
					<option value="1" ${('1'==forumPostVO.forumTopicNo)?'selected':''} >1</option>
					<option value="2" ${('2'==forumPostVO.forumTopicNo)?'selected':''} >2</option>
					<option value="3" ${('3'==forumPostVO.forumTopicNo)?'selected':''} >3</option>
					
				    </select>
                </p>
                
                
                
                <p>
                    <label for="studentNo">學員編號 :</label>
                    <input type="text" name="studentNo"
					value="<%=(forumPostVO == null) ? "S000001" : forumPostVO.getStudentNo()%>">
                  	<input type="hidden" name="forumPostNo" value="${forumPostVO.forumPostNo}">
                </p>
                
                <p>
                    <label for="title">標題 :</label>
                   <input type="text" name="title"
					value="<%=(forumPostVO == null) ? "請輸貼文標題" : forumPostVO.getTitle()%>">
                </p>
                <p>
                    <label for="content">貼文內容 :</label>
                </p>
                
              
        
        <textarea id="summernote"  name="content"></textarea>
        
         <p><input type="hidden" name="action" value="insert"> 
				<input type="submit"></p>
            </fieldset>
        </form>
  <script>
    $(document).ready(function() {
        $('#summernote').summernote();
    });
  </script>
  
        
   
	
	
</body>
</html>