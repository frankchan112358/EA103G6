package com.teachingfile.model;

import java.util.List;

public class TeachingFileService {
	
	private TeachingFileDAO_interface dao;
	
	public TeachingFileService() {
		dao = new TeachingFileDAO();
	}
	
	public TeachingFileVO addTeachingFile( String timetableNo, String teachingFileName,	byte[] teachingFile) {

		TeachingFileVO teachingFileVO = new TeachingFileVO();

		teachingFileVO.setTimetableNo(timetableNo);
		teachingFileVO.setTeachingFileName(teachingFileName);
		teachingFileVO.setTeachingFile(teachingFile);
		dao.insert(teachingFileVO);

		return teachingFileVO;
	}

	public TeachingFileVO updateTeachingFile(String teachingFileNo, String timetableNo, String teachingFileName,
			byte[] teachingFile) {

		TeachingFileVO teachingFileVO = new TeachingFileVO();

		teachingFileVO.setTeachingFileNo(teachingFileNo);
		teachingFileVO.setTimetableNo(timetableNo);
		teachingFileVO.setTeachingFileName(teachingFileName);
		teachingFileVO.setTeachingFile(teachingFile);
		dao.update(teachingFileVO);

		return teachingFileVO;
	}
	
	public TeachingFileVO updateTeachingFileNOFILE(String teachingFileNo, String timetableNo, String teachingFileName) {

		TeachingFileVO teachingFileVO = new TeachingFileVO();

		teachingFileVO.setTeachingFileNo(teachingFileNo);
		teachingFileVO.setTimetableNo(timetableNo);
		teachingFileVO.setTeachingFileName(teachingFileName);
		dao.updateNoFile(teachingFileVO);

		return teachingFileVO;
	}

	public void deleteTeachingFile(String teachingFileNo) {
		dao.delete(teachingFileNo);
	}

	public TeachingFileVO getOneTeachingFile(String teachingFileNo) {
		return dao.findByPrimaryKey(teachingFileNo);
	}

	public List<TeachingFileVO> getAll() {
		return dao.getAll();
	}

}
