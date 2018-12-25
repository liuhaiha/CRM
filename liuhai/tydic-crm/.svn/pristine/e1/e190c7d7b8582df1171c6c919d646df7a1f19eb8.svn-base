package com.tydic.traffic.crm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tydic.traffic.crm.dao.TCrmMeetingRecordMapper;
import com.tydic.traffic.crm.dao.TSaleFilesMapper;
import com.tydic.traffic.crm.entity.TCrmMeetingRecord;
import com.tydic.traffic.crm.entity.TSaleFiles;
import com.tydic.traffic.crm.service.SaleTrackService;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example;
import com.tydic.traffic.tafa.daf.mybatis.tk.entity.Example.Criteria;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;

@Service
public class SaleTrackServiceImpl implements SaleTrackService {
	
	@Autowired
	private TCrmMeetingRecordMapper recordMapper;
	@Autowired
	private TSaleFilesMapper filesMapper;

	@Override
	public int createCom(TCrmMeetingRecord record) {
		return recordMapper.insert(record);
	}
	
	@Override
	@Pageable
	public void getMeetingList(Page<TCrmMeetingRecord> result,int cid, int pid) {
		List<TCrmMeetingRecord> list = null;
		Example example = new Example(TCrmMeetingRecord.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("cid", cid);
		if (pid != 0)
		{
			criteria.andEqualTo("pid", pid);
		}
		else
		{
			criteria.andCondition("(pid is null or pid=0)");
		}
		example.setOrderByClause("mtime asc");
		list = recordMapper.selectByExample(example);
	}

	@Override
	public TCrmMeetingRecord getMeeting(int id) {
		TCrmMeetingRecord record = recordMapper.selectByPrimaryKey(id);
		if (record.getPeer() != null) {
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("eno", record.getPeer().split(","));
			List<String> list = recordMapper.queryPeerName(param);
			String peerName = list.toString();
			record.setPeer(peerName.substring(1, peerName.length() - 1));
		}
		return record;
	}

	@Override
	public TSaleFiles createSaleFiles(TSaleFiles files) {
		filesMapper.insert(files);
		Example example = new Example(TSaleFiles.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("fname", files.getFname());
		List<TSaleFiles> list = filesMapper.selectByExample(example);
		return list.get(0);
	}

	@Override
	public int delMeeting(int id) {
		TCrmMeetingRecord meetingRecord = recordMapper.selectByPrimaryKey(id);
		meetingRecord.setIsDelete("1");
		return recordMapper.updateByPrimaryKey(meetingRecord);
	}

	@Override
	public int updateMeetingRecord(TCrmMeetingRecord record) {
		return recordMapper.updateByPrimaryKeySelective(record);
	}

}