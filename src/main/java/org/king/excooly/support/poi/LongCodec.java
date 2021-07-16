package org.king.excooly.support.poi;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.king.excooly.ExcelUtils;
import org.king.excooly.support.AbstractExcelValueDeserializer;
import org.king.excooly.support.ExcelValueDeserializerParameter;
import org.king.excooly.support.PropertyValueSerializer;

/**
 * 负责将Excel值转为Long或long类型以及将Long或long类型值转为Excel值的默认转换器。
 * @author wangjw5
 */
class LongCodec extends AbstractExcelValueDeserializer implements PropertyValueSerializer {

	@Override
	public Object innerDeserialize(ExcelValueDeserializerParameter deserializerParam) {
		Cell cell = (Cell) deserializerParam.cell();
		ExcelColumnConfiguration configuration = deserializerParam.configuration();
		if(configuration.isEnum) {
			Map<String, String> excelPropertyMap = configuration.excelPropertyMap;
			String celValStr = ExcelUtils.getCellValueAsString(cell), propVal = excelPropertyMap.get(celValStr);
			if(propVal != null) return Long.parseLong(propVal);
			else if((propVal = configuration.defaultPropertyVal) != null) return Long.parseLong(propVal);
			else return null;
		} 
		
		if (cell == null) return null;
		Long v = null;
		int ct = cell.getCellType();
		switch (ct) {
			case Cell.CELL_TYPE_STRING: v = Long.parseLong(cell.getStringCellValue()); break;
			case Cell.CELL_TYPE_NUMERIC: v = (long) cell.getNumericCellValue(); break;
			case Cell.CELL_TYPE_BLANK: v = null; break;
			case Cell.CELL_TYPE_ERROR: v = null; break;
			default: throw new IllegalStateException("Unsupported format cell type " + ct + " to long");
		}
		return v;
	}

	@Override
	public void serialize(PropertyValueSerializationParameter serializationParam) {
		if(serializationParam.javaValue == null) return;
		
		Long obj = (Long) serializationParam.javaValue;
		ExcelColumnConfiguration configuration = serializationParam.columnConfiguration;
		if(configuration.isEnum && obj != null) {
			Map<String, String> propertyExcelMap = configuration.propertyExcelMap;
			String propVal = String.valueOf(obj), cellValStr = propertyExcelMap.get(propVal);
			if(cellValStr == null && configuration.defaultExcelVal != null) cellValStr = configuration.defaultExcelVal;
			serializationParam.cell.setCellValue(cellValStr);
		}else serializationParam.cell.setCellValue(obj);
	}
}
