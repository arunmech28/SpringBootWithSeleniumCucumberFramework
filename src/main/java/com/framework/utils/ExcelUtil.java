package com.framework.utils;

import com.framework.constants.FrameworkConstants;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelUtil {

    public FileInputStream fis;

    public XSSFWorkbook wb;

    public XSSFSheet sh;

    List<String> scenarios;

    public ExcelUtil() throws Throwable {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/RunManager.xlsx");
        FileInputStream fis = new FileInputStream(file);
        wb = new XSSFWorkbook(fis);
        scenarios = getScenarioIds(wb);
    }

    public List<String> getScenarioIds(XSSFWorkbook wb) {

        XSSFSheet sh = wb.getSheet("Runner");
        int row = sh.getLastRowNum();
        List<String> scenarioIDs = new ArrayList<String>();
        for (int i = 1; i < row; i++) {
            if (sh.getRow(i).getCell(3).getStringCellValue().equalsIgnoreCase("Y")) {
                scenarioIDs.add(sh.getRow(i).getCell(1).getStringCellValue());
            }
        }

        return scenarioIDs;
    }

    public Map<String, Map<String, String>> getAllScenarioData() {

        Map<String, Map<String, String>> datas = new HashMap<String, Map<String, String>>();
        XSSFSheet sh = wb.getSheet("TestData");
        int row = sh.getLastRowNum();
        int col = sh.getRow(1).getLastCellNum();
        for (int i = 1; i < row; i++) {
            String scenarioName = sh.getRow(i).getCell(0).getStringCellValue();
            int iteration = Integer.parseInt(sh.getRow(i).getCell(1).getStringCellValue());
            if (scenarios.contains(scenarioName)) {
                if (iteration == 1) {
                    datas.put(scenarioName, getScenarioData(sh, i, col));
                } else {
                    datas.put(scenarioName + "-iteration" + iteration, getScenarioData(sh, i, col));
                }
            }
        }

        return datas;

    }

    public Map<String, String> getScenarioData(XSSFSheet sh, int rownum, int totalcolumns) {

        Map<String, String> currentScenarioData = new HashMap<String, String>();

        for (int col = 2; col < totalcolumns; col++) {
            String colName = sh.getRow(1).getCell(col).getStringCellValue();
            String colData = sh.getRow(rownum).getCell(col).getStringCellValue();
            currentScenarioData.put(colName, colData);
        }

        return currentScenarioData;

    }

    public static HashMap<String, String> getCurrentScenarioData(String scenarioName, String iteration) throws IOException {

        HashMap<String, String> currentScenarioData = new HashMap<String, String>();
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/RunManager.xlsx");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sh = wb.getSheet("TestData");
        int row = sh.getLastRowNum();
        int totalcolumns = sh.getRow(0).getLastCellNum();
        for (int i = 1; i <= row; i++) {
            if (sh.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase(scenarioName)
                    && sh.getRow(i).getCell(1).getStringCellValue().equalsIgnoreCase(iteration)) {
                for (int col = 3; col < totalcolumns; col++) {
                    String colName = sh.getRow(0).getCell(col).getStringCellValue();
                    String colData = sh.getRow(i).getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().isEmpty() ? "": sh.getRow(i).getCell(col).getStringCellValue();
                    Boolean isStepIterationEmpty = sh.getRow(i).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().isEmpty();
                    if (isStepIterationEmpty || sh.getRow(i).getCell(2).getStringCellValue().equalsIgnoreCase("1")){
                        currentScenarioData.put(colName, colData);
                    } else {
                        currentScenarioData.put(colName + sh.getRow(i).getCell(2).getStringCellValue(), colData);
                    }
                }
            }
        }
        wb.close();
        return currentScenarioData;

    }

//    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("Feature Name", "feature1");
//        map.put("Scenario Name", "scenario1");
//        map.put("Status", "PASSED");
//        map.put("Error", "");
//        updateConsolidatedReport(map);
//
//    }

    public static void createNewFile() {
        File file = new File(FrameworkConstants.CONSOLIDATEDREPORTFILE);
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            wb.createSheet("Report");
            XSSFSheet sh = wb.getSheet("Report");
            updateHeaders(sh);
            FileOutputStream outFile = null;
            outFile = new FileOutputStream(file);
            wb.write(outFile);
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void updateConsolidatedReport(Map<String, String> updateData) {
        File directory = new File(FrameworkConstants.CONSOLIDATEDREPORTDIRECTORY);
        File file = new File(FrameworkConstants.CONSOLIDATEDREPORTFILE);
        XSSFWorkbook wb = null;
        XSSFSheet sh = null;
        try {
            FileInputStream fis;
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
                wb = new XSSFWorkbook();
                wb.createSheet("Report");
            } else {
                fis = new FileInputStream(file);
                wb = new XSSFWorkbook(fis);
                fis.close();
            }
            if (isSheetPresent(wb, "Sheet1")) {
                wb.removeSheetAt(wb.getSheetIndex("Sheet1"));
            }
            if (!isSheetPresent(wb, "Report")) {
                wb.createSheet("Report");
            }
            sh = wb.getSheet("Report");
            if (sh.getLastRowNum() <= 0) {
                updateHeaders(sh);
            }

            sh = updateScenarioData(updateData, sh, wb);

            FileOutputStream outFile = new FileOutputStream(file);
            wb.write(outFile);
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean isSheetPresent(XSSFWorkbook wb, String sheetName) {
        return wb.getSheetIndex(sheetName) != -1;
    }

    public static XSSFSheet getSheet() {
        File file = new File(
                System.getProperty("user.dir") + "\\test-output\\consolidated-reports\\Consolidated_Report.xlsx");
        XSSFWorkbook wb = null;
        XSSFSheet sh = null;
        try {
            FileInputStream fis;
            if (!file.exists()) {
                file.createNewFile();
            }

            fis = new FileInputStream(file);
            wb = new XSSFWorkbook(fis);
            if (isSheetPresent(wb, "Sheet1")) {
                wb.removeSheetAt(wb.getSheetIndex("Sheet1"));
            }
            if (!isSheetPresent(wb, "Report")) {
                wb.createSheet("Report");
            }
            sh = wb.getSheet("Report");
            if (sh.getLastRowNum() <= 0) {
                sh = updateHeaders(sh);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb.getSheet("Report");
    }

    public static XSSFSheet updateHeaders(XSSFSheet sh) {
        sh.createRow(0);
        XSSFCell cell = null;
        cell = sh.getRow(0).createCell(0);
        cell.setCellValue("Scenario Name");
        cell = sh.getRow(0).createCell(1);
        cell.setCellValue("Feature Name");
        cell = sh.getRow(0).createCell(2);
        cell.setCellValue("Iteration");
        cell = sh.getRow(0).createCell(3);
        cell.setCellValue("Status");
        cell = sh.getRow(0).createCell(4);
        cell.setCellValue("Error");

        return sh;
    }

    public static XSSFCellStyle getCellStyle(String status) {
        XSSFCellStyle cellStyle = new XSSFCellStyle(0, 0, null, null);
        switch (status) {
            case "PASSED":
                cellStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
                break;

            default:
                break;
        }
        return cellStyle;
    }

    public static XSSFSheet updateScenarioData(Map<String, String> updateData, XSSFSheet sh, XSSFWorkbook wb) {
        int lastRow = sh.getLastRowNum();
        if (lastRow <= 0) {
            sh.createRow(1);
            XSSFCell cell = null;
            cell = sh.getRow(1).createCell(0);
            cell.setCellValue(updateData.get("Scenario Name"));
            cell = sh.getRow(1).createCell(1);
            cell.setCellValue(updateData.get("Feature Name"));
            cell = sh.getRow(1).createCell(2);
            cell.setCellValue("1");
            cell = sh.getRow(1).createCell(3);
            if (updateData.get("Status").equalsIgnoreCase("PASSED")) {
                XSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(cellStyle);
            }
            cell.setCellValue(updateData.get("Status"));
            cell = sh.getRow(1).createCell(4);
            cell.setCellValue(updateData.get("Error"));
            return sh;
        } else {
            for (int i = lastRow; i > 0; i--) {
                if (updateData.get("Scenario Name").equalsIgnoreCase(sh.getRow(i).getCell(0).getStringCellValue())) {
                    int iteration = Integer.parseInt(sh.getRow(i).getCell(2).getStringCellValue()) + 1;
                    if (i == lastRow) {
                        sh.createRow(i + 1);
                        XSSFCell cell = null;
                        cell = sh.getRow(i + 1).createCell(0);
                        cell.setCellValue(updateData.get("Scenario Name"));
                        cell = sh.getRow(i + 1).createCell(1);
                        cell.setCellValue(updateData.get("Feature Name"));
                        cell = sh.getRow(i + 1).createCell(2);
                        cell.setCellValue(String.valueOf(iteration));
                        cell = sh.getRow(i + 1).createCell(3);
                        if (updateData.get("Status").equalsIgnoreCase("PASSED")) {
                            XSSFCellStyle cellStyle = wb.createCellStyle();
                            cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            cell.setCellStyle(cellStyle);
                        }
                        cell.setCellValue(updateData.get("Status"));
                        cell = sh.getRow(i + 1).createCell(4);
                        cell.setCellValue(updateData.get("Error"));
                        return sh;
                    } else {
                        sh.shiftRows(i + 1, lastRow, 1);
                        sh.createRow(i + 1);
                        XSSFCell cell = null;
                        cell = sh.getRow(i + 1).createCell(0);
                        cell.setCellValue(updateData.get("Scenario Name"));
                        cell = sh.getRow(i + 1).createCell(1);
                        cell.setCellValue(updateData.get("Feature Name"));
                        cell = sh.getRow(i + 1).createCell(2);
                        cell.setCellValue(String.valueOf(iteration));
                        if (updateData.get("Status").equalsIgnoreCase("PASSED")) {
                            XSSFCellStyle cellStyle = wb.createCellStyle();
                            cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            cell.setCellStyle(cellStyle);
                        }
                        cell = sh.getRow(i + 1).createCell(3);
                        cell.setCellValue(updateData.get("Status"));
                        cell = sh.getRow(i + 1).createCell(4);
                        cell.setCellValue(updateData.get("Error"));
                        return sh;

                    }
                }
            }
            sh.createRow(lastRow + 1);
            XSSFCell cell = null;
            cell = sh.getRow(lastRow + 1).createCell(0);
            cell.setCellValue(updateData.get("Scenario Name"));
            cell = sh.getRow(lastRow + 1).createCell(1);
            cell.setCellValue(updateData.get("Feature Name"));
            cell = sh.getRow(lastRow + 1).createCell(2);
            cell.setCellValue("1");
            cell = sh.getRow(lastRow + 1).createCell(3);
            if (updateData.get("Status").equalsIgnoreCase("PASSED")) {
                XSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(cellStyle);
            }
            cell.setCellValue(updateData.get("Status"));
            cell = sh.getRow(lastRow + 1).createCell(4);
            cell.setCellValue(updateData.get("Error"));
            return sh;
        }
    }

}
