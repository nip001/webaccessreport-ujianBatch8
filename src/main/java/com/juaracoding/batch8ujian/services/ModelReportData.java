package com.juaracoding.batch8ujian.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juaracoding.batch8ujian.entity.ReportData;
import com.juaracoding.batch8ujian.repository.ReportDataRepository;

@Service
public class ModelReportData implements ReportDataInterface{
	@Autowired
	ReportDataRepository reportRepo;
	@Override
	public ReportData addReportData(ReportData data) {
		// TODO Auto-generated method stub
		return this.reportRepo.save(data);
	}
	@Override
	public List<ReportData> getAllReportData() {
		// TODO Auto-generated method stub
		return (List<ReportData>) this.reportRepo.findAll();
	}
	@Override
	public List<ReportData> getReportDataResponse() {
		// TODO Auto-generated method stub
		return this.reportRepo.findStatusResponse();
	}
	@Override
	public List<ReportData> getReportDataProses() {
		// TODO Auto-generated method stub
		return this.reportRepo.findStatusNull();
	}
	@Override
	public ReportData getReportByID(String id) {
		// TODO Auto-generated method stub
		return this.reportRepo.findById(Long.parseLong(id)).get();
	}
	
}
