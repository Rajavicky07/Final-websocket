package com.excel.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private String filePath = "C:\\Users\\rajav\\OneDrive\\Documents\\EmployeeSheet.xlsx";

    @Scheduled(fixedRate = 1000) // Broadcast updates every 5000 ms
    public void updateClients() {
        List<String[]> newData = getData();
        broadcastSheetData(newData);
        broadcastSheetRecordCount(newData.size());
    }

    public List<String[]> getData() {
        return readExcel();
    }   
                  
    private List<String[]> readExcel() {
        List<String[]> data = new ArrayList<>();
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {  
                int lastColumn = row.getLastCellNum();
                String[] rowData = new String[lastColumn];
                for (int col = 0; col < lastColumn; col++) {
                    Cell cell = row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowData[col] = getStringValueFromCell(cell);
                }
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to read Excel file: " + e.getMessage());
            return Collections.emptyList(); // Ensure not returning null
        }
        return data;
    }

    private String getStringValueFromCell(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    return new DecimalFormat("0").format(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private void broadcastSheetData(List<String[]> data) {
        System.out.println("Broadcasting data: " + data);
        messagingTemplate.convertAndSend("/topic/sheetData", data);
    }

    private void broadcastSheetRecordCount(int count) {
        messagingTemplate.convertAndSend("/topic/recordCount", count);
    }
}






//package com.excel.service;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ExcelService {
//
//	@Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    private String filePath = "C:\\Users\\rajav\\OneDrive\\Documents\\EmployeeSheet.xlsx";
//
//    public void updateClients() {
//        List<String[]> newData = getData();
//        broadcastSheetData(newData);
//        broadcastSheetRecordCount(newData.size());
//    }
//
//    public void broadcastSheetData(List<String[]> data) {
//        messagingTemplate.convertAndSend("/topic/sheetData", data);
//    }
//    
//    public void broadcastSheetRecordCount(int count) {
//        messagingTemplate.convertAndSend("/topic/recordCount", count);
//    }
//
//	public List<String[]> getData() {
//		return readExcel();
//	}
//
//	private List<String[]> readExcel() {
//		List<String[]> data = new ArrayList<>();
//		File file = new File(filePath);
//		try (FileInputStream fis = new FileInputStream(file)) {
//			try (Workbook workbook = new XSSFWorkbook(fis)) {
//				Sheet sheet = workbook.getSheetAt(0);
//				for (Row row : sheet) {
//					int lastColumn = row.getLastCellNum();
//					String[] rowData = new String[lastColumn];
//					for (int col = 0; col < lastColumn; col++) {
//						Cell cell = row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//						rowData[col] = getStringValueFromCell(cell);
//					}
//					data.add(rowData);
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.err.println("Failed to read Excel file: " + e.getMessage());
//		}
//		return data;
//	}
//
//	private String getStringValueFromCell(Cell cell) {
//		if (cell == null) {
//			return "";
//		}
//		switch (cell.getCellType()) {
//		case STRING:
//			return cell.getStringCellValue();
//		case NUMERIC:
//			if (DateUtil.isCellDateFormatted(cell)) {
//				return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
//			} else {
//				return new java.text.DecimalFormat("0").format(cell.getNumericCellValue());
//			}
//		case BOOLEAN:
//			return String.valueOf(cell.getBooleanCellValue());
//		case FORMULA:
//			return cell.getCellFormula();
//		default:
//			return "";
//		}
//	}
//}
