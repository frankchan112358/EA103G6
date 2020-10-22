<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.forumcomment.model.*"%>

<%
  ForumCommentVO forumCommentVO = (ForumCommentVO) request.getAttribute("forumCommentVO");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>回覆貼文</title>
  <meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
</head>
<body class="subpage">
	<!-- Header -->
	<!-- One -->
	<section id="One" class="wrapper style3">
		<div class="inner">
			<header class="align-center">
				<h2>WJL 討論區</h2>
			</header>
		</div>
	</section>
	<!-- Two -->
	<section id="two" class="wrapper style2">
		<div class="inner">
			<div class="box">
				<div class="content">
					<header class="align-center">
						<h2>
							<b>新增留言</b>
						</h2>
					</header>
					<div id="addforumComment">
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/forumComment/forumComment.do" name="form1">
	
	
	<fieldset>
                <legend><b>新增貼文</b></legend>
                <p>
                    <label for="forumPostNo">貼文編號:</label>
                    <select size="1" name="forumPostNo">
					<option value="1" ${('1'==forumCommentVO.forumPostNo)?'selected':''} >1</option>
					<option value="2" ${('2'==forumCommentVO.forumPostNo)?'selected':''} >2</option>
					<option value="3" ${('3'==forumCommentVO.forumPostNo)?'selected':''} >3</option>
					
				    </select>
                </p>
	   

	
       
       <p>
                    <label for="studentNo">學員編號 :</label>
                    <input type="text" name="studentNo"
					value="<%=(forumCommentVO == null) ? "S000001" : forumCommentVO.getStudentNo()%>">
                  	<input type="hidden" name="forumCommentNo" value="${forumCommentVO.forumCommentNo}">
                </p>
	 
           
                
                
                
								<p>
									<label for="content">留言內容 :</label>
								</p>
								<textarea id="summernote" name="content"></textarea>
								
								<p>
								   <input type="hidden" name="action" value="insert"/>
	                               <input type="submit" value="送出新增"/>
	                            </p>
											</fieldset>
				
							</form>
							</div>
				</div>
			</div>
		</div>
	</section>
              
       
  <script>
    $(document).ready(function() {
        $('#summernote').summernote();
    });
  </script>
  
        
   
	
	
</body>
</html>