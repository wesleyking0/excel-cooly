package org.king.excooly;

import org.king.excooly.support.JavaValueGetter;
import org.king.excooly.support.poi.WritingExcelColumn;

public final class UsingDefaultValueGetter implements JavaValueGetter {

	@Override
	public Object get(WritingExcelColumn writingExcelCell, Object instance) {
		throw new UnsupportedOperationException();
	}
}
