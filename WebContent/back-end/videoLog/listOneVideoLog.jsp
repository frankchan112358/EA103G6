<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.videolog.model.*"%>

<%
	VideoLogVO videoLogVO = (VideoLogVO) request.getAttribute("videoLogVO");
%>
<html>
<head>
<title>影片觀看紀錄資料 - listOneVedioLog.jsp</title>


<style>
  table#table-1 {
	background-color: #CCCCFF;
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
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		<h3>影片觀看紀錄資料</h3>
		 <h4><a href="<%=request.getContextPath() %>/back-end/videoLog/select_page.jsp"><img src="<%=request.getContextPath() %>/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr> 
</table>

<table>
	<tr>
		<th>影片編號</th>
		<th>觀看時間</th>
		<th>狀態</th>
	</tr>
	<tr>
		<td><%=videoLogVO.getVideoNo()%></td>
		<td><%=videoLogVO.getWatchTime()%></td>
		<td><%=videoLogVO.getStatus()%></td>
	</tr>
</table>

</body>
</html>