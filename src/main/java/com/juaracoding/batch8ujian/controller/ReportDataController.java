package com.juaracoding.batch8ujian.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.juaracoding.batch8ujian.entity.ReportData;
import com.juaracoding.batch8ujian.services.ModelReportData;
import com.juaracoding.batch8ujian.utility.FileUtility;

@Controller
public class ReportDataController {
	@Autowired
	ModelReportData modReport;
	
	@GetMapping("/report/dashboard")
	public String viewDashboard(Model model) {
		model.addAttribute("jmlhLaporan", modReport.getAllReportData().size());
		model.addAttribute("jmlhTanggapi", modReport.getReportDataResponse().size());
		model.addAttribute("jmlhProses", modReport.getReportDataProses().size());
		return "dashboard";
	}
	
	@GetMapping("/report/add")
	public String viewAddReport(Model model) {
		model.addAttribute("report", new ReportData());
		model.addAttribute("active", 1);
		return "add_report";
	}
	@PostMapping("/report/add")
	public String addReport(@RequestParam(value="file") MultipartFile file, @ModelAttribute ReportData report, Model model) throws IOException {
		{
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			String uploadDir = "user-photos/";

			FileUtility.saveFile(uploadDir, fileName, file);

			report.setGambar("/" + uploadDir + fileName);
			
		
			this.modReport.addReportData(report);
					
			
			return "redirect:/report/dashboard";
		}
	}
	

	@GetMapping("/report/view")
	public String viewReport(Model model) {
		List<ReportData> reportData = modReport.getAllReportData();
		for (ReportData report : reportData) {
			if(report.getStatus() == null) {
				report.setStatus("Pending");
			}
		}
		model.addAttribute("listreport", reportData);
		return "view_report";
	}
	

	@GetMapping("/report/approved/{id}")
	public String approvedReport(@PathVariable String id ,Model model) {
		ReportData report = modReport.getReportByID(id);
		report.setStatus("Approved");
		this.modReport.addReportData(report);
		return "redirect:/report/view";
	}
	@GetMapping("/report/rejected/{id}")
	public String rejectedReport(@PathVariable String id ,Model model) {
		ReportData report = modReport.getReportByID(id);
		report.setStatus("Rejected");
		this.modReport.addReportData(report);
		return "redirect:/report/view";
	}
}
