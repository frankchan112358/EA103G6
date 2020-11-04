	var notifyHost = window.location.host;
	var notifyPath = window.location.pathname;
	var notifyWebCtx = notifyPath.substring(0, notifyPath.indexOf('/', 1));
	var notifyEndPointURL = "ws://" + window.location.host + notifyWebCtx + NotifyMyPoint;
	var webSocketForNotify;
	
	$(document).ready(function (){
		webSocketForNotify = new WebSocket(notifyEndPointURL);
		
		webSocketForNotify.onopen = function(event){
			console.log("Connect Success!");
		}
		
		
		
		webSocketForNotify.onmessage = function(event) {			
			var jsonObj = JSON.parse(event.data);
			var dateForNow=new Date().getTime();
			var noReadCount=0;
			if(Array.isArray(jsonObj)){
				if(jsonObj !== null){
					for(let i=0;i<jsonObj.length;i++){	
						
						var dateForNow=new Date().getTime();
						var detailJson =JSON.parse(jsonObj[i]);
						var jsonObjForName=jsonObj[i];
						var notifyTitle=detailJson.title;
						var notifyContent=detailJson.content;
						var countTimeForShow=detailJson.time;
						
						//進行時間處理，僅適用於 GMT+的地方
						var dateForLong=dateForNow-countTimeForShow;
						var timeZone=new Date().getTimezoneOffset();
						var showNotifyDay;
					
						if(dateForLong <= (1000*60*60)){ //時間短於一小時
							showNotifyDay=new Date(dateForLong).getMinutes();							
							if(showNotifyDay<3){
								showNotifyDay="剛剛"
							}else{
								showNotifyDay=showNotifyDay+"分鐘前";
							}		
							
						}else if(dateForLong <= (1000*60*60*24)){ //時間短於一天
							
							if(new Date(dateForLong).getDate()==2){
								showNotifyDay=new Date(dateForLong).getHours()+24+(timeZone/60)+"小時前";	//取得小時會自動時區換算成當地時間顧會發生錯誤							
							}else { 
								showNotifyDay=new Date(dateForLong).getHours();							
								showNotifyDay=showNotifyDay+(timeZone/60);     //小時要忽略時區故要計算
								showNotifyDay=showNotifyDay+"小時前";
							}
							
						}else if(dateForLong <= (1000*60*60*24*30)){ //時間短於三十天
							
							var minusTimeZoneDate=dateForLong+(timeZone*60*1000);
							showNotifyDay=new Date(minusTimeZoneDate).getDate()-1+"天前";
							
						}else{ //時間長於一個月
							showNotifyDay="一個月前";
						}
						
						//時間處理結束，僅適用於 GMT+的地方
						
						
						if("已讀"==detailJson.status){
							$("#decorateForNotification").prepend(`<li class="unread" name=${jsonObjForName}>
		                            <div class="d-flex align-items-center show-child-on-hover">
		                            <span class="d-flex flex-column flex-1">
		                                <span class="name d-flex align-items-center"> ${notifyTitle} <span class="badge badge-success fw-n ml-1">NOTIFY</span></span>
		                                <span class="msg-a fs-sm">
		                                   ${notifyContent}
		                                </span>
		                                <span class="fs-nano text-muted mt-1">${showNotifyDay}</span>
		                            </span>
		                        </div>
		                    </li>`);
						}
						
						if("未讀"==detailJson.status){
							$("#decorateForNotification_noRead").prepend(`<li class="unread" name=${jsonObjForName}>
		                            <div class="d-flex align-items-center show-child-on-hover">
		                            <span class="d-flex flex-column flex-1">
		                                <span class="name d-flex align-items-center"> ${notifyTitle} <span class="badge badge-success fw-n ml-1">NOTIFY</span></span>
		                                <span class="msg-a fs-sm">
		                                   ${notifyContent}
		                                </span>
		                                <span class="fs-nano text-muted mt-1">${showNotifyDay}</span>
		                            </span>
		                        </div>
		                    </li>`);
							noReadCount++;
						}
						
					}
					$("#noReadPic").text(noReadCount);
				}
			}else{
				if(jsonObj !== null){ 

					var dateForNow=new Date().getTime();				
					var notifyTitle=jsonObj.title;
					var notifyContent=jsonObj.content;
					var countTimeForShow="剛剛";
					var noReadCount;					
										
					$("#decorateForNotification").prepend(`<li class="unread" name=${event.data}>
                            <div class="d-flex align-items-center show-child-on-hover">
                            <span class="d-flex flex-column flex-1">
                                <span class="name d-flex align-items-center"> ${notifyTitle} <span class="badge badge-success fw-n ml-1">NOTIFY</span></span>
                                <span class="msg-a fs-sm">
                                  ${notifyContent}
                                </span>
                                <span class="fs-nano text-muted mt-1">${countTimeForShow}</span>
                            </span>
                        </div>
                    </li>`);
					noReadCount=$("#noReadPic").text()+1;
					$("#noReadPic").text("noReadCount");

                    Swal.fire(
                    {
                        position: "top-end",
                        type: "success",
                        title: "您有一則新通知",
                        showConfirmButton: false,
                        timer: 3000
                    });
					
					
			                
				}		
			}						
		}	
	});				
	
	
	window.unonload=function() {	
		webSocketForNotify.onclose = function(event) {
			webSocketForNotify.close();
		}
	}	
	
	
	$(document).ready(function (){
		$("#decorateForNotification_noRead").on('click',".unread", function(event) {
			var noReadCount=parseInt($("#noReadPic").text());
			webSocketForNotify.send($(this).attr("name"));
			$(this).insertBefore("#decorateForNotification li:eq(0)");
			noReadCount=noReadCount-1;
			$("#noReadPic").text(noReadCount.toString());
		});
		
		$("#decorateForNotification").on('click',".unread", function(event) {
			
			webSocketForNotify.send($(this).attr("name"));
			$(this).insertBefore("#decorateForNotification li:eq(0)");
		});
		
	
	});