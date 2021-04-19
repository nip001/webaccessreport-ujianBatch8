package com.juaracoding.batch8ujian.services;

import java.util.List;
import java.util.Optional;

import com.juaracoding.batch8ujian.entity.ReportData;



public interface ReportDataInterface {

	public ReportData addReportData(ReportData data);
	public List<ReportData> getAllReportData();
	public List<ReportData> getReportDataResponse();
	public List<ReportData> getReportDataProses();
	public ReportData getReportByID(String id);
}
