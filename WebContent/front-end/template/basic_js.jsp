<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath() %>/SmartAdmin4/js/vendors.bundle.js"></script>
<script src="<%=request.getContextPath() %>/SmartAdmin4/js/app.bundle.js"></script>
<script type="text/javascript">
    /* Activate smart panels */
    $('#js-page-content').smartPanel();
</script>
<!-- The order of scripts is irrelevant. Please check out the plugin pages for more details about these plugins below: -->
<script src="<%=request.getContextPath() %>/SmartAdmin4/js/dependency/moment/moment.js"></script>
<script src="<%=request.getContextPath() %>/SmartAdmin4/js/miscellaneous/fullcalendar/fullcalendar.bundle.js"></script>
<script src="<%=request.getContextPath() %>/SmartAdmin4/js/statistics/sparkline/sparkline.bundle.js"></script>
<script src="<%=request.getContextPath() %>/SmartAdmin4/js/statistics/easypiechart/easypiechart.bundle.js"></script>
<script src="<%=request.getContextPath() %>/SmartAdmin4/js/statistics/flot/flot.bundle.js"></script>
<script src="<%=request.getContextPath() %>/SmartAdmin4/js/miscellaneous/jqvmap/jqvmap.bundle.js"></script>
<script src="<%=request.getContextPath() %>/SmartAdmin4/js/datagrid/datatables/datatables.bundle.js"></script>
<script>var NotifyMyPoint = "/NotifyServlet/${sessionScope.userVO.userNo}"</script>
<script src="<%=request.getContextPath() %>/notify/websocketNotify.js"></script>
<script src="<%=request.getContextPath() %>/SmartAdmin4/js/notifications/sweetalert2/sweetalert2.bundle.js"></script>

<!-- 此為聊天室呼叫之方法 -->
<script>
$(document).ready(function (){	
	messagerInit();//此方法寫在messager.jsp
	getAllMessager(); //此方法在messager.jsp中作為顯示聊天列表

}); 
</script>