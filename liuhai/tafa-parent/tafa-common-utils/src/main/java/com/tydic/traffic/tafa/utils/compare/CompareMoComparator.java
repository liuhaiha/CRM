package com.tydic.traffic.tafa.utils.compare;

import java.util.Comparator;

/**
 * ClassName:CompareMo <br/>
 * Function: 比较用MO按类型排序. <br/>
 * @author think
 */

public class CompareMoComparator implements Comparator<CompareMo> {
	
	@Override
	public int compare(CompareMo o1, CompareMo o2) {
		return -o2.getType().compareTo(o1.getType());
	}
}
