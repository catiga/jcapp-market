package com.jeancoder.market.internal.coupon

import java.util.Map

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.coupon.CouponInterface
import com.jeancoder.market.ready.coupon.CouponFactoryUtil
import com.jeancoder.market.ready.coupon.CouponUtilAbstract
import com.jeancoder.market.ready.dto.coupon.CouponCodeDto
import com.jeancoder.market.ready.dto.coupon.CouponCodeResult
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.service.GoodsCouponRuleServer
import com.jeancoder.market.ready.service.MovieCouponRuleServer
import com.jeancoder.market.ready.service.OnlineServerCouponRuleService
import com.jeancoder.market.ready.service.ServerCouponRuleServer
import com.jeancoder.market.ready.util.JackSonBeanMapper
import com.jeancoder.market.ready.util.NumberUtil
import com.jeancoder.market.ready.util.StringUtil

/**
 * 返回所有可以用的卡劵列表
 */
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName()+"get_available_coupon_list");
CouponCodeResult mcc = new CouponCodeResult();
def t_num =  NumberUtil.getSerialNumber();
try {
	String mobile = CommunicationSource.getParameter("mobile");
	String pid = CommunicationSource.getParameter("pid");
	def ap_id = JC.internal.param('ap_id');
	
	Logger.info('{t_num= '+t_num+ ',mobile='+mobile + ',pid='+pid);
	
	if (StringUtil.isEmpty(pid) || StringUtil.isEmpty(mobile)) {
		mcc.code = JsConstants.unknown;;
		mcc.msg = "参数不能为空";
		return mcc;
	}
	List<CouponCode> list = null;
	if(ap_id) {
		list = CouponService.INSTANSE.get_available_codes_by_mobile(mobile, ap_id, new BigInteger(pid));
	} else {
		list = CouponService.INSTANSE.get_available_codes_by_mobile(mobile, new BigInteger(pid));
	}
	Map<String,CouponBatch> couponBatch = new HashMap<String, CouponBatch>();
	List<CouponCodeDto> avacps= new ArrayList<CouponCodeDto>();
	for (CouponCode couponCode : list) {
		CouponBatch batch = couponBatch.get(couponCode.batch_id.toString());
		if (batch == null) {
			batch = CouponBatchService.INSTANSE.getById(couponCode.batch_id);
			couponBatch.put(couponCode.batch_id.toString(), batch);
		}
		if (batch == null) {
			continue;
		}
		def couponCodeRule = null;
		if (CouponConstants._coupon_app_general_.equals(batch.crapp)) {
			couponCodeRule = GoodsCouponRuleServer.INSTANSE.getRule_ByBathId(batch.id);
		}
		if (CouponConstants._coupon_app_ticket_.equals(batch.crapp)) {
			couponCodeRule = MovieCouponRuleServer.INSTANSE.getRuleByBathId(batch.id);
		}
		if (CouponConstants._coupon_app_appoint_.equals(batch.crapp)) {
			couponCodeRule = ServerCouponRuleServer.INSTANSE.getRuleByBathId(batch.id);
		}
		if (CouponConstants._coupon_online_server_.equals(batch.crapp)) {
			couponCodeRule = OnlineServerCouponRuleService.INSTANSE.getRule_ByBathId(batch.id);
		}
		if (couponCodeRule == null) {
			continue;
		}
		
		def util =  CouponFactoryUtil.getCouponUtil(batch.crapp);
		if (util == null) {
			println  "util os unll crapp=" + batch.crapp;
			continue;
		}
		if (util.isAvailable(batch, couponCode, couponCodeRule)) {
			def dto =  util.getCouponDto(batch, couponCode, couponCodeRule);
			if (dto == null) {
				println  "CouponCodeDto os unll crapp=" + batch.crapp;
				continue;
			}
			avacps.add(dto);
		}
		
	}
	mcc.code = "0";
	mcc.list = avacps;
	return  mcc;
} catch (Exception e) {
	Logger.error("查询卡劵列表失败", e);
	mcc.code = JsConstants.unknown;;
	mcc.msg = "查询失败"
	return mcc;
} finally {
	Logger.info('{t_num= '+t_num+ ' , rules:'+ JackSonBeanMapper.toJson(mcc)+'}');
}