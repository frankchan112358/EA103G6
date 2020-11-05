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
						
						let dateForNow=new Date().getTime();
						let detailJson =JSON.parse(jsonObj[i]);
						let jsonObjForName=jsonObj[i];
						let notifyTitle=detailJson.title;
						let notifyContent=detailJson.content;
						let countTimeForShow=detailJson.time;
						
						//進行時間處理，僅適用於 GMT+的地方
						let dateForLong=dateForNow-countTimeForShow;
						let timeZone=new Date().getTimezoneOffset();
						let showNotifyDay;
					
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
							
							let minusTimeZoneDate=dateForLong+(timeZone*60*1000);
							showNotifyDay=new Date(minusTimeZoneDate).getDate()-1+"天前";
							
						}else{ //時間長於一個月
							showNotifyDay="一個月前";
						}
						
						//時間處理結束，僅適用於 GMT+的地方
						
						
						if("已讀"==detailJson.status){
							$("#decorateForNotification").prepend(`<li class="unread">
		                            <div class="d-flex align-items-center show-child-on-hover">
		                            <span class="d-flex flex-column flex-1">
		                                <span class="name d-flex align-items-center"> ${notifyTitle} <span class="badge badge-success fw-n ml-1">NOTIFY</span></span>
		                                <span class="msg-a fs-sm">
		                                   ${notifyContent}
		                                </span>
		                                <span class="fs-nano text-muted mt-1">${showNotifyDay}</span>
		                            </span>
		                        </div>
		                        <div class="jsonObjForName" style="display:none">${jsonObjForName}</div>
		                    </li>`);
						}
						
						if("未讀"==detailJson.status){
							$("#decorateForNotification_noRead").prepend(`<li class="unread">
		                            <div class="d-flex align-items-center show-child-on-hover">
		                            <span class="d-flex flex-column flex-1">
		                                <span class="name d-flex align-items-center"> ${notifyTitle} <span class="badge badge-success fw-n ml-1">NOTIFY</span></span>
		                                <span class="msg-a fs-sm">
		                                   ${notifyContent}
		                                </span>
		                                <span class="fs-nano text-muted mt-1">${showNotifyDay}</span>
		                            </span>
		                        </div>
		                        <div class="jsonObjForName" style="display:none">${jsonObjForName}</div>
		                    </li>`);
							noReadCount++;
						}
						
					}
					//當歷史資料有未讀時顯示被隱藏未讀數字
					if(noReadCount!=0){
						$("#noReadPic").text(noReadCount);
						$("#noReadPic").show("fast");
					}
					
				}
			}else{
				if(jsonObj !== null){ 

					let dateForNow=new Date().getTime();				
					let notifyTitle=jsonObj.title;
					let notifyContent=jsonObj.content;
					let countTimeForShow="剛剛";
										
					$("#decorateForNotification_noRead").prepend(`<li class="unread">
                            <div class="d-flex align-items-center show-child-on-hover">
                            <span class="d-flex flex-column flex-1">
                                <span class="name d-flex align-items-center"> ${notifyTitle} <span class="badge badge-success fw-n ml-1">NOTIFY</span></span>
                                <span class="msg-a fs-sm">
                                  ${notifyContent}
                                </span>
                                <span class="fs-nano text-muted mt-1">${countTimeForShow}</span>
                            </span>
                        </div>
                        <div class="jsonObjForName" style="display:none">${jsonObjForName}</div>
                    </li>`);
					//收到即時提醒時未讀加一
					if($("#noReadPic").text()!=0){
						noReadCount=parseInt($("#noReadPic").text())+1;
						$("#noReadPic").text(noReadCount.toString());
					}else{
						noReadCount=1;
						$("#noReadPic").text(noReadCount.toString());
						$("#noReadPic").show("fast");
					}

                    Swal.fire(
                    {
                        position: "top-end",
                        type: "success",
                        width:300,
                        title: "您有一則新通知",
                        showConfirmButton: false,
                        timer: 2000
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
	
	
	$(document).ready(function(){
		$("#noReadPic").hide();
		
		$("#decorateForNotification_noRead").on('click',".unread", function(event) {
			//狀態改已讀右上小鈴鐺數字改變
			var noReadCount=parseInt($("#noReadPic").text());
			webSocketForNotify.send($(this).find('div.jsonObjForName').html());
			$(this).insertBefore("#decorateForNotification li:eq(0)");
			noReadCount=noReadCount-1;
			$("#noReadPic").text(noReadCount.toString());
			
			if(noReadCount===0){ //狀態改已讀導致未讀0則時隱藏
				$("#noReadPic").hide();
			}
		});
		
	
	});