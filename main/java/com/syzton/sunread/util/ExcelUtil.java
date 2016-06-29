package com.syzton.sunread.util;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.syzton.sunread.model.user.User;

public class ExcelUtil {

	public static String getStringFromExcelCell(Cell cell){
		String ret = "";
		try {
			if(Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
				ret ="" + (long)cell.getNumericCellValue();
			}else{
				ret =  cell.toString();
			}
		} catch (Exception e) {
		}
		return ret;
	}
	
	public static boolean getBoolFromExcelCell(Cell cell){
		boolean ret = false;
		try{
			ret = cell.getBooleanCellValue();
		}catch(Exception e){
		}
		return ret;
	}
	
	public static int getIntFromExcelCell(Cell cell){
		int ret = 0;
		try{
			ret = (int)Double.parseDouble(cell.toString());
		}catch(Exception e){
		}
		return ret;
	}
	
	public static float getFloatFromExcelCell(Cell cell){
		float ret = 0;
		try{
			ret = (float)Double.parseDouble(cell.toString());
		}catch(Exception e){
		}
		return ret;
	}

	public static long getLongFromExcelCell(Cell cell) {
		long ret = 0;
		try{
			ret = (long)Double.parseDouble(cell.toString());
		}catch(Exception e){
		}
		return ret;
	}
	
	
}
