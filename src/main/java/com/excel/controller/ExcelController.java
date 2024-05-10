package com.excel.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excel.service.ExcelService;

@CrossOrigin
@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 100)
    public void sendDataAutomatically() {
        List<String[]> excelData = excelService.getData();
        if (!excelData.isEmpty()) {
            messagingTemplate.convertAndSend("/topic/excelData", excelData);
            System.out.println("ExcelData: " + Arrays.deepToString(excelData.toArray()));
        }
        
        int rowCount = excelData.size();
        if (rowCount > 0) {
            messagingTemplate.convertAndSend("/topic/count", rowCount);
            System.out.println("Number of Excel Data Rows: " + rowCount);
        }
    }
}