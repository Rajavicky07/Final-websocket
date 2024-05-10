package com.excel.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {

    private String filePath = "C:\\Users\\rajav\\OneDrive\\Documents\\EmployeeSheet.xlsx";
    private List<String[]> previousData = null;

    public List<String[]> getData() {
        List<String[]> data = new ArrayList<>();
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            boolean isFirstRow = true; 
            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false; 
                    continue;
                }
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
            return Collections.emptyList(); 
        }
        if (isDataChanged(data)) {
            previousData = data;
            return data;
        }
        return Collections.emptyList();
    }

    private boolean isDataChanged(List<String[]> newData) {
        if (previousData == null) 
        	return true;
        if (previousData.size() != newData.size()) 
        	return true;
        for (int i = 0; i < previousData.size(); i++) {
            if (!Arrays.equals(previousData.get(i), newData.get(i))) {
                return true;
            }
        }
        return false;
    }

    private String getStringValueFromCell(Cell cell) {
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case BOOLEAN: return Boolean.toString(cell.getBooleanCellValue());
            case NUMERIC:return new DecimalFormat("0").format(cell.getNumericCellValue());
            default: return "";
        }
    }
}