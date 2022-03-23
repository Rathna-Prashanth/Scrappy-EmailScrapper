package io.prashanth.scrappy.extensions;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelWrite {
	public static Boolean Entry = true;

	public void WrtiteData(String EmailID,String Titles, int Row) throws InterruptedException {

		if (Entry) {
			try {
				Entry = false;
				FileInputStream file = new FileInputStream(
						new File(System.getProperty("user.dir") + "/OutputSheet/Done.xlsx"));

				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheetAt(0);
				Cell cell = null;

				cell = sheet.getRow(Row).getCell(3);
				cell.setCellValue(EmailID);
				
				cell = sheet.getRow(Row).getCell(4);
				cell.setCellValue(Titles);

//			cell = sheet.getRow(0).getCell(5);
//			cell.setCellValue(5);

//			Row row = sheet.getRow(0);
//			row.createCell(3).setCellValue("Value 2");

				file.close();

				FileOutputStream outFile = new FileOutputStream(
						new File(System.getProperty("user.dir") + "/OutputSheet/Done.xlsx"));
				workbook.write(outFile);
				outFile.close();
				Entry = true;

			} catch (Exception e) {
				System.out.println(e);
				Thread.sleep(7000);
				WrtiteData(EmailID,Titles, Row);
			}
		} else {
			while (!Entry) {
				System.out.println("-------------- Waiting for Excel Write 1--------");
				Thread.sleep(7000);
				WrtiteData(EmailID,Titles, Row);
			}
		}
	}

	public void writeDataSecond(String EmailID, int Row) throws InterruptedException {

		if (Entry) {
			try {
				Entry = false;
				FileInputStream file = new FileInputStream(
						new File(System.getProperty("user.dir") + "/OutputSheet/Done.xlsx"));

				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheetAt(0);
				Cell cell = null;

				cell = sheet.getRow(Row).getCell(3);
				cell.setCellValue(EmailID);

//			cell = sheet.getRow(0).getCell(5);
//			cell.setCellValue(5);

//			Row row = sheet.getRow(0);
//			row.createCell(3).setCellValue("Value 2");

				file.close();

				FileOutputStream outFile = new FileOutputStream(
						new File(System.getProperty("user.dir") + "/OutputSheet/Done.xlsx"));
				workbook.write(outFile);
				outFile.close();
				Entry = true;

			} catch (Exception e) {
				System.out.println(e);
				Thread.sleep(7000);
				writeDataSecond(EmailID, Row);
			}
		} else {
			while (!Entry) {
				System.out.println("-------------- Waiting for Excel Write 2--------");
				Thread.sleep(7000);
				writeDataSecond(EmailID, Row);
			}
		}
	}
}
