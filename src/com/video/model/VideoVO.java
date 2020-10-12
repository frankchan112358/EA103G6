package com.video.model;

public class VideoVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String videoNo;
	private String timetableNo;
	private String videoName;
	private String video;
	
	public VideoVO() {}
	
	public String getVideoNo() {
		return videoNo;
	}
	public void setVideoNo(String videoNo) {
		this.videoNo = videoNo;
	}
	public String getTimetableNo() {
		return timetableNo;
	}
	public void setTimetableNo(String timetableNo) {
		this.timetableNo = timetableNo;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
		
}
