	var chatHost = window.location.host;
	var chatPath = window.location.pathname;
	var chatWebCtx = chatPath.substring(0, chatPath.indexOf('/', 1));
	var chatEndPointURL = "ws://" + window.location.host + chatWebCtx + ChatMyPoint;
	var webSocketForChat;
	