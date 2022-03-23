package io.prashanth.scrappy.extensions;

import io.prashanth.scrappy.AppEngine;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelRead {

    public static String[][] dataprovider(String SheetName) throws IOException {

        XSSFWorkbook book = new XSSFWorkbook("./Sheet/" + SheetName + ".xlsx");
        XSSFSheet sheet = book.getSheetAt(0);
        int row = sheet.getLastRowNum();
        int coloumn = sheet.getRow(row).getLastCellNum();
        System.out.println(row + " " + coloumn);
        String[][] value = new String[row][coloumn];

        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < coloumn; j++) {
                String data = sheet.getRow(i).getCell(j).getStringCellValue();
                System.out.println(data);
                value[i - 1][j] = data;
            }
        }

        return value;
    }

    public String[] GetExcelData(int Row, int Tcolumn) throws IOException {

        XSSFWorkbook book = new XSSFWorkbook(System.getProperty("user.dir") + "/InputSheet/texas.xlsx");
        XSSFSheet sheet = book.getSheetAt(0);
        int row = sheet.getLastRowNum();
        int coloumn = sheet.getRow(row).getLastCellNum();
        // System.out.println(row + " " + coloumn);
        String[] data = new String[Tcolumn + 1];
        for (int j = 0; j <= Tcolumn; j++) {
            try {
                XSSFCell cell = sheet.getRow(Row).getCell(j);
                // System.out.println(cell.getCellType());
                try {
                    if (cell.getCellType().toString().equals("NUMERIC")) {
                        data[j] = "" + sheet.getRow(Row).getCell(j).getNumericCellValue();
                    } else {
                        data[j] = sheet.getRow(Row).getCell(j).getStringCellValue();
                    }
                } catch (Exception e) {
                    data[j] = "Data Error";
                }
            } catch (Exception e) {
                data[j] = "";
            }
            // System.out.println(data[j]);
        }
        return data;
    }

    public static String[] GetExcelEmailIDs() throws IOException {

        XSSFWorkbook book = new XSSFWorkbook(
                System.getProperty("user.dir") + "/InputSheet/" + AppEngine.config.getProperty("SheetName") + ".xlsx");
        XSSFSheet sheet = book.getSheetAt(0);
        int row = sheet.getLastRowNum();
        int coloumn = sheet.getRow(row).getLastCellNum();
        // System.out.println(row + " " + coloumn);
        String[] data = new String[row + 1];
        for (int j = 1; j <= row; j++) {
            try {
                XSSFCell cell = sheet.getRow(j).getCell(0);
                // System.out.println(cell.getCellType());
                try {
                    if (cell.getCellType().toString().equals("NUMERIC")) {
                        data[j] = "" + sheet.getRow(j).getCell(0).getNumericCellValue();
                    } else {
                        data[j] = sheet.getRow(j).getCell(0).getStringCellValue();
                    }
                } catch (Exception e) {
                    data[j] = "Data Error";
                }
            } catch (Exception e) {
                data[j] = "";
            }
            // System.out.println(data[j]);
        }
        return data;
    }

    public static String[] GetExcelEmailIDs(String SheetName) throws IOException {

        XSSFWorkbook book = new XSSFWorkbook(System.getProperty("user.dir") + "/InputSheet/" + SheetName + ".xlsx");
        XSSFSheet sheet = book.getSheetAt(0);
        int row = sheet.getLastRowNum();
        int coloumn = sheet.getRow(row).getLastCellNum();
        System.out.println(row + " " + coloumn);
        String[] data = new String[row + 1];
        for (int j = 1; j <= row; j++) {
            try {
                XSSFCell cell = sheet.getRow(j).getCell(0);
                // System.out.println(cell.getCellType());
                try {
                    if (cell.getCellType().toString().equals("NUMERIC")) {
                        data[j] = "" + sheet.getRow(j).getCell(0).getNumericCellValue();
                    } else {
                        data[j] = sheet.getRow(j).getCell(0).getStringCellValue();
                    }
                } catch (Exception e) {
                    data[j] = "Data Error";
                }
            } catch (Exception e) {
                data[j] = "";
            }
            // System.out.println(data[j]);
        }
        return data;
    }

    public static String[][] GetNameNCompany() throws IOException {
        XSSFWorkbook book = new XSSFWorkbook(
                System.getProperty("user.dir") + "/InputSheet/" + AppEngine.config.getProperty("SheetName") + ".xlsx");
        XSSFSheet sheet = book.getSheetAt(0);
        int row = sheet.getLastRowNum();
        int coloumn = sheet.getRow(row).getLastCellNum();
        System.out.println(row + " " + coloumn);
        String[][] data = new String[row][3];

        for (int j = 1; j < row; j++) {
            try {
                XSSFCell cell = sheet.getRow(j).getCell(0);
                // System.out.println(cell.getCellType());
                try {
                    data[j][0] = "" + sheet.getRow(j).getCell(0).getNumericCellValue();
                    data[j][1] = sheet.getRow(j).getCell(1).getStringCellValue();

                    data[j][2] = sheet.getRow(j).getCell(2).getStringCellValue();
                } catch (Exception e) {
                    System.out.println(e);
                    data[j][0] = "Data Error";
                    data[j][1] = "#N/A";
                }
            } catch (Exception e) {
                System.out.println(e);
                data[j][0] = "Data Error";
                data[j][1] = "#N/A";
            }
            // System.out.println(data[j]);
        }
        return data;
    }

    public static Map<String, String> GetCompany(String SheetName) throws IOException {
        Map<String, String> data = new HashMap<String, String>();
        XSSFWorkbook book = new XSSFWorkbook(System.getProperty("user.dir") + "/InputSheet/" + SheetName + ".xlsx");
        XSSFSheet sheet = book.getSheetAt(0);
        int row = sheet.getLastRowNum();
        int coloumn = sheet.getRow(row).getLastCellNum();
        System.out.println(row + " " + coloumn);

        for (int j = 1; j <= row; j++) {
            try {
                XSSFCell cell = sheet.getRow(j).getCell(0);
                // System.out.println(cell.getCellType());
                try {
                    data.put("" + sheet.getRow(j).getCell(0).getNumericCellValue(),
                            sheet.getRow(j).getCell(2).getStringCellValue());
                } catch (Exception e) {
                    data.put("Data Error", "#N/A");
                }
            } catch (Exception e) {
                data.put("Data Error", "#N/A");
            }
            // System.out.println(data[j]);
        }
        return data;
    }

    public String[] getTitiles() throws IOException {
        XSSFWorkbook book = new XSSFWorkbook(
                System.getProperty("user.dir") + "/TrainingSet/Titles.xlsx");
        XSSFSheet sheet = book.getSheetAt(0);
        int row = sheet.getLastRowNum();
        int coloumn = sheet.getRow(row).getLastCellNum();
        System.out.println(row + " " + coloumn);
        String[] data = new String[row];

        for (int j = 0; j < row; j++) {
            try {
                XSSFCell cell = sheet.getRow(j).getCell(0);
                // System.out.println(cell.getCellType());
                data[j] = cell.getStringCellValue();
            } catch (Exception e) {
                System.out.println(e);
                data[j] = "Data Error";
                data[j] = "#N/A";
            }
            // System.out.println(data[j]);
        }
        return data;
    }
}
