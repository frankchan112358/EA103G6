package com.banji.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;

public class BanjiService {
	private BanjiDAO_interface dao;

	public BanjiService() {
		dao = new BanjiJNDIDAO();
	}

	public BanjiVO addBanji(String empNo, String banjiTypeNo, java.sql.Date startDay, java.sql.Date endDay,
			String banjiName, Integer classHours, Integer numberOfStudent, String classroomNo, Integer status,
			String banjiContent) {

		BanjiVO banjiVO = new BanjiVO();

		banjiVO.setEmpNo(empNo);
		banjiVO.setBanjiTypeNo(banjiTypeNo);
		banjiVO.setStartDay(startDay);
		banjiVO.setEndDay(endDay);
		banjiVO.setBanjiName(banjiName);
		banjiVO.setClassHours(classHours);
		banjiVO.setNumberOfStudent(numberOfStudent);
		banjiVO.setClassroomNo(classroomNo);
		banjiVO.setStatus(status);
		banjiVO.setBanjiContent(banjiContent);
		dao.insert(banjiVO);

		return banjiVO;

	}

	public BanjiVO updateBanji(String banjiNo, String empNo, String banjiTypeNo, java.sql.Date startDay,
			java.sql.Date endDay, String banjiName, Integer classHours, Integer numberOfStudent, String classroomNo,
			Integer status, String banjiContent) {

		BanjiVO banjiVO = new BanjiVO();

		banjiVO.setBanjiNo(banjiNo);
		banjiVO.setEmpNo(empNo);
		banjiVO.setBanjiTypeNo(banjiTypeNo);
		banjiVO.setStartDay(startDay);
		banjiVO.setEndDay(endDay);
		banjiVO.setBanjiName(banjiName);
		banjiVO.setClassHours(classHours);
		banjiVO.setNumberOfStudent(numberOfStudent);
		banjiVO.setClassroomNo(classroomNo);
		banjiVO.setStatus(status);
		banjiVO.setBanjiContent(banjiContent);
		dao.update(banjiVO);

		return banjiVO;
	}

	public void deleteBanji(String banjiNo) {
		dao.delete(banjiNo);
	}

	public BanjiVO getOneBanji(String banjiNo) {
		return dao.findByPrimaryKey(banjiNo);
	}

	public List<BanjiVO> getAll() {
		return dao.getAll();
	}

	public List<BanjiVO> getAllWithEmp(String empNo) {
		List<BanjiVO> list = new ArrayList<BanjiVO>();
		for (BanjiVO banjiVO : getAll()) {
			if (empNo.equals(banjiVO.getEmpNo()))
				list.add(banjiVO);
		}
		return list;
	}

	public HashMap<String, TreeMap<String,BanjiVO>> getBanjiGroup(String empNo) {
		EmpVO empVO = new EmpService().getOneEmp(empNo);
		HashMap<String, TreeMap<String,BanjiVO>> map = new HashMap<String, TreeMap<String,BanjiVO>>();
		for (BanjiVO banjiVO : empVO.getBanjiList()) {
			Object obj = map.get(banjiVO.getBanjiTypeNo());
			if (obj == null) {
				TreeMap<String,BanjiVO> treeMap = new TreeMap<String,BanjiVO>();
				treeMap.put(banjiVO.getBanjiName(), banjiVO);
				map.put(banjiVO.getBanjiTypeNo(), treeMap);				
			}else {
				TreeMap<String,BanjiVO> treeMap = (TreeMap<String,BanjiVO>)obj;
				treeMap.put(banjiVO.getBanjiName(), banjiVO);
			}
		}
		return map;
	}
}
