package com.report.model;

import java.util.List;


public interface ReportDAO_interface {
	public void insert(ReportVO reportVO);
    public void update(ReportVO reportVO);
    public void delete(String reportNo);
    public ReportVO findByPrimaryKey(String reportVO);
    public List<ReportVO> getAll();

}
