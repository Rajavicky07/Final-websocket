package com.excel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.excel.service.ExcelService;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @MessageMapping("/fetchExcel")
    @SendTo("/topic/excelData")
    public List<String[]> sendExcelData() {
        return excelService.getData();
    }

    @Scheduled(fixedRate = 5000)
    public void sendDataAutomatically() {
        sendExcelData();
    }
}
