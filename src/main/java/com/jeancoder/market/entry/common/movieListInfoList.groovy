package com.jeancoder.market.entry.common

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.dto.MovieListInfoDto
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.dto.QueryAutoCompletDto
import com.jeancoder.market.ready.dto.Res_MovieListInfoDto

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	//Res_MovieListInfoDto res = RemoteUtil.connect(Res_MovieListInfoDto.class, 'ticketingsys', '/plan/projmovies');
	Res_MovieListInfoDto res = JC.internal.call(Res_MovieListInfoDto.class, 'ticketingsys', '/plan/projmovies', null);
	if (!0.equals(res.code)) {
		return result.setData(AvailabilityStatus.notAvailable(res.msg));
	}
	List<MovieListInfoDto> data = res.data;
	List<QueryAutoCompletDto> params = new ArrayList<QueryAutoCompletDto>();
	for (MovieListInfoDto m:data) {
		if(m == null) {
			continue;
		}
		QueryAutoCompletDto qac = new QueryAutoCompletDto();
		qac.setLabel(m.id.toString());
		StringBuffer buffer = new StringBuffer();
		buffer.append(m.film_name);
		buffer.append("--" + m.film_no);
		if(m.film_dimensional!=null&&!m.film_dimensional.equals("")) {
			buffer.append("--" + m.film_dimensional);
			if(m.film_size!=null&&!m.film_size.equals("")) {
				buffer.append(m.film_size);
			}
		} else {
			if(m.film_size!=null&&!m.film_size.equals("")) {
				buffer.append("--" + m.film_size());
			}
		}
		qac.setValue(buffer.toString());
		params.add(qac);
	}

	final def dtoList = params;
	result.setData(AvailabilityStatus.available(dtoList));
} catch (Exception e) {
	Logger.error("查询影片列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询影片列表失败"));
}
return result;
