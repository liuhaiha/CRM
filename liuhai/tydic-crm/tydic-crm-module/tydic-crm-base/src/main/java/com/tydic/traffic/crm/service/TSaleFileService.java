package com.tydic.traffic.crm.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jodconverter.office.OfficeException;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.spring.web.json.Json;

import com.tydic.traffic.crm.entity.TSaleFiles;
import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.web.JsonResult;

/**
 * 
 * TSaleFileService
 * @desc 
 * @author zjj
 * @version V1.0 2018年7月24日
 * @since V1.0
 */
public interface TSaleFileService {
	public File preview(TSaleFiles saleFile) throws OfficeException;
	public List<TSaleFiles> query(TSaleFiles saleFile);
	public void query(Page<TSaleFiles> pageResult,Map<String, Object> params);
	public boolean delete(String[] fids);
	public JsonResult filesUpLoad(List<MultipartFile> files, TSaleFiles saleFile) throws Exception;
	public JsonResult getMenu();
	public boolean addMenu(TSysCode record);
	public boolean deleteMenu(int cid);
	public TSaleFiles getFileInfo(int fid);
	public boolean evaluate(TSaleFiles file);
}
