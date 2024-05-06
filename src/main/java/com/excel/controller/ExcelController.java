//package com.excel.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.web.bind.annotation.*;
//
//import com.excel.service.ExcelService;
//
//import java.util.List;
//
//@RestController
//public class ExcelController {
//
//	@Autowired
//	private ExcelService excelService;
//
//	 @MessageMapping("/hello")
//	 @SendTo("/topic/greetings")
//	public ResponseEntity<List<String[]>> getExcelData() {
//		List<String[]> data = excelService.getData();
//		return ResponseEntity.ok(data);
//	}
//}
