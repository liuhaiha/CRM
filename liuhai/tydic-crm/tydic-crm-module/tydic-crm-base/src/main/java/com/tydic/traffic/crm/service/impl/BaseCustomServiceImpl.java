package com.tydic.traffic.crm.service.impl;

import java.util.ArrayList;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tydic.traffic.crm.common.cache.SysCodeCache;
import com.tydic.traffic.crm.dao.TCrmCustomerMapper;
import com.tydic.traffic.crm.dao.TCrmLinkmanMapper;
import com.tydic.traffic.crm.dao.TCrmMeetingRecordMapper;
import com.tydic.traffic.crm.dao.TCrmProjectMapper;
import com.tydic.traffic.crm.dao.TProjRefLinkmanMapper;
import com.tydic.traffic.crm.dao.TSysCodeMapper;
import com.tydic.traffic.crm.dao.TSysEmployeeMapper;
import com.tydic.traffic.crm.entity.TCrmCustomer;
import com.tydic.traffic.crm.entity.TCrmLinkman;
import com.tydic.traffic.crm.entity.TCrmMeetingRecord;
import com.tydic.traffic.crm.entity.TCrmProject;
import com.tydic.traffic.crm.entity.TProjRefLinkman;
import com.tydic.traffic.crm.entity.TSysCode;
import com.tydic.traffic.crm.entity.TSysEmployee;
import com.tydic.traffic.crm.service.BaseCustomService;
import com.tydic.traffic.crm.utils.CommonUtil;
import com.tydic.traffic.crm.vo.SysCodeVo;
import com.tydic.traffic.crm.vo.TCrmLinkmanVo;
import com.tydic.traffic.crm.vo.TCrmProjectVo;
import com.tydic.traffic.crm.vo.UserInfo;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example.Criteria;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;

/**
 * 
 * BaseCustomServiceImpl
 * @desc 
 * @version V1.0 2018年7月24日
 * @since V1.0
 */
@Service
public class BaseCustomServiceImpl implements BaseCustomService {
	
	@Autowired
	private TCrmLinkmanMapper crmLinkmanMapper;
	@Autowired
	private TCrmProjectMapper projectMapper;
	@Autowired
	private TSysCodeMapper sysCodeMapper;
	@Autowired
	private TCrmCustomerMapper customerMapper;
	@Autowired
	private TSysEmployeeMapper empMapper;
	@Autowired
	private TProjRefLinkmanMapper projRefLinkmanMapper;
	@Autowired
	private TCrmMeetingRecordMapper meetingRecordMapper;
	@Autowired
	private TCrmProjectMapper tCrmProjectMapper;
	
	@Override
	public List<TCrmProject> getProjectList() {
		return projectMapper.selectByExample(null);
	}
	
	@Override
	public int delProjectById(int pid) {
		TCrmProject project = projectMapper.selectByPrimaryKey(pid);
		project.setIsDelete("1");
		return projectMapper.updateByPrimaryKey(project);
	}

	@Override
	public TCrmProject findProjectById(int pid) {
		return projectMapper.selectByPrimaryKey(pid);
	}

	@Override
	public int updateProject(TCrmProject crmProject) {
		return projectMapper.updateByPrimaryKeySelective(crmProject);
	}

	@Override
	public int createProject(TCrmProject crmProject) {
		return projectMapper.insert(crmProject);
	}

	@Override
	@Pageable
	public void getLinkmanList(Page<TCrmLinkmanVo> pageResult) {
		List<TCrmLinkmanVo> list = null;
		try {
			list = crmLinkmanMapper.queryLinkmanList();
			for (int i = 0, len = CommonUtil.getLen(list); i < len; i++) {
				TCrmLinkmanVo linkmanVo = list.get(i);
				linkmanVo.setPostName(SysCodeCache.getCodeText(SysCodeCache.CODE_PHASE, linkmanVo.getPost()));
			}
		} catch (Exception e) {
			logger.error("分页查询工作日志出错", e);
		}
	}
	
	@Override
	public int delLinkmanById(int lid) {
		TCrmLinkman linkman = crmLinkmanMapper.selectByPrimaryKey(lid);
		linkman.setIsDelete("1");
		return crmLinkmanMapper.updateByPrimaryKey(linkman);
	}
	
	@Override
	public TCrmLinkman findLinkmanById(int lid) {
		return crmLinkmanMapper.selectByPrimaryKey(lid);
	}

	@Override
	public int updateLinkman(TCrmLinkman linkman) {
		return crmLinkmanMapper.updateByPrimaryKeySelective(linkman);
	}

	@Override
	public int createLinkman(TCrmLinkman linkman, Integer pid, UserInfo userInfo) {
		int result = crmLinkmanMapper.insert(linkman);
		
		if (null != pid) {
			Example example = new Example(TCrmLinkman.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("name", linkman.getName());
			List<TCrmLinkman> list = crmLinkmanMapper.selectByExample(example);
			TProjRefLinkman projRefLinkman = new TProjRefLinkman();
			projRefLinkman.setLid(list.get(0).getLid());
			projRefLinkman.setPid(pid);
			if (null != userInfo) {
				projRefLinkman.setCreator(userInfo.getEname());
				projRefLinkman.setModifier(userInfo.getEname());
			}
			projRefLinkman.setCreatetime(new Date());
			projRefLinkman.setModifytime(new Date());
			projRefLinkmanMapper.insert(projRefLinkman);
		}
		return result;
	}

	@Override
	public List<TSysCode> getProvince(int pid) {
		Example example = new Example(TSysCode.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("pid", pid);
		List<TSysCode> list = sysCodeMapper.selectByExample(example);
		return list;
	}

	@Override
	public int createCustomer(TCrmCustomer customer) {
		return customerMapper.insert(customer);
	}
	
	@Override
	public int updateCustomer(TCrmCustomer customer) {
		return customerMapper.updateByPrimaryKeySelective(customer);
	}
	
	public int delCustomerById(Integer cid)
	{
		try
		{
			return customerMapper.deleteByPrimaryKey(cid);
		}
		catch(Exception ex)
		{
			logger.error("删除客户信息时出错,参数={cid=" + cid + "}");
			return -1;
		}
	}
	
	@Override
	public TCrmCustomer findCustById(Integer id) {
		TCrmCustomer customer = customerMapper.selectByPrimaryKey(id);
		if (null != customer)
		{
			customer.setExtend1(SysCodeCache.getCodeText(SysCodeCache.CODE_REGION, customer.getRegion()));
			customer.setExtend2(SysCodeCache.getCodeText(SysCodeCache.CODE_REGION, customer.getProvince()));
		}
		return customer;
	}

	@Override
	public TSysCode getName(int cid) {
		return sysCodeMapper.selectByPrimaryKey(cid);
	}

	private static final Logger logger= LoggerFactory.getLogger(BaseCustomServiceImpl.class);
	@Pageable
	public void queryProjectList(Page<TCrmProjectVo> result) {
		List<TCrmProjectVo> projectList = null;
		try
		{
			projectList = projectMapper.queryProjectList();
			for (int i = 0, len = CommonUtil.getLen(projectList); i < len; i++)
			{
				TCrmProjectVo projectVo = projectList.get(i);
				projectVo.setStageName(SysCodeCache.getCodeText(SysCodeCache.CODE_PHASE, projectVo.getStage()));
			}
		}
		catch(Exception ex)
		{
			logger.error("分页查询工作日志出错", ex);
		}
	}
	
	@Pageable
	public void queryLinkmanList(Page<TCrmLinkmanVo> pageResult) {
		List<TCrmLinkmanVo> linkmanList = null;
		
		try
		{
			linkmanList = crmLinkmanMapper.queryLinkmanList();
			for (int i = 0, len = CommonUtil.getLen(linkmanList); i < len; i++)
			{
				TCrmLinkmanVo linkmanVo = linkmanList.get(i);
				linkmanVo.setPostName(SysCodeCache.getCodeText(SysCodeCache.CODE_POST, linkmanVo.getPost()));
			}
		}
		catch(Exception ex)
		{
			logger.error("分页查询工作日志出错", ex);
		}
	}

	@Override
	public int delTree(int id) {
		return sysCodeMapper.deleteByPrimaryKey(id);
	}

	public TSysCode addTree(TSysCode code) {
		int num = sysCodeMapper.insert(code);
		if (num == 0)
		{
			return null;
		}
		Example example = new Example(TSysCode.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("pid",code.getPid());
		c.andEqualTo("code",code.getCode());
		c.andEqualTo("name",code.getName());
		List<TSysCode> codeList = sysCodeMapper.selectByExample(example);
		TSysCode dbCode = CommonUtil.getLen(codeList) > 0?codeList.get(0):null;
		
		if (null != dbCode)
		{
			SysCodeVo sysCode = new SysCodeVo();
			try {
				BeanUtils.copyProperties(sysCode, dbCode);
				SysCodeCache.addSysCode(sysCode.getCategory(), sysCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dbCode;
	}
	
	public List<TCrmCustomer> queryAllCust()
	{
		try
		{
			return customerMapper.selectAll();
		}
		catch(Exception ex)
		{
			logger.error("", ex);
			return null;
		}
	}

	@Override
	@Pageable
	public void queryProjectListById(Page<TCrmProjectVo> result, Integer id) {
		List<TCrmProjectVo> proList = null;
		
		try
		{
			proList = projectMapper.queryProjectListById(id);
			for (int i = 0, len = CommonUtil.getLen(proList); i < len; i++)
			{
				TCrmProjectVo projectVo = proList.get(i);
				projectVo.setStageName(SysCodeCache.getCodeText(SysCodeCache.CODE_PHASE, projectVo.getStage()));
			}
		}
		catch(Exception ex)
		{
			logger.error("分页查询工作日志出错", ex);
		}
	}

	@Override
	@Pageable
	public void queryLinkmanListById(Page<TCrmLinkmanVo> pageResult, Integer id) {
		List<TCrmLinkmanVo> list = null;
		
		try
		{
			list = crmLinkmanMapper.queryLinkmanListById(id);
			
			for (int i = 0, len = CommonUtil.getLen(list); i < len; i++)
			{
				TCrmLinkmanVo linkmanVo = list.get(i);
				linkmanVo.setDeptName(SysCodeCache.getCodeText(SysCodeCache.CODE_CUSDEPT, linkmanVo.getDept()));
				linkmanVo.setSuperDeptName(SysCodeCache.getCodeText(SysCodeCache.CODE_SUPERDEPT, linkmanVo.getSuperDept()));
				linkmanVo.setPostName(SysCodeCache.getCodeText(SysCodeCache.CODE_POST, linkmanVo.getPost()));
				linkmanVo.setSexName(SysCodeCache.getCodeText(SysCodeCache.CODE_SEX, linkmanVo.getSex()));
			}
		}
		catch(Exception ex)
		{
			logger.error("分页查询工作日志出错", ex);
		}
	}
	
	@Override
	@Pageable(id="com.tydic.traffic.crm.dao.TCrmMeetingRecordMapper.queryMeetingListByCid")
	public void queryMeetingListById(Page<TCrmMeetingRecord> pageResult, Integer id) {
		List<TCrmMeetingRecord> list = null;
		try {
			list = meetingRecordMapper.queryMeetingListByCid(id);
			String no= "";
			for (TCrmMeetingRecord record:list)
			{
				if (CommonUtil.isNotNull(record.getPeer()))
				{
					no += record.getPeer() + ",";
				}
			}
			
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("eno", no.split(",",-1));
			List<TSysEmployee> peerList = meetingRecordMapper.queryPeerNameList(param);
			
			Map<String, String> empMap = new HashMap<>();
			for (TSysEmployee emp:peerList)
			{
				empMap.put(emp.getEno(), emp.getEname());
			}
			
			for (TCrmMeetingRecord record:list)
			{
				String peer = record.getPeer();
				if (peer != null && !peer.equals("")) {
					String[] peers = peer.split(",");
					List<String> nameList = new ArrayList<>();
					
					for (String str : peers) {
						String peerName = empMap.get(str);
						nameList.add(peerName);
						
					}
					record.setPeer(nameList.toString().substring(1, nameList.toString().length() - 1));
				}
			}
			
//			for (int i = 0, len = CommonUtil.getLen(list); i < len; i++) {
//				TCrmMeetingRecord record = list.get(i);
//				System.out.println(record.getPeer());
//				if (record.getPeer() != null) {
//					Map<String, Object> param=new HashMap<String, Object>();
//					param.put("eno", record.getPeer().split(","));
//					List<String> peerList = meetingRecordMapper.queryPeerName(param);
//					String peerName = peerList.toString();
//					record.setPeer(peerName.substring(1, peerName.length() - 1));
//				}
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<TSysEmployee> getBusLeader() {
		Example example = new Example(TSysEmployee.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("station", SysCodeCache.CODE_BUS);
		return empMapper.selectByExample(example);
	}
	
	public List<TSysEmployee> getBusLeaderByName(TSysEmployee employee) {
		Example example = new Example(TSysEmployee.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("station", SysCodeCache.CODE_BUS);
		criteria.andLike("ename", "%" + employee.getEname() + "%");
		return empMapper.selectByExample(example);
	}

	@Override
	public List<TSysEmployee> getPreLeader() {
		Example example = new Example(TSysEmployee.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("station", SysCodeCache.CODE_PRE);
		return empMapper.selectByExample(example);
	}

	@Override
	public List<TSysEmployee> getDelLeader() {
		Example example = new Example(TSysEmployee.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("station", SysCodeCache.CODE_DEL);
		return empMapper.selectByExample(example);
	}

	@Override
	public List<TCrmLinkman> queryLinkmanListByName(TCrmLinkman linkman) {
		return crmLinkmanMapper.queryLinkmanListByName(linkman);
	}

	@Override
	@Pageable
	public void queryLinkmanListByPid(Page<TCrmLinkmanVo> pageResult, Integer pid) {
		List<TCrmLinkmanVo> list = null;
		
		try {
			list = projRefLinkmanMapper.queryLinkmanListByPid(pid);
			for (int i = 0, len = CommonUtil.getLen(list); i < len; i++) {
				TCrmLinkmanVo linkmanVo = list.get(i);
				linkmanVo.setDeptName(SysCodeCache.getCodeText(SysCodeCache.CODE_CUSDEPT, linkmanVo.getDept()));
				linkmanVo.setSuperDeptName(SysCodeCache.getCodeText(SysCodeCache.CODE_SUPERDEPT, linkmanVo.getSuperDept()));
				linkmanVo.setPostName(SysCodeCache.getCodeText(SysCodeCache.CODE_POST, linkmanVo.getPost()));
				linkmanVo.setSexName(SysCodeCache.getCodeText(SysCodeCache.CODE_SEX, linkmanVo.getSex()));
			}
		} catch (Exception e) {
			logger.error("分页查询客户联系人列表信息出错", e);
		}
	}
	
	@Override
	@Pageable
	public void queryMeetingListByPid(Page<TCrmMeetingRecord> pageResult, Integer cid, Integer pid) {
		List<TCrmMeetingRecord> list = null;
		list = meetingRecordMapper.queryLinkmanListByPid(cid, pid);
	}

	@Override
	public int queryFirstTreeId() {
		return customerMapper.selectAll().get(0).getCid();
	}

	@Override
	public List<TCrmProject> queryAllPro() {
		List<TCrmProject> prolist = null;
		prolist = tCrmProjectMapper.selectAll(); 
		return prolist;
	}
}
