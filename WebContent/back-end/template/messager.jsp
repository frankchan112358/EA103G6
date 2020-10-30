<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade js-modal-messenger modal-backdrop-transparent" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-right">
        <div class="modal-content h-100">
            <div class="dropdown-header bg-trans-gradient d-flex align-items-center w-100" style="padding:0px;">
                <div class="d-flex flex-row align-items-center mt-1 mb-1 color-white">
                    <span class="mr-2">
                        <span class="rounded-circle profile-image d-block" style="background-image:url('img/demo/avatars/avatar-d.png'); background-size: cover;"></span>
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
                            <!-- start .chat-segment -->
                            <div class="chat-segment">
                                <div class="time-stamp text-center mb-2 fw-400">
                                    Jun 19
                                </div>
                            </div>
                            <!--  end .chat-segment -->
                            <!-- start .chat-segment -->
                            
                            <!--  end .chat-segment -->
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
			console.log("ConnectChata Success!");
		}

		webSocketForChat.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if ("open" === jsonObj.type) {
				refreshFriendList(jsonObj);
			} else if ("history" === jsonObj.type){
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
				console.log(jsonObj.sender===ChatMyself||jsonObj.sender !== $("#topChatName").attr("value"));
				if(jsonObj.sender!==ChatMyself&&jsonObj.sender !== $("#topChatName").attr("value")) return; //防止收訊息者 沒有跟指定對象聊天卻還收到訊息
				if(jsonObj.sender === ChatMyself){
					console.log(showMsg);
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
				refreshFriendList(jsonObj);
			}
		}		
	}
	
	function refreshFriendList(jsonObj) {
		var onlineList = jsonObj.users;
		var friendsValue;
		var friendsName;
		$("#js-msgr-listfilter").empty();
		for (var i = 0; i < onlineList.length; i++) {
			if (onlineList[i].userNo === ChatMyself) { continue; } //抓到自己的名字叫跳過，不要讓自己的名字出現在畫面
				friendsValue=onlineList[i].userNo;
				friendsName=onlineList[i].userName;
			$("#js-msgr-listfilter").prepend(`<li>
                       <a href="javascript:void(0);" class="d-table w-100 px-2 py-2 text-dark hover-white" data-filter-tags="${'${friendsName}'} online" value="${'${friendsValue}'}" name="${'${friendsName}'}">
                       <div id="test1" class="d-table-cell align-middle status status-success status-sm ">
                           <span class="profile-image-md rounded-circle d-block" style="background-image:url('img/demo/avatars/avatar-d.png'); background-size: cover;"></span>
                       </div>
                       <div class="d-table-cell w-100 align-middle pl-2 pr-2">
                           <div class="text-truncate text-truncate-md">
                           	${'${friendsName}'}
                               <small class="d-block font-italic text-success fs-xs">
                                   Online
                               </small>
                           </div>
                       </div>
                   </a>
               </li>`)
		}
		addListener();
	}
		
	function addListener(){//e.srcElement.textContent
		var container = document.getElementById("js-msgr-listfilter");
		container.addEventListener("click", function(e) {
			var friend = ($(e.srcElement).parents(".d-table").attr("name")==undefined?$(e.srcElement).attr("name"):$(e.srcElement).parents(".d-table").attr("name")); 
			console.log(friend)
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
		
		
	function updateFriendName(friend,friendVal) {
		$("#topChatName").html(`<h3>${'${friend}'}</h3>`);
		$("#topChatName").attr("value",friendVal);
	}
	
	var sendMessage= function(){
		var message=$("#msgr_input").text().trim();
		var friend=$("#topChatName").attr("value");
		
		if (message === "") {
			alert("請輸入訊息");
			$("#msgr_input").focus();
			return;
		}  
		if (friend == "" || friend == undefined) {
			alert("請選擇聊天對象");
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