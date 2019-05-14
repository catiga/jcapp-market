package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketRuleMemberCardRecharge
import com.jeancoder.market.ready.entity.MarketRuleRecharge
import com.jeancoder.market.ready.service.MarketRuleRechargeService
import com.jeancoder.market.ready.util.DataUtils
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
String id=request.getParameter("id")
String mtype=request.getParameter("mc_type")
String mc_name=request.getParameter("mc_name");
String mstatus=request.getParameter("mstatus");
String mc_store_name=request.getParameter("mc_store_name");
String mc_l_ms_name=request.getParameter("mc_l_ms_name");
String mc_no=request.getParameter("mc_no");//编号可手动输入，为空则自动生成
String mc_info=request.getParameter("mc_info");
String mc_store=request.getParameter("mc_store");
String mc_p_streg=request.getParameter("mc_p_streg");
String mc_start_time=request.getParameter("mc_start_time");
String mc_end_time=request.getParameter("mc_end_time");
String mc_l_ms=request.getParameter("mc_l_ms");
String mc_content=request.getParameter("mc_content");
try {
	MarketRuleRecharge market=new MarketRuleRecharge();
	MarketInfo maketInfo=new MarketInfo();
	def pid = RemoteUtil.getProj().getId();
	if (StringUtil.isEmpty(mc_name)||StringUtil.isEmpty(mc_info)||StringUtil.isEmpty(mc_p_streg)||StringUtil.isEmpty(mc_start_time)||
	StringUtil.isEmpty(mc_end_time) ){
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	String [] p_streg=mc_p_streg.split("/");//判断充值金额与返回金额是否为数字
	for (int i = 0; i < p_streg.length; i++) {
		String streg=p_streg[i];
		String [] sum=streg.split(",");
		println  sum[0]
		if (!(DataUtils.isNumber(sum[0])&&DataUtils.isNumber(sum[1])&&DataUtils.isNumber(sum[3]))) {
			return AvailabilityStatus.notAvailable("输入的活动策略必须为数字");
		}
	}

	maketInfo.mtype=mtype;
	maketInfo.pid=pid;
	maketInfo.info=mc_info;
	maketInfo.title=mc_name;
	maketInfo.start_time=DateUtil.getDate((mc_start_time), "yyyy-MM-dd HH:mm:ss");//开始时间
	maketInfo.end_time=DateUtil.getDate(mc_end_time,"yyyy-MM-dd HH:mm:ss");//结束时间
	maketInfo.content=mc_content;//详细信息
	maketInfo.dnum=mc_no;//编号
	market.mc_name=mc_name;
	market.pid=pid;
	market.mc_l_ms_name=mc_l_ms_name;
	market.store_name=mc_store_name
	market.store=mc_store;
	market.mc_p_streg=mc_p_streg; //活动策略
	market.mc_l_ms=mc_l_ms;//会员卡级别限制编号
	String msg;
	//判断id是否为空，不为空进行更新 为空增加
	if (!StringUtil.isEmpty(id)) {
		maketInfo.id=new BigInteger(id);
		maketInfo.mstatus=mstatus;
		msg=MarketRuleRechargeService.INSTANSE.update_rule_info(market,maketInfo,mtype);
	}else{
		msg=MarketRuleRechargeService.INSTANSE.save_rule_info(market,maketInfo,mtype);
	}
	if (!StringUtil.isEmpty(msg)) {
		AvailabilityStatus.notAvailable(msg)
	}
	return AvailabilityStatus.available();
} catch (Exception e) {
	e.printStackTrace()
	return AvailabilityStatus.notAvailable("添加失败");
}


