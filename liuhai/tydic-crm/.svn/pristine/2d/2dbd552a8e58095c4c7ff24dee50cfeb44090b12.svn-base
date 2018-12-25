package com.tydic.traffic.crm.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.tydic.traffic.crm.entity.TCrmMeetingRecord;
import com.tydic.traffic.crm.entity.TSaleFiles;
import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.annotion.Pageable;

public interface SaleTrackService {
	
	public int createCom(TCrmMeetingRecord record);
	public int updateMeetingRecord(TCrmMeetingRecord record);
	
	@Pageable
	public void getMeetingList(Page<TCrmMeetingRecord> result,int cid, int pid);
	public TCrmMeetingRecord getMeeting(int id);
	
	public TSaleFiles createSaleFiles(TSaleFiles files);
	public int delMeeting(int id);

}
