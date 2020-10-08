package com.teachingfile.model;

public class TeachingFileVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String teachingFileNo;
	private String timetableNo;
	private String teachingFileName;
	private byte[] teachingFile;
	
	public String getTeachingFileNo() {
		return teachingFileNo;
	}
	public void setTeachingFileNo(String teachingFileNo) {
		this.teachingFileNo = teachingFileNo;
	}
	public String getTimetableNo() {
		return timetableNo;
	}
	public void setTimetableNo(String timetableNo) {
		this.timetableNo = timetableNo;
	}
	public String getTeachingFileName() {
		return teachingFileName;
	}
	public void setTeachingFileName(String teachingFileName) {
		this.teachingFileName = teachingFileName;
	}
	public byte[] getTeachingFile() {
		return teachingFile;
	}
	public void setTeachingFile(byte[] teachingFile) {
		this.teachingFile = teachingFile;
	}
	@Override
	public String toString() {
		return String.format("TeachingFileVO [teachingFileNo=%s, timetableNo=%s, teachingFileName=%s, teachingFile=%s]",
				teachingFileNo, timetableNo, teachingFileName, teachingFile != null ? new String(teachingFile) : "null");
	}

}
