/*
 * Copyright (C) 2009 Search Solution Corporation. All rights reserved by Search
 * Solution.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: -
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. - Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. - Neither the name of the <ORGANIZATION> nor the names
 * of its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package com.cubrid.common.ui.spi;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * 
 * Table sorter for common table viewer
 * 
 * @author pangqiren
 * @version 1.0 - 2009-6-4 created by pangqiren
 */
public class TableViewerSorter extends
		ViewerSorter {
	protected static final int ASCENDING = 0;
	protected static final int DESCENDING = 1;

	protected int column;
	protected int direction;
	
	private HashMap<Integer, Comparator<Object>> columnComparators;
	
	public TableViewerSorter(HashMap<Integer, Comparator<Object>> columnComparators){
		this.columnComparators = columnComparators;
	}
	
	public TableViewerSorter(){
		columnComparators = new HashMap<Integer, Comparator<Object>>();
	}

	/**
	 * Does the sort. If it's a different column from the previous sort, do an
	 * ascending sort. If it's the same column as the last sort, toggle the sort
	 * direction.
	 * 
	 * @param column the table column index
	 */
	public void doSort(int column) {
		if (column == this.column) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.column = column;
			direction = ASCENDING;
		}
	}

	/**
	 * Compares the object for sorting
	 * 
	 * @param viewer the Viewer object
	 * @param e1 the object
	 * @param e2 the object
	 * @return the compared value
	 */
	@SuppressWarnings("rawtypes")
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (!(e1 instanceof Map) || !(e2 instanceof Map)) {
			return 0;
		}
		int rc = 0;
		Map map1 = (Map) e1;
		Map map2 = (Map) e2;
		Object obj1 = map1.get("" + column);
		Object obj2 = map2.get("" + column);
		Comparator<Object> comparator = columnComparators.get(column);
		
		if (comparator != null) {
			rc = comparator.compare (obj1, obj2);
		} else if (obj1 instanceof Number && obj2 instanceof Number) {
			Number num1 = (Number) obj1;
			Number num2 = (Number) obj2;
			if (num1.doubleValue() > num2.doubleValue()) {
				rc = 1;
			} else if (num1.doubleValue() < num2.doubleValue()) {
				rc = -1;
			} else {
				rc = 0;
			}
		} else if (obj1 instanceof String && obj2 instanceof String) {
			String str1 = (String) obj1;
			String str2 = (String) obj2;
			rc = str1.compareTo(str2);
		} else {
			return 0;
		}
		// If descending order, flip the direction
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}

	public boolean isAsc() {
		return this.direction == ASCENDING;
	}

	/**
	 * 
	 * Set whether asc
	 * 
	 * @param isAsc boolean
	 */
	public void setAsc(boolean isAsc) {
		if (isAsc) {
			this.direction = ASCENDING;
		} else {
			this.direction = DESCENDING;
		}
	}
	
	public void setColumnComparator(Integer column, Comparator<Object> comparator){
		columnComparators.put(column, comparator);
	}
}
