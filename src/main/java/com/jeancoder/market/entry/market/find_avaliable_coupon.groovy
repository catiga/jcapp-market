package com.jeancoder.market.entry.market

import java.util.List

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.dto.coupon.CouponCodeDto
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.service.GoodsCouponRuleServer
import com.jeancoder.market.ready.service.MovieCouponRuleServer
//
//Result result = new Result(); 
//JCRequest request = RequestSource.getRequest();
//JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
//CouponService cs = CouponService.INSTANSE;
//try{
//	String mobile = request.getParameter("mobile");//手机号
//	String stores = request.getParameter("stores");//门店
//	String crapp = request.getParameter("crapp");//类型
//	
//	//查询可用卡券通过手机号
//	List<CouponCode> cCodeList = cs.get_codes_by_mobile(mobile);
//	Set<BigInteger> batch_ids = new HashSet<BigInteger>();
//	for(CouponCode cCode:cCodeList){
//		BigInteger batch_id = cCode.batch_id;
//		batch_ids.add(batch_id);
//	}
//	List<List<CouponCodeDto>> two_avaCps = new ArrayList<List<CouponCodeDto>>();
//	List<CouponCodeDto> avacps= new ArrayList<CouponCodeDto>();
//	for(BigInteger batch_id :batch_ids){
//		if(crapp=="2000"){
//			//选座
//			BigInteger mcAble = MovieCouponRuleServer.INSTANSE.findAvaliableRule(batch_id, stores);
//			if(mcAble){
//				List<CouponCode> cCodes = CouponService.INSTANSE.get_codes_by_batch_id(batch_id,mobile);
//				for(CouponCode cCode:cCodes){
//					CouponCodeDto avaCp = new CouponCodeDto();
//					avaCp.id = cCode.id;
//					avaCp.code = cCode.code;
//					avacps.add(avaCp);
//				}
//				two_avaCps.add(avacps);
//			}
//		}
//		
//		if(crapp=="1000"){
//			//商品
//			BigInteger gcAble = GoodsCouponRuleServer.INSTANSE.findAvaliableRule(batch_id, stores);
//			if(gcAble){
//				List<CouponCode> cCodes = CouponService.INSTANSE.get_codes_by_batch_id(batch_id,mobile);
//				for(CouponCode cCode:cCodes){
//					CouponCodeDto avaCp = new CouponCodeDto();
//					avaCp.id = cCode.id;
//					avaCp.code = cCode.code;
//					avacps.add(avaCp);
//				}
//				two_avaCps.add(avacps);
//			}
//		}
//		if(two_avaCps==null){
//			return result.setData(AvailabilityStatus.notAvailable("不存在可用的卡券"));
//		}
//		return result.setData(AvailabilityStatus.available(two_avaCps));
//	}
//}catch(Exception e){
//	logger.error("查询可用卡券失败",e);
//	return result.setData(AvailabilityStatus.notAvailable("查询可用卡券失败"));
//}
