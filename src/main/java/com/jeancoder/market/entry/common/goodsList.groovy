package com.jeancoder.market.entry.common

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.dto.GoodsInfoDto
import com.jeancoder.market.ready.util.RemoteUtil
 
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
JCRequest request = RequestSource.getRequest();
try {
	String cat_id = request.getParameter("cat_id");
	List<CommunicationParam> params = new ArrayList<CommunicationParam>();
	CommunicationParam id = new CommunicationParam("cat_id",cat_id);
	params.add(id);
	def page = RemoteUtil.connectPage(GoodsInfoDto.class, 'scm', '/incall/catalog/goods', params);
//	def list = new ArrayList<GoodsInfoDto>();
//	def dto = new GoodsInfoDto();
//	dto.id = 2L;
//	dto.goods_id="2";
//	dto.goods_name = "商品1";
//	list.add(dto);
//	
//	def dto1 = new GoodsInfoDto();
//	dto1.id = 21L;
//	dto1.goods_id="11";
//	dto1.goods_name = "商品11";
//	list.add(dto1);
	final def dtoList = page.result;
	result.setData(AvailabilityStatus.available(dtoList));
} catch (Exception e) {
	Logger.error("查询商品列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询商品列表失败"));
}
return result;