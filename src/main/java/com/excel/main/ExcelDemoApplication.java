package com.excel.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import com.excel.service.ExcelService;

@SpringBootApplication
@ComponentScan("com.excel")
public class ExcelDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ExcelDemoApplication.class, args);
		ExcelService service = context.getBean(ExcelService.class);
		System.out.println("Excel Data: " + service.getData());
	}
}













//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
//
//@SpringBootApplication
//@ComponentScan("com.excel")
//public class ExcelDemoApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(ExcelDemoApplication.class, args);
//	}
//
//}
