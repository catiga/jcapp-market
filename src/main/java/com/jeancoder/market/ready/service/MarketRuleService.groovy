package com.jeancoder.market.ready.service

import com.jeancoder.market.ready.constant.MarketConstants
import com.jeancoder.market.ready.entity.MarketRuleTcss

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.dto.MarketJsonRule
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketJsonRuleWithMC
import com.jeancoder.market.ready.entity.MarketRuleMemberCardRecharge
import com.jeancoder.market.ready.entity.MarketRuleTicketingsys

class MarketRuleService {
	private static final JCLogger LOGGER = LoggerSource.getLogger(this.class);
	public static final MarketRuleService INSTANSE = new MarketRuleService();
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	/**
	 *创建会员卡充值营销活动 
	 * 
	 * 
	 */
	public Integer save_rule_info(MarketRuleMemberCardRecharge market,MarketInfo info,String mtype){
		MarketRuleMemberCardRecharge mRule=new MarketRuleMemberCardRecharge();
		Integer mmcNums=0;
		if ("3000".equals(mtype)) {
			mRule.mc_name=market.mc_name;
			mRule.pid=market.pid;
			mRule.mc_p_streg=market.mc_p_streg;
			mRule.mc_l_ms=market.mc_l_ms;
			mRule.mc_l_f=market.mc_l_f;
			mRule.mc_l_u=market.mc_l_u;
			mRule.mc_l_u_f=market.mc_l_u_f;
			mRule.mc_l_u_v=market.mc_l_u_v;
			mRule.l_cal_t_n=market.l_cal_t_n;
			mRule.l_cal_t_a=market.l_cal_t_a;
			mRule.a_time=new Date();
			mRule.c_time=new Timestamp(new Date().getTime());
			mRule.low_price_settle=market.low_price_settle;
			mRule.settle_aim=market.settle_aim;
			mmcNums=jcTemplate.save(mRule);
		}

		MarketInfo mInfo=new MarketInfo();
		mInfo.mtype=info.mtype;
		//mInfo.mru_id=mRule.id;
		mInfo.mstatus="10";
		mInfo.pid=info.pid;
		mInfo.selectmode=0;
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
		if(mmcNums>0){
			mInfo.mru_id = mmcNums;
			return jcTemplate.save(mInfo);
		}
	}
	//保存营销活动
	public Integer saveItem(MarketInfo mk,MarketJsonRuleWithMC mmc){
		MarketInfo mkInfo = new MarketInfo();
		mkInfo.mtype = mk.mtype;
		mkInfo.title = mk.title;
		mkInfo.dnum = mk.dnum;
		mkInfo.info = mk.info;
		mkInfo.selectmode=0;
		mkInfo.start_time = mk.start_time;
		mkInfo.end_time = mk.end_time;
		mkInfo.content = mk.content;
		mkInfo.pid = mk.pid;
		MarketJsonRuleWithMC mjrwc = new MarketJsonRuleWithMC();
		mjrwc.mc_p_streg = mmc.mc_p_streg;
		mjrwc.mc_user_list = mmc.mc_user_list;
		mjrwc.l_ms = mmc.l_ms;
		mjrwc.l_cs = mmc.l_cs;
		mjrwc.l_pts = mmc.l_pts;
		mjrwc.mc_l_u= mmc.mc_l_u;
		mjrwc.mc_l_u_f = mmc.mc_l_u_f;
		mjrwc.mc_l_u_v = mmc.mc_l_u_v;
		mjrwc.l_f = mmc.l_f;
		mjrwc.l_pay = mmc.l_pay;
		mjrwc.l_cts = mmc.l_cts;
		mjrwc.cal_t_n = mmc.cal_t_n;
		mjrwc.cal_t_a = mmc.cal_t_a;
		mjrwc.cal_sw = mmc.cal_sw;
		mjrwc.cal_a = mmc.cal_a;
		mjrwc.cal_r = mmc.cal_r;
		mjrwc.l_ht = mmc.l_ht;
		mjrwc.low_price_settle = mmc.low_price_settle;
		mjrwc.settle_aim = mmc.settle_aim;
		mjrwc.clear_handle_fee_policy = mmc.clear_handle_fee_policy;
		mjrwc.cal_t_nw =mmc.cal_t_nw;
		mjrwc.l_pds = mmc.l_pds;
		mjrwc.l_pde = mmc.l_pde;
		mjrwc.l_plays = mmc.l_plays;
		mjrwc.l_playe = mmc.l_playe;
		mjrwc.l_cwro = mmc.l_cwro;
		mjrwc.mc_list = mmc.mc_list;
		Integer mmcNums = jcTemplate.save(mmc);
		if(mmcNums>0){
			mk.mru_id = mmcNums;
			return jcTemplate.save(mk);
		}
		return -1;
	}
	
	//保存营销活动
	public Integer saveItemTcss(MarketInfo mk,MarketJsonRule mmc){
		MarketInfo mkInfo = new MarketInfo();
		mkInfo.mtype = mk.mtype;
		mkInfo.title = mk.title;
		mkInfo.dnum = mk.dnum;
		mkInfo.info = mk.info;
		mkInfo.selectmode=0;
		mkInfo.start_time = mk.start_time;
		mkInfo.end_time = mk.end_time;
		mkInfo.content = mk.content;
		mkInfo.pid = mk.pid;
		MarketJsonRule mjrwc = new MarketJsonRule();
		mjrwc.l_ms = mmc.l_ms;
		mjrwc.l_cs = mmc.l_cs;
		mjrwc.l_pts = mmc.l_pts;
		mjrwc.l_f = mmc.l_f;
		mjrwc.l_pay = mmc.l_pay;
		mjrwc.l_cts = mmc.l_cts;
		mjrwc.cal_t_n = mmc.cal_t_n;
		mjrwc.cal_t_a = mmc.cal_t_a;
		mjrwc.cal_sw = mmc.cal_sw;
		mjrwc.cal_a = mmc.cal_a;
		mjrwc.cal_r = mmc.cal_r;
		mjrwc.l_ht = mmc.l_ht;
		mjrwc.low_price_settle = mmc.low_price_settle;
		mjrwc.settle_aim = mmc.settle_aim;
		mjrwc.clear_handle_fee_policy = mmc.clear_handle_fee_policy;
		mjrwc.cal_t_nw =mmc.cal_t_nw;
		mjrwc.l_pds = mmc.l_pds;
		mjrwc.l_pde = mmc.l_pde;
		mjrwc.l_plays = mmc.l_plays;
		mjrwc.l_playe = mmc.l_playe;
		mjrwc.l_cwro = mmc.l_cwro;
		Integer mmcNums = jcTemplate.save(mmc);
		if(mmcNums>0){
			mk.mru_id = mmcNums;
			return jcTemplate.save(mk);
		}
		return -1;
	}

	protected int nextInt(final int min, final int max){
		Random rand= new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
	
	public List<MarketRuleTicketingsys> getItemByInfo_id(BigInteger pid,BigInteger market_info_id){
		String sql = "select * from MarketRuleTicketingsys where pid =? and market_info_id=? and flag!=?";
		return jcTemplate.find(MarketRuleTicketingsys.class, sql, pid,market_info_id,-1);
	}

	//查询所有已开启的票务营销活动
	public List<MarketInfo> get_all_market_info(def pid){
		String sql = "select * from MarketInfo where pid =? and flag!=-1 and mtype=? and mstatus=?";
		return jcTemplate.find(MarketInfo.class, sql, pid, MarketConstants._market_type_tc_ss_, '20');
	}

	public List<MarketRuleTcss> getTcssItemByInfo_id(BigInteger market_info_id){
		String sql = "select * from MarketRuleTcss where market_id=? and flag!=?";
		return jcTemplate.find(MarketRuleTcss.class, sql,market_info_id,-1);
	}
}
