package com.jeancoder.market.internal.market

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.constant.MarketConstants
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketRuleRecharge
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.service.MarketInfoServer
import com.jeancoder.market.ready.service.MarketRuleRechargeService
import com.jeancoder.market.ready.util.JackSonBeanMapper
import com.jeancoder.market.ready.util.MoneyUtil
import com.jeancoder.market.ready.util.NumberUtil
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil

// 会员卡充值劵
JCLogger logger = LoggerSource.getLogger(this.getClass().getName() + "_mc_recharge_market");
def t_num =  NumberUtil.getSerialNumber();
AvailabilityStatus availabilityStatus = null;
try{
	//{t_num= 2018-10-10-19:58:39:025939,mobile=15611790955,pid=1,h_id=1,recharge_amount=1,s_id=null
		
	String mobile = CommunicationSource.getParameter("mobile");// 会员手机号
	String pid = CommunicationSource.getParameter("pid"); // 项目id
	String h_id = CommunicationSource.getParameter("h_id");// 会员等级
	String recharge_amount = CommunicationSource.getParameter("recharge_amount");// 充值金额
	String s_id = CommunicationSource.getParameter("s_id");//门店id
	logger.info('{t_num= '+t_num+ ',mobile='+mobile + ',pid='+pid + ',h_id='+h_id + ',recharge_amount='+recharge_amount + ',s_id='+s_id);
	
	if (StringUtil.isEmpty(mobile) || StringUtil.isEmpty(pid) || StringUtil.isEmpty(h_id) || StringUtil.isEmpty(recharge_amount)) {
		availabilityStatus = AvailabilityStatus.notAvailable("参数不能为空");
		return availabilityStatus;
	}
	//查询当前正在进行的活动列表
	List<MarketInfo> list = MarketInfoServer.INSTANSE.getAvailableListByType(new BigInteger(pid), MarketConstants._market_type_recharge_gift_);
	if (list == null || list.isEmpty()) {
		availabilityStatus = AvailabilityStatus.available("没有合适的营销活动");
		return availabilityStatus;
	}
//	return  AvailabilityStatus.available(list);
	def coupon = [];
	def gift_balance = 0;
	for (MarketInfo item : list) {
		MarketRuleRecharge mcrRule = MarketRuleRechargeService.INSTANSE.getItem(new BigInteger(pid), item.mru_id);
		if (mcrRule == null) {
			continue;
		}
		boolean available = true;
		// 过滤门店
		if (!StringUtil.isEmpty(mcrRule.store)) {
			available = false;
			String[] stores = mcrRule.store.split(",");
			for (String str : stores) {
				if(str.equals(s_id)) {
					available = true;
					break;
				}
			}
		}
		if (!available) {
			continue;
		}
		// 过滤会员卡等级
		if (!StringUtil.isEmpty(mcrRule.mc_l_ms)) {
			available = false;
			String[] mc_l_ms = mcrRule.mc_l_ms.split(",");
			for (String str : mc_l_ms) {
				if(str.equals(h_id)) {
					available = true;
					break;
				}
			}
		}
		if (!available) {
			continue;
		}
		if (StringUtil.isEmpty(mcrRule.mc_p_streg)) { 
			continue;
		}
		// 发放
		String[]  stregs = mcrRule.mc_p_streg.split("/");
		for (String str : stregs) {
			//[0] 最少充值金额    [1] 类型 0 卡劵，[2] 编码 [3] 数量
			String[] mc_p_streg = str.split(",");
			if (mc_p_streg.length!=4) {
				continue;
			}
			// 判断金额
			String balance = MoneyUtil.add(recharge_amount, "-" + mc_p_streg[0]);//扣款
			if( Double.parseDouble(balance)< 0){
				// 低于最低金额，不赠送
				continue;
			}
			if("1".equals(mc_p_streg[1])) {
				//充值赠券
				coupon.add([mc_p_streg[2],Integer.valueOf(mc_p_streg[3] )]);
			} else if('2'.equals(mc_p_streg[1])) {
				//充值赠余额
				if(gift_balance<=0) {
					gift_balance = mc_p_streg[3];	//回传赠送金额，并且只匹配一个
				}
			}
		}
	}
	if(coupon) {
		CouponService.INSTANSE.give_out_coupons(coupon, mobile, new BigInteger(pid));
	}
	if(gift_balance) {
		availabilityStatus = AvailabilityStatus.available(gift_balance);
	} else {
		availabilityStatus = AvailabilityStatus.available();
	}
	return availabilityStatus;
} catch(Exception e){
	logger.error("赠劵失败", e);
	availabilityStatus = AvailabilityStatus.notAvailable("赠劵失败");
	return availabilityStatus;
} finally {
	logger.info('{t_num= '+t_num+ ' , rules:'+ JackSonBeanMapper.toJson(availabilityStatus)+'}');
}