package com.tydic.traffic.crm.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.jodconverter.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sun.star.packages.zip.ZipConstants;
import com.tydic.traffic.crm.common.cache.SysCodeCache;
import com.tydic.traffic.crm.entity.TSaleFiles;
import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.crm.service.TSaleFileService;
import com.tydic.traffic.crm.vo.UserInfo;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.web.JsonResult;

import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.lingala.zip4j.zip.ZipEngine;

/*
 * 文 件 名:  TSaleFileController
 * 版    权:   Tydic Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  zjj
 * 修 改 人:  
 * 修改时间:  
 * 修改内容:  <修改内容>
 */

@RestController
@RequestMapping("/saleFile")
public class TSaleFileController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(TSaleFileController.class);

	@Resource
	private TSaleFileService tSaleFileService;
	
	@Value("${saleData.zip.password}")
	private String zipPassword;

	@GetMapping("/preview/{fid}")
	public void preview(@PathVariable("fid") int fid) throws OfficeException{
		TSaleFiles saleFile=new TSaleFiles();
		saleFile.setFid(fid);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline;fileName=preview.pdf");
		File pdfFile=tSaleFileService.preview(saleFile);
		try {
			BufferedInputStream bis=new BufferedInputStream(new FileInputStream(pdfFile));
			BufferedOutputStream bos=new BufferedOutputStream(response.getOutputStream());
			byte[] b=new byte[1024];
			while(bis.read(b)!=-1){
				bos.write(b);
			}
			bis.close();
			bos.close();
			pdfFile.delete();
		} catch (FileNotFoundException e) {
			response.setStatus(500);
			logger.debug("openOffice转化后的"+pdfFile.getName()+"缓存文件找不到");
			e.printStackTrace();
		} catch (IOException e) {
			response.setStatus(500);
			logger.debug("web输出流出错");
			e.printStackTrace();
		}
	}
	
	@PostMapping("/upload")//多文件上传
	public JsonResult upload(@RequestParam("files") List<MultipartFile> files,
			@RequestParam("fclass") String fclass) {
		UserInfo userInfo=super.getUser();
		TSaleFiles saleFile = new TSaleFiles();

		saleFile.setCreator(userInfo.getEno());
		saleFile.setFclass(fclass);
		try {
		return tSaleFileService.filesUpLoad(files, saleFile);
		}catch (Exception e) {
			response.setStatus(500);
			JsonResult result=new JsonResult();
			result.setCode("500");
			return result;
		}
	}

	@GetMapping("/listMenu")
	public JsonResult listMenu() {
		return tSaleFileService.getMenu();
	}

	@PostMapping("/addMenu/{cid}")//添加菜单
	public JsonResult addMenu(@PathVariable("cid") String cid,
			@RequestParam("name") String name) {
		if(Integer.parseInt(cid)<1){
			response.setStatus(500);
			return null;
		}
		UserInfo userInfo=super.getUser();
		TSysCode menu = new TSysCode();
		String category=SysCodeCache.CODE_SALEDATA;
		menu.setCategory(category);
		menu.setPid(Integer.parseInt(cid));
		menu.setName(name);
		menu.setCreator(userInfo.getEno());
		menu.setCreatetime(new Date());
		menu.setModifier(userInfo.getEno());
		menu.setModifytime(new Date());
		menu.setType("1");
		JsonResult result = new JsonResult();
		if (tSaleFileService.addMenu(menu)) {
			result.setCode("200");
			result.setMessage("success");
			return result;
		}
		result.setCode("500");
		result.setMessage("fail");
		response.setStatus(500);
		return result;
	}

	@DeleteMapping("/deleteMenu/{cid}")
	public JsonResult deleteMenu(@PathVariable("cid") String cid) {
		JsonResult result = new JsonResult();
		if (tSaleFileService.deleteMenu(Integer.parseInt(cid))) {
			result.setCode("200");
			result.setMessage("success");
		}
		result.setMessage("fail");
		return result;
	}

	@GetMapping("/query/{fclass}")
	public Page<TSaleFiles> query(@PathVariable("fclass") String fclass) {
		String pageNotemp=request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String fname = request.getParameter("fname");
		String creator = request.getParameter("creator");
		String ftypeTemp = request.getParameter("ftype");
		
		int pageNo=1;
		List<String> ftypes = null;
		
		if (pageNotemp != null && !pageNotemp.toLowerCase().equals("null")
				&& !pageNotemp.isEmpty()) {
			pageNo = Integer.parseInt(pageNotemp);
		}
		if (fname == null || fname.toLowerCase().equals("null")
				|| fname.isEmpty()) {
			fname = null;
		}else{
			fname=fname.replace("*", "%");
			fname=fname.replace("?", "_");
		}
		if (creator == null || creator.toLowerCase().equals("null")
				|| creator.isEmpty()) {
			creator = null;
		}else{
			creator=creator.replace("*", "%");
			creator=creator.replace("?", "_");
		}
		if (ftypeTemp == null || ftypeTemp.toLowerCase().equals("null")
				|| ftypeTemp.isEmpty()) {
			ftypes = null;
		} else {
			String[] ftype = ftypeTemp.replaceAll("\\[?\\]?\"?", "").split(",");
			ftypes = new ArrayList<String>();
			for (String type : ftype) {
				if(type.equals("all")){
					ftypes=null;
					break;
				}
				ftypes.add(type);
			}
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fname", fname);
		params.put("fclass", fclass);
		params.put("creator", creator);
		params.put("ftype", ftypes);
		Page<TSaleFiles> pageResult=new Page<>();
		pageResult.setPageNo(pageNo);
		pageResult.setPageSize(Integer.parseInt(pageSize));
		tSaleFileService.query(pageResult,params);
		return pageResult;
	}

	@PostMapping("/delete")
	public JsonResult delete() {
		String[] fids = request.getParameter("fids").replaceAll("\\[?\\]?\"?", "").split(",");
		boolean succ = tSaleFileService.delete(fids);
		JsonResult result = new JsonResult();
		result.setCode(succ ? "1" : "-1");
		return result;
	}

	@PostMapping("/getFileInfo")
	public JsonResult edite(@Param(value = "fid") Integer fid){
		JsonResult result = new JsonResult();
		TSaleFiles file = tSaleFileService.getFileInfo(fid);
		result.setData(file);
		return result;
	}
	@PostMapping("/evaluate")
	public JsonResult edite(@Param(value = "fid") Integer fid,@Param(value = "fstar") Float fstar){
		JsonResult result = new JsonResult();
		TSaleFiles file = tSaleFileService.getFileInfo(fid);
		file.setFstar(fstar);
		boolean succ = tSaleFileService.evaluate(file);
		result.setCode(succ ? "1" : "-1");
		return result;
	}

	@PostMapping("/download")
	public JsonResult download() throws Exception {		
		String[] fids = request.getParameterValues("fids");
		TSaleFiles saleFile = new TSaleFiles();
		ZipOutputStream zos = null;
		ZipParameters zipParameters=new ZipParameters();
		zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		zipParameters.setCompressionLevel(ZipConstants.DEF_MEM_LEVEL);
		if(zipPassword !=null){
			zipParameters.setEncryptFiles(true);
			zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			zipParameters.setPassword(zipPassword.trim());
		}
		try {
			zos = new ZipOutputStream(response.getOutputStream());
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition",
					"attachment;fileName=temp.zip");
			for (String fid : fids) {
				saleFile.setFid(Integer.parseInt(fid));
				List<TSaleFiles> list = tSaleFileService.query(saleFile);
				for (TSaleFiles tsf : list) {
					File file = new File(tsf.getFpath());
					File tempFile = new File(file.getParent() + "\\"
							+ tsf.getFname() + "." + tsf.getFtype());
					if (tempFile.exists()) {
						tempFile.delete();
						tempFile.createNewFile();
					} else {
						tempFile.createNewFile();
					}
					BufferedInputStream bis = null;
					if (file.exists()) {
						try {
							byte[] b = new byte[1024];
							bis = new BufferedInputStream(new FileInputStream(
									tempFile));
							org.apache.commons.io.FileUtils.copyFile(file,
									tempFile);
							zos.putNextEntry(tempFile, zipParameters);
							while (bis.read(b) != -1) {
								zos.write(b);
							}
							zos.closeEntry();
							bis.close();
						} catch (IOException e) {
							logger.error("压缩文件" + tsf.getFname() + "失败:"
									+ e.getMessage());
							tempFile.delete();
						} finally {
							try {
								bis.close();
								tempFile.delete();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				zos.finish();
				zos.close();
			} catch (IOException e) {
				logger.error("压缩失败" + e.getMessage());
			}
		}
		JsonResult result = new JsonResult();
		result.setCode("200");
		result.setMessage("success");
		return result;
	}
}
