package org.king.excooly.support.poi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.king.excooly.ExcelCellValueDeserializer;
import org.king.excooly.support.ExcelValueDeserializerParameter;
import org.king.excooly.support.common.AbstractExcelValueDeserializer;

class ListCodec extends AbstractExcelValueDeserializer {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Object innerDeserialize(ExcelValueDeserializerParameter deserializerParam) {
		Object[] cells = deserializerParam.cells();
		int numCells = cells.length;
		Class<?> targetJavaType = deserializerParam.targetJavaType();
		Map<Class<?>, ExcelCellValueDeserializer> deserilizers = deserializerParam.deserializers();
		List ls = new ArrayList<>(numCells);
		
		for(int i = 0; i < numCells; i++) {
			Object cell = cells[i];
			PoiExcelValueDeserializerParameter parameter = (PoiExcelValueDeserializerParameter) deserializerParam;
			parameter.cells = new Object[] {cell};
			ls.add(deserilizers.get(targetJavaType).deserialize(parameter));
		}
		return ls;
	}

}
