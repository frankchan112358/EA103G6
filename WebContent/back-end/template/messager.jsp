<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade js-modal-messenger modal-backdrop-transparent" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-right">
        <div class="modal-content h-100">
            <div class="dropdown-header bg-trans-gradient d-flex align-items-center w-100" ">
                <div class="d-flex flex-row align-items-center mt-1 mb-1 color-white">
                    <span class="mr-2">
                    	<span class="rounded-circle profile-image d-block" style="background-image:url(); background-size: cover;" id="spaceForPic"></span>
                    </span>
                    <div class="info-card-text">
                        <a href="javascript:void(0);" class="fs-lg text-truncate text-truncate-lg text-white" data-toggle="dropdown" aria-expanded="false" id="topChatName">
                        </a>
                    </div>
                </div>
                <button type="button" class="close text-white position-absolute pos-top pos-right p-2 m-1 mr-2" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><i class="fal fa-times"></i></span>
                </button>
            </div>
            <div class="modal-body p-0 h-100 d-flex">
                <!-- BEGIN msgr-list -->
                <div class="msgr-list d-flex flex-column bg-faded border-faded border-top-0 border-right-0 border-bottom-0 position-absolute pos-top pos-bottom">
                    <div>
                        <div class="height-4 width-3 h3 m-0 d-flex justify-content-center flex-column color-primary-500 pl-3 mt-2">
                            <i class="fal fa-search"></i>
                        </div>
                        <input type="text" class="form-control bg-white" id="msgr_listfilter_input" placeholder="Filter contacts" aria-label="FriendSearch" data-listfilter="#js-msgr-listfilter">
                    </div>
                    <div class="flex-1 h-100 custom-scroll">
                        <div class="w-100">
                            <ul id="js-msgr-listfilter" class="list-unstyled m-0">
                                
                            </ul>
                            <div class="filter-message js-filter-message"></div>
                        </div>
                    </div>
                    <div>
                        <a class="fs-xl d-flex align-items-center p-3">
                            <i class="fal fa-cogs"></i>
                        </a>
                    </div>
                </div>
                <!-- END msgr-list -->
                <!-- BEGIN msgr -->
                <div class="msgr d-flex h-100 flex-column bg-white">
                    <!-- BEGIN custom-scroll -->
                    <div class="custom-scroll flex-1 h-100">
                        <div id="chat_container" class="w-100 p-4">
                           
                        </div>
                    </div>
                    <!-- END custom-scroll  -->
                    <!-- BEGIN msgr__chatinput -->
                    <div class="d-flex flex-column">
                        <div class="border-faded border-right-0 border-bottom-0 border-left-0 flex-1 mr-3 ml-3 position-relative shadow-top">
                            <div class="pt-3 pb-1 pr-0 pl-0 rounded-0" tabindex="-1">
                                <div id="msgr_input" contenteditable="true" data-placeholder="請輸入文字..." class="height-10 form-content-editable" onkeydown="if (event.keyCode == 13) sendMessage()"></div>
                            </div>
                        </div>
                        <div class="height-8 px-3 d-flex flex-row align-items-center flex-wrap flex-shrink-0">
                            <a href="javascript:void(0);" class="btn btn-icon fs-xl width-1 mr-1" data-toggle="tooltip" data-original-title="More options" data-placement="top">
                                <i class="fal fa-ellipsis-v-alt color-fusion-300"></i>
                            </a>
                            <a href="javascript:void(0);" class="btn btn-icon fs-xl mr-1" data-toggle="tooltip" data-original-title="Attach files" data-placement="top">
                                <i class="fal fa-paperclip color-fusion-300"></i>
                            </a>
                            <a href="javascript:void(0);" class="btn btn-icon fs-xl mr-1" data-toggle="tooltip" data-original-title="Insert photo" data-placement="top">
                                <i class="fal fa-camera color-fusion-300"></i>
                            </a>
                            <div class="ml-auto">
                                <a href="javascript:void(0);" class="btn btn-info" onclick="sendMessage()">送出</a>
                            </div>
                        </div>
                    </div>
                    <!-- END msgr__chatinput -->
                </div>
                <!-- END msgr -->
            </div>
        </div>
    </div>
</div>


<script>

	var ChatMyPoint = "/ChatServlet/${sessionScope.userVO.userNo}"
	var chatHost = window.location.host;
	var chatPath = window.location.pathname;
	var chatWebCtx = chatPath.substring(0, chatPath.indexOf('/', 1));
	var chatEndPointURL = "ws://" + window.location.host + chatWebCtx + ChatMyPoint;

	var ChatMyself ="${sessionScope.userVO.userNo}";
	var webSocketForChat;

	function messagerInit(){
		webSocketForChat = new WebSocket(chatEndPointURL);
		
		webSocketForChat.onopen = function(event) {
			console.log("ConnectChat Success!");
		}

		webSocketForChat.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if ("open" === jsonObj.type) {
				refreshFriendStatus(jsonObj);
			} else if ("history" === jsonObj.type){
				$("#chat_container").empty();
				var messages = JSON.parse(jsonObj.message);
				for (var i = 0; i < messages.length; i++) {
					var historyData = JSON.parse(messages[i]);
					var showMsg = historyData.message;
					if(historyData.sender === ChatMyself){
						$("#chat_container").append(`
						<div class="chat-segment chat-segment-sent">
							<div class="chat-message">
                           	 	<p>
                           	 ${'${showMsg}'}
                           	 	</p>
                       	 </div>
                   	 	</div>`);
					}else{
						$("#chat_container").append(`
							<div class="chat-segment chat-segment-get">
								<div class="chat-message">
		                           	<p>
		                           	${'${showMsg}'}
		                           	 </p>
		                        </div>
		                   	 </div>`);
					}
							
				}
				$("#chat_container").scrollTop($("#chat_container").prop('scrollHeight'));
			}else if ("chat" === jsonObj.type){
				var showMsg = jsonObj.message;
				if(jsonObj.sender!==ChatMyself&&jsonObj.sender !== $("#topChatName").attr("value")) return; //防止收訊息者 沒有跟指定對象聊天卻還收到訊息
				if(jsonObj.sender === ChatMyself){
					$("#chat_container").append(`
					<div class="chat-segment chat-segment-sent">
						<div class="chat-message">
                       	 	<p>
                       	 ${'${showMsg}'}
                       	 	</p>
                   	 </div>
               	 	</div>`);
				}else{
					$("#chat_container").append(`
						<div class="chat-segment chat-segment-get">
							<div class="chat-message">
	                           	<p>
	                           	${'${showMsg}'}
	                           	 </p>
	                        </div>
	                   	 </div>`);
				}
				$("#chat_container").scrollTop($("#chat_container").prop('scrollHeight'));
			}else if ("close" === jsonObj.type) {
				refreshFriendStatus(jsonObj);
			}
		}		
	}
	
	function refreshFriendStatus(jsonObj){
		if("close" === jsonObj.type){ //改變下線使用者的狀態
			var friendsValue=jsonObj.user;
			var friendsName=$("a[value="+friendsValue+"]").attr("name"); //取得離線人的名字，後端設計無傳送其姓名只傳送其編號
			$("#"+friendsValue+"Status").text("Offline"); 
			$("#"+friendsValue+"Status").removeClass("text-success"); //改變其狀態顏色
			$("#"+friendsValue+"StatusDot").removeClass("status-success"); //改變其狀態點點顏色
			$("a[value="+friendsValue+"]").attr("data-filter-tags",friendsName+" offline"); //更改其搜尋的內容
			$("a[value="+friendsValue+"]").parent().insertBefore("#js-msgr-listfilter li:last");//下線將其放置最後(其實是倒數第二)
		}
		
		var onlineList = jsonObj.users;
		var friendsValue;
		var friendsName;
		for (var i = 0; i < onlineList.length; i++){
			if (onlineList[i].userNo === ChatMyself) { continue; }
			friendsValue=onlineList[i].userNo;
			friendsName=onlineList[i].userName;
			$("#"+friendsValue+"Status").text("Online");
			$("#"+friendsValue+"Status").addClass("text-success");
			$("#"+friendsValue+"StatusDot").addClass("status-success")
			$("#"+friendsValue+"StatusDot").parent().attr("data-filter-tags",friendsName+" online");  //更改其搜尋的內容
			$("a[value="+friendsValue+"]").parent().insertBefore("#js-msgr-listfilter li:eq(0)"); //上線將其位置擺第一		
		}
		addListener();
	}//$("a[value=U000009]").parent().insertBefore("#js-msgr-listfilter li:last-child");
		
	function addListener(){//e.srcElement.textContent
		var container = document.getElementById("js-msgr-listfilter");
		container.addEventListener("click", function(e) {
			var friend = ($(e.srcElement).parents(".d-table").attr("name")==undefined?$(e.srcElement).attr("name"):$(e.srcElement).parents(".d-table").attr("name")); 
			var friendVal =($(e.srcElement).parents(".d-table").attr("value")==undefined?$(e.srcElement).attr("value"):$(e.srcElement).parents(".d-table").attr("value"));
			updateFriendName(friend,friendVal);
			var jsonObj = {
					"type" : "history",
					"sender" : ChatMyself,
					"receiver" : friendVal,
					"message" : ""  //此方法對應VO 因此不給空值
				};
			webSocketForChat.send(JSON.stringify(jsonObj));
		});
	}
		
	//與誰對話使用者更新
	function updateFriendName(friend,friendVal) {
		var pic=$("#"+friendVal+"HeadSticker").css("background-image");
		$("#topChatName").html(`<h3>${'${friend}'}</h3>`);
		$("#topChatName").attr("value",friendVal);
		$("#spaceForPic").css("background-image",pic);
	}
	
	//送出訊息
	var sendMessage= function(){
		var message=$("#msgr_input").text().trim();
		var friend=$("#topChatName").attr("value");
		
		if (message === "") {
			Swal.fire("傳送訊息", "請在輸入框輸入訊息?", "question");
			$("#msgr_input").focus();
			return;
		}  
		if (friend == "" || friend == undefined) {
			Swal.fire("傳送訊息", "請選擇聊天對象?", "question");
			return;
		}
			
		var jsonObj = {
			"type" : "chat",
			"sender" : ChatMyself,
			"receiver" : friend,
			"message" : message
		};
		webSocketForChat.send(JSON.stringify(jsonObj));
		$("#msgr_input").text("");
		$("#msgr_input").focus();		
	}
	
	
		
	window.unonload=function() {	
		webSocketForNotify.onclose = function(event) {
			webSocketForChat.close();
		}
	}	
	
</script>

<script> //聊天成員列表
function getAllMessager(){
	
	<jsp:useBean id="empSvcChat" scope="page" class="com.emp.model.EmpService" />
	<jsp:useBean id="studentSvcChat" scope="page" class="com.student.model.StudentService" />
	<jsp:useBean id="teacherSvcChat" scope="page" class="com.teacher.model.TeacherService" />
	<jsp:useBean id="userSvcChat" scope="page" class="com.user.model.UserService" />
	
<c:forEach var="messager" items="${empSvcChat.all}">
	<c:if test="${messager.userNo ne userVO.userNo}">
	
	$("#js-msgr-listfilter").prepend(`<li>
        <a href="javascript:void(0);" class="d-table w-100 px-2 py-2 text-dark hover-white" data-filter-tags="${messager.empName} offline" value="${messager.userNo}" name="${messager.empName}">
        <div class="d-table-cell align-middle status status-sm " id="${messager.userNo}StatusDot">
        	<c:if test="${userSvcChat.getOneUser(messager.userNo).photo != null}" var="condition" scope="page"> 
            	<span class="profile-image-md rounded-circle d-block" style="background-image:url(<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${messager.userNo}); background-size: cover;" id="${messager.userNo}HeadSticker"></span>
			</c:if>
			<c:if test="${condition == false}">
            	<span class="profile-image-md rounded-circle d-block" style="background-image:url(<%=request.getContextPath() %>/images/noPicture.png); background-size: cover;" id="${messager.userNo}HeadSticker"></span>
			</c:if>
        </div>
        <div class="d-table-cell w-100 align-middle pl-2 pr-2">
            <div class="text-truncate text-truncate-md">
            	${messager.empName}
                <small class="d-block font-italic fs-xs" id="${messager.userNo}Status">
                    Offline
                </small>
            </div>
        </div>
    	</a>
	</li>`)
	</c:if>
</c:forEach>

<c:forEach var="messager" items="${teacherSvcChat.all}">
	<c:if test="${messager.userNo ne userVO.userNo}">
	
	$("#js-msgr-listfilter").prepend(`<li>
        <a href="javascript:void(0);" class="d-table w-100 px-2 py-2 text-dark hover-white" data-filter-tags="${messager.teacherName} offline" value="${messager.userNo}" name="${messager.teacherName}">
        <div class="d-table-cell align-middle status status-sm " id="${messager.userNo}StatusDot">
    		<c:if test="${userSvcChat.getOneUser(messager.userNo).photo != null}" var="condition" scope="page"> 
    			<span class="profile-image-md rounded-circle d-block" style="background-image:url(<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${messager.userNo}); background-size: cover;" id="${messager.userNo}HeadSticker"></span>
			</c:if>
			<c:if test="${condition == false}">
    			<span class="profile-image-md rounded-circle d-block" style="background-image:url(<%=request.getContextPath() %>/images/noPicture.png); background-size: cover;" id="${messager.userNo}HeadSticker"></span>
			</c:if>
        </div>
        <div class="d-table-cell w-100 align-middle pl-2 pr-2">
            <div class="text-truncate text-truncate-md">
            	${messager.teacherName}
                <small class="d-block font-italic fs-xs" id="${messager.userNo}Status">
                    Offline
                </small>
            </div>
        </div>
    	</a>
	</li>`)
	</c:if>
</c:forEach>

<c:forEach var="messager" items="${studentSvcChat.all}">
	<c:if test="${messager.userNo ne userVO.userNo}">
	
		$("#js-msgr-listfilter").prepend(`<li>
            <a href="javascript:void(0);" class="d-table w-100 px-2 py-2 text-dark hover-white" data-filter-tags="${messager.studentName} offline" value="${messager.userNo}" name="${messager.studentName}">
            <div class="d-table-cell align-middle status status-sm " id="${messager.userNo}StatusDot">
        		<c:if test="${userSvcChat.getOneUser(messager.userNo).photo != null}" var="condition" scope="page"> 
        			<span class="profile-image-md rounded-circle d-block" style="background-image:url(<%=request.getContextPath() %>/user.do?action=getPhoto&userNo=${messager.userNo}); background-size: cover;" id="${messager.userNo}HeadSticker"></span>
				</c:if>
				<c:if test="${condition == false}">
        			<span class="profile-image-md rounded-circle d-block" style="background-image:url(<%=request.getContextPath() %>/images/noPicture.png); background-size: cover;" id="${messager.userNo}HeadSticker"></span>
				</c:if>
            </div>
            <div class="d-table-cell w-100 align-middle pl-2 pr-2">
                <div class="text-truncate text-truncate-md">
                	${messager.studentName}
                    <small class="d-block font-italic fs-xs" id="${messager.userNo}Status">
                        Offline
                    </small>
                </div>
            </div>
        	</a>
    	</li>`)
	</c:if>
</c:forEach>

}
</script>