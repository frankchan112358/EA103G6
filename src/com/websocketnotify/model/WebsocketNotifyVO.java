package com.websocketnotify.model;

public class WebsocketNotifyVO implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String content;
	private Long time;
	
	public WebsocketNotifyVO(String title,String content,Long time) {
		super();
		this.title=title;
		this.content=content;
		this.time=time;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}
