package com.juaracoding.batch8ujian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.juaracoding.batch8ujian.entity.ReportData;

public interface ReportDataRepository extends CrudRepository<ReportData, Long>{
	@Query(value = "select * from reportdata where status is not null",nativeQuery=true)
	public List<ReportData> findStatusResponse(); 

	@Query(value = "select * from reportdata where status is null",nativeQuery=true)
	public List<ReportData> findStatusNull(); 
}
