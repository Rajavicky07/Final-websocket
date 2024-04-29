package com.excel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.excel.service.ExcelService;

import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

	@Autowired
	private ExcelService excelService;

	@GetMapping("/getData")
	public ResponseEntity<List<String[]>> getExcelData() {
		List<String[]> data = excelService.getData();
		return ResponseEntity.ok(data);
	}
}
