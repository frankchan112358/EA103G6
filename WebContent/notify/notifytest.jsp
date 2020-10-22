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
			
			console.log(Array.isArray(jsonObj))
			if(Array.isArray(jsonObj)){
				if(jsonObj !== null){
					for(let i=0;i<jsonObj.length;i++){
						var detailJson =JSON.parse(jsonObj[i])
						$("#notifySpace").prepend("<div>"+detailJson.content+"</div>");
						
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