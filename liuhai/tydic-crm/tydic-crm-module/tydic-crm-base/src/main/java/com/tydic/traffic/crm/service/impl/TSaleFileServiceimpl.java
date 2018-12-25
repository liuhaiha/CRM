package com.tydic.traffic.crm.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tydic.traffic.crm.common.cache.SysCodeCache;
import com.tydic.traffic.crm.controller.TSaleFileController;
import com.tydic.traffic.crm.dao.TSaleFilesMapper;
import com.tydic.traffic.crm.entity.TSaleFiles;
import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.crm.service.TSaleFileService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.vo.SysCodeVo;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example.Criteria;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;
import com.tydic.traffic.tafa.utils.uuid.UUIDUtil;
import com.tydic.traffic.tafa.web.JsonResult;
import com.tydic.traffic.crm.dao.TSysCodeMapper;

/**
 * 
 * TSaleFileServiceimpl
 * 
 * @desc
 * @author zjj
 * @version V1.0 2018年7月24日
 * @since V1.0
 */
@Service
public class TSaleFileServiceimpl implements TSaleFileService {

	private static final Logger logger = LoggerFactory.getLogger(TSaleFileController.class);

	@Value(value = "${uploadurl.crm.uploadurl}")
	private String uploadurl;
	@Autowired
	private DocumentConverter converter;
	
	@Autowired
	TSaleFilesMapper TSaleFIlesMapper;
	@Autowired
	private TSysCodeMapper TSysCodeMapper;

	@Override
	public File preview(TSaleFiles saleFile) throws OfficeException {
		List<TSaleFiles> list=TSaleFIlesMapper.select(saleFile);
		File source=new File(list.get(0).getFpath());
		File target=new File(System.getProperty("java.io.tmpdir")+UUIDUtil.getUUID()+".pdf");
		target.deleteOnExit();
		converter.convert(source).to(target).execute();
		return target;
	}
	@SuppressWarnings("resource")
	@Override
	public JsonResult filesUpLoad(List<MultipartFile> files, TSaleFiles saleFile) throws Exception {
		// TODO Auto-generated method stub

		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		List<String> message = new ArrayList<String>();

		// 多文件处理
		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			String fname = file.getOriginalFilename().substring(0, fileName.lastIndexOf("."));
			String ftype = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			long fsize = file.getSize();
			String fpath = uploadurl + "/" + UUIDUtil.getUUID();
			String common = "0";
			File f = new File(fpath +"."+ ftype);
			try {
				bis = new BufferedInputStream(file.getInputStream());
				while (f.exists()) {
					fpath += String.valueOf(Math.random() * 10);
					f = new File(fpath +"."+ ftype);
				}
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}
				fpath += "."+ftype;
				fos = new FileOutputStream(f);
				byte[] b = new byte[1024];
				while (bis.read(b) != -1) {
					fos.write(b);
				}
				try {
					// 将参数传入实体
					saleFile.setFname(fname);
					saleFile.setFtype(ftype);
					saleFile.setFsize(Float.parseFloat(String.valueOf(fsize)));
					saleFile.setFpath(fpath);
					saleFile.setCommon(common);

					TSaleFIlesMapper.insertFile(saleFile);
				} catch (Exception e) {
					f.delete();
					message.add("文件" + fname + "上传失败:" + e.getMessage());
					logger.error("文件" + fname + "上传失败:" + e.getMessage());
					throw new Exception(e);
				}
			} catch (Exception e) {
				f.delete();
				message.add("文件" + fname + "上传失败:" + e.getMessage());
				logger.error("文件" + fname + "上传失败:" + e.getMessage());
				throw new Exception(e);
			} finally {
				try {
					fos.close();
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		JsonResult result = new JsonResult();
		result.setData(message);
		return result;
	}

	@Override
	public boolean addMenu(TSysCode menu) {
		Map<String, Double> temp=TSaleFIlesMapper.nextCode(menu);
		Integer code=temp.get("code").intValue();
		Integer fclass=temp.get("fclass").intValue();
		menu.setCode((code>fclass?code:fclass).toString());
		String category=SysCodeCache.CODE_SALEDATA;
		List<SysCodeVo> list = SysCodeCache.getSysCode(category).getChildList();
		for(SysCodeVo vo:list){
			if(vo.getCid().equals(menu.getPid())){//成功匹配为二级菜单
				int result = TSaleFIlesMapper.addMenu(menu);
				if (result != 1)
					return false;
				SysCodeCache.init();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteMenu(int cid) {
		int result = TSaleFIlesMapper.deleteMenu(cid);
		if (result <= 0)
			return false;
		SysCodeCache.init();
		return true;
	}

	@Override
	public JsonResult getMenu() {
		String category=SysCodeCache.CODE_SALEDATA;
		SysCodeVo root = SysCodeCache.getSysCode(category);
		JsonResult result = new JsonResult();
		result.setData(getMenuDeep(root));
		return result;
	}

	public List<Map<String, Object>> getMenuDeep(SysCodeVo root) {
		List<SysCodeVo> list = root.getChildList();
		if (list == null || list.isEmpty() || list.size() == 0) {
			return null;
		}
		List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (SysCodeVo vo : list) {
			map = new HashMap<String, Object>();
			map.put("name", vo.getName());
			map.put("cid", vo.getCid());
			map.put("spread", "true");
			map.put("code", vo.getCode());
			map.put("pid", vo.getPid());
			List<Map<String, Object>> children = getMenuDeep(vo);
			if (children != null) {
				map.put("children", children);
			}
			menu.add(map);
		}
		return menu;
	}

	@Override
	public List<TSaleFiles> query(TSaleFiles saleFile) {
		return TSaleFIlesMapper.select(saleFile);
	}
	
	@Override
	@Pageable
	public void query(Page<TSaleFiles> pageResult, Map<String, Object> params) {
		String code=params.get("fclass").toString();
		SysCodeVo root= SysCodeCache.getSysCode(SysCodeCache.CODE_SALEDATA);
		List<SysCodeVo> list=new ArrayList<SysCodeVo>();
		list.add(root);
		SysCodeVo vo;
		//广域遍历查找code所在节点
		for(int i=0;i<list.size();i++){
			vo=list.get(i);
			if(vo.getCode().equals(code)){
				root=vo;
				break;
			}
			if(vo.getChildList()!=null)
				list.addAll(vo.getChildList());
		}
		List<String> fclass=new ArrayList<String>();
		list.clear();
		list.add(root);
		//广域遍历添加code 节点与子节点数据
		for(int i=0;i<list.size();i++){
			vo=list.get(i);
			fclass.add(vo.getCode());
			if(vo.getChildList()!=null)
				list.addAll(vo.getChildList());
		}
		params.put("fclass", fclass);
		TSaleFIlesMapper.queryFile(params);
	}

	@Override
	public boolean delete(String[] fids) {
		Example example = new Example(TSaleFiles.class);
		Example.Criteria c = example.createCriteria();
		List fidArray = new ArrayList<>();
		for(int i=0;i<fids.length-1;i++){
			fidArray.add(CommonUtil.replaceNullInt(fids[i]));
		}
		c.andIn("fid", fidArray);
		int result = TSaleFIlesMapper.deleteByExample(example);
		return result>0;
	}

	@Override
	public TSaleFiles getFileInfo(int fid) {
		TSaleFiles file = TSaleFIlesMapper.selectByPrimaryKey(fid);
		return file;
	}
	@Override
	public boolean evaluate(TSaleFiles file) {
		int num = TSaleFIlesMapper.updateByPrimaryKey(file);
		return num>0;
	}
}
