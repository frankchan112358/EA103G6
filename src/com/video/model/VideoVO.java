package com.video.model;

import java.util.Arrays;

public class VideoVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String videoNo;
	private String timetableNo;
	private String videoName;
	private byte[] video;
	
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
	public byte[] getVideo() {
		return video;
	}
	public void setVideo(byte[] video) {
		this.video = video;
	}
	@Override
	public String toString() {
		return "VideoVO [videoNo=" + videoNo + ", timetableNo=" + timetableNo + ", videoName=" + videoName + ", video="
				+ Arrays.toString(video) + "]";
	}
	
}
