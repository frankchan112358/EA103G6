	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

	var webSocketForNotify;
	
	$(document).ready(function (){
		webSocketForNotify = new WebSocket(endPointURL);
		
		webSocketForNotify.onopen = function(event){
			console.log("Connect Success!");
		}
		
		
		
		webSocketForNotify.onmessage = function(event) {			
			var jsonObj = JSON.parse(event.data);
			var dateForNow=new Date().getTime();
			
			if(Array.isArray(jsonObj)){
				if(jsonObj !== null){
					for(let i=0;i<jsonObj.length;i++){	
						
						var detailJson =JSON.parse(jsonObj[i])						
						var notifyTitle=detailJson.title;
						var notifyContent=detailJson.content;
						var countTimeForShow=detailJson.time;
						var showNotifyDay;
						
						//進行時間處理						
						var dateForLong=dateForNow-countTimeForShow;
											
						if(dateForLong <= (1000*60*60)){ //時間短於一小時
							showNotifyDay=new Date(dateForLong).getMinutes();
							if(showNotifyDay<1){showNotifyDay=1}
							showNotifyDay=showNotifyDay+"分鐘前";
							
						}else if(dateForLong <= (1000*60*60*24)){ //時間短於一天
							
							showNotifyDay=new Date(dateForLong).getHours();
							showNotifyDay=showNotifyDay+(new Date().getTimezoneOffset()/60); //小時要忽略時區故要計算
							showNotifyDay=showNotifyDay+"小時前";
							
						}else if(dateForLong <= (1000*60*60*24*30)){ //時間短於三十天
							showNotifyDay=new Date(dateForLong).getDate()+"天前";
							
						}else{ //時間長於一小時
							showNotifyDay="一個月前";
						}
						
						
						
						
						$("#decorateForNotification").prepend(`<li class="unread">
	                            <div class="d-flex align-items-center show-child-on-hover">
	                            <span class="d-flex flex-column flex-1">
	                                <span class="name d-flex align-items-center"> ${notifyTitle} <span class="badge badge-success fw-n ml-1">UPDATE</span></span>
	                                <span class="msg-a fs-sm">
	                                   ${notifyContent}
	                                </span>
	                                <span class="fs-nano text-muted mt-1">${showNotifyDay}</span>
	                            </span>
	                            <div class="show-on-hover-parent position-absolute pos-right pos-bottom p-3">
	                                <a href="#" class="text-muted" title="delete"><i class="fal fa-trash-alt"></i></a>
	                            </div>
	                        </div>
	                    </li>`);
						
					}
				}
			}else{
				if(jsonObj !== null){ //"<div>"+jsonObj.content+"</div>"
					var dateForNow=new Date().getTime();
					
					var notifyTitle=jsonObj.title;
					var notifyContent=jsonObj.content;
					var countTimeForShow=jsonObj.time;
					var showNotifyDay;
					
					//進行時間處理						
					var dateForLong=dateForNow-countTimeForShow;
										
					if(dateForLong <= (1000*60*60)){ //時間短於一小時
						showNotifyDay=new Date(dateForLong).getMinutes();
						if(showNotifyDay<1){showNotifyDay=1}
						showNotifyDay=showNotifyDay+"分鐘前";
						
					}else if(dateForLong <= (1000*60*60*24)){ //時間短於一天
						
						showNotifyDay=new Date(dateForLong).getHours();
						showNotifyDay=showNotifyDay+(new Date().getTimezoneOffset()/60); //小時要忽略時區故要計算
						showNotifyDay=showNotifyDay+"小時前";
						
					}else if(dateForLong <= (1000*60*60*24*30)){ //時間短於三十天
						showNotifyDay=new Date(dateForLong).getDate()+"天前";
						
					}else{ //時間長於一小時
						showNotifyDay="一個月前";
					}
					
					
					$("#decorateForNotification").prepend(`<li class="unread">
                            <div class="d-flex align-items-center show-child-on-hover">
                            <span class="d-flex flex-column flex-1">
                                <span class="name d-flex align-items-center"> ${notifyTitle} <span class="badge badge-success fw-n ml-1">UPDATE</span></span>
                                <span class="msg-a fs-sm">
                                  ${notifyContent}
                                </span>
                                <span class="fs-nano text-muted mt-1">${showNotifyDay}</span>
                            </span>
                            <div class="show-on-hover-parent position-absolute pos-right pos-bottom p-3">
                                <a href="#" class="text-muted" title="delete"><i class="fal fa-trash-alt"></i></a>
                            </div>
                        </div>
                    </li>`);
					alert("及時提醒 "+jsonObj.content);
				}		
			}						
		}
	
			
	});				
	
	
	window.unonload=function() {	
		webSocketForNotify.onclose = function(event) {
			webSocket.close();
		}
	}	