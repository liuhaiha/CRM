package com.tydic.traffic.tafa.utils.compare;


import difflib.DiffRow;
import difflib.DiffRowGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * ClassName:CompareUtils <br/>
 * Function: 基于行模式的文本差异比较工具类. <br/>
 */

public class CompareUtils {

	private static final Logger logger = LoggerFactory.getLogger(CompareUtils.class);

	public static final String Result_EQUAL = "EQUAL"; // 内容无变更

	public static final String Result_INSERT = "INSERT"; // 新增的内容

	public static final String Result_DELETE = "DELETE"; // 删除的内容

	public static final String Result_CHANGE = "CHANGE"; // 变量的内容（oldText->newText）

	/**
	 * 
	 * fileToLines:(读取文件内容至LinkedList<String>). <br/>
	 * 
	 * @param filename
	 *            文件所在位置
	 * @throws IOException
	 *             List<String>
	 */
	private static List<String> fileToLines(String filename) throws IOException {
		BufferedReader in = null;
		List<String> lines = new LinkedList<String>();
		String line = "";
		try {
			in = new BufferedReader(new FileReader(filename));
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		finally {
			if (null != in) {
				in.close();
			}
		}
		return lines;
	}

	/**
	 * 
	 * compareAll:(比较两段字符). <br/>
	 * @return
	 * @throws Exception List<CompareMo>
	 */
	public static List<CompareMo> compareAll(List<String> original, List<String> revised) throws Exception {
		final DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();
		final DiffRowGenerator dfg = builder.build();
		final List<DiffRow> rows = dfg.generateDiffRows(original, revised);
		List<CompareMo> listCompareMo = new ArrayList<CompareMo>();
		int i = 0;
		int oldSize = original.size();
		int newSize = revised.size();
		int insertSize = 0;
		int deleteSize = 0;
		for (final DiffRow diffRow : rows) {
			String tag = diffRow.getTag().toString();
			String oldLine = diffRow.getOldLine();
			String newLine = diffRow.getNewLine();
			if (Result_CHANGE.equals(tag)) {
				boolean isInset = false;
				if ((i - insertSize) <= oldSize) {
					if (oldLine != null && oldLine.trim().length() == 0) {
						if (!original.get(i - 1 - insertSize).equals(oldLine)) {
							tag = Result_INSERT;
							isInset = true;
							insertSize++;
						}
					}
				}
				if (!isInset) {
					if ((i - deleteSize) <= newSize) {
						if (newLine != null && newLine.trim().length() == 0) {
							if (!revised.get(i - 1 - deleteSize).equals(oldLine)) {
								tag = Result_DELETE;
								isInset = true;
								deleteSize++;
							}
						}
					}
				}
			}
			listCompareMo.add(new CompareMo(i, oldLine, newLine, tag));
			i++;
		}
		
		if (null != listCompareMo && listCompareMo.size() > 0) {
			// 相同类型放一起
			Comparator<CompareMo> comp = new CompareMoComparator();
			Collections.sort(listCompareMo, comp);
		}

		return listCompareMo;
	}
	
	
	/**
	 * 
	 * compareAll:(比较两个文本文件). INSERT, DELETE, CHANGE, EQUAL <br/>
	 *
	 * @return List<CompareMo>
	 * @throws IOException
	 */
	public static List<CompareMo> compareAll(String fromFileName, String toFileName) throws IOException {
		List<CompareMo> list = null;
		try {
			List<String> original = fileToLines(fromFileName);
			List<String> revised = fileToLines(toFileName);
			list = compareAll(original, revised);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);			
		}
		return list;
	}
}