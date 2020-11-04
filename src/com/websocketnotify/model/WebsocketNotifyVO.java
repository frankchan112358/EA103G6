package com.websocketnotify.model;

public class WebsocketNotifyVO implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String content;
	private Long time;
	private String status;
	private Long index;
	

	public WebsocketNotifyVO(String title,String content,Long time,String status,Long index) {
		super();
		this.title=title;
		this.content=content;
		this.time=time;
		this.status=status;
		this.index=index;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}
}
