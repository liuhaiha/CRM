package com.tydic.traffic.tafa.daf.page.dialect;


import com.tydic.traffic.tafa.daf.page.Page;

public class OracleDialect extends AbstractDialect {

	@Override
	public String getPageSql(String sql, Page<Object> page) {
		StringBuilder sqlBuilder = new StringBuilder(sql.length() + 120);
		int startRow = (page.getPageNo() - 1) * page.getPageSize();
		int endRow = startRow + page.getPageSize();
		if (startRow > 0) {
			sqlBuilder.append("SELECT * FROM ( ");
		}
		if (endRow > 0) {
			sqlBuilder.append(" SELECT TMP_PAGE.*, ROWNUM ROW_ID FROM ( ");
		}
		sqlBuilder.append(sql);
		if (endRow > 0) {
			sqlBuilder.append(" ) TMP_PAGE WHERE ROWNUM <= ");
			sqlBuilder.append(endRow);
		}
		if (startRow > 0) {
			sqlBuilder.append(" ) WHERE ROW_ID > ");
			sqlBuilder.append(startRow);
		}
		return sqlBuilder.toString();
	}

}
