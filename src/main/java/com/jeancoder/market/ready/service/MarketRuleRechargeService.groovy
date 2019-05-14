package com.jeancoder.market.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.constant.MarketConstants
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketRuleRecharge
import com.jeancoder.market.ready.util.StringUtil

class MarketRuleRechargeService {
	private static final JCLogger LOGGER = LoggerSource.getLogger(this.class);
	public static final MarketRuleRechargeService INSTANSE = new MarketRuleRechargeService();
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	/**
	 *创建会员卡充值营销活动
	 *
	 *
	 */
	public Integer save_rule_info(MarketRuleRecharge market,MarketInfo info,String mtype){
		MarketRuleRecharge mRule=new MarketRuleRecharge();
		Integer num=0;
		if ("2110".equals(mtype)) {
			mRule.mc_name=market.mc_name;
			mRule.mc_l_ms_name=market.mc_l_ms_name;
			mRule.store_name=market.store_name;
			mRule.store=market.store;
			mRule.pid=market.pid;
			mRule.mc_p_streg=market.mc_p_streg;
			mRule.mc_l_ms=market.mc_l_ms;
			mRule.a_time=new Date();
			mRule.c_time=new Timestamp(new Date().getTime());
			num =jcTemplate.save(mRule);
		}
		MarketInfo mInfo=new MarketInfo();
		mInfo.mtype=info.mtype;
		mInfo.mstatus=MarketConstants._market_status_wait_;
		mInfo.selectmode=0;
		mInfo.pid=info.pid;
		mInfo.title=info.title;
		mInfo.info=info.info;
		mInfo.a_time=new Date();
		mInfo.c_time=new Timestamp(new Date().getTime());
		mInfo.start_time=info.start_time;
		mInfo.end_time=info.end_time;
		mInfo.content=info.content;
		if (StringUtil.isEmpty(info.dnum)) {
			String mall_num = nextInt(100000, 999999) + "";
			mInfo.dnum=mall_num;
		}else{
			mInfo.dnum=info.dnum;
		}
		if (num>0) {
			mInfo.mru_id=num;
			return jcTemplate.save(mInfo);
		}
	}
	//更新充值赠礼类
	public Integer update_rule_info(MarketRuleRecharge market,MarketInfo info,String mtype){
		MarketInfo mInfo=MarketInfoServer.INSTANSE.getItem(info.pid,info.id);
		MarketRuleRecharge mRule= new MarketRuleRecharge();
		Integer num=0;
		if (mInfo.mru_id!=null) {
			MarketRuleRecharge Rule=getItem(market.pid, mInfo.mru_id);
			if ("2110".equals(mtype)) {
				Rule.mc_name=market.mc_name;
				Rule.mc_l_ms_name=market.mc_l_ms_name;
				Rule.store=market.store;
				Rule.mc_p_streg=market.mc_p_streg;
				Rule.mc_l_ms=market.mc_l_ms;
				Rule.store_name=market.store_name;
				Rule.c_time=new Timestamp(new Date().getTime());
				jcTemplate.update(Rule);
			}
		}else{
			mRule.store_name=market.store_name;
			mRule.mc_l_ms_name=market.mc_l_ms_name;
			mRule.store=market.store;
			mRule.mc_name=market.mc_name;
			mRule.pid=market.pid;
			mRule.mc_p_streg=market.mc_p_streg;
			mRule.mc_l_ms=market.mc_l_ms;
			mRule.a_time=new Date();
			mRule.c_time=new Timestamp(new Date().getTime());
			num=jcTemplate.save(mRule);

		}
		mInfo.mtype=info.mtype;
		mInfo.mstatus=info.mstatus;
		mInfo.pid=info.pid;
		mInfo.title=info.title;
		mInfo.info=info.info;
		mInfo.c_time=new Timestamp(new Date().getTime());
		mInfo.start_time=info.start_time;
		mInfo.end_time=info.end_time;
		mInfo.content=info.content;
		if (StringUtil.isEmpty(info.dnum)) {
			String mall_num = nextInt(100000, 999999) + "";
			mInfo.dnum=mall_num;
		}else{
			mInfo.dnum=info.dnum;
		}
		if (num>0) {
			mInfo.mru_id=num;
			return jcTemplate.update(mInfo);
		}
	}

	//根据id查询
	public MarketRuleRecharge getItem(BigInteger pid,BigInteger id){
		String sql = "select * from MarketRuleRecharge where pid =? and id=?";
		jcTemplate.get(MarketRuleRecharge.class, sql, pid,id);
	}

	protected int nextInt(final int min, final int max){
		Random rand= new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
}
