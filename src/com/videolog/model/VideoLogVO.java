package com.videolog.model;

public class VideoLogVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String videoLogNo;
	private String videoNo;
	private Integer watchTime;
	private Integer status;
	
	public String getVideoLogNo() {
		return videoLogNo;
	}
	public void setVideoLogNo(String videoLogNo) {
		this.videoLogNo = videoLogNo;
	}
	public String getVideoNo() {
		return videoNo;
	}
	public void setVideoNo(String videoNo) {
		this.videoNo = videoNo;
	}
	public Integer getWatchTime() {
		return watchTime;
	}
	public void setWatchTime(Integer watchTime) {
		this.watchTime = watchTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "VideoLogVO [videoLogNo=" + videoLogNo + ", videoNo=" + videoNo + ", watchTime=" + watchTime
				+ ", status=" + status + "]";
	}
	
	
}
