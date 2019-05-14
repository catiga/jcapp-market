package com.jeancoder.market.internal.figure

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.dto.sys.FIgureDto
import com.jeancoder.market.ready.entity.FigureInfo
import com.jeancoder.market.ready.service.FigureInfoService

/**
 * 查询收银台轮播图
 * 
 */
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
JCRequest request = RequestSource.getRequest();
def pid = request.getParameter("pid");
try {
	List<FigureInfo> info = FigureInfoService.INSTANSE.get_all_figure(pid);
	List<FIgureDto> list = new ArrayList<FIgureDto>();
	if (info!=null) {
		for(FigureInfo item : info){
			if(item.type.equals('1000')){
				FIgureDto dto = new FIgureDto();
				//筛选出收银台的轮播图
				dto.id = item.id;
				dto.info = item.info;
				dto.picture = item.picture;
				dto.figure_type = item.figure_type;
				dto.start_time = item.start_time;
				dto.end_time = item.end_time;
				list.add(dto);
			}
		}
	}
	return SimpleAjax.available('', list)
} catch (Exception e) {
	Logger.error("查询收银台轮播图列表失败", e);
	return SimpleAjax.notAvailable("查询H5轮播图列表失败");
}