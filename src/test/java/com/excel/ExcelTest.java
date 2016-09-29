package com.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;

/**
 * Created by yuwt on 2016/4/25.
 */
public class ExcelTest {

	@Test
	public void test_create_xlsx_from_scratch() {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row headerRow = sheet.createRow(0);

		CellStyle headerRowStyle = headerRow.getRowStyle();
		Font headerRowFont = wb.createFont();
		headerRowFont.setBold(true);

		Cell cell0 = headerRow.createCell(0);
		cell0.setCellType(Cell.CELL_TYPE_STRING);
		cell0.setCellValue("CID");

	}

	@Test
	public void test_max_rows_per_sheet_in_xls() {
		File file = new File("e:\\tmp\\tmp.xls");
		InputStream in = null;
		HSSFWorkbook wb = null;
		try {
			in = new FileInputStream(file);
			wb = new HSSFWorkbook(in);
			HSSFSheet sheet = wb.getSheetAt(0);
			for(int i=0;i<65537;i++) {
				HSSFRow row = sheet.createRow(i);
				HSSFCell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue("abc");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			wb.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void populate_to_excel_from_db() {
		File file = new File("e:\\tmp\\elsevier.xlsx");
		InputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream(file);
			wb = new XSSFWorkbook(in);
			Sheet sheet = wb.getSheetAt(0);
			Row head = sheet.createRow(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_max_rows_per_sheet_in_xlsx() {
		File file = new File("e:\\tmp\\tmp.xlsx");
		InputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream(file);
			wb = new XSSFWorkbook(in);
			Sheet sheet = wb.getSheetAt(0);
			int max = 1<<20;
			for(int i=0;i<max;i++) {
				Row row = sheet.createRow(i);
				Cell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue("abc");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			wb.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
