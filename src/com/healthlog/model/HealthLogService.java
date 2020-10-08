package com.healthlog.model;

import java.sql.Date;
import java.util.List;

public class HealthLogService {
	private HealthLogDAO_interface dao;

	public HealthLogService() {
		dao = new HealthLogJDBCDAO();
	}

	public HealthLogVO addHealthLog(String studentNo, Integer status, Date healthLogDate) {
		HealthLogVO healthLogVO = new HealthLogVO();
		healthLogVO.setStudentNo(studentNo);
		healthLogVO.setStatus(status);
		healthLogVO.setHealthLogDate(healthLogDate);
		dao.insert(healthLogVO);
		return healthLogVO;
	}

	public HealthLogVO updateHealthLog(String healthLogNo, String studentNo, Integer status, Date healthLogDate) {
		HealthLogVO healthLogVO = new HealthLogVO();
		healthLogVO.setHealthLogNo(healthLogNo);
		healthLogVO.setStudentNo(studentNo);
		healthLogVO.setStatus(status);
		healthLogVO.setHealthLogDate(healthLogDate);
		dao.update(healthLogVO);
		return healthLogVO;
	}

	public void deleteHealthLog(String healthLogNo) {
		dao.delete(healthLogNo);
	}

	public HealthLogVO getOneHealthLog(String healthLogNo) {
		return dao.findByPrimaryKey(healthLogNo);
	}

	public List<HealthLogVO> getAll() {
		return dao.getAll();
	}
}
