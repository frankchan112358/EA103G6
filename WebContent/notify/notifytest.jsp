<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>測試提醒</title>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body onload="connect();" onunload="disconnect();">


	<div id="notifySpace"></div>
</body>
<script>
	var MyPoint = "/NotifyServlet/U000002";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	var webSocket;
	
	function connect(){
		webSocket = new WebSocket(endPointURL);
		
		webSocket.onopen = function(event){
			console.log("Connect Success!");
		}
		
		
		
		webSocket.onmessage = function(event) {			
			var jsonObj = JSON.parse(event.data);
			var dateForNow=new Date().getTime();
			
			
			if(Array.isArray(jsonObj)){
				if(jsonObj !== null){
					for(let i=0;i<jsonObj.length;i++){
						var detailJson =JSON.parse(jsonObj[i])
						var a=detailJson.content;
								
						//進行時間處理
						var countTimeForShow=detailJson.time
						var dateForLong=dateForNow-countTimeForShow;
						var showNotifyDay;
					
						if(dateForLong <= (1000*60*60)){ //時間短於一小時
							showNotifyDay=new Date(mitime).getMinutes();
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
						
						//時間處理結束
						
						
						$("#notifySpace").prepend(`<div>${'${showNotifyDay}'}</div>`);
						
					}
				}
			}else{
				if(jsonObj !== null){
					$("#notifySpace").prepend("<div>"+jsonObj.content+"</div>");
					alert("及時提醒 "+jsonObj.content);
				}		
			}						
		}
	
			
			
		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		}
			
	}				
	
	function disconnect() {
		webSocket.close();
	}
		
	
	
	
</script>

</html>