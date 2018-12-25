package com.tydic.traffic.crm.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tydic.traffic.crm.common.cache.SysCodeCache;
import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.crm.service.SysCodeService;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.web.JsonResult;

/**
 * 
 * TSysCodeController
 * @desc 
 * @author zjj
 * @version V1.0 2018年8月3日
 * @since V1.0
 */
@RestController
@RequestMapping("/sys")
public class TSysCodeController extends BaseController {
	
	private final Logger logger=LoggerFactory.getLogger(TSysCodeController.class);
	
	@Autowired
	private SysCodeService SysCodeService;
	
	@GetMapping("/getCodeMenu")
	public JsonResult getCodeMenu(){
		JsonResult result=new JsonResult();
		List<Map<String, Object>> menu=SysCodeService.getMenu();
		result.setCode("200");
		result.setData(menu);
		return result;
	}
	
	@GetMapping("/getCode/{cid}")
	public Page<TSysCode> getCode(@PathVariable int cid,
			@RequestParam(value="pageNo",required=false) Integer pageNo,
			@Param("pageSize") Integer pageSize){
		if(pageNo==null){
			pageNo=1;
		}
		if (null == pageSize) {
			pageSize = 10;
		}
		Page<TSysCode> result=new Page<TSysCode>();
		TSysCode code=new TSysCode();
		code.setPid(cid);
		result.setPageNo(pageNo);
		result.setPageSize(pageSize);
		SysCodeService.queryCodes(result,code);
		return result;
	}
	
	@DeleteMapping("/deleteCodeMenu/{cid}")
	public JsonResult deleteCodeMenu(@PathVariable("cid") int cid){
		JsonResult result=new JsonResult();
		result.setCode("200");
		
		if(!SysCodeService.deleteById(cid)){
			result.setCode("500");
			result.setMessage("非法操作");
			logger.info("工号"+super.getUser().getEno()+"非法操作");
			return result;
		}
		SysCodeCache.init();
		return result;
	}
	@PostMapping("/addCodeMenu")
	public JsonResult addCodeMenu(TSysCode code){
		JsonResult result=new JsonResult();
		result.setCode("200");
		code.setPid(code.getCid());
		if (!SysCodeService.checkDuplicateCode(code))
		{
			result.setCode("-1");
			return result;
		}
		
		code.setCreatetime(new Date());
		code.setCreator(super.getUser().getEno());
		code.setModifier(super.getUser().getEno());
		code.setModifytime(new Date());
		code.setType("1");
		if(!SysCodeService.addCodeMenu(code)){
			result.setCode("500");
			result.setMessage("非法操作");
			logger.info("工号"+super.getUser().getEno()+"非法操作");
			return result;
		}
		SysCodeCache.init();
		return result;
	}
	@PostMapping("/updateCodeMenu")
	public JsonResult updateCodeMenu(@Param("cid") Integer cid,@Param("name") String name,@Param("code") String code,@Param("sequence") Integer sequence){
		JsonResult result=new JsonResult();
		result.setCode("200");
		TSysCode tcode = new TSysCode();
		tcode = SysCodeService.getCode(cid);
		tcode.setName(name);
		tcode.setCode(code);
		tcode.setSequence(sequence);
		if (!SysCodeService.checkDuplicateCode(tcode))
		{
			result.setCode("-1");
			return result;
		}
		
		tcode.setModifier(super.getUser().getEno());
		tcode.setModifytime(new Date());
		if(!SysCodeService.updateCodeMenu(tcode)){
			result.setCode("500");
			result.setMessage("非法操作");
			logger.info("工号"+super.getUser().getEno()+"非法操作");
			return result;
		}
		SysCodeCache.init();
		return result;
	}
}
