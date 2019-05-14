package com.jeancoder.market.entry.common

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.dto.ticketingsys.CinemaHall
import com.jeancoder.market.ready.dto.ticketingsys.CinemaHallDto
import com.jeancoder.market.ready.dto.ticketingsys.CinemaHallListDto
import com.jeancoder.market.ready.dto.ticketingsys.CinemaHallResultDto
import com.jeancoder.market.ready.dto.ticketingsys.CinemaHallResultListDto
import com.jeancoder.market.ready.util.GlobalHolder
import com.jeancoder.market.ready.util.RemoteUtil

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
JCRequest request = RequestSource.getRequest();
try {
	SimpleAjax rules = JC.internal.call(SimpleAjax,"ticketingsys", "/store/store_halls_list", [pid:GlobalHolder.proj.id])
	if (!rules.available)  {
		return rules;
	}
	
	def resultHall = [];
	for (def dto : rules.data) {
		CinemaHallListDto chld = new CinemaHallListDto();
		chld.id = dto.store_basic;
		chld.CinemaStoreName= dto.store_name;//门店名称
		chld.store_basic= dto.store_basic;//门店名称
		List<CinemaHallResultListDto> resultDto1 = new ArrayList<CinemaHallResultListDto>();
		for(def cinema : dto.cinema){
			CinemaHallResultListDto resultDto = new CinemaHallResultListDto();
			resultDto.id= dto.store_basic+'-'+cinema.id;
			resultDto.CinemaHallName=cinema.name;//影厅
			resultDto1.add(resultDto);
		}
		chld.hall = resultDto1;
		resultHall.add(chld);
	}
	result.setData(AvailabilityStatus.available(resultHall));
} catch (Exception e) {
	Logger.error("查询影厅列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询影厅列表失败"));
}
return result;