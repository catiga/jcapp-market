package com.jeancoder.market.entry.common
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.dto.QueryAutoCompletDto
import com.jeancoder.market.ready.dto.mc.McCardAvailableDto
import com.jeancoder.market.ready.dto.mc.McHierchHiDto
import com.jeancoder.market.ready.dto.mc.McRetDto
import com.jeancoder.market.ready.util.GlobalHolder

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	
//	List<CommunicationParam> params = new ArrayList<CommunicationParam>();
//	CommunicationParam id = new CommunicationParam("pid",pid);
//	params.add(id);
//	println "----------------------------------"+id;
//	McRetDto  dto = RemoteUtil.connect(McRetDto.class, 'crm', '/getItem_mchandmc', params);
	
	BigInteger pid = GlobalHolder.proj.id;
	
	McRetDto dto = JC.internal.call(McRetDto, 'crm', '/getItem_mchandmc', [pid:pid]);
	if (!dto.available) {
		return result.setData(AvailabilityStatus.notAvailable(dto.messages[0]));
	}
	List<QueryAutoCompletDto> list = new ArrayList<QueryAutoCompletDto>();
	for (McCardAvailableDto mcDto : dto.obj) {
		for (McHierchHiDto mhDto : mcDto.hierarchyList) {
			QueryAutoCompletDto qacDto  = new QueryAutoCompletDto();
			qacDto.label = mhDto.id.toString();
			qacDto.value = mcDto.title + "______" + mhDto.h_name;
			list.add(qacDto);
		}
	}
	final def dtoList = list;
	result.setData(AvailabilityStatus.available(dtoList));
} catch (Exception e) {
	Logger.error("查询会员卡列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询会员卡列表失败"));
}
