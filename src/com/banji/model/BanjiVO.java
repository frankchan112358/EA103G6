package com.banji.model;

import java.sql.Date;
import java.util.List;

import com.banjitype.model.BanjiTypeService;
import com.banjitype.model.BanjiTypeVO;
import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.student.model.StudentService;
import com.student.model.StudentVO;
import com.timetable.model.TimetableService;
import com.timetable.model.TimetableVO;

public class BanjiVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String banjiNo;
	private String empNo;
	private String banjiTypeNo;
	private Date startDay;
	private Date endDay;
	private String banjiName;
	private Integer classHours;
	private Integer numberOfStudent;
	private String classroomNo;
	private Integer status;
	private String banjiContent;

	public BanjiVO() {
	}

	public String getBanjiNo() {
		return banjiNo;
	}

	public void setBanjiNo(String banjiNo) {
		this.banjiNo = banjiNo;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getBanjiTypeNo() {
		return banjiTypeNo;
	}

	public void setBanjiTypeNo(String banjiTypeNo) {
		this.banjiTypeNo = banjiTypeNo;
	}

	public Date getStartDay() {
		return startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public String getBanjiName() {
		return banjiName;
	}

	public void setBanjiName(String banjiName) {
		this.banjiName = banjiName;
	}

	public Integer getClassHours() {
		return classHours;
	}

	public void setClassHours(Integer classHours) {
		this.classHours = classHours;
	}

	public Integer getNumberOfStudent() {
		return numberOfStudent;
	}

	public void setNumberOfStudent(Integer numberOfStudent) {
		this.numberOfStudent = numberOfStudent;
	}

	public String getClassroomNo() {
		return classroomNo;
	}

	public void setClassroomNo(String classroomNo) {
		this.classroomNo = classroomNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBanjiContent() {
		return banjiContent;
	}

	public void setBanjiContent(String banjiContent) {
		this.banjiContent = banjiContent;
	}

	public List<StudentVO> getStudentList() {
		return new StudentService().getAllWithBanji(banjiNo);
	}

	public BanjiTypeVO getBanjiTypeVO() {
		return new BanjiTypeService().getOneBanjiType(this.banjiTypeNo);
	}

	public List<CourseVO> getCourseList() {
		return new CourseService().getAllWithBanjiNo(this.banjiNo);
	}

	public List<TimetableVO> getTimetableList() {
		return new TimetableService().getAllWithBanjiNo(this.banjiNo);
	}
}
