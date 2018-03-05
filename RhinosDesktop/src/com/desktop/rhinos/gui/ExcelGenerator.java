package com.desktop.rhinos.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;

import com.android.rhinos.gest.Service;
import com.desktop.rhinos.connector.Connector.App;

public class ExcelGenerator {

	public ExcelGenerator() {
		
		System.out.println("Generando informe excel....");
		ArrayList<ArrayList<Object>> list = App.CONNECTOR.getDatatoExport();		
		generate(list);		
	}
	
	private void generate(ArrayList<ArrayList<Object>> array) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		
		workbook.setSheetName(0, "DATOS");
		
		for (int i = 0; i < array.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < array.get(i).size(); j++) {
				HSSFCell cell = row.createCell(j);
				
				if (i == array.size() -1) //last one *AUTOSIZE*
					sheet.autoSizeColumn(j);
				
				if (i == 0) { // *HEADERS*
					cell.setCellStyle(style);
					cell.setCellValue(array.get(i).get(j).toString());
				}
				else
					switch (j) {
						case 4 : 	cell.setCellValue((double)array.get(i).get(j));
									cell.setCellType(CellType.NUMERIC);
									break;
									
						case 5 : 	String fp="";
									switch ((int)array.get(i).get(j)) {
										case Service.MENSUAL   	: fp = "MENSUAL"; break;
										case Service.BIMENSUAL 	: fp = "BIMENSUAL"; break;
										case Service.TRIMESTRAL	: fp = "TRIMESTRAL"; break;
										case Service.SEMESTRAL 	: fp = "SEMESTRAL"; break;
										case Service.ANUAL 		: fp = "ANUAL";
									}
									cell.setCellValue(fp);
									break;
						
						case 6 :
						case 7 : 	cell.setCellValue(f.format(array.get(i).get(j)));
									break;
						
						case 8 : 	cell.setCellValue(((int)array.get(i).get(j) == 1) ? 1 : 0);
									cell.setCellType(CellType.BOOLEAN);
									break;
									
						case 9 : 	String st="";
									switch ((int)array.get(i).get(j)) {
										case Service.CANCELLED  : st = "ANULADO"; break;
										case Service.PENDING 	: st = "PENDIENTE"; break;
										case Service.RETURNED	: st = "DEVUELTO"; break;
										case Service.VERIFIED 	: st = "VIGOR";
									}
									cell.setCellValue(st);
									break; 	
						
						default: 	cell.setCellValue(array.get(i).get(j).toString());
									cell.setCellType(CellType.STRING);
 
					}
			}
		}
		
		CellRangeAddress cr = new CellRangeAddress(0, array.size(), 
													sheet.getRow(0).getFirstCellNum(), 
													sheet.getRow(0).getLastCellNum() -1);
		sheet.setAutoFilter(cr); //*AUTOFILTER*
		
		try {
			File temp = File.createTempFile(new GregorianCalendar().getTimeInMillis()+"", ".xls");
			FileOutputStream file = new FileOutputStream(temp);
			
			workbook.write(file);
			workbook.close();
			file.close();

			if (JOptionPane.showConfirmDialog(null, "Informe excel creado. Desea abrirlo?") == JOptionPane.YES_OPTION)
				Desktop.getDesktop().open(temp);
		}
		catch (Exception e){
			e.printStackTrace();
		}	
	}
}
