package com.juaracoding.batch8ujian.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.juaracoding.batch8ujian.entity.ReportData;
import com.juaracoding.batch8ujian.security.CustomUserDetail;
import com.juaracoding.batch8ujian.services.ModelReportData;
import com.juaracoding.batch8ujian.services.ModelUserData;
import com.juaracoding.batch8ujian.utility.FileUtility;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ReportDataController {
	@Autowired
	ModelReportData modReport;
	@Autowired
	ModelUserData modUser;

	
	@GetMapping("/report/dashboard")
	public String viewDashboard(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetail customUser = (CustomUserDetail)authentication.getPrincipal();
		Long userId = customUser.getIdUser();

		model.addAttribute("jmlhLaporan", modReport.getAllReportData().size());
		model.addAttribute("datauser", modUser.getUserByID(userId));
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
	
	@GetMapping("/report/pdf")
	public String exportPDF() {
		try {
		File file = ResourceUtils.getFile("classpath:report.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		
		List<ReportData> lstReport = modReport.getAllReportData();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstReport);
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy","Juaracoding");
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        String path = "D:\\report.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint,path);
        
       
		
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return "redirect:/report/view";
		
				
	}
}
